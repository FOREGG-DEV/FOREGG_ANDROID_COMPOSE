package com.hugg.feature.util

import com.hugg.domain.model.response.profile.ProfileDetailResponseVo
import com.hugg.domain.model.vo.challenge.ChallengeInfoVo
import com.hugg.domain.model.vo.user.UserVo

object UserInfo {
    const val VERSION = "1.0.1"
    private var challengeInfo = ChallengeInfoVo()
    val challengeNickname: String get() = challengeInfo.nickname
    val challengePoint: Int get() = challengeInfo.point
    var info: UserVo = UserVo()
        private set

    fun updateInfo(info: ProfileDetailResponseVo) {
        this.info = UserVo(id = info.id,name = info.nickName, ssn = info.ssn, genderType = info.genderType, spouse = info.spouse, round = info.round)
    }

    fun updateChallengeNickname(nickname: String) {
        challengeInfo = challengeInfo.copy(nickname = nickname)
    }

    fun updateChallengePoint(point: Int) {
        challengeInfo = challengeInfo.copy(point = point)
    }
}