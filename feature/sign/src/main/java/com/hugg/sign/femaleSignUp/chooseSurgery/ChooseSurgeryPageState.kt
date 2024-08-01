package com.hugg.sign.femaleSignUp.chooseSurgery

import com.hugg.domain.model.enums.SurgeryType
import com.hugg.feature.base.PageState

data class ChooseSurgeryPageState(
    val surgeryType: SurgeryType = SurgeryType.THINK_SURGERY,
    val isExpandMenu : Boolean = false,
) : PageState