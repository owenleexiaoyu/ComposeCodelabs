package life.lixiaoyu.composeactionlearning.chapter2

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import life.lixiaoyu.composeactionlearning.FullPageWrapper
import life.lixiaoyu.composeactionlearning.ItemButton

class Chapter2Activity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FullPageWrapper {
                Chapter2Page()
            }
        }
    }
}

@Composable
fun Chapter2Page() {
    Column {
        val context = LocalContext.current
        Column {
            ItemButton(text = "Modifier Demo") {
                context.startActivity(Intent(context, ModifierDemoActivity::class.java))
            }
            ItemButton(text = "Text Demo") {
                context.startActivity(Intent(context, TextDemoActivity::class.java))
            }
            ItemButton(text = "TextField Demo") {
                context.startActivity(Intent(context, TextFieldDemoActivity::class.java))
            }
            ItemButton(text = "Image Demo") {
                context.startActivity(Intent(context, ImageDemoActivity::class.java))
            }
            ItemButton(text = "Button Demo") {
                context.startActivity(Intent(context, ButtonDemoActivity::class.java))
            }
            ItemButton(text = "Checkbox、Switch、Slider Demo") {
                context.startActivity(Intent(context, CheckboxDemoActivity::class.java))
            }
            ItemButton(text = "Dialog Demo") {
                context.startActivity(Intent(context, DialogDemoActivity::class.java))
            }
            ItemButton(text = "ProgressIndicator Demo") {
                context.startActivity(Intent(context, ProgressIndicatorDemoActivity::class.java))
            }
            ItemButton(text = "LayoutComponent Demo") {
                context.startActivity(Intent(context, LayoutComponentDemoActivity::class.java))
            }
            ItemButton(text = "ConstraintLayout Demo") {
                context.startActivity(Intent(context, ConstraintLayoutDemoActivity::class.java))
            }
            ItemButton(text = "Scaffold Demo") {
                context.startActivity(Intent(context, ScaffoldDemoActivity::class.java))
            }
            ItemButton(text = "LazyColumn Demo") {
                context.startActivity(Intent(context, ListDemoActivity::class.java))
            }
        }
    }
}