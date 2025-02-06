package com.app.silky.di

import com.app.silky.db.entity.*
import com.app.silky.model.*

object Transformer {

    fun convertUserModelToUserEntity(user: Users): UserEntity {
        return UserEntity(
            name = user.name,
            username = user.username,
            email = user.email,
            phone = user.phone,
            website = user.website,
            userid = user.id,
            address = AddressEntity(user.address.street,user.address.suite,user.address.city,user.address.zipcode,
                GeoEntity(user.address.geo.lat,user.address.geo.lng)),
            company = CompanyEntity(user.company.name,user.company.catchPhrase,user.company.bs)
        )
    }

    fun convertUserEntityToUserModelModel(userEntity: UserEntity): Users {
        return Users(
            id = userEntity.id,
            email = userEntity.email,
            phone = userEntity.phone,
            website = userEntity.website,
            username = userEntity.username,
            name = userEntity.name,
            address = Address(userEntity.address.street,userEntity.address.city, userEntity.address.suite, userEntity.address.zipcode,
                Geo(userEntity.address.geo.lat,userEntity.address.geo.lng)),
            company = Company(userEntity.company.name,userEntity.company.catchPhrase,userEntity.company.bs)
        )
    }
}