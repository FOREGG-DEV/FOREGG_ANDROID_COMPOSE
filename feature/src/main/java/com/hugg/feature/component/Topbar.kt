package com.hugg.feature.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.hugg.domain.model.enums.TopBarLeftType
import com.hugg.domain.model.enums.TopBarMiddleType
import com.hugg.domain.model.enums.TopBarRightType
import com.hugg.feature.R
import com.hugg.feature.theme.Background
import com.hugg.feature.theme.Black
import com.hugg.feature.theme.DAILY_HUGG_BTN_TEXT
import com.hugg.feature.theme.Gs20
import com.hugg.feature.theme.Gs50
import com.hugg.feature.theme.Gs60
import com.hugg.feature.theme.GsWhite
import com.hugg.feature.theme.HuggTheme
import com.hugg.feature.theme.HuggTypography
import com.hugg.feature.theme.WORD_SKIP

@Composable
fun TopBar(
    leftItemType : TopBarLeftType = TopBarLeftType.NONE,
    middleItemType : TopBarMiddleType = TopBarMiddleType.NONE,
    rightItemType : TopBarRightType = TopBarRightType.NONE,
    leftBtnClicked : () -> Unit = {},
    middleText : String = "",
    rightBtnClicked : () -> Unit = {},
    interactionSource : MutableInteractionSource = remember { MutableInteractionSource() },
    rightItemContent: String = ""
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .height(57.dp)
            .background(Background)
    ){
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                modifier = Modifier.weight(1f),
                horizontalArrangement = Arrangement.Start
            ) {
                DrawLeftItem(
                    leftItemType = leftItemType, leftBtnClicked = leftBtnClicked, interactionSource = interactionSource
                )
            }

            DrawMiddleItem(
                middleItemType = middleItemType, middleText = middleText
            )

            Row(
                modifier = Modifier.weight(1f),
                horizontalArrangement = Arrangement.End
            ) {
                DrawRightItem(
                    rightItemType = rightItemType, rightBtnClicked = rightBtnClicked, interactionSource = interactionSource, content = rightItemContent
                )
            }
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(1.dp)
                .background(Gs20)
        )
    }
}

@Composable
fun DrawLeftItem(
    leftItemType : TopBarLeftType = TopBarLeftType.NONE,
    leftBtnClicked : () -> Unit = {},
    interactionSource : MutableInteractionSource
){
    when(leftItemType){
        TopBarLeftType.BACK -> DrawLeftBackBtn(leftBtnClicked, interactionSource)
        TopBarLeftType.LOGO -> DrawLeftLogo()
        TopBarLeftType.CLOSE -> DrawLeftCloseBtn(leftBtnClicked, interactionSource)
        TopBarLeftType.NONE -> {}
    }
}

@Composable
fun DrawMiddleItem(
    middleItemType : TopBarMiddleType = TopBarMiddleType.NONE,
    middleText : String = "",
){
    when(middleItemType){
        TopBarMiddleType.LOGO -> DrawMiddleLogo()
        TopBarMiddleType.TEXT -> DrawMiddleText(middleText)
        TopBarMiddleType.NONE -> {}
    }
}

@Composable
fun DrawRightItem(
    rightItemType : TopBarRightType = TopBarRightType.NONE,
    rightBtnClicked : () -> Unit = {},
    interactionSource : MutableInteractionSource,
    content: String = ""
){
    when(rightItemType){
        TopBarRightType.SKIP -> DrawRightSkipBtn(rightBtnClicked, interactionSource)
        TopBarRightType.DAILY_RECORD -> DrawDailyRecordBtn(rightBtnClicked)
        TopBarRightType.CREATE -> DrawCreateBtn(rightBtnClicked, interactionSource)
        TopBarRightType.CHECK -> DrawCheckBtn(rightBtnClicked, interactionSource)
        TopBarRightType.DELETE -> DrawDeleteBtn(rightBtnClicked, interactionSource)
        TopBarRightType.DELETE_GS30 -> DrawDeleteGs30Btn(rightBtnClicked, interactionSource)
        TopBarRightType.NOTIFICATION -> DrawNotificationBtn(rightBtnClicked, interactionSource)
        TopBarRightType.POINT -> DrawPointIndicator(content = content)
        TopBarRightType.NONE -> {}
    }
}

// ----- 왼쪽 버튼들 ----- //
@Composable
fun DrawLeftBackBtn(
    onClickBtn : () -> Unit,
    interactionSource : MutableInteractionSource
){
    Spacer(modifier = Modifier.width(16.dp))
    Image(
        modifier = Modifier
            .width(48.dp)
            .height(48.dp)
            .padding(12.dp)
            .clickable(
                onClick = onClickBtn,
                interactionSource = interactionSource,
                indication = null
            ),
        imageVector = ImageVector.vectorResource(R.drawable.ic_back_arrow_gs_60),
        contentDescription = null
    )
}

@Composable
fun DrawLeftLogo(){
    Spacer(modifier = Modifier.width(22.dp))
    Image(
        painter = painterResource(R.drawable.ic_home_logo),
        contentDescription = null
    )
}

