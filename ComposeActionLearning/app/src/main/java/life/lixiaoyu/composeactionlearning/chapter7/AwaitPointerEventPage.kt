package life.lixiaoyu.composeactionlearning.chapter7

import android.util.Log
import android.widget.Toast
import androidx.compose.animation.core.*
import androidx.compose.animation.splineBasedDecay
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.*
import androidx.compose.ui.input.pointer.util.VelocityTracker
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.Job
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import life.lixiaoyu.composeactionlearning.DescItem
import life.lixiaoyu.composeactionlearning.FullPageWrapper
import life.lixiaoyu.composeactionlearning.VerticalSpacer
import kotlin.math.roundToInt

@Composable
fun AwaitPointerEventPage() {
    FullPageWrapper {
        Column(
            Modifier
                .padding(10.dp)
                .verticalScroll(rememberScrollState())
        ) {
            AwaitPointerEventDemo()
            PointerEventPassDemo1()
            PointerEventPassDemo2()
            PointerEventPassDemo3()
            PointerEventPassDemo4()
            ConsumePointerEventDemo()
            CustomTapGesturesDemo()
            CustomDragGestureDemo1()
            CustomDragGestureDemo2()
            DragFlingDemo()
        }
    }
}

@Composable
fun ColumnScope.AwaitPointerEventDemo() {
    DescItem(title = "awaitPointerEvent 事件之源")
    var logs by remember { mutableStateOf("") }
    Box(
        Modifier
            .size(200.dp)
            .background(Color.Red)
            .pointerInput(Unit) {
                forEachGesture {
                    awaitPointerEventScope {
                        var event = awaitPointerEvent()

                        val msg =
                            "x: ${event.changes[0].position.x}, y: ${event.changes[0].position.y}, type: ${event.type}"
                        println(msg)
                        logs = msg

                        event = awaitPointerEvent()
                        val msg2 =
                            "x: ${event.changes[0].position.x}, y: ${event.changes[0].position.y}, type: ${event.type}"
                        println(msg2)
                        logs = msg2
                    }
                }
            }
    )
    VerticalSpacer(height = 10.dp)
    Text(text = logs)
}

@Composable
fun ColumnScope.PointerEventPassDemo1() {
    DescItem(title = "测试不同 PointerEventPass 的效果，三层 Box 都是 Main")
    var logs by remember { mutableStateOf("") }
    PointerBox(
        size = 300.dp, color = Color.Red, pass = PointerEventPass.Main, onEvent = {
        Log.d("AwaitPointerEventPage", "First layer")
        logs += "First layer\n"
    }) {
        PointerBox(size = 200.dp, color = Color.Blue, pass = PointerEventPass.Main, onEvent = {
            Log.d("AwaitPointerEventPage", "Second layer")
            logs += "Second layer\n"
        }) {
            PointerBox(size = 100.dp, color = Color.Green, pass = PointerEventPass.Main, onEvent = {
                Log.d("AwaitPointerEventPage", "Third layer")
                logs += "Third layer\n"
            }) {

            }
        }
    }
    Text(
        text = logs
    )
}

@Composable
fun ColumnScope.PointerEventPassDemo2() {
    DescItem(title = "测试不同 PointerEventPass 的效果，第一层组件用 Initial，第二层用 Final，第三层用 Main")
    var logs by remember { mutableStateOf("") }
    PointerBox(
        size = 300.dp, color = Color.Red, pass = PointerEventPass.Initial, onEvent = {
            Log.d("AwaitPointerEventPage", "First layer")
            logs += "First layer\n"
        }) {
        PointerBox(size = 200.dp, color = Color.Blue, pass = PointerEventPass.Final, onEvent = {
            Log.d("AwaitPointerEventPage", "Second layer")
            logs += "Second layer\n"
        }) {
            PointerBox(size = 100.dp, color = Color.Green, pass = PointerEventPass.Main, onEvent = {
                Log.d("AwaitPointerEventPage", "Third layer")
                logs += "Third layer\n"
            }) {

            }
        }
    }
    Text(
        text = logs
    )
}

@Composable
fun ColumnScope.PointerEventPassDemo3() {
    DescItem(title = "测试不同 PointerEventPass 的效果，四层")
    var logs by remember { mutableStateOf("") }
    PointerBox(
        size = 300.dp, color = Color.Red, pass = PointerEventPass.Initial, onEvent = {
            Log.d("AwaitPointerEventPage", "First layer")
            logs += "First layer\n"
        }) {
        PointerBox(size = 200.dp, color = Color.Blue, pass = PointerEventPass.Final, onEvent = {
            Log.d("AwaitPointerEventPage", "Second layer")
            logs += "Second layer\n"
        }) {
            PointerBox(size = 100.dp, color = Color.Green, pass = PointerEventPass.Initial, onEvent = {
                Log.d("AwaitPointerEventPage", "Third layer")
                logs += "Third layer\n"
            }) {
                PointerBox(size = 50.dp, color = Color.Yellow, pass = PointerEventPass.Main, onEvent = {
                    Log.d("AwaitPointerEventPage", "Fourth layer")
                    logs += "Fourth layer\n"
                }) {

                }
            }
        }
    }
    Text(
        text = logs
    )
}

