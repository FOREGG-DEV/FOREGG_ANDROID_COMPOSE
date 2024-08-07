package com.hugg.sign.inputSsn

import com.hugg.feature.base.PageState

data class InputSsnPageState(
    val ssn1 : String = "",
    val ssn2 : String = "",
    val ssn3 : String = "",
    val ssn4 : String = "",
    val ssn5 : String = "",
    val ssn6 : String = "",
    val ssn7 : String = "",
) : PageState