package com.hugg.feature.uiItem

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.hugg.domain.model.enums.AccountColorType
import com.hugg.domain.model.enums.CreateOrEditType
import com.hugg.domain.model.response.account.SubsidyListResponseVo
import com.hugg.feature.theme.ACCOUNT_AVAILABLE_MONEY
import com.hugg.feature.theme.ACCOUNT_EXPENDITURE
import com.hugg.feature.theme.Background
import com.hugg.feature.theme.CalendarEtc
import com.hugg.feature.theme.CalendarHospital
import com.hugg.feature.theme.CalendarInjection
import com.hugg.feature.theme.Gs70
import com.hugg.feature.theme.Gs90
import com.hugg.feature.theme.HuggTypography
import com.hugg.feature.theme.SubsidyColorBlue
import com.hugg.feature.theme.SubsidyColorGreen
import com.hugg.feature.theme.SubsidyColorYellow
import com.hugg.feature.theme.White
import com.hugg.feature.util.UnitFormatter

@Composable
fun SubsidyDetailBox(
    onClickDetailSubsidyBtn : (Long, CreateOrEditType, Int) -> Unit = {_, _, _-> },
    interactionSource: MutableInteractionSource,
    item : SubsidyListResponseVo = SubsidyListResponseVo()
){
    val subColor = when(item.color){
        AccountColorType.BLUE -> CalendarInjection
        AccountColorType.GREEN -> CalendarHospital
        AccountColorType.YELLOW -> CalendarEtc
        else -> CalendarEtc
    }

    val percentColor = when(item.color){
        AccountColorType.BLUE -> SubsidyColorBlue
        AccountColorType.GREEN -> SubsidyColorGreen
        AccountColorType.YELLOW -> SubsidyColorYellow
        else -> SubsidyColorYellow
    }
    
    val percent = item.percent / 100f

    Box(
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .fillMaxWidth()
            .background(color = White, shape = RoundedCornerShape(8.dp))
            .clickable(
                onClick = { onClickDetailSubsidyBtn(item.id, CreateOrEditType.EDIT, -1) },
                interactionSource = interactionSource,
                indication = null
            )
            .padding(top = 16.dp, bottom = 16.dp, start = 12.dp, end = 11.dp)
    ) {
        Column{
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(12.dp)
                        .background(color = subColor, shape = RoundedCornerShape(3.dp))
                )

                Spacer(modifier = Modifier.size(4.dp))

                Text(
                    text = UnitFormatter.getSubsidyTitleFormat(item.nickname),
                    style = HuggTypography.h2,
                    color = Gs90
                )

                Spacer(modifier = Modifier.weight(1f))

                Text(
                    text = UnitFormatter.getMoneyFormatWithUnit(item.amount),
                    style = HuggTypography.h2,
                    color = Gs90
                )
            }

            Spacer(modifier = Modifier.size(8.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Spacer(modifier = Modifier.size(16.dp))

                Text(
                    text = ACCOUNT_EXPENDITURE,
                    style = HuggTypography.h2,
                    color = Gs70
                )

                Spacer(modifier = Modifier.weight(1f))

                Text(
                    text = UnitFormatter.getMoneyFormatWithUnit(item.expenditure),
                    style = HuggTypography.p1,
                    color = Gs90
                )
            }

            Spacer(modifier = Modifier.size(8.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Spacer(modifier = Modifier.size(16.dp))

                Text(
                    text = ACCOUNT_AVAILABLE_MONEY,
                    style = HuggTypography.h2,
                    color = Gs70
                )

                Spacer(modifier = Modifier.weight(1f))

                Text(
                    text = UnitFormatter.getMoneyFormatWithUnit(item.available),
                    style = HuggTypography.p1,
                    color = Gs90
                )
            }

            Spacer(modifier = Modifier.size(16.dp))

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(26.dp)
                    .background(color = Background, shape = RoundedCornerShape(4.dp))
                    .clip(shape = RoundedCornerShape(4.dp)),
            ){
                Box(
                    modifier = Modifier
                        .fillMaxWidth(percent)
                        .height(26.dp)
                        .background(color = percentColor)
                        .clip(shape = RoundedCornerShape(4.dp))
                )

                Row(
                    modifier = Modifier
                        .padding(end = 8.dp)
                        .fillMaxWidth()
                        .height(26.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.End
                ) {
                    Text(
                        text = UnitFormatter.getPercentFormat(item.percent),
                        style = HuggTypography.h3,
                        color = Gs70
                    )
                }
            }
        }
    }
}