package life.lixiaoyu.composeactionlearning.chapter5

import android.graphics.Bitmap
import android.graphics.BitmapShader
import android.graphics.Shader
import androidx.annotation.FloatRange
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.material.Slider
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.drawscope.translate
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.unit.dp
import androidx.core.graphics.scale
import androidx.core.graphics.transform
import life.lixiaoyu.composeactionlearning.R
import kotlin.math.min
import kotlin.math.roundToInt
import kotlin.math.sin

@Composable
fun WaveLoadingDemoInBook() {
    var _progress by remember { mutableStateOf(0.5f) }
    var _velocity by remember { mutableStateOf(1.0f) }
    var _amplitude by remember { mutableStateOf(0.2f) }

    val size = LocalDensity.current.run {
        200.dp.toPx().roundToInt()
    }
    val _bitmap = ImageBitmap.imageResource(id = R.drawable.logo_nba)
        .asAndroidBitmap().scale(size, size)

    Column {
        Box(
            Modifier
                .weight(1f)
                .align(Alignment.CenterHorizontally)
        ) {
            WaveLoadingInBook(
                Modifier
                    .size(200.dp)
                    .clipToBounds()
                    .align(Alignment.Center),
                WaveConfig(_progress, _amplitude, _velocity),
                bitmap = _bitmap
            )

        }

        LabelSlider(
            label = "Progress",
            value = _progress,
            onValueChange = { _progress = it },
            range = 0f..1f
        )

        LabelSlider(
            label = "Velocity",
            value = _velocity,
            onValueChange = { _velocity = it },
            range = 0f..1f
        )

        LabelSlider(
            label = "Amplitude",
            value = _amplitude,
            onValueChange = { _amplitude = it },
            range = 0f..1f
        )
    }
}


@Composable
private fun LabelSlider(
    label: String,
    value: Float,
    onValueChange: (Float) -> Unit,
    range: ClosedFloatingPointRange<Float>
) {
    Row(Modifier.padding(start = 10.dp, end = 10.dp)) {
        Text(
            label, modifier = Modifier
                .width(100.dp)
                .align(Alignment.CenterVertically)
        )
        Slider(
            modifier = Modifier.align(Alignment.CenterVertically),
            value = value,
            onValueChange = onValueChange,
            valueRange = range
        )
    }
}

private const val defaultAmlitude = 0.2f
private const val defaultVelocity = 1.0f
private const val waveDuration = 2000

data class WaveConfig(
    @FloatRange(from = 0.0, to = 1.0) val progress: Float = 0f,
    @FloatRange(from = 0.0, to = 1.0) val amplitude: Float = defaultAmlitude,
    @FloatRange(from = 0.0, to = 1.0) val velocity: Float = defaultVelocity
)


@Composable
fun WaveLoadingInBook(
    modifier: Modifier = Modifier,
    waveConfig: WaveConfig,
    bitmap: Bitmap,
) {
    val transition = rememberInfiniteTransition()
    val animates = listOf(
        1f, 0.75f, 0.5f
    ).map {
        transition.animateFloat(
            initialValue = 0f, targetValue = 1f, animationSpec = infiniteRepeatable(
                animation = tween((it * waveDuration).roundToInt()),
                repeatMode = RepeatMode.Restart
            )
        )
    }

    Canvas(modifier.fillMaxSize()) {

        // 方式一 ：使用 DrawScope API
        drawWave(
            bitmap.asImageBitmap(),
            waveConfig,
            animates
        )

        // 方式二：使用 Canvas API
//        drawWaveWithCanvas(
//            bitmap,
//            waveConfig,
//            animates
//        )
    }

}


private fun DrawScope.drawWave(
    imageBitmap: ImageBitmap,
    waveConfig: WaveConfig,
    animates: List<State<Float>>,
) {

    drawImage(image = imageBitmap, colorFilter = run {
        val cm = ColorMatrix().apply { setToSaturation(0f) }
        ColorFilter.colorMatrix(cm)
    })

    animates.forEachIndexed { index, anim ->

        val maxWidth = 2 * size.width / waveConfig.velocity.coerceAtLeast(0.1f)
        val offsetX = maxWidth / 2 * (1 - anim.value)

        translate(-offsetX) {
            drawPath(
                path = buildWavePathInBook(
                    width = maxWidth,
                    height = size.height,
                    amplitude = size.height * waveConfig.amplitude,
                    progress = waveConfig.progress
                ), brush = ShaderBrush(ImageShader(imageBitmap).apply {
                    transform { postTranslate(offsetX, 0f) }
                }), alpha = if (index == 0) 1f else 0.5f
            )
        }

    }

}


private fun DrawScope.drawWaveWithCanvas(
    bitmap: Bitmap,
    waveConfig: WaveConfig,
    animates: List<State<Float>>
) {

    val forePaint = Paint().apply {
        shader = BitmapShader(
            bitmap,
            Shader.TileMode.CLAMP,
            Shader.TileMode.CLAMP
        )
    }

    val backPaint = Paint().apply {
        shader = BitmapShader(
            bitmap,
            Shader.TileMode.CLAMP,
            Shader.TileMode.CLAMP
        )
        colorFilter = run {
            val cm = ColorMatrix().apply { setToSaturation(0f) }
            ColorFilter.colorMatrix(cm)
        }
    }

    drawIntoCanvas { canvas ->

        canvas.drawRect(0f, 0f, size.width, size.height, backPaint)

        animates.forEachIndexed { index, anim ->

            canvas.withSave {

                val maxWidth = 2 * size.width / waveConfig.velocity.coerceAtLeast(0.1f)
                val offsetX = maxWidth / 2 * (1 - anim.value)

                canvas.translate(-offsetX, 0f)

                forePaint.shader?.transform {
                    setTranslate(offsetX, 0f)
                }

                canvas.drawPath(
                    buildWavePathInBook(
                        width = maxWidth,
                        height = size.height,
                        amplitude = size.height * waveConfig.amplitude,
                        progress = waveConfig.progress
                    ), forePaint.apply {
                        alpha = if (index == 0) 1f else 0.5f
                    }
                )
            }

        }
    }

}


private fun buildWavePathInBook(
    dp: Float = 3f,
    width: Float,
    height: Float,
    amplitude: Float,
    progress: Float
): Path {

    //调整振幅，振幅不大于剩余空间
    var adjustHeight = min(height * Math.max(0f, 1 - progress), amplitude)

    return Path().apply {
        reset()
        moveTo(0f, height)
        lineTo(0f, height * (1 - progress))
        if (progress > 0f && progress < 1f) {
            if (adjustHeight > 0) {
                var x = dp
                while (x < width) {
                    lineTo(
                        x, height * (1 - progress) - adjustHeight / 2f * sin(4.0 * Math.PI * x / width)
                            .toFloat()
                    )
                    x += dp
                }
            }
        }
        lineTo(width, height * (1 - progress))
        lineTo(width, height)
        close()
    }
}