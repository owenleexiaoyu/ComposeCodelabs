package life.lixiaoyu.composeactionlearning

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import life.lixiaoyu.composeactionlearning.chapter1.AddComposeToViewActivity
import life.lixiaoyu.composeactionlearning.chapter1.AddViewToComposeActivity
import life.lixiaoyu.composeactionlearning.chapter1.Chapter1Activity
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
//        ItemButton(text = "Add WebView to Compose System") {
//            context.startActivity(Intent(context, AddViewToComposeActivity::class.java))
//        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    ComposeActionLearningTheme {
        MainPage()
    }
}