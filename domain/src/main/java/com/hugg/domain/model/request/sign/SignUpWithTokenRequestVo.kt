package com.hugg.domain.model.request.sign

data class SignUpWithTokenRequestVo(
    val accessToken : String,
    val signUpRequestVo: SignUpRequestVo
)
