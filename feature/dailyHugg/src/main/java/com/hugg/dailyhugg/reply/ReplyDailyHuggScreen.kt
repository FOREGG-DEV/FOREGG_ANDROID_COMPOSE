package com.hugg.dailyhugg.reply

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
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
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.rememberAsyncImagePainter
import com.hugg.domain.model.enums.DailyConditionType
import com.hugg.domain.model.enums.DailyHuggReplyType
import com.hugg.domain.model.enums.TopBarLeftType
import com.hugg.domain.model.enums.TopBarMiddleType
import com.hugg.domain.model.response.dailyHugg.DailyHuggItemVo
import com.hugg.feature.R
import com.hugg.feature.component.HuggText
import com.hugg.feature.component.HuggTextField
import com.hugg.feature.component.TopBar
import com.hugg.feature.theme.ACCOUNT_NICKNAME_TEXT_FIELD_HINT
import com.hugg.feature.theme.Background
import com.hugg.feature.theme.Black
import com.hugg.feature.theme.DAILY_HUGG
import com.hugg.feature.theme.FEMALE
import com.hugg.feature.theme.Gs50
import com.hugg.feature.theme.Gs70
import com.hugg.feature.theme.Gs90
import com.hugg.feature.theme.GsBlack
import com.hugg.feature.theme.HuggTypography
import com.hugg.feature.theme.REPLY_ACTION
import com.hugg.feature.theme.REPLY_EMPTY_TEXT
import com.hugg.feature.theme.REPLY_MAX_TEXT_COUNT
import com.hugg.feature.theme.SPOUSE_DAILY_HUGG
import com.hugg.feature.theme.THIS_WEEK_QUESTION
import com.hugg.feature.theme.White
import com.hugg.feature.util.UserInfo

@Composable
fun ReplyDailyHuggScreen(
    goToSuccessReplyPage: () -> Unit,
    popScreen: () -> Unit = {},
    selectedDate: String = ""
) {
    val viewModel: ReplyDailyHuggViewModel = hiltViewModel()
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val interactionSource = remember { MutableInteractionSource() }

    LaunchedEffect(Unit) {
        viewModel.getDailyHuggByDate(selectedDate)
    }

    LaunchedEffect(Unit) {
        viewModel.eventFlow.collect { event ->
            when(event) {
                ReplyDailyHuggEvent.CompleteReplyDailyHugg -> goToSuccessReplyPage()
            }
        }
    }

    ReplyDailyHuggContent(
        uiState = uiState,
        interactionSource = interactionSource,
        popScreen = popScreen,
        onSelectedReplyType = { type -> viewModel.onSelectedReplyType(type) },
        onChangedReply = { reply -> viewModel.onChangedReply(reply)}
    )
}

@Composable
fun ReplyDailyHuggContent(
    uiState: ReplyDailyHuggPageState = ReplyDailyHuggPageState(),
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    popScreen: () -> Unit = {},
    onSelectedReplyType : (DailyHuggReplyType) -> Unit = {},
    onChangedReply : (String) -> Unit = {},
) {
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Background)
        ) {
            TopBar(
                middleText = DAILY_HUGG,
                middleItemType = TopBarMiddleType.TEXT,
                leftItemType = TopBarLeftType.CLOSE,
                leftBtnClicked = { popScreen() }
            )

            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                item {
                    Spacer(modifier = Modifier.height(24.dp))
                    HuggText(
                        text = String.format(SPOUSE_DAILY_HUGG, UserInfo.info.spouse),
                        color = Black,
                        style = HuggTypography.h1
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                }

                item {
                    if(uiState.dailyHugg != null && uiState.dailyHugg.specialQuestion.isNotEmpty()){
                        SpecialQuestion(uiState.dailyHugg.specialQuestion)
                        Spacer(modifier = Modifier.height(8.dp))
                    }
                    else{
                        Spacer(modifier = Modifier.height(4.dp))
                    }
                }

                item {
                    if(uiState.dailyHugg != null){
                        DailyHuggItem(
                            item = uiState.dailyHugg,
                        )
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                }

                item {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Start
                    ) {
                        HuggText(
                            text = REPLY_ACTION,
                            style = HuggTypography.h4,
                            color = Black
                        )
                    }

                    Spacer(modifier = Modifier.size(4.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        DailyHuggReplyType.entries.forEach {
                            if (it != DailyHuggReplyType.NOTHING) {
                                BtnReplyActionItem(
                                    replyType = it,
                                    interactionSource = interactionSource,
                                    onSelectedReplyType = onSelectedReplyType,
                                    selectedReplyType = uiState.selectedReplyType
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.size(8.dp))
                }

                item {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(color = Color.White, shape = RoundedCornerShape(8.dp))
                            .padding(horizontal = 12.dp, vertical = 6.dp)
                            .padding(top = 6.dp)
                    ) {
                        if (uiState.reply.isEmpty()) {
                            HuggText(
                                text = REPLY_EMPTY_TEXT,
                                style = HuggTypography.p2,
                                color = Gs50,
                            )
                        }

                        Column {
                            HuggTextField(
                                value = uiState.reply,
                                onValueChange = { value ->
                                    onChangedReply(value)
                                },
                                textStyle = HuggTypography.p2_l.copy(
                                    color = Black,
                                ),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .defaultMinSize(minHeight = 40.dp),
                            )

                            Spacer(modifier = Modifier.size(4.dp))

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.End
                            ) {
                                HuggText(
                                    text = String.format(REPLY_MAX_TEXT_COUNT, uiState.reply.length),
                                    style = HuggTypography.p3_l,
                                    color = Gs70
                                )
                            }
                        }
                    }
                }

            }
        }
    }
}

