package life.lixiaoyu.composeactionlearning.chapter5

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.SubcomposeLayout
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

/**
 * 使用 SubcomposeLayout 实现两个 Text 中间 Divider 的实例
 */

@Composable
fun SubcomposeLayoutTwoTextsPage() {
    val text1 = "Hello"
    val text2 = "World"
    SubcomposeRow(text = {
        Text(
            text = text1,
            modifier = Modifier
                .background(Color.Red.copy(alpha = 0.3F))
                .padding(start = 5.dp)
                .wrapContentWidth(Alignment.Start)
        )
        Text(
            text = text2,
            fontSize = 30.sp,
            modifier = Modifier
                .background(Color.Yellow.copy(alpha = 0.3F))
                .padding(end = 5.dp)
                .wrapContentWidth(Alignment.End)
        )
    }) {
        val heightDp = with(LocalDensity.current) { it.toDp() }
        Divider(color = Color.Black, modifier = Modifier
            .width(2.dp)
            .height(heightDp)
        )
    }
}

@Composable
fun SubcomposeRow(
    modifier: Modifier = Modifier,
    text: @Composable () -> Unit,
    divider: @Composable (Int) -> Unit,
) {
    SubcomposeLayout(modifier = modifier) { constraints ->
        var maxHeight = 0
        var placeables = subcompose("text", text).map {
            val placeable = it.measure(constraints)
            maxHeight = placeable.height.coerceAtLeast(maxHeight)
            placeable
        }
        var dividerPlaceable = subcompose("divider") {
            divider(maxHeight)
        }.map {
            it.measure(constraints.copy(minWidth = 0))
        }
        assert(dividerPlaceable.size == 1, { "DividerScope Error!" })
        val midPos = constraints.maxWidth / 2
        layout(constraints.maxWidth, constraints.maxHeight) {
            placeables.forEach {
                it.placeRelative(0, 0)
            }
            dividerPlaceable.forEach {
                it.placeRelative(midPos, 0)
            }
        }
    }
}