package com.example.dailyhugg.preview

import android.net.Uri
import com.hugg.feature.base.PageState

data class ImagePreviewPageState(
    val selectedUri: Uri? = null
) : PageState