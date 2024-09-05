package com.hugg.account.subsidyList

import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.hugg.domain.model.enums.TopBarLeftType
import com.hugg.domain.model.enums.TopBarMiddleType
import com.hugg.domain.model.request.sign.SignUpMaleRequestVo
import com.hugg.feature.component.FilledBtn
import com.hugg.feature.component.HuggToast
import com.hugg.feature.component.SignUpIndicator
import com.hugg.feature.component.TopBar
import com.hugg.feature.theme.ACCOUNT_SUBSIDY_MONEY
import com.hugg.feature.theme.Background
import com.hugg.feature.theme.Black
import com.hugg.feature.theme.Gs80
import com.hugg.feature.theme.Gs90
import com.hugg.feature.theme.HuggTypography
import com.hugg.feature.theme.SIGN_UP_COMPLETE
import com.hugg.feature.theme.SIGN_UP_MALE
import com.hugg.feature.theme.SIGN_UP_SPOUSE_CODE_MALE_HINT
import com.hugg.feature.theme.TOAST_ERROR_NOR_CORRECT_SPOUSE_CODE
import com.hugg.feature.theme.WORD_SIGN_UP
import com.hugg.feature.theme.White
import com.hugg.feature.uiItem.RemoteRound
import com.hugg.feature.util.UserInfo

@Composable
fun SubsidyListContainer(
    goToBack : () -> Unit = {},
    navigateToCreateSubsidy : () -> Unit = {},
    nowRound : Int = UserInfo.info.round,
    viewModel: SubsidiyListViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit){
        viewModel.updateNowRound(nowRound)
    }

    SubsidyListScreen(
        uiState = uiState,
        onClickTopBarLeftBtn = goToBack,
    )
}

@Composable
fun SubsidyListScreen(
    uiState : SubsidyListPageState = SubsidyListPageState(),
    onClickTopBarLeftBtn : () -> Unit = {},
    onClickPrevRoundBtn: () -> Unit = {},
    onClickNextRoundBtn: () -> Unit = {},
    onClickCreateRoundBtn: () -> Unit = {},
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() }
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
            middleText = ACCOUNT_SUBSIDY_MONEY
        )

        Spacer(modifier = Modifier.size(16.dp))

        RemoteRound(
            onClickPrevRoundBtn = onClickPrevRoundBtn,
            onClickNextRoundBtn = onClickNextRoundBtn,
            onClickCreateRoundBtn = onClickCreateRoundBtn,
            interactionSource = interactionSource,
            nowRound = uiState.nowRound
        )
    }

//    HuggToast(
//        visible = uiState.isShowErrorSpouseCode,
//        text = TOAST_ERROR_NOR_CORRECT_SPOUSE_CODE
//    )
}

@Preview
@Composable
internal fun PreviewMainContainer() {
    SubsidyListScreen()
}