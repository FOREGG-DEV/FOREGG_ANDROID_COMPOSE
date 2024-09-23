package com.hugg.domain.model.enums

enum class RecordType(val type : String, val priority : Int) {
    MEDICINE("약", 3), INJECTION("주사", 2), HOSPITAL("병원", 1), ETC("기타", 4);

    companion object {
        fun getEnumType(value: String): RecordType {
            return RecordType.values().find {
                it.type == value
            } ?: RecordType.MEDICINE
        }
    }
}