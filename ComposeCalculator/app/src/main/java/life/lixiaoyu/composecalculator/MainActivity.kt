package life.lixiaoyu.composecalculator

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import life.lixiaoyu.composecalculator.ui.theme.AppColor
import life.lixiaoyu.composecalculator.ui.theme.ComposeCalculatorTheme
import net.objecthunter.exp4j.Expression
import net.objecthunter.exp4j.ExpressionBuilder

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {

            val expression = remember {
                mutableStateListOf("")
            }
            val lastExpressionStr = remember {
                mutableStateOf("")
            }
            val onError = remember {
                mutableStateOf(false)
            }

            ComposeCalculatorTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    CalculatorScreen(
                        expression = expression,
                        lastExpressionStr = lastExpressionStr,
                        onError = onError
                    )
                }
            }
        }
    }
}

@Composable
fun BaseCalculatorButton(
    modifier: Modifier = Modifier,
    text: String,
    textColor: Color,
    backgroundColor: Color,
    onClick: (value: String) -> Unit = {},
) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(20.dp))
            .background(backgroundColor)
            .clickable { onClick(text) },
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            color = textColor,
            fontWeight = FontWeight.SemiBold,
            fontSize = 18.sp
        )
    }
}

@Composable
fun CommonCalculatorButton(
    modifier: Modifier = Modifier,
    text: String,
    onClick: (value: String) -> Unit
) {
    BaseCalculatorButton(
        modifier = modifier.padding(10.dp),
        text = text,
        textColor = MaterialTheme.colors.onSecondary,
        backgroundColor = MaterialTheme.colors.secondary,
        onClick = onClick
    )
}

@Composable
fun CalculatorScreen(
    expression: SnapshotStateList<String>,
    lastExpressionStr: MutableState<String>,
    onError: MutableState<Boolean>,
) {
    val onAppendClick: (String) -> Unit = {
         if (expression.size < 10) {
             expression.add(it)
         }
    }

    val onClearClick: (Boolean) -> Unit = { clearAll ->
        if (clearAll) {
            expression.clear()
        } else {
            if (expression.size > 0) {
                expression.removeLast()
            }
        }
    }

    val onCalculateClick: () -> Unit = {
        val formula = Utils.getFormula(expression)
            .replace("✕", "*")
        if (Utils.usingExp4J()) {
            try {
                val e: Expression = ExpressionBuilder(formula)
                    .build()
                val result = e.evaluate().toString()
                if (result == "NaN") {
                    onError.value = true
                }
                lastExpressionStr.value = Utils.getFormula(expression)
                expression.clear()
                expression.add(result)
            } catch (e: Exception) {
                onError.value = true
                lastExpressionStr.value = Utils.getFormula(expression)
                expression.clear()
                expression.add("Error!")
            }
        } else {
            val result = try {
                Calculator.calculate(formula)
            } catch (e: Exception) {
                onError.value = true
                "Error!"
            }
            lastExpressionStr.value = Utils.getFormula(expression)
            expression.clear()
            expression.add(result)
        }
    }


    BoxWithConstraints {
        if (maxWidth < maxHeight) {
            // 竖屏
            Column {
                CalculatorText(
                    lastLine = lastExpressionStr.value,
                    currentLine = Utils.getFormula(expression),
                    isVertical = true,
                    modifier = Modifier.weight(1F)
                )
                Box(
                    modifier = Modifier
                        .weight(2F)
                        .fillMaxWidth()
                        .padding(bottom = 40.dp),
                    contentAlignment = Alignment.Center,
                ) {
                    CalculatorBoard(
                        isVertical = true,
                        onAppendClick = onAppendClick,
                        onClearClick = onClearClick,
                        onCalculateClick = onCalculateClick
                    )
                }
            }
        } else {
            // 横屏
            Row {
                CalculatorText(
                    lastLine = lastExpressionStr.value,
                    currentLine = Utils.getFormula(expression),
                    isVertical = false,
                    modifier = Modifier.weight(1F)
                )
                Box(
                    modifier = Modifier
                        .weight(2F)
                        .fillMaxHeight()
                        .padding(end = 40.dp),
                    contentAlignment = Alignment.Center,
                ) {
                    CalculatorBoard(
                        isVertical = false,
                        onAppendClick = onAppendClick,
                        onClearClick = onClearClick,
                        onCalculateClick = onCalculateClick
                    )
                }
            }
        }
    }

}

