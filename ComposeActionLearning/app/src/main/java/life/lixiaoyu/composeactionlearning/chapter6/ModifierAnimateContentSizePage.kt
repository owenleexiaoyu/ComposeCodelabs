package life.lixiaoyu.composeactionlearning.chapter6

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import life.lixiaoyu.composeactionlearning.FullPageWrapper

@Composable
fun ModifierAnimateContentSizePage() {
    FullPageWrapper {
        Column(Modifier.padding(10.dp)) {
            var expand by remember { mutableStateOf(false) }
            Button(onClick = { expand = !expand }) {
                Text(if (expand) "收起" else "展开")
            }
            Box(
                Modifier.background(Color.Blue.copy(alpha = 0.5F), RoundedCornerShape(10.dp))
                    .padding(10.dp)
                    .animateContentSize()
            ) {
                Text(text = "This modifier animates its own size when its child modifier (or the child composable if it is already at the tail of the chain) changes size. This allows the parent modifier to observe a smooth size change, resulting in an overall continuous visual change.\n" +
                        "A FiniteAnimationSpec can be optionally specified for the size change animation. By default, spring will be used.\n" +
                        "An optional finishedListener can be supplied to get notified when the size change animation is finished. Since the content size change can be dynamic in many cases, both initial value and target value (i.e. final size) will be passed to the finishedListener. " +
                        "Note: if the animation is interrupted, the initial value will be the size at the point of interruption. This is intended to help determine the direction of the size change (i.e. expand or collapse in x and y dimensions).",
                    maxLines = if (expand) Int.MAX_VALUE else 2,
                    fontSize = 16.sp,
                    textAlign = TextAlign.Justify
                )
            }
        }
    }
}