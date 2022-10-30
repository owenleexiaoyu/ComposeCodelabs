package life.lixiaoyu.composeactionlearning.chapter2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import life.lixiaoyu.composeactionlearning.*

class ProgressIndicatorDemoActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FullPageWrapper {
                ProgressIndicatorPage()
            }
        }
    }
}

@Composable
fun ProgressIndicatorPage() {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        DescItem(title = "无限加载的圆形进度条(默认样式)")
        CircularProgressIndicator()
        DescItem(title = "无限加载的圆形进度条(自定义样式)")
        CircularProgressIndicator(
            color = Color.Red,
            strokeWidth = 4.dp
        )
        DescItem(title = "有进度的圆形进度条(默认样式)")
        CircularProgressIndicator(
            progress = 0.5F
        )

        DescItem(title = "有进度的圆形进度条(有动画)")
        var progress by remember {
            mutableStateOf(0.3F)
        }
        val animatedProgress by animateFloatAsState(
            targetValue = progress,
            animationSpec = ProgressIndicatorDefaults.ProgressAnimationSpec
        )
        CircularProgressIndicator(
            progress = animatedProgress
        )
        Row {
            OutlinedButton(onClick = {
                if (progress < 1F) {
                    progress += 0.1F
                }
            }) {
                Text("增加进度")
            }
            HorizontalSpacer(width = 10.dp)
            OutlinedButton(onClick = {
                if (progress > 0F) {
                    progress -= 0.1F
                }
            }) {
                Text("减少进度")
            }
        }
        
        DescItem(title = "无限加载的条形进度条(默认样式)")
        LinearProgressIndicator()
        DescItem(title = "无限加载的条形进度条(自定义样式)")
        LinearProgressIndicator(
            color = Color.Red,
            backgroundColor = Color.Green.copy(alpha = 0.5F),
            modifier = Modifier
                .height(10.dp)
                .clip(CircleShape)
        )
        DescItem(title = "有进度的条形进度条(默认样式)")
        LinearProgressIndicator(
            progress = 0.7F
        )
        DescItem(title = "有进度的圆形进度条(有动画)")
        var progress2 by remember {
            mutableStateOf(0.3F)
        }
        val animatedProgress2 by animateFloatAsState(
            targetValue = progress2,
            animationSpec = ProgressIndicatorDefaults.ProgressAnimationSpec
        )
        LinearProgressIndicator(
            progress = animatedProgress2
        )
        Row {
            OutlinedButton(onClick = {
                if (progress2 < 1F) {
                    progress2 += 0.1F
                }
            }) {
                Text("增加进度")
            }
            HorizontalSpacer(width = 10.dp)
            OutlinedButton(onClick = {
                if (progress2 > 0F) {
                    progress2 -= 0.1F
                }
            }) {
                Text("减少进度")
            }
        }
    }
}

@Composable
@Preview(showBackground = true)
fun ProgressIndicatorPreview() {
    FullPageWrapper {
        ProgressIndicatorPage()
    }
}