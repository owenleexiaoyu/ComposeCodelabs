package life.lixiaoyu.composeactionlearning

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import life.lixiaoyu.composeactionlearning.chapter1.Chapter1Activity
import life.lixiaoyu.composeactionlearning.chapter2.Chapter2Activity
import life.lixiaoyu.composeactionlearning.chapter3.Chapter3Activity
import life.lixiaoyu.composeactionlearning.ui.theme.ComposeActionLearningTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FullPageWrapper {
                MainPage()
            }
        }
    }
}

@Composable
fun MainPage() {
    val context = LocalContext.current
    Column {
        ItemButton(text = "Chapter 1") {
            context.startActivity(Intent(context, Chapter1Activity::class.java))
        }
        ItemButton(text = "Chapter 2") {
            context.startActivity(Intent(context, Chapter2Activity::class.java))
        }
        ItemButton(text = "Chapter 3") {
            context.startActivity(Intent(context, Chapter3Activity::class.java))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    ComposeActionLearningTheme {
        MainPage()
    }
}