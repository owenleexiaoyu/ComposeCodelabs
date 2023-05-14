package life.lixiaoyu.composeactionlearning.chapter6

import androidx.compose.animation.*
import androidx.compose.animation.core.keyframes
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import life.lixiaoyu.composeactionlearning.DescItem
import life.lixiaoyu.composeactionlearning.FullPageWrapper
import life.lixiaoyu.composeactionlearning.HorizontalSpacer
import life.lixiaoyu.composeactionlearning.VerticalSpacer

@Composable
fun AnimatedContentPage() {
    FullPageWrapper {
        Column(
            Modifier
                .padding(10.dp)
                .verticalScroll(rememberScrollState())
        ) {
            DescItem(title = "AnimatedContent 简单用法，默认效果")
            FadeContentTransitionEffect()
            DescItem(title = "横向平移的 ContentTransition")
            HorizontalSlideContentTransitionEffect()
            DescItem(title = "纵向平移的 ContentTransition")
            VerticalSlideContentTransitionEffect()
            DescItem(title = "文字上下滚动动画效果")
            TextVerticalSlideEffect()
            DescItem(title = "使用 slideIntoContainer 实现文字上下滚动动画效果")
            SlideIntoContainerEffect()
            DescItem(title = "SizeTransform 效果")
            SizeTransformEffect()
        }
    }
}

/**
 * 淡入淡出的内容切换动画
 */
@OptIn(ExperimentalAnimationApi::class)
@Composable
fun FadeContentTransitionEffect() {
    Column {
        var count by remember { mutableStateOf(0) }
        Button(onClick = { count++ }) {
            Text("Add")
        }
        Text(text = "Count = $count", modifier = Modifier
            .padding(top = 5.dp)
            .background(
                Color.Cyan
            ))
        VerticalSpacer(height = 5.dp)
        AnimatedContent(targetState = count) { targetCount: Int ->
            Text(text = "Count = $targetCount", modifier = Modifier
                .padding(top = 5.dp)
                .background(
                    Color.Green
                ))
        }
    }
}

/**
 * 横向平移的内容变化动画
 */
