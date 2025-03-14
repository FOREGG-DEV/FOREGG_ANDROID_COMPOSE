package com.hugg.calendar.calendarMain

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import com.google.accompanist.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
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
import com.hugg.domain.model.enums.CreateOrEditType
import com.hugg.domain.model.enums.DayType
import com.hugg.domain.model.enums.GenderType
import com.hugg.domain.model.enums.RecordType
import com.hugg.domain.model.enums.TopBarMiddleType
import com.hugg.domain.model.vo.calendar.CalendarDayVo
import com.hugg.feature.component.CancelBtn
import com.hugg.feature.component.HuggText
import com.hugg.feature.component.PlusBtn
import com.hugg.feature.uiItem.RemoteYearMonth
import com.hugg.feature.component.TopBar
import com.hugg.feature.theme.*
import com.hugg.feature.uiItem.ScheduleDetailItem
import com.hugg.feature.util.ForeggLog
import com.hugg.feature.util.HuggToast
import com.hugg.feature.util.TimeFormatter
import com.hugg.feature.util.UserInfo
import com.hugg.feature.util.onThrottleClick


@Composable
fun CalendarContainer(
    navigateCreateSchedule : (CreateOrEditType, RecordType, Long, String) -> Unit = {_, _, _, _ -> },
    viewModel: CalendarViewModel = hiltViewModel()
) {

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val context = LocalContext.current
    val interactionSource = remember { MutableInteractionSource() }

    CalendarScreen(
        onClickPrevMonthBtn = { viewModel.onClickPrevMonth() },
        onClickNextMonthBtn = { viewModel.onClickNextMonth() },
        onClickDay = { position -> viewModel.onClickDay(position) },
        onClickCancel = { viewModel.onClickDialogCancel() },
        onClickCreateCancelScheduleBtn = { viewModel.onClickCreateCancelScheduleBtn() },
        onClickCreateScheduleBtn = { type, size, day -> viewModel.onClickCreateScheduleBtn(type, size, day)},
        onClickEditScheduleBtn = { id, recordType -> navigateCreateSchedule(CreateOrEditType.EDIT, recordType, id, TimeFormatter.getToday()) },
        uiState = uiState,
        interactionSource = interactionSource
    )

    LaunchedEffect(Unit){
        viewModel.setCalendar()
    }

    LaunchedEffect(Unit){
        viewModel.eventFlow.collect { event ->
            when(event){
                is CalendarEvent.GoToCreateSchedule -> { navigateCreateSchedule(CreateOrEditType.CREATE, event.type, -1, event.day) }
                CalendarEvent.ShowErrorMaxScheduleEvent -> HuggToast.createToast(context, CALENDAR_MAX_SCHEDULE, true).show()
            }
        }
    }
}

@OptIn(ExperimentalPagerApi::class)
@Composable
fun CalendarScreen(
    scrollState: ScrollState = rememberScrollState(),
    onClickPrevMonthBtn : () -> Unit = {},
    onClickNextMonthBtn : () -> Unit = {},
    onClickDay : ( Int ) -> Unit = {},
    onClickCancel: () -> Unit = {},
    onClickCreateCancelScheduleBtn: () -> Unit = {},
    onClickCreateScheduleBtn: (RecordType, Int, String) -> Unit = {_,_,_ -> },
    onClickEditScheduleBtn : (Long, RecordType) -> Unit = {_, _ -> },
    uiState : CalendarPageState = CalendarPageState(),
    interactionSource : MutableInteractionSource = remember { MutableInteractionSource() }
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Background),
    ) {
        TopBar(
            middleItemType = TopBarMiddleType.TEXT,
            middleText = WORD_CALENDAR,
            interactionSource = interactionSource
        )

        Column(
            modifier = Modifier
                .verticalScroll(scrollState)
        ) {
            Spacer(modifier = Modifier.size(12.dp))

            RemoteYearMonth(
                onClickPrevMonthBtn = onClickPrevMonthBtn,
                onClickNextMonthBtn = onClickNextMonthBtn,
                date = uiState.selectedYearMonth,
                interactionSource = interactionSource
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
                                position = index,
                                interactionSource = interactionSource
                            )
                        } else {
                            CalendarDayItem(
                                expand = expand,
                                showDivideLine = true,
                                item = item,
                                isClicked = onClickDay,
                                position = index,
                                interactionSource = interactionSource
                            )
                        }
                    }
                }
            }
        }
    }

    if(uiState.isShowDetailDialog) ScheduleDetailDialog(
        uiState = uiState,
        pagerState = rememberPagerState(initialPage = uiState.clickedPosition),
        onClickCancel = onClickCancel,
        onClickCreateCancelScheduleBtn = onClickCreateCancelScheduleBtn,
        onClickCreateScheduleBtn = onClickCreateScheduleBtn,
        onClickEditScheduleBtn = onClickEditScheduleBtn,
        interactionSource = interactionSource
    )
}

