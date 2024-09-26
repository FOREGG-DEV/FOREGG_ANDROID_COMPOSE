package com.hugg.mypage.profileManagement

import com.hugg.feature.base.PageState

data class MyPageProfileManagementPageState(
    val isShowLogoutDialog : Boolean = false,
    val isShowLogoutCompleteDialog : Boolean = false,
    val isShowUnregisterDialog : Boolean = false,
    val isShowUnregisterCompleteDialog : Boolean = false,
) : PageState
