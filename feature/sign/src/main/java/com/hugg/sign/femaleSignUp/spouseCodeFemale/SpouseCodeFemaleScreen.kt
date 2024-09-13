package com.hugg.sign.femaleSignUp.spouseCodeFemale

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import com.hugg.domain.model.request.sign.SignUpRequestVo
import com.hugg.feature.component.SignUpIndicator
import com.hugg.feature.component.TopBar
import com.hugg.feature.theme.*
import com.hugg.feature.R
import com.hugg.feature.component.FilledBtn
import com.hugg.feature.component.HuggSnackBar


@Composable
fun SpouseCodeFemaleContainer(
    navigateGoToHome : () -> Unit = {},
    accessToken : String,
    signUpRequestVo: SignUpRequestVo,
    goToBack : () -> Unit = {},
    viewModel: SpouseCodeFemaleViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val context = LocalContext.current
    val clipboardManager : ClipboardManager = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager

    LaunchedEffect(Unit) {
        viewModel.eventFlow.collect { event ->
            when(event) {
                SpouseCodeFemaleEvent.GoToMainEvent -> navigateGoToHome()
            }
        }
    }

    SpouseCodeFemaleScreen(
        uiState = uiState,
        onClickTopBarLeftBtn = goToBack,
        onClickSignUpBtn = { viewModel.onClickSignUp(accessToken, signUpRequestVo) },
        onClickCopyBtn = {
            val clip = ClipData.newPlainText("label", uiState.spouseCode)
            clipboardManager.setPrimaryClip(clip)
            viewModel.onClickCopyBtn()
        }
    )
}

@Composable
fun SpouseCodeFemaleScreen(
    uiState : SpouseCodeFemalePageState = SpouseCodeFemalePageState(),
    onClickTopBarLeftBtn : () -> Unit = {},
    onClickSignUpBtn : () -> Unit = {},
    onClickCopyBtn: () -> Unit = {},
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

            Text(
                color = Gs80,
                style = HuggTypography.h1,
                text = SIGN_UP_SPOUSE_CODE_FEMALE
            )

            Spacer(modifier = Modifier.height(24.dp))

            SpouseCodeCopyView(
                uiState = uiState,
                onClickCopyBtn = onClickCopyBtn,
                interactionSource = interactionSource
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                color = Gs90,
                style = HuggTypography.p2_l,
                text = SIGN_UP_SPOUSE_CODE_FEMALE_HINT
            )

            Spacer(modifier = Modifier.height(8.dp))

            AnimatedVisibility(
                visible = uiState.isShowSnackBar,
                enter = fadeIn(),
                exit = fadeOut()
            ) {
                HuggSnackBar(text = COPY_COMPLETE_TEXT)
            }
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
}

@Composable
fun SpouseCodeCopyView(
    uiState: SpouseCodeFemalePageState,
    onClickCopyBtn: () -> Unit,
    interactionSource : MutableInteractionSource
){
    Row(
        modifier = Modifier
            .height(48.dp)
            .background(color = White, shape = RoundedCornerShape(8.dp))
            .clickable(
                onClick = onClickCopyBtn,
                interactionSource = interactionSource,
                indication = null
            ),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(
            horizontalArrangement = Arrangement.Start
        ){
            Box(
                modifier = Modifier
                    .width(48.dp)
                    .height(48.dp)
                    .background(
                        color = MainNormal,
                        shape = RoundedCornerShape(topStart = 8.dp, bottomStart = 8.dp)
                    ),
                contentAlignment = Alignment.Center
            ){
                Image(
                    painter = painterResource(id = R.drawable.ic_calendar_white),
                    contentDescription = null
                )
            }
        }

        Spacer(modifier = Modifier.width(30.dp))

        Text(
            modifier = Modifier.padding(end = 39.dp),
            color = Black,
            style = HuggTypography.h3,
            text = uiState.spouseCode
        )
    }
}

@Preview
@Composable
internal fun PreviewMainContainer() {
    SpouseCodeFemaleScreen()
}