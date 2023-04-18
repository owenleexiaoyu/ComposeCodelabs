package life.lixiaoyu.composebloom

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.*
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import life.lixiaoyu.composebloom.ui.MainPage
import life.lixiaoyu.composebloom.ui.LoginPage
import life.lixiaoyu.composebloom.ui.PlantDetailPage
import life.lixiaoyu.composebloom.ui.WelcomePage
import life.lixiaoyu.composebloom.ui.theme.BloomTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BloomTheme {
                AppNavigation()
            }
        }
    }
}

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "welcome") {
        composable("welcome") {
            WelcomePage(onLoginClicked = {
                navController.navigate("login")
            })
        }
        composable("login") {
            LoginPage(onLoginClicked = {
                navController.navigate("main")
            })
        }
        composable("main") {
            MainPage(appNavController = navController)
        }
        composable("plantDetail/{plantId}?fromBanner={fromBanner}",
            arguments = listOf(
                navArgument("plantId") { type = NavType.IntType },
                navArgument("fromBanner") {
                    type = NavType.BoolType
                    defaultValue = true
                }
            )) {
            PlantDetailPage(it.arguments?.getInt("plantId") ?: 0, it.arguments?.getBoolean("fromBanner"))
        }
    }
}
