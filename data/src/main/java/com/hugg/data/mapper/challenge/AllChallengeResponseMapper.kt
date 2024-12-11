package com.hugg.data.mapper.challenge

import com.hugg.data.base.Mapper
import com.hugg.data.dto.challenge.ChallengeListResponse
import com.hugg.domain.model.response.challenge.ChallengeCardVo

object AllChallengeResponseMapper: Mapper.ResponseMapper<ChallengeListResponse, List<ChallengeCardVo>> {
    override fun mapDtoToModel(type: ChallengeListResponse?): List<ChallengeCardVo> {
        return type?.let {
            it.dtos.map { item ->
                ChallengeCardVo(
                    id = item.id,
                    point = item.point,
                    image = item.image ?: "",
                    name = item.name,
                    description = item.description,
                    participants = item.participants,
                    open = item.open,
                    participating = item.participating
                )
            }
        } ?: emptyList()
    }
}