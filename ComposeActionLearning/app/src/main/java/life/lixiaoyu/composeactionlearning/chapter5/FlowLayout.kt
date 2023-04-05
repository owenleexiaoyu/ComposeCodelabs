package life.lixiaoyu.composeactionlearning.chapter5

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.layout.Placeable
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.google.accompanist.flowlayout.FlowCrossAxisAlignment
import com.google.accompanist.flowlayout.FlowRow
import life.lixiaoyu.composeactionlearning.DescItem


@Composable
fun FlowLayoutPageDemo() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        DescItem(title = "自定义 FlowLayout")
        MyFlowLayout(
            modifier = Modifier
                .fillMaxWidth()
                .background(color = Color.LightGray)
                .padding(10.dp),
            itemSpace = 20.dp,
            lineSpace = 10.dp,
            itemAlignment = Alignment.Bottom
        ) {
            studyTopics.forEachIndexed { index, topic ->
                if (index % 3 == 1) {
                    Chip(modifier = Modifier.height(80.dp), text = topic)
                } else {
                    Chip(text = topic)
                }
            }
        }
        DescItem(title = "accompanist 库提供的 FlowLayout")
        FlowRow(
            modifier = Modifier
                .fillMaxWidth()
                .background(color = Color.Magenta)
                .padding(10.dp),
            crossAxisAlignment = FlowCrossAxisAlignment.Center,
            crossAxisSpacing = 10.dp,
            mainAxisSpacing = 20.dp
        ) {
            studyTopics.forEachIndexed { index, topic ->
                if (index % 3 == 1) {
                    Chip(modifier = Modifier.height(80.dp), text = topic)
                } else {
                    Chip(text = topic)
                }
            }
        }
    }
}

val studyTopics = listOf(
    "Arts & Crafts", "Beauty", "Books", "Business", "Comics", "Culinary",
    "Design", "Fashion", "Film", "History", "Maths", "Music", "People", "Philosophy",
    "Religion", "Social sciences", "Technology", "TV", "Writing"
)

@Composable
fun Chip(modifier: Modifier = Modifier, text: String) {
    Card(
        modifier = modifier,
        border = BorderStroke(color = Color.Black, width = Dp.Hairline),
        shape = RoundedCornerShape(8.dp)
    ) {
        Row(
            modifier = Modifier.padding(start = 8.dp, top = 4.dp, end = 8.dp, bottom = 4.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(16.dp, 16.dp)
                    .background(color = MaterialTheme.colors.secondary)
            )
            Spacer(Modifier.width(4.dp))
            Text(text = text)
        }
    }
}

@Composable
@Preview
fun FlowLayoutPageDemoPreview() {
    FlowLayoutPageDemo()
}

/**
 * 自动换行的 FlowLayout
 */
@Composable
fun MyFlowLayout(
    modifier: Modifier = Modifier,
    itemSpace: Dp = 0.dp,
    lineSpace: Dp = 0.dp,
    itemAlignment: Alignment.Vertical = Alignment.Top,
    content: @Composable () -> Unit,
) {
    Layout(modifier = modifier, content = content) { measurables, constrints ->
        val parentWidth = constrints.maxWidth
        val allPlaceables = mutableListOf<MutableList<Placeable>>()
        // 临时存放每行中的子组件的 Placeable
        var lineViews = mutableListOf<Placeable>()
        // 保存每行的高度
        val lineHeights = mutableListOf<Int>()
        // 临时保存每行的高度
        var lineHeight = 0

        var lineWidth = 0
        var totalHeight = 0

        val childConstraints = Constraints(maxWidth = constrints.maxWidth)
        measurables.forEachIndexed { index, measurable ->
            // 测量子View
            val placeable = measurable.measure(childConstraints)
            val childWidth = placeable.width
            val childHeight = placeable.height
            if (lineWidth + childWidth <= parentWidth) {
                lineWidth += (if (index == 0) childWidth else (childWidth + itemSpace.toPx()
                    .toInt()))
                lineHeight = maxOf(lineHeight, childHeight)
                lineViews.add(placeable)
            } else {
                // 换行的情况
                lineWidth = childWidth
                totalHeight += (lineHeight + lineSpace.toPx().toInt())
                // 记录上一行的高度
                lineHeights.add(lineHeight)
                // 记录新一行的初始高度
                lineHeight = childHeight
                // 将上一行的所有子组件保存列表中
                allPlaceables.add(lineViews)
                // 新建一个列表用于添加新一行的子组件
                lineViews = mutableListOf<Placeable>()
                // 添加新一行的第一个子组件到列表中
                lineViews.add(placeable)
            }
            if (index == measurables.size - 1) {
                totalHeight += lineHeight
                lineHeights.add(lineHeight)
                allPlaceables.add(lineViews)
            }
        }
        /**
         * 摆放子组件位置
         */
        layout(constrints.maxWidth, totalHeight) {
            var topOffset = 0
            var leftOffset = 0
            allPlaceables.forEachIndexed { index, lineViews ->
                lineHeight = lineHeights[index]
                lineViews.forEachIndexed { index, placeable ->
                    val childWidth = placeable.width
                    val childHeight = placeable.height
                    val childTopOffset =
                        getItemTopOffset(itemAlignment, lineHeight, topOffset, childHeight)
                    placeable.placeRelative(leftOffset, childTopOffset)
                    // 更新子项 x 坐标
                    leftOffset += (childWidth + itemSpace.toPx().toInt())
                }
                // 换行，重置子项 x 坐标
                leftOffset = 0
                // 换行，更新子项 y 坐标
                topOffset += (lineHeight + lineSpace.toPx().toInt())
            }
        }
    }
}

private fun getItemTopOffset(
    itemAlignment: Alignment.Vertical,
    lineHeight: Int,
    topOffset: Int,
    childHeight: Int
): Int {
    return when (itemAlignment) {
        Alignment.CenterVertically -> topOffset + (lineHeight - childHeight) / 2
        Alignment.Bottom -> topOffset + lineHeight - childHeight
        else -> topOffset
    }
}