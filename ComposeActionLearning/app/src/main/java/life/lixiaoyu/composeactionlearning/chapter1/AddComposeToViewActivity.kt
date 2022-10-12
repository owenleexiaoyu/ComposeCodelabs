package life.lixiaoyu.composeactionlearning.chapter1

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.compose.material.Text
import androidx.compose.ui.platform.ComposeView
import life.lixiaoyu.composeactionlearning.R

class AddComposeToViewActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_compose_to_view)
        findViewById<ComposeView>(R.id.compose_view).setContent {
            Text("Compose: Text")
        }
    }
}