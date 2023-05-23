package life.lixiaoyu.composetetris

import androidx.compose.foundation.Canvas
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.clipRect
import life.lixiaoyu.composetetris.model.Sprite
import kotlin.math.min

/**
 * 绘制单个块
 */
private fun DrawScope.drawBrick(
    brickSize: Float, // 每个方块的 Size
    offset: Offset,   // 每个方块的偏移
    color: Color,     // 每个方块的颜色
) {
    val location = Offset(offset.x * brickSize, offset.y * brickSize)
    val outerBoxSize = brickSize * 0.8F
    val outerBoxOffset = (brickSize - outerBoxSize) / 2
    drawRect(
        color,
        topLeft = location + Offset(outerBoxOffset, outerBoxOffset),
        size = Size(outerBoxSize, outerBoxSize),
        style = Stroke(outerBoxSize / 10)
    )
    val innerBoxSize = brickSize * 0.5F
    val innerBoxOffset = (brickSize - innerBoxSize) / 2
    drawRect(
        color,
        topLeft = location + Offset(innerBoxOffset, innerBoxOffset),
        size = Size(innerBoxSize, innerBoxSize)
    )
}

fun DrawScope.drawMatrix(
    brickSize: Float,
    matrix: Pair<Int, Int>
) {
    (0 until matrix.first).forEach { x ->
        (0 until matrix.second).forEach { y ->
            drawBrick(brickSize, Offset(x.toFloat(), y.toFloat()), Color.LightGray)
        }
    }
}

fun DrawScope.drawSprite(
    sprite: Sprite,
    brickSize: Float,
    matrix: Pair<Int, Int>
) {
    clipRect(0F, 0F, matrix.first * brickSize, matrix.second * brickSize) {
        val shape = sprite.getShape()
        for (i in 0 until Sprite.SIZE) {
            for (j in 0 until Sprite.SIZE) {
                val value = shape[i][j]
                if (value == 1) {
                    drawBrick(brickSize, Offset(sprite.offset.x + i, sprite.offset.y + j), Color.Black)
                }
            }
        }
    }
}

@Composable
fun BrickMatrix(
    modifier: Modifier = Modifier,
    viewState: ViewState
) {
    Canvas(
        modifier = modifier,
        onDraw = {
            val matrix = viewState.matrix
            val brickSize = min(size.width / matrix.first, size.height / matrix.second)
            drawMatrix(brickSize, matrix)
            drawSprite(viewState.sprite, brickSize, matrix)
        })
}