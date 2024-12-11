package com.hugg.data.mapper.challenge

import com.hugg.data.base.Mapper
import com.hugg.data.dto.challenge.ChallengeListResponse
import com.hugg.data.dto.challenge.ChallengeResponseListItem
import com.hugg.domain.model.response.challenge.ChallengeCardVo

object ChallengeResponseMapper: Mapper.ResponseMapper<ChallengeListResponse, List<ChallengeCardVo>> {
    override fun mapDtoToModel(type: ChallengeListResponse?): List<ChallengeCardVo> {
        return type?.dtos?.map { listItem ->
            ChallengeCardVo(
                id = listItem.id,
                name = listItem.name,
                description = listItem.description,
                participants = listItem.participants,
                image = listItem.image ?: "",
                participating = listItem.participating,
                point = listItem.point,
                open = listItem.open
            )
        } ?: emptyList()
    }
}