package com.hugg.feature.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.hugg.domain.model.enums.HuggTabClickedType
import com.hugg.feature.theme.Gs50
import com.hugg.feature.theme.HuggTypography
import com.hugg.feature.theme.MainNormal
import com.hugg.feature.theme.White

@Composable
fun HuggTabBar(
    leftText : String = "",
    middleText : String = "",
    rightText : String = "",
    onClickLeftTab : () -> Unit = {},
    onClickMiddleTab : () -> Unit = {},
    onClickRightTab : () -> Unit = {},
    tabCount : Int = 3,
    initialTabType : HuggTabClickedType = HuggTabClickedType.LEFT,
    interactionSource : MutableInteractionSource
){
    var tabType by remember { mutableStateOf(initialTabType) }
    val onClickSelectTab = { type : HuggTabClickedType -> tabType = type}

    Row(
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .fillMaxWidth()
            .background(color = White, shape = RoundedCornerShape(8.dp))
            .padding(horizontal = 4.dp, vertical = 4.dp)
    ) {
        TabItem(
            type = HuggTabClickedType.LEFT,
            clickedType = tabType,
            onClickSelectTab = onClickSelectTab,
            onClickTab = onClickLeftTab,
            text = leftText,
            interactionSource = interactionSource
        )
        if(tabCount == 3) {
            TabItem(
                type = HuggTabClickedType.MIDDLE,
                clickedType = tabType,
                onClickSelectTab = onClickSelectTab,
                onClickTab = onClickMiddleTab,
                text = middleText,
                interactionSource = interactionSource
            )
        }
        TabItem(
            type = HuggTabClickedType.RIGHT,
            clickedType = tabType,
            onClickSelectTab = onClickSelectTab,
            onClickTab = onClickRightTab,
            text = rightText,
            interactionSource = interactionSource
        )
    }
}

@Composable
fun RowScope.TabItem(
    type : HuggTabClickedType = HuggTabClickedType.LEFT,
    clickedType: HuggTabClickedType = HuggTabClickedType.LEFT,
    onClickSelectTab : (HuggTabClickedType) -> Unit = {},
    onClickTab : () -> Unit = {},
    text : String = "",
    interactionSource : MutableInteractionSource
){
    Box(
        modifier = Modifier
            .weight(1f)
            .height(40.dp)
            .background(
                color = if (type == clickedType) MainNormal else White,
                shape = RoundedCornerShape(8.dp)
            )
            .clickable(
                onClick = {
                    if (type == clickedType) return@clickable
                    onClickSelectTab(type)
                    onClickTab()
                },
                interactionSource = interactionSource,
                indication = null
            ),
        contentAlignment = Alignment.Center
    ){
        HuggText(
            text = text,
            style = HuggTypography.h2,
            color = if(type == clickedType) White else Gs50
        )
    }
}