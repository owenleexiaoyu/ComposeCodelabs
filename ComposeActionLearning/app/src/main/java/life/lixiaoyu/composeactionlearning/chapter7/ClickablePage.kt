package life.lixiaoyu.composeactionlearning.chapter7

import android.widget.Toast
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Switch
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import life.lixiaoyu.composeactionlearning.DescItem
import life.lixiaoyu.composeactionlearning.FullPageWrapper
import life.lixiaoyu.composeactionlearning.HorizontalSpacer

@Composable
fun ClickablePage() {
    FullPageWrapper {
        Column(Modifier.padding(10.dp)) {
            ClickableDemo()
            CombinedClickableDemo()
        }
    }
}

@Composable
fun ColumnScope.ClickableDemo() {
    DescItem(title = "Modifier.clickable 监听点击事件")
    var isBoxClickable by remember { mutableStateOf(true) }
    Row(verticalAlignment = Alignment.CenterVertically) {
        Text("Box 是否可点击？")
        HorizontalSpacer(width = 2.dp)
        Switch(checked = isBoxClickable, onCheckedChange = {
            isBoxClickable = it
        })
    }
    val context = LocalContext.current
    Box(modifier = Modifier
        .size(50.dp)
        .background(Color.Blue)
        .clickable(
            enabled = isBoxClickable,
            onClick = {
                Toast
                    .makeText(context, "点击了 Box", Toast.LENGTH_SHORT)
                    .show()
            }
        ))
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ColumnScope.CombinedClickableDemo() {
    DescItem(title = "Modifier.combinedClickable 监听复合点击事件")
    val context = LocalContext.current
    Box(modifier = Modifier
        .size(50.dp)
        .background(Color.Blue)
        .combinedClickable(
            onClick = {
                Toast
                    .makeText(context, "combinedClickable Box 单击", Toast.LENGTH_SHORT)
                    .show()
            },
            onLongClick = {
                Toast
                    .makeText(context, "combinedClickable Box 长按", Toast.LENGTH_SHORT)
                    .show()
            },
            onDoubleClick = {
                Toast
                    .makeText(context, "combinedClickable Box 双击", Toast.LENGTH_SHORT)
                    .show()
            }
        ))
}