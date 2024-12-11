package com.hugg.create

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.hugg.domain.model.enums.TopBarLeftType
import com.hugg.domain.model.enums.TopBarMiddleType
import com.hugg.feature.R
import com.hugg.feature.component.FilledBtn
import com.hugg.feature.component.HuggText
import com.hugg.feature.component.HuggTextField
import com.hugg.feature.component.TopBar
import com.hugg.feature.theme.Background
import com.hugg.feature.theme.CREATE_CHALLENGE_COMPLETED
import com.hugg.feature.theme.CREATE_CHALLENGE_DESCRIPTION_HINT
import com.hugg.feature.theme.CREATE_CHALLENGE_GUIDE
import com.hugg.feature.theme.CREATE_CHALLENGE_NAME_HINT
import com.hugg.feature.theme.CREATE_CHALLENGE_TOP_BAR
import com.hugg.feature.theme.Gs10
import com.hugg.feature.theme.Gs30
import com.hugg.feature.theme.Gs50
import com.hugg.feature.theme.GsWhite
import com.hugg.feature.theme.HuggTypography
import com.hugg.feature.theme.INSUFFICIENT_POINT
import com.hugg.feature.theme.WORD_COMPLETE
import com.hugg.feature.util.HuggToast

@Composable
fun CreateChallengeScreen(
    popScreen: () -> Unit
) {
    val viewModel: CreateChallengeViewModel = hiltViewModel()
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val context = LocalContext.current
    val interactionSource: MutableInteractionSource = remember { MutableInteractionSource() }

    LaunchedEffect(Unit) {
        viewModel.eventFlow.collect { event ->
            when(event) {
                CreateChallengeEvent.CreateChallengeCompleted -> {
                    HuggToast.createToast(context, CREATE_CHALLENGE_COMPLETED).show()
                    popScreen()
                }
                CreateChallengeEvent.InSufficientPoint -> {
                    HuggToast.createToast(context, INSUFFICIENT_POINT).show()
                }
            }
        }
    }

    CreateChallengeContent(
        uiState = uiState,
        onClickBtnBack = { popScreen() },
        onNameChange = { viewModel.onNameChanged(it) },
        onDescriptionChange = { viewModel.onDescriptionChanged(it) },
        onClickBtnComplete = { viewModel.createChallenge() },
        updateEmojiVisibility = { viewModel.updateEmojiVisibility(it) },
        interactionSource = interactionSource,
        onClickEmoji = { viewModel.updateSelectedEmojiIndex(it) }
    )
}

@Composable
fun CreateChallengeContent(
    uiState: CreateChallengePageState = CreateChallengePageState(),
    onClickBtnBack: () -> Unit = {},
    onNameChange: (String) -> Unit = {},
    onDescriptionChange: (String) -> Unit = {},
    onClickBtnComplete: () -> Unit = {},
    updateEmojiVisibility: (Boolean) -> Unit = {},
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    onClickEmoji: (Int) -> Unit = {}
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Background),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TopBar(
            leftItemType = TopBarLeftType.BACK,
            leftBtnClicked = onClickBtnBack,
            middleItemType = TopBarMiddleType.TEXT,
            middleText = CREATE_CHALLENGE_TOP_BAR
        )
        
        Spacer(modifier = Modifier.height(24.dp))

        HuggText(
            text = CREATE_CHALLENGE_GUIDE,
            style = HuggTypography.h1
        )

        Spacer(modifier = Modifier.height(30.dp))

        CreateChallengeCard(
            uiState = uiState,
            onNameChange = onNameChange,
            onDescriptionChange = onDescriptionChange,
            updateEmojiVisibility = updateEmojiVisibility,
            interactionSource = interactionSource,
            onClickEmoji = onClickEmoji
        )

        Spacer(modifier = Modifier.weight(1f))

        FilledBtn(
            text = WORD_COMPLETE,
            textColor = if (uiState.challengeName.isNotEmpty() && uiState.challengeDescription.isNotEmpty() && uiState.selectedEmojiIndex != -1) GsWhite else Gs30,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            onClickBtn = onClickBtnComplete
        )

        Spacer(modifier = Modifier.height(80.dp))
    }
}

