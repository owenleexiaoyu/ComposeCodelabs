package life.lixiaoyu.composeactionlearning.chapter6

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MusicNote
import androidx.compose.material.icons.rounded.FastForward
import androidx.compose.material.icons.rounded.FastRewind
import androidx.compose.material.icons.rounded.Pause
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.scale
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import life.lixiaoyu.composeactionlearning.*
import life.lixiaoyu.composeactionlearning.R

/**
 * 实现灵动岛动画。
 * Compose Camp: Compose 动画与实战
 * https://www.bilibili.com/video/BV1jW4y177wB/?spm_id_from=333.999.0.0&vd_source=581e0a7f58fafc192abc773bb2e5e1ed
 */
@Composable
fun DynamicIslandPage() {
    FullPageWrapper {
        Column(
            Modifier.padding(10.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            DescItem(title = "实战灵动岛动画效果")
            DynamicIslandAnimation()
        }
    }
}

enum class DynamicIslandState {
    Small,
    Medium,
    Large
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun DynamicIslandAnimation() {
    var dynamicIslandState: DynamicIslandState by remember { mutableStateOf(DynamicIslandState.Small) }
    val transition = updateTransition(targetState = dynamicIslandState, label = "DynamicIslandTransition")
    val size by transition.animateSize(
        transitionSpec = { spring(dampingRatio = Spring.DampingRatioMediumBouncy, stiffness = Spring.StiffnessLow) },
        label = "DynamicIslandSize"
    ) {
        when (it) {
            DynamicIslandState.Small -> Size(120F, 40F)
            DynamicIslandState.Medium -> Size(200F, 40F)
            DynamicIslandState.Large -> Size(350F, 230F)
        }
    }
    val layoutPadding by transition.animateDp(label = "LayoutPadding") {
        when (it) {
            DynamicIslandState.Small, DynamicIslandState.Medium -> 4.dp
            DynamicIslandState.Large -> 20.dp
        }
    }
    val avatorSize by transition.animateDp(label = "AvatorSize") {
        when (it) {
            DynamicIslandState.Small, DynamicIslandState.Medium -> 32.dp
            DynamicIslandState.Large -> 64.dp
        }
    }

    val avatorRoundedCorner by transition.animateDp(label = "AvatorRoundedCorner") {
        when (it) {
            DynamicIslandState.Small, DynamicIslandState.Medium -> 32.dp
            DynamicIslandState.Large -> 12.dp
        }
    }

    val musicIconSize by transition.animateDp(label = "MusicIconSize") {
        when (it) {
            DynamicIslandState.Small, DynamicIslandState.Medium -> 32.dp
            DynamicIslandState.Large -> 64.dp
        }
    }

    // 当 Large 时，Music Icon 有无限循环动画
    val musicIconInfiniteTransition = rememberInfiniteTransition()
    val musicIconScale by musicIconInfiniteTransition.animateFloat(
        initialValue = 1F,
        targetValue = 0.5F,
        animationSpec = infiniteRepeatable(animation = tween(durationMillis = 500), repeatMode = RepeatMode.Reverse)
    )
    val musicIconRotation by musicIconInfiniteTransition.animateFloat(
        initialValue = 0F,
        targetValue = 360F,
        animationSpec = infiniteRepeatable(animation = tween(durationMillis = 3000), repeatMode = RepeatMode.Restart)
    )

    // 绿色指示灯闪烁动画
    val indicatorColorTransition = rememberInfiniteTransition()
    val indicatorColor by indicatorColorTransition.animateColor(
        initialValue = Color.Green.copy(0.7F),
        targetValue = Color.Green.copy(alpha = 0.3F),
        animationSpec = infiniteRepeatable(initialStartOffset = StartOffset(offsetMillis = 500), animation = tween(delayMillis = 2000), repeatMode = RepeatMode.Reverse)
    )

    Column(
        modifier = Modifier
            .size(size.width.dp, size.height.dp)
            .clip(RoundedCornerShape(40.dp))
            .background(Color.Black)
            .padding(layoutPadding)
            .clickable {
                dynamicIslandState = when (dynamicIslandState) {
                    DynamicIslandState.Small -> DynamicIslandState.Medium
                    DynamicIslandState.Medium -> DynamicIslandState.Large
                    DynamicIslandState.Large -> DynamicIslandState.Small
                }
            }
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (dynamicIslandState != DynamicIslandState.Small) {
                Image(
                    painter = painterResource(id = R.drawable.chandler),
                    contentDescription = "",
                    modifier = Modifier
                        .size(avatorSize)
                        .clip(RoundedCornerShape(avatorRoundedCorner))
                )
            } else {
                Box(
                    Modifier
                        .size(32.dp)
                        .clip(RoundedCornerShape(32.dp))
                )
            }
            AnimatedVisibility(
                visible = dynamicIslandState == DynamicIslandState.Large,
                modifier = Modifier.padding(start = 16.dp, end = 16.dp),
                enter = fadeIn(),
                exit = fadeOut()
            ) {
                Column {
                    Text("Entropy", fontSize = 24.sp, fontWeight = FontWeight.Bold, color = Color.White)
                    VerticalSpacer(height = 4.dp)
                    Text("Beach Bunny", fontSize = 20.sp, fontWeight = FontWeight.Medium, color = Color.White.copy(alpha = 0.5F))
                }
            }
            Box(modifier = Modifier
                .weight(1F),
                contentAlignment = Alignment.Center
            ) {
                if (dynamicIslandState == DynamicIslandState.Small) {
                    Box(
                        Modifier
                            .size(10.dp)
                            .clip(RoundedCornerShape(10.dp))
                            .background(indicatorColor)
                    )
                }
            }
            if (dynamicIslandState != DynamicIslandState.Small) {
                Icon(
                    Icons.Default.MusicNote,
                    contentDescription = "",
                    modifier = Modifier
                        .size(musicIconSize)
                        .scale(if (dynamicIslandState == DynamicIslandState.Large) musicIconScale else 1F)
                        .rotate(musicIconRotation),
                    tint = Color.White
                )
            } else {
                Box(
                    Modifier
                        .size(32.dp)
                        .clip(RoundedCornerShape(32.dp))
                )
            }
        }
        AnimatedVisibility(
            visible = dynamicIslandState == DynamicIslandState.Large,
            enter = scaleIn(tween(500, delayMillis = 300)) + fadeIn(tween(500, delayMillis = 300)),
            exit = scaleOut() + fadeOut(),
        ) {
            Column (Modifier.padding(top = 20.dp)){
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "2:50",
                        color = Color.White,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                    HorizontalSpacer(width = 20.dp)
                    LinearProgressIndicator(
                        progress = 0.7F,
                        modifier = Modifier
                            .weight(1F)
                            .height(12.dp)
                            .clip(RoundedCornerShape(12.dp)),
                        backgroundColor = Color.White.copy(alpha = 0.7F),
                        color = Color.White
                    )
                    HorizontalSpacer(width = 20.dp)
                    Text(
                        text = "-0:51",
                        color = Color.White,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                }
                VerticalSpacer(height = 16.dp)
                Row(
                    Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceAround
                ) {
                    Icon(Icons.Rounded.FastRewind, contentDescription = "", modifier = Modifier.size(32.dp), tint = Color.White)
                    Icon(Icons.Rounded.Pause, contentDescription = "", modifier = Modifier.size(32.dp), tint = Color.White)
                    Icon(Icons.Rounded.FastForward, contentDescription = "", modifier = Modifier.size(32.dp), tint = Color.White)
                }
            }
        }
    }
}