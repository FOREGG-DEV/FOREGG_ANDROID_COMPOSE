package com.hugg.account.accountCreateOrEdit

import android.app.DatePickerDialog
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.hugg.domain.model.enums.AccountColorType
import com.hugg.domain.model.enums.CreateOrEditType
import com.hugg.domain.model.enums.TopBarLeftType
import com.hugg.domain.model.enums.TopBarMiddleType
import com.hugg.domain.model.enums.TopBarRightType
import com.hugg.domain.model.vo.account.AccountExpenditureItemVo
import com.hugg.feature.component.TopBar
import com.hugg.feature.theme.*
import com.hugg.feature.R
import com.hugg.feature.component.FilledBtn
import com.hugg.feature.component.HuggDialog
import com.hugg.feature.util.HuggToast
import com.hugg.feature.util.TimeFormatter
import com.hugg.feature.util.UnitFormatter
import com.hugg.feature.util.UserInfo
import java.util.Calendar


@Composable
fun AccountCreateOrEditContainer(
    navigateCreateSubsidy : (Int) -> Unit = {},
    goToBack : () -> Unit = {},
    id : Long = -1,
    type : CreateOrEditType = CreateOrEditType.CREATE,
    viewModel: AccountCreateOrEditViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val context = LocalContext.current
    val calendar = Calendar.getInstance()
    val interactionSource = remember { MutableInteractionSource() }

    val datePickerDialog = remember {
        DatePickerDialog(
            context,
            R.style.DatePickerStyle,
            { _, year, month, day ->
                viewModel.selectStartDate(TimeFormatter.getDatePickerDashDate(year, month, day))
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        )
    }

    LaunchedEffect(Unit) {
        viewModel.initView(id, type)
    }

    LaunchedEffect(Unit) {
        viewModel.eventFlow.collect { event ->
            when(event) {
                AccountCreateOrEditEvent.SuccessCreateAccountEvent -> {
                    HuggToast.createToast(context, ACCOUNT_TOAST_SUCCESS_CREATE).show()
                    goToBack()
                }
                AccountCreateOrEditEvent.SuccessModifyAccountEvent -> {
                    HuggToast.createToast(context, ACCOUNT_TOAST_SUCCESS_EDIT).show()
                    goToBack()
                }
                AccountCreateOrEditEvent.SuccessDeleteAccountEvent -> {
                    HuggToast.createToast(context, ACCOUNT_TOAST_SUCCESS_DELETE).show()
                    goToBack()
                }
            }
        }
    }

    AccountCreateOrEditScreen(
        uiState = uiState,
        onClickTopBarLeftBtn = goToBack,
        onClickDeleteBtn = { viewModel.showDeleteDialog() },
        onClickDatePickerBtn = { datePickerDialog.show() },
        onClickMinusBtn = { viewModel.onClickMinusBtn() },
        onClickPlusBtn = { viewModel.onClickPlusBtn() },
        onClickCreateSubsidyBtn = { navigateCreateSubsidy(uiState.nowRound) },
        onChangedContent = { content -> viewModel.onChangedContent(content) },
        onChangedMoney = { money, name -> viewModel.onChangedMoney(money, name) },
        onChangedMemo = { memo -> viewModel.onChangedMemo(memo) },
        onClickCreateOrChangeBtn = { viewModel.onClickCreateOrEdit() },
        interactionSource = interactionSource
    )

    if(uiState.isShowDialog) {
        HuggDialog(
            title = ACCOUNT_DIALOG_DELETE,
            positiveColor = Sunday,
            positiveText = WORD_DELETE,
            onClickCancel = { viewModel.cancelDeleteDialog() },
            onClickNegative = { viewModel.cancelDeleteDialog() },
            onClickPositive = { viewModel.deleteAccount() },
            interactionSource = interactionSource
        )
    }
}

@Composable
fun AccountCreateOrEditScreen(
    uiState : AccountCreateOrEditPageState = AccountCreateOrEditPageState(),
    onClickTopBarLeftBtn : () -> Unit = {},
    onClickDeleteBtn : () -> Unit = {},
    onClickDatePickerBtn: () -> Unit = {},
    onClickMinusBtn: () -> Unit = {},
    onClickPlusBtn: () -> Unit = {},
    interactionSource : MutableInteractionSource = remember { MutableInteractionSource() },
    onClickCreateSubsidyBtn : () -> Unit = {},
    onChangedContent : (String) -> Unit = {},
    onChangedMoney : (String, String) -> Unit = {_, _ -> },
    onChangedMemo : (String) -> Unit = {},
    onClickCreateOrChangeBtn : () -> Unit = {}
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Background),
    ) {

        TopBar(
            leftItemType = TopBarLeftType.BACK,
            leftBtnClicked = onClickTopBarLeftBtn,
            middleItemType = TopBarMiddleType.TEXT,
            middleText = ACCOUNT_CREATE,
            rightItemType = if(uiState.pageType == CreateOrEditType.CREATE) TopBarRightType.NONE else TopBarRightType.DELETE_GS30,
            rightBtnClicked = onClickDeleteBtn,
            interactionSource = interactionSource
        )

        Column(
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .fillMaxSize()
                .background(Background),
        ) {
            Spacer(modifier = Modifier.size(24.dp))

            SelectDateView(
                date = uiState.date,
                onClickDatePickerBtn = onClickDatePickerBtn,
                interactionSource = interactionSource
            )

            Spacer(modifier = Modifier.size(32.dp))

            SelectRoundView(
                round = uiState.nowRound,
                onClickMinusBtn = onClickMinusBtn,
                onClickPlusBtn = onClickPlusBtn,
                interactionSource = interactionSource
            )

            Spacer(modifier = Modifier.size(32.dp))

            ContentAndAmountView(
                onClickCreateSubsidyBtn = onClickCreateSubsidyBtn,
                interactionSource = interactionSource,
                content = uiState.content,
                expenditureList = uiState.expenditureList,
                onChangedContent = onChangedContent,
                onChangedMoney = onChangedMoney
            )

            Spacer(modifier = Modifier.size(28.dp))

            InputMemoView(
                memo = uiState.memo,
                onChangedMemo = onChangedMemo
            )

            Spacer(modifier = Modifier.size(24.dp))

            FilledBtn(
                modifier = Modifier
                    .fillMaxWidth(),
                onClickBtn = onClickCreateOrChangeBtn,
                isActive = uiState.isActiveBtn,
                text = if(uiState.pageType == CreateOrEditType.CREATE) WORD_REGISTRATION else WORD_MODIFY
            )
        }
    }
}

