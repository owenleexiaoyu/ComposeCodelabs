package life.lixiaoyu.composeactionlearning.chapter7

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

class Chapter7Activity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Chapter7Navigation()
        }
    }
}

enum class Chapter7Pages {
    chapter7,
    clickable,
    draggableAndSwipeable,
    transformable,
}

@Composable
fun Chapter7Navigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Chapter7Pages.chapter7.name) {
        composable(Chapter7Pages.chapter7.name) {
            Chapter6Page(navController)
        }
        composable(Chapter7Pages.clickable.name) {
            ClickablePage()
        }
        composable(Chapter7Pages.draggableAndSwipeable.name) {
            DraggableAndSwipeablePage()
        }
        composable(Chapter7Pages.transformable.name) {
            TransformablePage()
        }
    }
}

@Composable
fun Chapter6Page(navController: NavController) {
    FullPageWrapper {
        Column {
            ItemButton(text = "Clickable") {
                navController.navigate(Chapter7Pages.clickable.name)
            }
            ItemButton(text = "Draggable and Swipeable") {
                navController.navigate(Chapter7Pages.draggableAndSwipeable.name)
            }
            ItemButton(text = "Transformable") {
                navController.navigate(Chapter7Pages.transformable.name)
            }
        }
    }
}