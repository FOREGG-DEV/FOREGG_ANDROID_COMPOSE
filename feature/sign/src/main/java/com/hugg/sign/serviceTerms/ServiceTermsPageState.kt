package com.hugg.sign.serviceTerms

import com.hugg.feature.base.PageState

data class ServiceTermsPageState(
    val isServiceTermChecked : Boolean = false,
    val isPersonalTermChecked : Boolean = false,
    val isAgeTermChecked : Boolean = false,
) : PageState {
    val isAllTermChecked = isServiceTermChecked && isPersonalTermChecked && isAgeTermChecked
}