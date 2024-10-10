package com.hugg.domain.model.response.profile

data class MyMedicineInjectionResponseVo(
    val id : Long = -1,
    val date : String = "",
    val startDate : String = "",
    val endDate : String = "",
    val repeatDays : String = "",
    val name : String = "",
    val dose : String = "",
)
