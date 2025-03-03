package com.hugg.challenge.main

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.hugg.challenge.common.CommonChallenge
import com.hugg.challenge.my.MyChallenge
import com.hugg.domain.model.enums.ChallengeTabType
import com.hugg.domain.model.enums.HuggTabClickedType
import com.hugg.domain.model.enums.MyChallengeState
import com.hugg.domain.model.enums.TopBarLeftType
import com.hugg.domain.model.enums.TopBarMiddleType
import com.hugg.domain.model.enums.TopBarRightType
import com.hugg.feature.R
import com.hugg.feature.component.ChallengeCompleteDialog
import com.hugg.feature.component.HuggInputDialog
import com.hugg.feature.component.HuggTabBar
import com.hugg.feature.component.TopBar
import com.hugg.feature.theme.Background
import com.hugg.feature.theme.CHALLENGE_ALREADY_PARTICIPATED
import com.hugg.feature.theme.CHALLENGE_INPUT_DIALOG_TITLE
import com.hugg.feature.theme.CHALLENGE_POINT
import com.hugg.feature.theme.DUPLICATE_CHALLENGE_NICKNAME
import com.hugg.feature.theme.EXIST_CHALLENGE_NICKNAME
import com.hugg.feature.theme.INSUFFICIENT_POINT
import com.hugg.feature.theme.MY_CHALLENGE
import com.hugg.feature.theme.WORD_CHALLENGE
import com.hugg.feature.theme.WORD_CONFIRM
import com.hugg.feature.util.HuggToast
import com.hugg.feature.util.UserInfo
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ChallengeMainScreen(
    popScreen: () -> Unit,
    challengeTabType: ChallengeTabType,
    goToChallengeList: () -> Unit,
    goToCreateChallenge : () -> Unit,
    goToChallengeSupport: (Long) -> Unit
) {
    val viewModel: ChallengeMainViewModel = hiltViewModel()
    val context = LocalContext.current
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val interactionSource = remember { MutableInteractionSource() }
    val pagerState = rememberPagerState(pageCount = { uiState.commonChallengeList.size })
    val myChallengePagerState = rememberPagerState(pageCount = { uiState.myChallengeList.size })
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.challenge_success))
    val progress by animateLottieCompositionAsState(
        composition = composition,
        isPlaying = uiState.showChallengeSuccessAnimation,
        iterations = 1,
        restartOnPlay = true
    )

    LaunchedEffect(Unit) {
        val type = if(challengeTabType == uiState.currentTabType) challengeTabType else uiState.currentTabType
        viewModel.updateTabType(type)
    }

    LaunchedEffect(Unit) {
        viewModel.eventFlow.collect { event ->
            when(event) {
                is ChallengeMainEvent.ExistNickname -> {
                    HuggToast.createToast(context, EXIST_CHALLENGE_NICKNAME).show()
                }
                is ChallengeMainEvent.DuplicateNickname -> {
                    HuggToast.createToast(context, DUPLICATE_CHALLENGE_NICKNAME).show()
                }
                is ChallengeMainEvent.ChallengeAlreadyParticipated -> {
                    HuggToast.createToast(context, CHALLENGE_ALREADY_PARTICIPATED).show()
                }
                is ChallengeMainEvent.InsufficientPoint -> {
                    HuggToast.createToast(context, INSUFFICIENT_POINT).show()
                }
                is ChallengeMainEvent.GetMyChallengeSuccess -> {
                    viewModel.initializeChallengeState(myChallengePagerState.currentPage)
                }
            }
        }
    }

    LaunchedEffect(Unit) {
        viewModel.getChallengeList()
    }

    LaunchedEffect(progress) {
        if (progress == 1f && uiState.showChallengeSuccessAnimation) {
            viewModel.updateShowChallengeSuccessAnimation(false)
        }
    }

    if (UserInfo.challengeNickname.isEmpty()) {
        HuggInputDialog(
            title = CHALLENGE_INPUT_DIALOG_TITLE,
            maxLength = 15,
            positiveText = WORD_CONFIRM,
            onClickCancel = { popScreen() },
            onClickPositive = { nickname ->
                if (nickname.isNotEmpty()) {
                    viewModel.createNickname(nickname)
                }
            }
        )
    }

    if (uiState.showChallengeCompleteDialog) {
        ChallengeCompleteDialog(
            onClickCancel = { viewModel.updateShowDialog(false) },
            points = 2000
        )
    }

    if (uiState.commonChallengeList.isNotEmpty()) {
        Box(
            modifier = Modifier
                .fillMaxSize()
        ) {
            ChallengeMainContent(
                popScreen = popScreen,
                uiState = uiState,
                interactionSource = interactionSource,
                onClickBtnTab = { viewModel.updateTabType(it) },
                onClickBtnOpen = { viewModel.unlockChallenge(it) },
                onCLickBtnParticipate = { viewModel.participateChallenge(it) },
                pagerState = pagerState,
                myChallengePagerState = myChallengePagerState,
                showAnimationFlow = viewModel.showUnlockAnimationFlow,
                goToChallengeList = goToChallengeList,
                goToCreateChallenge = goToCreateChallenge,
                goToChallengeSupport = goToChallengeSupport,
                onPageChanged = { viewModel.initializeChallengeState(it) },
                updateCommentDialogVisibility = { viewModel.updateCommentDialogVisibility(it) },
                completeChallenge = { thoughts, state ->
                    viewModel.completeChallenge(
                        id = uiState.myChallengeList[myChallengePagerState.currentPage].id,
                        state = state,
                        thoughts = thoughts
                    )
                },
                updateDeleteDialogVisibility = { viewModel.updateDeleteDialogVisibility(it) },
                deleteChallenge = { viewModel.deleteChallenge(it) }
            )

            if (uiState.showChallengeSuccessAnimation) {
                ChallengeCompleteDialog(
                    onClickCancel = { viewModel.updateShowDialog(false) },
                    points = 100
                )
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ChallengeMainContent(
    popScreen: () -> Unit = {},
    uiState: ChallengeMainPageState = ChallengeMainPageState(),
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    onClickBtnTab: (ChallengeTabType) -> Unit = {},
    onClickBtnOpen: (Long) -> Unit = {},
    onCLickBtnParticipate: (Long) -> Unit = {},
    pagerState: PagerState,
    myChallengePagerState: PagerState,
    showAnimationFlow: SharedFlow<Boolean> = MutableSharedFlow(),
    goToChallengeList: () -> Unit = {},
    goToCreateChallenge : () -> Unit = {},
    goToChallengeSupport: (Long) -> Unit,
    onPageChanged: (Int) -> Unit,
    updateCommentDialogVisibility: (Boolean) -> Unit,
    completeChallenge: (String, MyChallengeState) -> Unit,
    updateDeleteDialogVisibility: (Boolean) -> Unit,
    deleteChallenge: (Long) -> Unit
) {
    val rememberedTabType = remember { mutableStateOf(uiState.currentTabType) }

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
            rightItemContent = String.format(CHALLENGE_POINT, UserInfo.challengePoint)
        )

        Spacer(modifier = Modifier.height(16.dp))

        HuggTabBar(
            tabCount = 2,
            interactionSource = interactionSource,
            leftText = WORD_CHALLENGE,
            rightText = MY_CHALLENGE,
            onClickRightTab = {
                rememberedTabType.value = ChallengeTabType.MY
                onClickBtnTab(ChallengeTabType.MY)
            },
            onClickLeftTab = {
                rememberedTabType.value = ChallengeTabType.COMMON
                onClickBtnTab(ChallengeTabType.COMMON)
            },
            initialTabType = when(rememberedTabType.value) {
                ChallengeTabType.COMMON -> HuggTabClickedType.LEFT
                ChallengeTabType.MY -> HuggTabClickedType.RIGHT
            }
        )

        Spacer(modifier = Modifier.height(24.dp))

        when (rememberedTabType.value) {
            ChallengeTabType.COMMON -> CommonChallenge(
                uiState = uiState,
                pagerState = pagerState,
                onClickBtnOpen = onClickBtnOpen,
                onClickBtnParticipation = onCLickBtnParticipate,
                showAnimationFlow = showAnimationFlow,
                onClickBtnChallengeList = { goToChallengeList() },
                onClickCreateChallenge = { goToCreateChallenge() }
            )
            ChallengeTabType.MY -> MyChallenge(
                uiState = uiState,
                pagerState = myChallengePagerState,
                goToChallengeSupport = goToChallengeSupport,
                onPageChange = onPageChanged,
                updateCommentDialogVisibility = updateCommentDialogVisibility,
                completeChallenge = completeChallenge,
                updateDeleteDialogVisibility = updateDeleteDialogVisibility,
                deleteChallenge = deleteChallenge
            )
        }
    }
}