package com.hugg.feature.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.compose.ui.window.DialogWindowProvider
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.hugg.domain.base.StatusCode
import com.hugg.domain.model.enums.DialogType
import com.hugg.feature.R
import com.hugg.feature.theme.Black
import com.hugg.feature.theme.CHALLENGE_COMPLETE
import com.hugg.feature.theme.CHALLENGE_GET_POINT
import com.hugg.feature.theme.DIALOG_MAX_LENGTH
import com.hugg.feature.theme.ERROR_DIALOG_ASK_FOR_ADMIN_CONTENT
import com.hugg.feature.theme.ERROR_DIALOG_ASK_FOR_ADMIN_TITLE
import com.hugg.feature.theme.ERROR_DIALOG_INTERNET_CONTENT
import com.hugg.feature.theme.ERROR_DIALOG_INTERNET_TITLE
import com.hugg.feature.theme.Gs10
import com.hugg.feature.theme.Gs30
import com.hugg.feature.theme.Gs50
import com.hugg.feature.theme.Gs70
import com.hugg.feature.theme.Gs80
import com.hugg.feature.theme.HuggTypography
import com.hugg.feature.theme.Gs90
import com.hugg.feature.theme.MainNormal
import com.hugg.feature.theme.Sunday
import com.hugg.feature.theme.WORD_CONFIRM
import com.hugg.feature.theme.WORD_NO
import com.hugg.feature.theme.WORD_RETRY
import com.hugg.feature.theme.WORD_YES
import com.hugg.feature.theme.White

@Composable
fun HuggDialog(
    title : String = "",
    dialogType : DialogType = DialogType.DOUBLE,
    negativeColor : Color = Gs10,
    positiveColor : Color = MainNormal,
    negativeText : String = WORD_NO,
    positiveText : String = WORD_YES,
    onClickNegative : () -> Unit = {},
    onClickPositive : () -> Unit = {},
    onClickCancel : () -> Unit = {},
    hasWarningText : Boolean = false,
    warningMessage : String = "",
){
    Dialog(
        onDismissRequest = onClickCancel,
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ){
        Column(
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .fillMaxWidth()
                .height(164.dp)
                .background(color = White, shape = RoundedCornerShape(12.dp))
        ) {
            Spacer(modifier = Modifier.size(if(hasWarningText) 24.dp else 40.dp))

            HuggText(
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .fillMaxWidth(),
                maxLines = 1,
                text = title,
                style = HuggTypography.h2,
                color = Black,
                textAlign = TextAlign.Center
            )

            if(hasWarningText) {
                Spacer(modifier = Modifier.size(2.dp))

                HuggText(
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                        .fillMaxWidth(),
                    maxLines = 1,
                    text = warningMessage,
                    style = HuggTypography.p1_l,
                    color = Sunday,
                    textAlign = TextAlign.Center
                )
            }

            Spacer(modifier = Modifier.weight(1f))

            Row(
                modifier = Modifier.padding(horizontal = 16.dp)
            ) {
                if(dialogType == DialogType.DOUBLE) {
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .height(48.dp)
                            .background(color = negativeColor, shape = RoundedCornerShape(4.dp))
                            .clickable(
                                onClick = onClickNegative,
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        HuggText(
                            text = negativeText,
                            style = HuggTypography.h2,
                            color = Gs80
                        )
                    }

                    Spacer(modifier = Modifier.size(8.dp))
                }

                Box(
                    modifier = Modifier
                        .weight(1f)
                        .height(48.dp)
                        .background(color = positiveColor, shape = RoundedCornerShape(4.dp))
                        .clickable(
                            onClick = {
                                onClickCancel()
                                onClickPositive()
                            },
                        ),
                    contentAlignment = Alignment.Center
                ){
                    HuggText(
                        text = positiveText,
                        style = HuggTypography.h2,
                        color = White
                    )
                }
            }

            Spacer(modifier = Modifier.size(16.dp))
        }
    }
}

