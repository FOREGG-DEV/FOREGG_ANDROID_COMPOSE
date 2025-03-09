package com.hugg.sign.femaleSignUp.batteryModal

import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.hugg.domain.model.enums.TopBarLeftType
import com.hugg.domain.model.enums.TopBarMiddleType
import com.hugg.feature.component.SignUpIndicator
import com.hugg.feature.component.TopBar
import com.hugg.feature.theme.*
import com.hugg.feature.component.FilledBtn
import com.hugg.feature.component.HuggText
import com.hugg.feature.util.openBatteryOptimizationSettings


@Composable
fun BatteryOptimizationContainer(
    navigateGoToHome : () -> Unit = {},
    goToBack : () -> Unit = {},
) {
    val context = LocalContext.current

    BatteryOptimizationScreen(
        onClickTopBarLeftBtn = goToBack,
        onClickNextBtn = {
            navigateGoToHome()
            openBatteryOptimizationSettings(context)
        }
    )
}

@Composable
fun BatteryOptimizationScreen(
    onClickTopBarLeftBtn : () -> Unit = {},
    onClickNextBtn : () -> Unit = {},
    interactionSource : MutableInteractionSource = remember { MutableInteractionSource() }
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Background),
    ) {

        TopBar(
            leftItemType = TopBarLeftType.BACK,
            leftBtnClicked = onClickTopBarLeftBtn,
            middleItemType = TopBarMiddleType.TEXT,
            middleText = WORD_SIGN_UP,
            interactionSource = interactionSource
        )

        Column(
            modifier = Modifier
                .weight(1f)
                .padding(horizontal = 16.dp)
        ) {

            Spacer(modifier = Modifier.height(38.dp))

            SignUpIndicator(
                nowPage = 3
            )

            Spacer(modifier = Modifier.height(16.dp))

            HuggText(
                color = Gs80,
                style = HuggTypography.h1,
                text = SIGN_UP_BATTERY_OPTIMIZATION_TITLE
            )

            Spacer(modifier = Modifier.height(24.dp))

            HuggText(
                color = Gs90,
                style = HuggTypography.p2_l,
                text = SIGN_UP_BATTERY_OPTIMIZATION_CONTENT
            )
        }

        Column(
            verticalArrangement = Arrangement.Bottom
        ) {
            FilledBtn(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                onClickBtn = onClickNextBtn,
                text = WORD_NEXT
            )
            Spacer(modifier = Modifier.height(80.dp))
        }
    }
}

@Preview
@Composable
internal fun PreviewMainContainer() {
    BatteryOptimizationScreen()
}