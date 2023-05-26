package life.lixiaoyu.composetetris

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.geometry.Offset
import androidx.lifecycle.ViewModel
import life.lixiaoyu.composetetris.model.*
import kotlin.random.Random

class TetrisViewModel : ViewModel() {

    private val _viewState: MutableState<ViewState> = mutableStateOf(ViewState())
    val viewState: State<ViewState> = _viewState

    fun dispatch(action: Action) {
        when (action) {
            Action.Reset -> {
                val sprite = generateRandomSprite()
                val nextSprite = generateRandomSprite()
                emit(_viewState.value.copy(sprite = sprite, nextSprite = nextSprite))
            }
            is Action.Move -> {
                val curSprite = _viewState.value.sprite
                val updatedSprite = curSprite.moveBy(action.direction.toOffset())
                if (updatedSprite.isValidInMatrix(_viewState.value.matrix)) {
                    emit(_viewState.value.copy(sprite = updatedSprite))
                } else if (action.direction == Direction.DOWN) {
                    // 如果方向是向下，然后不能再往下了，说明和 Matrix 中已有的 brick 碰撞了
                    val newMatrix = _viewState.value.matrix.copyOf()
                    val nextSprite = generateRandomSprite()
                    emit(_viewState.value.copy(
                        matrix = curSprite.addToMatrix(newMatrix),
                        sprite = _viewState.value.nextSprite,
                        nextSprite = nextSprite
                    ))
                }
            }
            Action.Rotate -> {
                val curSprite = _viewState.value.sprite
                val updatedSprite = curSprite.rotate()
                if (updatedSprite.isValidInMatrix(_viewState.value.matrix)) {
                    emit(_viewState.value.copy(sprite = updatedSprite))
                }
            }
            Action.GameTick -> {
                dispatch(Action.Move(direction = Direction.DOWN))
//                val curSprite = _viewState.value.sprite
//                val updatedSprite = curSprite.moveBy(Direction.DOWN.toOffset())
//                if (updatedSprite.isValidInMatrix(_viewState.value.matrix)) {
//                    emit(_viewState.value.copy(sprite = updatedSprite))
//                }
            }
            else -> {}
        }
    }

    private fun emit(state: ViewState) {
        _viewState.value = state
    }

    private fun generateRandomSprite(): Sprite {
        return when(val randomIndex = Random.nextInt(Sprite.TYPE_COUNT)) {
            0 -> Sprite(shapeType = SpriteZ, offset = Offset((MatrixWidth / 2).toFloat(), 0F))
            1 -> Sprite(shapeType = SpriteN, offset = Offset((MatrixWidth / 2).toFloat(), 0F))
            2 -> Sprite(shapeType = SpriteI, offset = Offset((MatrixWidth / 2).toFloat(), 0F))
            3 -> Sprite(shapeType = SpriteT, offset = Offset((MatrixWidth / 2).toFloat(), 0F))
            4 -> Sprite(shapeType = SpriteO, offset = Offset((MatrixWidth / 2).toFloat(), 0F))
            5 -> Sprite(shapeType = SpriteL, offset = Offset((MatrixWidth / 2).toFloat(), 0F))
            6 -> Sprite(shapeType = SpriteJ, offset = Offset((MatrixWidth / 2).toFloat(), 0F))
            else -> throw IllegalStateException("Unsupported index: $randomIndex")
        }
    }
}

const val MatrixWidth = 12
const val MatrixHeight = 24

data class ViewState(
    val bricks: List<Brick> = emptyList(),
    val sprite: Sprite = Sprite.Empty,
    val nextSprite: Sprite = Sprite.Empty,
    val matrix: Array<IntArray> = Array(MatrixHeight) { IntArray(MatrixWidth) },
    val gameStatus: GameStatus = GameStatus.OnBoard,
    val score: Int = 0,
)

sealed class Action {
    data class Move(val direction: Direction) : Action() // 向左右，下移动
    object Reset : Action()       // 重新开始
    object Pause : Action()       // 暂停或恢复游戏
    object Rotate : Action()      // 旋转
    object Drop : Action()        // 点击 UP，直接掉落
    object Mute : Action()        // 静音/取消静音
    object GameTick : Action()    // 砖块下落通知
}

/**
 * 最小单元：小砖块
 */
data class Brick(
    val location: Offset = Offset(0F, 0F)
)

enum class GameStatus {
    OnBoard,
    Running,
    LineClearing,
    Paused,
    ScreenClearing,
    GameOver
}

