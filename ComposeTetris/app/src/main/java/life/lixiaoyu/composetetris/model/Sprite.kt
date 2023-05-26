package life.lixiaoyu.composetetris.model

import android.util.Log
import androidx.compose.ui.geometry.Offset
import kotlin.math.roundToInt


data class Sprite(
    val shapeType: SpriteShape = EmptySpriteShape,
    val offset: Offset = Offset.Zero,
    val rotation: Int = 0, // 旋转次数，范围是 0~3
) {

    fun getShape(): Array<IntArray> = shapeType.getShape(rotation)

    fun moveBy(offset: Offset): Sprite {
        return copy(offset = this.offset + offset)
    }

    fun rotate() : Sprite {
        return copy(rotation = (rotation + 1) % 4)
    }

    /**
     * 判断一个 Sprite 是否在矩阵中
     */
    fun isValidInMatrix(matrix: Array<IntArray>): Boolean {
        val height = matrix.size
        val width = matrix[0].size
        val shape = getShape()
        for (i in 0 until SIZE) {
            for (j in 0 until SIZE) {
                val value = shape[i][j]
                if (value == 1) {
                    val offsetX = (offset.x + i).roundToInt()
                    if (offsetX < 0 || offsetX >= width) {
                        return false
                    }
                    val offsetY = (offset.y + j).roundToInt()
                    if (offsetY < 0 || offsetY >= height) {
                        return false
                    }
                    if (matrix[offsetY][offsetX] == 1) {
                        return false
                    }
                }
            }
        }
        return true
    }

    fun addToMatrix(matrix: Array<IntArray>): Array<IntArray> {
        val shape = getShape()
        for (i in 0 until SIZE) {
            for (j in 0 until SIZE) {
                val value = shape[i][j]
                if (value == 1) {
                    val offsetX = (offset.x + i).roundToInt()
                    val offsetY = (offset.y + j).roundToInt()
                    matrix[offsetY][offsetX] = 1
                }
            }
        }
        return matrix
    }

    companion object {
        const val SIZE = 4        // 每个 Sprite 都是 4×4 矩阵
        const val TYPE_COUNT = 7  // 一共有 7 种 Sprite

        val Empty: Sprite = Sprite()
    }
}

interface SpriteShape {
    fun getShape(rotation: Int): Array<IntArray>
}

object EmptySpriteShape : SpriteShape {
    override fun getShape(rotation: Int): Array<IntArray> {
        return Array(4) { IntArray(4) }
    }
}

object SpriteZ : SpriteShape {

    private val defaultShape = arrayOf(
        intArrayOf(1, 1, 0, 0),
        intArrayOf(0, 1, 1, 0),
        intArrayOf(0, 0, 0, 0),
        intArrayOf(0, 0, 0, 0),
    )

    override fun getShape(rotation: Int): Array<IntArray> {
        Log.d("OWEN", "SpriteZ getShape call")
        return if (rotation % 2 == 0) {
            defaultShape
        } else {
            arrayOf(
                intArrayOf(0, 1, 0, 0),
                intArrayOf(1, 1, 0, 0),
                intArrayOf(1, 0, 0, 0),
                intArrayOf(0, 0, 0, 0),
            )
        }
    }
}

object SpriteN : SpriteShape {
    private val defaultShape = arrayOf(
        intArrayOf(0, 1, 1, 0),
        intArrayOf(1, 1, 0, 0),
        intArrayOf(0, 0, 0, 0),
        intArrayOf(0, 0, 0, 0),
    )
    override fun getShape(rotation: Int): Array<IntArray> {
        Log.d("OWEN", "SpriteN getShape call")
        return if (rotation % 2 == 0) {
            defaultShape
        } else {
            arrayOf(
                intArrayOf(1, 0, 0, 0),
                intArrayOf(1, 1, 0, 0),
                intArrayOf(0, 1, 0, 0),
                intArrayOf(0, 0, 0, 0),
            )
        }
    }
}

object SpriteI : SpriteShape {
    private val defaultShape = arrayOf(
        intArrayOf(0, 0, 0, 0),
        intArrayOf(1, 1, 1, 1),
        intArrayOf(0, 0, 0, 0),
        intArrayOf(0, 0, 0, 0),
    )
    override fun getShape(rotation: Int): Array<IntArray> {
        Log.d("OWEN", "SpriteI getShape call")
        return if (rotation % 2 == 0) {
            defaultShape
        } else {
            arrayOf(
                intArrayOf(0, 1, 0, 0),
                intArrayOf(0, 1, 0, 0),
                intArrayOf(0, 1, 0, 0),
                intArrayOf(0, 1, 0, 0),
            )
        }
    }
}

