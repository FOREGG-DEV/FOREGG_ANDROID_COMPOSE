package com.hugg.account.subsidyList

import com.hugg.domain.model.response.account.SubsidyListResponseVo
import com.hugg.feature.base.PageState
import com.hugg.feature.util.UserInfo

data class SubsidyListPageState(
    val nowRound : Int = UserInfo.info.round,
    val subsidyList : List<SubsidyListResponseVo> = emptyList(),
    val isShowCreateRoundDialog : Boolean = false,
) : PageState
