package life.lixiaoyu.composebloom.ui

import androidx.annotation.DrawableRes
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material.icons.outlined.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.navigation.NavController
import life.lixiaoyu.composebloom.data.LocalDataSource

@Composable
fun HomeScreen(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background)
    ) {
        VerticalSpacer(height = 40.dp)
        SearchBar()
        PlantThemeBanner(onBannerItemClicked = { plantId ->
            navController.navigate("plantDetail/$plantId?fromBanner=true")
        })
        PlantSelectList(onPlantItemClicked = { plantId ->
            navController.navigate("plantDetail/$plantId?fromBanner=false")
        })
    }
}

@Composable
fun PlantSelectList(onPlantItemClicked: (Int) -> Unit = {}) {
    Row(
        modifier = Modifier
            .height(40.dp)
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            "Design your home garden",
            color = MaterialTheme.colors.onBackground,
            style = MaterialTheme.typography.h1
        )
        Icon(
            Icons.Filled.FilterList, "",
            tint = MaterialTheme.colors.onBackground,
            modifier = Modifier.size(24.dp)
        )
    }
    Column(
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        LocalDataSource.selectPlantList.forEachIndexed { index, plant ->
            PlantSelectItem(
                modifier = Modifier.clickable {
                    onPlantItemClicked.invoke(plant.id)
                },
                name = plant.name,
                description = plant.description,
                image = plant.image,
                isSelected = index % 2 == 0,
                showDivider = index != LocalDataSource.selectPlantList.size - 1
            )
        }
    }
}

@Composable
fun PlantSelectItem(
    modifier: Modifier = Modifier,
    name: String,
    description: String,
    @DrawableRes image: Int,
    isSelected: Boolean,
    showDivider: Boolean
) {
    ConstraintLayout(
        modifier = modifier
            .height(64.dp)
            .fillMaxWidth()
    ) {
        val (imageRef, nameRef, descRef, checkboxRef, dividerRef) = remember { createRefs() }
        Image(
            painter = painterResource(id = image),
            contentDescription = "",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(64.dp)
                .clip(MaterialTheme.shapes.small)
                .constrainAs(imageRef) {
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                    start.linkTo(parent.start)
                }
        )
        Text(
            name,
            color = MaterialTheme.colors.onBackground,
            style = MaterialTheme.typography.h2,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.constrainAs(nameRef) {
                start.linkTo(imageRef.end,16.dp)
                top.linkTo(parent.top, 8.dp)
            }
        )
        Text(
            description,
            color = MaterialTheme.colors.onBackground,
            style = MaterialTheme.typography.body1,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.constrainAs(descRef) {
                width = Dimension.fillToConstraints
                start.linkTo(nameRef.start)
                top.linkTo(nameRef.bottom, 4.dp)
                end.linkTo(checkboxRef.start, 8.dp)
            }
        )
        Checkbox(
            checked = isSelected,
            onCheckedChange = {},
            modifier = Modifier
                .size(24.dp)
                .constrainAs(checkboxRef) {
                    end.linkTo(parent.end)
                    top.linkTo(parent.top, 16.dp)
                }
        )
        if (showDivider) {
            Divider(
                thickness = 0.5.dp,
                modifier = Modifier.constrainAs(dividerRef) {
                    start.linkTo(imageRef.end, 8.dp)
                    bottom.linkTo(parent.bottom)
                }
            )
        }
    }
}

@Composable
fun PlantThemeBanner(onBannerItemClicked: (Int) -> Unit = {}) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(32.dp)
            .padding(start = 16.dp),
        contentAlignment = Alignment.BottomStart
    ) {
        Text("Browse themes",
            style = MaterialTheme.typography.h1,
            color = MaterialTheme.colors.onBackground
        )
    }
    Row(modifier = Modifier
        .height(168.dp)
        .padding(16.dp)
        .horizontalScroll(rememberScrollState()),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        LocalDataSource.bannerPlantList.forEach { plant ->
            Surface(
                modifier = Modifier
                    .size(136.dp)
                    .clickable {
                        onBannerItemClicked.invoke(plant.id)
                    },
                shape = MaterialTheme.shapes.small,
                elevation = 1.dp,
                color = MaterialTheme.colors.surface
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(MaterialTheme.colors.surface)
                ) {
                    Image(
                        painter = painterResource(id = plant.image),
                        "",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(96.dp)
                    )
                    Box(modifier = Modifier
                        .fillMaxWidth()
                        .height(40.dp)
                        .padding(start = 16.dp),
                        contentAlignment = Alignment.CenterStart
                    ) {
                        Text(
                            text = plant.name,
                            style = MaterialTheme.typography.h2,
                            color = MaterialTheme.colors.onSurface
                        )
                    }

                }
            }
        }
    }
}

@Composable
fun SearchBar() {
    BasicTextField(
        value = "Search",
        onValueChange = {},
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp)
            .padding(horizontal = 16.dp)
            .border(
                1.dp, MaterialTheme.colors.onBackground,
                shape = MaterialTheme.shapes.small
            ),
        decorationBox = { innerTextField ->
            Row(
                modifier = Modifier.padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    Icons.Outlined.Search, "",
                    tint = MaterialTheme.colors.onBackground,
                    modifier = Modifier.size(18.dp)
                )
                HorizontalSpacer(width = 8.dp)
                innerTextField()
            }
        }
    )
}