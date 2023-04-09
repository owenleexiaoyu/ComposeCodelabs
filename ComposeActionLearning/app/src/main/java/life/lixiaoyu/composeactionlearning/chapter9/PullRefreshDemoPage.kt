package life.lixiaoyu.composeactionlearning.chapter9

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.PullRefreshState
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.runtime.*
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
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
//            GlowIndicator(
//                refreshing = refreshing,
//                pullRefreshState = pullRefreshState,
//                refreshTriggerDistance = 20.dp
//            )
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

/**
 * A custom indicator which displays a glow and progress indicator
 */
@OptIn(ExperimentalMaterialApi::class)
@Composable
fun GlowIndicator(
    refreshing: Boolean,
    pullRefreshState: PullRefreshState,
    refreshTriggerDistance: Dp,
    color: Color = MaterialTheme.colors.primary,
) {
    Box(
        Modifier
            .drawWithCache {
                onDrawBehind {
                    val distance = refreshTriggerDistance.toPx()
                    val progress = (pullRefreshState.progress / distance).coerceIn(0f, 1f)
                    // We draw a translucent glow
                    val brush = Brush.verticalGradient(
                        0f to color.copy(alpha = 0.45f),
                        1f to color.copy(alpha = 0f)
                    )
                    // And fade the glow in/out based on the swipe progress
                    drawRect(brush = brush, alpha = FastOutSlowInEasing.transform(progress))
                }
            }
            .fillMaxWidth()
            .height(72.dp)
    ) {
        if (refreshing) {
            // If we're refreshing, show an indeterminate progress indicator
            LinearProgressIndicator(Modifier.fillMaxWidth())
        } else {
            // Otherwise we display a determinate progress indicator with the current swipe progress
            val trigger = with(LocalDensity.current) { refreshTriggerDistance.toPx() }
            val progress = (pullRefreshState.progress / trigger).coerceIn(0f, 1f)
            LinearProgressIndicator(
                progress = progress,
                modifier = Modifier.fillMaxWidth(),
            )
        }
    }
}