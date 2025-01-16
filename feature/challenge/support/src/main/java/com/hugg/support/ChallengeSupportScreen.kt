package com.hugg.support

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.hugg.domain.model.enums.CheerType
import com.hugg.domain.model.enums.TopBarLeftType
import com.hugg.domain.model.enums.TopBarMiddleType
import com.hugg.domain.model.enums.TopBarRightType
import com.hugg.domain.model.response.challenge.ChallengeSupportItemVo
import com.hugg.feature.R
import com.hugg.feature.component.HuggText
import com.hugg.feature.component.TopBar
import com.hugg.feature.theme.Background
import com.hugg.feature.theme.CHALLENGE_CLAP_SUCCESS
import com.hugg.feature.theme.CHALLENGE_POINT
import com.hugg.feature.theme.CHALLENGE_SUPPORT_SUCCESS
import com.hugg.feature.theme.CHALLENGE_SUPPORT_TOP_BAR
import com.hugg.feature.theme.EXCEED_PAGE_LIMIT
import com.hugg.feature.theme.Gs80
import com.hugg.feature.theme.Gs90
import com.hugg.feature.theme.GsWhite
import com.hugg.feature.theme.HuggTypography
import com.hugg.feature.theme.SUPPORT_LIMIT_MESSAGE
import com.hugg.feature.theme.WORD_SHOW_MORE
import com.hugg.feature.util.HuggToast
import com.hugg.feature.util.UserInfo
import com.hugg.feature.util.onThrottleClick

@Composable
fun ChallengeSupportScreen(
    popScreen: () -> Unit,
    challengeId: Long?
) {
    val viewModel: ChallengeSupportViewModel = hiltViewModel()
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val context = LocalContext.current
    val interactionSource = remember { MutableInteractionSource() }

    LaunchedEffect(Unit) {
        challengeId?.let { viewModel.setChallengeId(it) }
    }

    LaunchedEffect(Unit) {
        challengeId?.let {
            viewModel.getChallengeSupportList(challengeId, isSuccess = true, page = 0, size = 5)
            viewModel.getChallengeSupportList(challengeId, isSuccess = false, page = 0, size = 5)
        }
    }

    LaunchedEffect(Unit) {
        viewModel.eventFlow.collect { event ->
            when(event) {
                is ChallengeSupportEvent.ClapSuccess -> {
                    HuggToast.createToast(context, CHALLENGE_CLAP_SUCCESS).show()
                }
                is ChallengeSupportEvent.SupportSuccess -> {
                    HuggToast.createToast(context, CHALLENGE_SUPPORT_SUCCESS).show()
                }
                is ChallengeSupportEvent.ExceedSupportLimit -> {
                    HuggToast.createToast(context, SUPPORT_LIMIT_MESSAGE).show()
                }
                is ChallengeSupportEvent.ExceedPageLimit -> {
                    HuggToast.createToast(context, EXCEED_PAGE_LIMIT).show()
                }
            }
        }
    }

    ChallengeSupportContent(
        uiState = uiState,
        popScreen = popScreen,
        onLoadMoreCompleted = { viewModel.getChallengeSupportList(uiState.challengeId, isSuccess = true, page = uiState.completedCurPage + 1, size = 5) },
        onLoadMoreIncomplete = { viewModel.getChallengeSupportList(uiState.challengeId, isSuccess = false, page = uiState.incompleteCurPage + 1, size = 5) },
        supportChallenge = { userId: Long, cheerType: CheerType ->
            challengeId?.let {
                viewModel.supportChallenge(challengeId = challengeId, userId = userId, cheerType = cheerType)
            }
        },
        interactionSource = interactionSource
    )
}

