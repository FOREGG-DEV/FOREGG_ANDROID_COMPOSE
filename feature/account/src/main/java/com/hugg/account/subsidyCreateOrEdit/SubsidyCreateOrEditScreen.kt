package com.hugg.account.subsidyCreateOrEdit

import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
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
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.hugg.domain.model.enums.CreateOrEditType
import com.hugg.domain.model.enums.TopBarLeftType
import com.hugg.domain.model.enums.TopBarMiddleType
import com.hugg.domain.model.enums.TopBarRightType
import com.hugg.feature.component.FilledBtn
import com.hugg.feature.component.HuggDialog
import com.hugg.feature.component.TopBar
import com.hugg.feature.theme.ACCOUNT_ADD_SUBSIDY
import com.hugg.feature.theme.ACCOUNT_CONTENT_TEXT_FIELD_HINT
import com.hugg.feature.theme.ACCOUNT_DIALOG_SUBSIDY_DELETE
import com.hugg.feature.theme.ACCOUNT_MAX_TWO_WORD
import com.hugg.feature.theme.ACCOUNT_MODIFY_SUBSIDY
import com.hugg.feature.theme.ACCOUNT_MONEY_UNIT
import com.hugg.feature.theme.ACCOUNT_NICKNAME_DETAIL_EXPLAIN
import com.hugg.feature.theme.ACCOUNT_NICKNAME_TEXT_FIELD_HINT
import com.hugg.feature.theme.ACCOUNT_SUBSIDY_CONTENT
import com.hugg.feature.theme.ACCOUNT_SUBSIDY_MONEY_TITLE
import com.hugg.feature.theme.ACCOUNT_SUBSIDY_NICKNAME
import com.hugg.feature.theme.ACCOUNT_TOAST_SUCCESS_CREATE_SUBSIDY
import com.hugg.feature.theme.ACCOUNT_TOAST_SUCCESS_DELETE_SUBSIDY
import com.hugg.feature.theme.ACCOUNT_TOAST_SUCCESS_EDIT_SUBSIDY
import com.hugg.feature.theme.Background
import com.hugg.feature.theme.Gs50
import com.hugg.feature.theme.Gs80
import com.hugg.feature.theme.Gs90
import com.hugg.feature.theme.HuggTypography
import com.hugg.feature.theme.Sunday
import com.hugg.feature.theme.WORD_DELETE
import com.hugg.feature.theme.WORD_MODIFY
import com.hugg.feature.theme.WORD_REGISTRATION
import com.hugg.feature.util.HuggToast
import com.hugg.feature.util.UserInfo

@Composable
fun SubsidyCreateOrEditContainer(
    goToBack : () -> Unit = {},
    type : CreateOrEditType = CreateOrEditType.CREATE,
    id : Long = -1,
    round : Int = UserInfo.info.round,
    viewModel: SubsidyCreateOrEditViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val context = LocalContext.current
    val interactionSource = remember { MutableInteractionSource() }

    LaunchedEffect(key1 = type, key2 = id, key3 = round){
        viewModel.initTypeAndId(type, id, round)
    }

    LaunchedEffect(Unit) {
        viewModel.eventFlow.collect { event ->
            when(event) {
                SubsidyCreateOrEditEvent.SuccessDeleteSubsidyEvent -> {
                    HuggToast.createToast(context, ACCOUNT_TOAST_SUCCESS_DELETE_SUBSIDY).show()
                    goToBack()
                }
                SubsidyCreateOrEditEvent.SuccessCreateSubsidyEvent -> {
                    HuggToast.createToast(context, ACCOUNT_TOAST_SUCCESS_CREATE_SUBSIDY).show()
                    goToBack()
                }
                SubsidyCreateOrEditEvent.SuccessModifySubsidyEvent -> {
                    HuggToast.createToast(context, ACCOUNT_TOAST_SUCCESS_EDIT_SUBSIDY).show()
                    goToBack()
                }
            }
        }
    }

    SubsidyCreateOrEditScreen(
        uiState = uiState,
        onClickTopBarLeftBtn = goToBack,
        onClickDeleteBtn = { viewModel.showDeleteDialog() },
        onChangedNickname = { nickname -> viewModel.onChangedNickname(nickname) },
        onChangedContent = { content -> viewModel.onChangedContent(content) },
        onChangedMoney = { money -> viewModel.onChangedMoney(money) },
        onClickCreateOrChangeBtn = { viewModel.createOrEdit() }
    )

    if(uiState.isShowDialog) ShowDeleteDialog(
        interactionSource = interactionSource,
        onClickCancel = { viewModel.cancelDeleteDialog() },
        onClickDeleteBtn = {
            viewModel.cancelDeleteDialog()
            viewModel.deleteSubsidy()
        }
    )
}

