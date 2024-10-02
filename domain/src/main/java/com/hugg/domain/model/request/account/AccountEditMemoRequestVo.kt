package com.hugg.domain.model.request.account

import com.google.gson.annotations.SerializedName

data class AccountEditMemoRequestVo(
    @SerializedName("memo")
    val memo : String = "",
)
