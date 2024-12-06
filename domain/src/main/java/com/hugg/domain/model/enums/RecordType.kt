package com.hugg.domain.model.enums

enum class RecordType(val type : String, val string : String) {
    MEDICINE("약", "MEDICINE"), INJECTION("주사", "INJECTION"), HOSPITAL("병원", "HOSPITAL"), ETC("기타", "ETC");

    companion object {
        fun getEnumType(value: String): RecordType {
            return RecordType.values().find {
                it.type == value
            } ?: RecordType.MEDICINE
        }

        fun getEnumTypeByString(value: String): RecordType {
            return RecordType.values().find {
                it.string == value
            } ?: RecordType.MEDICINE
        }
    }
}