@Composable
fun SelectDateView(
    date: String = "",
    onClickDatePickerBtn: () -> Unit,
    interactionSource : MutableInteractionSource
){
    Text(
        text = ACCOUNT_CREATE_DATE_TITLE,
        style = HuggTypography.h3,
        color = Gs80
    )

    Spacer(modifier = Modifier.size(4.dp))

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(48.dp)
            .background(color = White, shape = RoundedCornerShape(8.dp))
            .clickable(
                onClick = onClickDatePickerBtn,
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
            modifier = Modifier.padding(end = 27.dp),
            color = Gs50,
            style = HuggTypography.h3,
            text = date
        )
    }
}

@Composable
fun SelectRoundView(
    round : Int = 0,
    onClickMinusBtn: () -> Unit = {},
    onClickPlusBtn: () -> Unit = {},
    interactionSource : MutableInteractionSource
){
    Text(
        text = ACCOUNT_CREATE_ROUND_TITLE,
        style = HuggTypography.h3,
        color = Gs80
    )

    Row(
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
                            color = if(round == 0) Disabled else Gs10,
                            shape = RoundedCornerShape(topStart = 8.dp, bottomStart = 8.dp)
                        )
                        .clickable(
                            onClick = onClickMinusBtn,
                            interactionSource = interactionSource,
                            indication = null
                        ),
                    contentAlignment = Alignment.Center
                ){
                    Image(
                        painter = painterResource(id = if(round == 0) R.drawable.ic_minus_white else R.drawable.ic_minus_gs_70),
                        contentDescription = null
                    )
                }
            }

            Text(
                color = Black,
                style = HuggTypography.h3,
                text = round.toString()
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
                            color = if(round == UserInfo.info.round) Disabled else Gs10,
                            shape = RoundedCornerShape(topEnd = 8.dp, bottomEnd = 8.dp)
                        )
                        .clickable(
                            onClick = onClickPlusBtn,
                            interactionSource = interactionSource,
                            indication = null
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        modifier = Modifier.size(14.dp),
                        painter = painterResource(id = if(round == UserInfo.info.round) R.drawable.ic_plus_white else R.drawable.ic_plus_gs_70),
                        contentDescription = null
                    )
                }
            }
        }

        Spacer(modifier = Modifier.width(8.dp))

        Text(
            style = HuggTypography.h3,
            color = Gs70,
            text = ACCOUNT_ROUND_UNIT_WITHOUT_CAR
        )
    }
}

