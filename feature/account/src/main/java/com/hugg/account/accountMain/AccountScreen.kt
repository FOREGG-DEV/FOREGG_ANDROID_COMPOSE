package com.hugg.account.accountMain

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
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
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.hugg.account.accountMain.bottomSheet.DatePickBottomSheet
import com.hugg.domain.model.enums.AccountColorType
import com.hugg.domain.model.enums.AccountTabType
import com.hugg.domain.model.enums.CreateOrEditType
import com.hugg.domain.model.enums.HuggTabClickedType
import com.hugg.domain.model.enums.TopBarMiddleType
import com.hugg.domain.model.enums.TopBarRightType
import com.hugg.feature.R
import com.hugg.feature.component.HuggDialog
import com.hugg.feature.component.HuggTabBar
import com.hugg.feature.component.PlusBtn
import com.hugg.feature.component.TopBar
import com.hugg.feature.theme.ACCOUNT_ALL
import com.hugg.feature.theme.ACCOUNT_ALL_EXPENSE
import com.hugg.feature.theme.ACCOUNT_DIALOG_CREATE_ROUND
import com.hugg.feature.theme.ACCOUNT_DIALOG_WARNING_CREATE_ROUND
import com.hugg.feature.theme.ACCOUNT_LIST_DIALOG_DELETE
import com.hugg.feature.theme.ACCOUNT_MONTH
import com.hugg.feature.theme.ACCOUNT_PERSONAL
import com.hugg.feature.theme.ACCOUNT_ROUND
import com.hugg.feature.theme.ACCOUNT_ROUND_MEMO
import com.hugg.feature.theme.ACCOUNT_SUBSIDY_ALL
import com.hugg.feature.theme.ACCOUNT_SUGGEST_ADD_SUBSIDY
import com.hugg.feature.theme.ACCOUNT_TOAST_SUCCESS_DELETE
import com.hugg.feature.theme.Background
import com.hugg.feature.theme.CalendarEtc
import com.hugg.feature.theme.CalendarHospital
import com.hugg.feature.theme.CalendarInjection
import com.hugg.feature.theme.CalendarPill
import com.hugg.feature.theme.EmptySubsidyBoxColor
import com.hugg.feature.theme.Gs20
import com.hugg.feature.theme.Gs30
import com.hugg.feature.theme.Gs60
import com.hugg.feature.theme.Gs70
import com.hugg.feature.theme.Gs80
import com.hugg.feature.theme.Gs90
import com.hugg.feature.theme.MainNormal
import com.hugg.feature.theme.Sunday
import com.hugg.feature.theme.WORD_ACCOUNT
import com.hugg.feature.theme.WORD_ADD
import com.hugg.feature.theme.WORD_DELETE
import com.hugg.feature.theme.White
import com.hugg.feature.theme.h2
import com.hugg.feature.theme.h3
import com.hugg.feature.theme.h4
import com.hugg.feature.theme.p1
import com.hugg.feature.theme.p2
import com.hugg.feature.uiItem.AccountCardItem
import com.hugg.feature.uiItem.RemoteRound
import com.hugg.feature.uiItem.SubsidyTotalBoxItem
import com.hugg.feature.util.ForeggLog
import com.hugg.feature.util.HuggToast
import com.hugg.feature.util.TimeFormatter
import com.hugg.feature.util.UnitFormatter
import kotlinx.coroutines.flow.distinctUntilChanged

