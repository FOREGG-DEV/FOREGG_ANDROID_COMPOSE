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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import androidx.compose.ui.unit.dp
import com.hugg.domain.model.enums.RecordType
import com.hugg.feature.R
import com.hugg.feature.theme.ACCOUNT_CREATE_CONTENT_HINT
import com.hugg.feature.theme.Background
import com.hugg.feature.theme.CALENDAR_INJECTION_UNIT
import com.hugg.feature.theme.CALENDAR_MEDICINE_UNIT
import com.hugg.feature.theme.CALENDAR_SCHEDULE_ABOUT_MEDICINE
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
import com.hugg.feature.theme.Gs10
import com.hugg.feature.theme.Gs50
import com.hugg.feature.theme.Gs70
import com.hugg.feature.theme.Gs80
import com.hugg.feature.theme.Gs90
import com.hugg.feature.theme.HuggTypography
import com.hugg.feature.theme.White

@Composable
fun InjMedCreateOrEditScreen(
    uiState: ScheduleCreateOrEditPageState = ScheduleCreateOrEditPageState(),
    interactionSource: MutableInteractionSource,
    onClickDropDown : () -> Unit = {},
    onClickKind : (String) -> Unit = {},
    onChangedName : (String) -> Unit = {},
    onChangedDose : (String) -> Unit = {},
) {

    InputKindView(
        type = uiState.recordType,
        kind = uiState.name,
        onClickDropDown = onClickDropDown,
        interactionSource = interactionSource,
        isExpandMenu = uiState.isExpandDropDown,
        onClickKind = onClickKind,
        onChangedKind = onChangedName
    )

    Spacer(modifier = Modifier.size(32.dp))

    InputDoseView(
        type = uiState.recordType,
        dose = uiState.dose,
        onChangedDose = onChangedDose,
        interactionSource = interactionSource
    )
}

@Composable
fun InputKindView(
    type : RecordType = RecordType.INJECTION,
    kind : String = "",
    onClickDropDown : () -> Unit = {},
    interactionSource : MutableInteractionSource,
    isExpandMenu : Boolean = false,
    onClickKind : (String) -> Unit = {},
    onChangedKind : (String) -> Unit = {},
){
    val text = if(type == RecordType.INJECTION) CALENDAR_SCHEDULE_INJECTION_KIND else CALENDAR_SCHEDULE_MEDICINE_KIND
    val kindList = if(type == RecordType.INJECTION) CALENDAR_SCHEDULE_INJECTION_KIND_LIST else CALENDAR_SCHEDULE_MEDICINE_KIND_LIST
    val hint = if(type == RecordType.INJECTION) CALENDAR_SCHEDULE_INJECTION_KIND_HINT else CALENDAR_SCHEDULE_MEDICINE_KIND_HINT
    var rowWidth by remember { mutableIntStateOf(0) }

    Text(
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
                    Text(
                        text = hint,
                        style = HuggTypography.h3,
                        color = Gs50,
                    )
                }

                BasicTextField(
                    value = kind,
                    onValueChange = { value ->
                        onChangedKind(value)
                    },
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
                .height(336.dp)
        ) {
            kindList.forEach { kind ->
                DropdownMenuItem(
                    text = {
                        Text(
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
    interactionSource: MutableInteractionSource
){
    val title = if(type == RecordType.INJECTION) CALENDAR_SCHEDULE_INJECTION_DOSE else CALENDAR_SCHEDULE_MEDICINE_DOSE
    val doseUnit = if(type == RecordType.INJECTION) CALENDAR_INJECTION_UNIT else CALENDAR_MEDICINE_UNIT

    Text(
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
                Text(
                    text = CALENDAR_SCHEDULE_DOSE_HINT,
                    style = HuggTypography.h3,
                    color = Gs50,
                )
            }

            BasicTextField(
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
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )
        }

        Spacer(modifier = Modifier.size(8.dp))

        Text(
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
                    interactionSource = interactionSource
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
    interactionSource: MutableInteractionSource
){
    Box(
        modifier = Modifier
            .size(width = 60.dp, height = 32.dp)
            .background(color = if(dose == basicDose) Gs70 else Gs10, shape = RoundedCornerShape(999.dp))
            .clickable(
                onClick = {
                    if(dose == basicDose) onClickDoseChip("") else onClickDoseChip(basicDose)
                          },
                interactionSource = interactionSource,
                indication = null
            ),
        contentAlignment = Alignment.Center
    ){
        Text(
            text = basicDose,
            style = HuggTypography.h4,
            color = if(dose == basicDose) White else Gs80
        )
    }
}

@Preview
@Composable
internal fun PrevainContainer() {
    ScheduleCreateOrEditScreen()
}