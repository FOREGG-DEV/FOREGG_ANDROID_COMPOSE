package com.hugg.domain.model.enums

enum class CreateOrEditType(name : String) {
    CREATE("create_mode"), EDIT("edit_mode");

    companion object {
        fun getEnumType(value: String): CreateOrEditType {
            return CreateOrEditType.values().find {
                it.name == value
            } ?: CreateOrEditType.CREATE
        }
    }
}