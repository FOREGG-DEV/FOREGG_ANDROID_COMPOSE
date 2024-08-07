package com.hugg.sign.femaleSignUp.spouseCodeFemale

import com.hugg.feature.base.PageState
import com.hugg.feature.util.TimeFormatter

data class SpouseCodeFemalePageState(
    val spouseCode : String = "",
    val isShowSnackBar : Boolean = false
) : PageState