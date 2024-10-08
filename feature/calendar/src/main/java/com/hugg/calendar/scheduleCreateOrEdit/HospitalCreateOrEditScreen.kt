package com.hugg.calendar.scheduleCreateOrEdit

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
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
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.hugg.domain.model.enums.CreateOrEditType
import com.hugg.domain.model.enums.RecordType
import com.hugg.domain.model.enums.RepeatDayType
import com.hugg.feature.R
import com.hugg.feature.component.FilledBtn
import com.hugg.feature.theme.CALENDAR_SCHEDULE_DATE_AND_TIME_PICKER
import com.hugg.feature.theme.CALENDAR_SCHEDULE_HOSPITAL_MEMO_HINT
import com.hugg.feature.theme.CALENDAR_SCHEDULE_HOSPITAL_TREATMENT_CONTENT
import com.hugg.feature.theme.CALENDAR_SCHEDULE_HOSPITAL_TREATMENT_CONTENT_HINT
import com.hugg.feature.theme.CALENDAR_SCHEDULE_HOSPITAL_TREATMENT_CONTENT_LIST
import com.hugg.feature.theme.Gs50
import com.hugg.feature.theme.Gs70
import com.hugg.feature.theme.Gs80
import com.hugg.feature.theme.Gs90
import com.hugg.feature.theme.MainNormal
import com.hugg.feature.theme.WORD_MEMO
import com.hugg.feature.theme.WORD_MODIFY
import com.hugg.feature.theme.WORD_REGISTRATION
import com.hugg.feature.theme.White
import com.hugg.feature.theme.h3

@Composable
fun HospitalCreateOrEditScreen(
    uiState: ScheduleCreateOrEditPageState = ScheduleCreateOrEditPageState(),
    interactionSource: MutableInteractionSource,
    onClickDropDown : () -> Unit = {},
    onClickKind : (String) -> Unit = {},
    onChangedName : (String) -> Unit = {},
    onClickTimePickerBtn : (Int) -> Unit = {},
    onClickDatePickerBtn : (RepeatDayType) -> Unit = {},
    onChangedMemo : (String) -> Unit = {},
    onClickCreateOrChangeBtn : () -> Unit = {},
    isActiveBtn : Boolean = true,
){
    Column(
        modifier = Modifier.padding(horizontal = 16.dp)
    ) {
        InputTreatmentView(
            kind = uiState.name,
            onClickDropDown = onClickDropDown,
            interactionSource = interactionSource,
            isExpandMenu = uiState.isExpandDropDown,
            onClickKind = onClickKind,
            onChangedKind = onChangedName
        )

        Spacer(modifier = Modifier.size(32.dp))

        SelectDateAndTimeView(
            onClickTimePickerBtn = onClickTimePickerBtn,
            time = uiState.repeatTimeList[0].time,
            interactionSource = interactionSource,
            date = uiState.date,
            onClickDatePickerBtn = onClickDatePickerBtn
        )

        Spacer(modifier = Modifier.size(32.dp))

        InputHospitalMemoView(
            memo = uiState.memo,
            onChangedMemo = onChangedMemo
        )
        
        Spacer(modifier = Modifier.weight(1f))

        FilledBtn(
            modifier = Modifier
                .fillMaxWidth(),
            onClickBtn = onClickCreateOrChangeBtn,
            isActive = isActiveBtn,
            text = if(uiState.pageType == CreateOrEditType.CREATE) WORD_REGISTRATION else WORD_MODIFY
        )
        
        Spacer(modifier = Modifier.size(80.dp))
    }
}

