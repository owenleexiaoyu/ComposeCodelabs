package life.lixiaoyu.composeactionlearning.chapter2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.*
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import life.lixiaoyu.composeactionlearning.*
import life.lixiaoyu.composeactionlearning.R
import life.lixiaoyu.composeactionlearning.ui.theme.Purple500

class LayoutComponentDemoActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FullPageWrapper {
                LayoutComponentPage()
            }
        }
    }
}

@Composable
fun LayoutComponentPage() {
    Column(
        modifier = Modifier
            .padding(5.dp)
            .verticalScroll(rememberScrollState()),
    ) {
        ColumnDemo()
        RowDemo()
        BoxDemo()
        SpacerDemo()
        SurfaceDemo()
    }
}

@Composable
fun SurfaceDemo() {
    DescItem(title = "Surface")
    Surface(
        shape = RoundedCornerShape(10.dp),
        color = Color.White,
        elevation = 5.dp
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Image(
                painter = painterResource(id = R.drawable.chandler),
                contentDescription = "",
                modifier = Modifier.size(100.dp)
            )
            Column(modifier = Modifier.padding(start = 10.dp, end = 50.dp)) {
                Text("Chandler Bing", fontSize = 18.sp, fontWeight = FontWeight.SemiBold)
                VerticalSpacer(height = 10.dp)
                Text("Monica's husband")
            }
        }
    }
}

@Composable
fun ColumnScope.SpacerDemo() {
    DescItem(title = "Spacer")
    var horizontalSpacerValue by remember {
        mutableStateOf(0)
    }
    Row(verticalAlignment = Alignment.CenterVertically) {
        Text("红黑块间距(0~100):$horizontalSpacerValue dp")
        HorizontalSpacer(width = 5.dp)
        OutlinedButton(
            onClick = {
                if (horizontalSpacerValue > 0) {
                    horizontalSpacerValue -= 10
                }
            }
        ) {
            Text("-10dp")
        }
        HorizontalSpacer(width = 5.dp)
        OutlinedButton(
            onClick = {
                if (horizontalSpacerValue < 100) {
                    horizontalSpacerValue += 10
                }
            }
        ) {
            Text("+10dp")
        }
    }
    Row {
        Box(modifier = Modifier
            .size(height = 40.dp, width = 60.dp)
            .clip(RoundedCornerShape(8.dp))
            .background(Color.Red)
        )
        HorizontalSpacer(width = horizontalSpacerValue.dp)
        Box(modifier = Modifier
            .size(height = 40.dp, width = 60.dp)
            .clip(RoundedCornerShape(8.dp))
            .background(Color.Black)
        )
    }
}

@Composable
fun ColumnScope.BoxDemo() {
    DescItem(title = "Box")
    val contentAlignmentList = listOf(
        "Alignment.TopStart" to Alignment.TopStart,
        "Alignment.TopCenter" to Alignment.TopCenter,
        "Alignment.TopEnd" to Alignment.TopEnd,
        "Alignment.CenterStart" to Alignment.CenterStart,
        "Alignment.Center" to Alignment.Center,
        "Alignment.CenterEnd" to Alignment.CenterEnd,
        "Alignment.BottomStart" to Alignment.BottomStart,
        "Alignment.BottomCenter" to Alignment.BottomCenter,
        "Alignment.BottomEnd" to Alignment.BottomEnd,
    )
    var contentAlignmentSelectedIndex by remember {
        mutableStateOf(0)
    }
    Text("Box contentAlignment", fontWeight = FontWeight.Bold)
    VerticalSpacer(height = 5.dp)
    Row(
        modifier = Modifier
            .horizontalScroll(rememberScrollState()),
        verticalAlignment = Alignment.CenterVertically
    ) {
        contentAlignmentList.forEachIndexed { index, pair ->
            RadioButton(selected = contentAlignmentSelectedIndex == index, onClick = {
                contentAlignmentSelectedIndex = index
            })
            Text(pair.first)
        }
    }
    Box(
        modifier = Modifier
            .size(200.dp)
            .background(Purple500)
            .align(Alignment.CenterHorizontally),
        contentAlignment = contentAlignmentList[contentAlignmentSelectedIndex].second
    ) {
        Box(modifier = Modifier
            .size(150.dp)
            .background(Color.Black)
        )
        Box(modifier = Modifier
            .size(100.dp)
            .background(Color.Yellow)
        )
        Box(modifier = Modifier
            .size(50.dp)
            .background(Color.Red)
        )
    }
}

