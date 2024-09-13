package com.hugg.account.subsidyCreateOrEdit

import com.hugg.domain.model.enums.CreateOrEditType
import com.hugg.domain.model.response.account.SubsidyListResponseVo
import com.hugg.feature.base.PageState
import com.hugg.feature.util.UserInfo

data class SubsidyCreateOrEditPageState(
    val nickname : String = "",
    val content : String = "",
    val money : String = "",
    val pageType : CreateOrEditType = CreateOrEditType.CREATE,
    val id : Long = -1,
    val nowRound : Int = UserInfo.info.round,
    val isShowDialog : Boolean = false,
) : PageState {
    val isActiveBtn = nickname.isNotEmpty() && money.isNotEmpty()
}