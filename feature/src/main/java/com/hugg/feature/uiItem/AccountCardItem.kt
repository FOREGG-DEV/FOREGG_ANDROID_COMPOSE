package com.hugg.feature.uiItem

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.hugg.domain.model.enums.AccountColorType
import com.hugg.domain.model.vo.account.AccountCardVo
import com.hugg.feature.component.HuggText
import com.hugg.feature.theme.ACCOUNT_ROUND_UNIT
import com.hugg.feature.theme.Black
import com.hugg.feature.theme.CalendarEtc
import com.hugg.feature.theme.CalendarHospital
import com.hugg.feature.theme.CalendarInjection
import com.hugg.feature.theme.CalendarPill
import com.hugg.feature.theme.Gs70
import com.hugg.feature.theme.Gs80
import com.hugg.feature.theme.HuggTypography
import com.hugg.feature.theme.SelectedCardColor
import com.hugg.feature.theme.White
import com.hugg.feature.util.TimeFormatter
import com.hugg.feature.util.UnitFormatter
import com.hugg.feature.util.UnitFormatter.getMoneyFormatWithUnit

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun AccountCardItem(
    item : AccountCardVo = AccountCardVo(),
    onClickCard : (Long, Long) -> Unit = {_, _ -> },
    onLongClickCard : (Long) -> Unit = {},
    interactionSource: MutableInteractionSource,
){
    val boxColor = when(item.color){
        AccountColorType.ALL,
        AccountColorType.RED -> CalendarPill
        AccountColorType.BLUE -> CalendarInjection
        AccountColorType.GREEN -> CalendarHospital
        AccountColorType.YELLOW -> CalendarEtc
    }

    val boxText = when(item.color){
        AccountColorType.ALL,
        AccountColorType.RED -> item.cardName
        AccountColorType.BLUE,
        AccountColorType.GREEN,
        AccountColorType.YELLOW -> UnitFormatter.getSubsidyTitleWithoutMoneyFormat(item.cardName)
    }

    Column(
        modifier = Modifier
            .padding(bottom = 4.dp, start = 16.dp, end = 16.dp)
            .fillMaxWidth()
            .background(
                color = if (item.isSelected) SelectedCardColor else White,
                shape = RoundedCornerShape(8.dp)
            )
            .padding(top = 8.dp, bottom = 11.dp, start = 12.dp, end = 12.dp)
            .combinedClickable(
                onLongClick = { onLongClickCard(item.expenditureId) },
                onClick = { onClickCard(item.ledgerId, item.expenditureId) },
                interactionSource = interactionSource,
                indication = null
            )
    ) {

        Row {
            HuggText(
                text = TimeFormatter.getDotsDate(item.date),
                style = HuggTypography.p3_l,
                color = Gs70
            )

            Spacer(modifier = Modifier.size(4.dp))

            HuggText(
                text = "${item.round}$ACCOUNT_ROUND_UNIT",
                style = HuggTypography.p3_l,
                color = Gs70
            )
        }

        Spacer(modifier = Modifier.size(8.dp))

        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .background(
                        color = boxColor,
                        shape = RoundedCornerShape(4.dp)
                    )
                    .padding(vertical = 4.dp, horizontal = 9.dp),
                contentAlignment = Alignment.Center
            ){
                HuggText(
                    text = boxText,
                    style = HuggTypography.p3_l,
                    color = Gs80
                )
            }

            Spacer(modifier = Modifier.size(8.dp))

            HuggText(
                text = item.title,
                style = HuggTypography.p1,
                color = Black
            )

            Spacer(modifier = Modifier.weight(1f))

            HuggText(
                text = getMoneyFormatWithUnit(item.money),
                style = HuggTypography.p1,
                color = Black
            )
        }

    }
}