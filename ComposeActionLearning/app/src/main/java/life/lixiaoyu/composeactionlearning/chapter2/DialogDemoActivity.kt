package life.lixiaoyu.composeactionlearning.chapter2

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.compose.ui.window.SecureFlagPolicy
import life.lixiaoyu.composeactionlearning.*

class DialogDemoActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FullPageWrapper {
                DialogPage()
            }
        }
    }
}


@Composable
fun DialogPage() {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        ShowFullPageDialog()
        ShowCommonSizeDialog()
        ShowAlertDialog()
        ShowCustomAlertDialog()
    }
}


@Composable
fun ShowCustomAlertDialog() {
    DescItem(title = "AlertDialog 弹窗修饰")
    var openDialog by remember {
        mutableStateOf(false)
    }
    Button(
        onClick = {
            openDialog = true
        }
    ) {
        Text("显示 AlertDialog 弹窗")
    }
    val context = LocalContext.current
    if (openDialog) {
        AlertDialog(
            onDismissRequest = {
                openDialog = false
            },
            title = {
                Text("开启位置服务")
            },
            text = {
                Text("这意味着，我们会给您提供精确的位置服务，并且您将接收关于您订阅的位置信息。")
            },
            confirmButton = {
                TextButton(onClick = {
                    openDialog = false
                    Toast.makeText(context, "位置服务已开启", Toast.LENGTH_SHORT).show()
                }) {
                    Text("同意")
                }
            },
            dismissButton = {
                TextButton(onClick = {
                    openDialog = false
                    Toast.makeText(context, "已取消", Toast.LENGTH_SHORT).show()
                }) {
                    Text("取消")
                }
            },
            shape = CircleShape,
            backgroundColor = Color.Cyan.copy(alpha = 0.5F)
        )
    }
}

@Composable
fun ShowAlertDialog() {
    DescItem(title = "显示一个 AlertDialog 弹窗")
    var openDialog by remember {
        mutableStateOf(false)
    }
    Button(
        onClick = {
            openDialog = true
        }
    ) {
        Text("显示一个 AlertDialog 弹窗")
    }
    val context = LocalContext.current
    if (openDialog) {
        AlertDialog(
            onDismissRequest = {
            openDialog = false
        },
            title = {
                Text("开启位置服务")
            },
            text = {
                Text("这意味着，我们会给您提供精确的位置服务，并且您将接收关于您订阅的位置信息。")
            },
            confirmButton = {
                TextButton(onClick = {
                    openDialog = false
                    Toast.makeText(context, "位置服务已开启", Toast.LENGTH_SHORT).show()
                }) {
                    Text("同意")
                }
            },
            dismissButton = {
                TextButton(onClick = {
                    openDialog = false
                    Toast.makeText(context, "已取消", Toast.LENGTH_SHORT).show()
                }) {
                    Text("取消")
                }
            }
        )
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun ShowCommonSizeDialog() {
    DescItem(title = "显示一个普通尺寸的弹窗")
    var openDialog by remember {
        mutableStateOf(false)
    }
    Button(
        onClick = {
            openDialog = true
        }
    ) {
        Text("显示一个普通尺寸的弹窗")
    }
    if (openDialog) {
        Dialog(
            onDismissRequest = {
                openDialog = false
            },
            properties = DialogProperties(
                dismissOnBackPress = false,
                dismissOnClickOutside = false,
                securePolicy = SecureFlagPolicy.Inherit,
                usePlatformDefaultWidth = true
            )
        ) {
            Column(
                modifier = Modifier
                    .size(200.dp, 120.dp)
                    .background(Color.White),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text("这是一个普通尺寸的弹窗")
                Spacer(modifier = Modifier.weight(1F))
                Divider()
                Button(onClick = { 
                    openDialog = false
                }) {
                    Text("关闭")
                }
            }
        }
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun ShowFullPageDialog() {
    DescItem(title = "显示一个全屏弹窗")
    var openDialog by remember {
        mutableStateOf(false)
    }
    Button(
        onClick = {
            openDialog = true
        }
    ) {
        Text("显示一个全屏弹窗")
    }
    if (openDialog) {
        Dialog(
            onDismissRequest = {
                openDialog = false
            },
            properties = DialogProperties(
                usePlatformDefaultWidth = false
            )
        ) {
            Surface(
                modifier = Modifier.fillMaxSize(),
                color = Color.Gray
            ) {
                Text("这是一个全屏的弹窗")
            }
        }
    }
}

@Composable
@Preview(showBackground = true)
fun DialogPagePreview() {
    FullPageWrapper {
        DialogPage()
    }
}