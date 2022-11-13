package life.lixiaoyu.composeactionlearning.chapter3

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import life.lixiaoyu.composeactionlearning.DescItem
import life.lixiaoyu.composeactionlearning.FullPageWrapper
import life.lixiaoyu.composeactionlearning.VerticalSpacer

class CompositionLocalDemoActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FullPageWrapper {
                CompositionLocalPage()
            }
        }
    }

}

@Composable
fun CompositionLocalPage() {
    val LocalString = compositionLocalOf { "AAA" }
    Column(modifier = Modifier
        .padding(10.dp)
        .verticalScroll(rememberScrollState())) {
        DescItem(title = "CompositionLocal 简单使用")
        Text(
            text = LocalString.current,
            color = Color.Red
        )
        CompositionLocalProvider(
            LocalString provides "BBB"
        ) {
            Text(
                text = LocalString.current,
                color = Color.Green
            )
            CompositionLocalProvider(
                LocalString provides "CCC"
            ) {
                Text(
                    text = LocalString.current,
                    color = Color.Blue
                )
            }
        }
        CompositionLocalOfDemo()
        StaticCompositionLocalOfDemo()
    }
}

var recomposeFlag = "No Recompose"
val LocalColor = compositionLocalOf { Color.Black }
@Composable
fun CompositionLocalOfDemo() {
    var color by remember { mutableStateOf(Color.Green) }
    Column {
        DescItem(title = "compositionLocalOf 重组方式")
        CompositionLocalProvider(
            LocalColor provides color
        ) {
            TaggedBox("Wrapper: ${recomposeFlag}", 400.dp, Color.Red) {
                TaggedBox("Middle: ${recomposeFlag}", 300.dp, LocalColor.current) {
                    TaggedBox("Inner: ${recomposeFlag}", 200.dp, Color.Yellow)
                }
            }
        }
        VerticalSpacer(height = 20.dp)
        Button(onClick = { color = Color.Blue }) {
            Text(text = "Trigger Recomposition")
        }
    }
    recomposeFlag = "Recompose"
}

var recomposeFlag2 = "No Recompose"
val LocalColor2 = staticCompositionLocalOf { Color.Black }
@Composable
fun StaticCompositionLocalOfDemo() {
    var color by remember { mutableStateOf(Color.Green) }
    Column {
        DescItem(title = "staticCompositionLocalOf 重组方式")
        CompositionLocalProvider(
            LocalColor2 provides color
        ) {
            TaggedBox("Wrapper: ${recomposeFlag2}", 400.dp, Color.Red) {
                TaggedBox("Middle: ${recomposeFlag2}", 300.dp, LocalColor2.current) {
                    TaggedBox("Inner: ${recomposeFlag2}", 200.dp, Color.Yellow)
                }
            }
        }
        VerticalSpacer(height = 20.dp)
        Button(onClick = { color = Color.Blue }) {
            Text(text = "Trigger Recomposition")
        }
    }
    recomposeFlag2 = "Recompose"
}
@Composable
fun TaggedBox(tag: String, size: Dp, background: Color, content: @Composable () -> Unit = {}) {
    Column(modifier = Modifier
        .size(size)
        .background(background),
    horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = tag)
        Box(modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center) {
            content()
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CompositionLocalPagePreview() {
    FullPageWrapper {
        CompositionLocalPage()
    }
}

