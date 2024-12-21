package com.hugg.data.mapper.challenge

import com.hugg.data.base.Mapper
import com.hugg.data.dto.challenge.ChallengeResponseListItem
import com.hugg.domain.model.response.challenge.ChallengeCardVo

object ChallengeDetailResponseMapper: Mapper.ResponseMapper<ChallengeResponseListItem, ChallengeCardVo> {
    override fun mapDtoToModel(type: ChallengeResponseListItem?): ChallengeCardVo {
        return type?.let {
            ChallengeCardVo(
                id = it.id,
                name = it.name,
                description = it.description,
                participants = it.participants,
                image = it.image ?: "",
                participating = it.participating,
                open = it.open,
                point = it.point,
                isCreateChallenge = false
            )
        } ?: ChallengeCardVo()
    }
}