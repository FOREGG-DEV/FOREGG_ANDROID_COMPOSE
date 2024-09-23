package com.example.dailyhugg.create.complete

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.InteractionSource
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.hugg.domain.model.enums.TopBarMiddleType
import com.hugg.feature.component.BlankBtn
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
    val interactionSource = remember { MutableInteractionSource() }

    DailyHuggCreationSuccessContent(
        goToDailyHuggMain = goToDailyHuggMain,
        interactionSource = interactionSource
    )
}

@Composable
fun DailyHuggCreationSuccessContent(
    goToDailyHuggMain: () -> Unit = {},
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() }
) {
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

        Text(
            text = CREATION_SUCCESS_TITLE,
            style = HuggTypography.h1,
            color = GsBlack
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