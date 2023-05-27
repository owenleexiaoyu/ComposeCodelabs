package life.lixiaoyu.composetetris

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import life.lixiaoyu.composetetris.ui.theme.Purple200
import life.lixiaoyu.composetetris.ui.theme.Purple500

@Composable
fun GameBody(clickable: Clickable) {
    Column(
        modifier = Modifier.padding(30.dp)
    ) {
        Row (
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            GameBar(title = "SOUNDS", onClick = {
                clickable.onMute()
            })
            GameBar(title = "PAUSE/RESUME", onClick = {
                clickable.onPauseOrResume()
            })
            GameBar(title = "START/RESET", onClick = {
                clickable.onRestart()
            })
        }
        Spacer(modifier = Modifier.height(10.dp))
        Row (
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ){
            Box(
                modifier = Modifier.size(160.dp)
            ) {
                GameButton(
                    modifier = Modifier.align(Alignment.TopCenter),
                    size = 60.dp,
                    onClick = {
                        clickable.onRotate()
                    }
                ) {
                    Icon(
                        Icons.Default.Refresh,
                        "Rotate",
                        modifier = it.size(40.dp),
                        tint = Color.White
                    )
                }
                GameButton(
                    modifier = Modifier.align(Alignment.CenterStart),
                    size = 60.dp,
                    onClick = {
                        clickable.onMove(Direction.LEFT)
                    }
                ) {
                    Icon(
                        Icons.Default.KeyboardArrowLeft,
                        "Left",
                        modifier = it.size(60.dp),
                        tint = Color.White
                    )
                }
                GameButton(
                    modifier = Modifier.align(Alignment.CenterEnd),
                    size = 60.dp,
                    onClick = {
                        clickable.onMove(Direction.RIGHT)
                    }
                ) {
                    Icon(
                        Icons.Default.KeyboardArrowRight,
                        "Right",
                        modifier = it.size(60.dp),
                        tint = Color.White
                    )
                }
                GameButton(
                    modifier = Modifier.align(Alignment.BottomCenter),
                    size = 60.dp,
                    onClick = {
                        clickable.onMove(Direction.DOWN)
                    }
                ) {
                    Icon(
                        Icons.Default.KeyboardArrowDown,
                        "Down",
                        modifier = it.size(60.dp),
                        tint = Color.White
                    )
                }
            }
            GameButton(size = 100.dp, onClick = {
                clickable.onDrop()
            }) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_speed_down),
                    "Speed Down",
                    modifier = it.size(40.dp),
                    tint = Color.White
                )
            }
        }
    }
}

@Composable
@Preview
fun GameBodyPreview() {
//    GameBody()
}

@Composable
fun GameBar(
    modifier: Modifier = Modifier,
    title: String,
    onClick: (() -> Unit)?,
) {
    Column(modifier = modifier,
    horizontalAlignment = Alignment.CenterHorizontally
        ) {
        Text(title, color = Color.Black, fontWeight = FontWeight.SemiBold)
        Spacer(modifier = Modifier.height(4.dp))
        Button(
            modifier = Modifier.height(10.dp),
            onClick = { onClick?.invoke() }) {

        }
    }
}

@Composable
fun GameButton(
    modifier: Modifier = Modifier,
    size: Dp,
    onClick: (() -> Unit)? = null,
    content: @Composable (Modifier) -> Unit
) {
    val backgroundShape = RoundedCornerShape(size / 2)
    Box(
        modifier = modifier
            .shadow(5.dp, shape = backgroundShape)
            .size(size = size)
            .clip(backgroundShape)
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        Purple200,
                        Purple500
                    ),
                    startY = 0F,
                    endY = 80F
                )
            )
            .clickable { onClick?.invoke() }
    ) {
        content(Modifier.align(Alignment.Center))
    }
}