package life.lixiaoyu.composeactionlearning.chapter5

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.AlignmentLine
import androidx.compose.ui.layout.FirstBaseline
import androidx.compose.ui.layout.layout
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import life.lixiaoyu.composeactionlearning.DescItem
import life.lixiaoyu.composeactionlearning.FullPageWrapper
import life.lixiaoyu.composeactionlearning.HorizontalSpacer

@Composable
fun FirstBaselineToTopPage(navController: NavController) {
    Column {
        DescItem(title = "第一个：paddingTop(30.dp); 第二个：firstBaselineToTop(30.dp); 第三个：firstBaselineToTop(30.dp) fontSize=20.sp; 第三个：paddingFromBaseline(top=30.dp)")
        Row {
            Text(
                "Hello",
                modifier = Modifier
                    .background(Color.Green)
                    .padding(top = 30.dp)
            )
            HorizontalSpacer(width = 10.dp)
            Box(Modifier.background(Color.Blue)) {
                Text(
                    "Hello",
                    modifier = Modifier
                        .firstBaselineToTop(firstBaselineToTop = 30.dp)
                )
            }
            HorizontalSpacer(width = 10.dp)
            Box(Modifier.background(Color.Blue)) {
                Text(
                    "Hello",
                    modifier = Modifier
                        .firstBaselineToTop(firstBaselineToTop = 30.dp),
                    fontSize = 20.sp
                )
            }
            HorizontalSpacer(width = 10.dp)
            Text(
                "Hello",
                modifier = Modifier
                    .background(Color.Yellow)
                    .paddingFromBaseline(top = 30.dp)
            )
        }
    }
}

@SuppressLint("ModifierFactoryUnreferencedReceiver")
fun Modifier.firstBaselineToTop(
    firstBaselineToTop: Dp
) = Modifier.layout { measurable, constraints ->
    // 采用布局约束对组件完成测量，测量结果保存在 Placeable 实例中
    val placeable = measurable.measure(constraints)
    // 确保该组件存在内容基线
    check(placeable[FirstBaseline] != AlignmentLine.Unspecified)
    // 获取极限的高度
    val firstBaseline = placeable[FirstBaseline]
    // 应该摆放的顶部高度是所设置的顶部到基线的高度减去实际组件内容顶部到基线的高度
    val placeableY = firstBaselineToTop.roundToPx() - firstBaseline
    // 该组件的高度是摆放的顶部高度加上实际内容的高度
    val height = placeable.height + placeableY
    // 只有高度发生了改变
    layout(placeable.width, height) {
        placeable.placeRelative(0, placeableY)
    }
}

@Preview
@Composable
fun FirstBaselineToTopPreview() {
    FullPageWrapper {
        FirstBaselineToTopPage(navController = rememberNavController())
    }
}