package life.lixiaoyu.composetetris

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.clipRect
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
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

/**
 * 绘制矩阵
 */
fun DrawScope.drawMatrix(
    brickSize: Float,
    matrix: Array<IntArray>
) {
    val width = matrix[0].size
    val height = matrix.size
    // 注意这里 i 表示矩阵中第 i 行，j 表示矩阵中第 j 列，和 Offset 的 x，y 正好相反
    for (i in 0 until height) {
        for (j in 0 until width) {
            val brickColor = if (matrix[i][j] == 1) Color.Black else Color(0xFF878C77)
            drawBrick(brickSize, Offset(j.toFloat(), i.toFloat()), brickColor)
        }
    }
}

/**
 * 绘制砖块
 */
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
    viewState: ViewState,
) {
    Box(modifier = modifier) {
        if (viewState.gameStatus == GameStatus.OnBoard) {
            Text(
                "TETRIS",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.align(Alignment.Center)
            )
        } else if (viewState.gameStatus == GameStatus.GameOver) {
            Text(
                text = "GAME OVER!",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.align(Alignment.Center)
            )
        } else {
            Canvas(
                modifier = Modifier.fillMaxSize(),
                onDraw = {
                    val matrix = viewState.matrix
                    val matrixSize = matrix[0].size to matrix.size
                    val brickSize = min(size.width / matrixSize.first, size.height / matrixSize.second)
                    drawMatrix(brickSize, matrix)
                    drawSprite(viewState.sprite, brickSize, matrixSize)
                })
        }
    }
}