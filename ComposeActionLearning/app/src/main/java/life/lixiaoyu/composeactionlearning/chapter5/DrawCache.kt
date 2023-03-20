package life.lixiaoyu.composeactionlearning.chapter5

import androidx.compose.animation.core.*
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import life.lixiaoyu.composeactionlearning.R

/**
 * Compose 自定义 View 中创建可缓存对象
 */

@Composable
fun DrawFuwaPage() {
    DrawFuwa()
}

@Composable
fun DrawFuwa() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        val transition = rememberInfiniteTransition()
        val alpha by transition.animateFloat(
            initialValue = 0F,
            targetValue = 1F,
            animationSpec = infiniteRepeatable(
                animation = tween(2000, easing = LinearEasing),
                repeatMode = RepeatMode.Reverse
            )
        )
        val context = LocalContext.current

        Box(
            modifier = Modifier
                .size(340.dp, 300.dp)
                .drawWithCache {
                    val fuwaImage = ImageBitmap.imageResource(context.resources, R.drawable.fuwa)
                    onDrawBehind {
                        drawImage(
                            image = fuwaImage,
                            dstSize = IntSize(drawContext.size.width.toInt(), drawContext.size.height.toInt()),
                            dstOffset = IntOffset.Zero,
                            alpha = alpha
                        )
                    }
                }
        )
    }
}