@OptIn(ExperimentalAnimationApi::class)
@Composable
fun HorizontalSlideContentTransitionEffect() {
    Column {
        var count by remember { mutableStateOf(0) }
        Button(onClick = { count++ }) {
            Text("Add")
        }
        Text(text = "Count = $count", modifier = Modifier
            .padding(top = 5.dp)
            .background(
                Color.Cyan
            ))
        VerticalSpacer(height = 5.dp)
        AnimatedContent(
            targetState = count,
            transitionSpec = {
                slideInHorizontally { fullWidth: Int -> fullWidth } + fadeIn() with
                        slideOutHorizontally { fullWidth: Int -> -fullWidth } + fadeOut()
            }
        ) { targetCount: Int ->
            Text(text = "Count = $targetCount", modifier = Modifier
                .padding(top = 5.dp)
                .background(
                    Color.Green
                ))
        }
    }
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun VerticalSlideContentTransitionEffect() {
    Column {
        var count by remember { mutableStateOf(0) }
        Button(onClick = { count++ }) {
            Text("Add")
        }
        Text(text = "$count", fontSize = 20.sp, fontWeight = FontWeight.SemiBold)
        VerticalSpacer(height = 5.dp)
        val animationSpec = tween<IntOffset>(200)
        val animationSpec2 = tween<Float>(200)
        AnimatedContent(
            targetState = count,
            transitionSpec = {
                slideInVertically(animationSpec) { height: Int -> height } + fadeIn(animationSpec2) with
                        slideOutVertically(animationSpec) { height: Int -> height } + fadeOut(animationSpec2)
            }
        ) { targetCount: Int ->
            Text(text = "$targetCount", fontSize = 20.sp, fontWeight = FontWeight.SemiBold)
        }
    }
}

/**
 * 文字上下滚动的动画效果
 */
@OptIn(ExperimentalAnimationApi::class)
@Composable
fun TextVerticalSlideEffect() {
    Column {
        var count by remember { mutableStateOf(0) }
        Row {
            Button(onClick = { count-- }) {
                Text("-")
            }
            HorizontalSpacer(width = 20.dp)
            Button(onClick = { count++ }) {
                Text("+")
            }
        }
        Text(text = "$count", fontSize = 20.sp, fontWeight = FontWeight.SemiBold)
        VerticalSpacer(height = 5.dp)
        AnimatedContent(
            targetState = count,
            transitionSpec = {
                if (targetState > initialState) {
                    // 如果目标值更大，从下往上出现动画
                    slideInVertically { height: Int -> height } + fadeIn() with
                            slideOutVertically { height: Int -> -height } + fadeOut()
                } else {
                    // 如果目标值更大，从上往下出现动画
                    slideInVertically { height: Int -> -height } + fadeIn() with
                            slideOutVertically { height: Int -> height } + fadeOut()
                }
            }
        ) { targetCount: Int ->
            Text(text = "$targetCount", fontSize = 20.sp, fontWeight = FontWeight.SemiBold)
        }
    }
}

/**
 * 使用 slideIntoContainer 实现文字上下滚动的动画效果
 */
@OptIn(ExperimentalAnimationApi::class)
@Composable
fun SlideIntoContainerEffect() {
    Column {
        var count by remember { mutableStateOf(0) }
        Row {
            Button(onClick = { count-- }) {
                Text("-")
            }
            HorizontalSpacer(width = 20.dp)
            Button(onClick = { count++ }) {
                Text("+")
            }
        }
        Text(text = "$count", fontSize = 20.sp, fontWeight = FontWeight.SemiBold)
        VerticalSpacer(height = 5.dp)
        AnimatedContent(
            targetState = count,
            transitionSpec = {
                if (targetState > initialState) {
                    // 如果目标值更大，从下往上出现动画
                    slideIntoContainer(towards = AnimatedContentScope.SlideDirection.Up) + fadeIn() with
                            slideOutOfContainer(towards = AnimatedContentScope.SlideDirection.Up) + fadeOut()
                } else {
                    // 如果目标值更大，从上往下出现动画
                    slideIntoContainer(towards = AnimatedContentScope.SlideDirection.Down) + fadeIn() with
                            slideOutOfContainer(towards = AnimatedContentScope.SlideDirection.Down) + fadeOut()
                }
            }
        ) { targetCount: Int ->
            Text(text = "$targetCount", fontSize = 20.sp, fontWeight = FontWeight.SemiBold)
        }
    }
}

/**
 * 使用 using 组合 ContentTransition 和 SizeTransform
 */
@OptIn(ExperimentalMaterialApi::class, ExperimentalAnimationApi::class)
@Composable
fun SizeTransformEffect() {
    var expanded by remember { mutableStateOf(false) }
    Box(Modifier.fillMaxWidth()
        .height(150.dp)) {
        Surface(
            color = MaterialTheme.colors.primary,
            onClick = { expanded = !expanded }
        ) {
            AnimatedContent(
                targetState = expanded,
                transitionSpec = {
                    fadeIn(animationSpec = tween(150, 150)) with fadeOut(tween(150)) using
                            SizeTransform { initialSize, targetSize ->
                                if (targetState) {
                                    keyframes {
                                        // 展开时，先水平方向展开
                                        // 150ms之前：宽度从initialSize.width增大到targetSize.width，高度保持initialSize.Height不变
                                        // 150ms之后：宽度保持targetSize.width不变，高度从initialSize.height增大到targetSize.height
                                        IntSize(targetSize.width, initialSize.height) at 150
                                        durationMillis = 300
                                    }
                                } else {
                                    keyframes {
                                        // 收起时，先垂直方向收起
                                        // 150ms之前：宽度保持initialSize.width不变，高度从initialSize.height缩小到targetSize.height
                                        // 150ms之后：宽度从initialSize.width缩小到targetSize.width，高度保持targetSize.Height不变
                                        IntSize(initialSize.width, targetSize.height) at 150
                                        durationMillis = 300
                                    }
                                }
                            }
                }
            ) { targetExpanded ->
                if (targetExpanded) {
                    Text("This creates a SizeTransform with the provided clip and sizeAnimationSpec. " +
                            "By default, clip will be true. This means during the size animation, the content will be clipped to the animated size. sizeAnimationSpec defaults to return a spring animation.", Modifier.padding(10.dp))
                } else {
                    Icon(Icons.Default.ArrowDropDown, "")
                }
            }
        }
    }
}