package com.hugg.challenge.my

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
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.lerp
import com.hugg.challenge.common.ChallengeParticipantsBox
import com.hugg.challenge.common.PagerIndicator
import com.hugg.challenge.main.ChallengeMainPageState
import com.hugg.domain.model.enums.MyChallengeState
import com.hugg.domain.model.response.challenge.MyChallengeListItemVo
import com.hugg.feature.R
import com.hugg.feature.component.HuggDialog
import com.hugg.feature.component.HuggInputDialog
import com.hugg.feature.component.HuggText
import com.hugg.feature.theme.CHALLENGE_DELETE
import com.hugg.feature.theme.CHALLENGE_POINT
import com.hugg.feature.theme.CHALLENGE_SUPPORT_GUIDE
import com.hugg.feature.theme.COMMENT_DIALOG_TITLE
import com.hugg.feature.theme.Disabled
import com.hugg.feature.theme.Gs20
import com.hugg.feature.theme.Gs30
import com.hugg.feature.theme.Gs50
import com.hugg.feature.theme.Gs70
import com.hugg.feature.theme.Gs80
import com.hugg.feature.theme.GsBlack
import com.hugg.feature.theme.GsWhite
import com.hugg.feature.theme.HuggTypography
import com.hugg.feature.theme.MY_CHALLENGE_EMPTY
import com.hugg.feature.theme.MainNormal
import com.hugg.feature.theme.StatusDestructive
import com.hugg.feature.theme.Sub
import com.hugg.feature.theme.WORD_CONFIRM
import com.hugg.feature.theme.WORD_DELETE
import com.hugg.feature.theme.WORD_NO
import com.hugg.feature.theme.WORD_SUCCESS
import kotlin.math.absoluteValue

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MyChallenge(
    uiState: ChallengeMainPageState,
    pagerState: PagerState,
    goToChallengeSupport: (Long) -> Unit,
    onPageChange: (Int) -> Unit,
    updateCommentDialogVisibility: (Boolean) -> Unit,
    completeChallenge: (String, MyChallengeState) -> Unit,
    updateDeleteDialogVisibility: (Boolean) -> Unit,
    deleteChallenge: (Long) -> Unit
) {
    val interactionSource: MutableInteractionSource = remember { MutableInteractionSource() }

    if (uiState.showCommentDialog) {
        HuggInputDialog(
            title = COMMENT_DIALOG_TITLE,
            positiveText = WORD_CONFIRM,
            maxLength = 15,
            onClickCancel = { updateCommentDialogVisibility(false) },
            onClickPositive = {
                if (it.isNotEmpty()) {
                    completeChallenge(it, MyChallengeState.TODAY)
                    updateCommentDialogVisibility(false)
                }
            }
        )
    }

    if (uiState.showDeleteDialog) {
        HuggDialog(
            title = CHALLENGE_DELETE,
            positiveColor = StatusDestructive,
            negativeText = WORD_NO,
            positiveText = WORD_DELETE,
            onClickNegative = { updateDeleteDialogVisibility(false) },
            onClickPositive = {
                deleteChallenge(uiState.myChallengeList[pagerState.currentPage].id)
                updateDeleteDialogVisibility(false)
            }
        )
    }

    if (uiState.myChallengeList.isEmpty()) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 75.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_empty_my_challenge),
                contentDescription = null,
                tint = Color.Unspecified
            )

            Spacer(modifier = Modifier.height(22.dp))
            
            HuggText(
                text = MY_CHALLENGE_EMPTY,
                style = HuggTypography.h1,
                color = Gs70
            )
        }
    } else {
        Column(
            modifier = Modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            MyChallengeListPager(
                items = uiState.myChallengeList,
                currentChallengeState = uiState.currentChallengeState,
                pagerState = pagerState,
                currentChallengeDayOfWeek = uiState.currentChallengeDayOfWeek,
                interactionSource = interactionSource,
                onPageChange = onPageChange,
                onClickStateItem = { state ->
                    if (state == MyChallengeState.YESTERDAY) {
                        completeChallenge("", MyChallengeState.YESTERDAY)
                    } else {
                        updateCommentDialogVisibility(true)
                    }
                },
                onClickBtnDelete = { updateDeleteDialogVisibility(true) }
            )

            Spacer(modifier = Modifier.height(8.dp))

            PagerIndicator(
                pagerState = pagerState
            )

            Spacer(modifier = Modifier.weight(1f))

            ChallengeSupportGuideItem(
                goToChallengeSupport = goToChallengeSupport,
                interactionSource = interactionSource,
                items = uiState.myChallengeList,
                pagerState = pagerState
            )

            Spacer(modifier = Modifier.height(63.dp))
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MyChallengeListPager(
    items: List<MyChallengeListItemVo>,
    currentChallengeState: List<MyChallengeState>,
    pagerState: PagerState,
    currentChallengeDayOfWeek: Int,
    interactionSource: MutableInteractionSource,
    onPageChange: (Int) -> Unit,
    onClickStateItem: (MyChallengeState) -> Unit,
    onClickBtnDelete: () -> Unit
) {
    LaunchedEffect(pagerState.currentPage) {
        onPageChange(pagerState.currentPage)
    }

    HorizontalPager(
        state = pagerState,
        modifier = Modifier
            .fillMaxWidth(),
        key = { items[it].id },
        contentPadding = PaddingValues(horizontal = 32.dp),
        pageSpacing = 8.dp
    ) {
        MyChallengeItem(
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
            item = items[it],
            currentChallengeState = currentChallengeState,
            currentChallengeDayOfWeek = currentChallengeDayOfWeek,
            interactionSource = interactionSource,
            onClickStateItem = onClickStateItem,
            onClickBtnDelete = onClickBtnDelete
        )
    }
}

@Composable
fun MyChallengeItem(
    modifier: Modifier = Modifier,
    item: MyChallengeListItemVo,
    currentChallengeState: List<MyChallengeState>,
    currentChallengeDayOfWeek: Int,
    interactionSource: MutableInteractionSource,
    onClickStateItem: (MyChallengeState) -> Unit,
    onClickBtnDelete: () -> Unit
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
                verticalArrangement = Arrangement.Top
            ) {
                Row {
                    Spacer(modifier = Modifier.weight(1f))
                    Icon(
                        painter = painterResource(id = R.drawable.ic_trash_gs_30),
                        contentDescription = null,
                        tint = Color.Unspecified,
                        modifier = Modifier
                            .padding(top = 12.dp, end = 12.dp)
                            .clickable(
                                interactionSource = interactionSource,
                                indication = null,
                                onClick = onClickBtnDelete
                            )
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                HuggText(
                    text = item.name,
                    style = HuggTypography.h1,
                    color = GsBlack,
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(4.dp))

                ChallengeParticipantsBox(participants = item.participants)

                Spacer(modifier = Modifier.height(26.dp))

                if (currentChallengeState.isNotEmpty()) {
                    CurrentChallengeProgress(
                        currentChallengeState = currentChallengeState,
                        dayOfWeek = currentChallengeDayOfWeek,
                        interactionSource = interactionSource,
                        onClickStateItem = onClickStateItem
                    )
                    Spacer(modifier = Modifier.height(21.dp))
                }
            }
        }
    }
}

@Composable
fun CurrentChallengeProgress(
    currentChallengeState: List<MyChallengeState>,
    dayOfWeek: Int,
    interactionSource: MutableInteractionSource,
    onClickStateItem: (MyChallengeState) -> Unit
) {
    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp.dp
    val horizontalPadding = 58.dp * 2
    val boxWidth = screenWidth - horizontalPadding

    Column(
        horizontalAlignment = Alignment.Start
    ) {
        Box(
            modifier = Modifier.padding(horizontal = 12.dp)
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_hugging_female),
                contentDescription = null,
                tint = Color.Unspecified,
                modifier = Modifier
                    .size(57.dp)
                    .offset(
                        x = ((dayOfWeek - 1) * (boxWidth.value / 7)).dp,
                        y = 0.dp
                    )
            )
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp)
                .aspectRatio(283f / 180f)
                .clip(RoundedCornerShape(8.dp))
                .background(Disabled)
        ) {

            Column {
                Spacer(modifier = Modifier.height(12.dp))
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 12.dp)
                        .aspectRatio(259f / 20f)
                        .clip(RoundedCornerShape(999.dp))
                ) {
                    for (i in 1..7) {
                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .fillMaxHeight()
                                .background(
                                    if (i <= dayOfWeek) Sub else GsWhite
                                )
                        )
                    }
                }
                Spacer(modifier = Modifier.height(24.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(
                        24.dp,
                        Alignment.CenterHorizontally
                    )
                ) {
                    repeat(4) { i ->
                        StateItem(
                            state = currentChallengeState[2 * i],
                            onClick = onClickStateItem,
                            interactionSource = interactionSource
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(
                        24.dp,
                        Alignment.CenterHorizontally
                    )
                ) {
                    repeat(3) { i ->
                        StateItem(
                            state = currentChallengeState[2 * i + 1],
                            onClick = onClickStateItem,
                            interactionSource = interactionSource
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun StateItem(
    state: MyChallengeState,
    onClick: (MyChallengeState) -> Unit,
    interactionSource: MutableInteractionSource
) {
    Box(
        modifier = Modifier
            .size(44.dp)
            .clip(CircleShape)
            .background(
                when (state) {
                    MyChallengeState.SUCCESS -> Gs80
                    MyChallengeState.NOT_YET, MyChallengeState.YESTERDAY -> Disabled
                    MyChallengeState.TODAY -> Sub
                    MyChallengeState.FAIL -> Gs20
                }
            )
            .then(
                if (state == MyChallengeState.NOT_YET || state == MyChallengeState.YESTERDAY) {
                    Modifier.border(
                        width = 1.dp,
                        color = if (state == MyChallengeState.NOT_YET) Gs30 else Sub,
                        shape = CircleShape
                    )
                } else Modifier
            )
            .clickable(
                interactionSource = interactionSource,
                indication = null,
                onClick = {
                    if (state == MyChallengeState.YESTERDAY || state == MyChallengeState.TODAY) {
                        onClick(state)
                    }
                }
            ),
        contentAlignment = Alignment.Center
    ) {
        HuggText(
            text = when (state) {
                MyChallengeState.SUCCESS -> WORD_SUCCESS
                MyChallengeState.FAIL -> String.format(CHALLENGE_POINT, 0)
                MyChallengeState.TODAY, MyChallengeState.NOT_YET -> String.format(CHALLENGE_POINT, 100)
                MyChallengeState.YESTERDAY -> String.format(CHALLENGE_POINT, 50)
            },
            style = HuggTypography.p1,
            color = when (state) {
                MyChallengeState.SUCCESS,MyChallengeState.TODAY, MyChallengeState.FAIL -> GsWhite
                MyChallengeState.YESTERDAY, MyChallengeState.NOT_YET  -> Gs50
            }
        )
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ChallengeSupportGuideItem(
    goToChallengeSupport: (Long) -> Unit,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    items: List<MyChallengeListItemVo>,
    pagerState: PagerState
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .clip(RoundedCornerShape(8.dp))
            .background(MainNormal)
            .aspectRatio(343f / 90f),
        contentAlignment = Alignment.Center
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp, vertical = 11.dp)
                .clickable(
                    interactionSource = interactionSource,
                    indication = null,
                    onClick = { goToChallengeSupport(items[pagerState.currentPage].id) }
                ),
            verticalAlignment = Alignment.CenterVertically
        ) {
            HuggText(
                text = CHALLENGE_SUPPORT_GUIDE,
                style = HuggTypography.h1,
                color = GsWhite
            )

            Spacer(modifier = Modifier.weight(1f))

            Icon(
                painter = painterResource(id = R.drawable.ic_arrow_right_white_24),
                contentDescription = null,
                tint = Color.Unspecified
            )
        }
    }
}