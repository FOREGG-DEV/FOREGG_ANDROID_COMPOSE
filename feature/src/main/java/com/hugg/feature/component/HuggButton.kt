package com.hugg.feature.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.hugg.feature.R
import com.hugg.feature.theme.Gs70
import com.hugg.feature.theme.HuggTheme
import com.hugg.feature.theme.HuggTypography
import com.hugg.feature.theme.MainNormal
import com.hugg.feature.theme.White

@Composable
fun BlankBtn(
    modifier: Modifier = Modifier,
    onClickBtn : () -> Unit = {},
    radius : Dp = 8.dp,
    text : String = "테스트",
    textStyle: TextStyle = HuggTypography.btn,
    textColor : Color = Gs70,
    contentPadding : PaddingValues = PaddingValues(horizontal = 0.dp, vertical = 16.dp)
) {
    Button(
        modifier = modifier,
        onClick = onClickBtn,
        colors = ButtonDefaults
            .buttonColors(containerColor = White),
        shape = RoundedCornerShape(radius),
        border = BorderStroke(1.dp, MainNormal),
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
            .buttonColors(containerColor = MainNormal),
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


@Composable
fun KaKaoLoginBtn(
    modifier: Modifier = Modifier,
){
    Image(
        contentScale = ContentScale.FillWidth,
        modifier = modifier,
        imageVector = ImageVector.vectorResource(R.drawable.ic_kakao_login),
        contentDescription = null
    )
}

@Composable
fun PlusBtn(
    modifier: Modifier = Modifier,
    onClickBtn: () -> Unit = {}
){
    Box(
        modifier = if(modifier != Modifier) modifier else Modifier
            .size(48.dp)
            .background(color = MainNormal, shape = RoundedCornerShape(8.dp))
            .clickable(
                onClick = onClickBtn,
                interactionSource = remember { MutableInteractionSource() },
                indication = null
            ),
        contentAlignment = Alignment.Center
    ){
        Image(
            imageVector = ImageVector.vectorResource(R.drawable.ic_plus_white),
            contentDescription = null
        )
    }
}

@Composable
fun CancelBtn(
    modifier: Modifier = Modifier,
    onClickBtn: () -> Unit = {}
){
    Box(
        modifier = if(modifier != Modifier) modifier else Modifier
            .size(48.dp)
            .background(color = Gs70, shape = RoundedCornerShape(8.dp))
            .clickable(
                onClick = onClickBtn,
                interactionSource = remember { MutableInteractionSource() },
                indication = null
            ),
        contentAlignment = Alignment.Center
    ){
        Image(
            imageVector = ImageVector.vectorResource(R.drawable.ic_close_white),
            contentDescription = null
        )
    }
}

@Preview
@Composable
internal fun Hi() {
    HuggTheme {
        CancelBtn()
    }
}