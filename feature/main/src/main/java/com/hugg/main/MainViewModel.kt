package com.hugg.main

import com.hugg.domain.model.enums.BottomNavType
import com.hugg.feature.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
) : BaseViewModel<MainPageState>(MainPageState()) {

    fun changedRoute(route : String?){
        val type = when(route){
            Routes.CalendarScreen.route -> BottomNavType.CALENDAR
            Routes.AccountScreen.route -> BottomNavType.ACCOUNT
            Routes.DailyHuggScreen.route -> BottomNavType.DAILY_HUGG
            Routes.MyPageScreen.route -> BottomNavType.PROFILE
            Routes.HomeScreen.route -> BottomNavType.HOME
            else -> BottomNavType.OTHER
        }

        updatePageType(type)
    }
    private fun updatePageType(type : BottomNavType){
        updateState(
            uiState.value.copy(pageType = type)
        )
    }
}