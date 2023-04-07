package life.lixiaoyu.composeactionlearning.chapter9

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.runtime.*
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import life.lixiaoyu.composeactionlearning.FullPageWrapper
import life.lixiaoyu.composeactionlearning.ui.theme.Purple200

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun PullRefreshDemoPage() {
    FullPageWrapper {
        val viewModel: MyViewModel = viewModel()
        val dataList = remember { viewModel.dataList }
        val refreshing by viewModel.isRefreshing
        val pullRefreshState = rememberPullRefreshState(refreshing = refreshing, onRefresh = {
            viewModel.refresh()
        })
        Box(modifier = Modifier.pullRefresh(pullRefreshState)) {
            LazyColumn(Modifier.fillMaxSize()) {
                items(dataList.size) { index ->
                    Text(
                        text = dataList[index],
                        modifier = Modifier.fillMaxWidth()
                            .padding(vertical = 5.dp, horizontal = 10.dp)
                            .background(Color.LightGray)
                            .padding(10.dp)
                    )
                }
            }
            PullRefreshIndicator(
                refreshing = refreshing,
                state = pullRefreshState,
                modifier = Modifier.align(Alignment.TopCenter),
                contentColor = Purple200
            )
        }
    }
}

class MyViewModel: ViewModel() {
    val dataList: SnapshotStateList<String> = mutableStateListOf()
    var isRefreshing: MutableState<Boolean> = mutableStateOf(false)

    init {
        (0 until 50).forEach {
            dataList.add("Item: $it")
        }
    }

    fun refresh() {
        viewModelScope.launch {
            isRefreshing.value = true
            delay(2000L)
            dataList.add(0, "新添加的 Item")
            isRefreshing.value = false
        }
    }
}