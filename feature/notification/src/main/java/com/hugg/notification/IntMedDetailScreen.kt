package com.hugg.notification

import android.content.ActivityNotFoundException
import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.hugg.domain.model.enums.GenderType
import com.hugg.domain.model.enums.RecordType
import com.hugg.feature.R
import com.hugg.feature.component.BlankBtn
import com.hugg.feature.component.FilledBtn
import com.hugg.feature.component.HuggText
import com.hugg.feature.theme.*
import com.hugg.feature.util.HuggToast
import com.hugg.feature.util.UserInfo
import com.kakao.sdk.common.util.KakaoCustomTabsClient
import com.kakao.sdk.share.ShareClient
import com.kakao.sdk.share.WebSharerClient
import com.kakao.sdk.template.model.Button
import com.kakao.sdk.template.model.Content
import com.kakao.sdk.template.model.FeedTemplate
import com.kakao.sdk.template.model.Link

@Composable
fun InjMedDetailContainer(
    goToBack: () -> Unit = {},
    type : RecordType = RecordType.ETC,
    time : String,
    id : Long,
    viewModel: InjMedDetailViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val context = LocalContext.current

    InjMedDetailScreen(
        uiState = uiState,
        goToBack = goToBack,
        onClickShareBtn = { viewModel.onClickShare(id, time) },
        context = context
    )

    LaunchedEffect(Unit){
        viewModel.initView(time, id, type)
    }

    LaunchedEffect(Unit) {
        viewModel.eventFlow.collect { event ->
            when(event) {
                InjMedDetailEvent.SuccessShareToast -> {
                    HuggToast.createToast(context, SUCCESS_SHARE_COMMENT, false, Toast.LENGTH_SHORT).show()
                }
                InjMedDetailEvent.ErrorShareToast -> {
                    shareWithKakao(context)
                }
            }
        }
    }
}

@Composable
fun InjMedDetailScreen(
    uiState : InjMedDetailPageState = InjMedDetailPageState(),
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    goToBack : () -> Unit = {},
    onClickShareBtn : () -> Unit = {},
    context : Context
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Background),
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {

            HuggText(
                text = if(uiState.pageType == RecordType.MEDICINE) WORD_MEDICINE else WORD_INJECTION,
                style = HuggTypography.h2,
                color = Black
            )
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(1.dp)
                .background(Gs20)
        )

        Spacer(modifier = Modifier.size(20.dp))

        HuggText(
            text = uiState.name,
            style = HuggTypography.h1,
            color = MainStrong
        )

        Spacer(modifier = Modifier.size(4.dp))

        val guideText = when{
            (UserInfo.info.genderType == GenderType.FEMALE) -> NOTIFICATION_GUIDE_FEMALE
            (UserInfo.info.genderType == GenderType.MALE && uiState.pageType == RecordType.INJECTION) -> String.format(NOTIFICATION_GUIDE_MALE_INJECTION, UserInfo.info.spouse)
            (UserInfo.info.genderType == GenderType.MALE && uiState.pageType == RecordType.MEDICINE) -> String.format(NOTIFICATION_GUIDE_MALE_MEDICINE, UserInfo.info.spouse)
            else -> ""
        }
        HuggText(
            text = guideText,
            style = HuggTypography.h1,
            color = Black
        )

        Spacer(modifier = Modifier.size(10.dp))

        AsyncImage(
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .clip(RoundedCornerShape(8.dp))
                .fillMaxWidth()
                .aspectRatio(343f / 200f),
            model = ImageRequest.Builder(context)
                .data(uiState.image)
                .crossfade(true)
                .build(),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            placeholder = painterResource(id = if(uiState.pageType == RecordType.MEDICINE) R.drawable.ic_empty_medicine else R.drawable.ic_empty_injection)
        )

        Spacer(modifier = Modifier.size(8.dp))

        Column(
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .shadow(elevation = 5.dp, shape = RoundedCornerShape(8.dp))
                .clip(RoundedCornerShape(8.dp))
                .background(color = White)
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 15.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                HuggText(
                    text = if(uiState.pageType == RecordType.MEDICINE) NOTIFICATION_MEDICINE_DATE else NOTIFICATION_INJECTION_DATE,
                    style = HuggTypography.p2,
                    color = Gs70
                )

                Spacer(modifier = Modifier.weight(1f))

                HuggText(
                    text = uiState.date,
                    style = HuggTypography.p2,
                    color = Black
                )
            }

            Spacer(modifier = Modifier.size(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                HuggText(
                    text = if(uiState.pageType == RecordType.MEDICINE) NOTIFICATION_MEDICINE_TIME else NOTIFICATION_INJECTION_TIME,
                    style = HuggTypography.p2,
                    color = Gs70
                )

                Spacer(modifier = Modifier.weight(1f))

                HuggText(
                    text = uiState.time,
                    style = HuggTypography.p2,
                    color = Black
                )
            }

            Spacer(modifier = Modifier.size(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                HuggText(
                    text = if(uiState.pageType == RecordType.MEDICINE) NOTIFICATION_MEDICINE_NAME else NOTIFICATION_INJECTION_NAME,
                    style = HuggTypography.p2,
                    color = Gs70
                )

                Spacer(modifier = Modifier.weight(1f))

                HuggText(
                    text = uiState.name,
                    style = HuggTypography.p2,
                    color = Black
                )
            }

            Spacer(modifier = Modifier.size(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                HuggText(
                    text = if(uiState.pageType == RecordType.MEDICINE) NOTIFICATION_MEDICINE_EXPLAIN else NOTIFICATION_INJECTION_EXPLAIN,
                    style = HuggTypography.p2,
                    color = Gs70
                )

                Spacer(modifier = Modifier.weight(1f))

                HuggText(
                    text = uiState.date,
                    style = HuggTypography.p2,
                    color = Black
                )
            }
        }

        if(UserInfo.info.genderType == GenderType.FEMALE){
            Spacer(modifier = Modifier.size(24.dp))

            HuggText(
                text = if(uiState.pageType == RecordType.MEDICINE) NOTIFICATION_MEDICINE_SHARE else NOTIFICATION_INJECTION_SHARE,
                style = HuggTypography.p1_l,
                color = Black
            )

            Spacer(modifier = Modifier.size(8.dp))

            FilledBtn(
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .fillMaxWidth(),
                onClickBtn = onClickShareBtn,
                text = NOTIFICATION_SHARE_BTN
            )

            Spacer(modifier = Modifier.size(8.dp))
        }
        else{
            Spacer(modifier = Modifier.size(118.dp))
        }

        BlankBtn(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            onClickBtn = goToBack,
            text = WORD_HOME,
        )
    }
}

