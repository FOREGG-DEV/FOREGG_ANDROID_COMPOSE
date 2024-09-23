package com.hugg.dailyhugg.preview

import android.net.Uri
import com.hugg.feature.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ImagePreviewViewModel @Inject constructor(

) : BaseViewModel<ImagePreviewPageState>(ImagePreviewPageState()) {

    fun setUri(uri: Uri?) {
        updateState(
            uiState.value.copy(
                selectedUri = uri
            )
        )
    }
}