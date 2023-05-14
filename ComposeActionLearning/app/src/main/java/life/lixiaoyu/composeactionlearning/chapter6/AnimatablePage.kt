package life.lixiaoyu.composeactionlearning.chapter6

import androidx.compose.animation.Animatable
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import life.lixiaoyu.composeactionlearning.DescItem
import life.lixiaoyu.composeactionlearning.FullPageWrapper

@Composable
fun AnimatablePage() {
    FullPageWrapper {
        Column(Modifier.padding(10.dp)) {
            DiggEffectUsingAnimatable()
            CustomTwoWayConverter()
        }
    }

}

@Composable
fun DiggEffectUsingAnimatable() {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        DescItem(title = "使用 Aimatable 实现小红心点赞效果")
        var changeSize by remember { mutableStateOf(false) }
        var changeColor by remember { mutableStateOf(false) }
        val buttonSizeVariable = remember { androidx.compose.animation.core.Animatable(24.dp, Dp.VectorConverter) }
        val buttonColorVariable = remember { Animatable(Color.Gray) }
        LaunchedEffect(key1 = changeColor, key2 = changeSize) {
            // 注意，因为animateTo是挂起函数，会阻塞当前协程，
            // 所以这里必须分别放在launch中启动子协程执行，否则动画效果是顺序执行的
            // 或者，也可以分开放在两个LaunchedEffect里执行
            launch {
                buttonSizeVariable.animateTo(if (changeSize) 32.dp else 24.dp)
            }
            launch {
                buttonColorVariable.animateTo(if (changeColor) Color.Red else Color.Gray) {
                    println(this.value)
                }
            }
        }
        if (buttonSizeVariable.value == 32.dp) {
            changeSize = false
        }
        IconButton(onClick = {
            changeSize = true
            changeColor = !changeColor
        }) {
            Icon(
                Icons.Filled.Favorite,
                "",
                modifier = Modifier.size(buttonSizeVariable.value),
                tint = buttonColorVariable.value
            )
        }
    }
}

data class MySize(val width: Dp, val height: Dp)
val MySizeVectorConverter: TwoWayConverter<MySize, AnimationVector2D> = TwoWayConverter(
    {
        AnimationVector2D(it.width.value, it.height.value)
    }, {
        MySize(it.v1.dp, it.v2.dp)
    }
)
@Composable
fun CustomTwoWayConverter() {
    Column(
        modifier = Modifier.height(300.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        DescItem(title = "自定义 TwoWayConverter 使用 animateValueAsState 做尺寸改变动画")
        var enabled by remember { mutableStateOf(true) }
        val animSize: MySize by animateValueAsState(
            targetValue = if (enabled) MySize(200.dp, 100.dp) else MySize(100.dp, 50.dp),
            typeConverter = MySizeVectorConverter
        )
        Box(
            Modifier
                .size(animSize.width, animSize.height)
                .background(Color.Red)
                .clickable { enabled = !enabled }
        )
    }
}