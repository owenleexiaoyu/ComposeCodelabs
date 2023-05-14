package life.lixiaoyu.composeactionlearning.chapter6

import androidx.compose.animation.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.RadioButton
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import life.lixiaoyu.composeactionlearning.DescItem
import life.lixiaoyu.composeactionlearning.FullPageWrapper
import life.lixiaoyu.composeactionlearning.R

@OptIn(ExperimentalAnimationApi::class)
val enterTransitionList = listOf(
    "No animation" to EnterTransition.None,
    "fadeIn" to fadeIn(),
    "slideIn" to slideIn { IntOffset(0,0) },
    "slideInHorizontally" to slideInHorizontally(),
    "slideInVertically" to slideInVertically(),
    "scaleIn" to scaleIn(
        initialScale = 0.3F,
        transformOrigin = TransformOrigin(1F, 1F)
    ),
    "expandIn" to expandIn(
        initialSize = {
            IntSize(it.width / 3, it.height / 3)
        }
    ),
    "expandHorizontally" to expandHorizontally(),
    "expandVertically" to expandVertically()
)

@OptIn(ExperimentalAnimationApi::class)
val exitTransitionList = listOf(
    "No animation" to ExitTransition.None,
    "fadeOut" to fadeOut(),
    "slideOut" to slideOut { IntOffset(0,0) },
    "slideOutHorizontally" to slideOutHorizontally(),
    "slideOutVertically" to slideOutVertically(),
    "scaleOut" to scaleOut(),
    "shrinkOut" to shrinkOut(),
    "shrinkHorizontally" to shrinkHorizontally(),
    "shrinkVertically" to shrinkVertically()
)

@Composable
fun AnimatedVisibilityTransitionEffectPage() {
    FullPageWrapper {
        var selectedEnterTransitionIndex by remember { mutableStateOf(0) }
        var selectedExitTransitionIndex by remember { mutableStateOf(0) }
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            DescItem(title = "AnimatedVisibility Transition 效果展示")
            Row(Modifier.horizontalScroll(rememberScrollState()), verticalAlignment = Alignment.CenterVertically) {
                Text("EnterTransition: ")
                enterTransitionList.forEachIndexed { index, transition ->
                    RadioButton(selected = index == selectedEnterTransitionIndex, onClick = {
                        selectedEnterTransitionIndex = index
                    })
                    Text(transition.first)
                }
            }
            Row(Modifier.horizontalScroll(rememberScrollState()), verticalAlignment = Alignment.CenterVertically) {
                Text("ExitTransition: ")
                exitTransitionList.forEachIndexed { index, transition ->
                    RadioButton(selected = index == selectedExitTransitionIndex, onClick = {
                        selectedExitTransitionIndex = index
                    })
                    Text(transition.first)
                }
            }
            var isShow by remember { mutableStateOf(false) }
            Button(onClick = { isShow = !isShow }) {
                Text("Switch")
            }
            AnimatedVisibility(
                visible = isShow,
                enter = enterTransitionList[selectedEnterTransitionIndex].second,
                exit = exitTransitionList[selectedExitTransitionIndex].second,
                modifier = Modifier.padding(top = 20.dp)
            ) {
                Image(painter = painterResource(id = R.drawable.chandler),
                    contentDescription = "",
                    modifier = Modifier
                        .size(100.dp)
                        .clip(RoundedCornerShape(50.dp)))
            }
        }


    }
}