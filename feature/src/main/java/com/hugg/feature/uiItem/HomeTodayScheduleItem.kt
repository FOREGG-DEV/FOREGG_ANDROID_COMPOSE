package com.hugg.feature.uiItem

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.hugg.domain.model.enums.RecordType
import com.hugg.domain.model.vo.home.HomeTodayScheduleCardVo
import com.hugg.feature.R
import com.hugg.feature.component.FilledBtn
import com.hugg.feature.component.HuggText
import com.hugg.feature.theme.CalendarEtc
import com.hugg.feature.theme.CalendarHospital
import com.hugg.feature.theme.CalendarInjection
import com.hugg.feature.theme.CalendarPill
import com.hugg.feature.theme.Gs50
import com.hugg.feature.theme.Gs70
import com.hugg.feature.theme.Gs80
import com.hugg.feature.theme.HOME_RECORD_MEDICAL
import com.hugg.feature.theme.HuggTypography
import com.hugg.feature.theme.MainNormal
import com.hugg.feature.theme.WORD_ETC
import com.hugg.feature.theme.WORD_HOSPITAL
import com.hugg.feature.theme.WORD_INJECTION
import com.hugg.feature.theme.WORD_MEDICINE
import com.hugg.feature.theme.White
import com.hugg.feature.util.onThrottleClick

@Composable
fun HomeTodayScheduleItem(
    item : HomeTodayScheduleCardVo = HomeTodayScheduleCardVo(),
    onClickTodo : (Long) -> Unit = {},
    onClickDetail : (Long) -> Unit = {},
    interactionSource: MutableInteractionSource
){
    val boxColor = when(item.recordType){
        RecordType.MEDICINE -> CalendarPill
        RecordType.INJECTION -> CalendarInjection
        RecordType.HOSPITAL -> CalendarHospital
        RecordType.ETC -> CalendarEtc
    }
    val boxText = when(item.recordType){
        RecordType.MEDICINE -> WORD_MEDICINE
        RecordType.INJECTION -> WORD_INJECTION
        RecordType.HOSPITAL -> WORD_HOSPITAL
        RecordType.ETC -> WORD_ETC
    }

    Column(
        modifier = Modifier
            .size(width = 285.dp, height = 136.dp)
            .background(color = White, shape = RoundedCornerShape(6.dp))
            .border(
                width = 1.dp,
                color = if (item.isNearlyNowTime) MainNormal else White,
                shape = RoundedCornerShape(6.dp)
            )
            .padding(start = 12.dp, bottom = 12.dp)
    ) {

        Row(
            verticalAlignment = Alignment.Top
        ) {
            Column(
                modifier = Modifier
                    .onThrottleClick(
                        onClick = { onClickTodo(item.id) },
                        interactionSource = interactionSource
                    )
            ){
                Box(
                    modifier = Modifier
                        .padding(top = 12.dp)
                        .size(width = 39.dp, height = 20.dp)
                        .background(color = boxColor, shape = RoundedCornerShape(4.dp)),
                    contentAlignment = Alignment.Center
                ){
                    HuggText(
                        text = boxText,
                        style = HuggTypography.p3,
                        color = Gs80
                    )
                }

                Spacer(modifier = Modifier.size(4.dp))

                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        modifier = Modifier.padding(start = 1.dp),
                        painter = painterResource(id = if(item.todo) R.drawable.ic_checked_box_gs_50 else R.drawable.ic_unchecked_box_gs_50),
                        contentDescription = null
                    )

                    Spacer(modifier = Modifier.size(4.dp))

                    HuggText(
                        text = item.time,
                        style = HuggTypography.h1,
                        color = if(item.todo) Gs50 else Gs80,
                        textDecoration = if(item.todo) TextDecoration.LineThrough else null
                    )
                }

                HuggText(
                    text = item.name,
                    style = HuggTypography.p2,
                    color = if(item.todo) Gs50 else Gs80,
                    textDecoration = if(item.todo) TextDecoration.LineThrough else null
                )
            }

            Spacer(modifier = Modifier.weight(1f))

            Image(
                modifier = Modifier
                    .size(48.dp)
                    .padding(12.dp)
                    .onThrottleClick(
                        onClick = { onClickDetail(item.id) },
                        interactionSource = interactionSource
                    ),
                painter = painterResource(id = R.drawable.ic_right_arrow_navigate_main_normal),
                contentDescription = null
            )
        }

        if(item.recordType == RecordType.HOSPITAL){
            Spacer(modifier = Modifier.weight(1f))

            FilledBtn(
                modifier = Modifier
                    .padding(end = 12.dp)
                    .height(29.dp)
                    .fillMaxWidth(),
                onClickBtn = { onClickDetail(item.id) },
                radius = 4.dp,
                text = HOME_RECORD_MEDICAL,
                textStyle = HuggTypography.p2,
                contentPadding = PaddingValues(vertical = 5.dp)
            )
        }
        else{
            Spacer(modifier = Modifier.size(3.dp))

            HuggText(
                modifier = Modifier.padding(end = 12.dp),
                text = item.memo,
                maxLines = 2,
                style = HuggTypography.p3_l,
                color = Gs70,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}