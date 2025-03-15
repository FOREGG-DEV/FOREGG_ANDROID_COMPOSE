package com.hugg.data.mapper

import com.hugg.data.base.Mapper

object StringResponseMapper: Mapper.ResponseMapper<String, String> {
    override fun mapDtoToModel(type: String?): String {
        return type ?: ""
    }
}