@Composable
fun CreateChallengeCard(
    uiState: CreateChallengePageState,
    onNameChange: (String) -> Unit = {},
    onDescriptionChange: (String) -> Unit = {},
    updateEmojiVisibility: (Boolean) -> Unit = {},
    interactionSource: MutableInteractionSource,
    onClickEmoji: (Int) -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 34.dp)
            .aspectRatio(307f / 404f)
            .clip(RoundedCornerShape(8.dp))
            .background(GsWhite),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Spacer(modifier = Modifier.height(32.dp))
            Box(
                contentAlignment = Alignment.Center
            ) {
                if (uiState.selectedEmojiIndex == -1) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_challenge_emoji_selector),
                        contentDescription = null,
                        tint = Color.Unspecified,
                        modifier = Modifier
                            .size(178.dp)
                            .clickable(
                                interactionSource = interactionSource,
                                indication = null,
                                onClick = { updateEmojiVisibility(true) }
                            )
                    )
                } else {
                    Box(
                        modifier = Modifier.size(178.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_challenge_emoji_selector_frame),
                            contentDescription = null,
                            tint = Color.Unspecified
                        )

                        Icon(
                            painter = painterResource(uiState.emojiList[uiState.selectedEmojiIndex].first),
                            contentDescription = null,
                            tint = Color.Unspecified,
                            modifier = Modifier
                                .size(92.dp)
                                .align(Alignment.Center)
                        )
                    }
                }

                if (uiState.showEmojiList) {
                    Box(
                        modifier = Modifier.align(Alignment.BottomCenter)
                    ) {
                        EmojiList(
                            updateEmojiVisibility = updateEmojiVisibility,
                            emojiList = uiState.emojiList,
                            onClickEmoji = onClickEmoji,
                            interactionSource = interactionSource
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.height(27.dp))
            CreateChallengeTextField(
                value = uiState.challengeName,
                onValueChange = onNameChange,
                hint = CREATE_CHALLENGE_NAME_HINT,
                textStyle = HuggTypography.h1,
                alignment = Alignment.Center,
                aspectRatio = 283f / 40f
            )
            Spacer(modifier = Modifier.height(8.dp))
            CreateChallengeTextField(
                value = uiState.challengeDescription,
                onValueChange = onDescriptionChange,
                hint = CREATE_CHALLENGE_DESCRIPTION_HINT,
                textStyle = HuggTypography.p3_l,
                alignment = Alignment.TopCenter,
                aspectRatio = 283f / 65f
            )
            Spacer(modifier = Modifier.height(54.dp))
        }

    }
}

@Composable
fun EmojiList(
    updateEmojiVisibility: (Boolean) -> Unit,
    emojiList: List<Pair<Int, String>>,
    onClickEmoji: (Int) -> Unit,
    interactionSource: MutableInteractionSource
) {
    Row(
        modifier = Modifier
            .background(GsWhite)
            .clip(RoundedCornerShape(8.dp))
            .padding(horizontal = 5.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        emojiList.forEachIndexed { index, item ->
            Icon(
                painter = painterResource(id = item.first),
                contentDescription = null,
                tint = Color.Unspecified,
                modifier = Modifier
                    .clickable(
                        indication = null,
                        interactionSource = interactionSource,
                        onClick = {
                            updateEmojiVisibility(false)
                            onClickEmoji(index)
                        }
                    )
            )
        }
    }
}

@SuppressLint("ModifierParameter")
@Composable
fun CreateChallengeTextField(
    value: String = "",
    onValueChange: (String) -> Unit = {},
    hint: String = "",
    textStyle: TextStyle,
    alignment: Alignment,
    aspectRatio: Float
) {
    HuggTextField(
        value = value,
        onValueChange = onValueChange,
        modifier = Modifier
            .padding(horizontal = 12.dp)
            .aspectRatio(aspectRatio),
        textStyle = textStyle
    ) { innerTextField ->
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(8.dp))
                .border(width = 1.dp, color = Gs10, shape = RoundedCornerShape(8.dp))
        ) {
            Box(
                modifier = Modifier
                    .align(Alignment.CenterStart)
                    .padding(horizontal = 4.dp)
            ) {
                innerTextField()
            }

            if (value.isEmpty()) {
                HuggText(
                    text = hint,
                    style = textStyle,
                    color = Gs50,
                    modifier = Modifier
                        .align(alignment)
                        .then(
                            if (alignment == Alignment.TopCenter) Modifier.padding(top = 7.dp) else Modifier
                        )
                )
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PreviewCreateChallenge() {
    CreateChallengeContent()
}