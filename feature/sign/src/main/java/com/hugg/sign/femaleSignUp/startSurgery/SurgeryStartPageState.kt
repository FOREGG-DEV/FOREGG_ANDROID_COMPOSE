package com.hugg.sign.femaleSignUp.startSurgery

import com.hugg.feature.base.PageState
import com.hugg.feature.util.TimeFormatter

data class SurgeryStartPageState(
    val date : String = TimeFormatter.getToday()
) : PageState