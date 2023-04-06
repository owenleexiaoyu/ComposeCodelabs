package life.lixiaoyu.composeactionlearning.chapter9

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import life.lixiaoyu.composeactionlearning.DescItem
import life.lixiaoyu.composeactionlearning.FullPageWrapper

@Composable
fun SystemUiControllerDemoPage() {
    FullPageWrapper {
        val systemUiController = rememberSystemUiController()
        val useDarkIcons = !isSystemInDarkTheme()
        var isStatusBarVisible by remember {
            mutableStateOf(systemUiController.isStatusBarVisible)
        }
        var isNavigationBarVisible by remember {
            mutableStateOf(systemUiController.isNavigationBarVisible)
        }
        DisposableEffect(systemUiController, useDarkIcons) {
            systemUiController.setSystemBarsColor(
                color = Color.Transparent,
                darkIcons = useDarkIcons
            )
            onDispose {}
        }
        Column(modifier = Modifier.fillMaxSize()) {
            DescItem(title = "使用 accompanist-systemuicontroller 修改系统 statusBar 和 navigationBar 颜色")
            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp),
                onClick = {
                systemUiController.setStatusBarColor(Color.Red, useDarkIcons)
            }) {
                Text("修改 StatusBar 颜色为 Color.Red")
            }
            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp),
                onClick = {
                systemUiController.setNavigationBarColor(Color.Blue, useDarkIcons)
            }) {
                Text("修改 NavigationBar 颜色为 Color.Blue")
            }
            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp),
                onClick = {
                systemUiController.setSystemBarsColor(Color.Green, useDarkIcons)
            }) {
                Text("修改 SystemBars 颜色为 Color.Green")
            }
            DescItem(title = "使用 accompanist-systemuicontroller 修改系统 statusBar 和 navigationBar 可见性")
            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp),
                onClick = {
                    isStatusBarVisible = !isStatusBarVisible
                    systemUiController.isStatusBarVisible = isStatusBarVisible
                }) {
                Text("隐藏/显示 StatusBar")
            }
            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp),
                onClick = {
                    isNavigationBarVisible = !isNavigationBarVisible
                    systemUiController.isNavigationBarVisible = isNavigationBarVisible
                }) {
                Text("隐藏/显示 NavigationBar")
            }
            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp),
                onClick = {
                    val isSystemBarsVisible = isNavigationBarVisible && isStatusBarVisible
                    isNavigationBarVisible = !isSystemBarsVisible
                    isStatusBarVisible = !isSystemBarsVisible
                    systemUiController.isSystemBarsVisible = !isSystemBarsVisible
                }) {
                Text("隐藏/显示 SystemBars")
            }
        }
    }
}