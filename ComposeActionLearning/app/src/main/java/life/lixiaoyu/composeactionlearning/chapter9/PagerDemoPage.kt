package life.lixiaoyu.composeactionlearning.chapter9

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronLeft
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.SkipNext
import androidx.compose.material.icons.filled.SkipPrevious
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.google.accompanist.pager.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import life.lixiaoyu.composeactionlearning.DescItem
import life.lixiaoyu.composeactionlearning.FullPageWrapper
import life.lixiaoyu.composeactionlearning.HorizontalSpacer
import life.lixiaoyu.composeactionlearning.ItemButton
import life.lixiaoyu.composeactionlearning.chapter5.topics
import life.lixiaoyu.composeactionlearning.ui.theme.Purple500

@Composable
fun PagerDemoPage(navController: NavController) {
    FullPageWrapper {
        Column {
            ItemButton(text = "Pager 的基础用法") {
                navController.navigate(Chapter9Pages.pager_basic.name)
            }
            ItemButton(text = "Pager 和 Indicator用法") {
                navController.navigate(Chapter9Pages.pager_indicator.name)
            }
            ItemButton(text = "Pager 和 TabLayout 配合使用") {
                navController.navigate(Chapter9Pages.pager_tabLayout.name)
            }
        }
    }
}

val pictureList = listOf(
    "https://images.pexels.com/photos/2662116/pexels-photo-2662116.jpeg?auto=compress&cs=tinysrgb&w=800",
    "https://images.pexels.com/photos/1287145/pexels-photo-1287145.jpeg?auto=compress&cs=tinysrgb&w=800",
    "https://images.pexels.com/photos/1166209/pexels-photo-1166209.jpeg?auto=compress&cs=tinysrgb&w=800",
    "https://images.pexels.com/photos/808465/pexels-photo-808465.jpeg?auto=compress&cs=tinysrgb&w=800",
    "https://images.pexels.com/photos/4818015/pexels-photo-4818015.jpeg?auto=compress&cs=tinysrgb&w=800",
    "https://images.pexels.com/photos/16120768/pexels-photo-16120768.jpeg?auto=compress&cs=tinysrgb&w=800",
    "https://images.pexels.com/photos/1996333/pexels-photo-1996333.jpeg?auto=compress&cs=tinysrgb&w=800",
    "https://images.pexels.com/photos/205001/pexels-photo-205001.jpeg?auto=compress&cs=tinysrgb&w=800",
    "https://images.pexels.com/photos/247373/pexels-photo-247373.jpeg?auto=compress&cs=tinysrgb&w=800",
    "https://images.pexels.com/photos/678448/pexels-photo-678448.jpeg?auto=compress&cs=tinysrgb&w=800",
    "https://images.pexels.com/photos/306036/pexels-photo-306036.jpeg?auto=compress&cs=tinysrgb&w=800",
)

/**
 * 练习 Pager 的基础用法
 */
