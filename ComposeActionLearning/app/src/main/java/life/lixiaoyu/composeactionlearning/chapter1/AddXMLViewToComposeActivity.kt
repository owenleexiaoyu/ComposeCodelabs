package life.lixiaoyu.composeactionlearning.chapter1

import android.os.Bundle
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import androidx.compose.ui.viewinterop.AndroidViewBinding
import life.lixiaoyu.composeactionlearning.R
import life.lixiaoyu.composeactionlearning.databinding.LayoutXmlViewToComposeBinding

class AddXMLViewToComposeActivity: ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AndroidViewBinding(factory = LayoutXmlViewToComposeBinding::inflate)
        }
    }
}