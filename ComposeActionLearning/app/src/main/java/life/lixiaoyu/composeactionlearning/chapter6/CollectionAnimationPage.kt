package life.lixiaoyu.composeactionlearning.chapter6

import androidx.compose.animation.*
import androidx.compose.animation.core.animateDp
import androidx.compose.animation.core.animateInt
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import life.lixiaoyu.composeactionlearning.DescItem
import life.lixiaoyu.composeactionlearning.FullPageWrapper
import life.lixiaoyu.composeactionlearning.ui.theme.Purple500

@Composable
fun CollectionAnimationPage() {
    FullPageWrapper {
        Column(Modifier.padding(10.dp)) {
            CollectionAnimationEffectUsingHighLevelAnimationAPI()
            CollectionAnimationEffectUsingLowLevelAnimationAPI()
        }
    }
}

data class CollectionButtonUiState(
    val backgroundColor: Color,
    val textColor: Color,
    val roundedCorner: Int,
    val buttonWidth: Dp
)

enum class CollectionButtonState(val ui: CollectionButtonUiState) {
    UnCollected(CollectionButtonUiState(Color.White, Purple500, 6, 300.dp)),
    Collected(CollectionButtonUiState(Purple500, Color.White, 50, 60.dp))
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun ColumnScope.CollectionAnimationEffectUsingHighLevelAnimationAPI() {
    DescItem(title = "Use high level animation API AnimatedContent implement collection animation")
    var buttonState by remember { mutableStateOf(CollectionButtonState.UnCollected) }
    AnimatedContent(
        modifier = Modifier.align(Alignment.CenterHorizontally),
        targetState = buttonState,
        transitionSpec = {
            fadeIn(tween(3000)) with fadeOut(tween(3000))
        }
    ) { targetState ->
        Button(
            border = BorderStroke(1.dp, Purple500),
            modifier = Modifier.size(targetState.ui.buttonWidth, 60.dp),
            shape = RoundedCornerShape(targetState.ui.roundedCorner),
            colors = ButtonDefaults.buttonColors(backgroundColor = targetState.ui.backgroundColor),
            onClick = {
                buttonState =
                    if (buttonState == CollectionButtonState.UnCollected) CollectionButtonState.Collected else CollectionButtonState.UnCollected
            }
        ) {
            if (targetState == CollectionButtonState.UnCollected) {
                Icon(
                    tint = targetState.ui.textColor,
                    imageVector = Icons.Default.FavoriteBorder,
                    modifier = Modifier
                        .size(24.dp)
                        .align(Alignment.CenterVertically),
                    contentDescription = ""
                )
                Spacer(modifier = Modifier.width(16.dp))
                Text(
                    "Add To Favorites!",
                    softWrap = false,
                    modifier = Modifier.align(Alignment.CenterVertically),
                    color = targetState.ui.textColor
                )
            } else {
                Icon(
                    tint = targetState.ui.textColor,
                    imageVector = Icons.Default.Favorite,
                    modifier = Modifier.size(24.dp),
                    contentDescription = ""
                )
            }
        }

    }
}

@Composable
fun ColumnScope.CollectionAnimationEffectUsingLowLevelAnimationAPI() {
    DescItem(title = "Use low level animation API updateTransition implement collection animation")
    var buttonState by remember { mutableStateOf(CollectionButtonState.UnCollected) }
    val transition = updateTransition(targetState = buttonState, label = "CollectionButtonAnimation")
    val backgroundColor by transition.animateColor(transitionSpec = { tween(3000)}, label = "Background Animation") {
        it.ui.backgroundColor
    }
    val textColor by transition.animateColor(transitionSpec = { tween(3000)}, label = "TextColor Animation") {
        it.ui.textColor
    }
    val roundedCorner by transition.animateInt(transitionSpec = { tween(3000)}, label = "RoundedCorner Animation") {
        it.ui.roundedCorner
    }
    val buttonWidth by transition.animateDp(transitionSpec = { tween(3000)}, label = "ButtonWidth Animation") {
        it.ui.buttonWidth
    }
    Button(
        border = BorderStroke(1.dp, Purple500),
        modifier = Modifier.size(buttonWidth, 60.dp).align(Alignment.CenterHorizontally),
        shape = RoundedCornerShape(roundedCorner),
        colors = ButtonDefaults.buttonColors(backgroundColor = backgroundColor),
        onClick = {
            buttonState =
                if (buttonState == CollectionButtonState.UnCollected) CollectionButtonState.Collected else CollectionButtonState.UnCollected
        }
    ) {
        if (buttonState == CollectionButtonState.UnCollected) {
            Icon(
                tint = textColor,
                imageVector = Icons.Default.FavoriteBorder,
                modifier = Modifier
                    .size(24.dp)
                    .align(Alignment.CenterVertically),
                contentDescription = ""
            )
            Spacer(modifier = Modifier.width(16.dp))
            Text(
                "Add To Favorites!",
                softWrap = false,
                modifier = Modifier.align(Alignment.CenterVertically),
                color = textColor
            )
        } else {
            Icon(
                tint = textColor,
                imageVector = Icons.Default.Favorite,
                modifier = Modifier.size(24.dp),
                contentDescription = ""
            )
        }
    }
}