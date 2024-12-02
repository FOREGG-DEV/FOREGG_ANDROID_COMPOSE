package com.hugg.support

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.hugg.domain.model.enums.CheerType
import com.hugg.domain.model.enums.TopBarLeftType
import com.hugg.domain.model.enums.TopBarMiddleType
import com.hugg.domain.model.enums.TopBarRightType
import com.hugg.domain.model.response.challenge.ChallengeSupportItemVo
import com.hugg.feature.R
import com.hugg.feature.component.HuggText
import com.hugg.feature.component.TopBar
import com.hugg.feature.theme.Background
import com.hugg.feature.theme.CHALLENGE_POINT
import com.hugg.feature.theme.CHALLENGE_SUPPORT_TOP_BAR
import com.hugg.feature.theme.Gs80
import com.hugg.feature.theme.Gs90
import com.hugg.feature.theme.GsWhite
import com.hugg.feature.theme.HuggTypography
import com.hugg.feature.theme.WORD_SHOW_MORE
import com.hugg.feature.util.UserInfo

@Composable
fun ChallengeSupportScreen(
    popScreen: () -> Unit
) {
    val viewModel: ChallengeSupportViewModel = hiltViewModel()
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    ChallengeSupportContent(
        uiState = uiState,
        popScreen = popScreen
    )
}

@Composable
fun ChallengeSupportContent(
    uiState: ChallengeSupportPageState = ChallengeSupportPageState(),
    popScreen: () -> Unit = {}
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Background)
    ) {
        TopBar(
            leftItemType = TopBarLeftType.BACK,
            leftBtnClicked = { popScreen() },
            middleItemType = TopBarMiddleType.TEXT,
            middleText = CHALLENGE_SUPPORT_TOP_BAR,
            rightItemType = TopBarRightType.POINT,
            rightItemContent = String.format(CHALLENGE_POINT, UserInfo.challengePoint)
        )

        ChallengeList(items = uiState.completedList, cheerType = CheerType.CLAP)

        Spacer(modifier = Modifier.height(16.dp))

        ChallengeList(items = uiState.incompleteList, cheerType = CheerType.SUPPORT)
    }
}

@Composable
fun ChallengeList(
    items: List<ChallengeSupportItemVo>,
    cheerType: CheerType
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        items.forEachIndexed { index, item ->
            ChallengeItem(item = item, cheerType = cheerType)
            if(index != items.size - 1) Spacer(modifier = Modifier.height(8.dp))
        }

        Spacer(modifier = Modifier.height(16.dp))
        
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            HuggText(
                text = WORD_SHOW_MORE,
                style = HuggTypography.p2,
                color = Gs80
            )
            Spacer(modifier = Modifier.width(4.dp))
            Icon(
                painter = painterResource(id = R.drawable.ic_bottom_arrow_gs_80),
                contentDescription = null,
                tint = Color.Unspecified,
                modifier = Modifier.size(14.dp)
            )
        }
    }
}

@Composable
fun ChallengeItem(
    item: ChallengeSupportItemVo,
    cheerType: CheerType
) {
    Row(
        modifier = Modifier
            .fillMaxSize()
            .clip(RoundedCornerShape(6.dp))
            .background(GsWhite),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Spacer(modifier = Modifier.width(12.dp))
        Icon(
            painter = painterResource(id = if(item.success) R.drawable.ic_check_circle_main else R.drawable.ic_check_circle_gs_30),
            contentDescription = null,
            tint = Color.Unspecified,
            modifier = Modifier.size(32.dp)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Column(
            modifier = Modifier
                .padding(vertical = 12.dp),
            horizontalAlignment = Alignment.Start
        ) {
            HuggText(
                text = item.challengeNickname,
                style = HuggTypography.h3,
                color = Gs90
            )
            Spacer(modifier = Modifier.height(4.dp))
            HuggText(
                text = item.thought,
                style = HuggTypography.p4,
                color = Gs80
            )
        }
        Spacer(modifier = Modifier.weight(1f))
        if (cheerType == CheerType.CLAP) {
            Icon(
                painter = painterResource(id = if (item.clap) R.drawable.ic_clap_enable else R.drawable.ic_clap_disable),
                contentDescription = null,
                tint = Color.Unspecified,
                modifier = Modifier.size(36.dp)
            )
        } else if (cheerType == CheerType.SUPPORT) {
            Icon(
                painter = painterResource(id = if (item.support) R.drawable.ic_support_enable else R.drawable.ic_support_disable),
                contentDescription = null,
                tint = Color.Unspecified,
                modifier = Modifier.size(36.dp)
            )
        }
        Spacer(modifier = Modifier.width(16.dp))
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PreviewChallengeItem() {
    ChallengeItem(
        item = ChallengeSupportItemVo(
            id = 1,
            challengeNickname = "askl;jrgnopqairhgo",
            thought = "ad;lkjgnoqpwaiue",
            success = true,
            clap = true,
            support = true
        ),
        cheerType = CheerType.CLAP
    )
}