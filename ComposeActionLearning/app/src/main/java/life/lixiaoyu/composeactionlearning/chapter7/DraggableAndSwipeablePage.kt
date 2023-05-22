package life.lixiaoyu.composeactionlearning.chapter7

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.gestures.rememberDraggableState
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsDraggedAsState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import life.lixiaoyu.composeactionlearning.DescItem
import life.lixiaoyu.composeactionlearning.FullPageWrapper
import kotlin.math.roundToInt

@Composable
fun DraggableAndSwipeablePage() {
    FullPageWrapper {
        Column(Modifier.padding(10.dp)) {
            DraggableDemo()
            SwipeableDemo()
        }
    }
}

@Composable
fun ColumnScope.DraggableDemo() {
    DescItem(title = "使用 Modifier.draggable 实现滑块拖动效果")
    var offsetX by remember { mutableStateOf(0f) }
    val boxSlideLengthDp = 50.dp
    val boxSlideLengthPx = with(LocalDensity.current) {
        boxSlideLengthDp.toPx()
    }
    val draggableState = rememberDraggableState {
        offsetX = (offsetX + it).coerceIn(0f, 3 * boxSlideLengthPx)
    }
    val interactionSource = remember { MutableInteractionSource() }
    Box(
        Modifier
            .size(boxSlideLengthDp * 4, boxSlideLengthDp)
            .background(Color.LightGray)
    ) {
        Box(Modifier
            .size(boxSlideLengthDp)
            .offset { IntOffset(offsetX.roundToInt(), 0) }
            .draggable(
                orientation = Orientation.Horizontal,
                state = draggableState,
                interactionSource = interactionSource
            )
            .background(Color.Red)
        )
    }
    val isDragged by interactionSource.collectIsDraggedAsState()
    Text(if (isDragged) "正在拖动" else "静止")
}

enum class SwitchState {
    CLOSE, OPEN
}


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ColumnScope.SwipeableDemo() {
    DescItem(title = "使用 Modifier.swipeable 实现开关效果")
    val blockSizeDp = 48.dp
    val blockSizePx = with(LocalDensity.current) {
        blockSizeDp.toPx()
    }
    val swipeableState = rememberSwipeableState(initialValue = SwitchState.CLOSE)
    val animatedColor by animateColorAsState(targetValue = if (swipeableState.targetValue == SwitchState.OPEN) Color.Blue else Color.LightGray)

    val anchors = mapOf(
        0f to SwitchState.CLOSE,
        blockSizePx to SwitchState.OPEN
    )
    Box(
        Modifier
            .size(blockSizeDp * 2, blockSizeDp)
            .clip(RoundedCornerShape(blockSizeDp))
            .background(animatedColor)
    ) {
        Box(
            Modifier
                .offset {
                    IntOffset(swipeableState.offset.value.toInt(), 0)
                }
                .swipeable(
                    state = swipeableState,
                    anchors = anchors,
                    thresholds = { from, to ->
                        if (from == SwitchState.CLOSE) { // 从关闭到开启状态时，滑块移动距离超过30%就自动吸附到开启状态
                            FractionalThreshold(0.3f)
                        } else {  // 从开启到关闭状态时，滑块移动距离超过50%才自动吸附到关闭状态
                            FractionalThreshold(0.5f)
                        }
                    },
                    orientation = Orientation.Horizontal
                )
                .size(blockSizeDp)
                .padding(2.dp)
        ) {
            Box(Modifier
                .fillMaxSize()
                .clip(RoundedCornerShape(blockSizeDp))
                .background(Color.White))
        }
    }
}