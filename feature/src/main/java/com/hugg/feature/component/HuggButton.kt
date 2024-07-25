package com.hugg.feature.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.hugg.feature.theme.Black
import com.hugg.feature.theme.HuggTheme
import com.hugg.feature.theme.HuggTypography
import com.hugg.feature.theme.Main
import com.hugg.feature.theme.White

@Composable
fun BlankBtn(
    modifier: Modifier = Modifier,
    onClickBtn : () -> Unit = {},
    radius : Dp = 8.dp,
    text : String = "테스트",
    textStyle: TextStyle = HuggTypography.btn,
    textColor : Color = Black,
    contentPadding : PaddingValues = PaddingValues(horizontal = 0.dp, vertical = 16.dp)
) {
    Button(
        modifier = modifier,
        onClick = onClickBtn,
        colors = ButtonDefaults
            .buttonColors(containerColor = White),
        shape = RoundedCornerShape(radius),
        border = BorderStroke(1.dp, Main),
        contentPadding = contentPadding
    ) {
        Text(
            text = text,
            color = textColor,
            style = textStyle
        )
    }
}

@Composable
fun FilledBtn(
    modifier: Modifier = Modifier,
    onClickBtn : () -> Unit = {},
    radius : Dp = 8.dp,
    text : String = "테스트",
    textStyle: TextStyle = HuggTypography.btn,
    textColor : Color = White,
    contentPadding : PaddingValues = PaddingValues(horizontal = 0.dp, vertical = 16.dp)
) {
    Button(
        modifier = modifier,
        onClick = onClickBtn,
        colors = ButtonDefaults
            .buttonColors(containerColor = Main),
        shape = RoundedCornerShape(radius),
        contentPadding = contentPadding
    ) {
        Text(
            text = text,
            color = textColor,
            style = textStyle
        )
    }
}

@Preview
@Composable
internal fun Hi() {
    HuggTheme {
        FilledBtn()
    }
}