private fun shareWithKakao(context: Context){
    val feed = getFeed()
    if (ShareClient.instance.isKakaoTalkSharingAvailable(context)) {
        ShareClient.instance.shareDefault(context, feed) { sharingResult, error ->
            if (error != null) {
                HuggToast.createToast(context, ERROR_KAKAO_SHARE, true, Toast.LENGTH_SHORT).show()
            }
            else if (sharingResult != null) {
                context.startActivity(sharingResult.intent)
            }
        }
    } else {
        val sharerUrl = WebSharerClient.instance.makeDefaultUrl(feed)
        try {
            KakaoCustomTabsClient.openWithDefault(context, sharerUrl)
        } catch(e: UnsupportedOperationException) {
            HuggToast.createToast(context, ERROR_NEED_WEB, true, Toast.LENGTH_SHORT).show()
        }
        try {
            KakaoCustomTabsClient.open(context, sharerUrl)
        } catch (e: ActivityNotFoundException) {
            HuggToast.createToast(context, ERROR_NEED_WEB, true, Toast.LENGTH_SHORT).show()
        }
    }
}

private fun getFeed() : FeedTemplate {
    return FeedTemplate(
        content = Content(
            title = KAKAO_SHARE_TITLE,
            description = KAKAO_SHARE_CONTENT,
            imageUrl = KAKAO_SHARE_IMAGE,
            link = Link(
                webUrl = KAKAO_SHARE_URL,
                mobileWebUrl = KAKAO_SHARE_URL,
            )
        ),
        buttons = listOf(
            Button(
                KAKAO_SHARE_BUTTON,
                Link(
                    webUrl = KAKAO_SHARE_URL,
                    mobileWebUrl = KAKAO_SHARE_URL,
                )
            )
        )
    )
}

@Preview
@Composable
internal fun PreviewMainContainer() {
    InjMedDetailScreen(context = LocalContext.current)
}