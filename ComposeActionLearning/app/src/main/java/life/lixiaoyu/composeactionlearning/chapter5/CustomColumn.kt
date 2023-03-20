package life.lixiaoyu.composeactionlearning.chapter5

import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import life.lixiaoyu.composeactionlearning.FullPageWrapper

@Composable
fun CustomColumnPage() {
    CustomColumn(modifier = Modifier.padding(20.dp)) {
        Text("This is a CustomColumn")
        Text("Contents in it place vertically")
        Text("Just like Column")
    }
}

@Preview
@Composable
fun CustomColumnPreview() {
    FullPageWrapper {
        CustomColumnPage()
    }
}

@Composable
fun CustomColumn(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    Layout(
        modifier = modifier,
        content = content
    ) { measurables, constraints ->
        val placeables = measurables.map { measurable ->
            // 测量每个组件
            measurable.measure(constraints)
        }
        var yPosition = 0
        layout(constraints.maxWidth, constraints.maxHeight) {
            placeables.forEach { placeable ->
                placeable.placeRelative(x = 0, y = yPosition)
                // 累加上一个组件的高度
                yPosition += placeable.height
            }
        }
    }
}