package com.app.silky.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "USERS")
data class UserEntity(
    @PrimaryKey(autoGenerate = true) var id: Int = 0,
    val userid : Int,
    val name : String,
    val username : String,
    val email : String,
    val phone : String,
    val website : String,
    val address: AddressEntity,
    val company: CompanyEntity,
)