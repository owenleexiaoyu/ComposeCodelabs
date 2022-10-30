package life.lixiaoyu.composeactionlearning.chapter2

import android.os.Bundle
import android.text.Layout
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.*
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowUpward
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.twotone.ShoppingCart
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import life.lixiaoyu.composeactionlearning.*
import life.lixiaoyu.composeactionlearning.R
import life.lixiaoyu.composeactionlearning.ui.theme.Purple200
import life.lixiaoyu.composeactionlearning.ui.theme.Purple500

class ButtonDemoActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FullPageWrapper {
                ButtonPage()
            }
        }
    }
}

@Composable
fun ButtonPage() {
    Column(
        modifier = Modifier.verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val context = LocalContext.current
        DescItem(title = "几种基本的 Button")
        Row {
            Button(onClick = {
                Toast.makeText(context, "没有内容的 Button", Toast.LENGTH_SHORT).show()
            }) {

            }
            HorizontalSpacer(width = 10.dp)
            Button(onClick = {
                Toast.makeText(context, "只有文本的普通 Button", Toast.LENGTH_SHORT).show()
            }) {
                Text("确定")
            }
            HorizontalSpacer(width = 10.dp)
            Button(onClick = {
                Toast.makeText(context, "只有 Icon 的 Button", Toast.LENGTH_SHORT).show()
            }) {
                Icon(
                    Icons.Filled.Done,
                    "",
                    modifier = Modifier.size(ButtonDefaults.IconSize)
                )
            }
            HorizontalSpacer(width = 10.dp)
            Button(onClick = {
                Toast.makeText(context, "文本前有 Icon 的 Button", Toast.LENGTH_SHORT).show()
            }) {
                Icon(
                    Icons.Filled.Done,
                    "",
                    modifier = Modifier.size(ButtonDefaults.IconSize)
                )
                HorizontalSpacer(width = ButtonDefaults.IconSpacing)
                Text("确定")
            }
        }
        DescItem(title = "Button 的外观修饰")
        Button(
            onClick = {
                Toast.makeText(context, "shape: CircleShape, BorderStroke: Brush.sweepGradient", Toast.LENGTH_SHORT).show()
            },
            shape = CircleShape,
            border = BorderStroke(2.dp, Brush.sweepGradient(listOf(Color.Red, Color.Yellow, Color.Cyan)))
        ) {
            Text("确定")
        }
        Button(
            onClick = {
                Toast.makeText(context, "contentPadding: horizontal 50dp, vertical 20dp", Toast.LENGTH_SHORT).show()
            },
            contentPadding = PaddingValues(horizontal = 50.dp, vertical = 20.dp)
        ) {
            Text("确定")
        }
        Button(
            onClick = {
                Toast.makeText(context, "自定义 Button elevation", Toast.LENGTH_SHORT).show()
            },
            elevation = ButtonDefaults.elevation(
                defaultElevation = 5.dp,
                pressedElevation = 10.dp
            )
        ) {
            Text("确定")
        }
        Row {
            Button(
                onClick = {
                    Toast.makeText(context, "自定义 Button colors", Toast.LENGTH_SHORT).show()
                },
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = Color.Blue,
                    disabledBackgroundColor = Color.LightGray,
                    contentColor = Color.White,
                    disabledContentColor = Color.Gray
                ),
            ) {
                Text("确定")
            }
            HorizontalSpacer(width = 10.dp)
            Button(
                onClick = {
                    Toast.makeText(context, "自定义 Button colors", Toast.LENGTH_SHORT).show()
                },
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = Color.Blue,
                    disabledBackgroundColor = Color.LightGray,
                    contentColor = Color.White,
                    disabledContentColor = Color.Gray
                ),
                enabled = false
            ) {
                Text("确定")
            }
        }
        DescItem(title = "Button interactionSource 不同状态下样式")
        val interactionSource = remember {
            MutableInteractionSource()
        }
        val pressedState = interactionSource.collectIsPressedAsState()
        val borderColor = if (pressedState.value) Color.Green else Color.Black
        Button(
            onClick = {
                Toast.makeText(context, "按下时按钮边框变为绿色，松开时边框变为黑色", Toast.LENGTH_SHORT).show()
            },
            border = BorderStroke(2.dp, borderColor),
            interactionSource = interactionSource
        ) {
            Text("确定")
        }
        DescItem(title = "TextButton 的使用")
        TextButton(onClick = {
            Toast.makeText(context, "这是个 TextButton", Toast.LENGTH_SHORT).show()
        }) {
            Text("确定")
        }
        DescItem(title = "IconButton 的使用")
        IconButton(onClick = {
            Toast.makeText(context, "这是个 IconButton", Toast.LENGTH_SHORT).show()
        }) {
            Icon(Icons.Filled.Favorite, "收藏")
        }
        DescItem(title = "OutlinedButton 的使用")
        OutlinedButton(onClick = {
            Toast.makeText(context, "这是个 OutlinedButton", Toast.LENGTH_SHORT).show()
        }) {
            Text("确定")
        }
        DescItem(title = "FloatingActionButton 的使用")
        FloatingActionButton(onClick = {
            Toast.makeText(context, "这是个 FloatingActionButton", Toast.LENGTH_SHORT).show()
        }) {
            Icon(Icons.Filled.ArrowUpward, "向上")
        }
        DescItem(title = "FloatingActionButton 的使用")
        ExtendedFloatingActionButton(
            onClick = {
                Toast.makeText(context, "这是个 ExtendedFloatingActionButton", Toast.LENGTH_SHORT)
                    .show()
            },
            icon = {
                Icon(Icons.Filled.Favorite, "收藏")
            },
            text = {
                Text("添加到收藏夹")
            }
        )
    }
}

@Composable
@Preview(showBackground = true)
fun ButtonPagePreview() {
    FullPageWrapper {
        ButtonPage()
    }
}