package com.hugg.dailyhugg.main

import android.net.Uri
import androidx.compose.foundation.BorderStroke
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.rememberAsyncImagePainter
import com.hugg.dailyhugg.create.CreateEditDailyHuggPageState
import com.hugg.domain.model.enums.DailyConditionType
import com.hugg.domain.model.enums.DialogType
import com.hugg.domain.model.enums.TopBarLeftType
import com.hugg.domain.model.enums.TopBarMiddleType
import com.hugg.domain.model.enums.TopBarRightType
import com.hugg.domain.model.response.dailyHugg.DailyHuggItemVo
import com.hugg.feature.R
import com.hugg.feature.component.HuggDialog
import com.hugg.feature.component.HuggText
import com.hugg.feature.component.PlusBtn
import com.hugg.feature.component.TopBar
import com.hugg.feature.theme.Background
import com.hugg.feature.theme.COMPLETE_DELETE_DAILY_HUGG
import com.hugg.feature.theme.DAILY_HUGG
import com.hugg.feature.theme.DAILY_HUGG_DATE
import com.hugg.feature.theme.DAILY_HUGG_LIST_TITLE
import com.hugg.feature.theme.DELETE_DAILY_HUGG
import com.hugg.feature.theme.DELETE_DAILY_HUGG_TITLE
import com.hugg.feature.theme.EDIT_DAILY_HUGG_DIALOG_TITLE
import com.hugg.feature.theme.EDIT_DAILY_HUGG_DIALOG_WARNING
import com.hugg.feature.theme.EMPTY_HUGG_FEMALE
import com.hugg.feature.theme.EMPTY_HUGG_MALE
import com.hugg.feature.theme.EMPTY_REPLY
import com.hugg.feature.theme.FEMALE
import com.hugg.feature.theme.Gs10
import com.hugg.feature.theme.Gs20
import com.hugg.feature.theme.Gs60
import com.hugg.feature.theme.Gs70
import com.hugg.feature.theme.Gs90
import com.hugg.feature.theme.GsBlack
import com.hugg.feature.theme.HuggTypography
import com.hugg.feature.theme.MALE
import com.hugg.feature.theme.MainNormal
import com.hugg.feature.theme.REPLY_COUNT
import com.hugg.feature.theme.Sunday
import com.hugg.feature.theme.WORD_DELETE
import com.hugg.feature.theme.White
import com.hugg.feature.util.HuggToast
import com.hugg.feature.util.TimeFormatter
import com.hugg.feature.util.onThrottleClick