@Composable
fun ChallengeSupportContent(
    uiState: ChallengeSupportPageState = ChallengeSupportPageState(),
    popScreen: () -> Unit = {},
    onLoadMoreCompleted: () -> Unit = {},
    onLoadMoreIncomplete: () -> Unit = {},
    supportChallenge: (Long, CheerType) -> Unit = {_,_ ->},
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() }
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Background)
    ) {
        TopBar(
            leftItemType = TopBarLeftType.BACK,
            leftBtnClicked = { popScreen() },
            middleItemType = TopBarMiddleType.TEXT,
            middleText = CHALLENGE_SUPPORT_TOP_BAR,
            rightItemType = TopBarRightType.POINT,
            rightItemContent = String.format(CHALLENGE_POINT, UserInfo.challengePoint)
        )
        
        Spacer(modifier = Modifier.height(24.dp))

        LazyColumn(
            modifier = Modifier
                .fillMaxSize(),
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            if (uiState.completedList.isNotEmpty()) {
                items(uiState.completedList) { item ->
                    ChallengeItem(item = item, cheerType = CheerType.CLAP, supportChallenge = supportChallenge, interactionSource)
                }
                item {
                    Spacer(modifier = Modifier.height(8.dp))
                    LoadMoreButton(onClick = onLoadMoreCompleted)
                }
            }

            item {
                Spacer(modifier = Modifier.height(8 .dp))
            }

            if (uiState.incompleteList.isNotEmpty()) {
                items(uiState.incompleteList) { item ->
                    ChallengeItem(item = item, cheerType = CheerType.SUPPORT, supportChallenge = supportChallenge, interactionSource)
                }
                item {
                    Spacer(modifier = Modifier.height(8.dp))
                    LoadMoreButton(onClick = onLoadMoreIncomplete)
                }
            }
        }
    }
}

@Composable
fun LoadMoreButton(
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .onThrottleClick(onClick = onClick),
        verticalAlignment = Alignment.CenterVertically
    ) {
        HuggText(
            text = WORD_SHOW_MORE,
            style = HuggTypography.p2,
            color = Gs80
        )
        Spacer(modifier = Modifier.width(4.dp))
        Icon(
            painter = painterResource(id = R.drawable.ic_bottom_arrow_gs_80),
            contentDescription = null,
            tint = Color.Unspecified,
            modifier = Modifier.size(14.dp)
        )
    }
}

@Composable
fun ChallengeItem(
    item: ChallengeSupportItemVo,
    cheerType: CheerType,
    supportChallenge: (Long, CheerType) -> Unit,
    interactionSource: MutableInteractionSource
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(6.dp))
            .background(GsWhite),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Spacer(modifier = Modifier.width(12.dp))
        Image(
            painter = painterResource(id = if(cheerType == CheerType.CLAP) R.drawable.ic_check_circle_main_normal else R.drawable.ic_check_circle_gs_30),
            contentDescription = null,
            modifier = Modifier.size(32.dp)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Column(
            modifier = Modifier
                .padding(vertical = 12.dp),
            horizontalAlignment = Alignment.Start
        ) {
            HuggText(
                text = item.nickname,
                style = HuggTypography.h3,
                color = Gs90
            )
            Spacer(modifier = Modifier.height(4.dp))
            HuggText(
                text = item.thoughts,
                style = HuggTypography.p4,
                color = Gs80
            )
        }
        Spacer(modifier = Modifier.weight(1f))
        if (cheerType == CheerType.CLAP) {
            Icon(
                painter = painterResource(id = if (!item.supported) R.drawable.ic_clap_enable else R.drawable.ic_clap_disable),
                contentDescription = null,
                tint = Color.Unspecified,
                modifier = Modifier
                    .size(36.dp)
                    .clickable(
                        interactionSource = interactionSource,
                        indication = null,
                        onClick = { if (!item.supported) supportChallenge(item.userId, cheerType) }
                    )
            )
        } else if (cheerType == CheerType.SUPPORT) {
            Icon(
                painter = painterResource(id = if (!item.supported) R.drawable.ic_support_enable else R.drawable.ic_support_disable),
                contentDescription = null,
                tint = Color.Unspecified,
                modifier = Modifier
                    .size(36.dp)
                    .clickable(
                        interactionSource = interactionSource,
                        indication = null,
                        onClick = { if (!item.supported) supportChallenge(item.userId, cheerType) }
                    )
            )
        }
        Spacer(modifier = Modifier.width(16.dp))
    }
}