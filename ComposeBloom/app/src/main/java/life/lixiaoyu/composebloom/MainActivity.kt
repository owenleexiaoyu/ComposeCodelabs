package life.lixiaoyu.composebloom

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.DrawableRes
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.outlined.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import life.lixiaoyu.composebloom.ui.theme.BloomTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BloomTheme {
                AppNavigation()
            }
        }
    }
}

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "welcome") {
        composable("welcome") {
            WelcomePage(navController = navController)
        }
        composable("login") {
            LoginPage(navController = navController)
        }
        composable("home") {
            HomePage(navController = navController)
        }
    }
}

@Composable
fun HomePage(navController: NavController) {
    Scaffold(
        bottomBar = {
            BottomBar()
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colors.background)
        ) {
            VerticalSpacer(height = 40.dp)
            SearchBar()
            PlantThemeBanner()
            PlantSelectList()
        }
    }
}

@Composable
fun BottomBar() {
    val bottomItems = listOf(
        "Home" to Icons.Filled.Home,
        "Favorites" to Icons.Outlined.FavoriteBorder,
        "Profile" to Icons.Outlined.AccountCircle,
        "Cart" to Icons.Outlined.ShoppingCart
    )
    var selectedIndex by remember { mutableStateOf(0) }
    BottomNavigation(
        backgroundColor = MaterialTheme.colors.primary,
        elevation = 16.dp
    ) {
        bottomItems.forEachIndexed { index, pair ->
            BottomNavigationItem(
                selected = selectedIndex == index,
                onClick = {
                    selectedIndex = index
                },
                label = {
                    Text(
                        pair.first,
                        style = MaterialTheme.typography.caption,
                        color = MaterialTheme.colors.onPrimary
                    )
                },
                icon = {
                    Icon(
                        pair.second,
                        "",
                        modifier = Modifier.size(24.dp),
                        tint = MaterialTheme.colors.onPrimary
                    )
                }
            )
        }
    }
}

@Composable
fun PlantSelectList() {
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

    val plantSelectList = listOf(
        Triple("Monstera", R.drawable.monstera, true),
        Triple("Aglaonema", R.drawable.aglaonema, false),
        Triple("Peace lily", R.drawable.peace_lily, false),
        Triple("Fiddle leaf tree", R.drawable.fiddle_leaf, false),
        Triple("Snake plant", R.drawable.snake_plant, false),
        Triple("Pothos", R.drawable.pothos, false),
    )
    Column(
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        plantSelectList.forEach {
            PlantSelectItem(
                name = it.first,
                description = "This is a description",
                image = it.second,
                isSelected = it.third
            )
        }
    }
}

@Composable
fun PlantSelectItem(
    name: String,
    description: String,
    @DrawableRes image: Int,
    isSelected: Boolean
) {
    ConstraintLayout(
        modifier = Modifier
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
            modifier = Modifier.constrainAs(nameRef) {
                start.linkTo(imageRef.end,16.dp)
                top.linkTo(parent.top, 8.dp)
            }
        )
        Text(
            description,
            color = MaterialTheme.colors.onBackground,
            style = MaterialTheme.typography.body1,
            modifier = Modifier.constrainAs(descRef) {
                start.linkTo(nameRef.start)
                top.linkTo(nameRef.bottom, 4.dp)

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
        Divider(
            modifier = Modifier.constrainAs(dividerRef) {
                start.linkTo(imageRef.end, 8.dp)
                bottom.linkTo(parent.bottom)
            }
        )
    }
}

@Composable
fun PlantThemeBanner() {
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
    val plantThemeList = listOf(
        "Desert chic" to R.drawable.desert_chic,
        "Tiny terrariums" to R.drawable.tiny_terrariums,
        "Jungle vibes" to R.drawable.jungle_vibes,
        "Easy care" to R.drawable.easy_care,
        "Statements" to R.drawable.statements
    )
    Row(modifier = Modifier
        .height(168.dp)
        .padding(16.dp)
        .horizontalScroll(rememberScrollState()),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        plantThemeList.forEach {
            Surface(
                modifier = Modifier.size(136.dp),
                shape = MaterialTheme.shapes.small,
                elevation = 1.dp,
                color = MaterialTheme.colors.surface
            ) {
                Column(
                    modifier = Modifier.fillMaxSize()
                ) {
                    Image(
                        painter = painterResource(id = it.second),
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
                            text = it.first,
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

@Preview(showBackground = true)
@Preview(showBackground = true, uiMode = UI_MODE_NIGHT_YES)
@Composable
fun DefaultPreview() {
    BloomTheme {
        val navController = rememberNavController()
        HomePage(navController)
    }
}