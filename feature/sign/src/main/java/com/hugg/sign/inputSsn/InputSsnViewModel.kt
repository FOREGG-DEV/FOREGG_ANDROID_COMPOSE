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
        if (uiState.value.ssnList[position].isEmpty() && event.key == Key.Backspace && event.type == KeyEventType.KeyDown) {
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
        val ssn = uiState.value.ssnList.joinToString(separator = "").run {
            substring(0, 6) + "-" + substring(6, 7)
        }
        if(uiState.value.ssnList[6].toInt() % 2 == 0) emitEventFlow(InputSsnEvent.GoToFemaleSignUp(ssn))
        else emitEventFlow(InputSsnEvent.GoToMaleSignUp(ssn))
    }

    private fun updateSsn(position : Int, value : String){
        val newList = uiState.value.ssnList.mapIndexed { index, origin ->
            if(index == position) value else origin
        }
        updateState(uiState.value.copy(ssnList = newList))
    }
}