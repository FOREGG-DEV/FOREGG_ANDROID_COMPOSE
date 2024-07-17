package com.hugg.domain.model.request.dailyRecord

import com.google.gson.annotations.SerializedName

data class CreateSideEffectRequestVo(
    @SerializedName("content")
    val content: String = ""
)
