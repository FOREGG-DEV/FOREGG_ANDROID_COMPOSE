package com.hugg.domain.model.request.calendar

import com.google.gson.annotations.SerializedName

data class AddMedicalRecordRequestVo(
    val id : Long,
    val request : AddMedicalRecordRequest
)

data class AddMedicalRecordRequest(
    @SerializedName("medicalRecord")
    val medicalRecord: String,
)