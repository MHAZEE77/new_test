package com.app.silky.di

import com.app.silky.api.UserListApi
import com.app.silky.model.Users
import retrofit2.Response
import javax.inject.Inject

class NetworkRepository @Inject constructor(
    val userListApi: UserListApi
) {

    suspend fun getUserList(): Response<List<Users>> {
        return userListApi.getUserList()
    }

}