@Composable
fun SubsidyCreateOrEditScreen(
    uiState : SubsidyCreateOrEditPageState = SubsidyCreateOrEditPageState(),
    onClickTopBarLeftBtn : () -> Unit = {},
    onClickDeleteBtn : () -> Unit = {},
    onChangedNickname : (String) -> Unit = {},
    onChangedContent : (String) -> Unit = {},
    onChangedMoney : (String) -> Unit = {},
    onClickCreateOrChangeBtn : () -> Unit = {},
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
            rightItemType = if(uiState.pageType == CreateOrEditType.CREATE) TopBarRightType.NONE else TopBarRightType.DELETE_GS30,
            rightBtnClicked = onClickDeleteBtn,
            middleText = if(uiState.pageType == CreateOrEditType.CREATE) ACCOUNT_ADD_SUBSIDY else ACCOUNT_MODIFY_SUBSIDY
        )

        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
        ){
            item {
                Spacer(modifier = Modifier.size(24.dp))
                InputNickName(
                    nickname = uiState.nickname,
                    onChangedNickname = onChangedNickname
                )
            }

            item {
                Spacer(modifier = Modifier.size(32.dp))
                InputContent(
                    content = uiState.content,
                    onChangedContent = onChangedContent
                )
            }

            item {
                Spacer(modifier = Modifier.size(34.dp))
                InputMoney(
                    money = uiState.money,
                    onChangedMoney = onChangedMoney
                )
            }

            item {
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
}

@Composable
fun InputNickName(
    nickname : String = "",
    onChangedNickname : (String) -> Unit = {}
){
    Column {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = ACCOUNT_SUBSIDY_NICKNAME,
                style = HuggTypography.h3,
                color = Gs80
            )

            Spacer(modifier = Modifier.weight(1f))

            Text(
                text = ACCOUNT_MAX_TWO_WORD,
                style = HuggTypography.p3_l,
                color = Sunday
            )
        }
        
        Spacer(modifier = Modifier.size(4.dp))

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(color = Color.White, shape = RoundedCornerShape(8.dp))
                .padding(horizontal = 12.dp, vertical = 13.dp)
        ) {
            if (nickname.isEmpty()) {
                Text(
                    text = ACCOUNT_NICKNAME_TEXT_FIELD_HINT,
                    style = HuggTypography.h3,
                    color = Gs50,
                )
            }

            BasicTextField(
                value = nickname,
                onValueChange = { value ->
                    onChangedNickname(value)
                },
                textStyle = HuggTypography.h3.copy(
                    color = Gs90,
                ),
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )
        }

        Spacer(modifier = Modifier.size(4.dp))

        Text(
            text = ACCOUNT_NICKNAME_DETAIL_EXPLAIN,
            style = HuggTypography.p3_l,
            color = Gs50,
        )
    }
}

@Composable
fun InputContent(
    content : String = "",
    onChangedContent : (String) -> Unit = {}
){
    Column {
        Text(
            text = ACCOUNT_SUBSIDY_CONTENT,
            style = HuggTypography.h3,
            color = Gs80
        )

        Spacer(modifier = Modifier.size(4.dp))

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .defaultMinSize(minHeight = 291.dp)
                .background(color = Color.White, shape = RoundedCornerShape(8.dp))
                .padding(horizontal = 12.dp, vertical = 13.dp)
        ) {
            if (content.isEmpty()) {
                Text(
                    text = ACCOUNT_CONTENT_TEXT_FIELD_HINT,
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
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

@Composable
fun InputMoney(
    money : String = "",
    onChangedMoney : (String) -> Unit = {}
){
    Column {
        Text(
            text = ACCOUNT_SUBSIDY_MONEY_TITLE,
            style = HuggTypography.h3,
            color = Gs80
        )

        Spacer(modifier = Modifier.size(4.dp))

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(color = Color.White, shape = RoundedCornerShape(8.dp))
                .padding(horizontal = 12.dp, vertical = 13.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                if (money.isEmpty()) {
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
                    text = money,
                    selection = TextRange(money.length) // 커서를 텍스트 끝으로 이동
                ),
                onValueChange = { value ->
                    onChangedMoney(value.text)
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
fun ShowDeleteDialog(
    interactionSource: MutableInteractionSource,
    onClickCancel : () -> Unit = {},
    onClickDeleteBtn: () -> Unit = {}
){
    HuggDialog(
        title = ACCOUNT_DIALOG_SUBSIDY_DELETE,
        positiveColor = Sunday,
        positiveText = WORD_DELETE,
        onClickCancel = onClickCancel,
        onClickNegative = onClickCancel,
        onClickPositive = onClickDeleteBtn,
        interactionSource = interactionSource
    )
}

@Preview
@Composable
internal fun PreviewMainContainer() {
    SubsidyCreateOrEditScreen()
}