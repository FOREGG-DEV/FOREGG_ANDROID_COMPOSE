package com.hugg.account

import androidx.compose.foundation.Canvas
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
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollDispatcher
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.hugg.domain.model.enums.AccountTabType
import com.hugg.domain.model.enums.TopBarMiddleType
import com.hugg.domain.model.enums.TopBarRightType
import com.hugg.feature.component.HuggTabBar
import com.hugg.feature.component.TopBar
import com.hugg.feature.theme.ACCOUNT_PERSONAL
import com.hugg.feature.theme.ACCOUNT_SUBSIDY_ALL
import com.hugg.feature.theme.ACCOUNT_UNIT_ALL
import com.hugg.feature.theme.ACCOUNT_UNIT_MONTH
import com.hugg.feature.theme.ACCOUNT_UNIT_ROUND
import com.hugg.feature.theme.Background
import com.hugg.feature.theme.CalendarPill
import com.hugg.feature.theme.Gs20
import com.hugg.feature.theme.Gs70
import com.hugg.feature.theme.Gs80
import com.hugg.feature.theme.HuggTypography
import com.hugg.feature.theme.WORD_ACCOUNT
import com.hugg.feature.theme.WORD_SIGN_UP
import com.hugg.feature.theme.White
import com.hugg.feature.R
import com.hugg.feature.theme.ACCOUNT_ALL_EXPENSE
import com.hugg.feature.theme.ACCOUNT_SUBSIDY
import com.hugg.feature.theme.Gs30
import com.hugg.feature.theme.Gs60
import com.hugg.feature.theme.Gs90

@Composable
fun AccountContainer(
    viewModel: AccountViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    AccountScreen(
        onClickTab = { type -> viewModel.onClickTabType(type) },
        onClickFilterBox = { filter -> viewModel.onClickFilterBox(filter)},
        uiState = uiState
    )
}

@Composable
fun AccountScreen(
    parentScrollState : ScrollState = rememberScrollState(),
    nestedScrollDispatcher : NestedScrollDispatcher = remember { NestedScrollDispatcher() },
    nestedScrollConnection : NestedScrollConnection = remember { object : NestedScrollConnection {} },
    onClickTab : (AccountTabType) -> Unit = {},
    uiState: AccountPageState = AccountPageState(),
    onClickFilterBox : (String) -> Unit = {}
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Background),
    ) {

        TopBar(
            middleItemType = TopBarMiddleType.TEXT,
            middleText = WORD_ACCOUNT,
            rightItemType = TopBarRightType.CREATE
        )
        
        Spacer(modifier = Modifier.size(16.dp))

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .verticalScroll(parentScrollState)
                .nestedScroll(nestedScrollConnection, nestedScrollDispatcher)
        ) {

            HuggTabBar(
                leftText = ACCOUNT_UNIT_ALL,
                middleText = ACCOUNT_UNIT_ROUND,
                rightText = ACCOUNT_UNIT_MONTH,
                onClickLeftTab = { onClickTab(AccountTabType.ALL) },
                onClickMiddleTab = { onClickTab(AccountTabType.ROUND) },
                onClickRightTab = { onClickTab(AccountTabType.MONTH) }
            )

            Spacer(modifier = Modifier.size(12.dp))

            AccountTotalBox()

            Spacer(modifier = Modifier.size(16.dp))

            AccountItemFilter(
                uiState = uiState,
                onClickFilterBox = onClickFilterBox
            )

//            LazyColumn(
//                modifier = Modifier
//                    .height(300.dp)
//                    .background(Color.LightGray)
//                    .nestedScroll(nestedScrollConnection) // 자식도 중첩 스크롤 설정
//            ) {
//                items(20) { index ->
//                    Text("Child LazyColumn Item $index", modifier = Modifier.padding(8.dp))
//                }
//            }
        }
    }
}

