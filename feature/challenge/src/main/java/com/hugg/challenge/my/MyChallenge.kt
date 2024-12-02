package com.hugg.challenge.my

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.hugg.challenge.main.ChallengeMainPageState
import com.hugg.feature.R
import com.hugg.feature.component.HuggText
import com.hugg.feature.theme.CHALLENGE_SUPPORT_GUIDE
import com.hugg.feature.theme.Gs70
import com.hugg.feature.theme.GsWhite
import com.hugg.feature.theme.HuggTypography
import com.hugg.feature.theme.MY_CHALLENGE_EMPTY
import com.hugg.feature.theme.MainNormal

@Composable
fun MyChallenge(
    uiState: ChallengeMainPageState,
    goToChallengeSupport: () -> Unit
) {
    val interactionSource: MutableInteractionSource = remember { MutableInteractionSource() }

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

            Spacer(modifier = Modifier.weight(1f))

            ChallengeSupportGuideItem(
                goToChallengeSupport = goToChallengeSupport,
                interactionSource = interactionSource
            )
        }
    }
}

@Composable
fun ChallengeSupportGuideItem(
    goToChallengeSupport: () -> Unit,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() }
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .clip(RoundedCornerShape(8.dp))
            .background(MainNormal)
            .aspectRatio(343f / 90f)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp, vertical = 11.dp)
                .clickable(
                    interactionSource = interactionSource,
                    indication = null,
                    onClick = { goToChallengeSupport() }
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