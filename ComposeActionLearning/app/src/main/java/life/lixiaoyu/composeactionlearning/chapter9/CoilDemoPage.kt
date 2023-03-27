package life.lixiaoyu.composeactionlearning.chapter9

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import coil.compose.*
import coil.request.ImageRequest
import life.lixiaoyu.composeactionlearning.DescItem
import life.lixiaoyu.composeactionlearning.FullPageWrapper
import life.lixiaoyu.composeactionlearning.R

@Composable
fun CoilDemoPage() {
    FullPageWrapper {
        CoilDemo()
    }
}

const val COMPOSE_LOGO_URL = "https://p5.music.126.net/obj/wonDlsKUwrLClGjCm8Kx/17570021171/3b69/cf52/209e/082411995c6b91400e4fc56a56271339.jpeg"
const val IMAGE_URL1 = "https://images.pexels.com/photos/12279474/pexels-photo-12279474.jpeg?auto=compress&cs=tinysrgb&w=800&lazy=load"
const val IMAGE_URL2 = "https://images.pexels.com/photos/10007306/pexels-photo-10007306.jpeg?auto=compress&cs=tinysrgb&w=800&lazy=load"
const val IMAGE_URL3 = "https://images.pexels.com/photos/14077027/pexels-photo-14077027.jpeg?auto=compress&cs=tinysrgb&w=800&lazy=load"

@Composable
fun CoilDemo() {
    Column(
        modifier = Modifier.verticalScroll(rememberScrollState())
    ) {
        DescItem(title = "使用 AsyncImage 加载网络图片")
        AsyncImage(model = IMAGE_URL1, contentDescription = "Network Image")
        DescItem(title = "使用 ImageRequest 设置更多属性")
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(COMPOSE_LOGO_URL)
                .crossfade(true)
                .size(400)
                .build(),
            contentDescription = "Network Image",
            placeholder = painterResource(id = R.mipmap.ic_loading),
            error = painterResource(id = R.mipmap.ic_error),
            onSuccess = {
                Log.d("Compose_Coil", "AsyncImage onSuccess")
            },
            onLoading = {
                Log.d("Compose_Coil", "AsyncImage onLoading")
            },
            onError = {
                Log.d("Compose_Coil", "AsyncImage onError")
            }
        )
        DescItem(title = "使用 SubcomposeAsyncImage 添加自定义加载 View")
        SubcomposeAsyncImage(
            model = IMAGE_URL2,
            contentDescription = "Network Image",
        ) {
            if (painter.state is AsyncImagePainter.State.Loading ||
                painter.state is AsyncImagePainter.State.Error) {
                CircularProgressIndicator()
            } else {
                SubcomposeAsyncImageContent()
            }
        }
        DescItem(title = "使用 AsyncImagePainter 结合 Image")
        drawImageWithAsyncImagePainter()
    }
}

@Composable
fun drawImageWithAsyncImagePainter() {
    val painter = rememberAsyncImagePainter(
        model = IMAGE_URL3
    )
    if (painter.state is AsyncImagePainter.State.Loading) {
        CircularProgressIndicator()
    }
    Image(painter = painter, contentDescription = "Network Image")
}