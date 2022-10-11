package life.xiaoyu.composestudyjam

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import life.xiaoyu.composestudyjam.ui.theme.ComposeStudyJamTheme

class ComposeQuadrantActivity: ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            QuadrantScreen()
        }
    }
}

@Composable
fun QuadrantCard(
    title: String,
    desc: String,
    backgroundColor: Color,
    modifier: Modifier
) {
    Column(
        modifier = modifier
            .background(backgroundColor)
            .padding(16.dp)
            .fillMaxHeight()
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = title,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 16.dp)
        )
        Text(
            text = desc
        )
    }
}

@Composable
fun QuadrantScreen() {
    ComposeStudyJamTheme {
        Column {
            Row(
                modifier = Modifier.weight(1F)
            ) {
                QuadrantCard(
                    title = "Text composable",
                    desc = "Displays text and follows Material Design guidelines.",
                    backgroundColor = Color.Green,
                    modifier = Modifier.weight(1F)
                )
                QuadrantCard(
                    title = "Image composable",
                    desc = "Creates a composable that lays out and draws a given Painter class object.",
                    backgroundColor = Color.Yellow,
                    modifier = Modifier.weight(1F)
                )
            }
            Row (
                modifier = Modifier.weight(1F)
            ) {
                QuadrantCard(
                    title = "Row composable",
                    desc = "A layout composable that places its children in a horizontal sequence.",
                    backgroundColor = Color.Cyan,
                    modifier = Modifier.weight(1F)
                )
                QuadrantCard(
                    title = "Column composable",
                    desc = "A layout composable that places its children in a vertical sequence.",
                    backgroundColor = Color.LightGray,
                    modifier = Modifier.weight(1F)
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun QuadrantScreenPreview() {
    QuadrantScreen()
}