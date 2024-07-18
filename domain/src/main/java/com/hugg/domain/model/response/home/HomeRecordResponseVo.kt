package com.hugg.domain.model.response.home

import com.google.gson.annotations.SerializedName
import com.hugg.domain.model.enums.RecordType

data class HomeRecordResponseVo(
    @SerializedName("id")
    val id: Long,
    @SerializedName("recordType")
    val recordType: RecordType,
    @SerializedName("times")
    val times: List<String>,
    @SerializedName("name")
    val name: String,
    @SerializedName("memo")
    val memo: String
)
