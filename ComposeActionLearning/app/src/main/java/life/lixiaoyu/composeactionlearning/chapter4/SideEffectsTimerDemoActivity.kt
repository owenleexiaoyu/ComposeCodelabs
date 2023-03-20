package life.lixiaoyu.composeactionlearning.chapter4

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import life.lixiaoyu.composeactionlearning.FullPageWrapper
import life.lixiaoyu.composeactionlearning.ItemButton
import life.lixiaoyu.composeactionlearning.VerticalSpacer

class SideEffectsTimerDemoActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FullPageWrapper {
                TimerNavigation()
            }
        }
    }
}

enum class Page {
    home,
    LaunchedEffect,
    NoSideEffect,
    SideEffect,
    NoDisposableEffect,
    DisposableEffect,
    rememberCoroutineScope
}

@Composable
fun TimerNavigation() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = Page.home.name
    ) {
        composable(Page.home.name) {
            SideEffectsTimerHomePage(navController)
        }
        composable(Page.LaunchedEffect.name) {
            TimerLaunchedEffectPage()
        }
        composable(Page.NoSideEffect.name) {
            TimerNoSideEffectPage()
        }
        composable(Page.SideEffect.name) {
            TimerSideEffectPage()
        }
        composable(Page.NoDisposableEffect.name) {
            TimerNoDisposableEffectPage()
        }
        composable(Page.DisposableEffect.name) {
            TimerDisposableEffectPage()
        }
        composable(Page.rememberCoroutineScope.name) {
            TimerRememberCoroutineScopePage()
        }
    }
}

@Composable
fun SideEffectsTimerHomePage(navController: NavController) {

    Column {
        ItemButton(text = Page.LaunchedEffect.name) {
            navController.navigate(Page.LaunchedEffect.name)
        }
        ItemButton(text = Page.NoSideEffect.name) {
            navController.navigate(Page.NoSideEffect.name)
        }
        ItemButton(text = Page.SideEffect.name) {
            navController.navigate(Page.SideEffect.name)
        }
        ItemButton(text = Page.NoDisposableEffect.name) {
            navController.navigate(Page.NoDisposableEffect.name)
        }
        ItemButton(text = Page.DisposableEffect.name) {
            navController.navigate(Page.DisposableEffect.name)
        }
        ItemButton(text = Page.rememberCoroutineScope.name) {
            navController.navigate(Page.rememberCoroutineScope.name)
        }
    }
}

@Preview
@Composable
fun SideEffectsTimerHomePagePreview() {
    FullPageWrapper {
        TimerNavigation()
    }
}

@Composable
fun TimerLaunchedEffectPage() {
    var timer by remember { mutableStateOf(0) }
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text("Time $timer")
    }
    LaunchedEffect(key1 = Unit) {
        while (true) {
            delay(1000)
            timer++
        }
    }
}


@Composable
fun TimerNoSideEffectPage() {
    var timer by remember { mutableStateOf(0) }
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text("Time $timer")
    }
    Thread.sleep(1000)
    timer++
}

@Composable
fun TimerSideEffectPage() {
    var timer by remember { mutableStateOf(0) }
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text("Time $timer")
    }
    SideEffect {
        Thread.sleep(1000)
        timer++
    }
//    Thread.sleep(1000)
//    timer++
}

@Composable
fun TimerNoDisposableEffectPage() {
    var timer by remember { mutableStateOf(0) }
    var timerStartStop by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("Time $timer")
            VerticalSpacer(height = 5.dp)
            Button(onClick = {
                timerStartStop = !timerStartStop

            }) {
                Text(if (timerStartStop) "Stop" else "Start")
            }
        }
    }
    val context = LocalContext.current
    LaunchedEffect(key1 = timerStartStop) {
        val x = (1..10).random()
        try {
            while (timerStartStop) {
                delay(1000)
                timer++
            }
        } catch (e: Exception) {
            Toast.makeText(context, "Oh No $x", Toast.LENGTH_SHORT).show()
        } finally {
            Toast.makeText(context, "Done $x", Toast.LENGTH_SHORT).show()
        }
    }
}

@Composable
fun TimerDisposableEffectPage() {
    var timer by remember { mutableStateOf(0) }
    var timerStartStop by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Button(onClick = {
                timerStartStop = !timerStartStop

            }) {
                Text(if (timerStartStop) "Stop" else "Start")
            }
        }
    }
    val context = LocalContext.current

    DisposableEffect(key1 = timerStartStop) {
        val x = (1..10).random()
        Toast.makeText(context, "Start $x", Toast.LENGTH_SHORT).show()
        onDispose {
            Toast.makeText(context, "Stop $x", Toast.LENGTH_SHORT).show()
        }
    }
}

@Composable
fun TimerRememberCoroutineScopePage() {
    val scope = rememberCoroutineScope()
    var timer by remember { mutableStateOf(0) }
    var timerStartStop by remember { mutableStateOf(false) }
    var job: Job? by remember { mutableStateOf(null) }

    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("Time $timer")
            VerticalSpacer(height = 5.dp)
            Button(onClick = {
                timerStartStop = !timerStartStop
                if (timerStartStop) {
                    job?.cancel()
                    job = scope.launch {
                        while (true) {
                            delay(1000)
                            timer++
                        }
                    }
                } else {
                    job?.cancel()
                }
            }) {
                Text(if (timerStartStop) "Stop" else "Start")
            }
        }
    }
}