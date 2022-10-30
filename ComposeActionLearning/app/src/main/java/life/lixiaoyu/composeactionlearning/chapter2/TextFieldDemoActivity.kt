package life.lixiaoyu.composeactionlearning.chapter2

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.RemoveRedEye
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material.icons.outlined.Search
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import life.lixiaoyu.composeactionlearning.DescItem
import life.lixiaoyu.composeactionlearning.FullPageWrapper
import life.lixiaoyu.composeactionlearning.ui.theme.Purple500

class TextFieldDemoActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FullPageWrapper {
                TextFieldPage()
            }
        }
    }
}

@Composable
fun TextFieldPage() {
    val context = LocalContext.current
    Column {
        DescItem(title = "TextField 基本使用")
        var text by remember {
            mutableStateOf("")
        }
        TextField(value = text, onValueChange = {
            text = it
        }, modifier = Modifier
            .padding(vertical = 5.dp, horizontal = 10.dp)
            .fillMaxWidth())
        DescItem(title = "OutlinedTextField 基本使用")
        var text2 by remember {
            mutableStateOf("")
        }
        OutlinedTextField(value = text2, onValueChange = {
            text2 = it
        }, modifier = Modifier
            .padding(vertical = 5.dp, horizontal = 10.dp)
            .fillMaxWidth())
        DescItem(title = "TextField 的装饰")
        var text3 by remember {
            mutableStateOf("")
        }
        TextField(
            value = text3,
            onValueChange = {
                text3 = it
            },
            modifier = Modifier
                .padding(vertical = 5.dp, horizontal = 10.dp)
                .fillMaxWidth(),
            label = {
                Text("用户名")
            },
            placeholder = {
                Text("请输入用户名", color = Color.Gray)
            },
            leadingIcon = {
                Icon(Icons.Outlined.AccountCircle, contentDescription = "")
            },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number, imeAction = ImeAction.Search),
            keyboardActions = KeyboardActions(onSearch = {
                Toast.makeText(context, "Press search", Toast.LENGTH_SHORT).show()
            }),
            singleLine = true,
            maxLines = 2,
            shape = RoundedCornerShape(8.dp),
        )
        var text4 by remember {
            mutableStateOf("")
        }
        var isVisible by remember {
            mutableStateOf(false)
        }
        TextField(
            value = text4,
            onValueChange = {
                text4 = it
            },
            modifier = Modifier
                .padding(vertical = 5.dp, horizontal = 10.dp)
                .fillMaxWidth(),
            label = {
                Text("密码")
            },
            placeholder = {
                Text("请输入密码", color = Color.Gray)
            },
            leadingIcon = {
                Icon(Icons.Outlined.Lock, contentDescription = "")
            },
            trailingIcon = {
                Icon(
                    Icons.Filled.RemoveRedEye,
                    tint = if (isVisible) Purple500 else Color.Gray,
                    contentDescription = "",
                    modifier = Modifier.clickable {
                        isVisible = !isVisible
                    })
            },
            visualTransformation = if (isVisible) VisualTransformation.None else PasswordVisualTransformation()
        )
        DescItem(title = "自定义 BasicTextField")
        var text5 by remember { mutableStateOf("") }
        BasicTextField(
            value = text5,
            onValueChange = {
            text5 = it
            },
            decorationBox = { innerTextField ->
                Column {
                    innerTextField()
                    Divider(
                        thickness = 2.dp,
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(Color.Black)
                    )
                }
            }
        )
        DescItem(title = "BasicTextField 实现 B 站风格输入框")
        BiliBiliSearchBar()
    }
}

@Composable
fun BiliBiliSearchBar() {
    var text by remember { mutableStateOf("") }
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(60.dp)
            .background(Color(0xFFD3D3D3)),
        contentAlignment = Alignment.Center
    ) {
        BasicTextField(
            value = text,
            onValueChange = {
                 text = it
            },
            textStyle = TextStyle(
                fontSize = 14.sp
            ),
            singleLine = true,
            decorationBox = { innerTextField ->
                Row(
                    modifier = Modifier.padding(top = 2.dp, bottom = 2.dp, start = 8.dp, end = 8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(Icons.Outlined.Search, "", tint = Color.Gray)
                    Spacer(modifier = Modifier.width(8.dp))
                    Box(
                        modifier = Modifier.weight(1F)
                    ) {
                        if (text.isEmpty()) {
                            Text(
                                text = "输入点东西看看吧",
                                color = Color.Gray,
                                fontSize = 14.sp
                            )
                        }
                        innerTextField()
                    }
                    if (text.isNotEmpty()) {
                        Spacer(modifier = Modifier.width(8.dp))
                        Icon(
                            Icons.Filled.Close, "",
                            tint = Color.Gray,
                            modifier = Modifier.clickable {
                            text = ""
                        })
                    }
                }

            },
            cursorBrush = SolidColor(Color.Gray),
            modifier = Modifier
                .padding(10.dp)
                .background(Color.White, CircleShape)
                .fillMaxSize()
        )
    }
}

@Preview(showBackground = true)
@Composable
fun TextFieldPagePreview() {
    FullPageWrapper {
        TextFieldPage()
    }
}