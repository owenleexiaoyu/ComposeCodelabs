package life.lixiaoyu.composetetris

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import kotlin.math.min

@Composable
fun ScoreBoard(
    modifier: Modifier = Modifier,
    viewState: ViewState
) {
    Box(modifier = modifier){
        Column {
            Text("Next Sprite")
            Canvas(
                modifier = modifier,
                onDraw = {
                    val matrix = viewState.matrix
                    val matrixSize = matrix[0].size to matrix.size
                    val brickSize = min(size.width / matrixSize.first, size.height / matrixSize.second)
                    drawSprite(viewState.nextSprite.copy(offset = Offset.Zero), brickSize, matrixSize)
                })
        }
    }
}