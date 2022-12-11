package life.lixiaoyu.composeactionlearning.chapter4

import android.os.Bundle
import android.os.Parcelable
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.parcelize.Parcelize
import life.lixiaoyu.composeactionlearning.DescItem
import life.lixiaoyu.composeactionlearning.FullPageWrapper
import life.lixiaoyu.composeactionlearning.InfoBox

class RememberSavableActivity: ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FullPageWrapper {
                RememberSaveablePage()
            }
        }
    }
}

@Parcelize
data class City(
    val name: String,
    val country: String
): Parcelable

data class UnParcelableCity(
    val name: String,
    val country: String
)

object CitySaver : Saver<UnParcelableCity, Bundle> {
    override fun restore(value: Bundle): UnParcelableCity? {
        val name = value.getString("name") ?: ""
        val country = value.getString("country") ?: ""
        return UnParcelableCity(name, country)
    }

    override fun SaverScope.save(value: UnParcelableCity): Bundle? {
        return Bundle().apply {
            putString("name", value.name)
            putString("country", value.country)
        }
    }
}

val MapCitySaver = run {
    val nameKey = "name"
    val countryKey = "country"
    mapSaver(
        save = { mapOf(nameKey to it.name, countryKey to it.country) },
        restore = { UnParcelableCity(it[nameKey] as String, it[countryKey] as String) }
    )
}

val ListCitySaver = listSaver<UnParcelableCity, Any>(
    save = {
        listOf(it.name, it.country)
    },
    restore = {
        UnParcelableCity(it[0] as String, it[1] as String)
    }
)

@Composable
fun RememberSaveablePage() {
    Column {
        DescItem(title = "remember 缓存状态")
        val selectedCity = remember { mutableStateOf(City("Shanghai", "China"))}
        InfoBox(info = "City 数据不能在 ConfigurationChanged、不保留活动等场景恢复") {
            Text(
                text = "City you select is: ${selectedCity.value.country}-${selectedCity.value.name}",
                modifier = Modifier.padding(10.dp)
            )
        }
        Button(onClick = {
            selectedCity.value = City("Beijing", "China")
        }) {
            Text("Change Select City to Beijing")
        }
        DescItem(title = "rememberSaveable 保存 Parcelable 数据")
        val selectedCity2 = rememberSaveable { mutableStateOf(City("Shanghai", "China"))}
        InfoBox(info = "City 数据在 ConfigurationChanged、不保留活动等场景可以恢复") {
            Text(
                text = "City you select is: ${selectedCity2.value.country}-${selectedCity2.value.name}",
                modifier = Modifier.padding(10.dp)
            )
        }
        Button(onClick = {
            selectedCity2.value = City("Beijing", "China")
        }) {
            Text("Change Select City to Beijing")
        }
        DescItem(title = "rememberSaveable 保存 Saver 数据")
        val selectedCity3 = rememberSaveable(stateSaver = CitySaver) { mutableStateOf(UnParcelableCity("Shanghai", "China"))}
        InfoBox(info = "City 数据在 ConfigurationChanged、不保留活动等场景可以恢复") {
            Text(
                text = "City you select is: ${selectedCity3.value.country}-${selectedCity3.value.name}",
                modifier = Modifier.padding(10.dp)
            )
        }
        Button(onClick = {
            selectedCity3.value = UnParcelableCity("Beijing", "China")
        }) {
            Text("Change Select City to Beijing")
        }
        DescItem(title = "rememberSaveable MapSaver")
        val selectedCity4 = rememberSaveable(stateSaver = MapCitySaver) { mutableStateOf(UnParcelableCity("Shanghai", "China"))}
        InfoBox(info = "City 数据在 ConfigurationChanged、不保留活动等场景可以恢复") {
            Text(
                text = "City you select is: ${selectedCity4.value.country}-${selectedCity4.value.name}",
                modifier = Modifier.padding(10.dp)
            )
        }
        Button(onClick = {
            selectedCity4.value = UnParcelableCity("Beijing", "China")
        }) {
            Text("Change Select City to Beijing")
        }
        DescItem(title = "rememberSaveable ListSaver")
        val selectedCity5 = rememberSaveable(stateSaver = ListCitySaver) { mutableStateOf(UnParcelableCity("Shanghai", "China"))}
        InfoBox(info = "City 数据在 ConfigurationChanged、不保留活动等场景可以恢复") {
            Text(
                text = "City you select is: ${selectedCity5.value.country}-${selectedCity5.value.name}",
                modifier = Modifier.padding(10.dp)
            )
        }
        Button(onClick = {
            selectedCity5.value = UnParcelableCity("Beijing", "China")
        }) {
            Text("Change Select City to Beijing")
        }
    }
}


@Preview(showBackground = true)
@Composable
fun RememberSaveablePagePreview() {
    FullPageWrapper {
        RememberSaveablePage()
    }
}



