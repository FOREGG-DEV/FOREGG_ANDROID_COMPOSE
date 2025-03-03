package com.hugg.account.accountMain.bottomSheet

import android.app.DatePickerDialog
import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import com.hugg.account.accountMain.AccountPageState
import com.hugg.domain.model.enums.AccountBottomSheetType
import com.hugg.feature.R
import com.hugg.feature.component.HuggText
import com.hugg.feature.theme.ACCOUNT_CHOOSE_DATE
import com.hugg.feature.theme.ACCOUNT_DIVIDE_DATE
import com.hugg.feature.theme.Background
import com.hugg.feature.theme.Black
import com.hugg.feature.theme.Gs30
import com.hugg.feature.theme.Gs60
import com.hugg.feature.theme.Gs70
import com.hugg.feature.theme.Gs80
import com.hugg.feature.theme.HuggTypography
import com.hugg.feature.theme.WORD_CONFIRM
import com.hugg.feature.theme.White
import com.hugg.feature.util.TimeFormatter
import kotlinx.coroutines.launch
import java.util.Calendar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DatePickBottomSheet(
    onClickClose : () -> Unit = {},
    sheetState: SheetState = rememberModalBottomSheetState(),
    uiState: AccountPageState = AccountPageState(),
    onClickConfirm : (AccountBottomSheetType, String, String) -> Unit = { _, _, _-> },
    context : Context
){
    val scope = rememberCoroutineScope()
    var activeType by remember { mutableStateOf(uiState.selectedBottomSheetType) }
    var isClickedStartDay = false
    var startDay by remember { mutableStateOf(uiState.startDay) }
    var endDay by remember { mutableStateOf(uiState.endDay) }
    val onClickBox = { type : AccountBottomSheetType -> activeType = type }

    val calendar = Calendar.getInstance()

    val datePickerDialog = remember {
        DatePickerDialog(
            context,
            R.style.DatePickerStyle,
            { _, year, month, day ->
                if(isClickedStartDay) startDay = TimeFormatter.getDatePickerDashDate(year, month, day)
                else endDay = TimeFormatter.getDatePickerDashDate(year, month, day)

                if(TimeFormatter.isAfter(startDay, endDay)) startDay = endDay
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        )
    }

    ModalBottomSheet(
        onDismissRequest = onClickClose,
        shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp),
        dragHandle = null,
        sheetState = sheetState
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .height(210.dp)
        ) {
            Spacer(modifier = Modifier.size(4.dp))

            Row(
                modifier = Modifier.padding(horizontal = 12.dp),
            ) {
                Image(
                    modifier = Modifier
                        .size(48.dp)
                        .padding(12.dp)
                        .clickable(
                            onClick = {
                                scope
                                    .launch { sheetState.hide() }
                                    .invokeOnCompletion {
                                        if (!sheetState.isVisible) {
                                            onClickClose()
                                        }
                                    }
                            },
                            interactionSource = remember { MutableInteractionSource() },
                            indication = null
                        ),
                    imageVector = ImageVector.vectorResource(R.drawable.ic_close_gs_60),
                    contentDescription = null
                )

                Spacer(modifier = Modifier.weight(1f))

                HuggText(
                    modifier = Modifier.padding(top = 12.dp),
                    text = ACCOUNT_CHOOSE_DATE,
                    style = HuggTypography.h2,
                    color = Black
                )

                Spacer(modifier = Modifier.weight(1f))

                Box(
                    modifier = Modifier
                        .size(48.dp)
                        .clickable(
                            onClick = {
                                scope
                                    .launch { sheetState.hide() }
                                    .invokeOnCompletion {
                                        if (!sheetState.isVisible) {
                                            onClickConfirm(
                                                activeType,
                                                getCustomStartDay(activeType, startDay),
                                                getCustomEndDay(activeType, endDay)
                                            )
                                            onClickClose()
                                        }
                                    }
                            },
                            interactionSource = remember { MutableInteractionSource() },
                            indication = null
                        ),
                    contentAlignment = Alignment.Center
                ){
                    HuggText(
                        text = WORD_CONFIRM,
                        style = HuggTypography.h4,
                        color = Gs60
                    )
                }
            }

            Spacer(modifier = Modifier.size(28.dp))

            Row(modifier = Modifier.padding(horizontal = 16.dp)) {
                BottomSheetDatePickItem(
                    activeType = activeType,
                    itemType = AccountBottomSheetType.ONE_MONTH,
                    onClickBox = onClickBox
                )
                Spacer(modifier = Modifier.size(4.dp))
                BottomSheetDatePickItem(
                    activeType = activeType,
                    itemType = AccountBottomSheetType.THREE_MONTH,
                    onClickBox = onClickBox
                )
                Spacer(modifier = Modifier.size(4.dp))
                BottomSheetDatePickItem(
                    activeType = activeType,
                    itemType = AccountBottomSheetType.LAST_MONTH,
                    onClickBox = onClickBox
                )
                Spacer(modifier = Modifier.size(4.dp))
                BottomSheetDatePickItem(
                    activeType = activeType,
                    itemType = AccountBottomSheetType.CUSTOM_INPUT,
                    onClickBox = onClickBox
                )
            }

            Spacer(modifier = Modifier.size(13.dp))

            if(activeType == AccountBottomSheetType.CUSTOM_INPUT) {
                Row(
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                        .fillMaxWidth()
                        .background(color = Background, shape = RoundedCornerShape(4.dp))
                        .padding(vertical = 12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Spacer(modifier = Modifier.weight(1f))

                    HuggText(
                        modifier = Modifier
                            .clickable(
                                onClick = {
                                    isClickedStartDay = true
                                    datePickerDialog.show()
                                },
                                interactionSource = remember { MutableInteractionSource() },
                                indication = null
                            ),
                        text = TimeFormatter.getDotsDate(startDay),
                        style = HuggTypography.p1_l,
                        color = Gs80
                    )

                    Spacer(modifier = Modifier.weight(1f))

                    HuggText(
                        text = ACCOUNT_DIVIDE_DATE,
                        style = HuggTypography.p1_l,
                        color = Gs80
                    )

                    Spacer(modifier = Modifier.weight(1f))

                    HuggText(
                        modifier = Modifier
                            .clickable(
                                onClick = {
                                    isClickedStartDay = false
                                    datePickerDialog.show()
                                },
                                interactionSource = remember { MutableInteractionSource() },
                                indication = null
                            ),
                        text = TimeFormatter.getDotsDate(endDay),
                        style = HuggTypography.p1_l,
                        color = Gs80
                    )

                    Spacer(modifier = Modifier.weight(1f))
                }
            }
        }
    }
}

