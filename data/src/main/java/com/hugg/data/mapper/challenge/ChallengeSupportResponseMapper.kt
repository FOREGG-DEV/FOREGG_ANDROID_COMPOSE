package com.hugg.data.mapper.challenge

import com.hugg.data.base.Mapper
import com.hugg.data.dto.challenge.ChallengeSupportResponseItem
import com.hugg.domain.model.response.challenge.ChallengeSupportItemVo

object ChallengeSupportResponseMapper: Mapper.ResponseMapper<List<ChallengeSupportResponseItem>, List<ChallengeSupportItemVo>> {
    override fun mapDtoToModel(type: List<ChallengeSupportResponseItem>?): List<ChallengeSupportItemVo> {
        return type?.map {
            ChallengeSupportItemVo(
                userId = it.id,
                nickname = it.nickname,
                thoughts = it.thoughts,
                supported = it.supported
            )
        } ?: emptyList()
    }
}