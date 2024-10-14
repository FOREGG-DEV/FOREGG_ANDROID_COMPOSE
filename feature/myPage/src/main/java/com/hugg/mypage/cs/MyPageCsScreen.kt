package com.hugg.mypage.cs

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.hugg.domain.model.enums.TopBarLeftType
import com.hugg.domain.model.enums.TopBarMiddleType
import com.hugg.feature.R
import com.hugg.feature.component.HuggSnackBar
import com.hugg.feature.component.HuggText
import com.hugg.feature.component.TopBar
import com.hugg.feature.theme.*
import com.hugg.feature.util.onThrottleClick

@Composable
fun MyPageCsContainer(
    goToBack: () -> Unit = {},
    viewModel: MyPageCsViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val context = LocalContext.current
    val clipboardManager : ClipboardManager = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager

    MyPageCsScreen(
        uiState = uiState,
        goToBack = goToBack,
        onClickPlusChannelBtn = { goToPlusChannel(context) },
        onClickCopyBtn = {
            val clip = ClipData.newPlainText("label", FOREGG_EMAIL)
            clipboardManager.setPrimaryClip(clip)
            viewModel.onClickCopyBtn()
        },
    )
}

@Composable
fun MyPageCsScreen(
    uiState : MyPageCsPageState = MyPageCsPageState(),
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    goToBack : () -> Unit = {},
    onClickPlusChannelBtn : () -> Unit = {},
    onClickCopyBtn: () -> Unit = {},
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Background),
    ) {

        TopBar(
            leftBtnClicked = goToBack,
            leftItemType = TopBarLeftType.BACK,
            middleItemType = TopBarMiddleType.TEXT,
            middleText = MY_PAGE_CS_ASK
        )

        Spacer(modifier = Modifier.size(24.dp))

        HuggText(
            modifier = Modifier.padding(start = 16.dp),
            text = MY_PAGE_CS_HUGG_KAKAO_PLUS,
            style = HuggTypography.h3,
            color = Gs90
        )

        Spacer(modifier = Modifier.size(8.dp))

        Image(
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .fillMaxWidth()
                .onThrottleClick(
                    onClick = onClickPlusChannelBtn,
                    interactionSource = interactionSource
                )
                .aspectRatio(343f / 52f),
            painter = painterResource(id = R.drawable.ic_kakao_plus_channel),
            contentDescription = null
        )

        Spacer(modifier = Modifier.size(24.dp))

        HuggText(
            modifier = Modifier.padding(start = 16.dp),
            text = MY_PAGE_CS_HUGG_EMAIL,
            style = HuggTypography.h3,
            color = Gs90
        )

        Spacer(modifier = Modifier.size(8.dp))

        Row(
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .fillMaxWidth()
                .height(48.dp)
                .background(color = White, shape = RoundedCornerShape(8.dp))
                .onThrottleClick(
                    onClick = onClickCopyBtn,
                    interactionSource = interactionSource,
                ),
            verticalAlignment = Alignment.CenterVertically
        ) {

            HuggText(
                modifier = Modifier.padding(start = 12.dp),
                color = Black,
                style = HuggTypography.p2,
                text = FOREGG_EMAIL
            )

            Spacer(modifier = Modifier.weight(1f))

            Box(
                modifier = Modifier
                    .width(48.dp)
                    .height(48.dp)
                    .background(
                        color = MainNormal,
                        shape = RoundedCornerShape(topEnd = 8.dp, bottomEnd = 8.dp)
                    ),
                contentAlignment = Alignment.Center
            ){
                Image(
                    painter = painterResource(id = R.drawable.ic_copy_white),
                    contentDescription = null
                )
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        AnimatedVisibility(
            modifier = Modifier.padding(horizontal = 16.dp),
            visible = uiState.isShowSnackBar,
            enter = fadeIn(),
            exit = fadeOut()
        ) {
            HuggSnackBar(text = COPY_COMPLETE_MAIL_TEXT)
        }
    }
}

fun goToPlusChannel(context: Context){
    val intent = Intent(Intent.ACTION_VIEW).apply {
        data = Uri.parse(MY_PAGE_KAKAO_CHANEL_LINK)
    }
    context.startActivity(intent)
}

@Preview
@Composable
internal fun PreviewMainContainer() {
    MyPageCsScreen()
}