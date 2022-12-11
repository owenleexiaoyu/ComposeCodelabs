package life.lixiaoyu.composeactionlearning.chapter4

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.ComponentActivity
import life.lixiaoyu.composeactionlearning.R

/**
 * 传统 View 体系开发的 MVC 范式，优化代码，抽取出 counter 这个数据作为状态，保存在 Activity 中
 */
class Counter2Activity: ComponentActivity() {

    private lateinit var button1: Button
    private lateinit var button2: Button
    private lateinit var text: TextView

    private var counter: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_counter)

        button1 = findViewById(R.id.button1)
        button2 = findViewById(R.id.button2)
        text = findViewById(R.id.text)

        updateCounter()

        button1.setOnClickListener {
            if (counter > 0) {
                counter--
                updateCounter()
            }
        }
        button2.setOnClickListener {
            counter++
            updateCounter()
        }
    }

    private fun updateCounter() {
        text.text = "$counter"
    }
}