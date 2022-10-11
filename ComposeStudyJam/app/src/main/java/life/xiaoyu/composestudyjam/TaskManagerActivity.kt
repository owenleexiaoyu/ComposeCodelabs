package life.xiaoyu.composestudyjam

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import life.xiaoyu.composestudyjam.ui.theme.ComposeStudyJamTheme

class TaskManagerActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TaskManagerScreen()
        }
    }
}

@Composable
fun TaskManagerScreen() {
    ComposeStudyJamTheme {
        Column (
            modifier = Modifier.fillMaxWidth()
                .fillMaxHeight(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Image(
                painter = painterResource(R.drawable.ic_task_completed),
                contentDescription = null
            )
            Text(
                text = "All tasks completed",
                fontSize = 24.sp,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.padding(top = 24.dp, bottom = 8.dp)
            )
            Text(
                text = "Nice work!",
                fontSize = 16.sp
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TaskManagerScreenPreview() {
    TaskManagerScreen()
}