package com.hugg.mypage.main

import com.hugg.feature.base.PageState

data class MyPagePageState(
    val spouse: String = "",
    val alarmSetting: Boolean = true,
) : PageState