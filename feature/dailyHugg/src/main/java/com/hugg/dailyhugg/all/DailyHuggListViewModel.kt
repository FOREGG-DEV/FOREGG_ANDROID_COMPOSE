package com.hugg.dailyhugg.all

import com.hugg.feature.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DailyHuggListViewModel @Inject constructor(

) : BaseViewModel<DailyHuggListPageState>(DailyHuggListPageState()) {
    
}