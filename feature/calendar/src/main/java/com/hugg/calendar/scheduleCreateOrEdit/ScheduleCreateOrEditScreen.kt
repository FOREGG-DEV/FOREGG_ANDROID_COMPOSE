package com.hugg.calendar.scheduleCreateOrEdit

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.hugg.feature.R
import com.hugg.domain.model.enums.CreateOrEditType
import com.hugg.domain.model.enums.RecordType
import com.hugg.domain.model.enums.RepeatDayType
import com.hugg.domain.model.enums.TopBarLeftType
import com.hugg.domain.model.enums.TopBarMiddleType
import com.hugg.domain.model.enums.TopBarRightType
import com.hugg.feature.component.HuggDialog
import com.hugg.feature.component.TopBar
import com.hugg.feature.theme.*
import com.hugg.feature.util.HuggToast
import com.hugg.feature.util.TimeFormatter
import com.hugg.feature.util.checkAlreadyBatteryOptimization
import com.hugg.feature.util.openBatteryOptimizationSettings
import java.util.Calendar

@Composable
fun ScheduleCreateOrEditContainer(
    goToBack : () -> Unit = {},
    pageType : CreateOrEditType = CreateOrEditType.CREATE,
    recordType: RecordType = RecordType.ETC,
    id : Long = -1,
    selectDate : String = "",
    viewModel: ScheduleCreateOrEditViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val context = LocalContext.current
    val interactionSource = remember { MutableInteractionSource() }

    val calendar = Calendar.getInstance()
    val timePickerListener = TimePickerDialog.OnTimeSetListener { _, hour, minute ->
        viewModel.setRepeatTimeList(TimeFormatter.getTimeFormatByKor(hour, minute))
    }
    val timePickerDialog = TimePickerDialog(context,
        R.style.DatePickerStyle,
        timePickerListener,
        calendar.get(Calendar.HOUR_OF_DAY),
        calendar.get(Calendar.MINUTE),
        false
    )

    val datePickerDialog = remember {
        DatePickerDialog(
            context,
            R.style.DatePickerStyle,
            { _, year, month, day ->
                viewModel.setDate(TimeFormatter.getDatePickerDashDate(year, month, day))
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        )
    }

    LaunchedEffect(Unit){
        viewModel.initView(pageType, recordType, id, selectDate)
        if(!checkAlreadyBatteryOptimization(context)) {
            viewModel.updateAlarmChecked(false)
            openBatteryOptimizationSettings(context)
        }
    }

    LaunchedEffect(Unit) {
        viewModel.eventFlow.collect { event ->
            when(event) {
                ScheduleCreateOrEditEvent.SuccessCreateScheduleEvent -> {
                    HuggToast.createToast(context, CALENDAR_TOAST_SUCCESS_CREATE).show()
                    goToBack()
                }
                ScheduleCreateOrEditEvent.SuccessModifyScheduleEvent -> {
                    HuggToast.createToast(context, CALENDAR_TOAST_SUCCESS_EDIT).show()
                    goToBack()
                }
                ScheduleCreateOrEditEvent.SuccessDeleteScheduleEvent -> {
                    HuggToast.createToast(context, CALENDAR_TOAST_SUCCESS_DELETE).show()
                    goToBack()
                }
            }
        }
    }


    ScheduleCreateOrEditScreen(
        uiState = uiState,
        recordType = recordType,
        pageType = pageType,
        onClickTopBarLeftBtn = goToBack,
        onClickTopBarRightBtn = { viewModel.showDeleteDialog(true) },
        interactionSource = interactionSource,
        onClickDropDown = { viewModel.showOrCancelDropDown() },
        onClickKind = { kind -> viewModel.onChangedName(kind)},
        onChangedName = { name -> viewModel.onChangedName(name)},
        onChangedDose = { dose -> viewModel.onChangedDose(dose) },
        onClickMinusBtn = { viewModel.onClickMinusBtn() },
        onClickPlusBtn = { viewModel.onClickPlusBtn() },
        onClickTimePickerBtn = { index ->
            viewModel.setTouchedTimePicker(index)
            timePickerDialog.show()
        },
        onCheckedChange = { checked ->
            if(!checkAlreadyBatteryOptimization(context) && checked) openBatteryOptimizationSettings(context)
            else viewModel.updateAlarmChecked(checked)
        },
        onClickDatePickerBtn = { repeatType ->
            viewModel.setTouchedDatePicker(repeatType)
            datePickerDialog.show()
                               },
        onRepeatBtnChanged = { checked -> viewModel.onRepeatChange(checked) },
        onChangedMemo = { memo -> viewModel.onChangedMemo(memo) },
        onClickCreateOrChangeBtn = { viewModel.onClickCreateOrEdit() },
        showToastNotMine = { HuggToast.createToast(context, CALENDAR_TOAST_NOT_MINE).show() }
    )

    if(uiState.showDeleteDialog) {
        HuggDialog(
            title = CALENDAR_SCHEDULE_DIALOG_DELETE,
            positiveColor = Sunday,
            positiveText = WORD_DELETE,
            onClickCancel = { viewModel.showDeleteDialog(false) },
            onClickNegative = { viewModel.showDeleteDialog(false) },
            onClickPositive = { viewModel.onDeleteSchedule() },
        )
    }
}

@Composable
fun ScheduleCreateOrEditScreen(
    uiState : ScheduleCreateOrEditPageState = ScheduleCreateOrEditPageState(),
    recordType: RecordType = RecordType.ETC,
    pageType : CreateOrEditType = CreateOrEditType.CREATE,
    onClickTopBarLeftBtn : () -> Unit = {},
    onClickTopBarRightBtn : () -> Unit = {},
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
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
    showToastNotMine : () -> Unit = {},
) {
    val topBarText = when(recordType) {
        RecordType.MEDICINE -> CALENDAR_SCHEDULE_ABOUT_MEDICINE
        RecordType.INJECTION -> CALENDAR_SCHEDULE_ABOUT_INJECTION
        RecordType.HOSPITAL -> CALENDAR_SCHEDULE_ABOUT_HOSPITAL
        RecordType.ETC -> CALENDAR_SCHEDULE_ABOUT_ETC
    } + if(pageType == CreateOrEditType.CREATE) " $WORD_ADD" else " $WORD_MODIFY"

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Background),
    ) {

        TopBar(
            leftItemType = TopBarLeftType.BACK,
            leftBtnClicked = onClickTopBarLeftBtn,
            middleItemType = TopBarMiddleType.TEXT,
            middleText = topBarText,
            rightItemType = if(uiState.isMine && pageType == CreateOrEditType.EDIT) TopBarRightType.DELETE_GS30 else TopBarRightType.NONE,
            rightBtnClicked = onClickTopBarRightBtn
        )

        Spacer(modifier = Modifier.size(24.dp))

        when(recordType) {
            RecordType.MEDICINE,
            RecordType.INJECTION ->{
                InjMedCreateOrEditScreen(
                    uiState = uiState,
                    interactionSource = interactionSource,
                    onClickDropDown = onClickDropDown,
                    onClickKind = onClickKind,
                    onChangedName = onChangedName,
                    onChangedDose = onChangedDose,
                    onClickMinusBtn = onClickMinusBtn,
                    onClickPlusBtn = onClickPlusBtn,
                    onClickTimePickerBtn = onClickTimePickerBtn,
                    onCheckedChange = onCheckedChange,
                    onClickDatePickerBtn = onClickDatePickerBtn,
                    onRepeatBtnChanged = onRepeatBtnChanged,
                    onChangedMemo = onChangedMemo,
                    onClickCreateOrChangeBtn = onClickCreateOrChangeBtn,
                    isActiveBtn = uiState.isActiveBtn,
                    showToastNotMine = showToastNotMine
                )
            }
            RecordType.HOSPITAL ->{
                HospitalCreateOrEditScreen(
                    uiState = uiState,
                    interactionSource = interactionSource,
                    onClickDropDown = onClickDropDown,
                    onClickKind = onClickKind,
                    onChangedName = onChangedName,
                    onClickTimePickerBtn = onClickTimePickerBtn,
                    onClickDatePickerBtn = onClickDatePickerBtn,
                    onChangedMemo = onChangedMemo,
                    onClickCreateOrChangeBtn = onClickCreateOrChangeBtn,
                    isActiveBtn = uiState.isActiveBtn,
                    showToastNotMine = showToastNotMine
                )
            }
            RecordType.ETC -> {
                EtcCreateOrEditScreen(
                    uiState = uiState,
                    interactionSource = interactionSource,
                    onChangedName = onChangedName,
                    onClickTimePickerBtn = onClickTimePickerBtn,
                    onClickDatePickerBtn = onClickDatePickerBtn,
                    onRepeatBtnChanged = onRepeatBtnChanged,
                    onChangedMemo = onChangedMemo,
                    onClickCreateOrChangeBtn = onClickCreateOrChangeBtn,
                    isActiveBtn = uiState.isActiveBtn,
                    showToastNotMine = showToastNotMine
                )
            }
        }
    }
}

@Preview
@Composable
internal fun PreviewMainContainer() {
    ScheduleCreateOrEditScreen()
}