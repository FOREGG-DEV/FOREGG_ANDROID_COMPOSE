package com.hugg.sign.onboarding

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.PagerState
import com.google.accompanist.pager.rememberPagerState
import com.hugg.domain.model.enums.TopBarLeftType
import com.hugg.domain.model.enums.TopBarMiddleType
import com.hugg.domain.model.enums.TopBarRightType
import com.hugg.feature.component.BlankBtn
import com.hugg.feature.component.PageIndicator
import com.hugg.feature.component.TopBar
import com.hugg.feature.theme.*
import com.hugg.feature.uiItem.OnBoardingItem
import com.hugg.feature.component.KaKaoLoginBtn
import com.kakao.sdk.user.UserApiClient

const val PAGE_COUNT = 5

@OptIn(ExperimentalPagerApi::class)
@Composable
fun OnboardingContainer(
    navigateServiceTerms : (String) -> Unit = {},
    navigateHome : () -> Unit = {},
    viewModel: OnboardingViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val pagerState = rememberPagerState()

    LaunchedEffect(key1 = Unit) {
        viewModel.eventFlow.collect { event ->
            when(event) {
                OnboardingEvent.MoveNextPage -> pagerState.animateScrollToPage(pagerState.currentPage + 1)
                OnboardingEvent.MoveLastPage -> pagerState.animateScrollToPage(pagerState.pageCount - 1)
                OnboardingEvent.MovePrevPage -> pagerState.animateScrollToPage(pagerState.currentPage - 1)
                OnboardingEvent.GoToMainEvent -> navigateHome()
                is OnboardingEvent.GoToSignUpEvent -> { navigateServiceTerms(event.accessToken) }
            }
        }
    }

    OnboardingScreen(
        uiState = uiState,
        pagerState = pagerState,
        onClickTopBarLeftBtn = { viewModel.onClickMovePrevPage() },
        onClickTopBarRightBtn = { viewModel.onClickLastPage() },
        onClickNextPageBtn = { viewModel.onClickMoveNextPage(pagerState.currentPage) },
        onClickLogin = { signInKakao(context = context, onLoginSuccess = { token ->
                viewModel.login(token)
            })
        }
    )
}

@OptIn(ExperimentalPagerApi::class)
@Composable
fun OnboardingScreen(
    uiState : OnboardingPageState = OnboardingPageState(),
    pagerState: PagerState = rememberPagerState(),
    onClickTopBarLeftBtn : () -> Unit = {},
    onClickTopBarRightBtn : () -> Unit = {},
    onClickNextPageBtn : () -> Unit = {},
    onClickLogin : () -> Unit = {},
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() }
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Background)
    ) {
        TopBar(
            leftItemType = if(pagerState.currentPage != 0) TopBarLeftType.BACK else TopBarLeftType.NONE,
            leftBtnClicked = onClickTopBarLeftBtn,
            middleItemType = TopBarMiddleType.LOGO,
            rightItemType = if(pagerState.currentPage != PAGE_COUNT - 1) TopBarRightType.SKIP else TopBarRightType.NONE,
            rightBtnClicked = onClickTopBarRightBtn,
            interactionSource = interactionSource
        )

        Spacer(modifier = Modifier.height(16.dp))

        HorizontalPager(
            count = uiState.onboardingList.size,
            state = pagerState,
        ) { page ->
            OnBoardingItem(uiState.onboardingList[page].img, uiState.onboardingList[page].title, uiState.onboardingList[page].content)
        }

        Spacer(modifier = Modifier.height(37.dp))

        Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center){
            PageIndicator(
                numberOfPages = PAGE_COUNT,
                selectedPage = pagerState.currentPage,
            )
        }

        Spacer(modifier = Modifier.height(39.dp))

        if(pagerState.currentPage != PAGE_COUNT - 1) BlankBtn(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            onClickBtn = onClickNextPageBtn,
            text = WORD_NEXT
        )
        else KaKaoLoginBtn(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .clickable(
                    onClick = onClickLogin,
                    interactionSource = interactionSource,
                    indication = null
                ),
        )
    }
}

private fun signInKakao(
    context : Context,
    onLoginSuccess : (String) -> Unit
) {
    if (UserApiClient.instance.isKakaoTalkLoginAvailable(context)) {
        signInKakaoApp(context, onLoginSuccess)
    } else {
        signInKakaoEmail(context, onLoginSuccess)
    }
}

private fun signInKakaoApp(
    context : Context,
    onLoginSuccess : (String) -> Unit
) {
    UserApiClient.instance.loginWithKakaoTalk(context) { token, error ->
        token?.let {
            onLoginSuccess(token.accessToken)
        }
    }
}

private fun signInKakaoEmail(
    context : Context,
    onLoginSuccess : (String) -> Unit
) {
    UserApiClient.instance.loginWithKakaoAccount(context) { token, error ->
        token?.let {
            onLoginSuccess(token.accessToken)
        }
    }
}

@OptIn(ExperimentalPagerApi::class)
@Preview
@Composable
internal fun PreviewMainContainer() {
    OnboardingScreen()
}