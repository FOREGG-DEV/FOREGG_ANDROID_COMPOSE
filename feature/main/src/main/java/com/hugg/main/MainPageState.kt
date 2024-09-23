package com.hugg.main

import com.hugg.domain.model.enums.BottomNavType
import com.hugg.feature.base.PageState

data class MainPageState(
    val pageType : BottomNavType = BottomNavType.OTHER
) : PageState
