package life.lixiaoyu.composebloom.ui.theme

import androidx.compose.runtime.staticCompositionLocalOf
import life.lixiaoyu.composebloom.R

open class WelcomeAssets private constructor(
    val background: Int,
    val illos: Int,
    val logo: Int
) {
    object lightWelcomeAssets : WelcomeAssets(
        background = R.drawable.ic_light_welcome_bg,
        illos = R.drawable.ic_light_welcome_illos,
        logo = R.drawable.ic_light_logo
    )

    object darkWelcomeAssets : WelcomeAssets(
        background = R.drawable.ic_dark_welcome_bg,
        illos = R.drawable.ic_dark_welcome_illos,
        logo = R.drawable.ic_dark_logo
    )
}

internal val LocalWelcomeAssets = staticCompositionLocalOf {
    WelcomeAssets.lightWelcomeAssets as WelcomeAssets
}