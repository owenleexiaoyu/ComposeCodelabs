package life.lixiaoyu.composeactionlearning.chapter2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.state.ToggleableState
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import life.lixiaoyu.composeactionlearning.*

class CheckboxDemoActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FullPageWrapper {
                CheckboxPage()
            }
        }
    }
}

@Composable
fun CheckboxPage() {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.verticalScroll(rememberScrollState())
    ) {
        DescItem(title = "Checkbox 简单使用")
        var checkedState by remember {
            mutableStateOf(true)
        }
        Checkbox(checked = checkedState, onCheckedChange = {
            checkedState = it
        })
        DescItem(title = "Checkbox 装饰")
        Row {
            Checkbox(
                checked = checkedState,
                onCheckedChange = {
                    checkedState = it
                },
                colors = CheckboxDefaults.colors(
                    checkedColor = Color.Red,
                    uncheckedColor = Color.Blue,
                    checkmarkColor = Color.Green,
                    disabledColor = Color.Gray,
                    disabledIndeterminateColor = Color.Black
                )
            )
            HorizontalSpacer(width = 10.dp)
            Checkbox(
                checked = checkedState,
                onCheckedChange = {
                    checkedState = it
                },
                colors = CheckboxDefaults.colors(
                    checkedColor = Color.Red,
                    uncheckedColor = Color.Blue,
                    checkmarkColor = Color.Green,
                    disabledColor = Color.Gray,
                    disabledIndeterminateColor = Color.Black
                ),
                enabled = false
            )
        }

        DescItem(title = "使用 Checkbox 多选")
        var state1 by remember {
            mutableStateOf(false)
        }
        var state2 by remember {
            mutableStateOf(false)
        }
        Column {
            Text("掌握的技能")
            Row(verticalAlignment = Alignment.CenterVertically) {
                Checkbox(checked = state1, onCheckedChange = {
                    state1 = it
                })
                Text("Java")
            }
            Row(verticalAlignment = Alignment.CenterVertically) {
                Checkbox(checked = state2, onCheckedChange = {
                    state2 = it
                })
                Text("Kotlin")
            }
        }
        DescItem(title = "三态 Checkbox：TriStateCheckbox")
        // 为两个 Checkbox 设置状态
        val (state3, onState3Changed) = remember {
            mutableStateOf(false)
        }
        val (state4, onState4Changed) = remember {
            mutableStateOf(false)
        }
        // 根据两个子 Checkbox 的状态，设置 TriStateCheckbox
        val parentState = remember(state3, state4) {
            if (state3 && state4) ToggleableState.On
            else if (!state3 && !state4) ToggleableState.Off
            else ToggleableState.Indeterminate
        }
        // 当父 Checkbox 点击时的状态变化
        val onParentCheckboxClick = {
            val s = parentState != ToggleableState.On
            onState3Changed(s)
            onState4Changed(s)
        }
        Column {
            Text("掌握的技能")
            Row(verticalAlignment = Alignment.CenterVertically) {
                TriStateCheckbox(
                    state = parentState,
                    onClick = onParentCheckboxClick,
                )
                Text("全选")
            }
            Row(verticalAlignment = Alignment.CenterVertically) {
                Checkbox(
                    checked = state3,
                    onCheckedChange = onState3Changed
                )
                Text("Java")
            }
            Row(verticalAlignment = Alignment.CenterVertically) {
                Checkbox(
                    checked = state4,
                    onCheckedChange = onState4Changed
                )
                Text("Kotlin")
            }
        }
        DescItem(title = "Switch 的简单使用")
        var switchChecked by remember {
            mutableStateOf(false)
        }
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text("开关：${if (switchChecked) "ON" else "OFF"}")
            Switch(checked = switchChecked, onCheckedChange = {
                switchChecked = it
            })
        }
        DescItem(title = "Switch 的装饰")
        Switch(
            checked = switchChecked,
            onCheckedChange = {
            switchChecked = it
        },
            colors = SwitchDefaults.colors(
                checkedThumbColor = Color.Red,
                checkedTrackColor = Color.Black,
                uncheckedThumbColor = Color.Green,
                uncheckedTrackColor = Color.Cyan
            )
        )
        DescItem(title = "滑竿 Slider 的简单使用")
        var sliderValue by remember {
            mutableStateOf(0.0F)
        }
        Slider(
            value = sliderValue,
            onValueChange = {
            sliderValue = it
        },
            modifier = Modifier.padding(horizontal = 10.dp)
        )
        Text("当前进度：${(sliderValue * 100).toInt()}%")
        DescItem(title = "滑竿 Slider 的 step")
        var sliderValue2 by remember {
            mutableStateOf(0.0F)
        }
        Slider(
            value = sliderValue2,
            onValueChange = {
            sliderValue2 = it
        },
            steps = 3,
            modifier = Modifier.padding(horizontal = 10.dp)
        )
        Text("当前进度：${(sliderValue2 * 100).toInt()}%")
    }
}

@Composable
@Preview(showBackground = true)
fun CheckboxPagePreview() {
    FullPageWrapper {
        CheckboxPage()
    }
}