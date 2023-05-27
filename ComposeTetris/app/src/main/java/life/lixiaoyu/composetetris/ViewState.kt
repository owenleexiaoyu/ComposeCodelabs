package life.lixiaoyu.composetetris

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.geometry.Offset
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import life.lixiaoyu.composetetris.model.*

class TetrisViewModel : ViewModel() {

    private val _viewState: MutableState<ViewState> = mutableStateOf(ViewState())
    val viewState: State<ViewState> = _viewState

    fun dispatch(action: Action) {
        reduce(_viewState.value, action)
    }

    private fun reduce(state: ViewState, action: Action) {
        viewModelScope.launch {
            withContext(Dispatchers.Default) {
                when (action) {
                    Action.Reset -> {
                        val sprite = Sprite.generateRandomSprite()
                        val nextSprite = Sprite.generateRandomSprite()
                        emit(state.copy(
                            matrix = Array(MatrixHeight) { IntArray(MatrixWidth) },
                            sprite = sprite,
                            nextSprite = nextSprite,
                            gameStatus = GameStatus.Running,
                            score = 0
                        ))
                    }
                    Action.Pause -> {
                        if (state.isRunning) {
                            emit(state.copy(gameStatus = GameStatus.Paused))
                        }
                    }
                    Action.Resume -> {
                        if (state.isPaused) {
                            emit(state.copy(gameStatus = GameStatus.Running))
                        }
                    }
                    is Action.Move -> run {
                        if (!state.isRunning) {
                            return@run
                        }
                        val curSprite = state.sprite
                        val updatedSprite = curSprite.moveBy(action.direction.toOffset())
                        if (updatedSprite.isValidInMatrix(state.matrix)) {
                            emit(state.copy(sprite = updatedSprite))
                        }
                    }
                    Action.Drop -> run {
                        if (!state.isRunning) {
                            return@run
                        }
                        val curSprite = state.sprite
                        var i = 0
                        while (curSprite.moveBy(Offset(0f, i.toFloat())).isValidInMatrix(state.matrix)) {
                            i++
                        }
                        val updatedSprite = curSprite.moveBy(Offset(0f, (i - 1).toFloat()))
                        emit(state.copy(sprite = updatedSprite))
                    }
                    Action.Rotate -> run {
                        if (!state.isRunning) {
                            return@run
                        }
                        val curSprite = state.sprite
                        val updatedSprite = curSprite.rotate()
                        if (updatedSprite.isValidInMatrix(state.matrix)) {
                            emit(state.copy(sprite = updatedSprite))
                        }
                    }
                    Action.GameTick -> run {
                        if (!state.isRunning) {
                            return@run
                        }
                        val curSprite = state.sprite
                        // 砖块持续下落的情况
                        if (curSprite != Sprite.Empty) {
                            val updatedSprite = curSprite.moveBy(Direction.DOWN.toOffset())
                            if (updatedSprite.isValidInMatrix(state.matrix)) {
                                emit(state.copy(sprite = updatedSprite))
                                return@run
                            }
                        }
                        // 走到这里有三种情况：
                        // 1. 有效砖块，达到游戏结束条件
                        // 2. 有效砖块，到达底部，被添加到 Matrix 中
                        // 3. 无效砖块（Empty），执行 Matrix 里清行、砖块往下平移的逻辑

                        if (!curSprite.isValidInMatrix(state.matrix)) {
                            // GameOver
                            emit(state.copy(
                                gameStatus = GameStatus.GameOver,
                                sprite = Sprite.Empty,
                                nextSprite = Sprite.Empty,
                                matrix = Array(MatrixHeight) { IntArray(MatrixWidth) }
                            ))
                        } else {
                            // 如果是有效砖块，把砖块先添加到 Matrix 中
                            if (curSprite != Sprite.Empty) {
                                val newMatrix = curSprite.addToMatrix(state.matrix.copyOf())
                                emit(state.copy(
                                    matrix = newMatrix,
                                    sprite = Sprite.Empty // 把当前的 Sprite 置为空
                                ))
                            }
                            val newMatrix = _viewState.value.matrix
                            var hasFulledLines = false
                            // 清除满了的行
                            for (i in newMatrix.size - 1 downTo 0) {
                                // 从最后一行开始计算，找到第一个填满了的行
                                var isFulled = true
                                for (j in 0 until newMatrix[0].size) {
                                    if (newMatrix[i][j] == 0) {
                                        isFulled = false
                                        break
                                    }
                                }
                                if (isFulled) {
                                    Log.d("Owen", "第 $i 行填满了，开始消除")
                                    hasFulledLines = true
                                    val updatedMatrix = newMatrix.copyOf()
                                    // 把第一个填满的行清空
                                    updatedMatrix[i] = IntArray(MatrixWidth)
                                    // 获取最新的 ViewState，更新 matrix
                                    withState {
                                        emit(copy(matrix = updatedMatrix))
                                    }
                                    delay(50)
                                    // 剩余的砖块往下平移，并更新 ViewState 中的 matrix，刷新 UI
                                    withState {
                                        val movedMatrix = matrix.copyOf()
                                        for (m in i downTo 1) {
                                            movedMatrix[m] = movedMatrix[m - 1]
                                        }
                                        movedMatrix[0] = IntArray(MatrixWidth)
                                        emit(copy(
                                            matrix = movedMatrix,
                                            score = score + 1
                                        ))
                                    }
                                    // 一次就只处理一个填满的行，有多行同时填满的情况，在下次 GameTick 中处理
                                    break
                                }
                            }
                            if (!hasFulledLines) {
                                // 经过多次 Empty 砖块触发 GameTick，把所有满的行都消除后，生成下一个砖块
                                // 等下一个 GameTick 的 Action 开始下一轮
                                withState {
                                    val nextSprite = Sprite.generateRandomSprite()
                                    emit(copy(
                                        sprite = state.nextSprite,
                                        nextSprite = nextSprite
                                    ))
                                }
                            }
                        }
                    }
                    else -> {}
                }
            }
        }
    }

    /**
     * 修改状态
     */
    private fun emit(state: ViewState) {
        _viewState.value = state
    }

    /**
     * 获取最新的状态
     */
    private fun withState(block: ViewState.() -> Unit) {
        block(_viewState.value)
    }
}

const val MatrixWidth = 12
const val MatrixHeight = 24

data class ViewState(
    val sprite: Sprite = Sprite.Empty,
    val nextSprite: Sprite = Sprite.Empty,
    val matrix: Array<IntArray> = Array(MatrixHeight) { IntArray(MatrixWidth) },
    val gameStatus: GameStatus = GameStatus.OnBoard,
    val score: Int = 0,
) {
    val isPaused
        get() = gameStatus == GameStatus.Paused

    val isRunning
        get() = gameStatus == GameStatus.Running
}

sealed class Action {
    data class Move(val direction: Direction) : Action() // 向左右，下移动
    object Reset : Action()       // 重新开始
    object Pause : Action()       // 暂停游戏
    object Resume: Action()       // 恢复游戏
    object Rotate : Action()      // 旋转
    object Drop : Action()        // 点击 UP，直接掉落
    object Mute : Action()        // 静音/取消静音
    object GameTick : Action()    // 砖块下落通知
}

enum class GameStatus {
    OnBoard,
    Running,
    Paused,
    GameOver
}

