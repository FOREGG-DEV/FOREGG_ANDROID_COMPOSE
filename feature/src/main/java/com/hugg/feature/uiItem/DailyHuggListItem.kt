package com.hugg.feature.uiItem

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.hugg.domain.model.response.dailyHugg.DailyHuggListItemVo
import com.hugg.feature.R
import com.hugg.feature.component.HuggText
import com.hugg.feature.theme.GsBlack
import com.hugg.feature.theme.GsWhite
import com.hugg.feature.theme.HuggTypography
import com.hugg.feature.theme.MainNormal
import com.hugg.feature.theme.White
import com.hugg.feature.util.TimeFormatter
import com.hugg.feature.util.onThrottleClick

@Composable
fun DailyHuggListItem(
    item: DailyHuggListItemVo = DailyHuggListItemVo(),
    goToDailyHuggDetail: (String) -> Unit = {},
    interactionSource: MutableInteractionSource,
    dateColor : Color = MainNormal,
    borderColor : Color = White
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(60.dp)
            .onThrottleClick(
                interactionSource = interactionSource,
                onClick = {
                    goToDailyHuggDetail(item.date)
                }
            )
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .background(color = White, shape = RoundedCornerShape(8.dp))
                .border(width = 1.dp, color = borderColor, shape = RoundedCornerShape(8.dp)),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Spacer(modifier = Modifier.width(60.dp))

            Icon(
                painter = painterResource(R.drawable.ic_hugging_female),
                contentDescription = "",
                modifier = Modifier
                    .size(20.dp),
                tint = Color.Unspecified
            )

            Spacer(modifier = Modifier.width(8.dp))

            HuggText(
                text = item.content,
                style = HuggTypography.p3_l,
                color = GsBlack,
                maxLines = 2
            )
        }

        Box(
            modifier = Modifier
                .fillMaxHeight()
                .width(52.dp)
                .clip(RoundedCornerShape(topStart = 8.dp, bottomStart = 8.dp))
                .background(dateColor),
            contentAlignment = Alignment.Center
        ) {
            HuggText(
                text = TimeFormatter.getMonthDayWithSlash(item.date),
                style = HuggTypography.h4,
                color = GsWhite,
            )
        }
    }
}