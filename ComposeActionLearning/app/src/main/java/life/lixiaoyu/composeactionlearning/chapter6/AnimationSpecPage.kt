package life.lixiaoyu.composeactionlearning.chapter6

import androidx.compose.animation.core.*
import androidx.compose.animation.core.Spring.DampingRatioHighBouncy
import androidx.compose.animation.core.Spring.DampingRatioLowBouncy
import androidx.compose.animation.core.Spring.DampingRatioMediumBouncy
import androidx.compose.animation.core.Spring.DampingRatioNoBouncy
import androidx.compose.animation.core.Spring.StiffnessHigh
import androidx.compose.animation.core.Spring.StiffnessLow
import androidx.compose.animation.core.Spring.StiffnessMedium
import androidx.compose.animation.core.Spring.StiffnessMediumLow
import androidx.compose.animation.core.Spring.StiffnessVeryLow
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import life.lixiaoyu.composeactionlearning.DescItem
import life.lixiaoyu.composeactionlearning.FullPageWrapper
import life.lixiaoyu.composeactionlearning.VerticalSpacer

@Composable
fun AnimationSpecPage() {
    FullPageWrapper {
        Column(
            Modifier
                .padding(10.dp)
                .verticalScroll(rememberScrollState())
        ) {
            SpringSpecDampingRatioDemo()
            SpringSpecStiffnessDemo()
            SpringSpecVisibilityThresholdDemo()
            TweenSpecEasingDemo()
            KeyframeSpecDemo()
            RepeatableAndInfiniteRepeatableSpecDemo()
            SnapSpecDemo()
        }
    }
}

@Composable
fun ColumnScope.SpringSpecDampingRatioDemo() {
    DescItem(title = "展示 spring 动画不同的 dampingRatio")
    var onLeft by remember { mutableStateOf(true) }
    Button(onClick = { onLeft = !onLeft }) {
        Text("切换位置")
    }
    DampingRatioItem(onLeft = onLeft, title = "DampingRatioHighBouncy(0.2F)", dampingRatio = DampingRatioHighBouncy)
    DampingRatioItem(onLeft = onLeft, title = "DampingRatioMediumBouncy(0.5F)", dampingRatio = DampingRatioMediumBouncy)
    DampingRatioItem(onLeft = onLeft, title = "DampingRatioLowBouncy(0.75F)", dampingRatio = DampingRatioLowBouncy)
    DampingRatioItem(onLeft = onLeft, title = "DampingRatioNoBouncy(1F)", dampingRatio = DampingRatioNoBouncy)
    DampingRatioItem(onLeft = onLeft, title = "dampingRatio = 2F", dampingRatio = 2F)
}

@Composable
fun DampingRatioItem(
    onLeft: Boolean,
    title: String,
    dampingRatio: Float,
) {
    val offset by animateDpAsState(
        targetValue = if (onLeft) 0.dp else 280.dp,
        animationSpec = spring(
            dampingRatio = dampingRatio
        )
    )
    Column {
        Text(text = title)
        VerticalSpacer(height = 2.dp)
        Box {
            Box(
                Modifier
                    .width(300.dp)
                    .height(20.dp)
                    .background(Color.LightGray)
            )
            Box(
                Modifier
                    .offset(offset, 0.dp)
                    .size(20.dp)
                    .background(Color.Red)
            )
        }
    }
}

@Composable
fun ColumnScope.SpringSpecStiffnessDemo() {
    DescItem(title = "展示 spring 动画不同的 stiffness")
    var onLeft by remember { mutableStateOf(true) }
    Button(onClick = { onLeft = !onLeft }) {
        Text("切换位置")
    }
    StiffnessItem(onLeft = onLeft, title = "StiffnessHigh(10_000F)", stiffness = StiffnessHigh)
    StiffnessItem(onLeft = onLeft, title = "StiffnessMedium(1500F)", stiffness = StiffnessMedium)
    StiffnessItem(onLeft = onLeft, title = "StiffnessMediumLow(400F)", stiffness = StiffnessMediumLow)
    StiffnessItem(onLeft = onLeft, title = "StiffnessLow(200F)", stiffness = StiffnessLow)
    StiffnessItem(onLeft = onLeft, title = "StiffnessVeryLow(50F)", stiffness = StiffnessVeryLow)
}

@Composable
fun StiffnessItem(
    onLeft: Boolean,
    title: String,
    stiffness: Float,
) {
    val offset by animateDpAsState(
        targetValue = if (onLeft) 0.dp else 280.dp,
        animationSpec = spring(
            stiffness = stiffness
        )
    )
    Column {
        Text(text = title)
        VerticalSpacer(height = 2.dp)
        Box {
            Box(
                Modifier
                    .width(300.dp)
                    .height(20.dp)
                    .background(Color.LightGray)
            )
            Box(
                Modifier
                    .offset(offset, 0.dp)
                    .size(20.dp)
                    .background(Color.Blue)
            )
        }
    }
}