@OptIn(ExperimentalComposeUiApi::class, ExperimentalMaterial3Api::class)
@Composable
fun AccountContainer(
    navigateToSubsidyList : (Int) -> Unit = {},
    navigateToCreateOrEditAccount : (Long, CreateOrEditType) -> Unit = {_, _ -> },
    viewModel: AccountViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val scrollState = rememberLazyListState()
    val context = LocalContext.current
    var isFilterAtTop by remember { mutableStateOf(false) }
    val interactionSource = remember { MutableInteractionSource() }
    val keyboardController = LocalSoftwareKeyboardController.current

    LaunchedEffect(Unit){
        viewModel.setView()
    }

    AccountScreen(
        onClickTab = { type -> viewModel.onClickTabType(type) },
        onClickFilterBox = { filter -> viewModel.onClickFilterBox(filter) },
        uiState = uiState,
        scrollState = scrollState,
        isFilterAtTop = isFilterAtTop,
        onClickCreateRoundBtn = { viewModel.showCreateRoundDialog(true) },
        onClickPrevMonthBtn = { viewModel.onClickPrevMonth() },
        onClickNextMonthBtn = { viewModel.onClickNextMonth() },
        onClickPrevRoundBtn = { viewModel.onClickPrevRound() },
        onClickNextRoundBtn = { viewModel.onClickNextRound() },
        onClickDateFilter = { viewModel.onClickBottomSheetOnOff() },
        onClickGoToSubsidyList = { navigateToSubsidyList(uiState.nowRound) },
        onClickCreateAccountBtn = { navigateToCreateOrEditAccount(-1, CreateOrEditType.CREATE) },
        onClickAccountCard = { ledgerId, expenditureId -> if(uiState.isDeleteMode) viewModel.onClickCard(expenditureId) else navigateToCreateOrEditAccount(ledgerId, CreateOrEditType.EDIT) },
        onLongClickAccountCard = { id -> viewModel.onLongClickItem(id) },
        onDeleteAccountList = { viewModel.showDeleteDialog(true) },
        onChangedMemo = { memo -> viewModel.onChangedMemo(memo)},
        onKeyboardDone = {
            viewModel.inputMemoDone()
            keyboardController?.hide()
        },
        interactionSource = interactionSource
    )

    LaunchedEffect(scrollState) {
        snapshotFlow { scrollState.firstVisibleItemIndex }
            .distinctUntilChanged()
            .collect { index ->
                isFilterAtTop = index >= 2
            }
    }

    LaunchedEffect(Unit) {
        viewModel.eventFlow.collect { event ->
            when(event) {
                AccountEvent.SuccessDeleteAccountEvent -> {
                    HuggToast.createToast(context, ACCOUNT_TOAST_SUCCESS_DELETE).show()
                }
            }
        }
    }

    if (uiState.isShowBottomSheet) {
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

    if(uiState.isShowDeleteDialog){
        HuggDialog(
            title = ACCOUNT_LIST_DIALOG_DELETE,
            positiveColor = Sunday,
            positiveText = WORD_DELETE,
            onClickCancel = { viewModel.showDeleteDialog(false) },
            onClickNegative = { viewModel.showDeleteDialog(false) },
            onClickPositive = { viewModel.deleteExpenditure() },
        )
    }

    if(uiState.isShowCreateRoundDialog){
        HuggDialog(
            title = ACCOUNT_DIALOG_CREATE_ROUND,
            warningMessage = ACCOUNT_DIALOG_WARNING_CREATE_ROUND,
            positiveText = WORD_ADD,
            hasWarningText = true,
            onClickCancel = { viewModel.showCreateRoundDialog(false) },
            onClickNegative = { viewModel.showCreateRoundDialog(false) },
            onClickPositive = { viewModel.onClickCreateRoundBtn() },
        )
    }
}

@Composable
fun AccountScreen(
    onClickPrevMonthBtn: () -> Unit = {},
    onClickNextMonthBtn: () -> Unit = {},
    onClickPrevRoundBtn: () -> Unit = {},
    onClickNextRoundBtn: () -> Unit = {},
    onClickCreateRoundBtn: () -> Unit = {},
    onClickGoToSubsidyList : () -> Unit = {},
    onClickTab: (AccountTabType) -> Unit = {},
    onClickCreateAccountBtn : () -> Unit = {},
    uiState: AccountPageState = AccountPageState(),
    onClickFilterBox: (String) -> Unit = {},
    scrollState: LazyListState = rememberLazyListState(),
    isFilterAtTop: Boolean = false,
    onClickDateFilter: () -> Unit = {},
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    onClickAccountCard : (Long, Long) -> Unit = {_, _ -> },
    onLongClickAccountCard : (Long) -> Unit = {},
    onChangedMemo : (String) -> Unit = {},
    onKeyboardDone : () -> Unit = {},
    onDeleteAccountList : () -> Unit = {}
) {

    val initialTabType = when(uiState.tabType){
        AccountTabType.ALL -> HuggTabClickedType.LEFT
        AccountTabType.ROUND -> HuggTabClickedType.MIDDLE
        AccountTabType.MONTH -> HuggTabClickedType.RIGHT
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Background)
            .pointerInput(Unit) {
                onKeyboardDone()
            },
    ) {

        TopBar(
            middleItemType = TopBarMiddleType.TEXT,
            middleText = WORD_ACCOUNT,
            rightItemType = if(uiState.isDeleteMode) TopBarRightType.DELETE else TopBarRightType.NONE,
            rightBtnClicked = onDeleteAccountList,
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
                    initialTabType = initialTabType,
                    leftText = ACCOUNT_ALL,
                    middleText = ACCOUNT_ROUND,
                    rightText = ACCOUNT_MONTH,
                    onClickLeftTab = { onClickTab(AccountTabType.ALL) },
                    onClickMiddleTab = { onClickTab(AccountTabType.ROUND) },
                    onClickRightTab = { onClickTab(AccountTabType.MONTH) },
                    interactionSource = interactionSource
                )

                Spacer(modifier = Modifier.size(6.dp))

                if (uiState.tabType == AccountTabType.MONTH) {
                    RemoteMonth(
                        onClickPrevMonthBtn = onClickPrevMonthBtn,
                        onClickNextMonthBtn = onClickNextMonthBtn,
                        date = uiState.selectedYearMonth,
                        isCurrent = uiState.isCurrentMonth,
                        interactionSource = interactionSource
                    )
                }
                if (uiState.tabType == AccountTabType.ROUND) {
                    RemoteRound(
                        onClickPrevRoundBtn = onClickPrevRoundBtn,
                        onClickNextRoundBtn = onClickNextRoundBtn,
                        onClickCreateRoundBtn = onClickCreateRoundBtn,
                        interactionSource = interactionSource,
                        nowRound = uiState.nowRound
                    )
                }
                Spacer(modifier = Modifier.size(6.dp))
            }

            item {
                AccountTotalBox(
                    uiState = uiState,
                    onClickDateFilter = onClickDateFilter,
                    onClickGoToSubsidyList = onClickGoToSubsidyList,
                    onChangedMemo = onChangedMemo,
                    onKeyboardDone = onKeyboardDone,
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
                    accountVo.expenditureId
                }
            ) { _, accountVo ->
                AccountCardItem(
                    item = accountVo,
                    interactionSource = interactionSource,
                    onClickCard = onClickAccountCard,
                    onLongClickCard = onLongClickAccountCard
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

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.End
    ) {
        Spacer(modifier = Modifier.weight(1f))
        PlusBtn(
            interactionSource = interactionSource,
            modifier = Modifier
                .padding(end = 16.dp)
                .shadow(
                    elevation = 6.dp,
                    shape = RoundedCornerShape(8.dp)
                )
                .size(56.dp)
                .background(color = MainNormal)
                .clickable(
                    onClick = onClickCreateAccountBtn,
                    interactionSource = interactionSource,
                    indication = null
                )
        )
        Spacer(modifier = Modifier.size(24.dp))
    }
}

@Composable
fun RemoteMonth(
    onClickPrevMonthBtn : () -> Unit = {},
    onClickNextMonthBtn : () -> Unit = {},
    date : String = "",
    isCurrent : Boolean = false,
    interactionSource : MutableInteractionSource
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
                    interactionSource = interactionSource,
                    indication = null
                ),
            imageVector = ImageVector.vectorResource(R.drawable.ic_back_arrow_gs_60),
            contentDescription = null
        )

        Spacer(modifier = Modifier.size(9.dp))

        Text(
            modifier = Modifier.align(Alignment.CenterVertically),
            text = date,
            color = Gs90,
            style = h2()
        )

        Spacer(modifier = Modifier.size(9.dp))

        Box(modifier = Modifier.size(48.dp)) {
            if (!isCurrent) Image(
                modifier = Modifier
                    .width(48.dp)
                    .height(48.dp)
                    .padding(12.dp)
                    .graphicsLayer(scaleX = -1f)
                    .clickable(
                        onClick = onClickNextMonthBtn,
                        interactionSource = interactionSource,
                        indication = null
                    ),
                imageVector = ImageVector.vectorResource(R.drawable.ic_back_arrow_gs_60),
                contentDescription = null
            )
        }

        Spacer(modifier = Modifier.size(9.dp))

    }
}

