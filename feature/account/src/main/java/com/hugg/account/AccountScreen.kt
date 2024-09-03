package com.hugg.account

import android.app.DatePickerDialog
import android.content.Context
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.hugg.account.bottomSheet.DatePickBottomSheet
import com.hugg.domain.model.enums.AccountBottomSheetType
import com.hugg.domain.model.enums.AccountColorType
import com.hugg.domain.model.enums.AccountTabType
import com.hugg.domain.model.enums.TopBarMiddleType
import com.hugg.domain.model.enums.TopBarRightType
import com.hugg.feature.R
import com.hugg.feature.component.HuggTabBar
import com.hugg.feature.component.TopBar
import com.hugg.feature.theme.ACCOUNT_ALL
import com.hugg.feature.theme.ACCOUNT_ALL_EXPENSE
import com.hugg.feature.theme.ACCOUNT_CHOOSE_DATE
import com.hugg.feature.theme.ACCOUNT_DIVIDE_DATE
import com.hugg.feature.theme.ACCOUNT_MONTH
import com.hugg.feature.theme.ACCOUNT_PERSONAL
import com.hugg.feature.theme.ACCOUNT_ROUND
import com.hugg.feature.theme.ACCOUNT_SUBSIDY
import com.hugg.feature.theme.ACCOUNT_SUBSIDY_ALL
import com.hugg.feature.theme.Background
import com.hugg.feature.theme.Black
import com.hugg.feature.theme.CalendarEtc
import com.hugg.feature.theme.CalendarHospital
import com.hugg.feature.theme.CalendarInjection
import com.hugg.feature.theme.CalendarPill
import com.hugg.feature.theme.Gs20
import com.hugg.feature.theme.Gs30
import com.hugg.feature.theme.Gs60
import com.hugg.feature.theme.Gs70
import com.hugg.feature.theme.Gs80
import com.hugg.feature.theme.Gs90
import com.hugg.feature.theme.HuggTypography
import com.hugg.feature.theme.WORD_ACCOUNT
import com.hugg.feature.theme.WORD_CONFIRM
import com.hugg.feature.theme.White
import com.hugg.feature.uiItem.AccountCardItem
import com.hugg.feature.util.TimeFormatter
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import java.util.Calendar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AccountContainer(
    viewModel: AccountViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val scrollState = rememberLazyListState()
    val context = LocalContext.current
    var isFilterAtTop by remember { mutableStateOf(false) }
    val interactionSource = remember { MutableInteractionSource() }

    AccountScreen(
        onClickTab = { type -> viewModel.onClickTabType(type) },
        onClickFilterBox = { filter -> viewModel.onClickFilterBox(filter) },
        uiState = uiState,
        scrollState = scrollState,
        isFilterAtTop = isFilterAtTop,
        onClickDateFilter = { viewModel.onClickBottomSheetOnOff() },
        interactionSource = interactionSource
    )

    LaunchedEffect(scrollState) {
        snapshotFlow { scrollState.firstVisibleItemIndex }
            .distinctUntilChanged()
            .collect { index ->
                isFilterAtTop = index >= 2
            }
    }

    if(uiState.isShowBottomSheet){
        DatePickBottomSheet(
            onClickClose = { viewModel.onClickBottomSheetOnOff() },
            onClickConfirm = { selectedType, startDay, endDay ->
                viewModel.initDay(startDay, endDay)
                viewModel.updateSelectedBottomSheetType(selectedType)
            },
            uiState = uiState,
            context = context
        )
    }
}

