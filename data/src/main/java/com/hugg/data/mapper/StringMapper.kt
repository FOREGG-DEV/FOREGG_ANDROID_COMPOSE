package com.hugg.data.mapper

import com.hugg.data.base.Mapper

object StringMapper: Mapper.ResponseMapper<String, String> {
    override fun mapDtoToModel(type: String?): String {
        return type ?: ""
    }
}