@OptIn(ExperimentalPagerApi::class)
@Composable
fun PagerBasicDemoPage() {
    FullPageWrapper {
        var isHorizontal by remember {
            mutableStateOf(true)
        }
        val pagerState = rememberPagerState()
        val scope = rememberCoroutineScope()
        Column {
            DescItem(title = "Pager 的基础用法")
            Row(
                modifier = Modifier.padding(horizontal = 10.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("方向：")
                RadioButton(selected = isHorizontal, onClick = {
                    isHorizontal = true
                })
                Text("Horizontal")
                HorizontalSpacer(width = 20.dp)
                RadioButton(selected = !isHorizontal, onClick = {
                    isHorizontal = false
                })
                Text("Vertical")
            }
            if (isHorizontal) {
                HorizontalPager(
                    count = pictureList.size,
                    state = pagerState,
                    contentPadding = PaddingValues(horizontal = 30.dp),
                    itemSpacing = 10.dp,
                ) { page ->
                    PagerItem(page)
                }
            } else {
                VerticalPager(
                    count = pictureList.size,
                    state = pagerState,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                ) { page ->
                    PagerItem(page)
                }
            }
            ActionsRow(pagerState, scope)
        }
    }
}

/**
 * 练习 Pager 和 Indicator 配合使用
 */
@OptIn(ExperimentalPagerApi::class)
@Composable
fun PagerIndicatorDemoPage() {
    FullPageWrapper {
        val pagerState = rememberPagerState()
        val scope = rememberCoroutineScope()
        Column {
            DescItem(title = "Pager Indicator")
            HorizontalPager(
                count = pictureList.size,
                state = pagerState,
                contentPadding = PaddingValues(horizontal = 30.dp),
                itemSpacing = 10.dp,
            ) { page ->
                PagerItem(page)
            }
            HorizontalPagerIndicator(
                pagerState = pagerState,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(16.dp),
            )
            HorizontalPagerIndicator(
                pagerState = pagerState,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(16.dp),
                indicatorShape = RectangleShape,
                indicatorWidth = 8.dp,
                indicatorHeight = 4.dp,
                inactiveColor = Color.LightGray,
                activeColor = Purple500
            )
            ActionsRow(pagerState, scope)
        }
    }
}

/**
 * 练习 Pager 和 TabLayout 配合使用
 */
@OptIn(ExperimentalPagerApi::class)
@Composable
fun PagerTabLayoutDemoPage() {
    FullPageWrapper {
        val pagerState = rememberPagerState()
        val scope = rememberCoroutineScope()
        Column {
            DescItem(title = "Pager TabLayout")
            ScrollableTabRow(
                selectedTabIndex = pagerState.currentPage,
                indicator = { tabPositions ->
                    TabRowDefaults.Indicator(
                        Modifier.pagerTabIndicatorOffset(pagerState, tabPositions)
                    )
                },
                edgePadding = 0.dp
            ) {
                topics.forEachIndexed{ index, topic ->
                    Tab(
                        text = { Text(topic) },
                        selected = pagerState.currentPage == index,
                        onClick = {
                            scope.launch {
                                pagerState.scrollToPage(index)
                            }
                        }
                    )
                }
            }
            HorizontalPager(
                count = topics.size,
                state = pagerState,
                contentPadding = PaddingValues(horizontal = 30.dp),
                itemSpacing = 10.dp,
            ) { page ->
                Box(modifier = Modifier.fillMaxSize()) {
                    Text(text = "Toady I study: ${topics[page]}",
                        fontSize = 20.sp,
                        modifier = Modifier.align(Alignment.Center))
                }
            }
        }
    }
}

@Composable
fun PagerItem(pageIndex: Int) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
            .clip(RoundedCornerShape(8.dp))
    ) {
        AsyncImage(
            model = pictureList[pageIndex],
            contentDescription = "",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )
        Text(
            text = "$pageIndex",
            modifier = Modifier
                .padding(10.dp)
                .align(Alignment.BottomEnd)
                .background(Color.LightGray, RoundedCornerShape(4.dp))
                .padding(5.dp)
        )
    }
}

@OptIn(ExperimentalPagerApi::class)
@Composable
fun ActionsRow(
    pagerState: PagerState,
    scope: CoroutineScope,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = Modifier
            .padding(top = 10.dp)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceAround
    ) {
        IconButton(onClick = {
            scope.launch {
                pagerState.scrollToPage(0)
            }
        }) {
            Icon(imageVector = Icons.Filled.SkipPrevious, contentDescription = "")
        }
        IconButton(onClick = {
            scope.launch {
                val currentIndex = pagerState.currentPage
                if (currentIndex != 0) {
                    pagerState.scrollToPage(currentIndex - 1)
                }
            }
        }) {
            Icon(imageVector = Icons.Filled.ChevronLeft, contentDescription = "")
        }
        IconButton(onClick = {
            scope.launch {
                val currentIndex = pagerState.currentPage
                if (currentIndex != pictureList.size - 1) {
                    pagerState.scrollToPage(currentIndex + 1)
                }
            }
        }) {
            Icon(imageVector = Icons.Filled.ChevronRight, contentDescription = "")
        }
        IconButton(onClick = {
            scope.launch {
                pagerState.scrollToPage(pictureList.size - 1)
            }
        }) {
            Icon(imageVector = Icons.Filled.SkipNext, contentDescription = "")
        }
    }
}