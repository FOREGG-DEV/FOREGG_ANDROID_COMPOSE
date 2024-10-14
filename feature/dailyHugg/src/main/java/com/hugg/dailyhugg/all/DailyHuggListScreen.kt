package com.hugg.dailyhugg.all

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.hugg.domain.model.enums.TopBarLeftType
import com.hugg.domain.model.enums.TopBarMiddleType
import com.hugg.domain.model.response.dailyHugg.DailyHuggListItemVo
import com.hugg.feature.R
import com.hugg.feature.component.HuggText
import com.hugg.feature.component.TopBar
import com.hugg.feature.theme.Background
import com.hugg.feature.theme.DAILY_HUGG_LIST_EMPTY_BUBBLE
import com.hugg.feature.theme.DAILY_HUGG_LIST_TITLE
import com.hugg.feature.theme.FEMALE
import com.hugg.feature.theme.Gs60
import com.hugg.feature.theme.GsBlack
import com.hugg.feature.theme.GsWhite
import com.hugg.feature.theme.HuggTypography
import com.hugg.feature.theme.MainNormal
import com.hugg.feature.theme.White
import com.hugg.feature.util.TimeFormatter

@Composable
fun DailyHuggListScreen(
    popScreen: () -> Unit,
    goToDailyHuggDetail: (String) -> Unit = {}
) {
    val viewModel: DailyHuggListViewModel = hiltViewModel()
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val listState = rememberLazyListState()
    val interactionSource = remember { MutableInteractionSource() }

    LaunchedEffect(Unit) {
        viewModel.getDailyHuggList(uiState.currentPage)
    }

    LaunchedEffect(listState) {
        snapshotFlow { listState.layoutInfo.visibleItemsInfo }
            .collect { visibleItems ->
                val lastVisibleItem = visibleItems.lastOrNull()
                if (lastVisibleItem != null && lastVisibleItem.index == uiState.dailyHuggList.size - 1) {
                    if (uiState.currentPage < uiState.totalPages) {
                        viewModel.getDailyHuggList(uiState.currentPage + 1)
                    }
                }
            }
    }

    DailyHuggListContent(
        uiState = uiState,
        popScreen = popScreen,
        listState = listState,
        goToDailyHuggDetail = goToDailyHuggDetail,
        interactionSource = interactionSource
    )
}

@Composable
fun DailyHuggListContent(
    uiState: DailyHuggListPageState = DailyHuggListPageState(),
    popScreen: () -> Unit = {},
    listState: LazyListState,
    goToDailyHuggDetail: (String) -> Unit = {},
    interactionSource: MutableInteractionSource
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Background),
    ) {
        TopBar(
            leftItemType = TopBarLeftType.BACK,
            middleItemType = TopBarMiddleType.TEXT,
            middleText = DAILY_HUGG_LIST_TITLE,
            leftBtnClicked = { popScreen() }
        )

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp)
                .padding(top = 24.dp)
        ) {
            if (uiState.dailyHuggList.isEmpty()) {
                EmptyDailyHuggListItem()
            } else {
                DailyHuggList(
                    items = uiState.dailyHuggList,
                    listState = listState,
                    goToDailyHuggDetail = goToDailyHuggDetail,
                    interactionSource = interactionSource
                )
            }
        }
    }
}

@Composable
fun EmptyDailyHuggListItem() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .border(BorderStroke(1.dp, FEMALE), shape = RoundedCornerShape(8.dp))
            .clip(RoundedCornerShape(8.dp))
            .aspectRatio(343f / 169f)
            .background(White)
    ) {
        Box(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(end = 93.dp, bottom = 45.dp)
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_msg_bubble_female),
                contentDescription = "",
                tint = Color.Unspecified,
            )

            HuggText(
                text = DAILY_HUGG_LIST_EMPTY_BUBBLE,
                textAlign = TextAlign.Center,
                color = Gs60,
                style = HuggTypography.h2,
                modifier = Modifier
                    .align(Alignment.Center)
                    .padding(end = 14.dp)
            )
        }

        Icon(
            painter = painterResource(id = R.drawable.ic_hugging_female),
            contentDescription = "",
            tint = Color.Unspecified,
            modifier = Modifier.align(Alignment.BottomEnd)
        )
    }
}

@Composable
fun DailyHuggList(
    items: List<DailyHuggListItemVo> = emptyList(),
    listState: LazyListState,
    goToDailyHuggDetail: (String) -> Unit = {},
    interactionSource: MutableInteractionSource
) {
    LazyColumn(
        state = listState,
        modifier = Modifier
            .fillMaxSize()
    ) {
        items(items, key = {it.id} ) { item ->
            DailyHuggListItem(
                item = item,
                goToDailyHuggDetail = goToDailyHuggDetail,
                interactionSource = interactionSource
            )
            Spacer(modifier = Modifier.height(12.dp))
        }
    }
}

@Composable
fun DailyHuggListItem(
    item: DailyHuggListItemVo = DailyHuggListItemVo(),
    goToDailyHuggDetail: (String) -> Unit = {},
    interactionSource: MutableInteractionSource
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(60.dp)
            .clip(RoundedCornerShape(8.dp))
            .clickable(
                interactionSource = interactionSource,
                indication = null,
                onClick = {
                    goToDailyHuggDetail(item.date)
                }
            )
    ) {
        Box(
            modifier = Modifier
                .fillMaxHeight()
                .clip(RoundedCornerShape(topStart = 8.dp, bottomStart = 8.dp))
                .background(MainNormal),
            contentAlignment = Alignment.Center
        ) {
            HuggText(
                text = TimeFormatter.getMonthDayWithSlash(item.date),
                style = HuggTypography.h4,
                color = GsWhite,
                modifier = Modifier
                    .padding(start = 7.dp, end = 6.dp)
            )
        }

        Row(
            modifier = Modifier
                .fillMaxSize()
                .background(GsWhite),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Spacer(modifier = Modifier.width(8.dp))

            Icon(
                painter = painterResource(R.drawable.ic_hugging_female),
                contentDescription = "",
                modifier = Modifier
                    .size(20.dp),
                tint = Color.Unspecified
            )

            Spacer(modifier = Modifier.width(8.dp))

            HuggText(
                text = item.content,
                style = HuggTypography.p3_l,
                color = GsBlack,
                maxLines = 2
            )
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PreviewDailyHuggListContent() {
    DailyHuggListContent(
        uiState = DailyHuggListPageState(
            dailyHuggList = listOf(
                DailyHuggListItemVo(
                    date = "09/22",
                    content = "askldjnbopqauwrhgo;iwahedrnfgwouejdwsaeidgruaslkjgaowerig"
                ),
                DailyHuggListItemVo(
                    date = "09/22",
                    content = "askldjnbopqauwrhgo;iwahedrnfgwouejdwsaeidgruaslkjgaowerig"
                )
            )
        ),
        listState = rememberLazyListState(),
        interactionSource = remember { MutableInteractionSource() }
    )
}