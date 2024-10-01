package com.hugg.data.mapper.account

import com.hugg.data.base.Mapper
import com.hugg.data.dto.account.AccountResponse
import com.hugg.domain.model.response.account.AccountItemResponseVo
import com.hugg.domain.model.response.account.AccountResponseVo
import com.hugg.domain.model.response.account.AccountSubsidyAvailableItemVo

object AccountItemResponseMapper: Mapper.ResponseMapper<AccountResponse, AccountResponseVo> {
    override fun mapDtoToModel(type: AccountResponse?): AccountResponseVo {
        return type?.run {
            AccountResponseVo(
                personalSum = personalSum,
                subsidySum = subsidySum ?: 0,
                subsidyAvailable = subsidyAvailable?.map {
                    AccountSubsidyAvailableItemVo(
                        color = it.color,
                        nickname = it.nickname,
                        amount = it.amount
                    )
                } ?: emptyList(),
                total = total,
                ledgerDetailResponseDTOS = ledgerDetailResponseDTOS.map {
                    AccountItemResponseVo(
                        ledgerId = it.ledgerId,
                        expenditureId = it.expenditureId,
                        date = it.date,
                        round = it.count,
                        color = it.color,
                        name = it.name,
                        content = it.content,
                        amount = it.amount
                    )
                }
            )
        } ?: AccountResponseVo()
    }
}