package life.lixiaoyu.composeactionlearning.chapter5

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.*
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import life.lixiaoyu.composeactionlearning.DescItem

/**
 * 自定义固有特性测量
 */

@Composable
fun CustomIntrinsicSizeRowPage() {
    val text1 = "Hello"
    val text2 = "World"
    Column {
        DescItem(title = "IntrinsicRow")
        IntrinsicRow(
            modifier = Modifier
                .fillMaxWidth()
                .height(IntrinsicSize.Min)
                .border(0.5.dp, Color.Blue.copy(alpha = 0.5F))
        ) {
            Text(
                text = text1,
                modifier = Modifier
                    .background(Color.Red.copy(alpha = 0.3F))
                    .padding(start = 5.dp)
                    .wrapContentWidth(Alignment.Start)
                    .layoutId("main")
            )
            Divider(color = Color.Black, modifier = Modifier
                .fillMaxHeight()
                .width(2.dp)
                .layoutId("divider")
            )
            Text(
                text = text2,
                fontSize = 30.sp,
                modifier = Modifier
                    .background(Color.Yellow.copy(alpha = 0.3F))
                    .padding(end = 5.dp)
                    .wrapContentWidth(Alignment.End)
                    .layoutId("main")
            )
        }
        DescItem(title = "MyIntrinsicRow")
        MyIntrinsicRow(
            modifier = Modifier
                .fillMaxWidth()
                .height(IntrinsicSize.Min)
                .border(0.5.dp, Color.Blue.copy(alpha = 0.5F))
        ) {
            Text(
                text = text1,
                modifier = Modifier
                    .background(Color.Red.copy(alpha = 0.3F))
                    .padding(horizontal = 5.dp)
                    .wrapContentWidth(Alignment.Start)
            )
            Divider(color = Color.Black, modifier = Modifier
                .fillMaxHeight()
                .width(2.dp)
            )
            Text(
                text = text2,
                fontSize = 30.sp,
                modifier = Modifier
                    .background(Color.Yellow.copy(alpha = 0.3F))
                    .padding(horizontal = 5.dp)
                    .wrapContentWidth(Alignment.End)
            )
        }
    }
}

@Composable
fun IntrinsicRow(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit,
) {
    Layout(
        content = content,
        modifier = modifier,
        measurePolicy = object : MeasurePolicy {

            override fun MeasureScope.measure(
                measurables: List<Measurable>,
                constraints: Constraints
            ): MeasureResult {
                val mainPlaceables = measurables.filter { it.layoutId == "main" }.map {
                    it.measure(constraints)
                }
                val dividerConstraints= constraints.copy(minWidth = 0)
                val dividerPlaceables = measurables.filter { it.layoutId == "divider" }.map {
                    it.measure(dividerConstraints)
                }
                if (dividerPlaceables.size > 1) {
                    throw IllegalStateException("layoutId divider can only be one")
                }
                val midPos= constraints.maxWidth / 2
                return layout(constraints.maxWidth, constraints.maxHeight) {
                    mainPlaceables.forEach {
                        it.placeRelative(0, 0)
                    }
                    dividerPlaceables.forEach {
                        it.placeRelative(midPos, 0)
                    }
                }
            }

            override fun IntrinsicMeasureScope.minIntrinsicHeight(
                measurables: List<IntrinsicMeasurable>,
                width: Int
            ): Int {
                var maxHeight = 0
                measurables.forEach {
                    maxHeight = it.minIntrinsicHeight(width).coerceAtLeast(maxHeight)
                }
                return maxHeight
            }
        }

    )
}

@Composable
fun MyIntrinsicRow(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit,
) {
    Layout(
        content = content,
        modifier = modifier,
        measurePolicy = object : MeasurePolicy {

            override fun MeasureScope.measure(
                measurables: List<Measurable>,
                constraints: Constraints
            ): MeasureResult {
                val childConstraints = constraints.copy(minWidth = 0)
                val placeables = measurables.map {
                    it.measure(childConstraints)
                }
                return layout(constraints.maxWidth, constraints.maxHeight) {
                    var xOffset = 0
                    placeables.forEach {
                        it.placeRelative(xOffset, 0)
                        xOffset += it.width
                    }
                }
            }

            override fun IntrinsicMeasureScope.minIntrinsicHeight(
                measurables: List<IntrinsicMeasurable>,
                width: Int
            ): Int {
                var maxHeight = 0
                measurables.forEach {
                    maxHeight = it.minIntrinsicHeight(width).coerceAtLeast(maxHeight)
                }
                return maxHeight
            }
        }
    )
}