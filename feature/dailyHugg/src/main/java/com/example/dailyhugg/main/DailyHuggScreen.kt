package com.example.dailyhugg.main

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.rememberAsyncImagePainter
import coil.compose.rememberImagePainter
import com.hugg.domain.model.enums.TopBarMiddleType
import com.hugg.domain.model.enums.TopBarRightType
import com.hugg.domain.model.response.dailyHugg.DailyHuggItemVo
import com.hugg.feature.component.PlusBtn
import com.hugg.feature.component.TopBar
import com.hugg.feature.theme.Background
import com.hugg.feature.theme.DAILY_HUGG
import com.hugg.feature.theme.EMPTY_HUGG_FEMALE
import com.hugg.feature.theme.EMPTY_HUGG_MALE
import com.hugg.feature.theme.FEMALE
import com.hugg.feature.theme.Gs60
import com.hugg.feature.theme.Gs70
import com.hugg.feature.theme.Gs90
import com.hugg.feature.theme.GsBlack
import com.hugg.feature.theme.GsWhite
import com.hugg.feature.theme.HuggTypography
import com.hugg.feature.theme.MALE
import com.hugg.feature.theme.MainLight
import com.hugg.feature.theme.MainNormal
import com.hugg.feature.theme.REPLY_COUNT
import com.hugg.feature.theme.ROUND_TEXT
import com.hugg.feature.theme.White

@Composable
fun DailyHuggScreen(
    onClickCreateDailyHugg: () -> Unit
) {
    val viewModel: DailyHuggViewModel = hiltViewModel()
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val interactionSource = remember { MutableInteractionSource() }

    DailyHuggContent(
        uiState = uiState,
        interactionSource = interactionSource,
        onClickCreateDailyHugg = onClickCreateDailyHugg
    )
}

@Composable
fun DailyHuggContent(
    uiState: DailyHuggPageState = DailyHuggPageState(),
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    onClickCreateDailyHugg: () -> Unit = {}
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
                rightItemType = TopBarRightType.DAILY_RECORD,
            )

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(12.dp))

                BtnRound(
                    interactionSource = interactionSource,
                    round = uiState.round
                )

                Spacer(modifier = Modifier.height(14.dp))

                DateNavigator(interactionSource = interactionSource)

                Spacer(modifier = Modifier.height(22.dp))

                if (uiState.dailyHugg.isEmpty()) EmptyDailyHuggContent()
                else DailyHuggItem(
                    item = uiState.dailyHugg.last()
                )
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
fun BtnRound(
    round: Int = 0,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() }
) {
    Box(
        modifier = Modifier
            .width(84.dp)
            .height(24.dp)
            .clip(RoundedCornerShape(6.dp))
            .background(MainNormal)
            .clickable(
                onClick = {},
                indication = null,
                interactionSource = interactionSource
            )
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = String.format(ROUND_TEXT, round),
                color = GsWhite,
                style = HuggTypography.h4
            )

            Spacer(modifier = Modifier.width(8.dp))

            Divider(
                color = MainLight,
                modifier = Modifier
                    .height(20.dp)
                    .width(0.5.dp)
                    .padding(vertical = 3.dp)
            )

            Spacer(modifier = Modifier.width(8.dp))

            Icon(
                painter = painterResource(id = com.hugg.feature.R.drawable.ic_plus_white),
                contentDescription = "",
                tint = Color.Unspecified,
                modifier = Modifier.size(12.dp)
            )
        }
    }
}

@Composable
fun DateNavigator(
    date: String = "2024년 8월 5일 목요일",
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() }
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        BtnArrow(
            arrowIcon = com.hugg.feature.R.drawable.ic_back_arrow_gs_20,
            interactionSource = interactionSource
        )

        Text(
            text = date,
            style = HuggTypography.h2,
            color = Gs90,
            modifier = Modifier.weight(1f),
            textAlign = TextAlign.Center
        )

        BtnArrow(
            interactionSource = interactionSource
        )
    }
}

@Composable
fun BtnArrow(
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    arrowIcon: Int = com.hugg.feature.R.drawable.ic_right_arrow_gs_20
) {
    Box(
        modifier = Modifier.clickable(
            indication = null,
            onClick = {},
            interactionSource = interactionSource
        )
    ) {
        Icon(
            painter = painterResource(id = arrowIcon),
            contentDescription = "",
            modifier = Modifier
                .size(48.dp),
            tint = Color.Unspecified
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
            hugging = com.hugg.feature.R.drawable.ic_hugging_male,
            msgBubble = com.hugg.feature.R.drawable.ic_msg_bubble_male,
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
    hugging: Int = com.hugg.feature.R.drawable.ic_hugging_female,
    msgBubble: Int = com.hugg.feature.R.drawable.ic_msg_bubble_female,
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

            Text(
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
    item: DailyHuggItemVo = DailyHuggItemVo()
) {

    LazyColumn {
        item {
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
                            painter = painterResource(id = com.hugg.feature.R.drawable.ic_worst),
                            contentDescription = "",
                            tint = Color.Unspecified,
                        )

                        Spacer(modifier = Modifier.width(8.dp))

                        Column(
                            horizontalAlignment = Alignment.Start
                        ) {
                            Text(
                                text = item.dailyConditionType.value,
                                style = HuggTypography.p1,
                                color = Gs90
                            )

                            Text(
                                text = item.date,
                                style = HuggTypography.p3_l,
                                color = Gs70
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(12.dp))
                    Text(
                        text = item.content,
                        style = HuggTypography.p2_l,
                        color = GsBlack
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    UrlToImage(url = item.imageUrl)
                }
            }
        }

        item { Spacer(modifier = Modifier.height(8.dp)) }

        if (item.reply.isNotEmpty()) {
            item {
                ReplyItem(reply = item.reply)
            }
        }
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
        Text(
            text = reply,
            style = HuggTypography.p2_l,
            color = GsBlack,
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp)
        )

        Text(
            text = String.format(REPLY_COUNT, reply.length),
            style = HuggTypography.p5,
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

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PreviewDailyHugg() {
    DailyHuggContent(
        uiState = DailyHuggPageState(
            dailyHugg = listOf(DailyHuggItemVo(
                id = 1,
                content = "asldnaqoprubgpqouehfl;aksndvuqenbrgpouqhgopinaf;lvjnasdjgbawqougrh[oqwivn;alskdvnawlirng[oirhg[pqwirjhgaskdnv;lawnvo",
                imageUrl = "https://discord.com/channels/1280552212116013116/1280552212116013119/1284190261890646058",
                reply = "anlkrjgnoaiwe"
            ))
        )
    )
}