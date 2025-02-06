package com.app.silky.api

import com.app.silky.model.Users
import retrofit2.Response
import retrofit2.http.GET

interface UserListApi {

    @GET("users")
    suspend fun getUserList(
    ): Response<List<Users>>
}