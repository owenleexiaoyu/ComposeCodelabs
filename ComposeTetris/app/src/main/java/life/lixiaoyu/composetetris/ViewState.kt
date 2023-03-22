package life.lixiaoyu.composetetris

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.geometry.Offset
import androidx.lifecycle.ViewModel
import kotlin.random.Random

class TetrisViewModel : ViewModel() {

    private val _viewState: MutableState<ViewState> = mutableStateOf(ViewState())
    val viewState: State<ViewState> = _viewState

    fun dispatch(action: Action) {
        when (action) {
            Action.Reset -> {
                val sprite = generateRandomSprite()
                val nextSprite = generateRandomSprite()
                emit(_viewState.value.copy(sprite = sprite, spriteReserve = listOf(nextSprite)))
            }
            is Action.Move -> {
                val curSprite = _viewState.value.sprite
                val updatedSprite = curSprite.moveBy(action.direction.toOffset())
                // TODO 加边界判断
                emit(_viewState.value.copy(sprite = updatedSprite))
            }
            Action.Rotate -> {
                val curSprite = _viewState.value.sprite
            }
            else -> {}
        }
        reduce(_viewState.value, action)
    }

    private fun reduce(state: ViewState, action: Action) {
        when(action) {
            Action.GameTick -> {
                val sprite = state.sprite.moveBy(Direction.DOWN.toOffset())
                emit(state.copy(sprite = sprite))
            }
            else -> {}
        }
    }

    private fun emit(state: ViewState) {
        _viewState.value = state
    }

    private fun generateRandomSprite(): Sprite {
        val randomIndex = Random.nextInt(SpriteType.size)
        return Sprite(SpriteType[randomIndex], Offset((MatrixWidth / 2).toInt().toFloat(), 0F))
    }
}

const val MatrixWidth = 12
const val MatrixHeight = 24

data class ViewState(
    val bricks: List<Brick> = emptyList(),
    val sprite: Sprite = Empty,
    val spriteReserve: List<Sprite> = emptyList(),
    val matrix: Pair<Int, Int> = MatrixWidth to MatrixHeight,
    val gameStatus: GameStatus = GameStatus.OnBoard,
    val score: Int = 0,
)

sealed class Action {
    data class Move(val direction: Direction): Action() // 向左右，下移动
    object Reset: Action()       // 重新开始
    object Pause: Action()       // 暂停或恢复游戏
    object Rotate: Action()      // 旋转
    object Drop: Action()        // 点击 UP，直接掉落
    object Mute: Action()        // 静音/取消静音
    object GameTick: Action()    // 砖块下落通知
}

data class Brick(
    val location: Offset = Offset(0F, 0F)
)

data class Sprite(
    val shape: List<Offset> = emptyList(),     // 砖块形状
    val offset: Offset = Offset(0F, 0F)  // 砖块整体在 Matrix 中的偏移
) {
    val location: List<Offset> = shape.map { it + offset }   // 计算出砖块各个点在 Matrix 中的位置

    fun moveBy(offset: Offset): Sprite {
        return Sprite(this.shape, this.offset + offset)
    }

    fun rotate(): Sprite {
        return Sprite(shape.map { -it }, offset)
    }
}

val Empty = Sprite()

val SpriteType = listOf(
    listOf(Offset(1F, -1F), Offset(1F, 0F), Offset(0F, 0F), Offset(0F, 1F)),   // Z
    listOf(Offset(0F, -1F), Offset(0F, 0F), Offset(1F, 0F), Offset(1F, 1F)),   // S
    listOf(Offset(0F, -1F), Offset(0F, 0F), Offset(0F, 1F), Offset(0F, 2F)),   // I
    listOf(Offset(0F, 1F), Offset(0F, 0F), Offset(0F, -1F), Offset(1F, 0F)),   // T
    listOf(Offset(1F, 0F), Offset(0F, 0F), Offset(1F, -1F), Offset(0F, -1F)),   // O
    listOf(Offset(0F, -1F), Offset(1F, -1F), Offset(1F, 0F), Offset(1F, 1F)),   // L
    listOf(Offset(1F, -1F), Offset(0F, -1F), Offset(0F, 0F), Offset(0F, 1F)),   // J
)
enum class GameStatus {
    OnBoard,
    Running,
    LineClearing,
    Paused,
    ScreenClearing,
    GameOver
}