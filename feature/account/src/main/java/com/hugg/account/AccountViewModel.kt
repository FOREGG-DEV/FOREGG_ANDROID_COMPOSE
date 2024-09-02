package com.hugg.account

import com.hugg.domain.model.enums.AccountTabType
import com.hugg.feature.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import java.text.NumberFormat
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class AccountViewModel @Inject constructor(
) : BaseViewModel<AccountPageState>(
    AccountPageState()
) {
    fun onClickTabType(type : AccountTabType){
        updateState(
            uiState.value.copy(tabType = type)
        )
    }

    fun onClickFilterBox(filterText : String){
        updateState(
            uiState.value.copy(filterText = filterText)
        )
    }

    private fun getMoneyFormat(money : Int) : String {
        val koreanFormat = NumberFormat.getNumberInstance(Locale("ko"))
        return koreanFormat.format(money) + "원"
    }
}