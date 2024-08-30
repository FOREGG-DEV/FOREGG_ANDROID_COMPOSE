package com.hugg.calendar

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import com.google.accompanist.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.cheonjaeung.compose.grid.SimpleGridCells
import com.cheonjaeung.compose.grid.VerticalGrid
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.PagerState
import com.hugg.domain.model.enums.DayType
import com.hugg.domain.model.enums.RecordType
import com.hugg.domain.model.enums.TopBarMiddleType
import com.hugg.domain.model.vo.calendar.CalendarDayVo
import com.hugg.feature.R
import com.hugg.feature.component.TopBar
import com.hugg.feature.theme.*
import com.hugg.feature.uiItem.OnBoardingItem


@OptIn(ExperimentalPagerApi::class)
@Composable
fun CalendarContainer(
    navigateCreateSchedule : () -> Unit = {},
    viewModel: CalendarViewModel = hiltViewModel()
) {

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    CalendarScreen(
        onClickPrevMonthBtn = { viewModel.onClickPrevMonth() },
        onClickNextMonthBtn = { viewModel.onClickNextMonth() },
        onClickDay = { viewModel.onClickDay() },
        onClickCancel = { viewModel.onClickDialogCancel() },
        uiState = uiState
    )
}

@OptIn(ExperimentalPagerApi::class)
@Composable
fun CalendarScreen(
    scrollState: ScrollState = rememberScrollState(),
    onClickPrevMonthBtn : () -> Unit = {},
    onClickNextMonthBtn : () -> Unit = {},
    onClickDay : () -> Unit = {},
    onClickCancel: () -> Unit = {},
    uiState : CalendarPageState = CalendarPageState()
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Background),
    ) {
        TopBar(
            middleItemType = TopBarMiddleType.TEXT,
            middleText = WORD_CALENDAR
        )

        Column(
            modifier = Modifier
                .verticalScroll(scrollState)
        ) {
            Spacer(modifier = Modifier.size(12.dp))

            RemoteCalendar(
                onClickPrevMonthBtn = onClickPrevMonthBtn,
                onClickNextMonthBtn = onClickNextMonthBtn,
                uiState = uiState
            )

            Spacer(modifier = Modifier.size(13.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 36.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ){
                uiState.calendarHeadList.forEach {
                    CalendarHeadItem(it)
                }
            }

            Spacer(modifier = Modifier.size(8.dp))

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .background(color = White, shape = RoundedCornerShape(8.dp))
            ){
                VerticalGrid(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    columns = SimpleGridCells.Fixed(7),
                ) {
                    val expand = uiState.calendarDayList.any { it.scheduleList.size >= 6 }
                    uiState.calendarDayList.forEachIndexed { index, item ->
                        if (index >= uiState.calendarDayList.size - 7) {
                            CalendarDayItem(
                                expand = expand,
                                showDivideLine = false,
                                item = item,
                                isClicked = onClickDay,
                                position = index
                            )
                        } else {
                            CalendarDayItem(
                                expand = expand,
                                showDivideLine = true,
                                item = item,
                                isClicked = onClickDay,
                                position = index
                            )
                        }
                    }
                }
            }
        }
    }

    if(uiState.isShowDetailDialog) DetailScheduleDialog(onClickCancel = onClickCancel)
}

@Composable
fun RemoteCalendar(
    onClickPrevMonthBtn : () -> Unit = {},
    onClickNextMonthBtn : () -> Unit = {},
    uiState : CalendarPageState = CalendarPageState()
){
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center
    ) {
        Image(
            modifier = Modifier
                .width(48.dp)
                .height(48.dp)
                .padding(12.dp)
                .clickable(
                    onClick = onClickPrevMonthBtn,
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null
                ),
            imageVector = ImageVector.vectorResource(R.drawable.ic_back_arrow_gs_70),
            contentDescription = null
        )

        Spacer(modifier = Modifier.size(9.dp))

        Text(
            modifier = Modifier.align(Alignment.CenterVertically),
            text = uiState.selectedYearMonth,
            color = Gs90,
            style = HuggTypography.h2
        )

        Spacer(modifier = Modifier.size(9.dp))

        Image(
            modifier = Modifier
                .width(48.dp)
                .height(48.dp)
                .padding(12.dp)
                .graphicsLayer(scaleX = -1f)
                .clickable(
                    onClick = onClickNextMonthBtn,
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null
                ),
            imageVector = ImageVector.vectorResource(R.drawable.ic_back_arrow_gs_70),
            contentDescription = null
        )

        Spacer(modifier = Modifier.size(9.dp))

    }
}

@Composable
fun CalendarHeadItem(
    item : CalendarDayVo = CalendarDayVo()
){
    Text(
        text = item.day,
        color = if(item.isSunday) Sunday else Gs80,
        style = HuggTypography.h4
    )
}

