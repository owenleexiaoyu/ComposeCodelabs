package life.lixiaoyu.composeactionlearning.chapter2

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.*
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import life.lixiaoyu.composeactionlearning.*
import life.lixiaoyu.composeactionlearning.R

class ModifierDemoActivity: ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FullPageWrapper {
                ModifierDemoPage()
            }
        }
    }
}

@Composable
fun ModifierDemoPage() {
    Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
        DescItem("Modifier.size（width、height）")
        Row {
            HorizontalSpacer(10.dp)
            InfoBox(info = "size(50.dp) 宽 50dp 高 50dp", modifier = Modifier) {
                Image(
                    painterResource(id = R.drawable.chandler),
                    "",
                    modifier = Modifier
                        .size(50.dp)
                )
            }
            HorizontalSpacer(10.dp)
            InfoBox(info = "宽 80dp 高 50dp", modifier = Modifier) {
                Image(
                    painterResource(id = R.drawable.chandler),
                    "",
                    modifier = Modifier
                        .size(width = 80.dp, height = 50.dp),
                    contentScale = ContentScale.FillBounds
                )
            }
        }

        DescItem("Modifier.background")
        Row {
            HorizontalSpacer(10.dp)
            InfoBox(
                "Color.Green 纯色背景",
                modifier = Modifier
                    .size(60.dp)
                    .background(Color.Green)
            )
            HorizontalSpacer(10.dp)
            InfoBox(
                "Color.Green 纯色 圆角 背景",
                modifier = Modifier
                    .size(60.dp)
                    .background(Color.Green, shape = RoundedCornerShape(10.dp))
            )
        }
        VerticalSpacer(height = 10.dp)
        Row {
            HorizontalSpacer(width = 10.dp)
            val verticalGradient = Brush.verticalGradient(
                colors = listOf(Color.Red, Color.Yellow, Color.Cyan)
            )
            InfoBox(
                "Brush.verticalGradient 渐变背景",
                modifier = Modifier
                    .size(60.dp)
                    .background(brush = verticalGradient)
            )
            HorizontalSpacer(width = 10.dp)
            val radialGradient = Brush.radialGradient(colors = listOf(Color.Red, Color.Yellow, Color.Cyan))
            InfoBox(
                "Brush.radialGradient 渐变背景",
                modifier = Modifier
                    .size(60.dp)
                    .background(brush = radialGradient)
            )
            HorizontalSpacer(width = 10.dp)
            val sweepGradient = Brush.sweepGradient(colors = listOf(Color.Red, Color.Yellow, Color.Cyan))
            InfoBox(
                "Brush.sweepGradient 渐变背景",
                modifier = Modifier
                    .size(60.dp)
                    .background(brush = sweepGradient)
            )
        }
        DescItem("Modifier.fillMaxWidth（fillMaxHeight、fillMaxSize）")
        InfoBox("fillMaxWidth 撑满父容器的宽度", modifier = Modifier
            .fillMaxWidth()
            .height(50.dp)
            .background(Color.Red))
        VerticalSpacer(height = 10.dp)
        InfoBox("fraction = 0.5 的 fillMaxWidth", modifier = Modifier
            .fillMaxWidth(0.5F)
            .height(50.dp)
            .background(Color.Red))
        DescItem("Modifier.border")
        Row {
            HorizontalSpacer(width = 10.dp)
            InfoBox("2dp 宽 颜色为 Red 的 border", modifier = Modifier
                .size(100.dp, 50.dp)
                .padding(5.dp)
                .border(2.dp, Color.Red)
            )
            HorizontalSpacer(width = 10.dp)
            InfoBox("RoundedCornerShape 的 border", modifier = Modifier
                .size(100.dp, 50.dp)
                .padding(5.dp)
                .border(2.dp, Color.Red, RoundedCornerShape(10.dp))
            )
            HorizontalSpacer(width = 10.dp)
            InfoBox("Brush.sweepGradient 的 border", modifier = Modifier
                .size(100.dp, 50.dp)
                .padding(5.dp)
                .border(
                    border = BorderStroke(
                        2.dp,
                        Brush.sweepGradient(listOf(Color.Red, Color.Yellow, Color.Cyan))
                    )
                )
            )
        }
        DescItem(title = "Modifier.padding")
        InfoBox(
            info = "边框内有 10dp 内边距",
            modifier = Modifier
                .size(100.dp, 50.dp)
                .border(2.dp, Color.Blue)
                .padding(10.dp)
                .background(Color.Black)
        )
        DescItem(title = "Modifier.offset")
        InfoBox(
            info = "x 方向偏移 100dp，y 方向偏移 50dp",
            modifier = Modifier
                .size(40.dp)
                .offset(100.dp, 50.dp)
                .background(Color.Black)
        )
        DescItem(title = "Modifer.matchParentSize")
        val context = LocalContext.current
        ItemButton(text = "Modifier.matchParentSize Demo") {
            context.startActivity(Intent(context, MatchParentSizeActivity::class.java))
        }
        DescItem(title = "Modifier.weight")
        InfoBox(info = "Row 横向被红黄蓝三个色块等分", modifier = Modifier.height(50.dp)) {
            Row(
                modifier = Modifier.fillMaxSize()
            ) {
                Box(modifier = Modifier
                    .weight(1F)
                    .fillMaxHeight()
                    .background(Color.Red))
                Box(modifier = Modifier
                    .weight(1F)
                    .fillMaxHeight()
                    .background(Color.Yellow))
                Box(modifier = Modifier
                    .weight(1F)
                    .fillMaxHeight()
                    .background(Color.Blue))
            }
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFFFFFFF)
@Composable
fun PreviewModifierDemo() {
    ModifierDemoPage()
}