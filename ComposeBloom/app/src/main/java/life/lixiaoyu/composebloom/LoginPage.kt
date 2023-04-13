package life.lixiaoyu.composebloom

import android.content.Intent
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import life.lixiaoyu.composebloom.TextUtils.withStyle
import life.lixiaoyu.composebloom.ui.theme.BloomTheme

@Composable
fun LoginPage(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Login with email",
            style = MaterialTheme.typography.h1,
            color = MaterialTheme.colors.onBackground
        )
        VerticalSpacer(height = 16.dp)
        OutlinedTextField(
            value = "",
            onValueChange = {},
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            placeholder = {
                Text(
                    "Email address",
                    style = MaterialTheme.typography.body1,
                    color = MaterialTheme.colors.onBackground
                )
            }
        )
        VerticalSpacer(height = 8.dp)
        OutlinedTextField(
            value = "",
            onValueChange = {},
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            placeholder = {
                Text(
                    "Password(8+ characters)",
                    style = MaterialTheme.typography.body1,
                    color = MaterialTheme.colors.onBackground
                )
            }
        )
        VerticalSpacer(height = 8.dp)
        HintWithUnderline()
        VerticalSpacer(height = 16.dp)
        val context = LocalContext.current
        Button(
            onClick = {
                navController.navigate("home")
            },
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
                "Log in",
                style = MaterialTheme.typography.button
            )
        }
    }
}

@Composable
fun HintWithUnderline() {
    val annotatedString = buildAnnotatedString {
        withStyle(
            style = MaterialTheme.typography.body2.copy(color = MaterialTheme.colors.onBackground),
            useSpan = true
        ) {
            append("By clicking below, you agree to our ")
        }
        withStyle(
            style = MaterialTheme.typography.body2.copy(
                color = MaterialTheme.colors.onBackground,
                textDecoration = TextDecoration.Underline
            ),
            useSpan = true
        ) {
            append("Terms of Use")
        }
        withStyle(
            style = MaterialTheme.typography.body2.copy(color = MaterialTheme.colors.onBackground),
            useSpan = true
        ) {
            append(" and consent to our ")
        }
        withStyle(
            style = MaterialTheme.typography.body2.copy(
                color = MaterialTheme.colors.onBackground,
                textDecoration = TextDecoration.Underline
            ),
            useSpan = true
        ) {
            append("Privacy Policy")
        }
    }
    Text(
        text = annotatedString,
        lineHeight = 16.sp,
        textAlign = TextAlign.Center
    )
}

@Preview(showBackground = true)
@Preview(showBackground = true, uiMode = UI_MODE_NIGHT_YES)
@Composable
fun LoginPagePreview() {
    BloomTheme {
        val navController = rememberNavController()
        LoginPage(navController)
    }
}