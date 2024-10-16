package com.hugg.challenge.common

import androidx.compose.foundation.ExperimentalFoundationApi
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
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.lerp
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
import com.hugg.feature.theme.ChallengePoint
import com.hugg.feature.theme.DimBg
import com.hugg.feature.theme.Gs20
import com.hugg.feature.theme.Gs30
import com.hugg.feature.theme.Gs50
import com.hugg.feature.theme.Gs80
import com.hugg.feature.theme.GsBlack
import com.hugg.feature.theme.GsWhite
import com.hugg.feature.theme.HuggTypography
import kotlin.math.absoluteValue

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CommonChallenge(
    uiState: ChallengeMainPageState,
    pagerState: PagerState,
    onClickBtnParticipation: () -> Unit = {},
    onClickBtnChallengeList: () -> Unit = {},
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() }
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
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

        if (uiState.commonChallengeList[pagerState.currentPage].ifMine) {
            BlankBtn(
                text = CHALLENGE_MINE,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            )
        } else {
            FilledBtn(
                text =
                if (uiState.commonChallengeList[pagerState.currentPage].isOpen)
                    AnnotatedString(CHALLENGE_PARTICIPATION)
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
                onClickBtn = { onClickBtnParticipation() }
            )
        }

        Spacer(modifier = Modifier.height(80.dp))
    }
}

@Composable
fun CommonChallengeItem(
    item: ChallengeCardVo,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
    ) {
        Box(
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(GsWhite),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Spacer(modifier = Modifier.height(32.dp))

                Icon(
                    painter = painterResource(id = R.drawable.ic_daily_condition_bad_selected),
                    contentDescription = "",
                    modifier = Modifier.size(178.dp),
                    tint = Color.Unspecified
                )

                Spacer(modifier = Modifier.height(30.dp))

                HuggText(
                    text = item.name,
                    style = HuggTypography.h1,
                    color = GsBlack,
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(18.dp))

                HuggText(
                    text = item.description,
                    style = HuggTypography.p3_l,
                    color = Gs80,
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(17.dp))

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
                            text = String.format(CHALLENGE_PARTICIPANTS, item.participants),
                            style = HuggTypography.p3_l,
                            color = Gs80,
                            modifier = Modifier.padding(vertical = 4.dp)
                        )

                        Spacer(modifier = Modifier.width(9.dp))
                    }
                }

                Spacer(modifier = Modifier.height(19.dp))
            }

            if (!item.isOpen) {
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

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun PagerIndicator(
    pagerState: PagerState,
    modifier: Modifier
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
                        "이하의 알코올 섭취도 피하라고 말합니다.", participants = 19250, ifMine = false),
                ChallengeCardVo(id = 2, name = "시술 직전부터는 금주", description = "시술 직전~도중에는 완전히 피하는 게 좋아요.\n" +
                        "전문가들은 시술 직전~도중엔  소주 50cc, 또는 맥주 200cc\n" +
                        "이하의 알코올 섭취도 피하라고 말합니다.", participants = 19250, ifMine = false),
                ChallengeCardVo(id = 3, name = "시술 직전부터는 금주", description = "시술 직전~도중에는 완전히 피하는 게 좋아요.\n" +
                        "전문가들은 시술 직전~도중엔  소주 50cc, 또는 맥주 200cc\n" +
                        "이하의 알코올 섭취도 피하라고 말합니다.", participants = 19250, ifMine = false),
                ChallengeCardVo(id = 4, name = "시술 직전부터는 금주", description = "시술 직전~도중에는 완전히 피하는 게 좋아요.\n" +
                        "전문가들은 시술 직전~도중엔  소주 50cc, 또는 맥주 200cc\n" +
                        "이하의 알코올 섭취도 피하라고 말합니다.", participants = 19250, ifMine = false),
                ChallengeCardVo(id = 5, name = "시술 직전부터는 금주", description = "시술 직전~도중에는 완전히 피하는 게 좋아요.\n" +
                        "전문가들은 시술 직전~도중엔  소주 50cc, 또는 맥주 200cc\n" +
                        "이하의 알코올 섭취도 피하라고 말합니다.", participants = 19250, ifMine = false),
                ChallengeCardVo(id = 6, name = "시술 직전부터는 금주", description = "시술 직전~도중에는 완전히 피하는 게 좋아요.\n" +
                        "전문가들은 시술 직전~도중엔  소주 50cc, 또는 맥주 200cc\n" +
                        "이하의 알코올 섭취도 피하라고 말합니다.", participants = 19250, ifMine = false)
            )
        ),
        pagerState = rememberPagerState(pageCount = { 6 })
    )
}