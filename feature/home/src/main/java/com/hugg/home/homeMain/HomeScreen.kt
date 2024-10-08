package com.hugg.home.homeMain

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.PagerState
import com.google.accompanist.pager.rememberPagerState
import com.hugg.domain.model.enums.GenderType
import com.hugg.domain.model.enums.TopBarLeftType
import com.hugg.domain.model.enums.TopBarMiddleType
import com.hugg.domain.model.enums.TopBarRightType
import com.hugg.domain.model.response.challenge.MyChallengeListItemVo
import com.hugg.domain.model.response.home.HomeRecordResponseVo
import com.hugg.domain.model.vo.home.HomeTodayScheduleCardVo
import com.hugg.feature.component.TopBar
import com.hugg.feature.theme.*
import com.hugg.feature.uiItem.HomeTodayScheduleItem
import com.hugg.feature.util.TimeFormatter
import com.hugg.feature.util.UserInfo
import com.hugg.feature.util.onThrottleClick
import com.hugg.home.R


@OptIn(ExperimentalPagerApi::class)
@Composable
fun HomeContainer(
    navigateGoToCalendarDetail : (Long) -> Unit = {},
    navigateGoToChallenge : () -> Unit = {},
    navigateGoToNotification : () -> Unit = {},
    navigateGoToDailyHugg : () -> Unit = {},
    viewModel: HomeViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val pagerState = rememberPagerState()
    val interactionSource = remember { MutableInteractionSource() }

    LaunchedEffect(Unit){
        viewModel.getHome()
    }

    LaunchedEffect(uiState.todayScheduleList) {
        val initialPage = uiState.todayScheduleList.indexOfFirst { it.isNearlyNowTime }
        if (initialPage != -1) {
            pagerState.scrollToPage(initialPage)
        }
    }

    HomeScreen(
        uiState = uiState,
        pagerState = pagerState,
        interactionSource = interactionSource,
        navigateGoToCalendarDetail = navigateGoToCalendarDetail,
        navigateGoToChallenge = navigateGoToChallenge,
        navigateGoToNotification = navigateGoToNotification,
        navigateGoToDailyHugg = navigateGoToDailyHugg,
        onClickTodo = { id -> viewModel.onClickTodo(id) }
    )
}

