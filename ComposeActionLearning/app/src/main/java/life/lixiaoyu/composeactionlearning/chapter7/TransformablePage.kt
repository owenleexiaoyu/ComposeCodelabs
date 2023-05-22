package life.lixiaoyu.composeactionlearning.chapter7

import androidx.compose.foundation.Image
import androidx.compose.foundation.gestures.rememberTransformableState
import androidx.compose.foundation.gestures.transformable
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.scale
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import life.lixiaoyu.composeactionlearning.DescItem
import life.lixiaoyu.composeactionlearning.FullPageWrapper
import life.lixiaoyu.composeactionlearning.R
import kotlin.math.roundToInt

@Composable
fun TransformablePage() {
    FullPageWrapper {
        Column(Modifier.padding(10.dp)) {
            TransformableDemo()
        }
    }
}

@Composable
fun ColumnScope.TransformableDemo() {
    DescItem(title = "使用 Modifier.transformable 实现多点触控")
    val boxSize = 200.dp
    var offset by remember { mutableStateOf(Offset.Zero) }
    var rotateAngle by remember { mutableStateOf(0f) }
    var scale by remember { mutableStateOf(1f) }
    val transformableState = rememberTransformableState {
            zoomChange: Float, panChange: Offset, rotationChange: Float ->
        scale *= zoomChange
        offset += panChange
        rotateAngle += rotationChange
    }
    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Image(
            painter = painterResource(id = R.drawable.img_jetpack_compose),
            contentDescription = "",
            contentScale = ContentScale.Crop,
            modifier = Modifier.size(boxSize)
                .rotate(rotateAngle)
                .offset { IntOffset(offset.x.roundToInt(), offset.y.roundToInt()) }
                .scale(scale)
                .transformable(
                    state = transformableState,
                    lockRotationOnZoomPan = false
                )
        )
    }
}