package com.hugg.list.detail

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.hugg.challenge.common.CommonChallengeItem
import com.hugg.challenge.main.ChallengeMainEvent
import com.hugg.domain.model.enums.TopBarLeftType
import com.hugg.domain.model.enums.TopBarMiddleType
import com.hugg.domain.model.enums.TopBarRightType
import com.hugg.domain.model.response.challenge.ChallengeCardVo
import com.hugg.feature.R
import com.hugg.feature.base.Event
import com.hugg.feature.base.EventFlow
import com.hugg.feature.base.MutableEventFlow
import com.hugg.feature.component.BlankBtn
import com.hugg.feature.component.FilledBtn
import com.hugg.feature.component.TopBar
import com.hugg.feature.theme.ALL_CHALLENGE
import com.hugg.feature.theme.Background
import com.hugg.feature.theme.CHALLENGE_MINE
import com.hugg.feature.theme.CHALLENGE_OPEN
import com.hugg.feature.theme.CHALLENGE_PARTICIPATION
import com.hugg.feature.theme.CHALLENGE_POINT
import com.hugg.feature.theme.CREATE_CHALLENGE
import com.hugg.feature.theme.ChallengePoint
import com.hugg.feature.theme.DimBg
import com.hugg.feature.theme.INSUFFICIENT_POINT
import com.hugg.feature.util.HuggToast
import com.hugg.feature.util.UserInfo
import com.hugg.list.ChallengeListViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow

@Composable
fun ChallengeDetailScreen(
    popScreen: () -> Unit,
    challengeId: Long
) {
    val viewModel: ChallengeListViewModel = hiltViewModel()
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val interactionSource = remember { MutableInteractionSource() }
    var showAnimation by remember { mutableStateOf(false) }
    val context = LocalContext.current
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.challenge_open))
    val progress by animateLottieCompositionAsState(
        composition = composition,
        isPlaying = showAnimation,
        iterations = 1,
        restartOnPlay = true
    )

    LaunchedEffect(Unit) {
        viewModel.getChallengeDetail(challengeId)
        viewModel.showUnlockAnimationFlow.collect {
            showAnimation = it
        }
    }

    LaunchedEffect(Unit) {
        viewModel.eventFlow.collect { event ->
            when (event) {
                ChallengeMainEvent.InsufficientPoint -> HuggToast.createToast(context, INSUFFICIENT_POINT).show()
            }
        }
    }

    LaunchedEffect(progress) {
        if (progress == 1f && showAnimation) {
            showAnimation = false
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Background)
            .clickable(
                indication = null,
                interactionSource = interactionSource,
                onClick = {}
            )
    ) {
        TopBar(
            leftItemType = TopBarLeftType.BACK,
            middleItemType = TopBarMiddleType.TEXT,
            rightItemType = TopBarRightType.POINT,
            leftBtnClicked = { popScreen() },
            middleText = ALL_CHALLENGE,
            rightItemContent = String.format(CHALLENGE_POINT, UserInfo.challengePoint)
        )
        Spacer(modifier = Modifier.height(88.dp))
        Box {
            CommonChallengeItem(
                item = uiState.challengeDetailItem,
                modifier = Modifier
                    .padding(horizontal = 34.dp)
            )
            if (showAnimation) {
                LottieAnimation(
                    composition = composition,
                    progress = { progress },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 34.dp)
                        .aspectRatio(307f / 404f)
                        .clip(RoundedCornerShape(12.dp))
                        .background(DimBg)
                )
            }
        }
        Spacer(modifier = Modifier.weight(1f))
        if (uiState.challengeDetailItem.participating) {
            BlankBtn(
                text = CHALLENGE_MINE,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            )
        } else {
            FilledBtn(
                text =
                if (uiState.challengeDetailItem.open)
                    AnnotatedString(CHALLENGE_PARTICIPATION)
                else if (uiState.challengeDetailItem.isCreateChallenge) buildAnnotatedString {
                    append(CREATE_CHALLENGE)
                    addStyle(
                        style = SpanStyle(color = ChallengePoint),
                        start = 0,
                        end = 5
                    )
                }
                else buildAnnotatedString {
                    append(CHALLENGE_OPEN)
                    addStyle(
                        style = SpanStyle(color = ChallengePoint),
                        start = 0,
                        end = 4
                    )
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                onClickBtn = {
                    if (uiState.challengeDetailItem.open) { viewModel.participateChallenge(uiState.challengeDetailItem.id) }
                    else {
                        viewModel.unlockChallenge(uiState.challengeDetailItem.id)
                    }
                }
            )
        }
        Spacer(modifier = Modifier.height(80.dp))
    }
}