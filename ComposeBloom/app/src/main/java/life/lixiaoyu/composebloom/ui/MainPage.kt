package life.lixiaoyu.composebloom.ui

import android.content.res.Configuration
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.ShoppingCart
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import life.lixiaoyu.composebloom.ui.theme.BloomTheme

@Composable
fun MainPage(appNavController: NavHostController) {
    val navController = rememberNavController()
    Scaffold(
        bottomBar = {
            BottomBar(navController)
        }
    ) {
        NavHost(navController = navController, startDestination = MainTab.Home.route) {
            composable(MainTab.Home.route) {
                HomeScreen(navController = appNavController)
            }
            composable(MainTab.Favorite.route) {
                FavoriteScreen()
            }
            composable(MainTab.Cart.route) {
                CartScreen()
            }
            composable(MainTab.Profile.route) {
                ProfileScreen()
            }
        }

    }
}



@Composable
fun BottomBar(navController: NavController) {
    BottomNavigation(
        backgroundColor = MaterialTheme.colors.primary,
        elevation = 16.dp
    ) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentDestination = navBackStackEntry?.destination

        tabList.forEach { tab ->
            val isSelected = currentDestination?.hierarchy?.any { it.route == tab.route } == true
            BottomNavigationItem(
                selected = isSelected,
                onClick = {
                    navController.navigate(tab.route) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
                label = {
                    Text(
                        tab.title,
                        style = MaterialTheme.typography.caption,
                        color = MaterialTheme.colors.onPrimary
                    )
                },
                icon = {
                    Icon(
                        if (isSelected) tab.selectedIcon else tab.unSelectedIcon,
                        "",
                        modifier = Modifier.size(24.dp),
                        tint = MaterialTheme.colors.onPrimary
                    )
                }
            )
        }
    }
}



sealed class MainTab(
    val title: String,
    val selectedIcon: ImageVector,
    val unSelectedIcon: ImageVector,
    val route: String,
) {
    object Home: MainTab("Home", Icons.Filled.Home, Icons.Outlined.Home, "home")
    object Favorite: MainTab("Favorites", Icons.Filled.Favorite, Icons.Outlined.FavoriteBorder, "favorite")
    object Cart: MainTab("Cart", Icons.Filled.ShoppingCart ,Icons.Outlined.ShoppingCart, "cart")
    object Profile: MainTab("Profile", Icons.Filled.AccountCircle, Icons.Outlined.AccountCircle, "profile")
}

val tabList = listOf(
    MainTab.Home,
    MainTab.Favorite,
    MainTab.Cart,
    MainTab.Profile
)

@Preview(showBackground = true)
@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun DefaultPreview() {
    BloomTheme {
        val navController = rememberNavController()
        MainPage(navController)
    }
}