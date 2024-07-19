package com.hugg.feature.theme

import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import com.hugg.feature.R

fun Dp.toSp(): TextUnit = TextUnit(value = this.value, type = TextUnitType.Sp)

val huggFontFamily = FontFamily(
    Font(R.font.pretendard_bold, FontWeight.Bold),
    Font(R.font.pretendard_light, FontWeight.Light),
    Font(R.font.pretendard_medium, FontWeight.Medium),
    Font(R.font.pretendard_semi_bold, FontWeight.SemiBold),
    Font(R.font.pretendard_regular, FontWeight.Normal),
)
object HuggTypography {
    val btn: TextStyle = TextStyle(
        fontFamily = huggFontFamily,
        fontWeight = FontWeight.Bold,
        letterSpacing = -0.04.dp.toSp(),
        lineHeight = 24.dp.toSp(),
        fontSize = 24.dp.toSp()
    )

    val h1: TextStyle = TextStyle(
        fontFamily = huggFontFamily,
        fontWeight = FontWeight.SemiBold,
        letterSpacing = -0.03.dp.toSp(),
        lineHeight = 31.dp.toSp(),
        fontSize = 24.dp.toSp()
    )

    val h2: TextStyle = TextStyle(
        fontFamily = huggFontFamily,
        fontWeight = FontWeight.SemiBold,
        letterSpacing = -0.03.dp.toSp(),
        lineHeight = 28.dp.toSp(),
        fontSize = 20.dp.toSp()
    )

    val h3: TextStyle = TextStyle(
        fontFamily = huggFontFamily,
        fontWeight = FontWeight.SemiBold,
        letterSpacing = -0.03.dp.toSp(),
        lineHeight = 22.dp.toSp(),
        fontSize = 16.dp.toSp()
    )

    val h4: TextStyle = TextStyle(
        fontSize = 14.dp.toSp(),
        lineHeight = 19.dp.toSp(),
        letterSpacing = -0.03.dp.toSp(),
        fontFamily = huggFontFamily,
        fontWeight = FontWeight.SemiBold,
    )

    val p1: TextStyle = TextStyle(
        fontSize = 16.dp.toSp(),
        lineHeight = 22.dp.toSp(),
        letterSpacing = -0.03.dp.toSp(),
        fontFamily = huggFontFamily,
        fontWeight = FontWeight.Medium,
    )

    val p1_l: TextStyle = TextStyle(
        fontSize = 16.dp.toSp(),
        lineHeight = 24.dp.toSp(),
        letterSpacing = -0.03.dp.toSp(),
        fontFamily = huggFontFamily,
        fontWeight = FontWeight.Light,
    )

    val p2: TextStyle = TextStyle(
        fontSize = 14.dp.toSp(),
        lineHeight = 19.dp.toSp(),
        letterSpacing = -0.03.dp.toSp(),
        fontFamily = huggFontFamily,
        fontWeight = FontWeight.Medium,
    )

    val p3: TextStyle = TextStyle(
        fontSize = 12.dp.toSp(),
        lineHeight = 16.dp.toSp(),
        letterSpacing = -0.03.dp.toSp(),
        fontFamily = huggFontFamily,
        fontWeight = FontWeight.Medium,
    )

    val p3_l: TextStyle = TextStyle(
        fontSize = 12.dp.toSp(),
        lineHeight = 16.dp.toSp(),
        letterSpacing = -0.03.dp.toSp(),
        fontFamily = huggFontFamily,
        fontWeight = FontWeight.Light,
    )

    val p4: TextStyle = TextStyle(
        fontSize = 10.dp.toSp(),
        lineHeight = 14.dp.toSp(),
        letterSpacing = -0.03.dp.toSp(),
        fontFamily = huggFontFamily,
        fontWeight = FontWeight.Medium,
    )
}