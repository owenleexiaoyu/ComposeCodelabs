package life.lixiaoyu.composeactionlearning.chapter7

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.PointerInputChange
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import life.lixiaoyu.composeactionlearning.DescItem
import life.lixiaoyu.composeactionlearning.FullPageWrapper
import kotlin.math.roundToInt

@Composable
fun DetectDragGesturesPage() {
    FullPageWrapper {
        Column(Modifier.padding(10.dp)) {
            DetectDragGesturesDemo()
        }
    }
}

@Composable
fun ColumnScope.DetectDragGesturesDemo() {
    DescItem(title = "使用 detectDragGestures 监听拖动事件")
    var offset by remember { mutableStateOf(Offset.Zero) }
    var logs by remember { mutableStateOf("") }
    Box(Modifier.fillMaxSize()) {
        Box(Modifier
            .size(100.dp)
            .offset { IntOffset(x = offset.x.roundToInt(), y = offset.y.roundToInt()) }
            .background(Color.Red)
            .align(Alignment.Center)
            .pointerInput(Unit) {
                detectDragGestures(
                    onDragStart = { offset: Offset ->
                        logs = "拖动开始，$offset"
                    },
                    onDragEnd = {
                        logs = "拖动结束"
                    },
                    onDragCancel = {
                        logs = "拖动取消"
                    },
                    onDrag = { change: PointerInputChange, dragAmount: Offset ->
                        logs = "拖动中，dragAmount = $dragAmount"
                        offset += dragAmount
                    }
                )
            }
        )
        Text(text = logs, modifier = Modifier.align(Alignment.BottomStart))
    }
}