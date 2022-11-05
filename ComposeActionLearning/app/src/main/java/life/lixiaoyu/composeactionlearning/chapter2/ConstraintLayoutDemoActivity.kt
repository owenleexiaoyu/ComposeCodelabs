package life.lixiaoyu.composeactionlearning.chapter2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.RadioButton
import androidx.compose.material.Slider
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ChainStyle
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import life.lixiaoyu.composeactionlearning.*
import life.lixiaoyu.composeactionlearning.R
import life.lixiaoyu.composeactionlearning.ui.theme.Purple200

class ConstraintLayoutDemoActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FullPageWrapper {
                ConstraintLayoutPage()
            }
        }
    }
}

@Composable
fun ConstraintLayoutPage() {
    Column {
        Column {
            ConstraintLayoutDemo()
            BarrierDemo()
            GuidelineDemo()
            ChainDemo()
        }
    }
}

@Composable
fun ChainDemo() {
    DescItem(title = "ConstraintLayout Chain 的简单使用")
    val chainStyleList = listOf(
        "ChainStyle.Spread" to ChainStyle.Spread,
        "ChainStyle.Packed" to ChainStyle.Packed,
        "ChainStyle.SpreadInside" to ChainStyle.SpreadInside,
    )
    var chainStyleSelectedIndex by remember { mutableStateOf(0) }
    Text("ChainStyle", fontWeight = FontWeight.Bold)
    VerticalSpacer(height = 5.dp)
    Row(
        modifier = Modifier
            .horizontalScroll(rememberScrollState()),
        verticalAlignment = Alignment.CenterVertically
    ) {
        chainStyleList.forEachIndexed { index, pair ->
            RadioButton(selected = chainStyleSelectedIndex == index, onClick = {
                chainStyleSelectedIndex = index
            })
            Text(pair.first)
        }
    }
    ConstraintLayout(modifier = Modifier.padding(horizontal = 10.dp)
        .fillMaxWidth()
        .height(150.dp)
        .background(Purple200)
    ) {
        val (box1Ref, box2Ref, box3Ref, box4Ref) = remember { createRefs() }
        createHorizontalChain(box1Ref, box2Ref, box3Ref, box4Ref, chainStyle = chainStyleList[chainStyleSelectedIndex].second)
        Box(
            modifier = Modifier.size(width = 20.dp, height = 100.dp)
                .clip(RoundedCornerShape(4.dp))
                .border(2.dp, Color.Black, RoundedCornerShape(4.dp))
                .background(Color.Yellow)
                .constrainAs(box1Ref) {
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                }
        )
        Box(
            modifier = Modifier.size(width = 20.dp, height = 100.dp)
                .clip(RoundedCornerShape(4.dp))
                .border(2.dp, Color.Black, RoundedCornerShape(4.dp))
                .background(Color.Yellow)
                .constrainAs(box2Ref) {
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                }
        )
        Box(
            modifier = Modifier.size(width = 20.dp, height = 100.dp)
                .clip(RoundedCornerShape(4.dp))
                .border(2.dp, Color.Black, RoundedCornerShape(4.dp))
                .background(Color.Yellow)
                .constrainAs(box3Ref) {
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                }
        )
        Box(
            modifier = Modifier.size(width = 20.dp, height = 100.dp)
                .clip(RoundedCornerShape(4.dp))
                .border(2.dp, Color.Black, RoundedCornerShape(4.dp))
                .background(Color.Yellow)
                .constrainAs(box4Ref) {
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                }
        )
    }
}

