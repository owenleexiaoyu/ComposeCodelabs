package life.lixiaoyu.composetetris

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import life.lixiaoyu.composetetris.model.Sprite
import kotlin.math.min

@Composable
fun ScoreBoard(
    modifier: Modifier = Modifier,
    viewState: ViewState
) {
    Box(modifier = modifier) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text("Score")
            Spacer(modifier = Modifier.height(2.dp))
            Text("${viewState.score * 10}", fontSize = 20.sp, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(5.dp))
            Text("Next Sprite")
            Spacer(modifier = Modifier.height(2.dp))
            Canvas(
                modifier = Modifier.fillMaxSize(0.75F),
                onDraw = {
                    val matrixSizePair = Sprite.SIZE to Sprite.SIZE
                    val brickSize = min(size.width / matrixSizePair.first, size.height / matrixSizePair.second)
                    drawSprite(viewState.nextSprite.copy(offset = Offset.Zero), brickSize, matrixSizePair)
                })
        }
    }
}