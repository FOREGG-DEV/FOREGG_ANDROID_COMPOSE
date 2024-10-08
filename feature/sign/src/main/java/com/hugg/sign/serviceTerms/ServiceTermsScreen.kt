package com.hugg.sign.serviceTerms

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.hugg.domain.model.enums.TopBarLeftType
import com.hugg.domain.model.enums.TopBarMiddleType
import com.hugg.feature.component.BlankBtn
import com.hugg.feature.component.SignUpIndicator
import com.hugg.feature.component.TopBar
import com.hugg.feature.theme.*
import com.hugg.feature.R
import com.hugg.feature.util.onThrottleClick


@Composable
fun ServiceTermsContainer(
    navigateAccessPermission : () -> Unit = {},
    goToBack : () -> Unit = {},
    viewModel: ServiceTermsViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val context = LocalContext.current

    ServiceTermsScreen(
        uiState = uiState,
        onClickTopBarLeftBtn = goToBack,
        onClickNextPageBtn = navigateAccessPermission,
        onClickAllAgreeBtn = { viewModel.updateAllTerm() },
        onClickServiceTermAgreeBtn = { viewModel.updateServiceTerm() },
        onClickPersonalTermAgreeBtn = { viewModel.updatePersonalTerm() },
        onClickAgeTermAgreeBtn = { viewModel.updateAgeTerm() },
        onClickServiceTermDetail = { goToWebLink(context, MY_PAGE_TERMS_OF_SERVICE_LINK) },
        onClickPersonalTermDetail = { goToWebLink(context, TERMS_OF_PERSONAL_LINK) }
    )
}

@Composable
fun ServiceTermsScreen(
    uiState : ServiceTermsPageState = ServiceTermsPageState(),
    onClickTopBarLeftBtn : () -> Unit = {},
    onClickAllAgreeBtn : () -> Unit = {},
    onClickServiceTermAgreeBtn : () -> Unit = {},
    onClickPersonalTermAgreeBtn : () -> Unit = {},
    onClickAgeTermAgreeBtn : () -> Unit = {},
    onClickServiceTermDetail : () -> Unit = {},
    onClickPersonalTermDetail : () -> Unit = {},
    onClickNextPageBtn : () -> Unit = {},
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
                nowPage = 1
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                color = Gs80,
                style = h1(),
                text = SIGN_UP_SERVICE_TERMS_TITLE
            )

            Spacer(modifier = Modifier.height(24.dp))


            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .border(width = 1.dp, color = MainNormal, shape = RoundedCornerShape(8.dp))
                    .background(color = White)
                    .padding(horizontal = 12.dp, vertical = 13.dp)
                    .clickable(
                        onClick = onClickAllAgreeBtn,
                        interactionSource = interactionSource,
                        indication = null
                    ),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(id = if(uiState.isAllTermChecked) R.drawable.ic_checked_box_black else R.drawable.ic_unchecked_box_black),
                    contentDescription = null
                )

                Spacer(modifier = Modifier.size(8.dp))

                Text(
                    text = SIGN_UP_SERVICE_TERMS_ALL_AGREE,
                    style = h3(),
                    color = Black
                )
            }

            Spacer(modifier = Modifier.size(24.dp))

            TermTextRow(
                checked = uiState.isServiceTermChecked,
                annotateText = SIGN_UP_I_AGREE_SERVICE_TERM,
                annotateTag = "serviceTerm",
                onClickAgreeBtn = onClickServiceTermAgreeBtn,
                onClickTermsDetailBtn = onClickServiceTermDetail,
                interactionSource = interactionSource
            )

            Spacer(modifier = Modifier.size(24.dp))

            TermTextRow(
                checked = uiState.isPersonalTermChecked,
                annotateText = SIGN_UP_I_AGREE_PERSONAL_TERM,
                annotateTag = "personalTerm",
                onClickAgreeBtn = onClickPersonalTermAgreeBtn,
                onClickTermsDetailBtn = onClickPersonalTermDetail,
                interactionSource = interactionSource
            )

            Spacer(modifier = Modifier.size(24.dp))

            TermTextRow(
                checked = uiState.isAgeTermChecked,
                termsText = SIGN_UP_I_AGREE_AGE_TERM,
                onClickAgreeBtn = onClickAgeTermAgreeBtn,
                interactionSource = interactionSource
            )

        }

        Column(
            verticalArrangement = Arrangement.Bottom
        ) {
            BlankBtn(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                onClickBtn = onClickNextPageBtn,
                text = WORD_NEXT,
                isActive = uiState.isAllTermChecked
            )
            Spacer(modifier = Modifier.size(12.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = SIGN_UP_TERMS_ASK,
                    style = p2(),
                    color = Gs70
                )

                Spacer(modifier = Modifier.size(4.dp))

                Text(
                    text = FOREGG_EMAIL,
                    style = p2(),
                    color = Gs70
                )
            }

            Spacer(modifier = Modifier.height(48.dp))
        }
    }
}

@Composable
fun TermTextRow(
    checked : Boolean,
    termsText : String = "",
    annotateText : AnnotatedString = SIGN_UP_I_AGREE_SERVICE_TERM,
    annotateTag : String = "",
    onClickAgreeBtn : () -> Unit,
    onClickTermsDetailBtn : () -> Unit = {},
    interactionSource: MutableInteractionSource,
){
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 28.dp, end = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            modifier = Modifier
                .clickable(
                    onClick = onClickAgreeBtn,
                    interactionSource = interactionSource,
                    indication = null
                ),
            painter = painterResource(id = if(checked) R.drawable.ic_checked_box_black else R.drawable.ic_unchecked_box_black),
            contentDescription = null
        )

        Spacer(modifier = Modifier.size(8.dp))

        if(termsText.isEmpty()) {
            ClickableText(
                text = annotateText,
                style = p2(),
                onClick = { offset ->
                    annotateText.getStringAnnotations(tag = annotateTag, start = offset, end = offset)
                        .firstOrNull()?.let {
                            onClickTermsDetailBtn()
                        }
                }
            )
        }
        else{
            Text(
                text = termsText,
                style = p2(),
                color = Gs90
            )
        }

        Spacer(modifier = Modifier.size(4.dp))

        Text(
            text = SIGN_UP_ESSENTIAL_WORD,
            style = p2(),
            color = MainStrong
        )

        Spacer(modifier = Modifier.weight(1f))

        if(termsText.isEmpty()) {
            Image(
                modifier = Modifier.onThrottleClick(
                    onClick = onClickTermsDetailBtn,
                    interactionSource = interactionSource
                ),
                painter = painterResource(id = R.drawable.ic_right_arrow_gs_50),
                contentDescription = null
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
    ServiceTermsScreen()
}