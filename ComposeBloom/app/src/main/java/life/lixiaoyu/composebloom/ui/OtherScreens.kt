package life.lixiaoyu.composebloom.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

@Composable
fun FavoriteScreen() {
    Box(
        Modifier
            .fillMaxSize()
            .background(Color.Green.copy(alpha = 0.4F))
    ) {
        Text(
            "This is Favorite Screen",
            fontSize = 20.sp,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.align(Alignment.Center)
        )
    }
}

@Composable
fun CartScreen() {
    Box(
        Modifier
            .fillMaxSize()
            .background(Color.Blue.copy(alpha = 0.4F))
    ) {
        Text(
            "This is Cart Screen",
            fontSize = 20.sp,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.align(Alignment.Center)
        )
    }
}

@Composable
fun ProfileScreen() {
    Box(
        Modifier
            .fillMaxSize()
            .background(Color.Magenta.copy(alpha = 0.4F))
    ) {
        Text(
            "This is Profile Screen",
            fontSize = 20.sp,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.align(Alignment.Center)
        )
    }
}