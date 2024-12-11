package com.hugg.feature.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.hugg.feature.R
import com.hugg.feature.theme.Gs30
import com.hugg.feature.theme.Gs70
import com.hugg.feature.theme.HuggTypography
import com.hugg.feature.theme.InActiveBlankBtn
import com.hugg.feature.theme.InActiveMainLight
import com.hugg.feature.theme.InActiveMainNormal
import com.hugg.feature.theme.MainLight
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
    isActive : Boolean = true,
    contentPadding : PaddingValues = PaddingValues(horizontal = 0.dp, vertical = 16.dp),
    strokeColor: Color = MainLight
) {
    Button(
        modifier = modifier,
        onClick = onClickBtn,
        shape = RoundedCornerShape(radius),
        border = BorderStroke(1.dp, if(isActive) strokeColor else InActiveMainLight),
        contentPadding = contentPadding,
        enabled = isActive,
        colors = ButtonDefaults
            .buttonColors(
                containerColor = White,
                disabledContainerColor = InActiveBlankBtn
            ),
    ) {
        HuggText(
            text = text,
            color = if(isActive) textColor else Gs30,
            style = textStyle
        )
    }
}

@Composable
fun BlankBtnWithIcon(
    modifier: Modifier = Modifier,
    onClickBtn : () -> Unit = {},
    radius : Dp = 8.dp,
    text : String = "테스트",
    textStyle: TextStyle = HuggTypography.btn,
    textColor : Color = Gs70,
    icon : Int = R.drawable.ic_create_box_top_bar,
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
        Row {
            Image(painter = painterResource(id = icon), contentDescription = null)
            Spacer(modifier = Modifier.size(4.dp))
            HuggText(
                text = text,
                color = textColor,
                style = textStyle
            )
        }
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
    isActive : Boolean = true,
    contentPadding : PaddingValues = PaddingValues(horizontal = 0.dp, vertical = 16.dp)
) {
    Button(
        modifier = modifier,
        onClick = onClickBtn,
        shape = RoundedCornerShape(radius),
        contentPadding = contentPadding,
        enabled = isActive,
        colors = ButtonDefaults
            .buttonColors(
                containerColor = MainNormal,
                disabledContainerColor = InActiveMainNormal
            ),
    ) {
        HuggText(
            text = text,
            color = if(isActive) textColor else Gs30,
            style = textStyle
        )
    }
}

@Composable
fun FilledBtn(
    modifier: Modifier = Modifier,
    onClickBtn : () -> Unit = {},
    radius : Dp = 8.dp,
    text : AnnotatedString,
    textStyle: TextStyle = HuggTypography.btn,
    textColor : Color = White,
    isActive : Boolean = true,
    contentPadding : PaddingValues = PaddingValues(horizontal = 0.dp, vertical = 16.dp)
) {
    Button(
        modifier = modifier,
        onClick = onClickBtn,
        shape = RoundedCornerShape(radius),
        contentPadding = contentPadding,
        enabled = isActive,
        colors = ButtonDefaults
            .buttonColors(
                containerColor = MainNormal,
                disabledContainerColor = InActiveMainNormal
            ),
    ) {
        HuggText(
            text = text,
            color = if(isActive) textColor else Gs30,
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
    onClickBtn: () -> Unit = {},
    interactionSource : MutableInteractionSource
){
    Box(
        modifier = if(modifier != Modifier) modifier else Modifier
            .size(48.dp)
            .background(color = MainNormal, shape = RoundedCornerShape(8.dp))
            .clickable(
                onClick = onClickBtn,
                interactionSource = interactionSource,
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
    onClickBtn: () -> Unit = {},
    interactionSource : MutableInteractionSource
){
    Box(
        modifier = if(modifier != Modifier) modifier else Modifier
            .size(48.dp)
            .background(color = Gs70, shape = RoundedCornerShape(8.dp))
            .clickable(
                onClick = onClickBtn,
                interactionSource = interactionSource,
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
internal fun testt(){
    FilledBtn()
}