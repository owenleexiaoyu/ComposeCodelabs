package life.lixiaoyu.composeactionlearning.chapter4

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.ComponentActivity
import life.lixiaoyu.composeactionlearning.R

/**
 * 传统 View 体系开发的 MVC 范式
 */
class CounterActivity: ComponentActivity() {

    private lateinit var button1: Button
    private lateinit var button2: Button
    private lateinit var text: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_counter)

        button1 = findViewById(R.id.button1)
        button2 = findViewById(R.id.button2)
        text = findViewById(R.id.text)

        text.text = "0"

        button1.setOnClickListener {
            val value = Integer.valueOf(text.text.toString())
            if (value > 0) {
                text.text = "${value - 1}"
            }
        }
        button2.setOnClickListener {
            val value = Integer.valueOf(text.text.toString())
            text.text = "${value + 1}"
        }
    }
}