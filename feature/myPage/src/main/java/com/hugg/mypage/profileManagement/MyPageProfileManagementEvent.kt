package com.hugg.mypage.profileManagement

import com.hugg.feature.base.Event

sealed class MyPageProfileManagementEvent : Event {
    data object SuccessLogoutEvent : MyPageProfileManagementEvent()
    data object SuccessUnregisterEvent : MyPageProfileManagementEvent()
}