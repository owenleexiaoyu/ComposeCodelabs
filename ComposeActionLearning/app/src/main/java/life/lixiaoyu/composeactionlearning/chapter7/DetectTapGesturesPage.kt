package life.lixiaoyu.composeactionlearning.chapter7

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import life.lixiaoyu.composeactionlearning.DescItem
import life.lixiaoyu.composeactionlearning.FullPageWrapper

private const val TAG = "DetectTapGesturesPage"

@Composable
fun DetectTapGesturesPage() {
    var logs by remember { mutableStateOf("") }
    FullPageWrapper {
        Column(Modifier.padding(10.dp)) {
            DescItem(title = "使用 detectTapGestures 监听点击事件")
            Box(
                Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .background(Color.Blue)
                    .pointerInput(Unit) {
                        detectTapGestures(
                            onDoubleTap = { offset: Offset ->
                                val msg = "双击，$offset"
                                Log.d(TAG, msg)
                                logs += (msg + "\n")
                            },
                            onLongPress = { offset: Offset ->
                                val msg = "长按，$offset"
                                Log.d(TAG, msg)
                                logs += (msg + "\n")
                            },
                            onPress = { offset: Offset ->
                                val msg = "按下，$offset"
                                Log.d(TAG, msg)
                                logs += (msg + "\n")
                            },
                            onTap = { offset: Offset ->
                                val msg = "轻触，$offset"
                                Log.d(TAG, msg)
                                logs += (msg + "\n")
                            }
                        )
                    }
            )
            Text(
                text = logs.toString(),
                modifier = Modifier.verticalScroll(rememberScrollState())
            )
        }
    }
}