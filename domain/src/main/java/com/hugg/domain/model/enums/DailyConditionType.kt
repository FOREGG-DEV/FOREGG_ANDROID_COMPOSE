package com.hugg.domain.model.enums

enum class DailyConditionType(val value: String) {
    MAD("화나요"),
    SAD("슬퍼요"),
    ANXIOUS("불안해요"),
    HAPPY("기뻐요"),
    LOVE("사랑해요"),
    DEFAULT("기본 상태");

    companion object {
        fun fromValue(value: String): DailyConditionType? {
            return entries.find { it.value == value }
        }
    }
}