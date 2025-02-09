package com.hugg.challenge.common

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.lerp
import coil.compose.rememberAsyncImagePainter
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.hugg.challenge.main.ChallengeMainPageState
import com.hugg.domain.model.response.challenge.ChallengeCardVo
import com.hugg.feature.R
import com.hugg.feature.component.BlankBtn
import com.hugg.feature.component.FilledBtn
import com.hugg.feature.component.HuggText
import com.hugg.feature.theme.CHALLENGE_LIST
import com.hugg.feature.theme.CHALLENGE_MINE
import com.hugg.feature.theme.CHALLENGE_OPEN
import com.hugg.feature.theme.CHALLENGE_PAGER_INDICATOR
import com.hugg.feature.theme.CHALLENGE_PARTICIPANTS
import com.hugg.feature.theme.CHALLENGE_PARTICIPATION
import com.hugg.feature.theme.CREATE_CHALLENGE
import com.hugg.feature.theme.ChallengePoint
import com.hugg.feature.theme.DimBg
import com.hugg.feature.theme.Gs20
import com.hugg.feature.theme.Gs30
import com.hugg.feature.theme.Gs50
import com.hugg.feature.theme.Gs80
import com.hugg.feature.theme.GsBlack
import com.hugg.feature.theme.GsWhite
import com.hugg.feature.theme.HuggTypography
import com.hugg.feature.util.ForeggLog
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import kotlin.math.absoluteValue

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CommonChallenge(
    uiState: ChallengeMainPageState,
    pagerState: PagerState,
    onClickBtnOpen: (Long) -> Unit = {},
    onClickBtnParticipation: (Long) -> Unit = {},
    onClickBtnChallengeList: () -> Unit = {},
    onClickCreateChallenge : () -> Unit = {},
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    showAnimationFlow: SharedFlow<Boolean> = MutableSharedFlow()
) {
    var showAnimation by remember { mutableStateOf(false) }
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.challenge_open))
    val progress by animateLottieCompositionAsState(
        composition = composition,
        isPlaying = showAnimation,
        iterations = 1,
        restartOnPlay = true
    )

    LaunchedEffect(Unit) {
        showAnimationFlow.collect {
            showAnimation = it
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
    ) {
        Box(
            contentAlignment = Alignment.Center
        ) {
            HorizontalPager(
                state = pagerState,
                modifier = Modifier
                    .fillMaxWidth(),
                key = { uiState.commonChallengeList[it].id },
                contentPadding = PaddingValues(horizontal = 32.dp),
                pageSpacing = 8.dp
            ) {
                CommonChallengeItem(
                    modifier = Modifier
                        .graphicsLayer {
                            val pageOffset =
                                ((pagerState.currentPage - it) + pagerState.currentPageOffsetFraction)
                            alpha = lerp(
                                start = 0.7f,
                                stop = 1f,
                                fraction = 1f - pageOffset.absoluteValue.coerceIn(0f, 1f),
                            )
                            scaleY = lerp(
                                start = 0.85f,
                                stop = 1f,
                                fraction = 1f - pageOffset.absoluteValue.coerceIn(0f, 1f)
                            )
                        },
                    item = uiState.commonChallengeList[it]
                )
            }

            if (showAnimation) {
                LottieAnimation(
                    composition = composition,
                    progress = { progress },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 32.dp)
                        .aspectRatio(307f / 404f)
                        .clip(RoundedCornerShape(12.dp))
                        .background(DimBg)
                )
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 34.dp)
        ) {
            PagerIndicator(
                pagerState = pagerState,
                modifier = Modifier.align(Alignment.Center)
            )

            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(4.dp))
                    .border(width = 1.dp, color = Gs20, RoundedCornerShape(4.dp))
                    .background(GsWhite)
                    .align(Alignment.CenterEnd)
                    .clickable(
                        interactionSource = interactionSource,
                        indication = null,
                        onClick = { onClickBtnChallengeList() }
                    )
            ) {
                HuggText(
                    text = CHALLENGE_LIST,
                    style = HuggTypography.p2,
                    color = Gs50,
                    modifier = Modifier
                        .padding(horizontal = 14.dp, vertical = 4.dp)
                )
            }
        }

        Spacer(modifier = Modifier.weight(1f))

        if (uiState.commonChallengeList[pagerState.currentPage].participating) {
            BlankBtn(
                text = CHALLENGE_MINE,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            )
        } else {
            Box(
                modifier = Modifier.height(90.dp)
            ) {
                FilledBtn(
                    text =
                    if (uiState.commonChallengeList[pagerState.currentPage].open && !uiState.commonChallengeList[pagerState.currentPage].isCreateChallenge)
                        AnnotatedString(CHALLENGE_PARTICIPATION)
                    else if (uiState.commonChallengeList[pagerState.currentPage].isCreateChallenge) buildAnnotatedString {
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
                        .padding(horizontal = 16.dp)
                        .align(Alignment.BottomCenter),
                    onClickBtn = {
                        if (uiState.commonChallengeList[pagerState.currentPage].open && !uiState.commonChallengeList[pagerState.currentPage].isCreateChallenge) {
                            onClickBtnParticipation(uiState.commonChallengeList[pagerState.currentPage].id)
                        }
                        else if (uiState.commonChallengeList[pagerState.currentPage].isCreateChallenge) {
                            onClickCreateChallenge()
                        }
                        else {
                            onClickBtnOpen(uiState.commonChallengeList[pagerState.currentPage].id)
                        }
                    }
                )

                if (pagerState.currentPage == 0) {
                    Icon(
                        painter = painterResource(id = R.drawable.msg_bubble_challenge_guide),
                        contentDescription = null,
                        tint = Color.Unspecified,
                        modifier = Modifier
                            .align(Alignment.TopEnd)
                    )
                } else if (uiState.commonChallengeList[pagerState.currentPage].isCreateChallenge) {
                    Icon(
                        painter = painterResource(id = R.drawable.msg_bubble_create_challenge_guide),
                        contentDescription = null,
                        tint = Color.Unspecified,
                        modifier = Modifier
                            .align(Alignment.TopStart)
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(80.dp))
    }
}

@Composable
fun CommonChallengeItem(
    item: ChallengeCardVo,
    modifier: Modifier = Modifier,
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .aspectRatio(307f / 404f)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(307f / 404f)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(GsWhite),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Spacer(modifier = Modifier.height(32.dp))

                if (item.isCreateChallenge) {
                    Box(
                        modifier = Modifier
                            .size(178.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_create_challenge),
                            contentDescription = null,
                            tint = Color.Unspecified,
                            modifier = Modifier.size(130.dp)
                        )
                    }

                } else {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_daily_condition_bad_selected),
                        contentDescription = "",
                        modifier = Modifier.size(178.dp),
                        tint = Color.Unspecified
                    )
                }
