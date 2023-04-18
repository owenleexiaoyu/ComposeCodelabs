package life.lixiaoyu.composebloom.data

import androidx.annotation.DrawableRes


data class Plant(
    val id: Int,
    val name: String,
    val description: String,
    @DrawableRes val image: Int,
    val price: Float,
)
