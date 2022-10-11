package life.lixiaoyu.composecalculator

import java.lang.Exception
import java.lang.IllegalStateException
import java.util.*

object Calculator {

    fun calculate(infixExpression: String): String {
        println("中缀表达式：$infixExpression")
        val postfixExpression = try {
            transformToPostfixExpression(infixExpression)
        } catch (e: Exception) {
            e.printStackTrace()
            ""
        }
        println("后缀表达式：$postfixExpression")
        return try {
            calculatePostfixExpression(postfixExpression).toString()
        } catch (e: Exception) {
            e.printStackTrace()
            "Error!"
        }
//        println("运算结果：$result")
    }

    private fun transformToPostfixExpression(infixExpression: String): String {
        var lastStr = "" // 记录上一个字符，用来识别是否是负号。
        val postfixExpressionSb = StringBuilder("")
        val stack = Stack<String>()
        for(item in infixExpression) {
            val str = item.toString()
            if (str == " ") continue
            if (str in "0" .. "9" || str == ".") {
                // 数字或小数点
                postfixExpressionSb.append(str)
            } else {
                // 加个空格做区分
                postfixExpressionSb.append(" ")
                if (str == "(") {
                    // 左括号的情况
                    // 直接压栈
                    stack.push(str)
                } else if (str == ")") {
                    // 右括号的情况
                    // 如果栈为空，栈里没有 (，属于异常情况
                    if (stack.isEmpty()) {
                        throw IllegalStateException("括号不匹配")
                    }
                    // 不为空时，把栈顶元素依次弹出，并加到后缀表达式中，直到遇到第一个 ( 为止，
                    // 有可能没有 (，这时候会把栈清空
                    while (stack.peek() != "(") {
                        postfixExpressionSb.append("${stack.pop()} ")
                    }
                    // 如果这时候栈空了，说明没有匹配到 (, 异常情况
                    if (stack.isEmpty()) {
                        throw IllegalStateException("括号不匹配")
                    }
                    // 把匹配到的第一个 ( 弹栈，但是不加到后缀表达式中
                    if (stack.peek() == "(") {
                        stack.pop()
                    }
                } else if (str in arrayOf("+", "-", "*", "/")) {
                    if (str == "-" && (lastStr == "" || lastStr == "(")) {
                        // 负号（-）的情况
                        postfixExpressionSb.append(str)
                    } else {
                        // 加减乘除运算符的情况
                        if (
                            stack.isNotEmpty()
                            && stack.peek() != "("
                            && !((str == "*" || str == "/") && (stack.peek() == "+" || stack.peek() == "-"))) {
                            postfixExpressionSb.append("${stack.pop()} ")
                        }
                        stack.push(str)
                    }
                }
            }
            // 每次遍历结束时，进行 lastStr 的赋值
            lastStr = str
        }
        while (!stack.isEmpty()) {
            postfixExpressionSb.append(" ${stack.pop()}")
        }
        return postfixExpressionSb.toString()
    }

    private fun calculatePostfixExpression(postfixExpression: String): Double {
        val stack = Stack<String>()
        postfixExpression.split(" ")
            .map { it.trim() }
            .filter { it.isNotEmpty() }
            .forEach {
                if (it.startsWith(".") || it.endsWith(".")) {
                    throw IllegalStateException("小数点不符合规范")
                } else if (it in arrayOf("+", "-", "*", "/")) {
                    if (stack.isEmpty()) {
                        throw IllegalStateException("取第一个因子时，栈里没有元素")
                    }
                    val num2 = stack.pop().toDouble()
                    if (stack.isEmpty()) {
                        throw IllegalStateException("取第二个因子时，栈里没有元素")
                    }
                    val num1 = stack.pop().toDouble()
                    val result = when(it) {
                        "+" -> num1 + num2
                        "-" -> num1 - num2
                        "*" -> num1 * num2
                        "/" -> num1 / num2
                        else -> 0
                    }
                    stack.push("$result")
                } else {
                    // 数字
                    stack.push(it)
                }
            }
        if (stack.isEmpty()) {
            throw IllegalStateException("运算结束，栈里没有元素")
        }
        return stack.pop().toDouble()
    }

}