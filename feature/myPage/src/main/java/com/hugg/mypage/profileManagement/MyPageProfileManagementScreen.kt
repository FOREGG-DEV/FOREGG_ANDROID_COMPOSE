package com.hugg.mypage.profileManagement

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.hugg.domain.model.enums.DialogType
import com.hugg.domain.model.enums.TopBarLeftType
import com.hugg.domain.model.enums.TopBarMiddleType
import com.hugg.feature.component.HuggDialog
import com.hugg.feature.component.HuggText
import com.hugg.feature.component.TopBar
import com.hugg.feature.theme.*
import com.hugg.feature.util.HuggToast
import com.hugg.feature.util.onThrottleClick
import com.kakao.sdk.user.UserApiClient


@Composable
fun MyPageProfileManagementContainer(
    goToBack: () -> Unit = {},
    navigateToSignGraph : () -> Unit = {},
    viewModel: MyPageProfileManagementViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        viewModel.eventFlow.collect { event ->
            when(event) {
                MyPageProfileManagementEvent.SuccessLogoutEvent -> {
                    kakaoLogout(context = context) { viewModel.showLogoutCompleteDialog(true) }
                }
                MyPageProfileManagementEvent.SuccessUnregisterEvent -> {
                    kakaoUnregister(context = context) { viewModel.showUnregisterCompleteDialog(true) }
                }
            }
        }
    }

    MyPageProfileManagementScreen(
        goToBack = goToBack,
        onClickLogout = { viewModel.showLogoutDialog(true) },
        onClickUnregister = { viewModel.showUnregisterDialog(true) }
    )

    if(uiState.isShowLogoutDialog){
        HuggDialog(
            title = MY_PAGE_LOGOUT_DIALOG,
            positiveColor = Sunday,
            positiveText = WORD_LOGOUT,
            onClickCancel = { viewModel.showLogoutDialog(false) },
            onClickNegative = { viewModel.showLogoutDialog(false) },
            onClickPositive = { viewModel.logout() },
        )
    }

    if(uiState.isShowLogoutCompleteDialog){
        HuggDialog(
            title = MY_PAGE_LOGOUT_COMPLETE_DIALOG,
            dialogType = DialogType.SINGLE,
            positiveText = WORD_CONFIRM,
            onClickCancel = { viewModel.showLogoutCompleteDialog(false) },
            onClickPositive = navigateToSignGraph,
        )
    }

    if(uiState.isShowUnregisterDialog){
        HuggDialog(
            title = MY_PAGE_UNREGISTER_DIALOG,
            positiveColor = Sunday,
            positiveText = WORD_UNREGISTER,
            onClickCancel = { viewModel.showUnregisterDialog(false) },
            onClickNegative = { viewModel.showUnregisterDialog(false) },
            onClickPositive = { viewModel.unregister() },
        )
    }

    if(uiState.isShowUnregisterCompleteDialog){
        HuggDialog(
            title = MY_PAGE_UNREGISTER_COMPLETE_DIALOG,
            dialogType = DialogType.SINGLE,
            positiveText = WORD_CONFIRM,
            onClickCancel = { viewModel.showUnregisterCompleteDialog(false) },
            onClickPositive = navigateToSignGraph,
        )
    }
}

@Composable
fun MyPageProfileManagementScreen(
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    goToBack : () -> Unit = {},
    onClickLogout: () -> Unit = {},
    onClickUnregister: () -> Unit = {},
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
            middleText = MY_PAGE_PROFILE_MANAGEMENT
        )

        Spacer(modifier = Modifier.size(24.dp))

        MenuButtonBox(
            onClickBtn = onClickLogout,
            interactionSource = interactionSource,
            text = MY_PAGE_PROFILE_MANAGEMENT_LOGOUT
        )

        Spacer(modifier = Modifier.size(8.dp))

        MenuButtonBox(
            onClickBtn = onClickUnregister,
            interactionSource = interactionSource,
            text = MY_PAGE_PROFILE_MANAGEMENT_UNREGISTER
        )
    }
}

@Composable
fun MenuButtonBox(
    onClickBtn : () -> Unit = {},
    interactionSource: MutableInteractionSource,
    text : String = "",
){
    Box(
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .background(color = White, shape = RoundedCornerShape(8.dp))
            .fillMaxWidth()
            .height(48.dp)
            .onThrottleClick(
                onClick = onClickBtn,
                interactionSource = interactionSource
            )
            .padding(horizontal = 12.dp),
        contentAlignment = Alignment.CenterStart
    ){
        HuggText(
            text = text,
            style = HuggTypography.p2,
            color = if(text == MY_PAGE_PROFILE_MANAGEMENT_LOGOUT) Black else Unregister
        )
    }
}

private fun kakaoLogout(context : Context, successFun : () -> Unit){
    UserApiClient.instance.logout { error ->
        if (error != null) {
            HuggToast.createToast(context, TOAST_ERROR_FAILED_LOGOUT).show()
        }
        else{
            successFun()
        }
    }
}

private fun kakaoUnregister(context: Context, successFun : () -> Unit){
    UserApiClient.instance.unlink { error ->
        if (error != null) {
            HuggToast.createToast(context, TOAST_ERROR_FAILED_UNREGISTER).show()
        }
        else{
            successFun()
        }
    }
}

@Preview
@Composable
internal fun PreviewMainContainer() {
    MyPageProfileManagementScreen()
}