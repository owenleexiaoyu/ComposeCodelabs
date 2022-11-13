package life.lixiaoyu.composeactionlearning.chapter3

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import life.lixiaoyu.composeactionlearning.FullPageWrapper
import life.lixiaoyu.composeactionlearning.ItemButton

class Chapter3Activity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FullPageWrapper {
                Chapter3Page()
            }
        }
    }
}

@Composable
fun Chapter3Page() {
    Column {
        val context = LocalContext.current
        Column {
            ItemButton(text = "CompositionLocal 的用法") {
                context.startActivity(Intent(context, CompositionLocalDemoActivity::class.java))
            }
        }
    }
}