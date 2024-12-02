package com.hugg.data.mapper.challenge

import com.hugg.data.base.Mapper
import com.hugg.data.dto.challenge.ChallengeSupportResponseItem
import com.hugg.domain.model.response.challenge.ChallengeSupportItemVo

object ChallengeSupportResponseMapper: Mapper.ResponseMapper<List<ChallengeSupportResponseItem>, List<ChallengeSupportItemVo>> {
    override fun mapDtoToModel(type: List<ChallengeSupportResponseItem>?): List<ChallengeSupportItemVo> {
        return type?.map {
            ChallengeSupportItemVo(
                id = it.id,
                challengeNickname = it.challengeNickname,
                thought = it.thought,
                success = it.success,
                clap = it.clap,
                support = it.support
            )
        } ?: emptyList()
    }
}