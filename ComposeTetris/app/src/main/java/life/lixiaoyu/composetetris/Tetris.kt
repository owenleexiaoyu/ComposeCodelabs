package life.lixiaoyu.composetetris

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive

@Composable
fun Tetris() {
    val tetrisViewModel = viewModel<TetrisViewModel>()
    val viewState = tetrisViewModel.viewState.value

    // 设置沉浸式系统状态栏和导航栏
    val systemUiController = rememberSystemUiController()
    val useDarkIcons = !isSystemInDarkTheme()

    LaunchedEffect(key1 = Unit) {
        systemUiController.setSystemBarsColor(color = Color.Yellow, darkIcons = useDarkIcons)
        while (isActive) {
            delay(650L)
            tetrisViewModel.dispatch(Action.GameTick)
        }
    }
    Column(
        modifier = Modifier.background(Color.Yellow)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(2F)
        ) {
            Box(modifier = Modifier
                .fillMaxSize()
                .padding(30.dp)
                .border(3.dp, Color.Black, shape = RoundedCornerShape(4.dp))
                .padding(10.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxSize()
                ) {
                    BrickMatrix(
                        modifier = Modifier
                            .fillMaxHeight()
                            .weight(2F)
                            .aspectRatio(0.5F),
                        viewState
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    ScoreBoard(modifier = Modifier
                        .fillMaxHeight()
                        .weight(1F)
                        .aspectRatio(0.25F)
                        .border(0.5F.dp, Color.White))
                }

            }
        }
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1F)
        ) {
            GameBody(Clickable(
                onMove = {direction: Direction ->
                    tetrisViewModel.dispatch(Action.Move(direction))
                },
                onMute = {
                    tetrisViewModel.dispatch(Action.Mute)
                },
                onPause = {
                    tetrisViewModel.dispatch(Action.Pause)
                },
                onRestart = {
                    tetrisViewModel.dispatch(Action.Reset)
                },
                onRotate = {
                    tetrisViewModel.dispatch(Action.Rotate)
                },
                onDrop = {
                    tetrisViewModel.dispatch(Action.Drop)
                }
            ))
        }
    }
}


@Composable
@Preview
fun TetrisPreview() {
    Tetris()
}