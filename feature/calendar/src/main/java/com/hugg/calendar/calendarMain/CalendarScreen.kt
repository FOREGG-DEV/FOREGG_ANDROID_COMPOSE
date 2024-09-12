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
import com.hugg.domain.model.enums.CreateOrEditType
import com.hugg.domain.model.enums.DayType
import com.hugg.domain.model.enums.RecordType
import com.hugg.domain.model.enums.TopBarMiddleType
import com.hugg.domain.model.vo.calendar.CalendarDayVo
import com.hugg.feature.component.CancelBtn
import com.hugg.feature.component.PlusBtn
import com.hugg.feature.uiItem.RemoteYearMonth
import com.hugg.feature.component.TopBar
import com.hugg.feature.theme.*
import com.hugg.feature.uiItem.ScheduleDetailItem


@Composable
fun CalendarContainer(
    navigateCreateSchedule : (CreateOrEditType, RecordType, Long) -> Unit = {_, _, _ -> },
    viewModel: CalendarViewModel = hiltViewModel()
) {

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val interactionSource = remember { MutableInteractionSource() }

    CalendarScreen(
        onClickPrevMonthBtn = { viewModel.onClickPrevMonth() },
        onClickNextMonthBtn = { viewModel.onClickNextMonth() },
        onClickDay = { position -> viewModel.onClickDay(position) },
        onClickCancel = { viewModel.onClickDialogCancel() },
        onClickCreateCancelScheduleBtn = { viewModel.onClickCreateCancelScheduleBtn() },
        onClickCreateScheduleBtn = { type, size -> viewModel.onClickCreateScheduleBtn(type, size)},
        uiState = uiState,
        interactionSource = interactionSource
    )

    LaunchedEffect(Unit){
        viewModel.eventFlow.collect { event ->
            when(event){
                is CalendarEvent.GoToCreateSchedule -> { navigateCreateSchedule(CreateOrEditType.CREATE, event.type, -1) }
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
    onClickCreateScheduleBtn: (RecordType, Int) -> Unit = {_,_ -> },
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
        interactionSource = interactionSource
    )
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
                if(!scheduleDetailVo.isContinueSchedule) Text(
                    text = text,
                    color = Gs70,
                    maxLines = 1,
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
    onClickCreateScheduleBtn: (RecordType, Int) -> Unit = {_,_ -> },
    interactionSource : MutableInteractionSource
) {
    Dialog(
        onDismissRequest = onClickCancel,
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        Column {

            Box(
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .fillMaxWidth()
                    .height(40.dp)
                    .clickable(
                        onClick = onClickCancel,
                        interactionSource = interactionSource,
                        indication = null
                    )
                    .background(
                        if (uiState.showErrorMaxScheduleSnackBar) ErrorSnackBar else Color.Transparent,
                        shape = RoundedCornerShape(8.dp)
                    ),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = CALENDAR_MAX_SCHEDULE,
                    style = HuggTypography.p2,
                    color = if (uiState.showErrorMaxScheduleSnackBar) White else Color.Transparent
                )
            }

            Spacer(
                modifier = Modifier
                    .size(16.dp)
                    .clickable(
                        onClick = onClickCancel,
                        interactionSource = interactionSource,
                        indication = null
                    )
            )

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
                    interactionSource = interactionSource
                )
            }
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
                    .clickable(
                        onClick = onClickCancel,
                        interactionSource = interactionSource,
                        indication = null
                    )
                    .background(Color.Transparent)
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
    onClickCreateScheduleBtn: (RecordType, Int) -> Unit = {_,_ -> },
    interactionSource : MutableInteractionSource
) {
    Column(
        modifier = modifier
    ) {
        Spacer(modifier = Modifier.size(20.dp))

        Text(
            modifier = Modifier
                .padding(start = 16.dp),
            text = calendarDayVo.realDate,
            style = HuggTypography.h2,
            color = Gs80
        )

        Spacer(modifier = Modifier.size(16.dp))

        if (calendarDayVo.scheduleList.isEmpty()) Text(
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
                    isLastItem = calendarDayVo.scheduleList.size - 1 == index
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
        onClickCreateScheduleBtn = { recordType, _ -> onClickCreateScheduleBtn(recordType, calendarDayVo.scheduleList.size)},
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

            Row(
                modifier = Modifier
                    .padding(start = 14.dp)
                    .horizontalScroll(rememberScrollState()),
                verticalAlignment = Alignment.CenterVertically
            ) {
                CancelBtn(onClickBtn = onClickCreateCancelScheduleBtn, interactionSource = interactionSource)

                Spacer(modifier = Modifier.size(8.dp))

                CreateScheduleBtnByType(RecordType.HOSPITAL, onClickCreateScheduleBtn, interactionSource)
                CreateScheduleBtnByType(RecordType.INJECTION, onClickCreateScheduleBtn, interactionSource)
                CreateScheduleBtnByType(RecordType.MEDICINE, onClickCreateScheduleBtn, interactionSource)
                CreateScheduleBtnByType(RecordType.ETC, onClickCreateScheduleBtn, interactionSource)

                Spacer(modifier = Modifier.size(16.dp))
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
    Row(
        modifier = Modifier
            .border(
                width = 1.dp,
                color = MainNormal,
                shape = RoundedCornerShape(999.dp)
            )
            .clickable(
                onClick = { onClickCreateScheduleBtn(type, 0) },
                interactionSource = interactionSource,
                indication = null
            )
            .background(color = White)
            .padding(horizontal = 9.dp, vertical = 6.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(16.dp)
                .background(Gs10)
        )
        Spacer(modifier = Modifier.size(4.dp))
        Text(
            text = text,
            style = HuggTypography.h4,
            color = Gs80
        )
    }

    Spacer(modifier = Modifier.size(8.dp))
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