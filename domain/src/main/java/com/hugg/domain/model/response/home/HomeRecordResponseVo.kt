package com.hugg.domain.model.response.home

import com.google.gson.annotations.SerializedName
import com.hugg.domain.model.enums.RecordType

data class HomeRecordResponseVo(
    @SerializedName("id")
    val id: Long = -1,
    @SerializedName("recordType")
    val recordType: RecordType = RecordType.ETC,
    @SerializedName("time")
    val time: String = "",
    @SerializedName("name")
    val name: String = "",
    @SerializedName("memo")
    val memo: String = "",
    @SerializedName("todo")
    val todo : Boolean = false
)
