package life.xiaoyu.composestudyjam

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import life.xiaoyu.composestudyjam.ui.theme.ComposeStudyJamTheme


class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MainScreen(this)
        }
    }
}

@Composable
fun ItemButton(
    text: String,
    onClick: () -> Unit
) {
    Button(
        modifier = Modifier.fillMaxWidth(),
        onClick = onClick
    ) {
        Text(
            text = text
        )
    }
}

@Composable
fun MainScreen(activity: Activity) {
    ComposeStudyJamTheme {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            ItemButton("Happy Birthday Card") {
                activity.startActivity(Intent(activity, HappyBirthdayCardActivity::class.java))
            }
            ItemButton("Jetpack Compose tutorial") {
                activity.startActivity(Intent(activity, ComposeArticleActivity::class.java))
            }
            ItemButton("TaskManager") {
                activity.startActivity(Intent(activity, TaskManagerActivity::class.java))
            }
            ItemButton("Compose Quadrant") {
                activity.startActivity(Intent(activity, ComposeQuadrantActivity::class.java))
            }
        }
    }
}