package com.hugg.mypage.myMedicineInjection

import androidx.lifecycle.viewModelScope
import com.hugg.domain.model.enums.HuggTabClickedType
import com.hugg.domain.model.enums.ProfileMedicineInjectionType
import com.hugg.domain.model.enums.RecordType
import com.hugg.domain.model.response.profile.MyMedicineInjectionResponseVo
import com.hugg.domain.model.response.profile.ProfileDetailResponseVo
import com.hugg.domain.repository.ProfileRepository
import com.hugg.feature.base.BaseViewModel
import com.hugg.feature.theme.CALENDAR_INJECTION_UNIT
import com.hugg.feature.theme.CALENDAR_MEDICINE_UNIT
import com.hugg.feature.util.UserInfo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MyPageMedInjViewModel @Inject constructor(
    private val profileRepository: ProfileRepository
) : BaseViewModel<MyPageMedInjPageState>(
    MyPageMedInjPageState()
) {
    fun getMyMedicineInjection(){
        viewModelScope.launch {
            profileRepository.getMyMedicineInjection(uiState.value.tabType.type).collect{
                resultResponse(it, ::handleSuccessGetMyMedicalInfo)
            }
        }
    }

    private fun handleSuccessGetMyMedicalInfo(result : List<MyMedicineInjectionResponseVo>){
        updateState(
            uiState.value.copy(itemList = getMappedNameList(result))
        )
    }

    private fun getMappedNameList(list : List<MyMedicineInjectionResponseVo>) : List<MyMedicineInjectionResponseVo> {
        return list.map {
            it.copy(
                name = if(uiState.value.tabType == ProfileMedicineInjectionType.INJECTION) "(${it.dose}$CALENDAR_INJECTION_UNIT)${it.name}" else "(${it.dose}$CALENDAR_MEDICINE_UNIT)${it.name}"
            )
        }
    }

    fun onClickTab(type : ProfileMedicineInjectionType){
        updateState(
            uiState.value.copy(
                tabType = type,
                tabClickedType = if(type == ProfileMedicineInjectionType.MEDICINE) HuggTabClickedType.LEFT else HuggTabClickedType.RIGHT
            )
        )
        getMyMedicineInjection()
    }
}