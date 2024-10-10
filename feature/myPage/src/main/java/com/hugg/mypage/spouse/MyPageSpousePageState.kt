package com.hugg.mypage.spouse

import com.hugg.feature.base.PageState

data class MyPageSpousePageState(
    val spouseCode: String = "",
    val isShowSnackBar : Boolean = false
) : PageState