package life.lixiaoyu.composeactionlearning.chapter7

import android.content.ClipData
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
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
    scrollable,
    nestedScroll,
    detectTapGestures,
    detectDragGestures,
    detectTransformGestures,
    awaitPointerEvent,
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
        composable(Chapter7Pages.scrollable.name) {
            ScrollablePage()
        }
        composable(Chapter7Pages.nestedScroll.name) {
            NestedScrollPage()
        }
        composable(Chapter7Pages.detectTapGestures.name) {
            DetectTapGesturesPage()
        }
        composable(Chapter7Pages.detectDragGestures.name) {
            DetectDragGesturesPage()
        }
        composable(Chapter7Pages.detectTransformGestures.name) {
            DetectTransformGesturesPage()
        }
        composable(Chapter7Pages.awaitPointerEvent.name) {
            AwaitPointerEventPage()
        }
    }
}

@Composable
fun Chapter6Page(navController: NavController) {
    FullPageWrapper {
        Column(Modifier.verticalScroll(rememberScrollState())) {
            ItemButton(text = "Clickable") {
                navController.navigate(Chapter7Pages.clickable.name)
            }
            ItemButton(text = "Draggable and Swipeable") {
                navController.navigate(Chapter7Pages.draggableAndSwipeable.name)
            }
            ItemButton(text = "Transformable") {
                navController.navigate(Chapter7Pages.transformable.name)
            }
            ItemButton(text = "Scrollable") {
                navController.navigate(Chapter7Pages.scrollable.name)
            }
            ItemButton(text = "NestedScroll") {
                navController.navigate(Chapter7Pages.nestedScroll.name)
            }
            ItemButton(text = "DetectTapGestures") {
                navController.navigate(Chapter7Pages.detectTapGestures.name)
            }
            ItemButton(text = "DetectDragGestures") {
                navController.navigate(Chapter7Pages.detectDragGestures.name)
            }
            ItemButton(text = "DetectTransformGestures") {
                navController.navigate(Chapter7Pages.detectTransformGestures.name)
            }
            ItemButton(text = "AwaitPointerEvent") {
                navController.navigate(Chapter7Pages.awaitPointerEvent.name)
            }
        }
    }
}