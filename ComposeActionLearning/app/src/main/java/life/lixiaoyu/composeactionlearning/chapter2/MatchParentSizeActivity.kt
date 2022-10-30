package life.lixiaoyu.composeactionlearning.chapter2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import life.lixiaoyu.composeactionlearning.FullPageWrapper
import life.lixiaoyu.composeactionlearning.R

class MatchParentSizeActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FullPageWrapper {
                MatchParentSizePage()
            }
        }
    }
}

@Composable
fun MatchParentSizePage() {
    Column(modifier = Modifier.fillMaxSize()) {
        Box(modifier = Modifier.weight(1F)) {
            Box {
                Box(modifier = Modifier
                    .matchParentSize()
                    .background(Color.Yellow))
                UserInfo()
            }
        }
        Box(modifier = Modifier.weight(1F)) {
            Box {
                Box(modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Red))
                UserInfo()
            }
        }
    }
}

@Composable
fun UserInfo() {
    Row(modifier = Modifier.border(1.dp, Color.Black).padding(10.dp)) {
        Image(painterResource(id = R.drawable.chandler), "", modifier = Modifier.size(80.dp))
        Column(modifier = Modifier.padding(start = 10.dp)) {
            Text("Chandler Bing")
            Text("Monica's husband")
        }
    }
}