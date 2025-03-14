package com.hugg.calendar.scheduleCreateOrEdit

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.scrollable
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.dp
import com.hugg.domain.model.enums.CreateOrEditType
import com.hugg.domain.model.enums.RecordType
import com.hugg.domain.model.enums.RepeatDayType
import com.hugg.domain.model.vo.calendar.RepeatTimeVo
import com.hugg.feature.R
import com.hugg.feature.component.FilledBtn
import com.hugg.feature.component.HuggText
import com.hugg.feature.component.HuggTextField
import com.hugg.feature.theme.ACCOUNT_CREATE_CONTENT_HINT
import com.hugg.feature.theme.ACCOUNT_CREATE_DATE_TITLE
import com.hugg.feature.theme.ACCOUNT_ROUND_UNIT_WITHOUT_CAR
import com.hugg.feature.theme.Background
import com.hugg.feature.theme.Black
import com.hugg.feature.theme.CALENDAR_INJECTION_UNIT
import com.hugg.feature.theme.CALENDAR_MEDICINE_UNIT
import com.hugg.feature.theme.CALENDAR_SCHEDULE_ABOUT_MEDICINE
import com.hugg.feature.theme.CALENDAR_SCHEDULE_ALARM_HINT
import com.hugg.feature.theme.CALENDAR_SCHEDULE_DAILY_ADMINISTER_COUNT
import com.hugg.feature.theme.CALENDAR_SCHEDULE_DAILY_INTAKE_COUNT
import com.hugg.feature.theme.CALENDAR_SCHEDULE_DATE_PICK_AND_REPEAT
import com.hugg.feature.theme.CALENDAR_SCHEDULE_DOSE_HINT
import com.hugg.feature.theme.CALENDAR_SCHEDULE_INJECTION_BASIC_DOSE_LIST
import com.hugg.feature.theme.CALENDAR_SCHEDULE_INJECTION_DOSE
import com.hugg.feature.theme.CALENDAR_SCHEDULE_INJECTION_KIND
import com.hugg.feature.theme.CALENDAR_SCHEDULE_INJECTION_KIND_HINT
import com.hugg.feature.theme.CALENDAR_SCHEDULE_INJECTION_KIND_LIST
import com.hugg.feature.theme.CALENDAR_SCHEDULE_MEDICINE_DOSE
import com.hugg.feature.theme.CALENDAR_SCHEDULE_MEDICINE_KIND
import com.hugg.feature.theme.CALENDAR_SCHEDULE_MEDICINE_KIND_HINT
import com.hugg.feature.theme.CALENDAR_SCHEDULE_MEDICINE_KIND_LIST
import com.hugg.feature.theme.CALENDAR_SCHEDULE_MEMO_HINT
import com.hugg.feature.theme.CALENDAR_SCHEDULE_REPEAT_END_DAY_HINT
import com.hugg.feature.theme.CALENDAR_SCHEDULE_REPEAT_EVERYDAY
import com.hugg.feature.theme.CALENDAR_SCHEDULE_REPEAT_EVERYDAY_HINT
import com.hugg.feature.theme.CALENDAR_SCHEDULE_REPEAT_START_DAY_HINT
import com.hugg.feature.theme.Disabled
import com.hugg.feature.theme.Gs10
import com.hugg.feature.theme.Gs20
import com.hugg.feature.theme.Gs50
import com.hugg.feature.theme.Gs60
import com.hugg.feature.theme.Gs70
import com.hugg.feature.theme.Gs80
import com.hugg.feature.theme.Gs90
import com.hugg.feature.theme.HuggTypography
import com.hugg.feature.theme.MainNormal
import com.hugg.feature.theme.WORD_ALARM
import com.hugg.feature.theme.WORD_END
import com.hugg.feature.theme.WORD_MEMO
import com.hugg.feature.theme.WORD_MODIFY
import com.hugg.feature.theme.WORD_REGISTRATION
import com.hugg.feature.theme.WORD_START
import com.hugg.feature.theme.White
import com.hugg.feature.util.ForeggLog
import com.hugg.feature.util.TimeFormatter
import com.hugg.feature.util.onThrottleClick

