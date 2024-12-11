package com.hugg.domain.model.request.challenge

data class CreateChallengeRequestVo(
    val challengeEmojiType: String = "RED",
    val name: String = "",
    val description: String = ""
)
