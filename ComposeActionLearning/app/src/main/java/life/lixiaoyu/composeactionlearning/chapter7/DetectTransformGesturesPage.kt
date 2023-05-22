package life.lixiaoyu.composeactionlearning.chapter7

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.gestures.detectTransformGestures
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.scale
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import life.lixiaoyu.composeactionlearning.DescItem
import life.lixiaoyu.composeactionlearning.FullPageWrapper
import life.lixiaoyu.composeactionlearning.R
import kotlin.math.roundToInt

@Composable
fun DetectTransformGesturesPage() {
    FullPageWrapper {
        Column(Modifier.padding(10.dp)) {
            DetectTransformGesturesDemo()
        }
    }
}

@SuppressLint("LongLogTag")
@Composable
fun ColumnScope.DetectTransformGesturesDemo() {
    DescItem(title = "使用 detectTransformGestures 实现图片缩放")
    var offset by remember { mutableStateOf(Offset.Zero) }
    var rotationAngle by remember { mutableStateOf(0f) }
    var scale by remember { mutableStateOf(1f) }

    var logs by remember { mutableStateOf("") }

    Box(Modifier.fillMaxSize(),
    contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.img_jetpack_compose),
            contentScale = ContentScale.Crop,
            contentDescription = "",
            modifier = Modifier.size(200.dp)
                .rotate(rotationAngle)
                .offset { IntOffset(offset.x.roundToInt(), offset.y.roundToInt()) }
                .scale(scale)
                .pointerInput(Unit) {
                    detectTransformGestures(
                        panZoomLock = true,
                        onGesture = { centroid: Offset, pan: Offset, zoom: Float, rotation: Float ->
                            val msg = "centroid = $centroid, pan = $pan, zoom = $zoom, rotation = $rotation"
                            Log.d("DetectTransformGesturesDemo", msg)
                            logs = msg
                            offset += pan
                            scale *= zoom
                            rotationAngle += rotation
                        }
                    )
                }
        )
        Text(
            text = logs,
            modifier = Modifier.align(Alignment.BottomStart)
        )
    }
}