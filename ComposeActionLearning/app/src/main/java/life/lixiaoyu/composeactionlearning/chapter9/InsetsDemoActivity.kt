package life.lixiaoyu.composeactionlearning.chapter9

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.view.WindowCompat
import com.google.accompanist.systemuicontroller.rememberSystemUiController

class InsetsDemoActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // 将内容延伸到 System Bar 下方
        WindowCompat.setDecorFitsSystemWindows(window, false)
        setContent {
            InsetsDemoPage()
        }
    }
}

@Composable
fun InsetsDemoPage() {
    val systemUiController = rememberSystemUiController()
    val useDarkIcons = !isSystemInDarkTheme()
    DisposableEffect(key1 = systemUiController, key2 = useDarkIcons) {
        systemUiController.setSystemBarsColor(Color.Transparent, useDarkIcons)
        onDispose {}
    }
    TopAppBar(
        backgroundColor = Color.Green,
        contentPadding = WindowInsets.statusBars.asPaddingValues()
    ) {
        Text(
            "TopAppBar",
            style = MaterialTheme.typography.h6,
            modifier = Modifier.padding(start = 12.dp)
        )
    }
}