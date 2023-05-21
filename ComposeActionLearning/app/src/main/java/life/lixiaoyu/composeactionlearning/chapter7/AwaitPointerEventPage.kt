package life.lixiaoyu.composeactionlearning.chapter7

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.forEachGesture
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.PointerEventPass
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import life.lixiaoyu.composeactionlearning.DescItem
import life.lixiaoyu.composeactionlearning.FullPageWrapper
import life.lixiaoyu.composeactionlearning.VerticalSpacer

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
    onEvent: () -> Unit,
    content: @Composable () -> Unit,
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .size(size)
            .background(color)
            .pointerInput(Unit) {
                awaitPointerEventScope {
                    awaitPointerEvent(pass)
                    onEvent()
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