package life.lixiaoyu.composeactionlearning.chapter4

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import life.lixiaoyu.composeactionlearning.FullPageWrapper
import life.lixiaoyu.composeactionlearning.HorizontalSpacer

/**
 * Compose 中使用 StateHolder 来管理状态
 */
class Counter7Activity: ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FullPageWrapper {
                CounterScreenWithStateHolder()
            }
        }
    }
}

@Composable
fun CounterScreenWithStateHolder() {
    val stateHolder = remember { CounterState() }
    CounterComponent(
        counter = stateHolder.counter.value,
        onIncrement = stateHolder::increment,
        onDecrement = stateHolder::decrement
    )
}

class CounterState {
    val counter: State<Int>
     get() = _counter
    private val _counter = mutableStateOf(0)

    fun increment() {
        _counter.value = _counter.value + 1
    }

    fun decrement() {
        if (_counter.value > 0) {
            _counter.value = _counter.value - 1
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CounterScreenWithStateHolderPreview() {
    FullPageWrapper {
        CounterScreenWithStateHolder()
    }
}