package life.lixiaoyu.composeactionlearning.chapter5

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun DrawProgressBarPage() {
    LoadingProgressBar()
}

@Composable
fun LoadingProgressBar() {
    val sweepAngle by remember { mutableStateOf(162F) }
    Box(
        modifier = Modifier.size(375.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Loading",
                fontSize = 40.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color.Black
            )
            Text(
                text = "45%",
                fontSize = 40.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )
        }
        Canvas(
            modifier = Modifier
                .fillMaxSize()
                .padding(30.dp)) {
            drawCircle(
                color = Color(0xFF1E7171),
                center = Offset(drawContext.size.width / 2F, drawContext.size.height / 2F),
                style = Stroke(width = 20.dp.toPx())
            )
            drawArc(
                color = Color(0xFF3BDCCE),
                startAngle = -90F,
                sweepAngle = sweepAngle,
                useCenter = false,
                style = Stroke(width = 20.dp.toPx(), cap = StrokeCap.Round)
            )
        }
    }

    
}