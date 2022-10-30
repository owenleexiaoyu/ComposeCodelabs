package life.lixiaoyu.composeactionlearning.chapter2

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.selection.DisableSelection
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontSynthesis
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.*
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import life.lixiaoyu.composeactionlearning.DescItem
import life.lixiaoyu.composeactionlearning.FullPageWrapper
import life.lixiaoyu.composeactionlearning.InfoBox
import life.lixiaoyu.composeactionlearning.ItemButton

class TextDemoActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FullPageWrapper {
                TextPage()
            }
        }
    }
}

@Composable
fun TextPage() {
    Column {
        DescItem(title = "Text 用法演示")
        Text(
            "Hello world",
            color = Color.Blue,
            fontSize = 20.sp,
            fontStyle = FontStyle.Italic,
            fontWeight = FontWeight.Bold,
            fontFamily = FontFamily.Monospace,
            letterSpacing = 2.sp,
            textDecoration = TextDecoration.Underline,
            textAlign = TextAlign.Justify,
            overflow = TextOverflow.Ellipsis,
        )
        DescItem(title = "TextStyle shadow 效果演示")
        InfoBox(info = "Shadow: color = Color.Cyan, offset = Offset(5F, 5F), blurRadius = 5F", modifier = Modifier) {
            Text("Hello world",
                fontSize = 20.sp,
                style = TextStyle(
                    shadow = Shadow(Color.Cyan, Offset(5F, 5F), 5F)
                )
            )
        }
        DescItem(title = "TextStyle background 效果演示")
        InfoBox(
            info = "color = Color.Red background = Color.Blue",
            modifier = Modifier) {
            Text("Hello world",
                fontSize = 20.sp,
                color = Color.Red,
                style = TextStyle(background = Color.Blue)
            )
        }
        DescItem(title = "TextStyle textGeometricTransform 效果演示")
        InfoBox(info = "textGeometricTransform scaleX = 2F", modifier = Modifier.padding(10.dp)) {
            Text("Hello world",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                style = TextStyle(textGeometricTransform = TextGeometricTransform(scaleX = 2F))
            )
        }
        InfoBox(info = "textGeometricTransform skewX = 1F", modifier = Modifier.padding(10.dp)) {
            Text("Hello world",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                style = TextStyle(textGeometricTransform = TextGeometricTransform(skewX = 1F))
            )
        }
        InfoBox(info = "textGeometricTransform skewX = -1F", modifier = Modifier.padding(10.dp)) {
            Text("Hello world",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                style = TextStyle(textGeometricTransform = TextGeometricTransform(skewX = -1F))
            )
        }
        DescItem(title = "TextStyle textIndent 效果演示")
        InfoBox(info = "firstLine缩进100sp，restLine缩进50sp", modifier = Modifier.padding(10.dp)) {
            Text("Jetpack Compose 是 Android 新一代 UI 开发框架，可以让开发者用更少的代码完成 Native UI 开发。",
                fontSize = 20.sp,
                style = TextStyle(textIndent = TextIndent(firstLine = 100.sp, restLine = 50.sp))
            )
        }
//            DescItem(title = "TextStyle baselineShift 效果演示")
//            InfoBox(info = "baselineShift = BaselineShift.None", modifier = Modifier.padding(10.dp)) {
//                Text("Jetpack Compose 是 Android 新一代 UI 开发框架，可以让开发者用更少的代码完成 Native UI 开发。",
//                    fontSize = 15.sp,
//                    style = TextStyle(baselineShift = BaselineShift.None)
//                )
//            }
//            InfoBox(info = "baselineShift = BaselineShift.Subscript", modifier = Modifier.padding(10.dp)) {
//                Text("Jetpack Compose 是 Android 新一代 UI 开发框架，可以让开发者用更少的代码完成 Native UI 开发。",
//                    fontSize = 15.sp,
//                    style = TextStyle(baselineShift = BaselineShift.Subscript)
//                )
//            }
//            InfoBox(info = "baselineShift = BaselineShift.Superscript", modifier = Modifier.padding(10.dp)) {
//                Text("Jetpack Compose 是 Android 新一代 UI 开发框架，可以让开发者用更少的代码完成 Native UI 开发。",
//                    fontSize = 15.sp,
//                    style = TextStyle(baselineShift = BaselineShift.Superscript)
//                )
//            }
        DescItem(title = "AnnotatedString 效果演示")
        val annotatedString = buildAnnotatedString {
            withStyle(style = SpanStyle(fontSize = 20.sp)) {
                append("在点击注册按钮之前，请先阅读本应用的")
            }
            pushStringAnnotation(tag = "terms-of-use", annotation = "https://lixiaoyu.life")
            withStyle(SpanStyle(
                fontSize = 20.sp,
                color = Color.Red,
                fontWeight = FontWeight.Bold,
                textDecoration = TextDecoration.Underline
            )) {
                append("《用户协议》")
            }
            pop()
            withStyle(style = SpanStyle(fontSize = 20.sp)) {
                append("和")
            }
            pushStringAnnotation(tag = "privacy-policy", annotation = "https://baidu.com")
            withStyle(SpanStyle(
                fontSize = 20.sp,
                color = Color.Red,
                fontWeight = FontWeight.Bold,
                textDecoration = TextDecoration.Underline
            )) {
                append("《隐私条款》")
            }
            pop()
        }
        val context = LocalContext.current
        ClickableText(
            text = annotatedString,
            onClick = { offset ->
                Log.d("OWEN", "offset = $offset")
                annotatedString.getStringAnnotations(
                    tag = "terms-of-use",
                    start = offset,
                    end = offset
                ).firstOrNull()?.let { annotation ->
                    Toast.makeText(context, annotation.item, Toast.LENGTH_SHORT).show()
                }
                annotatedString.getStringAnnotations(
                    tag = "privacy-policy",
                    start = offset,
                    end = offset
                ).firstOrNull()?.let { annotation ->
                    Toast.makeText(context, annotation.item, Toast.LENGTH_SHORT).show()
                }
            }
        )
        DescItem(title = "文字部分选中效果")
        SelectionContainer {
            Column(modifier = Modifier.padding(10.dp)) {
                Text(text = "这行文本可以选中")
                Text(text = "这行文本可以选中")
                DisableSelection {
                    Text(text = "这行文本不可以选中")
                }
                Text(text = "这行文本可以选中")
                Text(text = "这行文本可以选中")
            }
        }
    }
}