@Composable
fun CalendarDayItem(
    expand : Boolean = false,
    showDivideLine : Boolean = false,
    item : CalendarDayVo = CalendarDayVo(),
    isClicked : () -> Unit = {},
    position : Int = 0
){
    Column(
        modifier = Modifier
            .clickable(
                onClick = isClicked,
                interactionSource = remember { MutableInteractionSource() },
                indication = null
            )
            .fillMaxWidth()
            .height(if (expand) 133.dp else 106.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Spacer(modifier = Modifier.size(5.dp))

        Surface(
            modifier = Modifier
                .width(28.dp),
            color = if(item.isToday) Sub else White,
            shape = RoundedCornerShape(4.dp)
        ) {
            Text(
                textAlign = TextAlign.Center,
                text = item.day,
                color = getDayTextColor(item),
                style = HuggTypography.p2
            )
        }

        Spacer(modifier = Modifier.size(4.dp))

        item.scheduleList.forEachIndexed { index, scheduleDetailVo ->
            val background = when(scheduleDetailVo.recordType){
                RecordType.MEDICINE -> CalendarPill
                RecordType.INJECTION -> CalendarInjection
                RecordType.HOSPITAL -> CalendarHospital
                RecordType.ETC -> CalendarEtc
            }

            if(scheduleDetailVo.blankCount != index && (scheduleDetailVo.isStartContinueSchedule || scheduleDetailVo.isContinueSchedule)) {
                val minusCount = if(index != 0) item.scheduleList[index - 1].blankCount + 1 else 0
                repeat(scheduleDetailVo.blankCount - minusCount) {
                    Box(
                        modifier = Modifier
                            .background(White)
                            .padding(start = 4.dp)
                            .fillMaxWidth()
                            .height(14.dp),
                    )

                    Spacer(modifier = Modifier.size(1.dp))
                }
            }

            Box(
                modifier = Modifier
                    .background(background)
                    .padding(start = 4.dp)
                    .fillMaxWidth()
                    .height(14.dp),
                contentAlignment = Alignment.CenterStart
            ){
                if(!scheduleDetailVo.isContinueSchedule) Text(
                    text = scheduleDetailVo.name,
                    color = Gs70,
                    style = HuggTypography.p5
                )
            }

            Spacer(modifier = Modifier.size(1.dp))
        }

        Spacer(modifier = Modifier.weight(1f))

        if(showDivideLine) Box(
            modifier = Modifier
                .background(Gs10)
                .fillMaxWidth()
                .height(1.dp)
        )
    }
}

@OptIn(ExperimentalPagerApi::class)
@Composable
fun DetailScheduleDialog(
    uiState: CalendarPageState = CalendarPageState(),
    pagerState : PagerState = rememberPagerState(),
    onClickCancel: () -> Unit = {},
) {
//    Dialog(
//        onDismissRequest = onClickCancel,
//        properties = DialogProperties(usePlatformDefaultWidth = false) // 기본 너비 사용 안 함
//    ) {
//        HorizontalPager(
//            count = uiState.calendarDayList.size,
//            state = pagerState,
//        ) { page ->
//            OnBoardingItem(uiState.onboardingList[page].img, uiState.onboardingList[page].title, uiState.onboardingList[page].content)
//        }
//        HorizontalPager(
//            count = 3,
//            state = pagerState,
//        ) { page ->
//            when (page) {
//                0 -> Text("Page 1 Content", modifier = Modifier
//                    .fillMaxWidth()
//                    .padding(horizontal = 16.dp)
//                    .background(color = White, shape = RoundedCornerShape(20.dp))
//                    .height(454.dp))
//                1 -> Text("Page 2 Content", modifier = Modifier
//                    .fillMaxWidth()
//                    .padding(horizontal = 16.dp)
//                    .background(color = White, shape = RoundedCornerShape(20.dp))
//                    .height(454.dp))
//                2 -> Text("Page 3 Content", modifier = Modifier
//                    .fillMaxWidth()
//                    .padding(horizontal = 16.dp)
//                    .background(color = White, shape = RoundedCornerShape(20.dp))
//                    .height(454.dp))
//            }
//        }
//    }
}

fun getDayTextColor(calendarDayVo: CalendarDayVo) : Color {
    if(calendarDayVo.dayType == DayType.PREV_NEXT && calendarDayVo.isSunday) return PrevNextSunday
    if(calendarDayVo.dayType == DayType.PREV_NEXT) return PrevNextNormalDay
    if(calendarDayVo.isToday) return White
    if(calendarDayVo.isSunday) return Sunday

    return Gs70
}

@Preview
@Composable
internal fun PreviewMainContainer() {
    CalendarScreen()
}