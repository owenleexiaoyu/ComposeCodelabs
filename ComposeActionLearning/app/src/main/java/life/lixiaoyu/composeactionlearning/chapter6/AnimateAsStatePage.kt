package life.lixiaoyu.composeactionlearning.chapter6

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp
import life.lixiaoyu.composeactionlearning.DescItem
import life.lixiaoyu.composeactionlearning.FullPageWrapper

@Composable
fun AnimateAsStatePage() {
    FullPageWrapper {
        Column(Modifier.padding(10.dp)) {
            BoxAlphaSwitchEffect()
            DiggEffect()
        }
    }
}

@Composable
fun BoxAlphaSwitchEffect() {
    Column {
        DescItem(title = "使用 animateFloatAsState 透明度改变动画")
        var enabled by remember { mutableStateOf(true) }
        val alpha by animateFloatAsState(targetValue = if (enabled) 1F else 0.2F)
        Box(
            Modifier
                .size(120.dp)
                .graphicsLayer(alpha = alpha)
                .background(Color.Red)
                .clickable { enabled = !enabled }
        )
    }
}

@Composable
fun DiggEffect() {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        DescItem(title = "使用 animate*AsState 实现小红心点赞效果")
        var changeSize by remember { mutableStateOf(false) }
        var changeColor by remember { mutableStateOf(false) }
        val buttonSize by animateDpAsState(targetValue = if (changeSize) 32.dp else 24.dp)
        val buttonColor by animateColorAsState(
            targetValue = if (changeColor) Color.Red else Color.Gray,
            animationSpec = spring(Spring.DampingRatioNoBouncy)
        )
        if (buttonSize == 32.dp) {
            changeSize = false
        }
        IconButton(onClick = {
            changeSize = true
            changeColor = !changeColor
        }) {
            Icon(
                Icons.Filled.Favorite,
                "",
                modifier = Modifier.size(buttonSize),
                tint = buttonColor
            )
        }
    }
}

