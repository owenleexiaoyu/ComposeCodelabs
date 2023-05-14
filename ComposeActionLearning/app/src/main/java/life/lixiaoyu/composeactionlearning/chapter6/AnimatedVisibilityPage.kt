package life.lixiaoyu.composeactionlearning.chapter6

import androidx.compose.animation.*
import androidx.compose.animation.core.Transition
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import life.lixiaoyu.composeactionlearning.DescItem
import life.lixiaoyu.composeactionlearning.FullPageWrapper

@Composable
fun AnimatedVisibilityPage() {
    FullPageWrapper {
        Column(Modifier.verticalScroll(rememberScrollState())) {
            DescItem(title = "默认 AnimatedVisibility 动画效果")
            DefaultAnimatedVisibilityEffect()
            DescItem(title = "Column 中默认 AnimatedVisibility 动画效果")
            DefaultAnimatedVisibilityInColumnEffect()
            DescItem(title = "Row 中默认 AnimatedVisibility 动画效果")
            DefaultAnimatedVisibilityInRowEffect()
            DescItem(title = "自定义 AnimatedVisibility 动画效果")
            CustomAnimatedVisibilityEffect()
            DescItem(title = "AnimatedVisibility 子组件中使用 Modifier.animateEnterExit")
            ModifierAnimateEnterExitEffect()
            DescItem(title = "AnimatedVisibility 中给每个子组件中使用 Modifier.animateEnterExit 设置不同的动画")
            ModifierAnimateEnterExitEffect2()
            DescItem(title = "AnimatedVisibility 中添加额外的动画效果")
            CustomEnterExitAnimationEffect()
        }

    }
}

@Composable
fun DefaultAnimatedVisibilityEffect() {
    Box(Modifier.padding(horizontal = 10.dp)) {
        var isShow by remember { mutableStateOf(false) }
        AnimatedVisibility(visible = isShow) {
            MyBox()
        }
        Button(
            modifier = Modifier.padding(top = 160.dp),
            onClick = { isShow = !isShow }) {
            Text(text = if (isShow) "Hide" else "Show")
        }
    }
}

@Composable
fun DefaultAnimatedVisibilityInColumnEffect() {
    Column(Modifier.padding(horizontal = 10.dp)) {
        var isShow by remember { mutableStateOf(false) }
        AnimatedVisibility(visible = isShow) {
            MyBox()
        }
        Button(
            modifier = Modifier.padding(top = 10.dp),
            onClick = { isShow = !isShow }) {
            Text(text = if (isShow) "Hide" else "Show")
        }
    }
}

@Composable
fun DefaultAnimatedVisibilityInRowEffect() {
    Row(Modifier.padding(horizontal = 10.dp)) {
        var isShow by remember { mutableStateOf(false) }
        AnimatedVisibility(visible = isShow) {
            MyBox()
        }
        Button(
            modifier = Modifier.padding(start = 10.dp),
            onClick = { isShow = !isShow }) {
            Text(text = if (isShow) "Hide" else "Show")
        }
    }
}

@Composable
fun CustomAnimatedVisibilityEffect() {
    val density = LocalDensity.current
    Box(Modifier.padding(horizontal = 10.dp)) {
        var isShow by remember { mutableStateOf(false) }
        AnimatedVisibility(
            visible = isShow,
            enter = slideInVertically { with(density) { -40.dp.roundToPx() } }
                + expandVertically(expandFrom = Alignment.Top)
                + fadeIn(initialAlpha = 0.3F),
            exit = slideOutVertically() + shrinkVertically() + fadeOut()
        ) {
            MyBox()
        }
        Button(
            modifier = Modifier.padding(top = 160.dp),
            onClick = { isShow = !isShow }) {
            Text(text = if (isShow) "Hide" else "Show")
        }
    }
}

/**
 * 测试在 AnimatedVisibility 的子组件中使用
 * Modifier.animateEnterExit 修饰符控制子组件的进场出场动画
 */
@OptIn(ExperimentalAnimationApi::class)
@Composable
fun ModifierAnimateEnterExitEffect() {
    Box(Modifier.padding(horizontal = 10.dp)) {
        var isShow by remember { mutableStateOf(false) }
        AnimatedVisibility(
            visible = isShow,
            enter = fadeIn(),
            exit = fadeOut()
        ) {
            MyBox(
                modifier = Modifier.animateEnterExit(
                    enter = slideInVertically(),
                    exit = slideOutVertically()
                ),
                size = DpSize(150.dp, 50.dp),
                color = Color.Cyan
            )
        }
        Button(
            modifier = Modifier.padding(top = 60.dp),
            onClick = { isShow = !isShow }) {
            Text(text = if (isShow) "Hide" else "Show")
        }
    }
}

/**
 * 测试在 AnimatedVisibility 的多个子组件中使用
 * Modifier.animateEnterExit 修饰符给每个子组件设置不同的动画
 */
@OptIn(ExperimentalAnimationApi::class)
@Composable
fun ModifierAnimateEnterExitEffect2() {
    Box(Modifier.padding(horizontal = 10.dp)) {
        var isShow by remember { mutableStateOf(false) }
        AnimatedVisibility(
            visible = isShow,
            enter = EnterTransition.None,
            exit = ExitTransition.None
        ) {
            Column {
                MyBox(
                    modifier = Modifier.animateEnterExit(
                        enter = fadeIn() + slideInVertically(),
                        exit = fadeOut() + slideOutVertically()
                    ),
                    size = DpSize(150.dp, 50.dp),
                    color = Color.Cyan
                )
                MyBox(
                    modifier = Modifier.animateEnterExit(
                        enter = scaleIn(),
                        exit = scaleOut()
                    ),
                    size = DpSize(150.dp, 50.dp),
                    color = Color.Blue
                )
            }
        }
        Button(
            modifier = Modifier.padding(top = 110.dp),
            onClick = { isShow = !isShow }) {
            Text(text = if (isShow) "Hide" else "Show")
        }
    }
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun CustomEnterExitAnimationEffect() {
    Box(Modifier.padding(horizontal = 10.dp)) {
        var isShow by remember { mutableStateOf(false) }
        AnimatedVisibility(
            visible = isShow,
            enter = scaleIn(),
            exit = scaleOut()
        ) {
            val background by transition.animateColor(label = "AnimateColor") { state ->
                if (state == EnterExitState.Visible) Color.Green else Color.Gray
            }
            MyBox(color = background)
        }
        Button(
            modifier = Modifier.padding(top = 160.dp),
            onClick = { isShow = !isShow }) {
            Text(text = if (isShow) "Hide" else "Show")
        }
    }
}

@Composable
private fun MyBox(
    modifier: Modifier = Modifier,
    size: DpSize = DpSize(150.dp, 150.dp),
    color: Color = Color.Green
) {
    Box(modifier = modifier
        .size(size)
        .background(color))
}
