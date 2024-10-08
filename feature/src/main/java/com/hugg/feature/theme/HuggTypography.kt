package com.hugg.feature.theme

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import com.hugg.feature.R

@Composable
fun dpToSp(dp: Dp): TextUnit = with(LocalDensity.current) { dp.toSp() }

val huggFontFamily = FontFamily(
    Font(R.font.pretendard_bold, FontWeight.Bold),
    Font(R.font.pretendard_light, FontWeight.Light),
    Font(R.font.pretendard_medium, FontWeight.Medium),
    Font(R.font.pretendard_semi_bold, FontWeight.SemiBold),
    Font(R.font.pretendard_regular, FontWeight.Normal),
)

val huggSemiFontFamily = FontFamily(
    Font(R.font.gmarketsans_bold, FontWeight.Bold),
    Font(R.font.gmarketsans_light, FontWeight.Light),
    Font(R.font.gmarketsans_medium, FontWeight.Medium),
)

@Composable
fun btn() : TextStyle = TextStyle(
    fontFamily = huggFontFamily,
    fontWeight = FontWeight.Bold,
    letterSpacing = dpToSp((-0.04).dp),
    lineHeight = dpToSp(dp = 24.dp),
    fontSize = dpToSp(dp = 24.dp)
)

@Composable
fun h1(): TextStyle = TextStyle(
    fontFamily = huggFontFamily,
    fontWeight = FontWeight.SemiBold,
    letterSpacing = dpToSp((-0.03).dp),
    lineHeight = dpToSp(dp = 31.dp),
    fontSize = dpToSp(dp = 24.dp)
)

@Composable
fun h2(): TextStyle = TextStyle(
    fontFamily = huggFontFamily,
    fontWeight = FontWeight.SemiBold,
    letterSpacing = dpToSp((-0.03).dp),
    lineHeight = dpToSp(dp = 28.dp),
    fontSize = dpToSp(dp = 20.dp)
)

@Composable
fun h3(): TextStyle = TextStyle(
    fontFamily = huggFontFamily,
    fontWeight = FontWeight.SemiBold,
    letterSpacing = dpToSp((-0.03).dp),
    lineHeight = dpToSp(dp = 22.dp),
    fontSize = dpToSp(dp = 16.dp)
)

@Composable
fun h4(): TextStyle = TextStyle(
    fontFamily = huggFontFamily,
    fontWeight = FontWeight.SemiBold,
    letterSpacing = dpToSp((-0.03).dp),
    lineHeight = dpToSp(dp = 19.dp),
    fontSize = dpToSp(dp = 14.dp)
)

@Composable
fun p1(): TextStyle = TextStyle(
    fontFamily = huggFontFamily,
    fontWeight = FontWeight.Medium,
    letterSpacing = dpToSp((-0.03).dp),
    lineHeight = dpToSp(dp = 22.dp),
    fontSize = dpToSp(dp = 16.dp)
)

@Composable
fun p1_l(): TextStyle = TextStyle(
    fontFamily = huggFontFamily,
    fontWeight = FontWeight.Light,
    letterSpacing = dpToSp((-0.03).dp),
    lineHeight = dpToSp(dp = 24.dp),
    fontSize = dpToSp(dp = 16.dp)
)

@Composable
fun p2(): TextStyle = TextStyle(
    fontFamily = huggFontFamily,
    fontWeight = FontWeight.Medium,
    letterSpacing = dpToSp((-0.03).dp),
    lineHeight = dpToSp(dp = 19.dp),
    fontSize = dpToSp(dp = 14.dp)
)

@Composable
fun p2_l(): TextStyle = TextStyle(
    fontFamily = huggFontFamily,
    fontWeight = FontWeight.Light,
    letterSpacing = dpToSp((-0.03).dp),
    lineHeight = dpToSp(dp = 19.dp),
    fontSize = dpToSp(dp = 14.dp)
)

@Composable
fun p3(): TextStyle = TextStyle(
    fontFamily = huggFontFamily,
    fontWeight = FontWeight.Medium,
    letterSpacing = dpToSp((-0.03).dp),
    lineHeight = dpToSp(dp = 16.dp),
    fontSize = dpToSp(dp = 12.dp)
)

@Composable
fun p3_l(): TextStyle = TextStyle(
    fontFamily = huggFontFamily,
    fontWeight = FontWeight.Light,
    letterSpacing = dpToSp((-0.03).dp),
    lineHeight = dpToSp(dp = 16.dp),
    fontSize = dpToSp(dp = 12.dp)
)

@Composable
fun p4(): TextStyle = TextStyle(
    fontFamily = huggFontFamily,
    fontWeight = FontWeight.Medium,
    letterSpacing = dpToSp((-0.03).dp),
    lineHeight = dpToSp(dp = 10.dp),
    fontSize = dpToSp(dp = 10.dp)
)

@Composable
fun p5(): TextStyle = TextStyle(
    fontFamily = huggFontFamily,
    fontWeight = FontWeight.Normal,
    letterSpacing = dpToSp((-0.03).dp),
    lineHeight = dpToSp(dp = (11.2).dp),
    fontSize = dpToSp(dp = 8.dp)
)

@Composable
fun challenge() : TextStyle = TextStyle(
    fontSize = dpToSp(dp = 40.dp),
    lineHeight = dpToSp(dp = 40.dp),
    fontFamily = huggSemiFontFamily,
    fontWeight = FontWeight.Normal,
)