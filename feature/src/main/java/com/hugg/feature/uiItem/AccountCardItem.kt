package com.hugg.feature.uiItem

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.hugg.domain.model.enums.AccountColorType
import com.hugg.domain.model.vo.account.AccountCardVo
import com.hugg.feature.theme.ACCOUNT_PERSONAL
import com.hugg.feature.theme.ACCOUNT_ROUND_UNIT
import com.hugg.feature.theme.Black
import com.hugg.feature.theme.CalendarEtc
import com.hugg.feature.theme.CalendarHospital
import com.hugg.feature.theme.CalendarInjection
import com.hugg.feature.theme.CalendarPill
import com.hugg.feature.theme.Gs70
import com.hugg.feature.theme.Gs80
import com.hugg.feature.theme.HuggTypography
import com.hugg.feature.theme.White
import com.hugg.feature.util.TimeFormatter
import com.hugg.feature.util.UnitFormatter
import com.hugg.feature.util.UnitFormatter.getMoneyFormat

@Composable
fun AccountCardItem(
    item : AccountCardVo = AccountCardVo()
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
        AccountColorType.RED -> item.title
        AccountColorType.BLUE,
        AccountColorType.GREEN,
        AccountColorType.YELLOW -> UnitFormatter.getSubsidyTitleWithoutMoneyFormat(item.title)
    }

    Column(
        modifier = Modifier
            .padding(bottom = 4.dp, start = 16.dp, end = 16.dp)
            .fillMaxWidth()
            .background(color = White, shape = RoundedCornerShape(8.dp))
            .padding(top = 8.dp, bottom = 11.dp, start = 12.dp, end = 12.dp)
    ) {

        Row {
            Text(
                text = TimeFormatter.getDotsDate(item.date),
                style = HuggTypography.p3_l,
                color = Gs70
            )

            Spacer(modifier = Modifier.size(4.dp))

            Text(
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
                    .width(40.dp)
                    .background(
                        color = boxColor,
                        shape = RoundedCornerShape(4.dp)
                    )
                    .padding(vertical = 2.dp),
                contentAlignment = Alignment.Center
            ){
                Text(
                    text = boxText,
                    style = HuggTypography.p3_l,
                    color = Gs80
                )
            }

            Spacer(modifier = Modifier.size(8.dp))

            Text(
                text = item.title,
                style = HuggTypography.p1,
                color = Black
            )

            Spacer(modifier = Modifier.weight(1f))

            Text(
                text = getMoneyFormat(item.money),
                style = HuggTypography.p1,
                color = Black
            )
        }

    }
}

@Preview
@Composable
fun test(){
    AccountCardItem()
}