@Composable
internal fun InputTreatmentView(
    kind : String = "",
    onClickDropDown : () -> Unit = {},
    interactionSource : MutableInteractionSource,
    isExpandMenu : Boolean = false,
    onClickKind : (String) -> Unit = {},
    onChangedKind : (String) -> Unit = {},
){
    var rowWidth by remember { mutableIntStateOf(0) }

    Text(
        text = CALENDAR_SCHEDULE_HOSPITAL_TREATMENT_CONTENT,
        style = h3(),
        color = Gs80
    )

    Spacer(modifier = Modifier.size(4.dp))

    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp)
                .background(White, shape = RoundedCornerShape(8.dp))
                .onGloballyPositioned { coordinates ->
                    rowWidth = coordinates.size.width
                },
            verticalAlignment = Alignment.CenterVertically
        ) {

            Box(
                modifier = Modifier
                    .weight(1f)
                    .height(48.dp)
                    .padding(horizontal = 12.dp, vertical = 13.dp)
            ) {

                if (kind.isEmpty()) {
                    Text(
                        text = CALENDAR_SCHEDULE_HOSPITAL_TREATMENT_CONTENT_HINT,
                        style = h3(),
                        color = Gs50,
                    )
                }

                BasicTextField(
                    value = kind,
                    onValueChange = { value ->
                        onChangedKind(value)
                    },
                    textStyle = h3().copy(
                        color = Gs90,
                        textAlign = TextAlign.Start
                    ),
                    singleLine = true,
                )
            }

            Image(
                modifier = Modifier
                    .clickable(
                        onClick = onClickDropDown,
                        interactionSource = interactionSource,
                        indication = null
                    ),
                painter = painterResource(if(isExpandMenu) R.drawable.ic_drop_down_active else R.drawable.ic_drop_down_inactive),
                contentDescription = null
            )
        }

        DropdownMenu(
            expanded = isExpandMenu,
            onDismissRequest = onClickDropDown,
            modifier = Modifier
                .background(White, shape = RoundedCornerShape(8.dp))
                .width(with(LocalDensity.current) { rowWidth.toDp() })
        ) {
            CALENDAR_SCHEDULE_HOSPITAL_TREATMENT_CONTENT_LIST.forEach { kind ->
                DropdownMenuItem(
                    text = {
                        Text(
                            style = h3(),
                            color = Gs70,
                            text = kind
                        )
                    },
                    contentPadding = PaddingValues(horizontal = 12.dp, vertical = 13.dp),
                    onClick = {
                        onClickKind(kind)
                        onClickDropDown()
                    }
                )
            }
        }
    }
}

@Composable
internal fun SelectDateAndTimeView(
    onClickTimePickerBtn : (Int) -> Unit = {},
    time : String = "",
    interactionSource: MutableInteractionSource,
    date : String = "",
    onClickDatePickerBtn : (RepeatDayType) -> Unit = {},
){
    Text(
        text = CALENDAR_SCHEDULE_DATE_AND_TIME_PICKER,
        style = h3(),
        color = Gs80
    )
    
    Spacer(modifier = Modifier.size(4.dp))

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(48.dp)
            .background(color = White, shape = RoundedCornerShape(8.dp))
            .clickable(
                onClick = { onClickDatePickerBtn(RepeatDayType.NORMAL) },
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
                    painter = painterResource(id = R.drawable.ic_calendar_white),
                    contentDescription = null
                )
            }
        }

        Spacer(modifier = Modifier.width(16.dp))

        Text(
            color = Gs50,
            style = h3(),
            text = date
        )
    }

    Spacer(modifier = Modifier.size(8.dp))

    Row(
        modifier = Modifier
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

@Composable
fun InputHospitalMemoView(
    memo : String = "",
    onChangedMemo : (String) -> Unit = {},
){
    Text(
        text = WORD_MEMO,
        style = h3(),
        color = Gs80,
    )

    Spacer(modifier = Modifier.size(4.dp))

    Row(
        verticalAlignment = Alignment.Bottom
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(color = Color.White, shape = RoundedCornerShape(8.dp))
                .padding(horizontal = 12.dp, vertical = 13.dp)
        ) {
            if (memo.isEmpty()) {
                Text(
                    text = CALENDAR_SCHEDULE_HOSPITAL_MEMO_HINT,
                    style = h3(),
                    color = Gs50,
                )
            }

            BasicTextField(
                value = memo,
                onValueChange = { value ->
                    onChangedMemo(value)
                },
                textStyle = h3().copy(
                    color = Gs90,
                    textAlign = TextAlign.Start
                ),
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}