@Composable
fun InjMedCreateOrEditScreen(
    uiState: ScheduleCreateOrEditPageState = ScheduleCreateOrEditPageState(),
    interactionSource: MutableInteractionSource,
    onClickDropDown : () -> Unit = {},
    onClickKind : (String) -> Unit = {},
    onChangedName : (String) -> Unit = {},
    onChangedDose : (String) -> Unit = {},
    onClickMinusBtn : () -> Unit = {},
    onClickPlusBtn : () -> Unit = {},
    onClickTimePickerBtn : (Int) -> Unit = {},
    onCheckedChange : (Boolean) -> Unit = {},
    onClickDatePickerBtn : (RepeatDayType) -> Unit = {},
    onRepeatBtnChanged : (Boolean) -> Unit = {},
    onChangedMemo : (String) -> Unit = {},
    onClickCreateOrChangeBtn : () -> Unit = {},
    isActiveBtn : Boolean = true,
    showToastNotMine : () -> Unit = {},
) {
    LazyColumn{
        item {
            InputKindView(
                type = uiState.recordType,
                kind = uiState.name,
                onClickDropDown = onClickDropDown,
                interactionSource = interactionSource,
                isExpandMenu = uiState.isExpandDropDown,
                onClickKind = onClickKind,
                onChangedKind = onChangedName,
                isMine = uiState.isMine,
                showToastNotMine = showToastNotMine
            )

            Spacer(modifier = Modifier.size(32.dp))
        }

        item {
            InputDoseView(
                type = uiState.recordType,
                dose = uiState.dose,
                onChangedDose = onChangedDose,
                interactionSource = interactionSource,
                isMine = uiState.isMine,
                showToastNotMine = showToastNotMine
            )

            Spacer(modifier = Modifier.size(32.dp))
        }

        item {
            SelectDailyTakeCount(
                repeatCount = uiState.repeatCount,
                repeatTimeList = uiState.repeatTimeList,
                isChecked = uiState.isAlarmCheck,
                onClickMinusBtn = onClickMinusBtn,
                onClickPlusBtn = onClickPlusBtn,
                onClickTimePickerBtn = onClickTimePickerBtn,
                onCheckedChange = onCheckedChange,
                interactionSource = interactionSource,
                isMine = uiState.isMine,
                showToastNotMine = showToastNotMine
            )

            Spacer(modifier = Modifier.size(32.dp))
        }

        item {
            SelectRepeatDateView(
                date = uiState.date,
                startDate = uiState.startDate,
                endDate = uiState.endDate,
                isRepeat = uiState.isRepeatDay,
                onClickDatePickerBtn = onClickDatePickerBtn,
                onRepeatBtnChanged = onRepeatBtnChanged,
                interactionSource = interactionSource,
                isMine = uiState.isMine,
                showToastNotMine = showToastNotMine
            )

            Spacer(modifier = Modifier.size(32.dp))
        }

        item {
            InputMemoView(
                memo = uiState.memo,
                onChangedMemo = onChangedMemo,
                isMine = uiState.isMine,
                showToastNotMine = showToastNotMine,
                interactionSource = interactionSource
            )

            Spacer(modifier = Modifier.size(24.dp))
        }

        item {
            if(uiState.isMine) FilledBtn(
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .fillMaxWidth(),
                onClickBtn = onClickCreateOrChangeBtn,
                isActive = isActiveBtn,
                text = if(uiState.pageType == CreateOrEditType.CREATE) WORD_REGISTRATION else WORD_MODIFY
            )

            Spacer(modifier = Modifier.size(36.dp))
        }
    }
}

