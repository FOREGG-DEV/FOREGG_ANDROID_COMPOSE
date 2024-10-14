package com.hugg.feature.theme

import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.hugg.feature.R

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
        letterSpacing = (-0.04).sp,
        lineHeight = 24.sp,
        fontSize = 24.sp
    )

    val h1: TextStyle = TextStyle(
        fontFamily = huggFontFamily,
        fontWeight = FontWeight.SemiBold,
        letterSpacing = (-0.03).sp,
        lineHeight = 31.sp,
        fontSize = 24.sp
    )

    val h2: TextStyle = TextStyle(
        fontFamily = huggFontFamily,
        fontWeight = FontWeight.SemiBold,
        letterSpacing = (-0.03).sp,
        lineHeight = 28.sp,
        fontSize = 20.sp
    )

    val h3: TextStyle = TextStyle(
        fontFamily = huggFontFamily,
        fontWeight = FontWeight.SemiBold,
        letterSpacing = (-0.03).sp,
        lineHeight = 22.sp,
        fontSize = 16.sp
    )

    val h4: TextStyle = TextStyle(
        fontSize = 14.sp,
        lineHeight = 19.sp,
        letterSpacing = (-0.03).sp,
        fontFamily = huggFontFamily,
        fontWeight = FontWeight.SemiBold,
    )

    val p1: TextStyle = TextStyle(
        fontSize = 16.sp,
        lineHeight = 22.sp,
        letterSpacing = (-0.03).sp,
        fontFamily = huggFontFamily,
        fontWeight = FontWeight.Medium,
    )

    val p1_l: TextStyle = TextStyle(
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = (-0.03).sp,
        fontFamily = huggFontFamily,
        fontWeight = FontWeight.Light,
    )

    val p2: TextStyle = TextStyle(
        fontSize = 14.sp,
        lineHeight = 19.sp,
        letterSpacing = (-0.03).sp,
        fontFamily = huggFontFamily,
        fontWeight = FontWeight.Medium,
    )

    val p2_l: TextStyle = TextStyle(
        fontSize = 14.sp,
        lineHeight = 19.sp,
        letterSpacing = (-0.03).sp,
        fontFamily = huggFontFamily,
        fontWeight = FontWeight.Light,
    )

    val p3: TextStyle = TextStyle(
        fontSize = 12.sp,
        lineHeight = 16.sp,
        letterSpacing = -0.03.sp,
        fontFamily = huggFontFamily,
        fontWeight = FontWeight.Medium,
    )

    val p3_l: TextStyle = TextStyle(
        fontSize = 12.sp,
        lineHeight = 16.sp,
        letterSpacing = (-0.03).sp,
        fontFamily = huggFontFamily,
        fontWeight = FontWeight(300),
    )

    val p4: TextStyle = TextStyle(
        fontSize = 10.sp,
        lineHeight = 10.sp,
        letterSpacing = (-0.03).sp,
        fontFamily = huggFontFamily,
        fontWeight = FontWeight.Medium,
    )

    val p5: TextStyle = TextStyle(
        fontSize = 8.sp,
        lineHeight = (11.2).sp,
        letterSpacing = (-0.03).sp,
        fontFamily = huggFontFamily,
        fontWeight = FontWeight.Normal,
    )
}