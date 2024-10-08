package com.hugg.calendar.scheduleCreateOrEdit

import androidx.compose.foundation.Image
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.hugg.domain.model.enums.CreateOrEditType
import com.hugg.domain.model.enums.RepeatDayType
import com.hugg.feature.R
import com.hugg.feature.component.FilledBtn
import com.hugg.feature.theme.CALENDAR_SCHEDULE_ETC_CONTENT_HINT
import com.hugg.feature.theme.CALENDAR_SCHEDULE_ETC_CONTENT_TITLE
import com.hugg.feature.theme.CALENDAR_SCHEDULE_ETC_TIME_PICKER_TITLE
import com.hugg.feature.theme.Gs50
import com.hugg.feature.theme.Gs80
import com.hugg.feature.theme.Gs90
import com.hugg.feature.theme.MainNormal
import com.hugg.feature.theme.WORD_MODIFY
import com.hugg.feature.theme.WORD_REGISTRATION
import com.hugg.feature.theme.White
import com.hugg.feature.theme.h3

@Composable
fun EtcCreateOrEditScreen(
    uiState: ScheduleCreateOrEditPageState = ScheduleCreateOrEditPageState(),
    interactionSource: MutableInteractionSource,
    onChangedName : (String) -> Unit = {},
    onClickTimePickerBtn : (Int) -> Unit = {},
    onClickDatePickerBtn : (RepeatDayType) -> Unit = {},
    onRepeatBtnChanged : (Boolean) -> Unit = {},
    onChangedMemo : (String) -> Unit = {},
    onClickCreateOrChangeBtn : () -> Unit = {},
    isActiveBtn : Boolean = true,
){
    Column{
        InputEtcContentView(
            content = uiState.name,
            onChangedName = onChangedName
        )

        Spacer(modifier = Modifier.size(32.dp))

        SelectEtcTimePickerView(
            onClickTimePickerBtn = onClickTimePickerBtn,
            time = uiState.repeatTimeList[0].time,
            interactionSource = interactionSource
        )

        Spacer(modifier = Modifier.size(32.dp))

        SelectRepeatDateView(
            date = uiState.date,
            startDate = uiState.startDate,
            endDate = uiState.endDate,
            isRepeat = uiState.isRepeatDay,
            onClickDatePickerBtn = onClickDatePickerBtn,
            onRepeatBtnChanged = onRepeatBtnChanged,
            interactionSource = interactionSource
        )

        Spacer(modifier = Modifier.size(32.dp))

        InputMemoView(
            memo = uiState.memo,
            onChangedMemo = onChangedMemo
        )

        Spacer(modifier = Modifier.weight(1f))

        FilledBtn(
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .fillMaxWidth(),
            onClickBtn = onClickCreateOrChangeBtn,
            isActive = isActiveBtn,
            text = if(uiState.pageType == CreateOrEditType.CREATE) WORD_REGISTRATION else WORD_MODIFY
        )

        Spacer(modifier = Modifier.size(64.dp))
    }
}

@Composable
fun InputEtcContentView(
    content : String = "",
    onChangedName : (String) -> Unit = {},
){
    Text(
        modifier = Modifier.padding(horizontal = 16.dp),
        text = CALENDAR_SCHEDULE_ETC_CONTENT_TITLE,
        style = h3(),
        color = Gs80,
    )

    Spacer(modifier = Modifier.size(4.dp))

    Row(
        verticalAlignment = Alignment.Bottom
    ) {
        Box(
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .fillMaxWidth()
                .background(color = Color.White, shape = RoundedCornerShape(8.dp))
                .padding(horizontal = 12.dp, vertical = 13.dp)
        ) {
            if (content.isEmpty()) {
                Text(
                    text = CALENDAR_SCHEDULE_ETC_CONTENT_HINT,
                    style = h3(),
                    color = Gs50,
                )
            }

            BasicTextField(
                value = content,
                onValueChange = { value ->
                    onChangedName(value)
                },
                textStyle = h3().copy(
                    color = Gs90,
                    textAlign = TextAlign.Start
                ),
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Number
                ),
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

@Composable
internal fun SelectEtcTimePickerView(
    onClickTimePickerBtn : (Int) -> Unit = {},
    time : String = "",
    interactionSource: MutableInteractionSource,
){
    Text(
        modifier = Modifier.padding(horizontal = 16.dp),
        text = CALENDAR_SCHEDULE_ETC_TIME_PICKER_TITLE,
        style = h3(),
        color = Gs80,
    )
    
    Spacer(modifier = Modifier.size(4.dp))

    Row(
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .fillMaxWidth()
            .height(48.dp)
            .background(color = White, shape = RoundedCornerShape(8.dp))
            .clickable(
                onClick = { onClickTimePickerBtn(0) },
                interactionSource = interactionSource,
                indication = null
            ),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(
            horizontalArrangement = Arrangement.Start
        ){
            Box(
                modifier = Modifier
                    .width(48.dp)
                    .height(48.dp)
                    .background(
                        color = MainNormal,
                        shape = RoundedCornerShape(topStart = 8.dp, bottomStart = 8.dp)
                    ),
                contentAlignment = Alignment.Center
            ){
                Image(
                    painter = painterResource(id = R.drawable.ic_clock_white),
                    contentDescription = null
                )
            }
        }

        Spacer(modifier = Modifier.width(16.dp))

        Text(
            color = Gs50,
            style = h3(),
            text = time
        )
    }
}