@Composable
fun DailyHuggScreen(
    onClickCreateDailyHugg: () -> Unit,
    onClickDailyHuggItem: (CreateEditDailyHuggPageState, Long) -> Unit,
    goToDailyHuggList: () -> Unit,
    popScreen: () -> Unit = {},
    selectedDate: String = ""
) {
    val viewModel: DailyHuggViewModel = hiltViewModel()
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val interactionSource = remember { MutableInteractionSource() }
    val context = LocalContext.current
    val onClickBtnNextDay = {
        val newDate = TimeFormatter.getNextDate(uiState.date)
        viewModel.setDate(
            newDate = newDate,
            newDay = TimeFormatter.getKoreanFullDayOfWeek(newDate)
        )
    }
    val onClickBtnPreviousDay = {
        val newDate = TimeFormatter.getPreviousDate(uiState.date)
        viewModel.setDate(
            newDate = newDate,
            newDay = TimeFormatter.getKoreanFullDayOfWeek(newDate)
        )
    }
    val dateText = TimeFormatter.getTodayYearMonthDayKor(uiState.date)

    LaunchedEffect(Unit) {
        if (selectedDate.isEmpty()) {
            if (uiState.date.isEmpty()) {
                val today = TimeFormatter.getToday()
                viewModel.setDate(
                    newDate = today,
                    newDay = TimeFormatter.getKoreanFullDayOfWeek(today)
                )
            } else {
                viewModel.getDailyHuggByDate(uiState.date)
            }
        } else {
            val today = selectedDate.split(" ").first()
            viewModel.setDate(
                newDate = today,
                newDay = TimeFormatter.getKoreanFullDayOfWeek(today)
            )
        }
    }

    LaunchedEffect(Unit) {
        viewModel.eventFlow.collect { event ->
            when(event) {
                DailyHuggEvent.CompleteDeleteDailyHugg -> HuggToast.createToast(context, COMPLETE_DELETE_DAILY_HUGG).show()
                DailyHuggEvent.GoToDailyHuggList -> goToDailyHuggList()
            }
        }
    }

    if (uiState.showEditDialog) {
        HuggDialog(
            hasWarningText = true,
            warningMessage = EDIT_DAILY_HUGG_DIALOG_WARNING,
            title = EDIT_DAILY_HUGG_DIALOG_TITLE,
            onClickNegative = { viewModel.updateShowEditDialog(false) },
            onClickPositive = {
                uiState.dailyHugg?.let {
                    onClickDailyHuggItem(
                        CreateEditDailyHuggPageState(
                            dailyHuggContent = it.content,
                            dailyConditionType = DailyConditionType.fromValue(it.dailyConditionType)!!,
                            selectedImageUri = Uri.parse(it.imageUrl)
                        ),
                        it.id
                    )
                }
                viewModel.updateShowEditDialog(false)
            }
        )
    }

    if (uiState.showDeleteDialog) {
        HuggDialog(
            title = DELETE_DAILY_HUGG_TITLE,
            onClickNegative = { viewModel.updateShowDeleteDialog(false) },
            onClickPositive = {
                viewModel.deleteDailyHugg()
                viewModel.updateShowDeleteDialog(false)
            },
            positiveColor = Sunday,
            positiveText = WORD_DELETE
        )
    }

    DailyHuggContent(
        uiState = uiState,
        interactionSource = interactionSource,
        onClickCreateDailyHugg = onClickCreateDailyHugg,
        onClickBtnNextDay = onClickBtnNextDay,
        onClickBtnPreviousDay = onClickBtnPreviousDay,
        dateText = dateText,
        onClickDailyHuggItem = {
            viewModel.updateShowEditDialog(true)
        },
        onClickBtnDeleteDailyHugg = { viewModel.updateShowDeleteDialog(true) },
        onClickBtnDailyHuggList = { viewModel.onClickBtnDailyHuggList() },
        popScreen = popScreen,
        selectedDate = selectedDate
    )
}

@Composable
fun DailyHuggContent(
    uiState: DailyHuggPageState = DailyHuggPageState(),
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    onClickCreateDailyHugg: () -> Unit = {},
    onClickBtnNextDay: () -> Unit = {},
    onClickBtnPreviousDay: () -> Unit = {},
    dateText: String = "",
    onClickDailyHuggItem: () -> Unit = {},
    onClickBtnDeleteDailyHugg: () -> Unit = {},
    onClickBtnDailyHuggList: () -> Unit = {},
    popScreen: () -> Unit = {},
    selectedDate: String = "",
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
                middleText = if(selectedDate.isEmpty()) DAILY_HUGG else DAILY_HUGG_LIST_TITLE,
                middleItemType = TopBarMiddleType.TEXT,
                rightItemType = TopBarRightType.DAILY_RECORD,
                rightBtnClicked = { onClickBtnDailyHuggList() },
                leftItemType = if (selectedDate.isEmpty()) TopBarLeftType.NONE else TopBarLeftType.BACK,
                leftBtnClicked = { popScreen() }
            )

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(26.dp))

                DateNavigator(
                    date = dateText,
                    day = uiState.day,
                    interactionSource = interactionSource,
                    onClickBtnPreviousDay = { onClickBtnPreviousDay() },
                    onClickBtnNextDay = { onClickBtnNextDay() },
                    navigateEnabled = selectedDate.isEmpty()
                )

                Spacer(modifier = Modifier.height(22.dp))

                if (uiState.dailyHugg == null) EmptyDailyHuggContent()
                else DailyHuggItem(
                    item = uiState.dailyHugg,
                    onClickDailyHuggItem = onClickDailyHuggItem,
                    interactionSource = interactionSource
                )
                
                Spacer(modifier = Modifier.height(16.dp))

                if (uiState.dailyHugg != null) BtnDeleteDailyHugg(onClickBtnDeleteDailyHugg = onClickBtnDeleteDailyHugg)
            }
        }

        PlusBtn(
            interactionSource = interactionSource,
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(end = 16.dp, bottom = 24.dp)
                .shadow(
                    elevation = 6.dp,
                    shape = RoundedCornerShape(8.dp)
                )
                .size(56.dp)
                .background(color = MainNormal)
                .clickable(
                    onClick = { onClickCreateDailyHugg() },
                    interactionSource = interactionSource,
                    indication = null
                )
        )
    }
}

