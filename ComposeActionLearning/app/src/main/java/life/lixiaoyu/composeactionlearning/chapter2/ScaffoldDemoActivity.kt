package life.lixiaoyu.composeactionlearning.chapter2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import kotlinx.coroutines.launch
import life.lixiaoyu.composeactionlearning.FullPageWrapper

class ScaffoldDemoActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FullPageWrapper {
                ScaffoldPage()
            }
        }
    }
}

@Composable
fun ScaffoldPage() {
    val navItems = listOf(
        "主页" to Icons.Filled.Home,
        "列表" to Icons.Filled.List,
        "设置" to Icons.Filled.Settings,
        "我的" to Icons.Filled.Person
    )
    var selectedIndex by remember { mutableStateOf(0) }
    val scaffoldState = rememberScaffoldState()
    val scope = rememberCoroutineScope()
    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            TopAppBar(
                title = { Text("首页") },
                navigationIcon = { IconButton(onClick = {
                    scope.launch {
                        scaffoldState.drawerState.open()
                    }
                }) {
                    Icon(Icons.Filled.Menu, "")
                }}
            )
        },
        bottomBar = {
            BottomAppBar(
                cutoutShape = CircleShape,
            ) {
                navItems.forEachIndexed { index, pair ->
                    BottomNavigationItem(
                        selected = selectedIndex == index,
                        onClick = {
                            selectedIndex = index
                        },
                        icon = { Icon(pair.second, "") },
                        alwaysShowLabel = false,
                        label = { Text(pair.first) },
                    )
                }
            }
//            BottomNavigation {
//                navItems.forEachIndexed { index, pair ->
//                    BottomNavigationItem(
//                        selected = selectedIndex == index,
//                        onClick = {
//                            selectedIndex = index
//                        },
//                        icon = { Icon(pair.second, "") },
//                        alwaysShowLabel = false,
//                        label = { Text(pair.first) }
//                    )
//                }
//            }
        },
        floatingActionButton = {
            FloatingActionButton(onClick = { /*TODO*/ }) {
                Icon(Icons.Filled.Publish, "", tint = Color.White)
            }
        },
        isFloatingActionButtonDocked = true,
        floatingActionButtonPosition = FabPosition.Center,
        drawerContent = {
            Box(
                Modifier
                    .fillMaxSize()
                    .background(Color.LightGray),
                contentAlignment = Alignment.Center
            ) {
                Text("这里是 Drawer")
            }
        }
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text("Hello world!")
        }
    }
    BackHandler(enabled = scaffoldState.drawerState.isOpen) {
        scope.launch {
            scaffoldState.drawerState.close()
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ScaffoldPagePreview() {
    FullPageWrapper {
        ScaffoldPage()
    }
}