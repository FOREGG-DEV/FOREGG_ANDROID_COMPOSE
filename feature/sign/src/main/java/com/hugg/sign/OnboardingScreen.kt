package com.hugg.sign

import androidx.compose.foundation.background
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import com.hugg.domain.model.enums.TopBarLeftType
import com.hugg.domain.model.enums.TopBarMiddleType
import com.hugg.domain.model.enums.TopBarRightType
import com.hugg.feature.component.BlankBtn
import com.hugg.feature.component.PageIndicator
import com.hugg.feature.component.TopBar
import com.hugg.feature.theme.*
import com.hugg.feature.uiItem.OnBoardingItem

const val PAGE_COUNT = 4

@OptIn(ExperimentalPagerApi::class)
@Composable
fun OnboardingScreen(
    viewModel: OnboardingViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val pagerState = rememberPagerState()

    LaunchedEffect(key1 = Unit) {
        viewModel.eventFlow.collect {
            when(it as OnboardingEvent){
                OnboardingEvent.MoveNextPage -> pagerState.animateScrollToPage(pagerState.currentPage + 1)
                OnboardingEvent.MoveLastPage -> pagerState.animateScrollToPage(pagerState.pageCount - 1)
                OnboardingEvent.MovePrevPage -> pagerState.animateScrollToPage(pagerState.currentPage - 1)
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Background)
    ) {
        TopBar(
            leftItemType = if(pagerState.currentPage != 0) TopBarLeftType.BACK else TopBarLeftType.NONE,
            leftBtnClicked = { viewModel.onClickMovePrevPage() },
            middleItemType = TopBarMiddleType.LOGO,
            rightItemType = if(pagerState.currentPage != PAGE_COUNT - 1) TopBarRightType.SKIP else TopBarRightType.NONE,
            rightBtnClicked = { viewModel.onClickLastPage() }
        )

        Spacer(modifier = Modifier.height(38.dp))

        HorizontalPager(
            count = uiState.onboardingList.size,
            state = pagerState,
        ) { page ->
            OnBoardingItem(uiState.onboardingList[page].img, uiState.onboardingList[page].title, uiState.onboardingList[page].content)
        }

        Spacer(modifier = Modifier.height(53.dp))

        Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center){
            PageIndicator(
                numberOfPages = PAGE_COUNT,
                selectedPage = pagerState.currentPage,
            )
        }

        Spacer(modifier = Modifier.height(39.dp))

        BlankBtn(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            onClickBtn = {
                viewModel.onClickMoveNextPage(pagerState.currentPage)
            },
            text = WORD_NEXT
        )
    }
}

@Preview
@Composable
internal fun PreviewMainContainer() {
    OnboardingScreen()
}