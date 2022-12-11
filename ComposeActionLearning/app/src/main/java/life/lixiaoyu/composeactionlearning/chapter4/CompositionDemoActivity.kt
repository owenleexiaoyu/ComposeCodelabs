package life.lixiaoyu.composeactionlearning.chapter4

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import life.lixiaoyu.composeactionlearning.FullPageWrapper
import life.lixiaoyu.composeactionlearning.HorizontalSpacer

/**
 * 计数器示例演示重组过程
 */
class CompositionDemoActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FullPageWrapper {
                CounterComposition()
            }
        }
    }
}

@Composable
fun CounterComposition() {
    var counter by remember { mutableStateOf(0) }
    Column(
        modifier = Modifier.padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Click the buttons to adjust your value:") // Text1
        Text("$counter", fontSize = 80.sp)  //Text2
        Row {
            Button(
                modifier = Modifier.weight(1F),
                onClick = {
                    if (counter > 0) {
                        counter--
                    }
                }) {
                Text("-")
            }
            HorizontalSpacer(width = 10.dp)
            Button(
                modifier = Modifier.weight(1F),
                onClick = {
                    counter++
                }) {
                Text("+")
            }
        }
    }
}

