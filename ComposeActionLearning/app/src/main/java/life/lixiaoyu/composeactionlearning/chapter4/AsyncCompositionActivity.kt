package life.lixiaoyu.composeactionlearning.chapter4

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import life.lixiaoyu.composeactionlearning.DescItem
import life.lixiaoyu.composeactionlearning.FullPageWrapper
import life.lixiaoyu.composeactionlearning.HorizontalSpacer

class AsyncCompositionActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FullPageWrapper {
                AsyncComposition()
            }
        }
    }
}

@Composable
fun AsyncComposition() {

    val list1 = listOf("1", "2", "3", "4")
    val list2 = listOf("A", "B", "C")

    var total = 0
    Column {
        DescItem(title = "并行化重组还没有上线，所以这里的结果是线程安全的")
        Row {
            Column { // column-1
                list1.forEach {
                    Text(it)
                    total++
                }
            }
            HorizontalSpacer(width = 10.dp)
            Column { // column-2
                list2.forEach {
                    Text(it)
                    total++
                }
            }
            HorizontalSpacer(width = 10.dp)
            Text(
                if (total == 0) "No content" else "Total: $total"
            )
        }
    }
}

@Preview
@Composable
fun AsyncCompositionPreview() {
    FullPageWrapper {
        AsyncComposition()
    }
}