@Composable
fun AccountScreen(
    onClickTab: (AccountTabType) -> Unit = {},
    uiState: AccountPageState = AccountPageState(),
    onClickFilterBox: (String) -> Unit = {},
    scrollState: LazyListState = rememberLazyListState(),
    isFilterAtTop : Boolean = false,
    onClickDateFilter : () -> Unit = {},
    interactionSource : MutableInteractionSource = remember { MutableInteractionSource() }
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Background),
    ) {

        TopBar(
            middleItemType = TopBarMiddleType.TEXT,
            middleText = WORD_ACCOUNT,
            rightItemType = TopBarRightType.CREATE,
            interactionSource = interactionSource
        )

        Spacer(modifier = Modifier.size(16.dp))

        LazyColumn(
            state = scrollState,
            modifier = Modifier
                .fillMaxWidth(),
        ) {
            item {
                HuggTabBar(
                    leftText = ACCOUNT_ALL,
                    middleText = ACCOUNT_ROUND,
                    rightText = ACCOUNT_MONTH,
                    onClickLeftTab = { onClickTab(AccountTabType.ALL) },
                    onClickMiddleTab = { onClickTab(AccountTabType.ROUND) },
                    onClickRightTab = { onClickTab(AccountTabType.MONTH) },
                    interactionSource = interactionSource
                )

                Spacer(modifier = Modifier.size(12.dp))
            }

            item {
                AccountTotalBox(
                    uiState = uiState,
                    onClickDateFilter = onClickDateFilter,
                    interactionSource = interactionSource
                )

                Spacer(modifier = Modifier.size(16.dp))
            }

            item {
                AccountItemFilter(
                    uiState = uiState,
                    onClickFilterBox = onClickFilterBox,
                    interactionSource = interactionSource
                )

                Spacer(modifier = Modifier.size(8.dp))
            }

            itemsIndexed(
                items = uiState.accountList,
                key = { _, accountVo ->
                    accountVo.id
                }
            ) { _, accountVo ->
                AccountCardItem(
                    item = accountVo,
                )
            }
        }
    }

    AnimatedVisibility(
        visible = isFilterAtTop,
        enter = fadeIn(animationSpec = tween(200)),
        exit = fadeOut(animationSpec = tween(200))
    ) {
        Column(
            modifier = Modifier
                .padding(top = 73.dp)
                .background(Background)
                .fillMaxWidth()
        ) {
            AccountItemFilter(
                uiState = uiState,
                onClickFilterBox = onClickFilterBox,
                interactionSource = interactionSource
            )

            Box(
                modifier = Modifier
                    .background(color = Background)
                    .fillMaxWidth()
                    .height(8.dp)
            )
        }
    }
}

