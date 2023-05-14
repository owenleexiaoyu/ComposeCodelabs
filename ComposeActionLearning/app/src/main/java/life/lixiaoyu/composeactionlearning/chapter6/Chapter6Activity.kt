package life.lixiaoyu.composeactionlearning.chapter6

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

class Chapter6Activity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Chapter6Navigation()
        }
    }
}

enum class Chapter6Pages {
    chapter6,
    animatedVisibility,
    animatedVisibilityTransition,
    animatedContent,
    crossfade,
    animateContentSize,
    animateAsState,
    animatable,
    transition,
    animationSpec,
    skeletonAnimation,
    collectionAnimation,
}

@Composable
fun Chapter6Navigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Chapter6Pages.chapter6.name) {
        composable(Chapter6Pages.chapter6.name) {
            Chapter6Page(navController)
        }
        composable(Chapter6Pages.animatedVisibility.name) {
            AnimatedVisibilityPage()
        }
        composable(Chapter6Pages.animatedVisibilityTransition.name) {
            AnimatedVisibilityTransitionEffectPage()
        }
        composable(Chapter6Pages.animatedContent.name) {
            AnimatedContentPage()
        }
        composable(Chapter6Pages.crossfade.name) {
            CrossfadePage()
        }
        composable(Chapter6Pages.animateContentSize.name) {
            ModifierAnimateContentSizePage()
        }
        composable(Chapter6Pages.animateAsState.name) {
            AnimateAsStatePage()
        }
        composable(Chapter6Pages.animatable.name) {
            AnimatablePage()
        }
        composable(Chapter6Pages.transition.name) {
            TransitionPage()
        }
        composable(Chapter6Pages.animationSpec.name) {
            AnimationSpecPage()
        }
        composable(Chapter6Pages.skeletonAnimation.name) {
            SkeletonAnimationPage()
        }
        composable(Chapter6Pages.collectionAnimation.name) {
            CollectionAnimationPage()
        }
    }
}

@Composable
fun Chapter6Page(navController: NavController) {
    FullPageWrapper {
        Column {
            ItemButton(text = "AnimatedVisibility") {
                navController.navigate(Chapter6Pages.animatedVisibility.name)
            }
            ItemButton(text = "AnimatedVisibilityTransitionEffect") {
                navController.navigate(Chapter6Pages.animatedVisibilityTransition.name)
            }
            ItemButton(text = "AnimatedContent") {
                navController.navigate(Chapter6Pages.animatedContent.name)
            }
            ItemButton(text = "Crossfade") {
                navController.navigate(Chapter6Pages.crossfade.name)
            }
            ItemButton(text = "Modifier.animateContentSize") {
                navController.navigate(Chapter6Pages.animateContentSize.name)
            }
            ItemButton(text = "animate*AsState") {
                navController.navigate(Chapter6Pages.animateAsState.name)
            }
            ItemButton(text = "Animatable") {
                navController.navigate(Chapter6Pages.animatable.name)
            }
            ItemButton(text = "Transition") {
                navController.navigate(Chapter6Pages.transition.name)
            }
            ItemButton(text = "AnimationSpec") {
                navController.navigate(Chapter6Pages.animationSpec.name)
            }
            ItemButton(text = "动画实战：骨架屏动画 Skeleton") {
                navController.navigate(Chapter6Pages.skeletonAnimation.name)
            }
            ItemButton(text = "动画实战：收藏按钮动画") {
                navController.navigate(Chapter6Pages.collectionAnimation.name)
            }
        }
    }
}