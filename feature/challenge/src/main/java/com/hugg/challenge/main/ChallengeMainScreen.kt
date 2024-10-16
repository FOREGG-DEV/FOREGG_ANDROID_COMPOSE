package com.hugg.challenge.main

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.hugg.challenge.common.CommonChallenge
import com.hugg.domain.model.enums.ChallengeTabType
import com.hugg.domain.model.enums.TopBarLeftType
import com.hugg.domain.model.enums.TopBarMiddleType
import com.hugg.domain.model.enums.TopBarRightType
import com.hugg.feature.component.HuggTabBar
import com.hugg.feature.component.TopBar
import com.hugg.feature.theme.Background
import com.hugg.feature.theme.CHALLENGE_POINT
import com.hugg.feature.theme.MY_CHALLENGE
import com.hugg.feature.theme.WORD_CHALLENGE

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ChallengeMainScreen(
    popScreen: () -> Unit
) {
    val viewModel: ChallengeMainViewModel = hiltViewModel()
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val interactionSource = remember { MutableInteractionSource() }
    val pagerState = rememberPagerState(pageCount = { uiState.commonChallengeList.size })

    ChallengeMainContent(
        popScreen = popScreen,
        uiState = uiState,
        interactionSource = interactionSource,
        onClickBtnTab = { viewModel.updateTabType(it) },
        pagerState = pagerState
    )
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ChallengeMainContent(
    popScreen: () -> Unit = {},
    uiState: ChallengeMainPageState = ChallengeMainPageState(),
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    onClickBtnTab: (ChallengeTabType) -> Unit = {},
    pagerState: PagerState
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Background)
    ) {
        TopBar(
            leftItemType = TopBarLeftType.BACK,
            leftBtnClicked = { popScreen() },
            middleItemType = TopBarMiddleType.TEXT,
            middleText = WORD_CHALLENGE,
            rightItemType = TopBarRightType.POINT,
            rightItemContent = String.format(CHALLENGE_POINT, uiState.challengePoint)
        )

        Spacer(modifier = Modifier.height(16.dp))

        HuggTabBar(
            tabCount = 2,
            interactionSource = interactionSource,
            leftText = WORD_CHALLENGE,
            rightText = MY_CHALLENGE,
            onClickRightTab = { onClickBtnTab(ChallengeTabType.MY) },
            onClickLeftTab = { onClickBtnTab(ChallengeTabType.COMMON) }
        )

        Spacer(modifier = Modifier.height(24.dp))

        when (uiState.currentTabType) {
            ChallengeTabType.COMMON -> CommonChallenge(uiState = uiState, pagerState = pagerState)
            ChallengeTabType.MY -> TODO()
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PreviewCHMain() {
    ChallengeMainContent(
        pagerState = rememberPagerState {
            0
        }
    )
}