package life.lixiaoyu.composeactionlearning.chapter6

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import life.lixiaoyu.composeactionlearning.FullPageWrapper

/**
 * 骨架屏动画
 */
@Composable
fun SkeletonAnimationPage() {
    FullPageWrapper {
        Column {
            repeat(4) {
                AnimatedSkeletonItem()
            }
        }
    }
}

val skeletonColors = listOf(
    Color.LightGray.copy(alpha = 0.6F),
    Color.LightGray.copy(alpha = 0.2F),
    Color.LightGray.copy(alpha = 0.6F),
)

@Composable
fun AnimatedSkeletonItem() {
    val transition = rememberInfiniteTransition()
    val translateAnimation = transition.animateFloat(
        initialValue = 0f,
        targetValue = 1000f,
        animationSpec = infiniteRepeatable(
            animation = tween(1500, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Restart
        )
    )
    val brush = Brush.linearGradient(
        colors = skeletonColors,
        start = Offset.Zero,
        end = Offset(x = translateAnimation.value, y = translateAnimation.value)
    )
    SkeletonItem(brush = brush)
}

@Composable
fun SkeletonItem(brush: Brush) {
    Column(modifier = Modifier
        .fillMaxWidth()
        .padding(10.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Spacer(modifier = Modifier
                .size(100.dp)
                .clip(RoundedCornerShape(5.dp))
                .background(brush)
            )
            Spacer(modifier = Modifier.padding(10.dp))
            Column(
                verticalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .weight(1F)
                    .height(100.dp)
            ) {
                repeat(5) {
                    Spacer(modifier = Modifier
                        .height(10.dp)
                        .clip(RoundedCornerShape(5.dp))
                        .fillMaxWidth()
                        .background(brush)
                    )
                }
            }
        }
        repeat(3) {
            Spacer(modifier = Modifier
                .padding(top = 10.dp)
                .height(10.dp)
                .clip(RoundedCornerShape(5.dp))
                .fillMaxWidth()
                .background(brush)
            )
        }
    }

}