object SpriteT : SpriteShape {
    private val defaultShape = arrayOf(
        intArrayOf(0, 1, 0, 0),
        intArrayOf(1, 1, 1, 0),
        intArrayOf(0, 0, 0, 0),
        intArrayOf(0, 0, 0, 0),
    )
    override fun getShape(rotation: Int): Array<IntArray> {
        Log.d("OWEN", "SpriteT getShape call")
        return when (rotation) {
            1 -> arrayOf(
                intArrayOf(1, 0, 0, 0),
                intArrayOf(1, 1, 0, 0),
                intArrayOf(1, 0, 0, 0),
                intArrayOf(0, 0, 0, 0),
            )
            2 -> arrayOf(
                intArrayOf(1, 1, 1, 0),
                intArrayOf(0, 1, 0, 0),
                intArrayOf(0, 0, 0, 0),
                intArrayOf(0, 0, 0, 0),
            )
            3 -> arrayOf(
                intArrayOf(0, 1, 0, 0),
                intArrayOf(1, 1, 0, 0),
                intArrayOf(0, 1, 0, 0),
                intArrayOf(0, 0, 0, 0),
            )
            else -> defaultShape
        }
    }
}

object SpriteO : SpriteShape {
    private val defaultShape = arrayOf(
        intArrayOf(1, 1, 0, 0),
        intArrayOf(1, 1, 0, 0),
        intArrayOf(0, 0, 0, 0),
        intArrayOf(0, 0, 0, 0),
    )
    override fun getShape(rotation: Int): Array<IntArray> {
        Log.d("OWEN", "SpriteO getShape call")
        return defaultShape
    }
}

object SpriteL : SpriteShape {
    private val defaultShape = arrayOf(
        intArrayOf(0, 0, 1, 0),
        intArrayOf(1, 1, 1, 0),
        intArrayOf(0, 0, 0, 0),
        intArrayOf(0, 0, 0, 0),
    )
    override fun getShape(rotation: Int): Array<IntArray> {
        Log.d("OWEN", "SpriteL getShape call")
        return when (rotation) {
            1 -> arrayOf(
                intArrayOf(1, 0, 0, 0),
                intArrayOf(1, 0, 0, 0),
                intArrayOf(1, 1, 0, 0),
                intArrayOf(0, 0, 0, 0),
            )
            2 -> arrayOf(
                intArrayOf(1, 1, 1, 0),
                intArrayOf(1, 0, 0, 0),
                intArrayOf(0, 0, 0, 0),
                intArrayOf(0, 0, 0, 0),
            )
            3 -> arrayOf(
                intArrayOf(1, 1, 0, 0),
                intArrayOf(0, 1, 0, 0),
                intArrayOf(0, 1, 0, 0),
                intArrayOf(0, 0, 0, 0),
            )
            else -> defaultShape
        }
    }
}

object SpriteJ : SpriteShape {
    private val defaultShape = arrayOf(
        intArrayOf(1, 0, 0, 0),
        intArrayOf(1, 1, 1, 0),
        intArrayOf(0, 0, 0, 0),
        intArrayOf(0, 0, 0, 0),
    )
    override fun getShape(rotation: Int): Array<IntArray> {
        Log.d("OWEN", "SpriteJ getShape call")
        return when (rotation) {
            1 -> arrayOf(
                intArrayOf(1, 1, 0, 0),
                intArrayOf(1, 0, 0, 0),
                intArrayOf(1, 0, 0, 0),
                intArrayOf(0, 0, 0, 0),
            )
            2 -> arrayOf(
                intArrayOf(1, 1, 1, 0),
                intArrayOf(0, 0, 1, 0),
                intArrayOf(0, 0, 0, 0),
                intArrayOf(0, 0, 0, 0),
            )
            3 -> arrayOf(
                intArrayOf(0, 1, 0, 0),
                intArrayOf(0, 1, 0, 0),
                intArrayOf(1, 1, 0, 0),
                intArrayOf(0, 0, 0, 0),
            )
            else -> defaultShape
        }
    }
}
