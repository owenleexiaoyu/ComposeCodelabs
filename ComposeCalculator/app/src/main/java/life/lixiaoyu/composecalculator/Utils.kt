package life.lixiaoyu.composecalculator

import androidx.compose.runtime.snapshots.SnapshotStateList

object Utils {
    fun getFormula(expression: SnapshotStateList<String>): String {
        if (expression.size <= 0) {
            return ""
        }
        val builder = StringBuilder("")
        for (str in expression) {
            builder.append(str)
        }
        return builder.toString()
    }

    fun usingExp4J(): Boolean = false

}