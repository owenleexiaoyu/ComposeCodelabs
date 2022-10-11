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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import life.xiaoyu.composestudyjam.ui.theme.ComposeStudyJamTheme

class HappyBirthdayCardActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeStudyJamTheme {
                BirthdayCardScreen(stringResource(R.string.happy_birthday_text), stringResource(R.string.from_text))
            }
        }
    }
}

@Composable
fun BirthdayCardScreen(message: String, from: String) {
    Image(
        painter = painterResource(R.drawable.androidparty),
        contentDescription = null,
        modifier = Modifier
            .fillMaxHeight()
            .fillMaxWidth(),
        contentScale = ContentScale.Crop
    )
    Column {
        Text(
            text = message,
            fontSize = 36.sp,
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentWidth(Alignment.CenterHorizontally)
                .padding(16.dp)
        )
        Text(
            text = from,
            fontSize = 24.sp,
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentWidth(Alignment.CenterHorizontally)
                .padding(end = 16.dp)
        )
    }
}


@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    ComposeStudyJamTheme {
        BirthdayCardScreen(stringResource(R.string.happy_birthday_text), stringResource(R.string.from_text))
    }
}