@Composable
fun ColumnScope.PointerEventPassDemo4() {
    DescItem(title = "测试不同 PointerEventPass 的效果，六层")
    var logs by remember { mutableStateOf("") }
    PointerBox(
        size = 300.dp, color = Color.Red, pass = PointerEventPass.Initial, onEvent = {
            Log.d("AwaitPointerEventPage", "First layer")
            logs += "First layer\n"
        }) {
        PointerBox(size = 250.dp, color = Color.Blue, pass = PointerEventPass.Final, onEvent = {
            Log.d("AwaitPointerEventPage", "Second layer")
            logs += "Second layer\n"
        }) {
            PointerBox(size = 200.dp, color = Color.Green, pass = PointerEventPass.Initial, onEvent = {
                Log.d("AwaitPointerEventPage", "Third layer")
                logs += "Third layer\n"
            }) {
                PointerBox(size = 150.dp, color = Color.Yellow, pass = PointerEventPass.Main, onEvent = {
                    Log.d("AwaitPointerEventPage", "Fourth layer")
                    logs += "Fourth layer\n"
                }) {
                    PointerBox(size = 100.dp, color = Color.Magenta, pass = PointerEventPass.Final, onEvent = {
                        Log.d("AwaitPointerEventPage", "Fifth layer")
                        logs += "Fifth layer\n"
                    }) {
                        PointerBox(size = 50.dp, color = Color.Gray, pass = PointerEventPass.Main, onEvent = {
                            Log.d("AwaitPointerEventPage", "Sixth layer")
                            logs += "Sixth layer\n"
                        }) {

                        }
                    }
                }
            }
        }
    }
    Text(
        text = logs
    )
}

@Composable
fun PointerBox(
    size: Dp,
    color: Color,
    pass: PointerEventPass,
    onEvent: AwaitPointerEventScope.(PointerEvent) -> Unit,
    content: @Composable () -> Unit,
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .size(size)
            .background(color)
            .pointerInput(Unit) {
                awaitPointerEventScope {
                    val event = awaitPointerEvent(pass)
                    onEvent(event)
                }
            }
    ) {
        Text(
            text = pass.name,
            color = Color.White,
            modifier = Modifier.align(Alignment.TopStart)
        )
        content()
    }
}

@Composable
fun ColumnScope.ConsumePointerEventDemo() {
    DescItem(title = "测试事件消费状态")
    var logs by remember { mutableStateOf("") }
    PointerBox(
        size = 300.dp, color = Color.Red, pass = PointerEventPass.Initial, onEvent = {
            val msg = "First layer, downChange: ${it.changes[0].isConsumed}"
            Log.d("AwaitPointerEventPage", msg)
            logs += "$msg\n"
        }) {
        PointerBox(size = 200.dp, color = Color.Blue, pass = PointerEventPass.Final, onEvent = {
            val msg = "Second layer, downChange: ${it.changes[0].isConsumed}"
            Log.d("AwaitPointerEventPage", msg)
            logs += "$msg\n"
        }) {
            PointerBox(size = 100.dp, color = Color.Green, pass = PointerEventPass.Main, onEvent = {
                it.changes[0].consume() // 消费手势事件
                val msg = "Third layer, downChange: ${it.changes[0].isConsumed}"
                Log.d("AwaitPointerEventPage", msg)
                logs += "$msg\n"
            }) {

            }
        }
    }
    Text(
        text = logs
    )
}

@Composable
fun ColumnScope.CustomTapGesturesDemo() {
    DescItem(title = "利用底层 API 实现自定义的 onClick 监听")
    val context = LocalContext.current
    Box(
        Modifier
            .size(100.dp)
            .background(Color.Red)
            .myClick {
                Toast
                    .makeText(context, "myClick -> 点击了", Toast.LENGTH_SHORT)
                    .show()
            })
}

private fun Modifier.myClick(onClick: () -> Unit) = this.pointerInput(Unit) {
    forEachGesture {
        awaitPointerEventScope {
            awaitFirstDown()
            while (true) {
                val event = awaitPointerEvent()
                if (event.type == PointerEventType.Move) {
                    val pos = event.changes[0].position
                    if (pos.x < 0 || pos.x > size.width || pos.y < 0 || pos.y > size.height) {
                        // 按下状态时，手指滑动超出边界，不处理
                        break
                    }
                } else if (event.type == PointerEventType.Release && event.changes.size == 1) {
                    // 判断 change.size == 1 是判断只有一根手指抬起，也就是最后一根手指抬起
                    onClick()
                    break
                }
            }
        }
    }
}

