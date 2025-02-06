package com.app.silky.di

import androidx.lifecycle.LiveData
import com.app.silky.db.AppDatabase
import com.app.silky.db.entity.UserEntity
import com.app.silky.di.Transformer.convertUserModelToUserEntity
import com.app.silky.model.Users
import javax.inject.Inject

class DBRepository @Inject constructor(val appDatabase: AppDatabase) {

    suspend fun insertArticle(user: Users): Long {
        return appDatabase.articleDao()
            .insert(convertUserModelToUserEntity(user))
    }

    suspend fun delete(user: Users) {
        appDatabase.articleDao().delete(convertUserModelToUserEntity(user))
    }

    // NOTE - Since we are already using LIVE-DATA no need to use suspend function
    fun getAllArticles(): LiveData<List<UserEntity>> {
        return appDatabase.articleDao().getAllOfflineArticles()
    }


}