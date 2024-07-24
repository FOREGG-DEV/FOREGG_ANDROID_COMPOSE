package com.hugg.sign

import android.util.Log
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import com.hugg.feature.component.Topbar
import com.hugg.feature.R
import com.hugg.feature.component.BlankBtn
import com.hugg.feature.theme.*
import kotlinx.coroutines.launch

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
        Topbar(
            leftContent = {
                Spacer(modifier = Modifier.width(16.dp))
                if(pagerState.currentPage != 0){
                    Image(
                        modifier = Modifier
                            .width(48.dp)
                            .height(48.dp)
                            .padding(12.dp)
                            .clickable(
                                onClick = {
                                    viewModel.onClickMovePrevPage()
                                },
                                interactionSource = remember { MutableInteractionSource() },
                                indication = null
                            ),
                        imageVector = ImageVector.vectorResource(R.drawable.ic_back_arrow_gs_60),
                        contentDescription = null
                    )
                }
            },
            rightContent = {
                if(pagerState.currentPage != PAGE_COUNT - 1){
                    Text(
                        modifier = Modifier
                            .padding(horizontal = 11.dp, vertical = 14.dp)
                            .clickable(
                                onClick = {
                                    viewModel.onClickLastPage()
                                },
                                interactionSource = remember { MutableInteractionSource() },
                                indication = null
                            ),
                        text = WORD_SKIP,
                        color = Gs60,
                        style = HuggTypography.h4
                    )
                }
                Spacer(modifier = Modifier.width(17.dp))
            }
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
@Composable
fun PageIndicator(
    numberOfPages: Int,
    modifier: Modifier = Modifier,
    selectedPage: Int = 0,
    selectedColor: Color = Main,
    defaultColor: Color = Gs30,
    defaultRadius: Dp = 8.dp,
    selectedLength: Dp = 24.dp,
    space: Dp = 8.dp,
    animationDurationInMillis: Int = 100,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(space),
        modifier = modifier,
    ) {
        for (i in 0 until numberOfPages) {
            val isSelected = i == selectedPage
            PageIndicatorView(
                isSelected = isSelected,
                selectedColor = selectedColor,
                defaultColor = defaultColor,
                defaultRadius = defaultRadius,
                selectedLength = selectedLength,
                animationDurationInMillis = animationDurationInMillis,
            )
        }
    }
}

@Composable
fun PageIndicatorView(
    isSelected: Boolean,
    selectedColor: Color,
    defaultColor: Color,
    defaultRadius: Dp,
    selectedLength: Dp,
    animationDurationInMillis: Int,
    modifier: Modifier = Modifier,
) {

    val color: Color by animateColorAsState(
        targetValue = if (isSelected) {
            selectedColor
        } else {
            defaultColor
        },
        animationSpec = tween(
            durationMillis = animationDurationInMillis,
        ), label = ""
    )
    val width: Dp by animateDpAsState(
        targetValue = if (isSelected) {
            selectedLength
        } else {
            defaultRadius
        },
        animationSpec = tween(
            durationMillis = animationDurationInMillis,
        ), label = ""
    )

    Canvas(
        modifier = modifier
            .size(
                width = width,
                height = defaultRadius,
            ),
    ) {
        drawRoundRect(
            color = color,
            topLeft = Offset.Zero,
            size = Size(
                width = width.toPx(),
                height = defaultRadius.toPx(),
            ),
            cornerRadius = CornerRadius(
                x = defaultRadius.toPx(),
                y = defaultRadius.toPx(),
            ),
        )
    }
}

@Composable
fun OnBoardingItem(
    image : Int = R.drawable.onboarding_first,
    title : String = "",
    content : String = ""
){
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            imageVector = ImageVector.vectorResource(image),
            contentDescription = null
        )
        Spacer(modifier = Modifier.height(12.dp))
        Text(
            modifier = Modifier.padding(horizontal = 16.dp),
            textAlign = TextAlign.Center,
            text = title,
            style = HuggTypography.h1,
            color = Black
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            modifier = Modifier.padding(horizontal = 16.dp),
            textAlign = TextAlign.Center,
            text = content,
            style = HuggTypography.p1,
            color = Gs80
        )
    }
}


@Preview
@Composable
internal fun PreviewMainContainer() {
    OnboardingScreen()
}