@Composable
fun DrawLeftCloseBtn(
    onClickBtn : () -> Unit,
    interactionSource : MutableInteractionSource
){
    Spacer(modifier = Modifier.width(16.dp))
    Image(
        modifier = Modifier
            .width(48.dp)
            .height(48.dp)
            .padding(12.dp)
            .clickable(
                onClick = onClickBtn,
                interactionSource = interactionSource,
                indication = null
            ),
        imageVector = ImageVector.vectorResource(R.drawable.ic_close_gs_60),
        contentDescription = null
    )
}

// -------------------- //

// ----- 중간 버튼들 ----- //
@Composable
fun DrawMiddleLogo(){
    Image(
        modifier = Modifier.padding(vertical = 15.dp),
        imageVector = ImageVector.vectorResource(R.drawable.ic_logo),
        contentDescription = null
    )
}

@Composable
fun DrawMiddleText(
    middleText : String = "",
){
    HuggText(
        text = middleText,
        color = Black,
        style = HuggTypography.h3
    )
}

// -------------------- //

// ----- 오른쪽 버튼들 ----- //
@Composable
fun DrawRightSkipBtn(
    rightBtnClicked : () -> Unit = {},
    interactionSource : MutableInteractionSource
){
    HuggText(
        modifier = Modifier
            .padding(horizontal = 11.dp, vertical = 14.dp)
            .clickable(
                onClick = rightBtnClicked,
                interactionSource = interactionSource,
                indication = null
            ),
        text = WORD_SKIP,
        color = Gs60,
        style = HuggTypography.h4
    )
    Spacer(modifier = Modifier.width(17.dp))
}

@Composable
fun DrawDailyRecordBtn(
    rightBtnClicked : () -> Unit = {},
){
    BlankBtn(
        modifier = Modifier
            .width(90.dp)
            .height(28.dp),
        onClickBtn = rightBtnClicked,
        radius = 6.dp,
        text = DAILY_HUGG_BTN_TEXT,
        textStyle = HuggTypography.p2,
        textColor = Gs50,
        strokeColor = Gs20,
        contentPadding = PaddingValues(vertical = 6.dp)
    )
    Spacer(modifier = Modifier.width(16.dp))
}

@Composable
fun DrawPointIndicator(
    content: String = ""
) {
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(4.dp))
            .background(GsWhite)
            .border(width = 1.dp, color = Gs20, shape = RoundedCornerShape(4.dp)),
        contentAlignment = Alignment.Center
    ) {
        HuggText(
            text = content,
            style = HuggTypography.p2,
            color = Gs50,
            modifier = Modifier
                .padding(vertical = 4.dp, horizontal = 24.dp)
        )
    }
    Spacer(modifier = Modifier.width(16.dp))
}

@Composable
fun DrawCreateBtn(
    rightBtnClicked : () -> Unit = {},
    interactionSource : MutableInteractionSource
){
    Image(
        modifier = Modifier
            .width(48.dp)
            .height(48.dp)
            .padding(8.dp)
            .clickable(
                onClick = rightBtnClicked,
                interactionSource = interactionSource,
                indication = null
            ),
        imageVector = ImageVector.vectorResource(R.drawable.ic_create_box_top_bar),
        contentDescription = null
    )
    Spacer(modifier = Modifier.width(16.dp))
}

@Composable
fun DrawCheckBtn(
    rightBtnClicked : () -> Unit = {},
    interactionSource : MutableInteractionSource
){
    Image(
        modifier = Modifier
            .width(48.dp)
            .height(48.dp)
            .padding(8.dp)
            .clickable(
                onClick = rightBtnClicked,
                interactionSource = interactionSource,
                indication = null
            ),
        imageVector = ImageVector.vectorResource(R.drawable.ic_check_circle_main),
        contentDescription = null
    )
    Spacer(modifier = Modifier.width(16.dp))
}

@Composable
fun DrawDeleteBtn(
    rightBtnClicked : () -> Unit = {},
    interactionSource : MutableInteractionSource
){
    Image(
        modifier = Modifier
            .width(48.dp)
            .height(48.dp)
            .padding(8.dp)
            .clickable(
                onClick = rightBtnClicked,
                interactionSource = interactionSource,
                indication = null
            ),
        imageVector = ImageVector.vectorResource(R.drawable.ic_delete),
        contentDescription = null
    )
    Spacer(modifier = Modifier.width(16.dp))
}

@Composable
fun DrawDeleteGs30Btn(
    rightBtnClicked : () -> Unit = {},
    interactionSource : MutableInteractionSource
){
    Image(
        modifier = Modifier
            .width(48.dp)
            .height(48.dp)
            .padding(8.dp)
            .clickable(
                onClick = rightBtnClicked,
                interactionSource = interactionSource,
                indication = null
            ),
        imageVector = ImageVector.vectorResource(R.drawable.ic_delete_gs_30),
        contentDescription = null
    )
    Spacer(modifier = Modifier.width(16.dp))
}


@Composable
fun DrawNotificationBtn(
    rightBtnClicked : () -> Unit = {},
    interactionSource : MutableInteractionSource
){
    Image(
        modifier = Modifier
            .size(48.dp)
            .clickable(
                onClick = rightBtnClicked,
                interactionSource = interactionSource,
                indication = null
            ),
        painter = painterResource(R.drawable.ic_notification_fill_gs_50),
        contentDescription = null
    )
    Spacer(modifier = Modifier.width(16.dp))
}
// -------------------- //

@Preview
@Composable
internal fun Preview() {
    HuggTheme {
        DrawDailyRecordBtn()
    }
}