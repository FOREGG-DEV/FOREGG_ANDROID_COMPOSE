package com.hugg.mypage.main

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat.startActivity
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.hugg.domain.model.enums.GenderType
import com.hugg.domain.model.enums.TopBarMiddleType
import com.hugg.feature.component.TopBar
import com.hugg.feature.theme.*
import com.hugg.feature.util.UserInfo
import com.hugg.feature.util.onThrottleClick


@Composable
fun MyPageContainer(
    navigateGoToSpouse : () -> Unit = {},
    navigateGoToMyMedInj : () -> Unit = {},
    navigateGoToCs : () -> Unit = {},
    navigateGoToRegistration : () -> Unit = {},
    viewModel: MyPageViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val context = LocalContext.current
    val interactionSource = remember { MutableInteractionSource() }

    LaunchedEffect(Unit){
        viewModel.getMyInfo()
    }

    MyPageScreen(
        uiState = uiState,
        navigateGoToSpouse = navigateGoToSpouse,
        interactionSource = interactionSource,
        navigateGoToMyMedInj = navigateGoToMyMedInj,
        navigateGoToCs = navigateGoToCs,
        navigateGoToRegistration = navigateGoToRegistration,
        onClickNotice = { goToWebLink(context, MY_PAGE_NOTICE_LINK) },
        onClickFaq = { goToWebLink(context, MY_PAGE_FAQ_LINK) },
        onClickTermsOfService = { goToWebLink(context, MY_PAGE_TERMS_OF_SERVICE_LINK) }
    )
}

@Composable
fun MyPageScreen(
    uiState : MyPagePageState = MyPagePageState(),
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    navigateGoToSpouse : () -> Unit = {},
    navigateGoToMyMedInj : () -> Unit = {},
    navigateGoToCs : () -> Unit = {},
    navigateGoToRegistration : () -> Unit = {},
    onClickNotice : () -> Unit = {},
    onClickFaq : () -> Unit = {},
    onClickTermsOfService : () -> Unit = {},
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Background),
    ) {

        TopBar(
            middleItemType = TopBarMiddleType.TEXT,
            middleText = WORD_MY_PAGE
        )

        Spacer(modifier = Modifier.size(24.dp))

        Column(
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .fillMaxWidth()
                .background(color = White, shape = RoundedCornerShape(8.dp))
                .padding(horizontal = 12.dp)
        ) {

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp)
                    .onThrottleClick(
                        onClick = {
                            if (UserInfo.info.genderType == GenderType.MALE) return@onThrottleClick
                            navigateGoToSpouse()
                        },
                        interactionSource = interactionSource
                    ),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = MY_PAGE_SPOUSE,
                    style = p2(),
                    color = Black
                )

                Spacer(modifier = Modifier.weight(1f))

                Text(
                    text = uiState.spouse.ifEmpty { MY_PAGE_REGISTER_SPOUSE },
                    style = p3(),
                    color = Gs50
                )
            }

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp)
                    .onThrottleClick(
                        onClick = { navigateGoToMyMedInj() },
                        interactionSource = interactionSource
                    ),
                contentAlignment = Alignment.CenterStart
            ) {
                Text(
                    text = if(UserInfo.info.genderType == GenderType.FEMALE) MY_PAGE_MY_MEDICINE_INJECTION else MY_PAGE_SPOUSE_MEDICINE_INJECTION(UserInfo.info.spouse),
                    style = p2(),
                    color = Black,
                )
            }
        }

        Spacer(modifier = Modifier.size(8.dp))

        Column(
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .fillMaxWidth()
                .background(color = White, shape = RoundedCornerShape(8.dp))
                .padding(horizontal = 12.dp)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp)
                    .onThrottleClick(
                        onClick = onClickNotice,
                        interactionSource = interactionSource
                    ),
                contentAlignment = Alignment.CenterStart
            ) {
                Text(
                    text = MY_PAGE_NOTICE,
                    style = p2(),
                    color = Black,
                )
            }

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp)
                    .onThrottleClick(
                        onClick = onClickFaq,
                        interactionSource = interactionSource
                    ),
                contentAlignment = Alignment.CenterStart
            ) {
                Text(
                    text = MY_PAGE_FAQ,
                    style = p2(),
                    color = Black,
                )
            }

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp)
                    .onThrottleClick(
                        onClick = { navigateGoToCs() },
                        interactionSource = interactionSource
                    ),
                contentAlignment = Alignment.CenterStart
            ) {
                Text(
                    text = MY_PAGE_CS_ASK,
                    style = p2(),
                    color = Black,
                )
            }

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp)
                    .onThrottleClick(
                        onClick = onClickTermsOfService,
                        interactionSource = interactionSource
                    ),
                contentAlignment = Alignment.CenterStart
            ) {
                Text(
                    text = MY_PAGE_TERMS_OF_SERVICE,
                    style = p2(),
                    color = Black,
                )
            }
        }

        Spacer(modifier = Modifier.size(8.dp))

        Box(
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .background(color = White, shape = RoundedCornerShape(8.dp))
                .fillMaxWidth()
                .height(48.dp)
                .padding(horizontal = 12.dp)
                .onThrottleClick(
                    onClick = { navigateGoToRegistration() },
                    interactionSource = interactionSource
                ),
            contentAlignment = Alignment.CenterStart
        ) {
            Text(
                text = MY_PAGE_PROFILE_MANAGEMENT,
                style = p2(),
                color = Black,
            )
        }
    }
}

private fun goToWebLink(context: Context, url : String){
    val intent = Intent(Intent.ACTION_VIEW).apply {
        data = Uri.parse(url)
    }
    context.startActivity(intent)
}

@Preview
@Composable
internal fun PreviewMainContainer() {
    MyPageScreen()
}