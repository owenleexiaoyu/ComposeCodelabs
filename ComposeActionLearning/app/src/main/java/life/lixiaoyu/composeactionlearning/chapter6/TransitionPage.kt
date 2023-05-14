package life.lixiaoyu.composeactionlearning.chapter6

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.Star
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import life.lixiaoyu.composeactionlearning.DescItem
import life.lixiaoyu.composeactionlearning.FullPageWrapper
import life.lixiaoyu.composeactionlearning.HorizontalSpacer
import life.lixiaoyu.composeactionlearning.R

@Composable
fun TransitionPage() {
    FullPageWrapper {
        Column(
            Modifier
                .padding(10.dp)
                .verticalScroll(rememberScrollState())
        ) {
            UpdateTransitionDemo1()
            UpdateTransitionDemo2()
            MutableTransitionStateDemo()
            CreateChildTransitionDemo()
            TransitionWithAnimatedVisibilityAndAnimatedContentDemo()
            ReuseTransitionDemo()
            InfiniteTransitionDemo1()
            InfiniteRepeatableDemo()
            ImageBorderAnimation()
        }
    }
}

enum class BoxState {
    Collapsed,
    Expanded
}

fun <T> BoxState.valueOf(collapsed: T, expanded: T): T {
    return when(this) {
        BoxState.Collapsed -> collapsed
        BoxState.Expanded -> expanded
    }
}

@Composable
fun ColumnScope.UpdateTransitionDemo1() {
    DescItem(title = "使用 updateTransition 控制 Box size、bg、borderColor、borderWidth 的动画同时进行")
    var currentState by remember { mutableStateOf(BoxState.Collapsed) }
    val transition = updateTransition(targetState = currentState, label = "Box Transition")

    val size by transition.animateFloat(transitionSpec = { tween(500) }, label = "size") {
        it.valueOf(100F, 200F)
    }
    val bg by transition.animateColor(transitionSpec = { tween(500) }, label = "bg") {
        it.valueOf(Color.Green, Color.Cyan)
    }
    val borderWidth by transition.animateDp(transitionSpec = { tween(500) }, label = "borderWidth") {
        it.valueOf(1.dp, 2.dp)
    }
    val borderColor by transition.animateColor(transitionSpec = { tween(500) }, label = "borderColor") {
        it.valueOf(Color.Red, Color.Blue)
    }
    Box(
        modifier = Modifier
            .clickable {
                currentState =
                    if (currentState == BoxState.Collapsed) BoxState.Expanded else BoxState.Collapsed
            }
            .padding(5.dp)
            .size(size.dp)
            .background(bg)
            .border(width = borderWidth, color = borderColor)
    )
}

sealed class SwitchState {
    object OPEN: SwitchState()
    object CLOSE: SwitchState()
}

@Composable
fun ColumnScope.UpdateTransitionDemo2() {
    DescItem(title = "使用 updateTransition 实现收藏动画")
    var currentState: SwitchState by remember { mutableStateOf(SwitchState.CLOSE) }
    val transition = updateTransition(targetState = currentState, label = "Collection Transition")
    val textAlpha by transition.animateFloat(label = "textAlpha") {
        when(it) {
            SwitchState.CLOSE -> 1F
            SwitchState.OPEN -> 0F
        }
    }
    val selectBarPadding by transition.animateDp(label = "selectBarPadding") {
        when (it) {
            SwitchState.CLOSE -> 40.dp
            SwitchState.OPEN -> 0.dp
        }
    }
    Box(
        modifier = Modifier
            .size(150.dp)
            .padding(8.dp)
            .clip(RoundedCornerShape(10.dp))
            .clickable {
                currentState =
                    if (currentState == SwitchState.CLOSE) SwitchState.OPEN else SwitchState.CLOSE
            }
    ) {
        Image(
            painter = painterResource(id = R.drawable.chandler),
            contentDescription = "",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.FillBounds
        )
        Text(
            text = "收藏",
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White,
            modifier = Modifier
                .align(Alignment.Center)
                .alpha(textAlpha)
        )
        Box(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .height(40.dp)
                .padding(top = selectBarPadding)
                .background(Color(0xFF5FB878))
        ) {
            Row(modifier = Modifier
                .align(Alignment.Center)
                .alpha(1 - textAlpha)) {
                Icon(Icons.Default.Star, contentDescription = "", tint = Color.White)
                HorizontalSpacer(width = 2.dp)
                Text(
                    text = "已收藏",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color.White
                )
            }
        }
    }
}

