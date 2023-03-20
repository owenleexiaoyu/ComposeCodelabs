package life.lixiaoyu.composeactionlearning.chapter5

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

/**
 * 使用 Row 已实现的固有特性测量
 */

@Composable
fun TwoTexts(
    modifier: Modifier = Modifier,
    text1: String,
    text2: String
) {
    Row(
        modifier = modifier
            .height(IntrinsicSize.Min)
            .border(0.5.dp, Color.Blue.copy(alpha = 0.5F)),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = text1,
            modifier = Modifier
                .weight(1F)
                .background(Color.Red.copy(alpha = 0.3F))
                .padding(start = 5.dp)
                .wrapContentWidth(Alignment.Start)
        )
        Divider(color = Color.Black, modifier = Modifier
            .fillMaxHeight()
            .width(2.dp))
        Text(
            text = text2,
            fontSize = 30.sp,
            modifier = Modifier
                .weight(1F)
                .background(Color.Yellow.copy(alpha = 0.3F))
                .padding(end = 5.dp)
                .wrapContentWidth(Alignment.End)
        )
    }
}

@Composable
fun TwoTextsPage() {
    TwoTexts(modifier = Modifier.padding(10.dp), text1 = "Hello", text2 = "World")
}