@Composable
fun HuggErrorDialog(
    errorCode : String = "",
    onClickPositive : () -> Unit = {},
    onDismiss: () -> Unit,
){
    val title = when(errorCode){
        StatusCode.ERROR -> ERROR_DIALOG_INTERNET_TITLE
        StatusCode.NETWORK_ERROR -> ERROR_DIALOG_ASK_FOR_ADMIN_TITLE
        StatusCode.ERROR_404 -> ERROR_DIALOG_INTERNET_TITLE
        else -> ERROR_DIALOG_ASK_FOR_ADMIN_TITLE
    }

    val message = when(errorCode){
        StatusCode.ERROR -> ERROR_DIALOG_INTERNET_CONTENT
        StatusCode.NETWORK_ERROR -> ERROR_DIALOG_ASK_FOR_ADMIN_CONTENT
        StatusCode.ERROR_404 -> ERROR_DIALOG_INTERNET_CONTENT
        else -> ERROR_DIALOG_ASK_FOR_ADMIN_CONTENT
    }

    val buttonText = when(errorCode){
        StatusCode.ERROR -> WORD_RETRY
        StatusCode.NETWORK_ERROR -> WORD_CONFIRM
        StatusCode.ERROR_404 -> WORD_RETRY
        else -> WORD_CONFIRM
    }

    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ){
        Column(
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .fillMaxWidth()
                .background(color = White, shape = RoundedCornerShape(12.dp))
        ) {
            Spacer(modifier = Modifier.size(24.dp))

            HuggText(
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .fillMaxWidth(),
                maxLines = 1,
                text = title,
                style = HuggTypography.h2,
                color = Black,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.size(2.dp))

            HuggText(
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .fillMaxWidth(),
                text = message,
                style = HuggTypography.p1_l,
                color = Gs50,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.size(22.dp))

            Box(
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .fillMaxWidth()
                    .height(48.dp)
                    .background(color = MainNormal, shape = RoundedCornerShape(4.dp))
                    .clickable(
                        onClick = {
                            onDismiss()
                            onClickPositive()
                        },
                    ),
                contentAlignment = Alignment.Center
            ){
                HuggText(
                    text = buttonText,
                    style = HuggTypography.h2,
                    color = White
                )
            }

            Spacer(modifier = Modifier.size(16.dp))
        }
    }
}

@Composable
fun HuggInputDialog(
    title : String = "",
    maxLength : Int = 1,
    positiveText: String = "",
    onClickPositive: (String) -> Unit = {},
    onClickCancel : () -> Unit = {}
){
    var inputContent by remember { mutableStateOf("") }

    Dialog(
        onDismissRequest = onClickCancel,
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ){
        Column(
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .fillMaxWidth()
                .background(color = White, shape = RoundedCornerShape(12.dp))
                .padding(top = 24.dp, bottom = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            HuggText(
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .fillMaxWidth(),
                maxLines = 1,
                text = title,
                style = HuggTypography.h2,
                color = Black,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.size(8.dp))

            HuggTextField(
                value = inputContent,
                onValueChange = { value ->
                    if (value.length <= maxLength) {
                        inputContent = value
                    }
                },
                textStyle = HuggTypography.p1_l.copy(
                    textAlign = TextAlign.Center,
                    color = Gs90,
                ),
                singleLine = true,
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .fillMaxWidth()
                    .border(width = 1.dp, color = Gs30, shape = RoundedCornerShape(4.dp))
                    .padding(vertical = 12.dp),
            )

            Spacer(modifier = Modifier.size(5.dp))

            HuggText(
                text = String.format(DIALOG_MAX_LENGTH, maxLength),
                style = HuggTypography.p3_l,
                color = Gs70
            )

            Spacer(modifier = Modifier.size(16.dp))

            FilledBtn(
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .fillMaxWidth(),
                contentPadding = PaddingValues(vertical = 12.dp),
                isActive = inputContent.isNotEmpty(),
                text = positiveText,
                onClickBtn = {
                    onClickPositive(inputContent)
                }
            )
        }
    }
}

@Composable
fun ChallengeCompleteDialog(
    onClickCancel : () -> Unit = {},
    points : Int = 0,
){
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.challenge_complete))
    val progress by animateLottieCompositionAsState(
        composition,
        iterations = 1
    )

    LaunchedEffect(progress) {
        if (progress == 1f) {
            onClickCancel()
        }
    }

    Dialog(
        onDismissRequest = onClickCancel,
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ){
        Column(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            HuggText(
                text = CHALLENGE_COMPLETE,
                color = White,
                style = HuggTypography.challenge
            )

            Spacer(modifier = Modifier.size(8.dp))

            LottieAnimation(
                composition = composition,
                progress = progress,
                modifier = Modifier.size(258.dp)
            )

            Spacer(modifier = Modifier.size(8.dp))

            HuggText(
                text = CHALLENGE_GET_POINT(points),
                color = White,
                style = HuggTypography.challenge
            )
        }
    }
}

@Composable
fun LoadingDialog(
    onDismiss: () -> Unit
) {
    Dialog(
        onDismissRequest = { onDismiss() },
        properties = DialogProperties(
            dismissOnBackPress = false,
            dismissOnClickOutside = false,
            usePlatformDefaultWidth = false
        )
    ) {
        (LocalView.current.parent as DialogWindowProvider)?.window?.setDimAmount(0f)
        Box(
            modifier = Modifier
                .background(Color.Transparent),
            contentAlignment = Alignment.Center
        ) {
            val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.loading))
            LottieAnimation(
                composition = composition,
                iterations = LottieConstants.IterateForever,
                modifier = Modifier.size(150.dp)
            )
        }
    }
}