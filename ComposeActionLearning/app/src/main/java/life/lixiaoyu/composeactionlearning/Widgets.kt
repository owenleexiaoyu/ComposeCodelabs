package life.lixiaoyu.composeactionlearning

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import life.lixiaoyu.composeactionlearning.ui.theme.ComposeActionLearningTheme

@Composable
fun ItemButton(text: String, onClick: () -> Unit) {
    Button(
        modifier = Modifier.fillMaxWidth()
            .padding(horizontal = 12.dp, vertical = 4.dp),
        onClick = onClick
    ) {
        Text(text = text)
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