@Composable
fun ColumnScope.SpringSpecVisibilityThresholdDemo() {
    DescItem(title = "展示 spring 动画 visibilityThreshold 效果")
    var onLeft by remember { mutableStateOf(true) }
    Button(onClick = { onLeft = !onLeft }) {
        Text("切换位置")
    }
    val offset by animateDpAsState(
        targetValue = if (onLeft) 0.dp else 280.dp,
        animationSpec = spring(
            dampingRatio = DampingRatioHighBouncy,
            visibilityThreshold = 140.dp
        )
    )
    Box {
        Box(
            Modifier
                .width(300.dp)
                .height(20.dp)
                .background(Color.LightGray)
        )
        Box(
            Modifier
                .offset(offset, 0.dp)
                .size(20.dp)
                .background(Color.Green)
        )
    }
}

@Composable
fun ColumnScope.TweenSpecEasingDemo() {
    DescItem(title = "展示 tween 动画不同 Easing 的效果")
    var onLeft by remember { mutableStateOf(true) }
    Button(onClick = { onLeft = !onLeft }) {
        Text("切换位置")
    }
    EasingItem(onLeft = onLeft, title = "LinearEasing", easing = LinearEasing)
    EasingItem(onLeft = onLeft, title = "LinearOutSlowInEasing", easing = LinearOutSlowInEasing)
    EasingItem(onLeft = onLeft, title = "FastOutSlowInEasing", easing = FastOutSlowInEasing)
    EasingItem(onLeft = onLeft, title = "FastOutLinearInEasing", easing = FastOutLinearInEasing)
}

@Composable
fun EasingItem(
    onLeft: Boolean,
    title: String,
    easing: Easing,
) {
    val offset by animateDpAsState(
        targetValue = if (onLeft) 0.dp else 280.dp,
        animationSpec = tween(
            durationMillis = 1000,
            delayMillis = 50,
            easing = easing
        )
    )
    Column {
        Text(text = title)
        VerticalSpacer(height = 2.dp)
        Box {
            Box(
                Modifier
                    .width(300.dp)
                    .height(20.dp)
                    .background(Color.LightGray)
            )
            Box(
                Modifier
                    .offset(offset, 0.dp)
                    .size(20.dp)
                    .background(Color.Cyan)
            )
        }
    }
}

@Composable
fun ColumnScope.KeyframeSpecDemo() {
    DescItem(title = "展示 keyframes 关键帧动画效果")
    var onLeft by remember { mutableStateOf(true) }
    Button(onClick = { onLeft = !onLeft }) {
        Text("切换位置")
    }
    val offset by animateDpAsState(
        targetValue = if (onLeft) 0.dp else 280.dp,
        animationSpec = keyframes {
            durationMillis = 2000
            0.dp at 0 with LinearOutSlowInEasing
            50.dp at 200 with FastOutLinearInEasing
            140.dp at 500
            100.dp at 700
            200.dp atFraction 0.9f
        }
    )
    Box {
        Box(
            Modifier
                .width(300.dp)
                .height(20.dp)
                .background(Color.LightGray)
        )
        Box(
            Modifier
                .offset(offset, 0.dp)
                .size(20.dp)
                .background(Color.Magenta)
        )
    }
}

@Composable
fun ColumnScope.RepeatableAndInfiniteRepeatableSpecDemo() {
    DescItem(title = "展示 repeatable 和 infiniteRepeatable 效果")
    var onLeft by remember { mutableStateOf(true) }
    Button(onClick = { onLeft = !onLeft }) {
        Text("切换位置")
    }
    val offset1 by animateDpAsState(
        targetValue = if (onLeft) 0.dp else 280.dp,
        animationSpec = repeatable(
            iterations = 3,
            animation = tween(1000),
            repeatMode = RepeatMode.Reverse
        )
    )
    val offset2 by animateDpAsState(
        targetValue = if (onLeft) 0.dp else 280.dp,
        animationSpec = infiniteRepeatable(
            animation = tween(1000),
            repeatMode = RepeatMode.Reverse
        )
    )
    Text(text = "repeatable(3)")
    VerticalSpacer(height = 2.dp)
    Box {
        Box(
            Modifier
                .width(300.dp)
                .height(20.dp)
                .background(Color.LightGray)
        )
        Box(
            Modifier
                .offset(offset1, 0.dp)
                .size(20.dp)
                .background(Color.Yellow)
        )
    }
    Text(text = "infiniteRepeatable")
    VerticalSpacer(height = 2.dp)
    Box {
        Box(
            Modifier
                .width(300.dp)
                .height(20.dp)
                .background(Color.LightGray)
        )
        Box(
            Modifier
                .offset(offset2, 0.dp)
                .size(20.dp)
                .background(Color.Yellow)
        )
    }
}

@Composable
fun ColumnScope.SnapSpecDemo() {
    DescItem(title = "展示 snap 快闪动画效果")
    var onLeft by remember { mutableStateOf(true) }
    Button(onClick = { onLeft = !onLeft }) {
        Text("切换位置")
    }
    val offset by animateDpAsState(
        targetValue = if (onLeft) 0.dp else 280.dp,
        animationSpec = snap(
            delayMillis = 100
        )
    )
    Box {
        Box(
            Modifier
                .width(300.dp)
                .height(20.dp)
                .background(Color.LightGray)
        )
        Box(
            Modifier
                .offset(offset, 0.dp)
                .size(20.dp)
                .background(Color(0xFFF6BB45))
        )
    }
}