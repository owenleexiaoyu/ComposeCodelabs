package life.xiaoyu.composestudyjam

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import life.xiaoyu.composestudyjam.ui.theme.ComposeStudyJamTheme

class ComposeArticleActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ArticleScreen()
        }
    }
}

@Composable
fun ArticleScreen() {
    ComposeStudyJamTheme {
        Column {
            Image(
                painter = painterResource(id = R.drawable.bg_compose_background),
                contentDescription = null,
                modifier = Modifier.fillMaxWidth()
            )
            Text(
                text = stringResource(R.string.jetpack_compose_tutorial_title),
                fontSize = 24.sp,
                modifier = Modifier.padding(16.dp)
            )
            Text(
                text = stringResource(R.string.jetpack_compose_tutorial_desc1),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                textAlign = TextAlign.Justify
            )
            Text(
                text = stringResource(R.string.jetpack_compose_tutorial_desc2),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                textAlign = TextAlign.Justify
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ArticleScreenPreview() {
    ArticleScreen()
}