@Composable
fun ColumnScope.MutableTransitionStateDemo() {
    DescItem(title = "使用 MutableTransitionState 实现自动执行动画")
    var collectState = remember {
        MutableTransitionState<SwitchState>(SwitchState.CLOSE).apply {
            targetState = SwitchState.OPEN
        }
    }
    val transition = updateTransition(transitionState = collectState, label = "Collection Transition")
    val textAlpha by transition.animateFloat(transitionSpec = { tween(500) }, label = "textAlpha") {
        when(it) {
            SwitchState.CLOSE -> 1F
            SwitchState.OPEN -> 0F
        }
    }
    val selectBarPadding by transition.animateDp(transitionSpec = { tween(500) }, label = "selectBarPadding") {
        when (it) {
            SwitchState.CLOSE -> 40.dp
            SwitchState.OPEN -> 0.dp
        }
    }
    Box(
        modifier = Modifier
            .size(150.dp)
            .padding(8.dp)
            .clip(RoundedCornerShape(10.dp))
            .clickable {
                collectState.targetState =
                    if (collectState.targetState == SwitchState.CLOSE) SwitchState.OPEN else SwitchState.CLOSE
            }
    ) {
        Image(
            painter = painterResource(id = R.drawable.chandler),
            contentDescription = "",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.FillBounds
        )
        Text(
            text = "收藏",
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White,
            modifier = Modifier
                .align(Alignment.Center)
                .alpha(textAlpha)
        )
        Box(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .height(40.dp)
                .padding(top = selectBarPadding)
                .background(Color(0xFF5FB878))
        ) {
            Row(modifier = Modifier
                .align(Alignment.Center)
                .alpha(1 - textAlpha)) {
                Icon(Icons.Default.Star, contentDescription = "", tint = Color.White)
                HorizontalSpacer(width = 2.dp)
                Text(
                    text = "已收藏",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color.White
                )
            }
        }
    }
}

@Composable
fun ColumnScope.CreateChildTransitionDemo() {
    DescItem(title = "使用 createChildTransition 创建子过渡")
    var dialerState by remember { mutableStateOf(DialerState.DialerMinimized) }
    Button(onClick = {
        dialerState = if (dialerState == DialerState.DialerMinimized)
            DialerState.NumberPad else DialerState.DialerMinimized
    }) {
        Text("Switch")
    }
    Dialer(dialerState = dialerState)
}

enum class DialerState {
    DialerMinimized, NumberPad
}

@Composable
fun DialerButton(
    isVisibleTransition: Transition<Boolean>,
    modifier: Modifier = Modifier
) {
    val alpha by isVisibleTransition.animateFloat(
        transitionSpec = { tween(5000) },
        label = "DialerButton"
    ) { visible ->
        if (visible) 1F else 0F
    }
    Button(
        onClick = {  },
        modifier = modifier.alpha(alpha)
    ) {
        Icon(Icons.Default.Call, "")
    }
}

@Composable
fun NumberPad(
    isVisibleTransition: Transition<Boolean>,
    modifier:Modifier = Modifier,
) {
    val alpha by isVisibleTransition.animateFloat(
        transitionSpec = { tween(5000) },
        label = "NumberPad alpha"
    ) { visible ->
        if (visible) 1F else 0F
    }
    val size by isVisibleTransition.animateDp(
        transitionSpec = { tween(5000) },
        label = "NumberPad size"
    ) { visible ->
        if (visible) 200.dp else 0.dp
    }
    Box(modifier = modifier
        .size(size)
        .background(Color.LightGray)
        .graphicsLayer(alpha = alpha)
    )
}

@OptIn(ExperimentalTransitionApi::class)
@Composable
fun Dialer(dialerState: DialerState) {
    val transition = updateTransition(dialerState, "Dialer Transition")
    Box(Modifier.size(200.dp)) {
        NumberPad(
            transition.createChildTransition {
                it == DialerState.NumberPad
            }
        )
        DialerButton(
            transition.createChildTransition {
                it == DialerState.DialerMinimized
            },
            Modifier.align(Alignment.Center)
        )
    }
}

@OptIn(ExperimentalMaterialApi::class, ExperimentalAnimationApi::class)
@Composable
fun ColumnScope.TransitionWithAnimatedVisibilityAndAnimatedContentDemo() {
    DescItem(title = "Transition 配合 AnimatedVisibility 和 AnimatedContent 使用")
    var selected by remember { mutableStateOf(false) }
    val transition = updateTransition(targetState = selected, label = "")
    val borderColor by transition.animateColor(label = "") { selected ->
        if (selected) Color.Magenta else Color.White
    }
    val elevation by transition.animateDp(label = "") { selected ->
        if (selected) 10.dp else 2.dp
    }
    Box(modifier = Modifier
        .height(120.dp)
        .padding(10.dp)) {
        Surface(
            onClick = { selected = !selected },
            shape = RoundedCornerShape(8.dp),
            border = BorderStroke(2.dp, borderColor),
            elevation = elevation,
        ) {
            Column(modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)) {
                Text("Hello world")
                transition.AnimatedVisibility(
                    visible = { targetSelected -> targetSelected },
                    enter = expandVertically(),
                    exit = shrinkVertically()
                ) {
                    Text("It is a fine day")
                }
                transition.AnimatedContent { targetSelected ->
                    if (targetSelected) {
                        Text("Selected")
                    } else {
                        Icon(Icons.Default.Phone, "")
                    }
                }
            }
        }
    }
}

