package life.lixiaoyu.composebloom.data

import life.lixiaoyu.composebloom.R

object LocalDataSource {

    val bannerPlantList = listOf(
        Plant(1, "Desert chic", ("This is Desert chic, " * 10), R.drawable.desert_chic, 100F),
        Plant(2, "Tiny terrariums", ("This is Tiny terrariums, " * 10), R.drawable.tiny_terrariums, 110F),
        Plant(3, "Jungle vibes", ("This is Jungle vibes, " * 10), R.drawable.jungle_vibes, 120F),
        Plant(4, "Easy care", ("This is Easy care, " * 10), R.drawable.desert_chic, 130F),
        Plant(5, "Statements", ("This is Statements, " * 10), R.drawable.statements, 140F),
    )

    val selectPlantList = listOf(
        Plant(6, "Monstera", ("This is Monstera, " * 10), R.drawable.monstera, 500F),
        Plant(7, "Aglaonema", ("This is Aglaonema, " * 10), R.drawable.aglaonema, 60F),
        Plant(8, "Peace lily", ("This is Peace lily, " * 10), R.drawable.peace_lily, 70F),
        Plant(9, "Fiddle leaf tree", ("This is Fiddle leaf tree, " * 10), R.drawable.fiddle_leaf, 90F),
        Plant(10, "Snake plant", ("This is Snake plant, " * 10), R.drawable.snake_plant, 20F),
        Plant(11, "Pothos", ("This is Pothos, " * 10), R.drawable.pothos, 80F),
    )

    fun getPlantById(plantId: Int): Plant? {
        for (p in bannerPlantList) {
            if (p.id == plantId) {
                return p
            }
        }
        for (p in selectPlantList) {
            if (p.id == plantId) {
                return p
            }
        }
        return null
    }
}

private operator fun String.times(times: Int): String {
    val stringBuilder = StringBuilder()
    for (i in 0 until times) {
        stringBuilder.append(this)
    }
    return stringBuilder.toString()
}
