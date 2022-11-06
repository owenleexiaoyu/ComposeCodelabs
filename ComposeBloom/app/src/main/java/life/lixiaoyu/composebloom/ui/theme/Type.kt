package life.lixiaoyu.composebloom.ui.theme

import androidx.compose.material.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import life.lixiaoyu.composebloom.R

private val nunitoSansFamily = FontFamily(
    Font(R.font.nunitosans_bold, FontWeight.Bold),
    Font(R.font.nunitosans_semibold, FontWeight.SemiBold),
    Font(R.font.nunitosans_light, FontWeight.Light)
)

private val h1 = TextStyle(
    fontSize = 18.sp,
    fontFamily = nunitoSansFamily,
    fontWeight = FontWeight.Bold
)

private val h2 = TextStyle(
    fontSize = 14.sp,
    letterSpacing = 0.15.sp,
    fontFamily = nunitoSansFamily,
    fontWeight = FontWeight.Bold
)

private val subtitle1 = TextStyle(
    fontSize = 16.sp,
    fontFamily = nunitoSansFamily,
    fontWeight = FontWeight.Light
)

private val body1 = TextStyle(
    fontSize = 14.sp,
    fontFamily = nunitoSansFamily,
    fontWeight = FontWeight.Light
)

private val body2 = TextStyle(
    fontSize = 12.sp,
    fontFamily = nunitoSansFamily,
    fontWeight = FontWeight.Light
)

private val button = TextStyle(
    fontSize = 14.sp,
    letterSpacing = 1.sp,
    fontFamily = nunitoSansFamily,
    fontWeight = FontWeight.SemiBold
)

private val caption = TextStyle(
    fontSize = 12.sp,
    fontFamily = nunitoSansFamily,
    fontWeight = FontWeight.SemiBold
)

// Set of Material typography styles to start with
val Typography = Typography(
    h1 = h1,
    h2 = h2,
    subtitle1 = subtitle1,
    body1 = body1,
    body2 = body2,
    button = button,
    caption = caption
)