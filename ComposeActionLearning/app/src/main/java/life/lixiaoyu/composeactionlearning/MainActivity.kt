package life.lixiaoyu.composeactionlearning

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Divider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import life.lixiaoyu.composeactionlearning.chapter1.Chapter1Activity
import life.lixiaoyu.composeactionlearning.chapter2.Chapter2Activity
import life.lixiaoyu.composeactionlearning.chapter3.Chapter3Activity
import life.lixiaoyu.composeactionlearning.chapter4.Chapter4Activity
import life.lixiaoyu.composeactionlearning.chapter5.Chapter5Activity
import life.lixiaoyu.composeactionlearning.chapter6.Chapter6Activity
import life.lixiaoyu.composeactionlearning.chapter9.Chapter9Activity
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
    Column(
        modifier = Modifier.verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.img_jetpack_compose),
            contentDescription = "Book cover",
            modifier = Modifier
                .padding(20.dp)
                .height(300.dp),
            contentScale = ContentScale.FillHeight
        )
        Divider()
        ItemButton(text = "Chapter 1") {
            context.startActivity(Intent(context, Chapter1Activity::class.java))
        }
        ItemButton(text = "Chapter 2") {
            context.startActivity(Intent(context, Chapter2Activity::class.java))
        }
        ItemButton(text = "Chapter 3") {
            context.startActivity(Intent(context, Chapter3Activity::class.java))
        }
        ItemButton(text = "Chapter 4") {
            context.startActivity(Intent(context, Chapter4Activity::class.java))
        }
        ItemButton(text = "Chapter 5") {
            context.startActivity(Intent(context, Chapter5Activity::class.java))
        }
        ItemButton(text = "Chapter 6") {
            context.startActivity(Intent(context, Chapter6Activity::class.java))
        }
        ItemButton(text = "Chapter 9") {
            context.startActivity(Intent(context, Chapter9Activity::class.java))
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