@Composable
fun RowScope.BottomSheetDatePickItem(
    activeType : AccountBottomSheetType = AccountBottomSheetType.ONE_MONTH,
    itemType : AccountBottomSheetType = AccountBottomSheetType.ONE_MONTH,
    onClickBox : (AccountBottomSheetType) -> Unit = {}
){
    Box(
        modifier = Modifier
            .weight(1f)
            .height(40.dp)
            .border(
                width = if (activeType == itemType) 0.dp else 1.dp,
                color = Gs30,
                shape = RoundedCornerShape(4.dp)
            )
            .background(
                color = if (activeType == itemType) Gs70 else White,
                shape = RoundedCornerShape(4.dp)
            )
            .clickable(
                onClick = {
                    onClickBox(itemType)
                },
                interactionSource = remember { MutableInteractionSource() },
                indication = null
            ),
        contentAlignment = Alignment.Center
    ){
        HuggText(
            text = itemType.text,
            style = HuggTypography.h3,
            color = if (activeType == itemType) White else Gs70
        )
    }
}

fun getCustomStartDay(itemType : AccountBottomSheetType, customStartDay : String) : String {
    return when(itemType){
        AccountBottomSheetType.ONE_MONTH -> TimeFormatter.getPreviousMonthDate()
        AccountBottomSheetType.THREE_MONTH -> TimeFormatter.getPreviousThreeMonthDate()
        AccountBottomSheetType.LAST_MONTH -> TimeFormatter.getPreviousMonthStartDay()
        AccountBottomSheetType.CUSTOM_INPUT -> customStartDay
    }
}

fun getCustomEndDay(itemType : AccountBottomSheetType, customEndDay : String) : String {
    return when(itemType){
        AccountBottomSheetType.ONE_MONTH -> TimeFormatter.getToday()
        AccountBottomSheetType.THREE_MONTH -> TimeFormatter.getToday()
        AccountBottomSheetType.LAST_MONTH -> TimeFormatter.getPreviousMonthEndDay()
        AccountBottomSheetType.CUSTOM_INPUT -> customEndDay
    }
}
