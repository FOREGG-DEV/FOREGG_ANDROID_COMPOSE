package com.hugg.sign.inputSsn

import com.hugg.feature.base.PageState

data class InputSsnPageState(
    val ssnList : List<String> = List(7) {""}
) : PageState