@Composable
fun CalculatorText(
    lastLine: String,
    currentLine: String,
    isVertical: Boolean,
    modifier: Modifier = Modifier
) {
    val boxModifier = if (isVertical)
        modifier
            .fillMaxWidth()
            .padding(horizontal = 40.dp)
    else
        modifier
            .fillMaxHeight()
            .padding(vertical = 40.dp, horizontal = 20.dp)

    val boxContentAlignment = if (isVertical)
        Alignment.BottomEnd
    else
        Alignment.TopEnd
    Box(
        modifier = boxModifier,
        contentAlignment = boxContentAlignment
    ) {
        Column(
            horizontalAlignment = Alignment.End
        ) {
            Text(
                text = lastLine,
                fontSize = 25.sp,
            )
            Spacer(modifier = Modifier.padding(vertical = 10.dp))
            Text(
                text = currentLine,
                fontSize = 40.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Composable
fun CalculatorBoard(
    isVertical: Boolean = true,
    onAppendClick: (String) -> Unit,
    onClearClick: (clearAll: Boolean) -> Unit, // clearAll = true: 清除全部，clearAll = false: 清除最后一个字符
    onCalculateClick: () -> Unit,
) {
    if (isVertical) {
        VerticalCalculatorBoard(
            onAppendClick = onAppendClick,
            onClearClick = onClearClick,
            onCalculateClick = onCalculateClick
        )
    } else {
        HorizontalCalculatorBoard(
            onAppendClick = onAppendClick,
            onClearClick = onClearClick,
            onCalculateClick = onCalculateClick
        )
    }
}

@Composable
fun VerticalCalculatorBoard(
    onAppendClick: (String) -> Unit,
    onClearClick: (clearAll: Boolean) -> Unit, // clearAll = true: 清除全部，clearAll = false: 清除最后一个字符
    onCalculateClick: () -> Unit,
) {
    Column {
        Row {
            BaseCalculatorButton(
                text = "C",
                textColor = Color.Red,
                backgroundColor = MaterialTheme.colors.primary.copy(alpha = 0.5F),
                modifier = Modifier
                    .size(80.dp)
                    .padding(10.dp),
                onClick = { onClearClick(true) }
            )
            CommonCalculatorButton(
                text = "⌫",
                modifier = Modifier.size(80.dp),
                onClick = { onClearClick(false) }
            )
            BaseCalculatorButton(
                text = "(",
                textColor = MaterialTheme.colors.onSecondary,
                backgroundColor = MaterialTheme.colors.secondary,
                modifier = Modifier
                    .size(width = 40.dp, height = 80.dp)
                    .padding(top = 10.dp, bottom = 10.dp, start = 10.dp, end = 2.dp),
                onClick = onAppendClick
            )
            BaseCalculatorButton(
                text = ")",
                textColor = MaterialTheme.colors.onSecondary,
                backgroundColor = MaterialTheme.colors.secondary,
                modifier = Modifier
                    .size(width = 40.dp, height = 80.dp)
                    .padding(top = 10.dp, bottom = 10.dp, start = 2.dp, end = 10.dp),
                onClick = onAppendClick
            )
            BaseCalculatorButton(
                text = "/",
                textColor = Color.White,
                backgroundColor = AppColor.Orange,
                modifier = Modifier
                    .size(80.dp)
                    .padding(10.dp),
                onClick = onAppendClick
            )
        }
        Row {
            CommonCalculatorButton(
                text = "7",
                modifier = Modifier.size(80.dp),
                onClick = onAppendClick
            )
            CommonCalculatorButton(
                text = "8",
                modifier = Modifier.size(80.dp),
                onClick = onAppendClick
            )
            CommonCalculatorButton(
                text = "9",
                modifier = Modifier.size(80.dp),
                onClick = onAppendClick
            )
            BaseCalculatorButton(
                text = "✕",
                textColor = Color.White,
                backgroundColor = AppColor.Orange,
                modifier = Modifier
                    .size(80.dp)
                    .padding(10.dp),
                onClick = onAppendClick
            )
        }
        Row {
            CommonCalculatorButton(
                text = "4",
                modifier = Modifier.size(80.dp),
                onClick = onAppendClick
            )
            CommonCalculatorButton(
                text = "5",
                modifier = Modifier.size(80.dp),
                onClick = onAppendClick
            )
            CommonCalculatorButton(
                text = "6",
                modifier = Modifier.size(80.dp),
                onClick = onAppendClick
            )
            BaseCalculatorButton(
                text = "-",
                textColor = Color.White,
                backgroundColor = AppColor.Orange,
                modifier = Modifier
                    .size(80.dp)
                    .padding(10.dp),
                onClick = onAppendClick
            )
        }
        Row {
            CommonCalculatorButton(
                text = "1",
                modifier = Modifier.size(80.dp),
                onClick = onAppendClick
            )
            CommonCalculatorButton(
                text = "2",
                modifier = Modifier.size(80.dp),
                onClick = onAppendClick
            )
            CommonCalculatorButton(
                text = "3",
                modifier = Modifier.size(80.dp),
                onClick = onAppendClick
            )
            BaseCalculatorButton(
                text = "+",
                textColor = Color.White,
                backgroundColor = AppColor.Orange,
                modifier = Modifier
                    .size(80.dp)
                    .padding(10.dp),
                onClick = onAppendClick
            )
        }
        Row {
            CommonCalculatorButton(
                text = "0",
                modifier = Modifier.size(80.dp),
                onClick = onAppendClick
            )
            CommonCalculatorButton(
                text = ".",
                modifier = Modifier.size(80.dp),
                onClick = onAppendClick
            )
            BaseCalculatorButton(
                text = "=",
                textColor = Color.White,
                backgroundColor = AppColor.Green,
                modifier = Modifier
                    .size(width = 160.dp, height = 80.dp)
                    .padding(10.dp),
                onClick = { onCalculateClick() }
            )
        }
    }
}

@Composable
fun HorizontalCalculatorBoard(
    onAppendClick: (String) -> Unit,
    onClearClick: (clearAll: Boolean) -> Unit, // clearAll = true: 清除全部，clearAll = false: 清除最后一个字符
    onCalculateClick: () -> Unit,
) {
    Column {
        Row {
            BaseCalculatorButton(
                text = "C",
                textColor = Color.Red,
                backgroundColor = MaterialTheme.colors.primary.copy(alpha = 0.5F),
                modifier = Modifier
                    .size(80.dp)
                    .padding(10.dp),
                onClick = { onClearClick(true) }
            )
            CommonCalculatorButton(
                text = "⌫",
                modifier = Modifier.size(80.dp),
                onClick = { onClearClick(false) }
            )
            BaseCalculatorButton(
                text = "(",
                textColor = MaterialTheme.colors.onSecondary,
                backgroundColor = MaterialTheme.colors.secondary,
                modifier = Modifier
                    .size(width = 40.dp, height = 80.dp)
                    .padding(top = 10.dp, bottom = 10.dp, start = 10.dp, end = 2.dp),
                onClick = onAppendClick
            )
            BaseCalculatorButton(
                text = ")",
                textColor = MaterialTheme.colors.onSecondary,
                backgroundColor = MaterialTheme.colors.secondary,
                modifier = Modifier
                    .size(width = 40.dp, height = 80.dp)
                    .padding(top = 10.dp, bottom = 10.dp, start = 2.dp, end = 10.dp),
                onClick = onAppendClick
            )
            BaseCalculatorButton(
                text = "/",
                textColor = Color.White,
                backgroundColor = AppColor.Orange,
                modifier = Modifier
                    .size(80.dp)
                    .padding(10.dp),
                onClick = onAppendClick
            )
            BaseCalculatorButton(
                text = "✕",
                textColor = Color.White,
                backgroundColor = AppColor.Orange,
                modifier = Modifier
                    .size(80.dp)
                    .padding(10.dp),
                onClick = onAppendClick
            )
        }
        Row {
            CommonCalculatorButton(
                text = "7",
                modifier = Modifier.size(80.dp),
                onClick = onAppendClick
            )
            CommonCalculatorButton(
                text = "8",
                modifier = Modifier.size(80.dp),
                onClick = onAppendClick
            )
            CommonCalculatorButton(
                text = "9",
                modifier = Modifier.size(80.dp),
                onClick = onAppendClick
            )
            BaseCalculatorButton(
                text = "-",
                textColor = Color.White,
                backgroundColor = AppColor.Orange,
                modifier = Modifier
                    .size(80.dp)
                    .padding(10.dp),
                onClick = onAppendClick
            )
            BaseCalculatorButton(
                text = "+",
                textColor = Color.White,
                backgroundColor = AppColor.Orange,
                modifier = Modifier
                    .size(80.dp)
                    .padding(10.dp),
                onClick = onAppendClick
            )
        }
        Row {
            Column {
                CommonCalculatorButton(
                    text = "4",
                    modifier = Modifier.size(80.dp),
                    onClick = onAppendClick
                )
                CommonCalculatorButton(
                    text = "1",
                    modifier = Modifier.size(80.dp),
                    onClick = onAppendClick
                )
            }
            Column {
                CommonCalculatorButton(
                    text = "5",
                    modifier = Modifier.size(80.dp),
                    onClick = onAppendClick
                )
                CommonCalculatorButton(
                    text = "2",
                    modifier = Modifier.size(80.dp),
                    onClick = onAppendClick
                )
            }
            Column {
                CommonCalculatorButton(
                    text = "6",
                    modifier = Modifier.size(80.dp),
                    onClick = onAppendClick
                )
                CommonCalculatorButton(
                    text = "3",
                    modifier = Modifier.size(80.dp),
                    onClick = onAppendClick
                )
            }
            Column {
                CommonCalculatorButton(
                    text = ".",
                    modifier = Modifier.size(80.dp),
                    onClick = onAppendClick
                )
                CommonCalculatorButton(
                    text = "0",
                    modifier = Modifier.size(80.dp),
                    onClick = onAppendClick
                )
            }
            BaseCalculatorButton(
                text = "=",
                textColor = Color.White,
                backgroundColor = AppColor.Green,
                modifier = Modifier
                    .size(width = 80.dp, height = 160.dp)
                    .padding(10.dp),
                onClick = { onCalculateClick() }
            )
        }
    }
}