@Composable
fun AccountTotalBox(){
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
                text = "2024. 03. 02 - 2024. 08. 25",
                style = HuggTypography.h4,
                color = Gs70
            )

            Spacer(modifier = Modifier.weight(1f))

            Image(
                modifier = Modifier
                    .size(48.dp)
                    .padding(12.dp)
                    .clickable(
                        onClick = {},
                        interactionSource = remember { MutableInteractionSource() },
                        indication = null
                    ),
                imageVector = ImageVector.vectorResource(id = R.drawable.ic_filter),
                contentDescription = null
            )
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Box(
                modifier = Modifier
                    .size(12.dp)
                    .background(color = CalendarPill, shape = RoundedCornerShape(3.dp))
            )

            Spacer(modifier = Modifier.size(4.dp))

            Text(
                text = ACCOUNT_PERSONAL,
                style = HuggTypography.p1,
                color = Gs80
            )

            Spacer(modifier = Modifier.weight(1f))

            Text(
                text = "3,000,500원",
                style = HuggTypography.p1,
                color = Gs80
            )

            Spacer(modifier = Modifier.size(11.dp))
        }

        Spacer(modifier = Modifier.size(18.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Box(
                modifier = Modifier
                    .size(12.dp)
                    .background(color = Gs20, shape = RoundedCornerShape(3.dp))
            )

            Spacer(modifier = Modifier.size(4.dp))

            Text(
                text = ACCOUNT_SUBSIDY_ALL,
                style = HuggTypography.p1,
                color = Gs80
            )

            Spacer(modifier = Modifier.weight(1f))

            Text(
                text = "200,000,000원",
                style = HuggTypography.p1,
                color = Gs80
            )

            Spacer(modifier = Modifier.size(11.dp))
        }

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
                text = "203,000,500원",
                style = HuggTypography.h2,
                color = Gs90
            )

            Spacer(modifier = Modifier.size(11.dp))
        }
    }
}

@Composable
fun AccountItemFilter(
    uiState: AccountPageState = AccountPageState(),
    onClickFilterBox : (String) -> Unit = {}
){
    Row {
        Box(
            modifier = Modifier
                .padding(start = 16.dp, end = 4.dp)
                .size(width = 78.dp, height = 28.dp)
                .background(color = if(uiState.filterText == ACCOUNT_UNIT_ALL) Gs70 else White, shape = RoundedCornerShape(999.dp))
                .clickable(
                    onClick = { onClickFilterBox(ACCOUNT_UNIT_ALL) },
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null
                ),
            contentAlignment = Alignment.Center
        ){
            Text(
                textAlign = TextAlign.Center,
                text = ACCOUNT_UNIT_ALL,
                style = if(uiState.filterText == ACCOUNT_UNIT_ALL) HuggTypography.h3 else HuggTypography.p2,
                color = if(uiState.filterText == ACCOUNT_UNIT_ALL) White else Gs60
            )
        }

        Box(
            modifier = Modifier
                .padding(end = 4.dp)
                .size(width = 78.dp, height = 28.dp)
                .background(color = if(uiState.filterText == ACCOUNT_PERSONAL) Gs70 else White, shape = RoundedCornerShape(999.dp))
                .clickable(
                    onClick = { onClickFilterBox(ACCOUNT_PERSONAL) },
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null
                ),
            contentAlignment = Alignment.Center
        ){
            Text(
                textAlign = TextAlign.Center,
                text = ACCOUNT_PERSONAL,
                style = if(uiState.filterText == ACCOUNT_PERSONAL) HuggTypography.h3 else HuggTypography.p2,
                color = if(uiState.filterText == ACCOUNT_PERSONAL) White else Gs60
            )
        }

        if(uiState.tabType == AccountTabType.ALL || uiState.tabType == AccountTabType.MONTH) {
            Box(
                modifier = Modifier
                    .padding(end = 4.dp)
                    .size(width = 78.dp, height = 28.dp)
                    .background(color = if(uiState.filterText == ACCOUNT_SUBSIDY) Gs70 else White, shape = RoundedCornerShape(999.dp))
                    .clickable(
                        onClick = { onClickFilterBox(ACCOUNT_SUBSIDY) },
                        interactionSource = remember { MutableInteractionSource() },
                        indication = null
                    ),
                contentAlignment = Alignment.Center
            ){
                Text(
                    textAlign = TextAlign.Center,
                    text = ACCOUNT_SUBSIDY,
                    style = if(uiState.filterText == ACCOUNT_SUBSIDY) HuggTypography.h3 else HuggTypography.p2,
                    color = if(uiState.filterText == ACCOUNT_SUBSIDY) White else Gs60
                )
            }
        }
    }
}

@Preview
@Composable
internal fun PreviewMainContainer() {
    AccountContainer()
}