@Composable
fun ColumnScope.CustomDragGestureDemo1() {
    DescItem(title = "使用 drag 挂起函数实现自定义拖动效果")
    var offset by remember { mutableStateOf(Offset.Zero) }
    Box(
        Modifier
            .fillMaxWidth()
            .height(300.dp),
        contentAlignment = Alignment.Center
    ) {
        Box(
            Modifier
                .size(40.dp)
                .offset { IntOffset(offset.x.roundToInt(), offset.y.roundToInt()) }
                .background(Color.Red)
                .pointerInput(Unit) {
                    forEachGesture {
                        awaitPointerEventScope {
                            // 获取第一根手指的 DOWN 事件
                            var downEvent = awaitFirstDown()
                            // 根据手指标识跟踪拖动手势
                            drag(downEvent.id) {
                                offset += it.positionChange()
                            }
                        }
                    }
                }
        )
    }
}

@Composable
fun ColumnScope.CustomDragGestureDemo2() {
    DescItem(title = "使用 awaitDragOrCancellation 挂起函数实现自定义拖动效果")
    var offset by remember { mutableStateOf(Offset.Zero) }
    Box(
        Modifier
            .fillMaxWidth()
            .height(300.dp),
        contentAlignment = Alignment.Center
    ) {
        Box(
            Modifier
                .size(40.dp)
                .offset { IntOffset(offset.x.roundToInt(), offset.y.roundToInt()) }
                .background(Color.Blue)
                .pointerInput(Unit) {
                    forEachGesture {
                        awaitPointerEventScope {
                            // 获取第一根手指的 DOWN 事件
                            val downEvent = awaitFirstDown()
                            while (true) {
                                // 根据手指标识跟踪拖动手势
                                val event = awaitDragOrCancellation(downEvent.id)
                                if (event == null) {
                                    // 拖动事件被取消
                                    break
                                }
                                if (event.changedToUp()) {
                                    // 所有手指都已经抬起
                                    break
                                }
                                offset += event.positionChange()
                            }

                        }
                    }
                }
        )
    }
}

@Composable
fun ColumnScope.DragFlingDemo() {
    DescItem(title = "使用 awaitTouchSlopOrCancellation 实现 Fling 效果")
    var offset = remember { Animatable(Offset.Zero, Offset.VectorConverter) }
    var horizontalVelocity by remember { mutableStateOf(0f) }
    var verticalVelocity by remember { mutableStateOf(0f) }
    Box(
        modifier = Modifier
            .size(300.dp)
            .background(Color.Gray)
            .pointerInput(Unit) {
                offset.updateBounds(lowerBound = Offset.Zero, upperBound = Offset(250.dp.toPx(), 250.dp.toPx()))
                coroutineScope {
                    forEachGesture {
                        awaitPointerEventScope {
                            val down = awaitFirstDown()
                            launch { offset.stop() }
                            var validDrag: PointerInputChange?
                            do {
                                validDrag = awaitTouchSlopOrCancellation(down.id) { change, overSlop ->
                                    if (change.positionChange() != Offset.Zero) {
                                        change.consume()
                                    }
                                }
                            } while (validDrag != null && !validDrag.isConsumed)
                            if (validDrag != null) {
                                val velocityTracker = VelocityTracker()
                                var dragAnimJob: Job?  = null
                                drag(validDrag.id) {
                                    dragAnimJob = launch {
                                        offset.snapTo(offset.value + it.positionChange())
                                        velocityTracker.addPosition(it.uptimeMillis, it.position)
                                        horizontalVelocity = velocityTracker.calculateVelocity().x
                                        verticalVelocity = velocityTracker.calculateVelocity().y
                                    }
                                }
                                horizontalVelocity = velocityTracker.calculateVelocity().x
                                verticalVelocity = velocityTracker.calculateVelocity().y
                                val decay = splineBasedDecay<Offset>(this)
                                val targetOffset = decay.calculateTargetValue(
                                    Offset.VectorConverter,
                                    offset.value,
                                    Offset(horizontalVelocity, verticalVelocity)
                                ).run {
                                    Offset(x.coerceIn(0f, 250.dp.toPx()), y.coerceIn(0f, 250.dp.toPx()))
                                }
                                dragAnimJob?.cancel()
                                launch {
                                    offset.animateTo(targetOffset, tween(2000, easing = LinearOutSlowInEasing))
                                }
                            }
                        }
                    }
                }
            },
    ) {
        Box(modifier = Modifier
            .size(50.dp)
            .offset {
                IntOffset(offset.value.x.roundToInt(), offset.value.y.roundToInt())
            }
            .clip(RoundedCornerShape(25.dp))
            .background(Color.Magenta)
        )
        Text(
            text = "Velocity: horizontal = %.2f, vertical = %.2f".format(horizontalVelocity, verticalVelocity),
            color = Color.White,
            modifier = Modifier.align(Alignment.BottomStart)
        )
    }
}