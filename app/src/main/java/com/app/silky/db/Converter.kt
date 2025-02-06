package com.app.silky.db

import androidx.room.TypeConverter
import com.app.silky.db.entity.AddressEntity
import com.app.silky.db.entity.CompanyEntity
import com.app.silky.db.entity.GeoEntity
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class Converter {

    val gson = Gson()

    @TypeConverter
    fun toAddressEntityToString(addressEntity: AddressEntity): String {

        val type = object : TypeToken<AddressEntity>() {}.type
        return gson.toJson(addressEntity, type)
    }

    @TypeConverter
    fun fromStringToAddressEntity(string: String): AddressEntity {

        val type = object : TypeToken<AddressEntity>() {}.type
        return gson.fromJson<AddressEntity>(string, type)

    }

    @TypeConverter
    fun toCompanyEntityToString(addressEntity: CompanyEntity): String {
        val type = object : TypeToken<CompanyEntity>() {}.type
        return gson.toJson(addressEntity, type)
    }

    @TypeConverter
    fun fromStringToCompanyEntity(string: String): CompanyEntity {
        val type = object : TypeToken<CompanyEntity>() {}.type
        return gson.fromJson<CompanyEntity>(string, type)
    }

    @TypeConverter
    fun fromStringToGeoEntity(string: String): GeoEntity {
        val type = object : TypeToken<GeoEntity>() {}.type
        return gson.fromJson<GeoEntity>(string, type)
    }


    @TypeConverter
    fun toGeoEntityToString(addressEntity: GeoEntity): String {
        val type = object : TypeToken<GeoEntity>() {}.type
        return gson.toJson(addressEntity, type)
    }

}