@Composable
fun ColumnScope.ColumnDemo() {
    DescItem(title = "Column")
    val verticalArrangementList = listOf(
        "Arrangement.Top" to Arrangement.Top,
        "Arrangement.Center" to Arrangement.Center,
        "Arrangement.Bottom" to Arrangement.Bottom,
        "Arrangement.SpaceAround" to Arrangement.SpaceAround,
        "Arrangement.SpaceBetween" to Arrangement.SpaceBetween,
        "Arrangement.SpaceEvenly" to Arrangement.SpaceEvenly,
    )
    val horizontalAlignmentList = listOf(
        "Alignment.Start" to Alignment.Start,
        "Alignment.CenterHorizontally" to Alignment.CenterHorizontally,
        "Alignment.End" to Alignment.End,
    )
    var verticalArrangementSelectedIndex by remember {
        mutableStateOf(0)
    }
    var horizontalAlignmentSelectedIndex by remember {
        mutableStateOf(0)
    }
    Text("Column verticalArrangement", fontWeight = FontWeight.Bold)
    VerticalSpacer(height = 5.dp)
    Row(
        modifier = Modifier
            .horizontalScroll(rememberScrollState()),
        verticalAlignment = Alignment.CenterVertically
    ) {
        verticalArrangementList.forEachIndexed { index, pair ->
            RadioButton(selected = verticalArrangementSelectedIndex == index, onClick = {
                verticalArrangementSelectedIndex = index
            })
            Text(pair.first)
        }
    }
    Text("Column horizontalAlignment", fontWeight = FontWeight.Bold)
    VerticalSpacer(height = 5.dp)
    Row(
        modifier = Modifier
            .horizontalScroll(rememberScrollState()),
        verticalAlignment = Alignment.CenterVertically
    ) {
        horizontalAlignmentList.forEachIndexed { index, pair ->
            RadioButton(selected = horizontalAlignmentSelectedIndex == index, onClick = {
                horizontalAlignmentSelectedIndex = index
            })
            Text(pair.first)
        }
    }
    Column(
        modifier = Modifier
            .size(width = 300.dp, height = 150.dp)
            .background(Purple500)
            .align(Alignment.CenterHorizontally),
        verticalArrangement = verticalArrangementList[verticalArrangementSelectedIndex].second,
        horizontalAlignment =  horizontalAlignmentList[horizontalAlignmentSelectedIndex].second
    ) {
        Box(modifier = Modifier
            .size(height = 20.dp, width = 200.dp)
            .background(Color.Black)
        )
        Box(modifier = Modifier
            .size(height = 20.dp, width = 100.dp)
            .background(Color.Yellow)
        )
        Box(modifier = Modifier
            .size(height = 20.dp, width = 150.dp)
            .background(Color.Red)
        )
    }

}

@Composable
fun ColumnScope.RowDemo() {
    DescItem(title = "Row")
    val horizontalArrangementList = listOf(
        "Arrangement.Start" to Arrangement.Start,
        "Arrangement.Center" to Arrangement.Center,
        "Arrangement.End" to Arrangement.End,
        "Arrangement.SpaceAround" to Arrangement.SpaceAround,
        "Arrangement.SpaceBetween" to Arrangement.SpaceBetween,
        "Arrangement.SpaceEvenly" to Arrangement.SpaceEvenly,
    )
    val verticalAlignmentList = listOf(
        "Alignment.Top" to Alignment.Top,
        "Alignment.CenterVertically" to Alignment.CenterVertically,
        "Alignment.Bottom" to Alignment.Bottom,
    )
    var horizontalArrangementSelectedIndex by remember {
        mutableStateOf(0)
    }
    var verticalAlignmentSelectedIndex by remember {
        mutableStateOf(0)
    }
    Text("Row horizontalArrangement", fontWeight = FontWeight.Bold)
    VerticalSpacer(height = 5.dp)
    Row(
        modifier = Modifier
            .horizontalScroll(rememberScrollState()),
        verticalAlignment = Alignment.CenterVertically
    ) {
        horizontalArrangementList.forEachIndexed { index, pair ->
            RadioButton(selected = horizontalArrangementSelectedIndex == index, onClick = {
                horizontalArrangementSelectedIndex = index
            })
            Text(pair.first)
        }
    }
    Text("Row verticalAlignment", fontWeight = FontWeight.Bold)
    VerticalSpacer(height = 5.dp)
    Row(
        modifier = Modifier
            .horizontalScroll(rememberScrollState()),
        verticalAlignment = Alignment.CenterVertically
    ) {
        verticalAlignmentList.forEachIndexed { index, pair ->
            RadioButton(selected = verticalAlignmentSelectedIndex == index, onClick = {
                verticalAlignmentSelectedIndex = index
            })
            Text(pair.first)
        }
    }
    Row(
        modifier = Modifier
            .size(width = 300.dp, height = 150.dp)
            .background(Purple500)
            .align(Alignment.CenterHorizontally),
        horizontalArrangement = horizontalArrangementList[horizontalArrangementSelectedIndex].second,
        verticalAlignment =  verticalAlignmentList[verticalAlignmentSelectedIndex].second
    ) {
        Box(modifier = Modifier
            .size(height = 100.dp, width = 50.dp)
            .background(Color.Black)
        )
        Box(modifier = Modifier
            .size(height = 60.dp, width = 50.dp)
            .background(Color.Yellow)
        )
        Box(modifier = Modifier
            .size(height = 80.dp, width = 50.dp)
            .background(Color.Red)
        )
    }

}

@Composable
@Preview(showBackground = true)
fun LayoutComponentPagePreview() {
    FullPageWrapper {
        LayoutComponentPage()
    }
}