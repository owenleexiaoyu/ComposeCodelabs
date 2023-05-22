package life.lixiaoyu.composeactionlearning.chapter7

import androidx.compose.foundation.*
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.layout
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.dp
import life.lixiaoyu.composeactionlearning.DescItem
import life.lixiaoyu.composeactionlearning.FullPageWrapper

@Composable
fun ScrollablePage() {
    FullPageWrapper {
        Column(
            Modifier
                .fillMaxSize()
                .padding(10.dp)
        ) {
            HorizontalScrollDemo()
            ModifierScrollableDemo()
            VerticalScrollDemo()
        }
    }
}

@Composable
fun ColumnScope.HorizontalScrollDemo() {
    DescItem(title = "Modifier.horizontalScroll 横向滚动")
    Row(
        Modifier
            .fillMaxWidth()
            .horizontalScroll(rememberScrollState())
    ) {
        Box(
            Modifier
                .size(600.dp, 200.dp)
                .background(Brush.horizontalGradient(listOf(Color.Red, Color.Yellow)))
        )
    }
}

@Composable
fun ColumnScope.VerticalScrollDemo() {
    DescItem(title = "Modifier.verticalScroll 横向滚动")
    Box(
        Modifier
            .fillMaxHeight()
            .verticalScroll(rememberScrollState())
    ) {
        Box(
            Modifier
                .fillMaxWidth()
                .height(1000.dp)
                .background(Brush.verticalGradient(listOf(Color.Green, Color.Blue)))
        )
    }
}

@Composable
fun ColumnScope.ModifierScrollableDemo() {
    DescItem(title = "使用 Modifier.scrollable 实现类似 horizontalScroll 效果")
    val scrollState = rememberScrollState()
    Row(
        Modifier
            .border(BorderStroke(1.dp, Color.Blue))
            .height(50.dp)
//            .offset(x = with(LocalDensity.current) {
                    // 滚动位置增大，表示是左滑，所以 offsetX 应该设为负数
//                  (-scrollState.value).toDp()
//            })
            .clipScrollableContainer(Orientation.Horizontal)
            .scrollable(
                scrollState, Orientation.Horizontal, reverseDirection = true,
            )
            .layout { measurable, constraints ->
                // 约束中默认最大宽度为父组件所允许的最大宽度，此处为屏幕宽度，将最大宽度设为无限大
                val childConstraints = constraints.copy(maxWidth = Constraints.Infinity)
                // 使用新约束进行组件测量
                val placeable = measurable.measure(childConstraints)
                // 计算当前组件宽度与父组件所允许的最大宽度中取一个最小值
                // 如果组件超出屏幕，此时 width 为屏幕宽度，如果没有超出，则为组件原本的宽度
                val width = placeable.width.coerceAtMost(constraints.maxWidth)
                // 计算当前组件高度与父组件所允许的最大高度中取一个最小值
                val height = placeable.height.coerceAtMost(constraints.maxHeight)
                // 计算可滚动的距离
                // 假设 width 是屏幕宽度 x，placeable.width 是 2x，则 scrollDistance = x，可以向左滑动一个屏幕宽度
                val scrollDistance = placeable.width - width
                // horizontalScroll 等源码中，给 scrollState 设置了 maxValue，就是为了在计算出最大可滚动距离后，
                // 给 scrollState 设置最大值，防止 scrollState 的 value 还会继续变大。

                layout(width, height) {
                    // 根据可滚动的距离来计算滚动位置
                    val scroll = scrollState.value.coerceIn(0, scrollDistance)
                    // 根据滚动位置得到实际组件偏移量
                    val offsetX = -scroll
                    // 对组件内容完成布局
                    placeable.placeRelativeWithLayer(offsetX, 0)
                }
            }
    ) {
        repeat(50) {
            Text("item $it", modifier = Modifier.padding(10.dp))
            Divider(Modifier.width(1.dp).fillMaxHeight().background(Color.Red))
        }
    }
    Text(text = "scrollState.value: ${scrollState.value}")
}