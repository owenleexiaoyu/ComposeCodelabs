package life.lixiaoyu.composeactionlearning.chapter2

import android.os.Bundle
import android.text.Layout
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.twotone.ShoppingCart
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import life.lixiaoyu.composeactionlearning.*
import life.lixiaoyu.composeactionlearning.R
import life.lixiaoyu.composeactionlearning.ui.theme.Purple200
import life.lixiaoyu.composeactionlearning.ui.theme.Purple500

class ImageDemoActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FullPageWrapper {
                ImagePage()
            }
        }
    }
}

@Composable
fun ImagePage() {
    Column {
        DescItem(title = "Icon 的使用")
        Row(
            modifier = Modifier.padding(10.dp)
        ) {
            InfoBox(info = "Icon imageVector 属性加载 MD icon") {
                Icon(
                    imageVector = Icons.Filled.Favorite,
                    contentDescription = "",
                    modifier = Modifier.size(36.dp)
                )
            }
            InfoBox(info = "Icon imageVector 属性加载 MD icon，tint 染色") {
                Icon(
                    imageVector = Icons.Filled.Favorite,
                    contentDescription = "",
                    tint = Color.Red,
                    modifier = Modifier.size(36.dp)
                )
            }
            InfoBox(info = "Icon imageVector 属性加载纯色 svg 资源") {
                Icon(
                    imageVector = ImageVector.vectorResource(id = R.drawable.ic_star),
                    contentDescription = "",
                    modifier = Modifier.size(36.dp)
                )
            }
            InfoBox(info = "Icon imageVector 属性加载纯色 svg 资源，tint 染色") {
                Icon(
                    imageVector = ImageVector.vectorResource(id = R.drawable.ic_star),
                    contentDescription = "",
                    tint = Color.Yellow,
                    modifier = Modifier.size(36.dp)
                )
            }
            InfoBox(info = "Icon imageVector 属性加载多色 svg 资源") {
                Icon(
                    imageVector = ImageVector.vectorResource(id = R.drawable.ic_konglong),
                    contentDescription = "",
                    modifier = Modifier.size(36.dp)
                )
            }
            InfoBox(info = "Icon imageVector 属性加载纯色 svg 资源，tint 染色") {
                Icon(
                    imageVector = ImageVector.vectorResource(id = R.drawable.ic_konglong),
                    contentDescription = "",
                    tint = Color.Blue,
                    modifier = Modifier.size(36.dp)
                )
            }
            InfoBox(info = "Icon bitmap 属性加载 png 资源") {
                Icon(
                    bitmap = ImageBitmap.imageResource(id = R.drawable.ic_comment),
                    contentDescription = "",
                    modifier = Modifier.size(36.dp)
                )
            }
            InfoBox(info = "Icon bitmap 属性加载 png 资源，tint 染色") {
                Icon(
                    bitmap = ImageBitmap.imageResource(id = R.drawable.ic_comment),
                    contentDescription = "",
                    tint = Color.Green,
                    modifier = Modifier.size(36.dp)
                )
            }
            InfoBox(info = "Icon imageVector 属性加载 MD 双色 icon") {
                Icon(
                    imageVector = Icons.TwoTone.ShoppingCart,
                    contentDescription = "",
                    modifier = Modifier.size(36.dp)
                )
            }
        }
        DescItem(title = "Image 的使用")
        Row {
            InfoBox(info = "Image imageVector 属性加载 svg 资源") {
                Image(
                    imageVector = ImageVector.vectorResource(id = R.drawable.ic_konglong),
                    contentDescription = "",
                    modifier = Modifier.size(80.dp)
                )
            }
            HorizontalSpacer(width = 10.dp)
            InfoBox(info = "Image painter 属性加载 png 资源") {
                Image(
                    painter = painterResource(id = R.drawable.chandler),
                    contentDescription = "",
                    modifier = Modifier.size(80.dp)
                )
            }
        }
        DescItem(title = "Image 的 alignment")
        val alignmentList = listOf(
            "Alignment.TopStart" to Alignment.TopStart,
            "Alignment.Center" to Alignment.TopCenter,
            "Alignment.TopEnd" to Alignment.TopEnd,
            "Alignment.CenterStart" to Alignment.CenterStart,
            "Alignment.Center" to Alignment.Center,
            "Alignment.CenterEnd" to Alignment.CenterEnd,
            "Alignment.BottomStart" to Alignment.BottomStart,
            "Alignment.BottomCenter" to Alignment.BottomCenter,
            "Alignment.BottomEnd" to Alignment.BottomEnd,
        )
        Row(
            modifier = Modifier
                .horizontalScroll(rememberScrollState())
                .padding(horizontal = 10.dp)
        ) {
            alignmentList.forEach{
                InfoBox(info = it.first) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_comment),
                        contentDescription = "",
                        modifier = Modifier
                            .size(80.dp)
                            .background(Color.Blue),
                        alignment = it.second,
                        contentScale = ContentScale.None
                    )
                }
                HorizontalSpacer(width = 5.dp)
            }
        }
        DescItem(title = "Image 的 contentScale")
        val contentScaleList = listOf(
            "ContentScale.Fit" to ContentScale.Fit,
            "ContentScale.FillBounds" to ContentScale.FillBounds,
            "ContentScale.FillWidth" to ContentScale.FillWidth,
            "ContentScale.FillHeight" to ContentScale.FillHeight,
            "ContentScale.None" to ContentScale.None,
            "ContentScale.Crop" to ContentScale.Crop,
            "ContentScale.Inside" to ContentScale.Inside,
        )
        Row(
            modifier = Modifier
                .horizontalScroll(rememberScrollState())
                .padding(horizontal = 10.dp)
        ) {
            contentScaleList.forEach{
                InfoBox(info = it.first) {
                    Image(
                        painter = painterResource(id = R.drawable.chandler),
                        contentDescription = "",
                        modifier = Modifier
                            .size(width = 80.dp, height = 120.dp)
                            .background(Purple500),
                        contentScale = it.second
                    )
                }
                HorizontalSpacer(width = 5.dp)
            }
        }
        DescItem(title = "Image 的 colorFilter.tint")
        val colorBlendMode = listOf(
            "BlendMode.Color" to BlendMode.Color,
            "BlendMode.ColorBurn" to BlendMode.ColorBurn,
            "BlendMode.Clear" to BlendMode.Clear,
            "BlendMode.ColorDodge" to BlendMode.ColorDodge,
            "BlendMode.Dst" to BlendMode.Dst,
            "BlendMode.Darken" to BlendMode.Darken,
            "BlendMode.Difference" to BlendMode.Difference,
            "BlendMode.DstAtop" to BlendMode.DstAtop,
            "BlendMode.DstIn" to BlendMode.DstIn,
            "BlendMode.DstOut" to BlendMode.DstOut,
            "BlendMode.DstOver" to BlendMode.DstOver,
            "BlendMode.Exclusion" to BlendMode.Exclusion,
            "BlendMode.Hardlight" to BlendMode.Hardlight,
            "BlendMode.Hue" to BlendMode.Hue,
            "BlendMode.Lighten" to BlendMode.Lighten,
            "BlendMode.Luminosity" to BlendMode.Luminosity,
            "BlendMode.Modulate" to BlendMode.Modulate,
            "BlendMode.Multiply" to BlendMode.Multiply,
            "BlendMode.Overlay" to BlendMode.Overlay,
            "BlendMode.Plus" to BlendMode.Plus,
            "BlendMode.Saturation" to BlendMode.Saturation,
            "BlendMode.Screen" to BlendMode.Screen,
            "BlendMode.Src" to BlendMode.Src,
            "BlendMode.SrcIn" to BlendMode.SrcIn,
            "BlendMode.SrcOut" to BlendMode.SrcOut,
            "BlendMode.SrcAtop" to BlendMode.SrcAtop,
            "BlendMode.SrcOver" to BlendMode.SrcOver,
            "BlendMode.Softlight" to BlendMode.Softlight,
            "BlendMode.Xor" to BlendMode.Xor,
        )
        Row(
            modifier = Modifier
                .horizontalScroll(rememberScrollState())
                .padding(horizontal = 10.dp)
        ) {
            InfoBox(info = "原图") {
                Image(
                    painter = painterResource(id = R.drawable.chandler),
                    contentDescription = "",
                    modifier = Modifier
                        .size(80.dp),
                )
            }
            HorizontalSpacer(width = 5.dp)
            colorBlendMode.forEach{
                InfoBox(info = it.first) {
                    Image(
                        painter = painterResource(id = R.drawable.chandler),
                        contentDescription = "",
                        modifier = Modifier
                            .size(80.dp),
                        colorFilter = ColorFilter.tint(Purple200, it.second)
                    )
                }
                HorizontalSpacer(width = 5.dp)
            }
        }
        DescItem(title = "Image 的 colorFilter.lighting")
        Row(
            modifier = Modifier
                .padding(horizontal = 10.dp)
        ) {
            InfoBox(info = "原图") {
                Image(
                    painter = painterResource(id = R.drawable.chandler),
                    contentDescription = "",
                    modifier = Modifier
                        .size(80.dp),
                )
            }
            HorizontalSpacer(width = 5.dp)
            InfoBox(info = "ColorFilter.lighting(Purple200, Color.Cyan)") {
                Image(
                    painter = painterResource(id = R.drawable.chandler),
                    contentDescription = "",
                    modifier = Modifier
                        .size(80.dp),
                    colorFilter = ColorFilter.lighting(Purple200, Color.Cyan)
                )
            }
        }
    }
}

@Composable
@Preview(showBackground = true)
fun ImagePagePreview() {
    FullPageWrapper {
        ImagePage()
    }
}