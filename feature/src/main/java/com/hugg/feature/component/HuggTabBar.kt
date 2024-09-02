package com.hugg.feature.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
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
    initialTabType : HuggTabClickedType = HuggTabClickedType.LEFT
){
    var tabType by remember { mutableStateOf(initialTabType) }

    Row(
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .fillMaxWidth()
            .background(color = White, shape = RoundedCornerShape(8.dp))
            .padding(horizontal = 4.dp, vertical = 4.dp)
    ) {
        Box(
            modifier = Modifier
                .weight(1f)
                .height(40.dp)
                .background(
                    color = if (tabType == HuggTabClickedType.LEFT) MainNormal else White,
                    shape = RoundedCornerShape(8.dp)
                )
                .clickable(
                    onClick = {
                        tabType = HuggTabClickedType.LEFT
                        onClickLeftTab()
                    },
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null
                ),
            contentAlignment = Alignment.Center
        ){
            Text(
                text = leftText,
                style = HuggTypography.h2,
                color = if(tabType == HuggTabClickedType.LEFT) White else Gs50
            )
        }
        if(tabCount == 3) {
            Box(
                modifier = Modifier
                    .weight(1f)
                    .height(40.dp)
                    .background(
                        color = if (tabType == HuggTabClickedType.MIDDLE) MainNormal else White,
                        shape = RoundedCornerShape(8.dp)
                    )
                    .clickable(
                        onClick = {
                            tabType = HuggTabClickedType.MIDDLE
                            onClickMiddleTab()
                        },
                        interactionSource = remember { MutableInteractionSource() },
                        indication = null
                    ),
                contentAlignment = Alignment.Center
            ){
                Text(
                    text = middleText,
                    style = HuggTypography.h2,
                    color = if(tabType == HuggTabClickedType.MIDDLE) White else Gs50
                )
            }
        }
        Box(
            modifier = Modifier
                .weight(1f)
                .height(40.dp)
                .background(
                    color = if (tabType == HuggTabClickedType.RIGHT) MainNormal else White,
                    shape = RoundedCornerShape(8.dp)
                )
                .clickable(
                    onClick = {
                        tabType = HuggTabClickedType.RIGHT
                        onClickRightTab()
                    },
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null
                ),
            contentAlignment = Alignment.Center
        ){
            Text(
                text = rightText,
                style = HuggTypography.h2,
                color = if(tabType == HuggTabClickedType.RIGHT) White else Gs50
            )
        }
    }
}