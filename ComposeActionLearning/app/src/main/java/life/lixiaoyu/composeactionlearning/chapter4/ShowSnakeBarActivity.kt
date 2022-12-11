package life.lixiaoyu.composeactionlearning.chapter4

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.tooling.preview.Preview
import kotlinx.coroutines.launch
import life.lixiaoyu.composeactionlearning.FullPageWrapper

class ShowSnakeBarActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FullPageWrapper {
                ShowSnackBarPage()
            }
        }
    }
}

/**
 * 简单的 UI 状态以及配套逻辑适合在 Composable 中直接管理
 */
@Composable
fun ShowSnackBarPage() {

    val scaffoldState = rememberScaffoldState()
    val coroutineScope = rememberCoroutineScope()

    Scaffold(
        scaffoldState = scaffoldState
    ) {
        Button(onClick = {
            coroutineScope.launch {
                scaffoldState.snackbarHostState.showSnackbar("Hello world")
            }
        }) {
            Text("Show SnackBar")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ShowSnackBarPagePreview() {
    FullPageWrapper {
        ShowSnackBarPage()
    }
}