@Composable
internal fun InputKindView(
    type : RecordType = RecordType.INJECTION,
    kind : String = "",
    onClickDropDown : () -> Unit = {},
    interactionSource : MutableInteractionSource,
    isExpandMenu : Boolean = false,
    onClickKind : (String) -> Unit = {},
    onChangedKind : (String) -> Unit = {},
    isMine : Boolean = true,
    showToastNotMine : () -> Unit = {},
){
    val text = if(type == RecordType.INJECTION) CALENDAR_SCHEDULE_INJECTION_KIND else CALENDAR_SCHEDULE_MEDICINE_KIND
    val kindList = if(type == RecordType.INJECTION) CALENDAR_SCHEDULE_INJECTION_KIND_LIST else CALENDAR_SCHEDULE_MEDICINE_KIND_LIST
    val hint = if(type == RecordType.INJECTION) CALENDAR_SCHEDULE_INJECTION_KIND_HINT else CALENDAR_SCHEDULE_MEDICINE_KIND_HINT
    var rowWidth by remember { mutableIntStateOf(0) }

    HuggText(
        modifier = Modifier.padding(start = 16.dp),
        text = text,
        style = HuggTypography.h3,
        color = Gs80
    )

    Spacer(modifier = Modifier.size(4.dp))

    Column(
        modifier = Modifier.padding(horizontal = 16.dp),
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
                    HuggText(
                        text = hint,
                        style = HuggTypography.h3,
                        color = Gs50,
                    )
                }

                HuggTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .onThrottleClick(
                            onClick = { if(!isMine) showToastNotMine() },
                            interactionSource = interactionSource
                        ),
                    value = kind,
                    onValueChange = { value ->
                        onChangedKind(value)
                    },
                    enabled = isMine,
                    textStyle = HuggTypography.h3.copy(
                        color = Gs90,
                        textAlign = TextAlign.Start
                    ),
                    singleLine = true,
                )
            }

            Image(
                modifier = Modifier
                    .clickable(
                        onClick = { if(!isMine) showToastNotMine() else onClickDropDown() },
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
                .height(336.dp)
        ) {
            kindList.forEach { kind ->
                DropdownMenuItem(
                    text = {
                        HuggText(
                            style = HuggTypography.h3,
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
internal fun InputDoseView(
    type: RecordType = RecordType.INJECTION,
    dose : String = "",
    onChangedDose : (String) -> Unit = {},
    interactionSource: MutableInteractionSource,
    isMine : Boolean = true,
    showToastNotMine : () -> Unit = {},
){
    val title = if(type == RecordType.INJECTION) CALENDAR_SCHEDULE_INJECTION_DOSE else CALENDAR_SCHEDULE_MEDICINE_DOSE
    val doseUnit = if(type == RecordType.INJECTION) CALENDAR_INJECTION_UNIT else CALENDAR_MEDICINE_UNIT

    HuggText(
        modifier = Modifier.padding(start = 16.dp),
        text = title,
        style = HuggTypography.h3,
        color = Gs80
    )

    Spacer(modifier = Modifier.size(4.dp))

    Row(
        modifier = Modifier.padding(start = 16.dp),
        verticalAlignment = Alignment.Bottom
    ) {
        Box(
            modifier = Modifier
                .width(168.dp)
                .background(color = Color.White, shape = RoundedCornerShape(8.dp))
                .padding(horizontal = 12.dp, vertical = 13.dp)
        ) {
            if (dose.isEmpty()) {
                HuggText(
                    text = CALENDAR_SCHEDULE_DOSE_HINT,
                    style = HuggTypography.h3,
                    color = Gs50,
                )
            }

            HuggTextField(
                value = dose,
                onValueChange = { value ->
                    onChangedDose(value)
                },
                textStyle = HuggTypography.h3.copy(
                    color = Gs90,
                    textAlign = TextAlign.Start
                ),
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Number
                ),
                enabled = isMine,
                singleLine = true,
                modifier = Modifier
                    .fillMaxWidth()
                    .onThrottleClick(
                        onClick = { if(!isMine) showToastNotMine() },
                        interactionSource = interactionSource
                    ),
            )
        }

        Spacer(modifier = Modifier.size(8.dp))

        HuggText(
            text = doseUnit,
            style = HuggTypography.h3,
            color = Gs70
        )
    }

    if(type == RecordType.INJECTION) {
        Spacer(modifier = Modifier.size(8.dp))
        LazyRow(
            modifier = Modifier
                .padding(start = 16.dp, end = 16.dp)
        ) {
            items(CALENDAR_SCHEDULE_INJECTION_BASIC_DOSE_LIST){
                InjectionDoseChipView(
                    dose = dose,
                    basicDose = it,
                    onClickDoseChip = onChangedDose,
                    interactionSource = interactionSource,
                    isMine = isMine,
                    showToastNotMine = showToastNotMine
                )

                Spacer(modifier = Modifier.size(8.dp))
            }
        }
    }
}

@Composable
internal fun InjectionDoseChipView(
    dose : String = "",
    basicDose : String = "",
    onClickDoseChip : (String) -> Unit = {},
    interactionSource: MutableInteractionSource,
    isMine : Boolean = true,
    showToastNotMine : () -> Unit = {},
){
    Box(
        modifier = Modifier
            .size(width = 60.dp, height = 32.dp)
            .background(
                color = if (dose == basicDose) Gs70 else Gs10,
                shape = RoundedCornerShape(999.dp)
            )
            .clickable(
                onClick = {
                    if(!isMine) {
                        showToastNotMine()
                        return@clickable
                    }
                    if (dose == basicDose) onClickDoseChip("") else onClickDoseChip(basicDose)
                },
                interactionSource = interactionSource,
                indication = null,
            ),
        contentAlignment = Alignment.Center
    ){
        HuggText(
            text = basicDose,
            style = HuggTypography.h4,
            color = if(dose == basicDose) White else Gs80
        )
    }
}

@Composable
internal fun SelectDailyTakeCount(
    type: RecordType = RecordType.INJECTION,
    repeatCount : Int = 1,
    repeatTimeList : List<RepeatTimeVo> = emptyList(),
    onClickMinusBtn : () -> Unit = {},
    onClickPlusBtn : () -> Unit = {},
    onClickTimePickerBtn : (Int) -> Unit = {},
    isChecked : Boolean = true,
    onCheckedChange : (Boolean) -> Unit = {},
    interactionSource: MutableInteractionSource,
    isMine : Boolean = true,
    showToastNotMine : () -> Unit = {},
){
    val title = if(type == RecordType.INJECTION) CALENDAR_SCHEDULE_DAILY_ADMINISTER_COUNT else CALENDAR_SCHEDULE_DAILY_INTAKE_COUNT

    HuggText(
        modifier = Modifier.padding(horizontal = 16.dp),
        color = Gs80,
        style = HuggTypography.h3,
        text = title
    )

    Spacer(modifier = Modifier.size(4.dp))

    Row(
        modifier = Modifier.padding(horizontal = 16.dp),
        verticalAlignment = Alignment.Bottom
    ) {
        Row(
            modifier = Modifier
                .width(168.dp)
                .height(48.dp)
                .background(color = White, shape = RoundedCornerShape(8.dp)),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                modifier = Modifier
                    .weight(1f),
                horizontalArrangement = Arrangement.Start
            ){
                Box(
                    modifier = Modifier
                        .width(51.dp)
                        .height(48.dp)
                        .background(
                            color = if (repeatCount == 1) Disabled else Gs10,
                            shape = RoundedCornerShape(topStart = 8.dp, bottomStart = 8.dp)
                        )
                        .clickable(
                            onClick = if(!isMine) showToastNotMine else onClickMinusBtn,
                            interactionSource = interactionSource,
                            indication = null,
                        ),
                    contentAlignment = Alignment.Center
                ){
                    Image(
                        painter = painterResource(id = if(repeatCount == 1) R.drawable.ic_minus_white else R.drawable.ic_minus_gs_70),
                        contentDescription = null
                    )
                }
            }

            HuggText(
                color = Black,
                style = HuggTypography.h3,
                text = repeatCount.toString()
            )

            Row(
                modifier = Modifier
                    .weight(1f),
                horizontalArrangement = Arrangement.End
            ) {
                Box(
                    modifier = Modifier
                        .width(51.dp)
                        .height(48.dp)
                        .background(
                            color = Gs10,
                            shape = RoundedCornerShape(topEnd = 8.dp, bottomEnd = 8.dp)
                        )
                        .clickable(
                            onClick = if(!isMine) showToastNotMine else onClickPlusBtn,
                            interactionSource = interactionSource,
                            indication = null,
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        modifier = Modifier.size(14.dp),
                        painter = painterResource(id = R.drawable.ic_plus_gs_70),
                        contentDescription = null
                    )
                }
            }
        }

        Spacer(modifier = Modifier.width(8.dp))

        HuggText(
            style = HuggTypography.h3,
            color = Gs70,
            text = ACCOUNT_ROUND_UNIT_WITHOUT_CAR
        )
    }

    Spacer(modifier = Modifier.size(8.dp))

    repeat(repeatCount){ index ->
        TimePickerView(
            onClickTimePickerBtn = { num -> if(!isMine) showToastNotMine() else onClickTimePickerBtn(num) },
            time = repeatTimeList[index].time,
            index = index,
            interactionSource = interactionSource
        )

        Spacer(modifier = Modifier.size(8.dp))
    }

    SelectAlarmOnAndOffView(
        isChecked = isChecked,
        onCheckedChange = { checked -> if(!isMine) showToastNotMine() else onCheckedChange(checked) }
    )
}

@Composable
internal fun TimePickerView(
    onClickTimePickerBtn : (Int) -> Unit = {},
    time : String = "",
    index : Int = 0,
    interactionSource: MutableInteractionSource,
){
    Row(
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .fillMaxWidth()
            .height(48.dp)
            .background(color = White, shape = RoundedCornerShape(8.dp))
            .clickable(
                onClick = { onClickTimePickerBtn(index) },
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

        HuggText(
            color = Gs50,
            style = HuggTypography.h3,
            text = time
        )
    }
}

@Composable
internal fun SelectAlarmOnAndOffView(
    isChecked : Boolean = true,
    onCheckedChange : (Boolean) -> Unit = {},
){
    Column(
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .fillMaxWidth()
            .background(color = White, RoundedCornerShape(8.dp))
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            HuggText(
                color = Gs80,
                style = HuggTypography.h2,
                text = WORD_ALARM
            )

            Spacer(modifier = Modifier.weight(1f))

            Switch(
                modifier = Modifier
                    .size(width = 49.dp, height = 28.dp),
                checked = isChecked,
                onCheckedChange = onCheckedChange,
                colors = SwitchDefaults.colors(
                    checkedThumbColor = White,
                    checkedTrackColor = MainNormal,
                    checkedBorderColor = MainNormal,
                    uncheckedThumbColor = White,
                    uncheckedTrackColor = Gs20,
                    uncheckedBorderColor = Gs20,
                ),
                thumbContent = {}
            )
        }

        Spacer(modifier = Modifier.size(4.dp))

        HuggText(
            color = Gs60,
            style = HuggTypography.p3_l,
            text = CALENDAR_SCHEDULE_ALARM_HINT
        )
    }
}

@Composable
internal fun SelectRepeatDateView(
    onClickDatePickerBtn : (RepeatDayType) -> Unit = {},
    date : String = "",
    startDate : String = "",
    endDate : String = "",
    isRepeat : Boolean = false,
    onRepeatBtnChanged : (Boolean) -> Unit = {},
    interactionSource: MutableInteractionSource,
    isMine : Boolean = true,
    showToastNotMine : () -> Unit = {},
){
    HuggText(
        modifier = Modifier.padding(start = 16.dp),
        text = CALENDAR_SCHEDULE_DATE_PICK_AND_REPEAT,
        style = HuggTypography.h3,
        color = Gs80
    )

    Spacer(modifier = Modifier.size(4.dp))

    DatePickerView(
        date = if(isRepeat) startDate else date,
        interactionSource = interactionSource,
        onClickDatePickerBtn = { repeatDayType ->  if(!isMine) showToastNotMine() else onClickDatePickerBtn(repeatDayType) },
        repeatDayType = if(isRepeat) RepeatDayType.START else RepeatDayType.NORMAL
    )

    if(isRepeat) {
        Spacer(modifier = Modifier.size(8.dp))
        DatePickerView(
            date = endDate,
            interactionSource = interactionSource,
            onClickDatePickerBtn = { repeatDayType ->  if(!isMine) showToastNotMine() else onClickDatePickerBtn(repeatDayType) },
            repeatDayType = RepeatDayType.END
        )
    }

    Spacer(modifier = Modifier.size(8.dp))

    SelectRepeatEveryDayView(
        isChecked = isRepeat,
        onCheckedChange = { checked -> if(!isMine) showToastNotMine() else onRepeatBtnChanged(checked) }
    )
}

@Composable
internal fun DatePickerView(
    date : String = "",
    interactionSource: MutableInteractionSource,
    onClickDatePickerBtn : (RepeatDayType) -> Unit = {},
    repeatDayType : RepeatDayType = RepeatDayType.NORMAL
){
    Row(
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .fillMaxWidth()
            .height(48.dp)
            .background(color = White, shape = RoundedCornerShape(8.dp))
            .clickable(
                onClick = { onClickDatePickerBtn(repeatDayType) },
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

        val hintText = when(repeatDayType){
            RepeatDayType.NORMAL -> ""
            RepeatDayType.START -> CALENDAR_SCHEDULE_REPEAT_START_DAY_HINT
            RepeatDayType.END -> CALENDAR_SCHEDULE_REPEAT_END_DAY_HINT
        }

        HuggText(
            color = Gs50,
            style = HuggTypography.h3,
            text = if(date.isEmpty()) hintText else date + " ${TimeFormatter.getKoreanFullDayOfWeek(date)}"
        )

        Spacer(modifier = Modifier.weight(1f))

        val endText = when(repeatDayType){
            RepeatDayType.NORMAL -> ""
            RepeatDayType.START -> WORD_START
            RepeatDayType.END -> WORD_END
        }

        HuggText(
            color = Gs50,
            style = HuggTypography.h3,
            text = endText
        )

        Spacer(modifier = Modifier.width(16.dp))
    }
}

@Composable
internal fun SelectRepeatEveryDayView(
    isChecked : Boolean = true,
    onCheckedChange : (Boolean) -> Unit = {},
){
    Column(
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .fillMaxWidth()
            .background(color = White, RoundedCornerShape(8.dp))
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            HuggText(
                color = Gs80,
                style = HuggTypography.h2,
                text = CALENDAR_SCHEDULE_REPEAT_EVERYDAY
            )

            Spacer(modifier = Modifier.weight(1f))

            Switch(
                modifier = Modifier
                    .size(width = 49.dp, height = 28.dp),
                checked = isChecked,
                onCheckedChange = onCheckedChange,
                colors = SwitchDefaults.colors(
                    checkedThumbColor = White,
                    checkedTrackColor = MainNormal,
                    checkedBorderColor = MainNormal,
                    uncheckedThumbColor = White,
                    uncheckedTrackColor = Gs20,
                    uncheckedBorderColor = Gs20,
                ),
                thumbContent = {}
            )
        }

        Spacer(modifier = Modifier.size(4.dp))

        HuggText(
            color = Gs60,
            style = HuggTypography.p3_l,
            text = CALENDAR_SCHEDULE_REPEAT_EVERYDAY_HINT
        )
    }
}

@Composable
fun InputMemoView(
    memo : String = "",
    onChangedMemo : (String) -> Unit = {},
    isMine : Boolean = true,
    showToastNotMine : () -> Unit = {},
    interactionSource: MutableInteractionSource
){
    HuggText(
        modifier = Modifier.padding(start = 16.dp),
        text = WORD_MEMO,
        style = HuggTypography.h3,
        color = Gs80,
    )

    Spacer(modifier = Modifier.size(4.dp))

    Row(
        modifier = Modifier.padding(horizontal = 16.dp),
        verticalAlignment = Alignment.Bottom
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(color = Color.White, shape = RoundedCornerShape(8.dp))
                .padding(horizontal = 12.dp, vertical = 13.dp)
        ) {
            if (memo.isEmpty()) {
                HuggText(
                    text = CALENDAR_SCHEDULE_MEMO_HINT,
                    style = HuggTypography.h3,
                    color = Gs50,
                )
            }

            HuggTextField(
                value = memo,
                onValueChange = { value ->
                    onChangedMemo(value)
                },
                textStyle = HuggTypography.h3.copy(
                    color = Gs90,
                    textAlign = TextAlign.Start
                ),
                enabled = isMine,
                singleLine = true,
                modifier = Modifier
                    .fillMaxWidth()
                    .onThrottleClick(
                        onClick = { if(!isMine) showToastNotMine() },
                        interactionSource = interactionSource
                    ),
            )
        }
    }
}