@Composable
fun DateNavigator(
    date: String = "2024년 8월 5일",
    day: String = "목요일",
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    onClickBtnNextDay: () -> Unit = {},
    onClickBtnPreviousDay: () -> Unit = {},
    navigateEnabled: Boolean = true
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        if (navigateEnabled) {
            BtnArrow(
                arrowIcon = R.drawable.ic_back_arrow_gs_20,
                interactionSource = interactionSource,
                onClick = { onClickBtnPreviousDay() },
                tint = Gs60
            )
        }

        HuggText(
            text = String.format(DAILY_HUGG_DATE, date, day),
            style = HuggTypography.h2,
            color = Gs90,
            textAlign = TextAlign.Center,
            modifier = Modifier.weight(1f)
        )

        if (navigateEnabled) {
            BtnArrow(
                interactionSource = interactionSource,
                onClick = { onClickBtnNextDay() },
                tint = if (date == TimeFormatter.getTodayYearMonthDayKor(TimeFormatter.getToday())) Gs20 else Gs60
            )
        }
    }
}

@Composable
fun BtnArrow(
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    arrowIcon: Int = R.drawable.ic_right_arrow_gs_20,
    onClick: () -> Unit = {},
    tint: Color = Color.Unspecified
) {
    Box(
        modifier = Modifier.clickable(
            indication = null,
            onClick = { if (tint == Gs60) onClick() },
            interactionSource = interactionSource
        )
    ) {
        Icon(
            painter = painterResource(id = arrowIcon),
            contentDescription = "",
            modifier = Modifier
                .size(48.dp),
            tint = tint
        )
    }
}

@Composable
fun EmptyDailyHuggContent() {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        EmptyHuggItem()
        Spacer(modifier = Modifier.height(8.dp))
        EmptyHuggItem(
            color = MALE,
            hugging = R.drawable.ic_hugging_male,
            msgBubble = R.drawable.ic_msg_bubble_male,
            msg = EMPTY_HUGG_MALE,
            msgBubbleAlignment = Alignment.BottomStart,
            msgPadding = PaddingValues(start = 93.dp, bottom = 45.dp),
            msgContentPadding = PaddingValues(start = 14.dp),
            iconAlignment = Alignment.BottomStart
        )
    }
}

@Composable
fun EmptyHuggItem(
    color: Color = FEMALE,
    hugging: Int = R.drawable.ic_hugging_female,
    msgBubble: Int = R.drawable.ic_msg_bubble_female,
    msg: String = EMPTY_HUGG_FEMALE,
    msgBubbleAlignment: Alignment = Alignment.BottomEnd,
    msgPadding: PaddingValues = PaddingValues(end = 93.dp, bottom = 45.dp),
    msgContentPadding: PaddingValues = PaddingValues(end = 14.dp),
    iconAlignment: Alignment = Alignment.BottomEnd
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .border(BorderStroke(1.dp, color), shape = RoundedCornerShape(8.dp))
            .clip(RoundedCornerShape(8.dp))
            .aspectRatio(343f / 169f)
            .background(White)
    ) {
        Box(
            modifier = Modifier
                .align(msgBubbleAlignment)
                .padding(msgPadding)
        ) {
            Icon(
                painter = painterResource(id = msgBubble),
                contentDescription = "",
                tint = Color.Unspecified,
            )

            HuggText(
                text = msg,
                textAlign = TextAlign.Center,
                color = Gs60,
                style = HuggTypography.h2,
                modifier = Modifier
                    .align(Alignment.Center)
                    .padding(msgContentPadding)
            )
        }

        Icon(
            painter = painterResource(id = hugging),
            contentDescription = "",
            tint = Color.Unspecified,
            modifier = Modifier.align(iconAlignment)
        )
    }
}

