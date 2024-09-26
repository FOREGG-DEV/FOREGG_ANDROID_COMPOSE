package com.hugg.mypage

import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.hugg.domain.model.enums.TopBarLeftType
import com.hugg.domain.model.enums.TopBarMiddleType
import com.hugg.domain.model.request.sign.SignUpMaleRequestVo
import com.hugg.feature.component.FilledBtn
import com.hugg.feature.component.SignUpIndicator
import com.hugg.feature.component.TopBar
import com.hugg.feature.theme.*
import com.hugg.feature.util.HuggToast
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

    LaunchedEffect(Unit){
        viewModel.getMyInfo()
    }

    MaleSignUpScreen(
        uiState = uiState,
        navigateGoToSpouse = navigateGoToSpouse,
        navigateGoToMyMedInj = navigateGoToMyMedInj,
        navigateGoToCs = navigateGoToCs,
        navigateGoToRegistration = navigateGoToRegistration
    )
}

@Composable
fun MaleSignUpScreen(
    uiState : MyPagePageState = MyPagePageState(),
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    navigateGoToSpouse : () -> Unit = {},
    navigateGoToMyMedInj : () -> Unit = {},
    navigateGoToCs : () -> Unit = {},
    navigateGoToRegistration : () -> Unit = {},
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
                        onClick = { navigateGoToSpouse() },
                        interactionSource = interactionSource
                    ),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = MY_PAGE_SPOUSE,
                    style = HuggTypography.p2,
                    color = Black
                )

                Spacer(modifier = Modifier.weight(1f))

                Text(
                    text = uiState.spouse.ifEmpty { MY_PAGE_REGISTER_SPOUSE },
                    style = HuggTypography.p3,
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
                    text = MY_PAGE_MY_MEDICINE_INJECTION,
                    style = HuggTypography.p2,
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
                        onClick = { },
                        interactionSource = interactionSource
                    ),
                contentAlignment = Alignment.CenterStart
            ) {
                Text(
                    text = MY_PAGE_NOTICE,
                    style = HuggTypography.p2,
                    color = Black,
                )
            }

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp)
                    .onThrottleClick(
                        onClick = { },
                        interactionSource = interactionSource
                    ),
                contentAlignment = Alignment.CenterStart
            ) {
                Text(
                    text = MY_PAGE_FAQ,
                    style = HuggTypography.p2,
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
                    style = HuggTypography.p2,
                    color = Black,
                )
            }

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp)
                    .onThrottleClick(
                        onClick = { },
                        interactionSource = interactionSource
                    ),
                contentAlignment = Alignment.CenterStart
            ) {
                Text(
                    text = MY_PAGE_TERMS_OF_SERVICE,
                    style = HuggTypography.p2,
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
                style = HuggTypography.p2,
                color = Black,
            )
        }
    }
}

@Preview
@Composable
internal fun PreviewMainContainer() {
    MaleSignUpScreen()
}