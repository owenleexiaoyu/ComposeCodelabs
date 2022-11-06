package life.lixiaoyu.composebloom

import android.content.Intent
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.UiMode
import androidx.compose.ui.unit.dp
import life.lixiaoyu.composebloom.ui.theme.BloomTheme

class WelcomeActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BloomTheme {
                WelcomePage()
            }
        }
    }
}

@Composable
fun WelcomePage() {
    Box(modifier = Modifier
        .fillMaxSize()
        .background(MaterialTheme.colors.primary)
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_light_welcome_bg),
            "Background of welcome page",
            modifier = Modifier.fillMaxSize()
        )
        WelcomeContent()
    }
}

@Composable
fun WelcomeContent() {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        VerticalSpacer(height = 72.dp)
        Image(
            painter = painterResource(id = R.drawable.ic_light_welcome_illos),
            "Illos of welcome page",
            modifier = Modifier
                .wrapContentSize()
                .padding(start = 88.dp)
        )
        VerticalSpacer(height = 48.dp)
        Image(
            painter = painterResource(id = R.drawable.ic_light_logo),
            "Logo of welcome page",
            modifier = Modifier
                .wrapContentSize()
                .height(32.dp)
        )
        Box(modifier = Modifier
            .height(24.dp)
            .fillMaxWidth(),
            contentAlignment = Alignment.BottomCenter
        ) {
            Text(
                "Beautiful home garden solutions",
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.subtitle1,
                color = MaterialTheme.colors.onPrimary
            )
        }
        VerticalSpacer(height = 40.dp)
        Button(
            onClick = {},
            modifier = Modifier
                .height(48.dp)
                .padding(horizontal = 16.dp)
                .fillMaxWidth()
                .clip(MaterialTheme.shapes.medium),
            colors = ButtonDefaults.buttonColors(
                backgroundColor = MaterialTheme.colors.secondary,
                contentColor = MaterialTheme.colors.onSecondary
            )
        ) {
            Text(
                "Create account",
                style = MaterialTheme.typography.button
            )
        }
        VerticalSpacer(height = 24.dp)
        val context = LocalContext.current
        TextButton(
            onClick = {
                context.startActivity(Intent(context, LoginActivity::class.java))
            }
        ) {
            Text(
                "Log in",
                style = MaterialTheme.typography.button,
                color = MaterialTheme.colors.secondary
            )
        }
    }
}

@Preview(showBackground = true)
@Preview(showBackground = true, uiMode = UI_MODE_NIGHT_YES)
@Composable
fun WelcomePagePreview() {
    BloomTheme {
        WelcomePage()
    }
}