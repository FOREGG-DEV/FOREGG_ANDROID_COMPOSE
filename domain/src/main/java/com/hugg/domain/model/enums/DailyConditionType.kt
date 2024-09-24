package com.hugg.domain.model.enums

enum class DailyConditionType(val value: String) {
    WORST("매우 안좋아요"),
    BAD("안좋아요"),
    SOSO("보통이예요"),
    GOOD("좋아요"),
    PERFECT("매우 좋아요"),
    DEFAULT("기본 상태")
}