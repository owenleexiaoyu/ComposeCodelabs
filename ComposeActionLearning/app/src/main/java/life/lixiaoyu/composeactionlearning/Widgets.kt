package life.lixiaoyu.composeactionlearning

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowRight
import androidx.compose.material.icons.filled.ArrowRightAlt
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import life.lixiaoyu.composeactionlearning.ui.theme.ComposeActionLearningTheme

@Composable
fun ItemButton(text: String, onClick: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp)
            .clickable {
                onClick()
            }
    ) {
        Row(
            modifier = Modifier
                .weight(1F)
                .padding(vertical = 5.dp, horizontal = 10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = text, maxLines = 1, overflow = TextOverflow.Ellipsis)
            Spacer(modifier = Modifier.weight(1F))
            Icon(Icons.Filled.ChevronRight, "", modifier = Modifier.size(20.dp))
        }
        Divider(modifier = Modifier.padding(horizontal = 10.dp))
    }
}

@Composable
fun DescItem(title: String) {
    Row(
        modifier = Modifier
            .padding(5.dp)
            .background(Color.Gray)
            .padding(vertical = 2.dp, horizontal = 5.dp)
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Icon(imageVector = Icons.Filled.PlayArrow, contentDescription = "arrow", modifier = Modifier.size(20.dp))
        Spacer(modifier = Modifier.width(5.dp))
        Text(title)
    }
}

@Composable
fun FullPageWrapper(child: @Composable () -> Unit) {
    ComposeActionLearningTheme {
        // A surface container using the 'background' color from the theme
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colors.background
        ) {
            child.invoke()
        }
    }
}

/**
 * 点击 Box 时，弹出提示信息
 */
@Composable
fun InfoBox(
    info: String,
    modifier: Modifier = Modifier,
    child: @Composable (() -> Unit)? = null
) {
    val context = LocalContext.current
    Box(modifier.clickable {
        Toast.makeText(context, info, Toast.LENGTH_SHORT).show()
    }) {
        child?.invoke()
    }
}

@Composable
fun HorizontalSpacer(width: Dp) {
    Spacer(modifier = Modifier.width(width))
}

@Composable
fun VerticalSpacer(height: Dp) {
    Spacer(modifier = Modifier.height(height))
}