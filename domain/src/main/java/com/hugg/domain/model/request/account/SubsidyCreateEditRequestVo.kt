package com.hugg.domain.model.request.account

import com.google.gson.annotations.SerializedName

data class SubsidyRequestVo(
    val id : Long = -1,
    val request : SubsidyCreateEditRequestVo = SubsidyCreateEditRequestVo()
)

data class SubsidyCreateEditRequestVo(
    @SerializedName("nickname")
    val nickname : String = "",
    @SerializedName("content")
    val content : String = "",
    @SerializedName("amount")
    val amount : Int = -1,
    @SerializedName("count")
    val count : Int = -1,
)
