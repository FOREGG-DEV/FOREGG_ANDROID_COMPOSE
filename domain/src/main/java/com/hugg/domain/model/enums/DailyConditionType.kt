package com.hugg.domain.model.enums

enum class DailyConditionType(val value: String) {
    WORST("매우 안좋음"),
    BAD("안좋음"),
    SOSO("보통"),
    GOOD("좋음"),
    PERFECT("매우 좋음"),
    DEFAULT("기본 상태")
}