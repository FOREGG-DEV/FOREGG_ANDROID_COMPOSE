package com.hugg.feature.util

import com.hugg.domain.model.response.profile.ProfileDetailResponseVo
import com.hugg.domain.model.vo.user.UserVo

object UserInfo {

    var info: UserVo = UserVo()
        private set

    fun updateInfo(info: ProfileDetailResponseVo) {
        this.info = UserVo(name = info.nickName, ssn = info.ssn, genderType = info.genderType, spouse = info.spouse, round = info.round)
    }
}