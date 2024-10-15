package com.hugg.dailyhugg.create.complete

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.rememberLottieComposition
import com.hugg.domain.model.enums.TopBarMiddleType
import com.hugg.feature.component.BlankBtn
import com.hugg.feature.component.HuggText
import com.hugg.feature.component.TopBar
import com.hugg.feature.theme.Background
import com.hugg.feature.theme.CONFIRM_DAILY_HUGG
import com.hugg.feature.theme.CREATION_SUCCESS_TITLE
import com.hugg.feature.theme.DAILY_HUGG
import com.hugg.feature.theme.GsBlack
import com.hugg.feature.theme.HuggTypography

@Composable
fun DailyHuggCreationSuccessScreen(
    goToDailyHuggMain: () -> Unit = {}
) {
    DailyHuggCreationSuccessContent(
        goToDailyHuggMain = goToDailyHuggMain,
    )
}

@Composable
fun DailyHuggCreationSuccessContent(
    goToDailyHuggMain: () -> Unit = {}
) {
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(com.hugg.feature.R.raw.daily_hugg_female))

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Background),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TopBar(
            middleText = DAILY_HUGG,
            middleItemType = TopBarMiddleType.TEXT
        )

        Spacer(modifier = Modifier.height(122.dp))

        HuggText(
            text = CREATION_SUCCESS_TITLE,
            style = HuggTypography.h1,
            color = GsBlack
        )

        LottieAnimation(
            composition = composition,
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1f / 1f)
        )

        Spacer(modifier = Modifier.weight(1f))

        BlankBtn(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            text = CONFIRM_DAILY_HUGG,
            onClickBtn = { goToDailyHuggMain() }
        )

        Spacer(modifier = Modifier.height(24.dp))
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PreviewDailyHuggCreationSuccess() {
    DailyHuggCreationSuccessContent()
}