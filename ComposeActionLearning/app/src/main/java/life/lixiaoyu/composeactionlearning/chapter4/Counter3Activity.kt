package life.lixiaoyu.composeactionlearning.chapter4

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.ComponentActivity
import androidx.activity.viewModels
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import life.lixiaoyu.composeactionlearning.R

/**
 * 传统 View 体系开发的 MVVM 范式，引入 ViewModel
 */
class Counter3Activity: ComponentActivity() {

    private lateinit var button1: Button
    private lateinit var button2: Button
    private lateinit var text: TextView

    private val viewModel by viewModels<CounterViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_counter)

        button1 = findViewById(R.id.button1)
        button2 = findViewById(R.id.button2)
        text = findViewById(R.id.text)

        viewModel.counter.observe(this) {
            text.text = "$it"
        }
        
        button1.setOnClickListener {
            viewModel.decrement()
        }
        button2.setOnClickListener {
            viewModel.increment()
        }
    }
}

class CounterViewModel : ViewModel() {

    private val _counter = MutableLiveData(0)
    val counter: LiveData<Int> = _counter

    fun increment() {
        _counter.value = _counter.value!! + 1
    }

    fun decrement() {
        if (_counter.value!! > 0) {
            _counter.value = _counter.value!! - 1
        }
    }
}