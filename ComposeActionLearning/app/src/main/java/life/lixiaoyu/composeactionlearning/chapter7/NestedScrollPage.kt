package life.lixiaoyu.composeactionlearning.chapter7

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.VectorConverter
import androidx.compose.animation.core.tween
import androidx.compose.foundation.MutatorMutex
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.SubcomposeLayout
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import life.lixiaoyu.composeactionlearning.DescItem
import kotlin.ranges.coerceAtLeast

@Composable
fun NestedScrollPage() {
    Column {
        var list by remember {
            mutableStateOf(('A'..'Z').toList())
        }
        val lazyListState = rememberLazyListState()
        SmartSwipeRefresh(
            onRefresh = {
                delay(1000)
                list = list.shuffled()
                lazyListState.scrollToItem(0)
            },
            loadingIndicator = {
                Box(
                    Modifier
                        .fillMaxWidth()
                        .height(100.dp)
                        .background(Color.Blue.copy(alpha = 0.5f)), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator(color = Color.White)
                }
            }
        ) {
            LazyColumn(
                contentPadding = PaddingValues(15.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp),
                state = lazyListState
            ) {
                items(list.size, key = { it }) { index ->
                    Card(
                        elevation = 8.dp,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(100.dp)
                    ) {
                        Box(
                            Modifier
                                .fillMaxSize()
                                .padding(15.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "Item ${list[index]}", fontSize = 20.sp
                            )
                        }
                    }
                }
            }
        }
    }
}


@SuppressLint("LongLogTag")
@Composable
fun SubcomposeSmartSwipeRefresh(
    indicator: @Composable () -> Unit,
    content: @Composable (Dp) -> Unit
) {
    SubcomposeLayout { constraints ->
        val indicatorPlaceable = subcompose("indicator", indicator).first().measure(constraints)
        val contentPlaceable = subcompose("content") {
            content(indicatorPlaceable.height.toDp())
        }.map {
            it.measure(constraints)
        }.first()
        Log.d(
            "SubcomposeSmartSwipeRefresh",
            "indicator height dp: ${indicatorPlaceable.height.toDp()}"
        )
        layout(contentPlaceable.width, contentPlaceable.height) {
            contentPlaceable.placeRelative(0, 0)
        }
    }
}

@Composable
fun SmartSwipeRefresh(
    onRefresh: suspend () -> Unit,
    state: SmartSwipeRefreshState = remember { SmartSwipeRefreshState() },
    loadingIndicator: @Composable () -> Unit = { CircularProgressIndicator() },
    content: @Composable () -> Unit
) {
    SubcomposeSmartSwipeRefresh(indicator = loadingIndicator) { height ->
        val connection = remember(state, height) {
            SwipeRefreshNestedScrollConnection(state, height)
        }
        Box(
            Modifier.nestedScroll(connection),
            contentAlignment = Alignment.TopCenter
        ) {
            Box(Modifier.offset(y = -height + state.indicatorOffset)) {
                loadingIndicator()
            }
            Box(Modifier.offset(y = state.indicatorOffset)) {
                content()
            }
        }
        val density = LocalDensity.current
        LaunchedEffect(Unit) {
            state.indicatorOffsetFlow.collect {
                val currentOffset = with(density) { state.indicatorOffset + it.toDp() }
                state.snapToOffset(currentOffset.coerceAtLeast(0.dp).coerceAtMost(height))
            }
        }
        LaunchedEffect(state.isRefreshing) {
            if (state.isRefreshing) {
                onRefresh()
                state.animateToOffset(0.dp)
                state.isRefreshing = false
            }
        }
    }
}

class SmartSwipeRefreshState {
    private val indicatorOffsetAnimatable = Animatable(0.dp, Dp.VectorConverter)
    val indicatorOffset
        get() = indicatorOffsetAnimatable.value

    private val _indicatorOffsetFlow = MutableStateFlow(0F)
    val indicatorOffsetFlow: Flow<Float>
        get() = _indicatorOffsetFlow
    val isSwipeInProgress by derivedStateOf { indicatorOffset != 0.dp }
    var isRefreshing: Boolean by mutableStateOf(false)

    fun updateOffsetData(value: Float) {
        _indicatorOffsetFlow.value = value
    }

    suspend fun snapToOffset(value: Dp) {
        indicatorOffsetAnimatable.snapTo(value)
    }

    suspend fun animateToOffset(value: Dp) {
        indicatorOffsetAnimatable.animateTo(value, tween(1000))
    }
}

class SwipeRefreshNestedScrollConnection(
    val state: SmartSwipeRefreshState,
    val height: Dp
) : NestedScrollConnection {

    @SuppressLint("LongLogTag")
    override fun onPreScroll(available: Offset, source: NestedScrollSource): Offset {
        Log.d("SwipeRefreshNestedScrollConnection", "onPreScroll")
        // 上滑时，希望滑动手势先被父布局消费（减少加载指示器的偏移量）
        // 如果加载指示器还未出现，则不需要消费，剩余的滑动手势事件会交给子布局列表继续进行消费
        return if (source == NestedScrollSource.Drag && available.y < 0) {
            state.updateOffsetData(available.y)
            if (state.isSwipeInProgress) Offset(x = 0f, y = available.y) else Offset.Zero
        } else Offset.Zero
    }

    @SuppressLint("LongLogTag")
    override fun onPostScroll(
        consumed: Offset,
        available: Offset,
        source: NestedScrollSource
    ): Offset {
        Log.d("SwipeRefreshNestedScrollConnection", "onPostScroll")
        // 下滑时，希望滑动手势先交给子布局中的列表进行处理
        // 如果列表已经滑动到顶部说明此时滑动手势事件没有被消费，此时再交由父布局进行消费
        return if (source == NestedScrollSource.Drag && available.y > 0) {
            state.updateOffsetData(available.y)
            Offset(x = 0f, y = available.y)
        } else Offset.Zero
    }

    @SuppressLint("LongLogTag")
    override suspend fun onPreFling(available: Velocity): Velocity {
        Log.d("SwipeRefreshNestedScrollConnection", "onPreFling")
        // 如果加载指示器已经被拖动并超过一半，则应该吸附到加载状态，否则就收缩到初始状态
        if (state.indicatorOffset > height / 2) {
            state.animateToOffset(height)
            state.isRefreshing = true
        } else {
            state.animateToOffset(0.dp)
            state.isRefreshing = false
        }
        return super.onPreFling(available)
    }

    @SuppressLint("LongLogTag")
    override suspend fun onPostFling(consumed: Velocity, available: Velocity): Velocity {
        Log.d("SwipeRefreshNestedScrollConnection", "onPostFling")
        if (available.y <= 0 && state.indicatorOffset <= 0.dp && state.isRefreshing) {
            state.isRefreshing = false
        }
        return super.onPostFling(consumed, available)
    }
}