@OptIn(ExperimentalPagerApi::class)
@Composable
fun HomeScreen(
    uiState : HomePageState = HomePageState(),
    pagerState : PagerState = rememberPagerState(),
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    navigateGoToCalendarDetail : (Long) -> Unit = {},
    onClickTodo: (Long) -> Unit = {},
    navigateGoToChallenge : () -> Unit = {},
    navigateGoToNotification : () -> Unit = {},
    navigateGoToDailyHugg : () -> Unit = {},
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Background),
    ) {

        TopBar(
            leftItemType = TopBarLeftType.LOGO,
            rightItemType = TopBarRightType.NOTIFICATION,
            rightBtnClicked = navigateGoToNotification
        )

        LazyColumn{
            item {
                Spacer(modifier = Modifier.size(25.dp))

                TodayRecordHorizontalPager(
                    itemList = uiState.todayScheduleList,
                    pagerState = pagerState,
                    interactionSource = interactionSource,
                    onClickTodo = onClickTodo,
                    onClickDetail = navigateGoToCalendarDetail
                )
            }

            item {
                Spacer(modifier = Modifier.size(32.dp))

                Box(
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                        .aspectRatio(343f / 88f)
                        .background(color = Gs80, shape = RoundedCornerShape(8.dp))
                        .fillMaxWidth()
                )
            }

            item {
                Spacer(modifier = Modifier.size(32.dp))

                if(UserInfo.info.genderType == GenderType.FEMALE){
                    MyChallengeView(
                        challengeList = uiState.challengeList,
                        navigateGoToChallenge = navigateGoToChallenge,
                        interactionSource = interactionSource
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalPagerApi::class)
@Composable
fun TodayRecordHorizontalPager(
    itemList : List<HomeTodayScheduleCardVo>,
    pagerState: PagerState,
    interactionSource: MutableInteractionSource,
    onClickTodo : (Long) -> Unit = {},
    onClickDetail : (Long) -> Unit = {},
){
    val configuration = LocalConfiguration.current
    val screenWidthDp = configuration.screenWidthDp.dp
    val itemWidth = 285.dp
    val today = TimeFormatter.getToday()
    Text(
        modifier = Modifier.padding(start = 16.dp),
        text = if(UserInfo.info.genderType == GenderType.FEMALE) String.format(HOME_TODAY_SCHEDULE_FEMALE_NAME, UserInfo.info.name)
                else String.format(HOME_TODAY_SCHEDULE_MALE_NAME, UserInfo.info.spouse, UserInfo.info.name),
        color = Black,
        style = h2()
    )

    Text(
        modifier = Modifier
            .padding(start = 16.dp)
            .offset(y = (-2).dp),
        text = String.format(HOME_TODAY_RECORD, TimeFormatter.getMonth(today), TimeFormatter.getDay(today)),
        color = Black,
        style = h2()
    )

    Spacer(modifier = Modifier.size(8.dp))

    if(itemList.isEmpty()) {
        Image(
            modifier = Modifier.padding(start = 16.dp),
            painter = painterResource(id = com.hugg.feature.R.drawable.ic_empty_today_schedule),
            contentDescription = null
        )
    }
    else{
        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.CenterStart
        ){
            HorizontalPager(
                count = itemList.size,
                state = pagerState,
                contentPadding = PaddingValues(start = 16.dp, end = screenWidthDp - itemWidth),
                modifier = Modifier.fillMaxWidth(),
                itemSpacing = 8.dp
            ) { page ->
                HomeTodayScheduleItem(
                    item = itemList[page],
                    interactionSource = interactionSource,
                    onClickDetail = onClickDetail,
                    onClickTodo = onClickTodo
                )
            }
        }
    }
}

@Composable
fun MyChallengeView(
    challengeList : List<MyChallengeListItemVo> = emptyList(),
    navigateGoToChallenge: () -> Unit = {},
    interactionSource: MutableInteractionSource
){
    Row(
        modifier = Modifier.padding(horizontal = 16.dp)
    ) {
        Text(
            text = HOME_MY_CHALLENGE,
            style = h2(),
            color = Gs90
        )

        Spacer(modifier = Modifier.weight(1f))

        Image(
            modifier = Modifier
                .padding(top = 1.dp)
                .onThrottleClick(
                    onClick = navigateGoToChallenge,
                    interactionSource = interactionSource
                ),
            painter = painterResource(id = com.hugg.feature.R.drawable.ic_right_arrow_navigate_gs_50),
            contentDescription = null,
        )
    }

    Spacer(modifier = Modifier.size(8.dp))

    if(challengeList.isEmpty()) {
        EmptyChallengeView(
            navigateGoToChallenge = navigateGoToChallenge,
            interactionSource = interactionSource
        )
    }
}

@Composable
fun EmptyChallengeView(
    navigateGoToChallenge: () -> Unit = {},
    interactionSource: MutableInteractionSource
){
    Column(
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .fillMaxWidth()
            .background(color = White, shape = RoundedCornerShape(6.dp))
            .padding(top = 22.dp, bottom = 18.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            text = HOME_EMPTY_MY_CHALLENGE_TITLE,
            style = h4(),
            color = Gs70
        )

        Spacer(modifier = Modifier.size(4.dp))

        Text(
            text = HOME_EMPTY_MY_CHALLENGE_CONTENT,
            style = p3(),
            color = Gs50
        )

        Spacer(modifier = Modifier.size(16.dp))

        Box(
            modifier = Modifier
                .border(width = 1.dp, color = Gs10, shape = RoundedCornerShape(6.dp))
                .padding(horizontal = 24.dp, vertical = 5.dp)
                .onThrottleClick(
                    onClick = navigateGoToChallenge,
                    interactionSource = interactionSource
                )
        ){
            Text(
                text = HOME_PARTICIPATE_CHALLENGE,
                style = h4(),
                color = Gs70
            )
        }
    }
}

@OptIn(ExperimentalPagerApi::class)
@Preview
@Composable
internal fun PreviewMainContainer() {
    HomeScreen()
}