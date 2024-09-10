package com.hugg.data.mapper.account

import com.hugg.data.base.Mapper
import com.hugg.data.dto.account.AccountDetailResponse
import com.hugg.domain.model.response.account.AccountDetailResponseVo
import com.hugg.domain.model.response.account.AccountSubsidyAvailableItemVo

object AccountDetailResponseMapper: Mapper.ResponseMapper<AccountDetailResponse, AccountDetailResponseVo> {

    override fun mapDtoToModel(type: AccountDetailResponse?): AccountDetailResponseVo {
        return type?.run {
            AccountDetailResponseVo(
                date = date,
                count = count,
                content = content,
                memo = memo,
                subsidyAvailable = expenditureRequestDTOList.map {
                    AccountSubsidyAvailableItemVo(
                        color = it.color,
                        nickname = it.nickname,
                        amount = it.amount
                    )
                }
            )
        }?: AccountDetailResponseVo()
    }
}