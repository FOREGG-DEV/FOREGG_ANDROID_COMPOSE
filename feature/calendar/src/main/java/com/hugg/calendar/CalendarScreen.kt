package com.hugg.calendar

import androidx.compose.foundation.Image
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.scrollable
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
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
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
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.cheonjaeung.compose.grid.SimpleGridCells
import com.cheonjaeung.compose.grid.VerticalGrid
import com.hugg.domain.model.enums.DayType
import com.hugg.domain.model.enums.TopBarMiddleType
import com.hugg.domain.model.vo.calendar.CalendarDayVo
import com.hugg.feature.R
import com.hugg.feature.component.TopBar
import com.hugg.feature.theme.*


@Composable
fun CalendarContainer(
    navigateCreateSchedule : () -> Unit = {},
    viewModel: CalendarViewModel = hiltViewModel()
) {

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    CalendarScreen(
        onClickPrevMonthBtn = { viewModel.onClickPrevMonth() },
        onClickNextMonthBtn = { viewModel.onClickNextMonth() },
        uiState = uiState
    )
}

@Composable
fun CalendarScreen(
    scrollState: ScrollState = rememberScrollState(),
    onClickPrevMonthBtn : () -> Unit = {},
    onClickNextMonthBtn : () -> Unit = {},
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
                    uiState.calendarDayList.forEach {
                        CalendarDayItem(
                            item = it
                        )
                    }
                }
            }
        }
    }
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
    item : CalendarDayVo = CalendarDayVo()
){
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .defaultMinSize(minHeight = 106.dp),
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

        Spacer(modifier = Modifier.weight(1f)) // 남은 공간을 채우는 Spacer

        Box(
            modifier = Modifier
                .background(Gs10)
                .fillMaxWidth()
                .height(1.dp) // Box의 높이를 설정
        )
    }
}

fun getDayTextColor(calendarDayVo: CalendarDayVo) : Color {
    if(calendarDayVo.dayType == DayType.PREV_NEXT && calendarDayVo.isSunday) return Sub
    if(calendarDayVo.dayType == DayType.PREV_NEXT) return Gs50
    if(calendarDayVo.isToday) return White
    if(calendarDayVo.isSunday) return Sunday

    return Gs70
}

@Preview
@Composable
internal fun PreviewMainContainer() {
    CalendarScreen()
}

@Preview
@Composable
internal fun PreviewDay() {
    CalendarDayItem(
        CalendarDayVo(day = "20", isToday = true)
    )
}