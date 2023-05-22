package life.lixiaoyu.composebloom.ui

import android.widget.Toast
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.Share
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.launch
import life.lixiaoyu.composebloom.data.LocalDataSource
import life.lixiaoyu.composebloom.data.Plant
import life.lixiaoyu.composebloom.ui.theme.BloomTheme

@Composable
fun PlantDetailPage(plantId: Int, fromBanner: Boolean?) {
    val context = LocalContext.current
    LaunchedEffect(key1 = Unit, block = {
        launch {
            Toast.makeText(context, "From Banner = $fromBanner", Toast.LENGTH_SHORT).show()
        }
    })
    val plant = LocalDataSource.getPlantById(plantId)
    if (plant == null) {
        Box (modifier = Modifier.fillMaxSize()) {
            Text("Content is Empty",
                fontSize = 20.sp,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.align(Alignment.Center))
        }
    } else {
        PlantDetail(plant)
    }
}

@Composable
fun PlantDetail(plant: Plant) {
    Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
        Box(
            Modifier
                .fillMaxWidth()
                .height(240.dp)
        ) {
            Image(
                painter = painterResource(id = plant.image), "",
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )
            Box(modifier = Modifier
                .background(
                    MaterialTheme.colors.primary,
                    shape = RoundedCornerShape(topEnd = 10.dp)
                )
                .padding(10.dp)
                .align(Alignment.BottomStart)
            ) {
                Text("$${plant.price}")
            }
            Row(
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(10.dp)
            ) {
                Box(
                    modifier = Modifier
                        .clickable { }
                        .background(MaterialTheme.colors.primary, shape = RoundedCornerShape(4.dp))
                        .padding(4.dp)
                ) {
                    Icon(Icons.Outlined.Share, "", tint = MaterialTheme.colors.onPrimary)
                }
                HorizontalSpacer(width = 8.dp)
                Box(
                    modifier = Modifier
                        .clickable { }
                        .background(MaterialTheme.colors.primary, shape = RoundedCornerShape(4.dp))
                        .padding(4.dp)
                ) {
                    Icon(Icons.Outlined.FavoriteBorder, "", tint = MaterialTheme.colors.onPrimary)
                }
            }
        }
        Column(modifier = Modifier.padding(10.dp)) {
            Text(plant.name, fontSize = 20.sp, fontWeight = FontWeight.SemiBold)
            VerticalSpacer(height = 8.dp)
            Text(text = plant.description,
                lineHeight = 16.sp,
                textAlign = TextAlign.Justify)
            VerticalSpacer(height = 8.dp)
            Button(
                onClick = {},
                modifier = Modifier
                    .height(48.dp)
                    .fillMaxWidth()
                    .clip(MaterialTheme.shapes.medium),
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = MaterialTheme.colors.secondary,
                    contentColor = MaterialTheme.colors.onSecondary
                )
            ) {
                Text(
                    "Add to cert",
                    style = MaterialTheme.typography.button
                )
            }
        }
    }
}

@Preview
@Composable
fun PlantDetailPagePreview() {
    BloomTheme {
        PlantDetailPage(0, false)
    }
}