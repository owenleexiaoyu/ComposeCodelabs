package life.lixiaoyu.composeactionlearning.chapter9

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import life.lixiaoyu.composeactionlearning.FullPageWrapper
import life.lixiaoyu.composeactionlearning.ItemButton

class Chapter9Activity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FullPageWrapper {
                Chapter9NavHost()
            }
        }
    }
}

@Composable
fun Chapter9NavHost() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = Chapter9Pages.chapter9.name
    ) {
        composable(Chapter9Pages.chapter9.name) {
            Chapter9Page(navController)
        }
        composable(Chapter9Pages.systemUiContriller.name) {
            SystemUiControllerDemoPage()
        }
        // Pager 相关页面
        composable(Chapter9Pages.pager.name) {
            PagerDemoPage(navController)
        }
        composable(Chapter9Pages.pager_basic.name) {
            PagerBasicDemoPage()
        }
        composable(Chapter9Pages.pager_indicator.name) {
            PagerIndicatorDemoPage()
        }
        composable(Chapter9Pages.pager_tabLayout.name) {
            PagerTabLayoutDemoPage()
        }
        composable(Chapter9Pages.coil.name) {
            CoilDemoPage()
        }
    }
}

enum class Chapter9Pages {
    chapter9,
    systemUiContriller,
    pager,
    pager_basic,
    pager_indicator,
    pager_tabLayout,
    coil,
}

@Composable
fun Chapter9Page(navController: NavController) {
    Column {
        ItemButton(text = "SystemUiController Demo") {
            navController.navigate(Chapter9Pages.systemUiContriller.name)
        }
        ItemButton(text = "Pager Demo") {
            navController.navigate(Chapter9Pages.pager.name)
        }
        ItemButton(text = "Coil Demo") {
            navController.navigate(Chapter9Pages.coil.name)
        }
    }
}