//                Image(
//                    painter = rememberAsyncImagePainter(model = item.image),
//                    contentDescription = "",
//                    modifier = Modifier.size(178.dp),
//                    contentScale = ContentScale.Crop
//                )

                Spacer(modifier = Modifier.height(30.dp))

                HuggText(
                    text = item.name,
                    style = HuggTypography.h1,
                    color = GsBlack,
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(18.dp))

                HuggText(
                    modifier = Modifier.padding(horizontal = 12.dp),
                    text = item.description,
                    style = HuggTypography.p3_l,
                    color = Gs80,
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(17.dp))

                if (!item.isCreateChallenge) {
                    ChallengeParticipantsBox(participants = item.participants)
                }

                Spacer(modifier = Modifier.height(19.dp))
            }

            if (!item.open) {
                Box(
                    modifier = Modifier
                        .matchParentSize()
                        .background(DimBg)
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_lock),
                        contentDescription = "",
                        modifier = Modifier
                            .align(Alignment.Center),
                        tint = Color.Unspecified
                    )
                }
            }
        }
    }
}

@Composable
fun ChallengeParticipantsBox(
    participants: Int
) {
    Box(
        modifier = Modifier
            .border(width = 1.dp, color = Gs30, shape = RoundedCornerShape(4.dp)),
        contentAlignment = Alignment.Center
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Spacer(modifier = Modifier.width(9.dp))

            Icon(
                painter = painterResource(id = R.drawable.ic_people),
                contentDescription = ""
            )

            HuggText(
                text = String.format(CHALLENGE_PARTICIPANTS, participants),
                style = HuggTypography.p3_l,
                color = Gs80,
                modifier = Modifier.padding(vertical = 4.dp)
            )

            Spacer(modifier = Modifier.width(9.dp))
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun PagerIndicator(
    pagerState: PagerState,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(999.dp))
            .background(GsWhite)
    ) {
        HuggText(
            text = String.format(CHALLENGE_PAGER_INDICATOR, pagerState.currentPage + 1, pagerState.pageCount),
            style = HuggTypography.p1_l,
            color = Gs50,
            modifier = Modifier
                .padding(vertical = 2.dp)
                .padding(start = 19.dp, end = 18.dp)
        )
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PreviewCommonChallenge() {
    CommonChallenge(
        uiState = ChallengeMainPageState(
            commonChallengeList = listOf(
                ChallengeCardVo(id = 1, name = "시술 직전부터는 금주", description = "시술 직전~도중에는 완전히 피하는 게 좋아요.\n" +
                        "전문가들은 시술 직전~도중엔  소주 50cc, 또는 맥주 200cc\n" +
                        "이하의 알코올 섭취도 피하라고 말합니다.", participants = 19250, participating = false),
                ChallengeCardVo(id = 2, name = "시술 직전부터는 금주", description = "시술 직전~도중에는 완전히 피하는 게 좋아요.\n" +
                        "전문가들은 시술 직전~도중엔  소주 50cc, 또는 맥주 200cc\n" +
                        "이하의 알코올 섭취도 피하라고 말합니다.", participants = 19250, participating = false),
                ChallengeCardVo(id = 3, name = "시술 직전부터는 금주", description = "시술 직전~도중에는 완전히 피하는 게 좋아요.\n" +
                        "전문가들은 시술 직전~도중엔  소주 50cc, 또는 맥주 200cc\n" +
                        "이하의 알코올 섭취도 피하라고 말합니다.", participants = 19250, participating = false),
                ChallengeCardVo(id = 4, name = "시술 직전부터는 금주", description = "시술 직전~도중에는 완전히 피하는 게 좋아요.\n" +
                        "전문가들은 시술 직전~도중엔  소주 50cc, 또는 맥주 200cc\n" +
                        "이하의 알코올 섭취도 피하라고 말합니다.", participants = 19250, participating = false),
                ChallengeCardVo(id = 5, name = "시술 직전부터는 금주", description = "시술 직전~도중에는 완전히 피하는 게 좋아요.\n" +
                        "전문가들은 시술 직전~도중엔  소주 50cc, 또는 맥주 200cc\n" +
                        "이하의 알코올 섭취도 피하라고 말합니다.", participants = 19250, participating = false),
                ChallengeCardVo(id = 6, name = "시술 직전부터는 금주", description = "시술 직전~도중에는 완전히 피하는 게 좋아요.\n" +
                        "전문가들은 시술 직전~도중엔  소주 50cc, 또는 맥주 200cc\n" +
                        "이하의 알코올 섭취도 피하라고 말합니다.", participants = 19250, participating = false)
            )
        ),
        pagerState = rememberPagerState(pageCount = { 6 })
    )
}