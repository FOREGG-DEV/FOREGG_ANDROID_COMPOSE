package com.hugg.domain.model.request.sign

data class SignUpWithTokenMaleRequestVo(
    val accessToken : String,
    val signUpMaleRequestVo: SignUpMaleRequestVo
)