package com.hugg.challenge.my

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.hugg.challenge.main.ChallengeMainPageState
import com.hugg.feature.R
import com.hugg.feature.component.HuggText
import com.hugg.feature.theme.Gs70
import com.hugg.feature.theme.HuggTypography
import com.hugg.feature.theme.MY_CHALLENGE_EMPTY

@Composable
fun MyChallenge(
    uiState: ChallengeMainPageState
) {
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
    }
}