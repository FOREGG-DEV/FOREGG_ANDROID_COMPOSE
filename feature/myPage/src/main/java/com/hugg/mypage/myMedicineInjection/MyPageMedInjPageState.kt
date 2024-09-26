package com.hugg.mypage.myMedicineInjection

import com.hugg.domain.model.enums.HuggTabClickedType
import com.hugg.domain.model.enums.ProfileMedicineInjectionType
import com.hugg.domain.model.response.profile.MyMedicineInjectionResponseVo
import com.hugg.feature.base.PageState

data class MyPageMedInjPageState(
    val tabType : ProfileMedicineInjectionType = ProfileMedicineInjectionType.MEDICINE,
    val itemList : List<MyMedicineInjectionResponseVo> = emptyList(),
    val tabClickedType: HuggTabClickedType = HuggTabClickedType.LEFT
) : PageState