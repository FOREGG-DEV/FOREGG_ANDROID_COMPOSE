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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
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
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.hugg.domain.model.enums.RecordType
import com.hugg.feature.R
import com.hugg.feature.theme.CALENDAR_SCHEDULE_ABOUT_MEDICINE
import com.hugg.feature.theme.CALENDAR_SCHEDULE_INJECTION_KIND
import com.hugg.feature.theme.CALENDAR_SCHEDULE_INJECTION_KIND_LIST
import com.hugg.feature.theme.CALENDAR_SCHEDULE_MEDICINE_KIND_LIST
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
    val text = if(type == RecordType.INJECTION) CALENDAR_SCHEDULE_INJECTION_KIND else CALENDAR_SCHEDULE_ABOUT_MEDICINE
    val kindList = if(type == RecordType.INJECTION) CALENDAR_SCHEDULE_INJECTION_KIND_LIST else CALENDAR_SCHEDULE_MEDICINE_KIND_LIST
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

            Spacer(modifier = Modifier.width(12.dp))

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
                modifier = Modifier.weight(1f)
            )

            Spacer(modifier = Modifier.width(12.dp))

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

@Preview
@Composable
internal fun PrevainContainer() {
    ScheduleCreateOrEditScreen()
}