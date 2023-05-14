package life.lixiaoyu.composeactionlearning.chapter6

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import life.lixiaoyu.composeactionlearning.DescItem
import life.lixiaoyu.composeactionlearning.FullPageWrapper

@Composable
fun CrossfadePage() {
    FullPageWrapper {
        Column(Modifier.padding(10.dp)) {
            DescItem(title = "使用 Crossfade 添加淡入淡出切换动画")
            var currentPage by remember { mutableStateOf("A") }
            Button(onClick = {
                currentPage = if (currentPage == "A") {
                    "B"
                } else {
                    "A"
                }
            }) {
                Text("切换页面")
            }
            Crossfade(targetState = currentPage) { currentPage ->
                when (currentPage) {
                    "A" -> Text("Page A",
                        Modifier
                            .background(Color.Green)
                            .padding(20.dp))
                    "B" -> Text("Page B",
                        Modifier
                            .background(Color.Red)
                            .padding(20.dp))
                }
            }
        }
    }
}