@Composable
fun AccountTotalBox(
    uiState: AccountPageState = AccountPageState(),
    onClickDateFilter: () -> Unit = {},
    onClickGoToSubsidyList: () -> Unit = {},
    onChangedMemo : (String) -> Unit = {},
    onKeyboardDone : () -> Unit = {},
    interactionSource: MutableInteractionSource
) {
    Column(
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .fillMaxWidth()
            .background(color = White, shape = RoundedCornerShape(8.dp))
            .padding(bottom = 16.dp)
    ) {
        if (uiState.tabType == AccountTabType.ALL) {
            Row(
                modifier = Modifier
                    .padding(start = 12.dp)
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {

                Text(
                    text = "${TimeFormatter.getDotsDate(uiState.startDay)} - ${TimeFormatter.getDotsDate(uiState.endDay)}",
                    style = h4(),
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
        }

        if (uiState.tabType == AccountTabType.ROUND) {
            Spacer(modifier = Modifier.size(16.dp))

            Box(
                modifier = Modifier
                    .padding(horizontal = 12.dp)
                    .fillMaxWidth(),
            ) {
                if(uiState.memo.isEmpty()){
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.ic_write_gs_70),
                            contentDescription = null
                        )

                        Spacer(modifier = Modifier.size(4.dp))

                        Text(
                            text = ACCOUNT_ROUND_MEMO,
                            style = h4(),
                            color = Gs70
                        )
                    }
                }

                BasicTextField(
                    value = uiState.memo,
                    onValueChange = { value ->
                        onChangedMemo(value)
                    },
                    textStyle = h4().copy(
                        color = Gs70,
                    ),
                    keyboardActions = KeyboardActions(onDone = {onKeyboardDone()}),
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }

        if (uiState.tabType == AccountTabType.MONTH) Spacer(modifier = Modifier.size(22.dp))
        if (uiState.tabType == AccountTabType.ROUND) Spacer(modifier = Modifier.size(12.dp))

        TotalBoxItem(AccountColorType.RED, uiState)

        Spacer(modifier = Modifier.size(
            if (uiState.tabType == AccountTabType.ROUND && uiState.subsidyList.isEmpty()) 12.dp else 18.dp
        ))

        if (uiState.tabType != AccountTabType.ROUND) {
            TotalBoxItem(AccountColorType.ALL, uiState)
        }
        else{
            if(uiState.subsidyList.isEmpty()) EmptySubsidyBox(
                onClickGoToSubsidyList = onClickGoToSubsidyList,
                interactionSource = interactionSource
            )
            else{
                uiState.subsidyList.forEachIndexed { index, subsidyListResponseVo ->
                    SubsidyTotalBoxItem(
                        item = subsidyListResponseVo,
                        interactionSource = interactionSource,
                        onClickGoToSubsidyList = onClickGoToSubsidyList
                    )

                    if(index != uiState.subsidyList.size -1) Spacer(modifier = Modifier.size(16.dp))
                }
            }
        }

        Spacer(modifier = Modifier.size(9.dp))

        Canvas(
            modifier = Modifier
                .padding(start = 12.dp, end = 11.dp)
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
            modifier = Modifier
                .padding(start = 12.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Text(
                text = ACCOUNT_ALL_EXPENSE,
                style = h2(),
                color = Gs90
            )

            Spacer(modifier = Modifier.weight(1f))

            Text(
                text = uiState.totalExpense,
                style = h2(),
                color = Gs90
            )

            Spacer(modifier = Modifier.size(11.dp))
        }
    }
}

@Composable
fun TotalBoxItem(
    colorType: AccountColorType = AccountColorType.RED,
    uiState: AccountPageState = AccountPageState()
) {
    val color = when (colorType) {
        AccountColorType.RED -> CalendarPill
        AccountColorType.ALL -> Gs20
        AccountColorType.BLUE -> CalendarInjection
        AccountColorType.GREEN -> CalendarHospital
        AccountColorType.YELLOW -> CalendarEtc
    }

    val text = when (colorType) {
        AccountColorType.RED -> ACCOUNT_PERSONAL
        AccountColorType.ALL -> ACCOUNT_SUBSIDY_ALL
        else -> ""
    }

    Row(
        modifier = Modifier
            .padding(start = 12.dp)
            .fillMaxWidth(),
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
            style = p1(),
            color = Gs80
        )

        Spacer(modifier = Modifier.weight(1f))

        Text(
            text = if (colorType == AccountColorType.RED) uiState.personalExpense else uiState.subsidyExpense, // 아직 서버 미반영
            style = p1(),
            color = Gs80
        )

        Spacer(modifier = Modifier.size(11.dp))
    }
}

@Composable
fun EmptySubsidyBox(
    onClickGoToSubsidyList : () -> Unit = {},
    interactionSource: MutableInteractionSource,
){
    Row(
        modifier = Modifier
            .padding(start = 12.dp, end = 6.dp)
            .fillMaxWidth()
            .background(color = EmptySubsidyBoxColor, shape = RoundedCornerShape(4.dp))
            .padding(vertical = 6.dp)
            .clickable(
                onClick = onClickGoToSubsidyList,
                interactionSource = interactionSource,
                indication = null
            ),
        verticalAlignment = Alignment.CenterVertically
    ) {

        Spacer(modifier = Modifier.size(7.dp))

        Text(
            text = ACCOUNT_SUGGEST_ADD_SUBSIDY,
            style = h3(),
            color = Gs80
        )

        Spacer(modifier = Modifier.weight(1f))

        Image(
            imageVector = ImageVector.vectorResource(R.drawable.ic_right_arrow_gs_80),
            contentDescription = null
        )

        Spacer(modifier = Modifier.size(4.dp))

    }
}

@Composable
fun AccountItemFilter(
    uiState: AccountPageState = AccountPageState(),
    onClickFilterBox: (String) -> Unit = {},
    interactionSource: MutableInteractionSource
) {
    LazyRow(
        modifier = Modifier
            .padding(start = 16.dp, end = 16.dp)
            .background(Background)
    ) {
        items(uiState.filterList){
            FilterItem(
                text = it,
                uiState = uiState,
                onClickFilterBox = onClickFilterBox,
                interactionSource = interactionSource
            )
        }
    }
}

@Composable
fun FilterItem(
    text: String = "",
    uiState: AccountPageState = AccountPageState(),
    onClickFilterBox: (String) -> Unit = {},
    interactionSource: MutableInteractionSource
) {
    Box(
        modifier = Modifier
            .padding(end = 4.dp)
            .size(width = 78.dp, height = 28.dp)
            .border(
                width = 1.dp,
                color = if (uiState.selectedFilterList.contains(text)) Gs70 else Gs20,
                shape = RoundedCornerShape(999.dp)
            )
            .background(
                color = if (uiState.selectedFilterList.contains(text)) Gs70 else White,
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
            text = if(uiState.tabType == AccountTabType.ROUND && text != ACCOUNT_ALL && text != ACCOUNT_PERSONAL) UnitFormatter.getSubsidyTitleWithoutMoneyFormat(text) else text,
            style = if(uiState.selectedFilterList.contains(text)) h4() else p2() ,
            color = if(uiState.selectedFilterList.contains(text)) White else Gs60
        )
    }
}