@Composable
fun CalendarHeadItem(
    item : CalendarDayVo = CalendarDayVo()
){
    HuggText(
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
    isClicked : ( Int ) -> Unit = {},
    position : Int = 0,
    interactionSource : MutableInteractionSource
){
    Column(
        modifier = Modifier
            .clickable(
                onClick = { isClicked(position) },
                interactionSource = interactionSource,
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
            HuggText(
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

            if(scheduleDetailVo.blankCount != index && scheduleDetailVo.isContinueSchedule) {
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

            val text = when(scheduleDetailVo.recordType){
                RecordType.MEDICINE -> "${scheduleDetailVo.dose + CALENDAR_MEDICINE_UNIT} / ${scheduleDetailVo.name}"
                RecordType.INJECTION -> "${scheduleDetailVo.dose + CALENDAR_INJECTION_UNIT} / ${scheduleDetailVo.name}"
                else -> scheduleDetailVo.name
            }

            Box(
                modifier = Modifier
                    .background(background)
                    .padding(horizontal = 4.dp)
                    .fillMaxWidth()
                    .height(14.dp),
                contentAlignment = Alignment.CenterStart
            ){
                if(!scheduleDetailVo.isContinueSchedule || scheduleDetailVo.isStartContinueSchedule || TimeFormatter.getKoreanFullDayOfWeek(scheduleDetailVo.date.toString()) == "일요일") HuggText(
                    text = text,
                    color = Gs70,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    style = HuggTypography.p5,
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
fun ScheduleDetailDialog(
    uiState: CalendarPageState = CalendarPageState(),
    pagerState : PagerState = rememberPagerState(),
    onClickCancel: () -> Unit = {},
    onClickCreateCancelScheduleBtn: () -> Unit = {},
    onClickEditScheduleBtn : (Long, RecordType) -> Unit = {_, _ -> },
    onClickCreateScheduleBtn: (RecordType, Int, String) -> Unit = {_,_,_ -> },
    interactionSource : MutableInteractionSource
) {
    Dialog(
        onDismissRequest = onClickCancel,
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        HorizontalPager(
            count = uiState.calendarDayList.size,
            state = pagerState,
        ) { page ->
            ScheduleDialogPagerItem(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .clip(RoundedCornerShape(20.dp))
                    .background(color = White, shape = RoundedCornerShape(20.dp))
                    .height(454.dp),
                calendarDayVo = uiState.calendarDayList[page],
                onClickCreateCancelScheduleBtn = onClickCreateCancelScheduleBtn,
                uiState = uiState,
                onClickCreateScheduleBtn = onClickCreateScheduleBtn,
                onClickEditScheduleBtn = onClickEditScheduleBtn,
                interactionSource = interactionSource
            )
        }
    }
}

@Composable
fun ScheduleDialogPagerItem(
    modifier : Modifier = Modifier,
    calendarDayVo: CalendarDayVo = CalendarDayVo(),
    uiState: CalendarPageState = CalendarPageState(),
    onClickCreateCancelScheduleBtn : () -> Unit = {},
    onClickEditScheduleBtn : (Long, RecordType) -> Unit = {_, _ -> },
    onClickCreateScheduleBtn: (RecordType, Int, String) -> Unit = {_,_,_ -> },
    interactionSource : MutableInteractionSource
) {
    Column(
        modifier = modifier
    ) {
        Spacer(modifier = Modifier.size(20.dp))

        HuggText(
            modifier = Modifier
                .padding(start = 16.dp),
            text = calendarDayVo.realDate,
            style = HuggTypography.h2,
            color = Gs80
        )

        Spacer(modifier = Modifier.size(16.dp))

        if (calendarDayVo.scheduleList.isEmpty()) HuggText(
            modifier = Modifier
                .padding(start = 16.dp),
            text = CALENDAR_EMPTY_SCHEDULE,
            style = HuggTypography.h4,
            color = Gs50
        )
        else LazyColumn {
            itemsIndexed(
                items = calendarDayVo.scheduleList,
                key = { _, scheduleVo ->
                    scheduleVo.id
                }
            ) { index, scheduleVo ->
                ScheduleDetailItem(
                    scheduleDetailVo = scheduleVo,
                    isLastItem = calendarDayVo.scheduleList.size - 1 == index,
                    onClickEditScheduleBtn = onClickEditScheduleBtn,
                    interactionSource = interactionSource
                )
            }
        }
    }

    DialogNormalMode(
        uiState = uiState,
        onClickCreateCancelScheduleBtn = onClickCreateCancelScheduleBtn,
        interactionSource = interactionSource
    )

    DialogCreateMode(
        uiState = uiState,
        onClickCreateCancelScheduleBtn = onClickCreateCancelScheduleBtn,
        onClickCreateScheduleBtn = { recordType, _ -> onClickCreateScheduleBtn(recordType, calendarDayVo.scheduleList.size, calendarDayVo.day)},
        interactionSource = interactionSource
    )
}

@Composable
fun DialogNormalMode(
    uiState : CalendarPageState = CalendarPageState(),
    onClickCreateCancelScheduleBtn : () -> Unit = {},
    interactionSource: MutableInteractionSource
){
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .height(454.dp)
    ) {
        Spacer(modifier = Modifier.weight(1f))

        AnimatedVisibility(
            visible = !uiState.isCreateMode,
            enter = fadeIn(animationSpec = tween(300)),
            exit = fadeOut(animationSpec = tween(300))
        ) {

            Row(
                modifier = Modifier.padding(end = 16.dp),
            ) {
                Spacer(modifier = Modifier.weight(1f))
                PlusBtn(onClickBtn = onClickCreateCancelScheduleBtn, interactionSource = interactionSource)
            }
        }

        Spacer(modifier = Modifier.size(16.dp))
    }
}

@Composable
fun DialogCreateMode(
    uiState : CalendarPageState = CalendarPageState(),
    onClickCreateCancelScheduleBtn : () -> Unit = {},
    onClickCreateScheduleBtn: (RecordType, Int) -> Unit = {_,_ -> },
    interactionSource : MutableInteractionSource
){
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .height(454.dp)
    ) {
        Spacer(modifier = Modifier.weight(1f))

        AnimatedVisibility(
            visible = uiState.isCreateMode,
            enter = fadeIn(animationSpec = tween(300)),
            exit = fadeOut(animationSpec = tween(300))
        ) {

            LazyRow(
                modifier = Modifier
                    .padding(start = 14.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                item {
                    CancelBtn(onClickBtn = onClickCreateCancelScheduleBtn, interactionSource = interactionSource)
                    Spacer(modifier = Modifier.size(8.dp))
                }
                item {
                    if(UserInfo.info.genderType == GenderType.FEMALE) CreateScheduleBtnByType(RecordType.HOSPITAL, onClickCreateScheduleBtn, interactionSource)
                }
                item {
                    if(UserInfo.info.genderType == GenderType.FEMALE) CreateScheduleBtnByType(RecordType.INJECTION, onClickCreateScheduleBtn, interactionSource)
                }
                item {
                    if(UserInfo.info.genderType == GenderType.FEMALE) CreateScheduleBtnByType(RecordType.MEDICINE, onClickCreateScheduleBtn, interactionSource)
                }
                item {
                    CreateScheduleBtnByType(RecordType.ETC, onClickCreateScheduleBtn, interactionSource)
                    Spacer(modifier = Modifier.size(16.dp))
                }
            }
        }

        Spacer(modifier = Modifier.size(16.dp))
    }
}

@Composable
fun CreateScheduleBtnByType(
    type : RecordType = RecordType.ETC,
    onClickCreateScheduleBtn: (RecordType, Int) -> Unit = {_,_ -> },
    interactionSource : MutableInteractionSource
){
    val text = when(type){
        RecordType.MEDICINE -> WORD_MEDICINE
        RecordType.INJECTION -> WORD_INJECTION
        RecordType.HOSPITAL -> WORD_HOSPITAL
        RecordType.ETC -> WORD_ETC
    }
    Box(
        modifier = Modifier
            .border(
                width = 1.dp,
                color = MainNormal,
                shape = RoundedCornerShape(999.dp)
            )
            .onThrottleClick(
                onClick = { onClickCreateScheduleBtn(type, 0) },
                interactionSource = interactionSource
            )
            .width(60.dp)
            .background(color = White)
            .padding(vertical = 6.dp),
        contentAlignment = Alignment.Center
    ) {
        HuggText(
            text = text,
            style = HuggTypography.h4,
            color = Gs80
        )
    }

    Spacer(modifier = Modifier.size(4.dp))
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