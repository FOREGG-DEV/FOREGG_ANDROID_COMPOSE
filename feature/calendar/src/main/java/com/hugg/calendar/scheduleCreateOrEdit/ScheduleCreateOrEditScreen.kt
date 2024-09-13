package com.hugg.calendar.scheduleCreateOrEdit

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
import com.hugg.domain.model.enums.TopBarLeftType
import com.hugg.domain.model.enums.TopBarMiddleType
import com.hugg.feature.component.TopBar
import com.hugg.feature.theme.*
import com.hugg.feature.util.TimeFormatter
import java.util.Calendar

@Composable
fun ScheduleCreateOrEditContainer(
    goToBack : () -> Unit = {},
    pageType : CreateOrEditType = CreateOrEditType.CREATE,
    recordType: RecordType = RecordType.ETC,
    id : Long = -1,
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

    LaunchedEffect(Unit){
        viewModel.initView(pageType, recordType, id)
    }


    ScheduleCreateOrEditScreen(
        uiState = uiState,
        onClickTopBarLeftBtn = goToBack,
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
        onCheckedChange = { checked -> viewModel.onCheckedChange(checked) }
    )
}

@Composable
fun ScheduleCreateOrEditScreen(
    uiState : ScheduleCreateOrEditPageState = ScheduleCreateOrEditPageState(),
    onClickTopBarLeftBtn : () -> Unit = {},
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    onClickDropDown : () -> Unit = {},
    onClickKind : (String) -> Unit = {},
    onChangedName : (String) -> Unit = {},
    onChangedDose : (String) -> Unit = {},
    onClickMinusBtn : () -> Unit = {},
    onClickPlusBtn : () -> Unit = {},
    onClickTimePickerBtn : (Int) -> Unit = {},
    onCheckedChange : (Boolean) -> Unit = {},
) {
    val topBarText = when(uiState.recordType) {
        RecordType.MEDICINE -> CALENDAR_SCHEDULE_ABOUT_MEDICINE
        RecordType.INJECTION -> CALENDAR_SCHEDULE_ABOUT_INJECTION
        RecordType.HOSPITAL -> CALENDAR_SCHEDULE_ABOUT_HOSPITAL
        RecordType.ETC -> CALENDAR_SCHEDULE_ABOUT_ETC
    } + if(uiState.pageType == CreateOrEditType.CREATE) " $WORD_ADD" else " $WORD_MODIFY"

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Background),
    ) {

        TopBar(
            leftItemType = TopBarLeftType.BACK,
            leftBtnClicked = onClickTopBarLeftBtn,
            middleItemType = TopBarMiddleType.TEXT,
            middleText = topBarText
        )

        Spacer(modifier = Modifier.size(24.dp))

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
            onCheckedChange = onCheckedChange
        )
    }
}

@Preview
@Composable
internal fun PreviewMainContainer() {
    ScheduleCreateOrEditScreen()
}