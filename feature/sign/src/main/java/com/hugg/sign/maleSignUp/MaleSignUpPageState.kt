package com.hugg.sign.maleSignUp

import com.hugg.feature.base.PageState

data class MaleSignUpPageState(
    val spouseCode: String = "",
    val isShowErrorSpouseCode : Boolean = false
) : PageState