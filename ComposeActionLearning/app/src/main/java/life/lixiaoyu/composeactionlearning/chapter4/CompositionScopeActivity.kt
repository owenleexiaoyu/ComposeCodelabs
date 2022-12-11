package life.lixiaoyu.composeactionlearning.chapter4

import android.os.Bundle
import android.util.Log
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

/**
 * 计数器示例演示重组过程
 */
class CompositionScopeActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FullPageWrapper {
                CounterCompositionScope()
            }
        }
    }
}

val TAG = "CounterCompositionScope"

@Composable
fun CounterCompositionScope() { // Scope-1
    var counter by remember { mutableStateOf(0) }
    Log.d(TAG, "Scope-1 run")
    Column( // Scope-2
        modifier = Modifier.padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Log.d(TAG, "Scope-2 run")
        Text("Click the buttons to adjust your value:") // Text1
        Text("$counter", fontSize = 80.sp)  //Text2
        Button(
            onClick = {
                counter++
                Log.d(TAG, "Button onClick")
            }) { // Scope-3
            Log.d(TAG, "Scope-3 run")
            Text("+")
        }
    }
}

@Preview
@Composable
fun CounterCompositionScopePreview() {
    FullPageWrapper {
        CounterCompositionScope()
    }
}



