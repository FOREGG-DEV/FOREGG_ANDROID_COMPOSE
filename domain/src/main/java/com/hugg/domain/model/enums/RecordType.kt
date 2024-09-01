package com.hugg.domain.model.enums

enum class RecordType(val type : String, val priority : Int) {
    MEDICINE("약", 3), INJECTION("주사", 2), HOSPITAL("병원", 1), ETC("기타", 4);
}