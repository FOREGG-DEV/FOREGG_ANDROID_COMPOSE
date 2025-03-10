package com.hugg.feature.uiItem

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import com.hugg.domain.model.enums.AccountColorType
import com.hugg.domain.model.response.account.AccountSubsidyAvailableItemVo
import com.hugg.feature.R
import com.hugg.feature.component.HuggText
import com.hugg.feature.theme.ACCOUNT_AVAILABLE_MONEY
import com.hugg.feature.theme.CalendarEtc
import com.hugg.feature.theme.CalendarHospital
import com.hugg.feature.theme.CalendarInjection
import com.hugg.feature.theme.Gs60
import com.hugg.feature.theme.Gs80
import com.hugg.feature.theme.HuggTypography
import com.hugg.feature.util.UnitFormatter

@Composable
fun SubsidyTotalBoxItem(
    item: AccountSubsidyAvailableItemVo = AccountSubsidyAvailableItemVo(),
    onClickGoToSubsidyList : () -> Unit = {},
    interactionSource: MutableInteractionSource
) {
    val color = when (item.color) {
        AccountColorType.BLUE -> CalendarInjection
        AccountColorType.GREEN -> CalendarHospital
        AccountColorType.YELLOW -> CalendarEtc
        else -> CalendarEtc
    }

    Row(
        modifier = Modifier
            .padding(start = 12.dp)
            .fillMaxWidth()
            .clickable(
                onClick = onClickGoToSubsidyList,
                interactionSource = interactionSource,
                indication = null
            ),
        verticalAlignment = Alignment.CenterVertically
    ) {

        Box(
            modifier = Modifier
                .size(12.dp)
                .background(color = color, shape = RoundedCornerShape(3.dp))
        )

        Spacer(modifier = Modifier.size(4.dp))

        HuggText(
            text = UnitFormatter.getSubsidyTitleFormat(item.nickname),
            style = HuggTypography.p1,
            color = Gs80
        )

        Spacer(modifier = Modifier.size(4.dp))

        Image(
            imageVector = ImageVector.vectorResource(id = R.drawable.ic_arrow_in_circle),
            contentDescription = null
        )

        Spacer(modifier = Modifier.weight(1f))

        HuggText(
            text = ACCOUNT_AVAILABLE_MONEY,
            style = HuggTypography.p3,
            color = Gs60
        )

        Spacer(modifier = Modifier.size(8.dp))

        HuggText(
            text = UnitFormatter.getMoneyFormatWithUnit(item.amount),
            style = HuggTypography.p1,
            color = Gs80
        )

        Spacer(modifier = Modifier.size(11.dp))
    }
}