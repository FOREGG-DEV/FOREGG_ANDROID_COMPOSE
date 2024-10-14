package com.hugg.feature.uiItem

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.hugg.domain.model.enums.RecordType
import com.hugg.domain.model.vo.calendar.ScheduleDetailVo
import com.hugg.feature.R
import com.hugg.feature.component.HuggText
import com.hugg.feature.theme.CalendarEtc
import com.hugg.feature.theme.CalendarHospital
import com.hugg.feature.theme.CalendarInjection
import com.hugg.feature.theme.CalendarPill
import com.hugg.feature.theme.Gs10
import com.hugg.feature.theme.Gs70
import com.hugg.feature.theme.Gs90
import com.hugg.feature.theme.HuggTypography
import com.hugg.feature.theme.White
import com.hugg.feature.util.TimeFormatter
import org.threeten.bp.LocalTime
import org.threeten.bp.format.DateTimeFormatter

@Composable
fun ScheduleDetailItem(
    scheduleDetailVo: ScheduleDetailVo = ScheduleDetailVo(),
    isLastItem : Boolean = false,
    onClickEditScheduleBtn : (Long, RecordType) -> Unit = {_, _ -> },
    interactionSource: MutableInteractionSource
){
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = White)
            .clickable(
                onClick = {
                    onClickEditScheduleBtn(
                        scheduleDetailVo.id,
                        scheduleDetailVo.recordType
                    )
                },
                interactionSource = interactionSource,
                indication = null
            )
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(1.dp)
                .background(Gs10)
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 12.dp)
        ) {

            Column {
                val background = when(scheduleDetailVo.recordType){
                    RecordType.MEDICINE -> CalendarPill
                    RecordType.INJECTION -> CalendarInjection
                    RecordType.HOSPITAL -> CalendarHospital
                    RecordType.ETC -> CalendarEtc
                }

                Spacer(modifier = Modifier.size(6.dp))

                Box(
                    modifier = Modifier
                        .size(16.dp)
                        .background(background)
                )
            }

            Spacer(modifier = Modifier.size(8.dp))

            Column(
                modifier = Modifier.weight(1f)
            ) {
                HuggText(
                    text = scheduleDetailVo.name,
                    color = Gs90,
                    style = HuggTypography.h2
                )

                Row {
                    val sortedTimeList = scheduleDetailVo.repeatTimes.sortedBy {
                        LocalTime.parse(it.time, DateTimeFormatter.ofPattern("HH:mm"))
                    }

                    sortedTimeList.forEach {
                        HuggText(
                            text = TimeFormatter.formatTimeToKor(it.time),
                            color = Gs70,
                            style = HuggTypography.p2_l
                        )

                        Spacer(modifier = Modifier.size(4.dp))
                    }
                }

                Spacer(modifier = Modifier.size(2.dp))

                HuggText(
                    text = scheduleDetailVo.memo,
                    color = Gs70,
                    style = HuggTypography.p3,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }

            Column(
                modifier = Modifier
                    .fillMaxHeight(),
                verticalArrangement = Arrangement.Center
            ) {
                Spacer(modifier = Modifier.size(14.dp))

                if(scheduleDetailVo.recordType == RecordType.INJECTION || scheduleDetailVo.recordType == RecordType.MEDICINE) Image(
                    painter = painterResource(if(scheduleDetailVo.vibration) R.drawable.ic_vibration_alarm else R.drawable.ic_push_alarm),
                    contentDescription = null
                )
            }
        }

        if(isLastItem) Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(1.dp)
                .background(Gs10)
        )
    }
}