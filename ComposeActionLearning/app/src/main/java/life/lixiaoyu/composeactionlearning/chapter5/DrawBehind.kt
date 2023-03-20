package life.lixiaoyu.composeactionlearning.chapter5

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import life.lixiaoyu.composeactionlearning.HorizontalSpacer
import life.lixiaoyu.composeactionlearning.R

@Composable
fun DrawBehindPage() {
    Row {
        DrawBefore()
        HorizontalSpacer(width = 10.dp)
        DrawBehind()
    }
}

@Composable
fun DrawBefore() {
    Box(
        modifier = Modifier.padding(20.dp),
        contentAlignment = Alignment.Center
    ) {
        Card(
            shape = RoundedCornerShape(8.dp),
            modifier = Modifier
                .size(100.dp)
                .drawWithContent {
                    drawContent()
                    drawCircle(
                        color = Color(0xFFE7614E),
                        radius = 10.dp.toPx(),
                        center = Offset(drawContext.size.width, 0F)
                    )
                }
        ) {
            Image(painterResource(id = R.drawable.chandler), "")
        }
    }
}

@Composable
fun DrawBehind() {
    Box(
        modifier = Modifier.padding(20.dp),
        contentAlignment = Alignment.Center
    ) {
        Card(
            shape = RoundedCornerShape(8.dp),
            modifier = Modifier
                .size(100.dp)
                .drawBehind {
                    drawCircle(
                        color = Color(0xFFE7614E),
                        radius = 10.dp.toPx(),
                        center = Offset(drawContext.size.width, 0F)
                    )
                }
        ) {
            Image(painterResource(id = R.drawable.chandler), "")
        }
    }
}