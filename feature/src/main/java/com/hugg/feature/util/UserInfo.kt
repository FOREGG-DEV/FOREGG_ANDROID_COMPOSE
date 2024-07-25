package com.hugg.feature.util

import com.hugg.domain.model.vo.user.UserVo

object UserInfo {

    var info: UserVo = UserVo()
        private set

    fun updateInfo(info: UserVo) {
        this.info = info
    }
}