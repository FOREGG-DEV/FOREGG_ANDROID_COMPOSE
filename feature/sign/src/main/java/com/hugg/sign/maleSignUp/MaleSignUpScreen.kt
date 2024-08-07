package com.hugg.sign.maleSignUp

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
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
import com.hugg.feature.component.HuggSnackBar
import com.hugg.feature.component.HuggToast
import com.hugg.feature.component.SignUpIndicator
import com.hugg.feature.component.TopBar
import com.hugg.feature.theme.*


@Composable
fun MaleSignUpContainer(
    navigateGoToHome : () -> Unit = {},
    accessToken : String,
    signUpMaleRequestVo : SignUpMaleRequestVo,
    goToBack : () -> Unit = {},
    viewModel: MaleSignUpViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.eventFlow.collect { event ->
            when(event) {
                MaleSignUpEvent.GoToMainEvent -> navigateGoToHome
            }
        }
    }

    MaleSignUpScreen(
        uiState = uiState,
        onClickTopBarLeftBtn = goToBack,
        onClickSignUpBtn = { viewModel.onClickSignUp(accessToken, signUpMaleRequestVo) },
        onChangedSpouseCode = { value -> viewModel.onChangedSpouseCode(value) }
    )
}

@Composable
fun MaleSignUpScreen(
    uiState : MaleSignUpPageState = MaleSignUpPageState(),
    onClickTopBarLeftBtn : () -> Unit = {},
    onClickSignUpBtn : () -> Unit = {},
    onChangedSpouseCode : (String) -> Unit = {}
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
            middleText = WORD_SIGN_UP
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

            Text(
                color = Gs80,
                style = HuggTypography.h1,
                text = SIGN_UP_MALE
            )

            Spacer(modifier = Modifier.height(24.dp))

            Box(modifier = Modifier
                .width(176.dp)
                .height(48.dp)
                .background(
                    color = White,
                    shape = RoundedCornerShape(8.dp)
                ),
                contentAlignment = Alignment.Center
            ){
                BasicTextField(
                    value = uiState.spouseCode,
                    onValueChange = { value ->
                        onChangedSpouseCode(value)
                    },
                    textStyle = HuggTypography.h3.copy(
                        color = Black,
                        textAlign = TextAlign.Center
                    ),
                    singleLine = true,
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                color = Gs90,
                style = HuggTypography.p2_l,
                text = SIGN_UP_SPOUSE_CODE_MALE_HINT
            )
        }

        Column(
            verticalArrangement = Arrangement.Bottom
        ) {
            FilledBtn(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                onClickBtn = onClickSignUpBtn,
                text = SIGN_UP_COMPLETE
            )
            Spacer(modifier = Modifier.height(80.dp))
        }
    }

    HuggToast(
        visible = uiState.isShowErrorSpouseCode,
        text = TOAST_ERROR_NOR_CORRECT_SPOUSE_CODE
    )
}

@Preview
@Composable
internal fun PreviewMainContainer() {
    MaleSignUpScreen()
}