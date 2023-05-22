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
import life.lixiaoyu.composeactionlearning.chapter7.Chapter7Activity
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
        ItemButton(text = "Chapter 1：全新的 Android UI 框架") {
            context.startActivity(Intent(context, Chapter1Activity::class.java))
        }
        ItemButton(text = "Chapter 2：了解常用 UI 组件") {
            context.startActivity(Intent(context, Chapter2Activity::class.java))
        }
        ItemButton(text = "Chapter 3：定制 UI 视图") {
            context.startActivity(Intent(context, Chapter3Activity::class.java))
        }
        ItemButton(text = "Chapter 4：状态管理与重组") {
            context.startActivity(Intent(context, Chapter4Activity::class.java))
        }
        ItemButton(text = "Chapter 5：Compose 组件渲染流程") {
            context.startActivity(Intent(context, Chapter5Activity::class.java))
        }
        ItemButton(text = "Chapter 6：让页面动起来：动画") {
            context.startActivity(Intent(context, Chapter6Activity::class.java))
        }
        ItemButton(text = "Chapter 7：增进交互体验：手势处理") {
            context.startActivity(Intent(context, Chapter7Activity::class.java))
        }
        ItemButton(text = "Chapter 9：Accompanist 与第三方组件库") {
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