@Composable
fun ColumnScope.ReuseTransitionDemo() {
    DescItem(title = "复杂动画场景下封装 Transition")
    var currentState by remember { mutableStateOf(BoxState.Collapsed) }
    val boxTransitionData = updateTransitionData(currentState)
    Box(
        modifier = Modifier
            .clickable {
                currentState =
                    if (currentState == BoxState.Collapsed) BoxState.Expanded else BoxState.Collapsed
            }
            .padding(5.dp)
            .size(boxTransitionData.boxSize.dp)
            .background(boxTransitionData.boxBg)
            .border(width = boxTransitionData.borderWidth, color = boxTransitionData.borderColor)
    )
}

private class BoxTransitionData(
    boxSize: State<Float>,
    boxBg: State<Color>,
    borderWidth: State<Dp>,
    borderColor: State<Color>,
) {
    val boxSize by boxSize
    val boxBg by boxBg
    val borderWidth by borderWidth
    val borderColor by borderColor
}

@Composable
private fun updateTransitionData(boxState: BoxState): BoxTransitionData {
    val transition = updateTransition(targetState = boxState, "")
    val size = transition.animateFloat(transitionSpec = { tween(500) }, label = "size") {
        it.valueOf(100F, 200F)
    }
    val bg = transition.animateColor(transitionSpec = { tween(500) }, label = "bg") {
        it.valueOf(Color.Green, Color.Cyan)
    }
    val borderWidth = transition.animateDp(transitionSpec = { tween(500) }, label = "borderWidth") {
        it.valueOf(1.dp, 2.dp)
    }
    val borderColor = transition.animateColor(transitionSpec = { tween(500) }, label = "borderColor") {
        it.valueOf(Color.Red, Color.Blue)
    }
    return remember(transition) { BoxTransitionData(size, bg, borderWidth, borderColor) }
}

@Composable
fun ColumnScope.InfiniteTransitionDemo1() {
    DescItem(title = "使用 rememberInfiniteTransition 实现无限循环动画")
    val transition = rememberInfiniteTransition()
    val color by transition.animateColor(
        initialValue = Color.Red,
        targetValue = Color.Blue,
        animationSpec = infiniteRepeatable(
            animation = tween(1000, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        )
    )
    val roundPercent by transition.animateFloat(
        initialValue = 0F,
        targetValue = 100F,
        animationSpec = infiniteRepeatable(
            animation = tween(1000, easing = FastOutLinearInEasing),
            repeatMode = RepeatMode.Reverse
        )
    )

    val offset by transition.animateValue(
        initialValue = Offset(0F,0F),
        targetValue = Offset(100F, 100F),
        typeConverter = Offset.VectorConverter,
        animationSpec = infiniteRepeatable(
            animation = tween(1000, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        )
    )

    Box(Modifier.size(300.dp)) {
        Box(
            Modifier
                .padding(10.dp)
                .size(100.dp)
                .offset(offset.x.dp, offset.y.dp)
                .clip(RoundedCornerShape(roundPercent.toInt()))
                .background(color)
        )
    }
}

@Composable
fun ColumnScope.InfiniteRepeatableDemo() {
    DescItem(title = "InfiniteRepeatable 实现旋转动画")
    val infiniteTransition = rememberInfiniteTransition()
    val degrees by infiniteTransition.animateFloat(
        initialValue = 0F,
        targetValue = 360F,
        animationSpec = infiniteRepeatable(
            animation = keyframes {
                durationMillis = 3000
                0F at 0
                360F at 3000
            },
            repeatMode = RepeatMode.Restart
        )
    )
    Box(
        Modifier
            .fillMaxWidth()
            .height(200.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "infiniteRepeatable",
            modifier = Modifier.rotate(degrees = degrees),
            color = Color.Red,
            fontSize = 22.sp
        )
    }
}

@Composable
fun ColumnScope.ImageBorderAnimation() {
    DescItem(title = "使用 rememberInfiniteTransition 实现头像边框旋转动画")
    val infiniteTransition = rememberInfiniteTransition()
    val degrees by infiniteTransition.animateFloat(
        initialValue = 0F,
        targetValue = 360F,
        animationSpec = infiniteRepeatable(
            animation = tween(1000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        )
    )
    Box(
        Modifier
            .fillMaxWidth()
            .height(200.dp),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.chandler),
            contentDescription = "",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(150.dp)
                .drawBehind {
                    rotate(degrees) {
                        drawCircle(
                            brush = Brush.sweepGradient(listOf(Color.Red, Color.Blue)),
                            style = Stroke(8.dp.toPx())
                        )
                    }
                }
                .padding(4.dp)
                .clip(CircleShape)
        )
    }
}