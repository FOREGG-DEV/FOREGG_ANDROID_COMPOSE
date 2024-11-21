package com.hugg.data.mapper.challenge

import com.hugg.data.base.Mapper
import com.hugg.data.dto.challenge.AllChallengeResponse
import com.hugg.domain.model.response.challenge.AllChallengeItemVo

object AllChallengeResponseMapper: Mapper.ResponseMapper<AllChallengeResponse, List<AllChallengeItemVo>> {
    override fun mapDtoToModel(type: AllChallengeResponse?): List<AllChallengeItemVo> {
        return type?.let {
            it.dtos.map { item ->
                AllChallengeItemVo(
                    id = item.id,
                    point = item.point,
                    image = item.image,
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