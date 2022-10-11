package life.xiaoyu.composestudyjam

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import life.xiaoyu.composestudyjam.ui.theme.ComposeStudyJamTheme

class MessageCardListActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MessageCardListScreen()
        }
    }
}

@Composable
fun MessageCard(message: Message) {
    Row(
        modifier = Modifier.padding(8.dp)
    ){
        Image(
            painter = painterResource(id = R.drawable.avactor),
            contentDescription = null,
            modifier = Modifier
                .size(50.dp)
                .clip(CircleShape)
                .border(1.5.dp, color = MaterialTheme.colors.secondary, shape = CircleShape)
        )
        Spacer(modifier = Modifier.padding(horizontal = 8.dp))
        Column {
            Text(message.author)
            Spacer(modifier = Modifier.padding(vertical = 4.dp))
            Text(message.body)
        }
    }
}

data class Message(
    val author: String,
    val body: String
)

@Composable
fun MessageCardListScreen() {
    ComposeStudyJamTheme {
        MessageCard(message = Message("Jetpack Compose 博物馆", "我们开始更新啦"))
    }
}

@Preview(showBackground = true)
@Composable
fun LibraryListScreenPreview() {
    MessageCardListScreen()
}
