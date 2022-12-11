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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import life.lixiaoyu.composeactionlearning.FullPageWrapper
import life.lixiaoyu.composeactionlearning.HorizontalSpacer

class Counter5Activity: ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FullPageWrapper {
                CounterScreen()
            }
        }
    }
}

@Composable
fun CounterScreen() {
    var counter by remember { mutableStateOf(0) }
    CounterComponent(
        counter = counter,
        onIncrement = {
            counter++
        },
        onDecrement = {
            if (counter > 0) {
                counter--
            }
        }
    )
}

/**
 * Stateless
 */
@Composable
fun CounterComponent(
    counter: Int,
    onIncrement: () -> Unit,
    onDecrement: () -> Unit
) {
    Column(
        modifier = Modifier.padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Click the buttons to adjust your value:")
        Text("$counter", fontSize = 80.sp)
        Row {
            Button(
                modifier = Modifier.weight(1F),
                onClick = {
                    onDecrement()
                }) {
                Text("-")
            }
            HorizontalSpacer(width = 10.dp)
            Button(
                modifier = Modifier.weight(1F),
                onClick = {
                    onIncrement()
                }) {
                Text("+")
            }
        }
    }

}

@Preview(showBackground = true)
@Composable
fun CounterScreenPreview() {
    FullPageWrapper {
        CounterScreen()
    }
}