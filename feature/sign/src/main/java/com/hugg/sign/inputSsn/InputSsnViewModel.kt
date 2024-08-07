package com.hugg.sign.inputSsn

import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.KeyEvent
import androidx.compose.ui.input.key.KeyEventType
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.type
import androidx.lifecycle.viewModelScope
import com.hugg.feature.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class InputSsnViewModel @Inject constructor() : BaseViewModel<InputSsnPageState>(
    InputSsnPageState()
) {

    companion object {
        const val OTP_LENGTH = 7
    }
    fun onClickKey(event : KeyEvent, position : Int){
        if (getSsnPosition(position).isEmpty() && event.key == Key.Backspace && event.type == KeyEventType.KeyDown) {
            if (position != 0) {
                updateSsn(position-1, "")
                emitEventFlow(InputSsnEvent.FocusTextFiled(position - 1))
            }
        }
    }

    fun onChangedText(value : String, position: Int){
        if (value.length <= 1) {
            updateSsn(position, value)
            if (value.isNotEmpty() && position < OTP_LENGTH - 1) {
                emitEventFlow(InputSsnEvent.FocusTextFiled(position + 1))
            }
            if(position == OTP_LENGTH - 1) emitEventFlow(InputSsnEvent.HideKeyboard)
        }
    }

    fun onClickNextBtn(){
        val ssn = uiState.value.run {
            "$ssn1$ssn2$ssn3$ssn4$ssn5$ssn6-$ssn7"
        }
        if(uiState.value.ssn7.toInt() % 2 == 0) emitEventFlow(InputSsnEvent.GoToFemaleSignUp(ssn))
        else emitEventFlow(InputSsnEvent.GoToMaleSignUp(ssn))
    }

    private fun updateSsn(position : Int, value : String){
        when(position){
            0 -> updateState(uiState.value.copy(ssn1 = value))
            1 -> updateState(uiState.value.copy(ssn2 = value))
            2 -> updateState(uiState.value.copy(ssn3 = value))
            3 -> updateState(uiState.value.copy(ssn4 = value))
            4 -> updateState(uiState.value.copy(ssn5 = value))
            5 -> updateState(uiState.value.copy(ssn6 = value))
            6 -> updateState(uiState.value.copy(ssn7 = value))
            else -> {}
        }
    }

    private fun getSsnPosition(position : Int) : String{
        return when(position){
            0 -> uiState.value.ssn1
            1 -> uiState.value.ssn2
            2 -> uiState.value.ssn3
            3 -> uiState.value.ssn4
            4 -> uiState.value.ssn5
            5 -> uiState.value.ssn6
            6 -> uiState.value.ssn7
            else -> ""
        }
    }
}