package com.hugg.domain.model.enums

enum class DailyConditionType(val value: String) {
    WORST("화나요"),
    BAD("슬퍼요"),
    SOSO("불안해요"),
    GOOD("기뻐요"),
    PERFECT("사랑해요"),
    DEFAULT("기본 상태");

    companion object {
        fun fromValue(value: String): DailyConditionType? {
            return entries.find { it.value == value }
        }
    }
}