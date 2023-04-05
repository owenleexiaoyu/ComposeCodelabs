package life.lixiaoyu.composeactionlearning.chapter5

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import life.lixiaoyu.composeactionlearning.DescItem
import life.lixiaoyu.composeactionlearning.FullPageWrapper
import life.lixiaoyu.composeactionlearning.ItemButton

class Chapter5Activity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FullPageWrapper {
                Chapter5NavHost()
            }
        }
    }
}

@Composable
fun Chapter5NavHost() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = "chapter5"
    ) {
        composable(Chapter5Pages.chapter5.name) {
            Chapter5Page(navController)
        }
        composable(Chapter5Pages.firstBaselineToTop.name) {
            FirstBaselineToTopPage(navController)
        }
        composable(Chapter5Pages.customColumn.name) {
            CustomColumnPage()
        }
        composable(Chapter5Pages.staggeredGrid.name) {
            StaggeredGridPage()
        }
        composable(Chapter5Pages.flowLayout.name) {
            FlowLayoutPageDemo()
        }
        composable(Chapter5Pages.twoTexts.name) {
            TwoTextsPage()
        }
        composable(Chapter5Pages.customIntrinsicSizeRow.name) {
            CustomIntrinsicSizeRowPage()
        }
        composable(Chapter5Pages.subcomposeLayoutTwoTexts.name) {
            SubcomposeLayoutTwoTextsPage()
        }
        composable(Chapter5Pages.drawProgressBar.name) {
            DrawProgressBarPage()
        }
        composable(Chapter5Pages.drawBehind.name) {
            DrawBehindPage()
        }
        composable(Chapter5Pages.drawCache.name) {
            DrawFuwaPage()
        }
        composable(Chapter5Pages.drawWaveLoading.name) {
            WaveLoadingPage()
        }
        composable(Chapter5Pages.waveLoadingInBook.name) {
            WaveLoadingDemoInBook()
        }
    }
}

enum class Chapter5Pages {
    chapter5,
    firstBaselineToTop,
    customColumn,
    staggeredGrid,
    flowLayout,
    twoTexts,
    customIntrinsicSizeRow,
    subcomposeLayoutTwoTexts,
    drawProgressBar,
    drawBehind,
    drawCache,
    drawWaveLoading,
    waveLoadingInBook
}

@Composable
fun Chapter5Page(navController: NavController) {
    Column {
        DescItem(title = "布局")
        ItemButton(text = "LayoutNode firstBaselineToTop") {
            navController.navigate(Chapter5Pages.firstBaselineToTop.name)
        }
        ItemButton(text = "自定义布局 CustomColumn") {
            navController.navigate(Chapter5Pages.customColumn.name)
        }
        ItemButton(text = "自定义布局 StaggeredGrid") {
            navController.navigate(Chapter5Pages.staggeredGrid.name)
        }
        ItemButton(text = "自定义布局 FlowLayout") {
            navController.navigate(Chapter5Pages.flowLayout.name)
        }
        ItemButton(text = "Row 固有特性测量： TwoTextDivider") {
            navController.navigate(Chapter5Pages.twoTexts.name)
        }
        ItemButton(text = "自定义固有特性测量： CustomIntrinsicSizeRow") {
            navController.navigate(Chapter5Pages.customIntrinsicSizeRow.name)
        }
        ItemButton(text = "使用 SubcomposeLayout 实现 Two Texts Divider") {
            navController.navigate(Chapter5Pages.subcomposeLayoutTwoTexts.name)
        }
        DescItem(title = "绘制")
        ItemButton(text = "绘制一个圆形进度条") {
            navController.navigate(Chapter5Pages.drawProgressBar.name)
        }
        ItemButton(text = "DrawBefore 和 DrawBehind") {
            navController.navigate(Chapter5Pages.drawBehind.name)
        }
        ItemButton(text = "DrawCache 绘制福娃") {
            navController.navigate(Chapter5Pages.drawCache.name)
        }
        ItemButton(text = "绘制实战：绘制波浪加载特效（书里 demo）") {
            navController.navigate(Chapter5Pages.waveLoadingInBook.name)
        }
        ItemButton(text = "绘制实战：绘制波浪加载特效") {
            navController.navigate(Chapter5Pages.drawWaveLoading.name)
        }

    }
}

