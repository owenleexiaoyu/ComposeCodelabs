package life.lixiaoyu.composeactionlearning.chapter4

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import life.lixiaoyu.composeactionlearning.FullPageWrapper
import life.lixiaoyu.composeactionlearning.ItemButton

class Chapter4Activity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FullPageWrapper {
                Chapter4Page()
            }
        }
    }
}

@Composable
fun Chapter4Page() {
    Column {
        val context = LocalContext.current
        Column {
            ItemButton(text = "传统 View + MVC") {
                context.startActivity(Intent(context, CounterActivity::class.java))
            }
            ItemButton(text = "传统 View + MVC + 提取状态") {
                context.startActivity(Intent(context, Counter2Activity::class.java))
            }
            ItemButton(text = "传统 View + ViewModel(MVVM)") {
                context.startActivity(Intent(context, Counter3Activity::class.java))
            }
            ItemButton(text = "Stateful Compose") {
                context.startActivity(Intent(context, Counter4Activity::class.java))
            }
            ItemButton(text = "Stateless Compose") {
                context.startActivity(Intent(context, Counter5Activity::class.java))
            }
            ItemButton(text = "Stateless Compose + ViewModel") {
                context.startActivity(Intent(context, Counter6Activity::class.java))
            }
            ItemButton(text = "Stateless Compose + StateHolder") {
                context.startActivity(Intent(context, Counter7Activity::class.java))
            }

            ItemButton(text = "RememberSaveable") {
                context.startActivity(Intent(context, RememberSavableActivity::class.java))
            }
            ItemButton(text = "Show SnackBar") {
                context.startActivity(Intent(context, ShowSnakeBarActivity::class.java))
            }
            ItemButton(text = "Composition Demo") {
                context.startActivity(Intent(context, CompositionDemoActivity::class.java))
            }
            ItemButton(text = "重组并发执行") {
                context.startActivity(Intent(context, AsyncCompositionActivity::class.java))
            }
            ItemButton(text = "副作用 API: Timer Navigation") {
                context.startActivity(Intent(context, SideEffectsTimerDemoActivity::class.java))
            }
        }
    }
}