@Composable
fun ContentAndAmountView(
    onClickCreateSubsidyBtn : () -> Unit = {},
    interactionSource : MutableInteractionSource,
    content : String = "",
    expenditureList : List<AccountExpenditureItemVo> = emptyList(),
    onChangedContent : (String) -> Unit = {},
    onChangedMoney : (String, String) -> Unit = {_, _ -> }
){
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            style = HuggTypography.h3,
            color = Gs80,
            text = ACCOUNT_CREATE_CONTENT_AMOUNT_TITLE
        )

        Spacer(modifier = Modifier.weight(1f))

        Box(
            modifier = Modifier
                .border(width = 0.5.dp, color = Gs20, shape = RoundedCornerShape(4.dp))
                .background(color = White, shape = RoundedCornerShape(4.dp)),
            contentAlignment = Alignment.Center
        ) {
            Text(
                modifier = Modifier
                    .padding(horizontal = 8.dp, vertical = 6.dp)
                    .clickable(
                        onClick = onClickCreateSubsidyBtn,
                        interactionSource = interactionSource,
                        indication = null
                    ),
                text = ACCOUNT_ADD_SUBSIDY_LIST,
                style = HuggTypography.p3,
                color = Gs50
            )
        }
    }

    Spacer(modifier = Modifier.size(3.dp))

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = Color.White, shape = RoundedCornerShape(8.dp))
            .padding(horizontal = 12.dp, vertical = 13.dp)
    ) {
        if (content.isEmpty()) {
            Text(
                text = ACCOUNT_CREATE_CONTENT_HINT,
                style = HuggTypography.h3,
                color = Gs50,
            )
        }

        BasicTextField(
            value = content,
            onValueChange = { value ->
                onChangedContent(value)
            },
            textStyle = HuggTypography.h3.copy(
                color = Gs90,
            ),
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )
    }

    Spacer(modifier = Modifier.size(8.dp))

    expenditureList.forEach {
        ExpenditureItemView(
            item = it,
            onChangedMoney = onChangedMoney
        )

        Spacer(modifier = Modifier.size(4.dp))
    }

}

@Composable
fun ExpenditureItemView(
    item : AccountExpenditureItemVo,
    onChangedMoney : (String, String) -> Unit = {_, _ -> }
){
    val boxColor = when(item.color){
        AccountColorType.RED,
        AccountColorType.ALL -> CalendarPill
        AccountColorType.BLUE -> CalendarInjection
        AccountColorType.GREEN -> CalendarHospital
        AccountColorType.YELLOW -> CalendarEtc
    }
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(12.dp)
                .background(color = boxColor, shape = RoundedCornerShape(3.dp))
        )

        Spacer(modifier = Modifier.size(4.dp))

        Text(
            text = if(item.color != AccountColorType.RED) UnitFormatter.getSubsidyTitleFormat(item.nickname) else item.nickname,
            style = HuggTypography.p1,
            color = Gs80
        )

        Spacer(modifier = Modifier.weight(1f))

        Box(
            modifier = Modifier
                .size(width = 234.dp, height = 48.dp)
                .background(color = Color.White, shape = RoundedCornerShape(8.dp))
                .padding(horizontal = 12.dp, vertical = 13.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                if (item.money.isEmpty()) {
                    Text(
                        text = "0",
                        style = HuggTypography.h3,
                        color = Gs50,
                    )
                }

                Spacer(modifier = Modifier.size(2.dp))

                Text(
                    text = ACCOUNT_MONEY_UNIT,
                    style = HuggTypography.h3,
                    color = Gs50,
                )
            }

            BasicTextField(
                value = TextFieldValue(
                    text = item.money,
                    selection = TextRange(item.money.length)
                ),
                onValueChange = { value ->
                    onChangedMoney(value.text, item.nickname)
                },
                textStyle = HuggTypography.h3.copy(
                    color = Gs90,
                    textAlign = TextAlign.End
                ),
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Number
                ),
                singleLine = true,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 18.dp)
            )
        }
    }
}

@Composable
fun InputMemoView(
    memo : String = "",
    onChangedMemo : (String) -> Unit = {}
){
    Text(
        style = HuggTypography.h3,
        color = Gs80,
        text = WORD_MEMO
    )

    Spacer(modifier = Modifier.size(4.dp))

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = Color.White, shape = RoundedCornerShape(8.dp))
            .padding(horizontal = 12.dp, vertical = 13.dp)
    ) {
        if (memo.isEmpty()) {
            Text(
                text = ACCOUNT_CREATE_MEMO_HINT,
                style = HuggTypography.h3,
                color = Gs50,
            )
        }

        BasicTextField(
            value = memo,
            onValueChange = { value ->
                onChangedMemo(value)
            },
            textStyle = HuggTypography.h3.copy(
                color = Gs90,
            ),
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Preview
@Composable
internal fun PreviewMainContainer() {
    AccountCreateOrEditScreen()
}