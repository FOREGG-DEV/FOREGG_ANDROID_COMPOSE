package com.hugg.domain.model.enums

enum class CreateOrEditType(val type : String) {
    CREATE("create_mode"), EDIT("edit_mode");

    companion object {
        fun getEnumType(value: String): CreateOrEditType {
            return CreateOrEditType.values().find {
                it.type == value
            } ?: CreateOrEditType.CREATE
        }
    }
}