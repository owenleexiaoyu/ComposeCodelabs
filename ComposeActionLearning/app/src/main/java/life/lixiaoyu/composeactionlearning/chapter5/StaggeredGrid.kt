package life.lixiaoyu.composeactionlearning.chapter5

import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import life.lixiaoyu.composeactionlearning.HorizontalSpacer
import java.util.*
import kotlin.math.max

/**
 * https://www.jianshu.com/p/0eae1ed46b6f
 * https://www.cnblogs.com/minminjy123/p/15089519.html
 * 三行的横向滑动瀑布流
 */

@Composable
fun StaggeredGrid(
    modifier: Modifier = Modifier,
    rows: Int = 3,
    content: @Composable () -> Unit,
) {
    Layout(modifier = modifier, content = content) { measurables, constrints ->
        /**
         * 第 1 步
         */
        // 保存每行的宽度
        val rowsWidth = IntArray(rows){0}
        // 保存每行的高度
        val rowsHeight = IntArray(rows){0}
        val placeables = measurables.mapIndexed { index, measurable ->
            val placeable = measurable.measure(constrints)
            // 这个子组件在哪一行
            val rowIndex = index % rows
            // 每行的宽度是这一行上所有子组件宽度的和
            rowsWidth[rowIndex] += placeable.width
            // 每行的高度是这一行上子组件高度的最大值
            rowsHeight[rowIndex] = max(rowsHeight[rowIndex], placeable.height)
            placeable
        }
        /**
         * 第 2 步
         */
        // width 取三行中宽度最大的值，与 constraints 的 minWidth 和 maxWidth 比较，
        // 在 minWidth 和 maxWidth 之间，返回 width，否则是 minWidth 或 maxWidth
        val width = rowsWidth.maxOrNull()
            ?.coerceIn(constrints.minWidth.rangeTo(constrints.maxWidth))
            ?: constrints.minWidth
        // height 取三行高度之和，与 constraints minHeight 和 maxHeight 比较
        val height = rowsHeight.sum().coerceIn(constrints.minHeight.rangeTo(constrints.maxHeight))
        /**
         * 第 3 步
         */
        // 计算每行元素在 Y 轴上的坐标
        val rowY = IntArray(rows) {0}
        for (i in 1 until rows) {
            rowY[i] = rowY[i - 1] + rowsHeight[i - 1]
        }
        // 用于临时保存每行中每个 Item 的 X 坐标
        val rowX = IntArray(rows){0}
        layout(width, height) {
            placeables.forEachIndexed {index, placeable ->
                val rowIndex = index % rows
                placeable.placeRelative(rowX[rowIndex], rowY[rowIndex])
                rowX[rowIndex] += placeable.width
            }
        }
    }
}

val topics = listOf(
    "Android", "Kotlin","Jetpack Compose", "Java", "Flutter",
    "Dart", "Server development"
)

val colors = listOf(
    Color.Blue,
    Color.Yellow,
    Color.Green,
    Color.Red,
    Color.Cyan,
    Color.Magenta
)

@Composable
fun StaggeredGridPage() {
    Row(
        Modifier
            .padding(10.dp)
            .horizontalScroll(rememberScrollState())
    ) {
        StaggeredGrid {
            topics.forEach {
                val randomColorIndex = Random().nextInt(colors.size)
                TopicCard(text = it, color = colors[randomColorIndex])
            }
        }
    }
}

@Composable
fun TopicCard(
    text: String,
    color: Color = Color.Black,
) {
    Card(elevation = 1.dp, modifier = Modifier.padding(5.dp)) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Box(
                Modifier
                    .size(60.dp)
                    .background(color),
                contentAlignment = Alignment.Center
            ) {
                if (text.isNotEmpty()) {
                    Text(text = text.substring(0, 1), fontWeight = FontWeight.Bold, fontSize = 30.sp, color = Color.White)
                }
            }
            HorizontalSpacer(width = 10.dp)
            Text(text, fontSize = 20.sp, fontWeight = FontWeight.SemiBold)
            HorizontalSpacer(width = 10.dp)
        }
    }
}

