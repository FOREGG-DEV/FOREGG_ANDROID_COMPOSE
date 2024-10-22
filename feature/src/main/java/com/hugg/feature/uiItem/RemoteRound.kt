package com.hugg.feature.uiItem

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import com.hugg.domain.model.enums.GenderType
import com.hugg.feature.R
import com.hugg.feature.component.HuggText
import com.hugg.feature.theme.ACCOUNT_ADD_ROUND
import com.hugg.feature.theme.Gs20
import com.hugg.feature.theme.Gs50
import com.hugg.feature.theme.Gs90
import com.hugg.feature.theme.HuggTypography
import com.hugg.feature.theme.White
import com.hugg.feature.util.UnitFormatter
import com.hugg.feature.util.UserInfo

@Composable
fun RemoteRound(
    onClickPrevRoundBtn: () -> Unit = {},
    onClickNextRoundBtn: () -> Unit = {},
    onClickCreateRoundBtn: () -> Unit = {},
    interactionSource: MutableInteractionSource,
    nowRound : Int = UserInfo.info.round
) {
    Box(
        modifier = Modifier.fillMaxWidth()
    ){
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(modifier = Modifier.size(48.dp)) {
                if (nowRound != 0) Image(
                    modifier = Modifier
                        .width(48.dp)
                        .height(48.dp)
                        .padding(12.dp)
                        .clickable(
                            onClick = onClickPrevRoundBtn,
                            interactionSource = interactionSource,
                            indication = null
                        ),
                    imageVector = ImageVector.vectorResource(R.drawable.ic_back_arrow_gs_60),
                    contentDescription = null
                )
            }

            Spacer(modifier = Modifier.size(35.dp))

            HuggText(
                modifier = Modifier.align(Alignment.CenterVertically),
                text = UnitFormatter.getRoundFormat(nowRound),
                color = Gs90,
                style = HuggTypography.h2
            )

            Spacer(modifier = Modifier.size(35.dp))

            Box(modifier = Modifier.size(48.dp)) {
                if (nowRound != UserInfo.info.round) Image(
                    modifier = Modifier
                        .width(48.dp)
                        .height(48.dp)
                        .padding(12.dp)
                        .graphicsLayer(scaleX = -1f)
                        .clickable(
                            onClick = onClickNextRoundBtn,
                            interactionSource = interactionSource,
                            indication = null
                        ),
                    imageVector = ImageVector.vectorResource(R.drawable.ic_back_arrow_gs_60),
                    contentDescription = null
                )
            }
        }
        if(UserInfo.info.genderType == GenderType.FEMALE) Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Spacer(modifier = Modifier.weight(1f))
            if (nowRound == UserInfo.info.round) {
                Box(
                    modifier = Modifier
                        .padding(end = 16.dp, top = 8.dp)
                        .border(width = 0.5.dp, color = Gs20, shape = RoundedCornerShape(4.dp))
                        .background(color = White, shape = RoundedCornerShape(4.dp)),
                    contentAlignment = Alignment.Center
                ) {
                    HuggText(
                        modifier = Modifier
                            .padding(horizontal = 8.dp, vertical = 6.dp)
                            .clickable(
                                onClick = onClickCreateRoundBtn,
                                interactionSource = interactionSource,
                                indication = null
                            ),
                        text = ACCOUNT_ADD_ROUND,
                        style = HuggTypography.p2,
                        color = Gs50
                    )
                }
            }
        }
    }
}