@Composable
fun AccountTotalBox(
    uiState: AccountPageState = AccountPageState(),
    onClickDateFilter : () -> Unit = {},
    interactionSource : MutableInteractionSource
) {
    Column(
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .fillMaxWidth()
            .background(color = White, shape = RoundedCornerShape(8.dp))
            .padding(start = 12.dp, bottom = 16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Text(
                text = "${TimeFormatter.getDotsDate(uiState.startDay)} - ${TimeFormatter.getDotsDate(uiState.endDay)}",
                style = HuggTypography.h4,
                color = Gs70
            )

            Spacer(modifier = Modifier.weight(1f))

            Image(
                modifier = Modifier
                    .size(48.dp)
                    .padding(12.dp)
                    .clickable(
                        onClick = onClickDateFilter,
                        interactionSource = interactionSource,
                        indication = null
                    ),
                imageVector = ImageVector.vectorResource(id = R.drawable.ic_filter),
                contentDescription = null
            )
        }

        TotalBoxItem(AccountColorType.PERSONAL, uiState)

        Spacer(modifier = Modifier.size(18.dp))

        TotalBoxItem(AccountColorType.ALL, uiState)

        Spacer(modifier = Modifier.size(9.dp))

        Canvas(
            modifier = Modifier
                .padding(end = 11.dp)
                .fillMaxWidth()
                .height((0.3).dp)
        ) {
            drawLine(
                color = Gs30,
                start = androidx.compose.ui.geometry.Offset(0f, 0f),
                end = androidx.compose.ui.geometry.Offset(size.width, 0f),
                strokeWidth = 1.dp.toPx(),
                pathEffect = PathEffect.dashPathEffect(floatArrayOf(4.dp.toPx(), 4.dp.toPx()), 0f)
            )
        }

        Spacer(modifier = Modifier.size(8.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Text(
                text = ACCOUNT_ALL_EXPENSE,
                style = HuggTypography.h2,
                color = Gs90
            )

            Spacer(modifier = Modifier.weight(1f))

            Text(
                text = uiState.totalExpense,
                style = HuggTypography.h2,
                color = Gs90
            )

            Spacer(modifier = Modifier.size(11.dp))
        }
    }
}

@Composable
fun TotalBoxItem(
    colorType: AccountColorType = AccountColorType.PERSONAL,
    uiState: AccountPageState = AccountPageState()
){
    val color = when(colorType){
        AccountColorType.PERSONAL -> CalendarPill
        AccountColorType.ALL -> Gs20
        AccountColorType.BLUE -> CalendarInjection
        AccountColorType.GREEN -> CalendarHospital
        AccountColorType.YELLOW -> CalendarEtc
    }

    val text = when(colorType){
        AccountColorType.PERSONAL -> ACCOUNT_PERSONAL
        AccountColorType.ALL -> ACCOUNT_SUBSIDY_ALL
        else -> ""
    }

    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {

        Box(
            modifier = Modifier
                .size(12.dp)
                .background(color = color, shape = RoundedCornerShape(3.dp))
        )

        Spacer(modifier = Modifier.size(4.dp))

        Text(
            text = text,
            style = HuggTypography.p1,
            color = Gs80
        )

        Spacer(modifier = Modifier.weight(1f))

        Text(
            text = if(colorType == AccountColorType.PERSONAL) uiState.personalExpense else "1,000원", // 아직 서버 미반영
            style = HuggTypography.p1,
            color = Gs80
        )

        Spacer(modifier = Modifier.size(11.dp))
    }
}

@Composable
fun AccountItemFilter(
    uiState: AccountPageState = AccountPageState(),
    onClickFilterBox: (String) -> Unit = {},
    interactionSource : MutableInteractionSource
) {
    Row(
        modifier = Modifier
            .padding(start = 16.dp)
            .background(Background)
    ) {
        FilterItem(
            text = ACCOUNT_ALL,
            uiState = uiState,
            onClickFilterBox = onClickFilterBox,
            interactionSource = interactionSource
        )

        FilterItem(
            text = ACCOUNT_PERSONAL,
            uiState = uiState,
            onClickFilterBox = onClickFilterBox,
            interactionSource = interactionSource
        )

        if (uiState.tabType == AccountTabType.ALL || uiState.tabType == AccountTabType.MONTH){
            FilterItem(
                text = ACCOUNT_SUBSIDY,
                uiState = uiState,
                onClickFilterBox = onClickFilterBox,
                interactionSource = interactionSource
            )
        }
    }
}

@Composable
fun FilterItem(
    text : String = "",
    uiState: AccountPageState = AccountPageState(),
    onClickFilterBox: (String) -> Unit = {},
    interactionSource : MutableInteractionSource
){
    Box(
        modifier = Modifier
            .padding(end = 4.dp)
            .size(width = 78.dp, height = 28.dp)
            .background(
                color = if (uiState.filterText == text) Gs70 else White,
                shape = RoundedCornerShape(999.dp)
            )
            .clickable(
                onClick = { onClickFilterBox(text) },
                interactionSource = interactionSource,
                indication = null
            ),
        contentAlignment = Alignment.Center
    ) {
        Text(
            textAlign = TextAlign.Center,
            text = text,
            style = if (uiState.filterText == text) HuggTypography.h3 else HuggTypography.p2,
            color = if (uiState.filterText == text) White else Gs60
        )
    }
}

@Preview
@Composable
internal fun PreviewMainContainer() {
    AccountContainer()
}