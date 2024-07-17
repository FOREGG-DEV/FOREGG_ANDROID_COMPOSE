package com.hugg.domain.model.response.sign

data class SignResponseVo(
    val accessToken : String = "",
    val refreshToken : String = "",
    val shareCode : String = "",
)