@Composable
fun DailyHuggItem(
    item: DailyHuggItemVo = DailyHuggItemVo(),
) {
    val resourceId = getDailyHuggIcon(item.dailyConditionType)
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .border(BorderStroke(1.dp, FEMALE), shape = RoundedCornerShape(8.dp))
            .clip(RoundedCornerShape(8.dp))
            .background(White)
    ) {
        Column(
            modifier = Modifier
                .padding(12.dp)
        ) {
            Row(
                verticalAlignment = Alignment.Bottom
            ) {
                Icon(
                    painter = painterResource(id = resourceId),
                    contentDescription = "",
                    tint = Color.Unspecified,
                )

                Spacer(modifier = Modifier.width(8.dp))

                Column(
                    horizontalAlignment = Alignment.Start
                ) {
                    HuggText(
                        text = item.dailyConditionType.value,
                        style = HuggTypography.p1,
                        color = Gs90
                    )

                    HuggText(
                        text = item.date.split(' ').last().substring(0, 5),
                        style = HuggTypography.p3_l,
                        color = Gs70
                    )
                }
            }
            Spacer(modifier = Modifier.height(12.dp))
            HuggText(
                text = item.content,
                style = HuggTypography.p2_l,
                color = GsBlack
            )
            Spacer(modifier = Modifier.height(8.dp))
            if(item.imageUrl != null) UrlToImage(url = item.imageUrl!!)
        }
    }
}

@Composable
fun UrlToImage(
    url: String,
    ratio: Float = 319f / 200f,
    radius: Dp = 4.dp
) {
    Image(
        painter = rememberAsyncImagePainter(model = url),
        contentDescription = null,
        contentScale = ContentScale.Crop,
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(ratio)
            .clip(RoundedCornerShape(radius))
    )
}

@Composable
fun SpecialQuestion(
    question : String = "",
){
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = White, shape = RoundedCornerShape(8.dp))
            .padding(horizontal = 12.dp)
    ) {
        Spacer(modifier = Modifier.size(12.dp))

        HuggText(
            text = THIS_WEEK_QUESTION,
            color = Black,
            style = HuggTypography.p2
        )

        Spacer(modifier = Modifier.size(4.dp))

        HuggText(
            text = question,
            color = Black,
            style = HuggTypography.p1_l
        )

        Spacer(modifier = Modifier.size(8.dp))
    }
}

@Composable
fun BtnReplyActionItem(
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    replyType: DailyHuggReplyType = DailyHuggReplyType.NOTHING,
    onSelectedReplyType: (DailyHuggReplyType) -> Unit = {},
    selectedReplyType: DailyHuggReplyType
) {
    val resourceId = getReplyIcon(
        replyType = replyType,
        isSelected = selectedReplyType == replyType
    )

    Box(
        modifier = Modifier
            .size(62.dp)
            .clickable(
                interactionSource = interactionSource,
                indication = null,
                onClick = { onSelectedReplyType(replyType) }
            ),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            painter = painterResource(id = resourceId),
            contentDescription = "",
            tint = Color.Unspecified
        )
    }
}

fun getDailyHuggIcon(
    dailyConditionType: DailyConditionType
) : Int {
    return when (dailyConditionType) {
        DailyConditionType.MAD -> R.drawable.ic_daily_condition_worst_selected
        DailyConditionType.SAD -> R.drawable.ic_daily_condition_bad_selected
        DailyConditionType.ANXIOUS -> R.drawable.ic_daily_condition_soso_selected
        DailyConditionType.HAPPY -> R.drawable.ic_daily_condition_good_selected
        DailyConditionType.LOVE -> R.drawable.ic_daily_condition_perfect_selected
        DailyConditionType.DEFAULT -> -1
    }
}

fun getReplyIcon(
    replyType: DailyHuggReplyType,
    isSelected: Boolean
): Int {
    return when (replyType) {
        DailyHuggReplyType.HUG -> if(isSelected) R.drawable.ic_hug_active else R.drawable.ic_hug_inactive
        DailyHuggReplyType.ARM -> if(isSelected) R.drawable.ic_arm_active else R.drawable.ic_arm_inactive
        DailyHuggReplyType.LETTER -> if(isSelected) R.drawable.ic_letter_active else R.drawable.ic_letter_inactive
        DailyHuggReplyType.CLAP -> if(isSelected) R.drawable.ic_clap_active else R.drawable.ic_clap_inactive
        DailyHuggReplyType.MEAL -> if(isSelected) R.drawable.ic_meal_active else R.drawable.ic_meal_inactive
        DailyHuggReplyType.NOTHING -> -1
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PreviewDailyHugg() {
    ReplyDailyHuggContent(
        uiState = ReplyDailyHuggPageState(
            dailyHugg = DailyHuggItemVo()
        )
    )
}