@Composable
fun ColumnScope.GuidelineDemo() {
    DescItem(title = "ConstraintLayout guideline 基本使用")
    var fraction by remember { mutableStateOf(1/3F) }
    ConstraintLayout(modifier = Modifier
        .height(120.dp)
        .fillMaxWidth()) {
        val guideline = createGuidelineFromStart(fraction)
        val (box1Ref, box2Ref, avatorRef, titleRef) = remember { createRefs() }
        Box(modifier = Modifier
            .background(Purple200)
            .constrainAs(box1Ref) {
                start.linkTo(parent.start)
                end.linkTo(guideline)
                height = Dimension.matchParent
                width = Dimension.fillToConstraints
            })
        Box(modifier = Modifier
            .background(Color.Yellow)
            .constrainAs(box2Ref) {
                start.linkTo(guideline)
                end.linkTo(parent.end)
                height = Dimension.matchParent
                width = Dimension.fillToConstraints
            })
        Image(
            painter = painterResource(id = R.drawable.chandler),
            contentDescription = "",
            modifier = Modifier
                .size(80.dp)
                .clip(CircleShape)
                .constrainAs(avatorRef) {
                    start.linkTo(guideline)
                    end.linkTo(guideline)
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                }
        )
        Text(
            "Chandler Bing",
            fontSize = 18.sp,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.constrainAs(titleRef) {
                top.linkTo(parent.top)
                bottom.linkTo(parent.bottom)
                start.linkTo(avatorRef.end, 10.dp)
            }
        )
    }
    VerticalSpacer(height = 5.dp)
    Row(
        modifier = Modifier.padding(horizontal = 10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text("1/3")
        HorizontalSpacer(width = 5.dp)
        Slider(
            value = fraction,
            onValueChange = {
                fraction = it
            },
            valueRange = 1/3F .. 0.5F,
            modifier = Modifier.weight(1F)
        )
        HorizontalSpacer(width = 5.dp)
        Text("1/2")
    }

}

@Composable
fun ColumnScope.BarrierDemo() {
    DescItem(title = "ConstraintLayout barrier 基本使用")
    ConstraintLayout {
        val (usernameRef, passwordRef, usernameInputRef, passwordInputRef) = remember {
            createRefs()
        }
        Text("用户名", modifier = Modifier.constrainAs(usernameRef) {
            top.linkTo(parent.top, 30.dp)
            start.linkTo(parent.start, 10.dp)
        })
        Text("密码", modifier = Modifier.constrainAs(passwordRef) {
            top.linkTo(usernameRef.bottom, 40.dp)
            start.linkTo(usernameRef.start)
        })
        val barrier = createEndBarrier(usernameRef, passwordRef)
        OutlinedTextField(
            value = "",
            onValueChange = {},
            modifier = Modifier.constrainAs(usernameInputRef) {
                top.linkTo(usernameRef.top)
                bottom.linkTo(usernameRef.bottom)
                start.linkTo(barrier, 10.dp)
                end.linkTo(parent.end)
            }
        )
        OutlinedTextField(
            value = "",
            onValueChange = {},
            modifier = Modifier.constrainAs(passwordInputRef) {
                top.linkTo(passwordRef.top)
                bottom.linkTo(passwordRef.bottom)
                start.linkTo(barrier, 10.dp)
                end.linkTo(parent.end)
            }
        )
    }

}

@Composable
fun ConstraintLayoutDemo() {
    DescItem(title = "ConstraintLayout createRef createRefs linkTo 基本使用")
    ConstraintLayout(
        modifier = Modifier
            .height(150.dp)
            .fillMaxWidth()
            .background(Purple200)
    ) {
        val imageRef = remember { createRef() }
        val (titleRef, descRef) = remember { createRefs() }
        Image(
            painter = painterResource(id = R.drawable.chandler),
            "",
            modifier = Modifier
                .size(80.dp)
                .constrainAs(imageRef) {
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                    start.linkTo(parent.start, 10.dp)
                }
        )
        Text(
            "Chandler Bing",
            fontSize = 18.sp,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.constrainAs(titleRef) {
                top.linkTo(imageRef.top)
                start.linkTo(imageRef.end, 10.dp)
            }
        )
        Text(
            "Monica's husband",
            modifier = Modifier.constrainAs(descRef) {
                top.linkTo(titleRef.bottom, 5.dp)
                start.linkTo(titleRef.start)
            }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun ConstraintLayoutPagePreview() {
    FullPageWrapper {
        ConstraintLayoutPage()
    }
}