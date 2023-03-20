package life.lixiaoyu.composeactionlearning.chapter5

import androidx.annotation.FloatRange
import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.translate
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.unit.dp
import androidx.core.graphics.scale
import androidx.core.graphics.transform
import life.lixiaoyu.composeactionlearning.R
import java.lang.Float.max
import java.lang.Float.min
import kotlin.math.roundToInt
import kotlin.math.sin

/**
 * 水波动画
 */
@Composable
fun WaveLoadingPage() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        WaveLoading(waveConfig = WaveConfiguration(0.5F, 0.5F, 1F))
    }
}

data class WaveConfiguration(
    @FloatRange(from = 0.0, to = 1.0) val progress: Float = 0F,
    @FloatRange(from = 0.0, to = 1.0) val amplitude: Float = 0.2F,
    @FloatRange(from = 0.0, to = 1.0) val velocity: Float = 1F
)

private const val WAVE_DURATION = 2000

@Composable
fun WaveLoading(
    modifier: Modifier = Modifier,
    waveConfig: WaveConfiguration
) {

    val transition = rememberInfiniteTransition()
    val animates = listOf(1F, 0.75F, 0.5F).map {
        transition.animateFloat(
            initialValue = 0F,
            targetValue = 1F,
            animationSpec = infiniteRepeatable(
                animation = tween((it * WAVE_DURATION).roundToInt()),
                repeatMode = RepeatMode.Restart
            )
        )
    }

    val imageSize = LocalDensity.current.run {
        200.dp.toPx().roundToInt()
    }
    val imageBitmap = ImageBitmap.imageResource(id = R.drawable.nba_logo).asAndroidBitmap()
        .scale(imageSize, imageSize)
    Canvas(modifier = modifier.size(200.dp).clipToBounds()) {
        // 绘制背景图片
        drawImage(
            image = imageBitmap.asImageBitmap(),
            colorFilter = kotlin.run {
                val cm = ColorMatrix().apply { setToSaturation(0F) }
                ColorFilter.colorMatrix(cm)
            }
        )
        // 绘制波浪
        animates.forEachIndexed { index, anim ->
            val maxWidth = size.width * 2  / waveConfig.velocity.coerceAtLeast(0.1F)
            val offsetX = maxWidth / 2 * (1 - anim.value)
            translate(-offsetX) {
                drawPath(
                    path = buildWavePath(
                        width = maxWidth,
                        height = size.height,
                        amplitude = size.height * waveConfig.amplitude,
                        progress = waveConfig.progress
                    ),
                    brush = ShaderBrush(ImageShader(imageBitmap.asImageBitmap()).apply {
                        transform { postTranslate(offsetX, 0F) }
                    }),
                    alpha = if (index == 0) 1F else 0.5F
                )
            }
        }

    }


}

// 构造波浪路径
fun buildWavePath(
    dp: Float = 3F,
    width: Float,
    height: Float,
    amplitude: Float,
    progress: Float,
): Path {
    // 调整振幅，振幅不大于剩余空间
    val adjustHeight = min(height * max(0F, 1 - progress), amplitude)
    return Path().apply {
        reset()
        moveTo(0F, height)
        lineTo(0F, height * (1 - progress))
        if (progress > 0F && progress < 1F) {
            if (adjustHeight > 0) {
                var x = dp
                while (x < width) {
                    lineTo(
                        x, height * (1 - progress) - adjustHeight / 2F * sin(4.0F * Math.PI * x / width)
                            .toFloat()
                    )
                    x += dp
                }
            }
        }
        lineTo(width,height * (1 - progress))
        lineTo(width, height)
        close()
    }
}