@Composable
fun DailyHuggItem(
    item: DailyHuggItemVo = DailyHuggItemVo(),
    onClickDailyHuggItem: () -> Unit = {},
    interactionSource: MutableInteractionSource
) {
    val resourceId = getDailyHuggIcon(DailyConditionType.fromValue(item.dailyConditionType) ?: DailyConditionType.DEFAULT)

    LazyColumn {
        item {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .border(BorderStroke(1.dp, FEMALE), shape = RoundedCornerShape(8.dp))
                    .clip(RoundedCornerShape(8.dp))
                    .background(White)
                    .onThrottleClick(
                        onClick = { onClickDailyHuggItem() },
                        interactionSource = interactionSource
                    )
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
                                text = item.dailyConditionType,
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

        item { Spacer(modifier = Modifier.height(8.dp)) }

        if (item.reply.isNotEmpty()) {
            item {
                ReplyItem(reply = item.reply)
            }
        } else {
            item {
                EmptyReplyItem()
            }
        }
    }
}

@Composable
fun EmptyReplyItem() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(8.dp))
            .background(Gs10),
        contentAlignment = Alignment.CenterStart
    ) {
        HuggText(
            text = EMPTY_REPLY,
            style = HuggTypography.p2,
            color = Gs70,
            modifier = Modifier.padding(12.dp)
        )
    }
}

@Composable
fun ReplyItem(
    reply: String = ""
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(79.dp)
            .border(BorderStroke(1.dp, MALE), shape = RoundedCornerShape(8.dp))
            .clip(RoundedCornerShape(8.dp))
            .background(White)
    ) {
        HuggText(
            text = reply,
            style = HuggTypography.p2_l,
            color = GsBlack,
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp)
        )

        HuggText(
            text = String.format(REPLY_COUNT, reply.length),
            style = HuggTypography.p3_l,
            color = Gs70,
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(12.dp)
        )
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
fun BtnDeleteDailyHugg(
    onClickBtnDeleteDailyHugg: () -> Unit = {}
) {
    Box(
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .size(width = 109.dp, height = 31.dp)
                .clip(RoundedCornerShape(4.dp))
                .background(Gs10)
                .clickable { onClickBtnDeleteDailyHugg() },
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_delete_gs_30),
                contentDescription = "",
                tint = Gs60,
                modifier = Modifier.size(24.dp)
            )

            HuggText(
                text = DELETE_DAILY_HUGG,
                color = Gs60,
                style = HuggTypography.p3
            )
        }
    }
}

fun getDailyHuggIcon(
    dailyConditionType: DailyConditionType
) : Int {
    return when (dailyConditionType) {
        DailyConditionType.WORST -> R.drawable.ic_daily_condition_worst_selected
        DailyConditionType.BAD -> R.drawable.ic_daily_condition_bad_selected
        DailyConditionType.SOSO -> R.drawable.ic_daily_condition_soso_selected
        DailyConditionType.GOOD -> R.drawable.ic_daily_condition_good_selected
        DailyConditionType.PERFECT -> R.drawable.ic_daily_condition_perfect_selected
        DailyConditionType.DEFAULT -> -1
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PreviewDailyHugg() {
    DailyHuggContent(
        uiState = DailyHuggPageState(
            dailyHugg = DailyHuggItemVo()
        )
    )
}