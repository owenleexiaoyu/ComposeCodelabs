package life.lixiaoyu.composebloom

import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.ParagraphStyle
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle

object TextUtils {

    inline fun <R : Any> AnnotatedString.Builder.withStyle(
        style: TextStyle,
        useSpan: Boolean,
        crossinline block: AnnotatedString.Builder.() -> R
    ): R {
        val index = if (useSpan) {
            val spanStyle = style.toSpanStyle()
            pushStyle(spanStyle)
        } else {
            val paragraphStyle = style.toParagraphStyle()
            pushStyle(paragraphStyle)
        }
        return try {
            block(this)
        } finally {
            pop(index)
        }
    }

    private fun TextStyle.toSpanStyle(): SpanStyle {
        return SpanStyle(
            color = this.color,
            fontSize = this.fontSize,
            fontWeight = this.fontWeight,
            fontStyle = this.fontStyle,
            fontSynthesis = this.fontSynthesis,
            fontFamily = this.fontFamily,
            fontFeatureSettings = this.fontFeatureSettings,
            letterSpacing = this.letterSpacing,
            baselineShift = this.baselineShift,
            textGeometricTransform = this.textGeometricTransform,
            localeList = this.localeList,
            background = this.background,
            textDecoration = this.textDecoration,
            shadow = this.shadow
        )
    }

    private fun TextStyle.toParagraphStyle(): ParagraphStyle {
        return ParagraphStyle(
            textAlign = this.textAlign,
            textDirection = this.textDirection,
            lineHeight = this.lineHeight,
            textIndent = this.textIndent
        )
    }

}