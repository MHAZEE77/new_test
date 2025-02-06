package com.app.silky.db.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.app.silky.db.entity.UserEntity

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(userEntity: UserEntity):Long

    @Query("SELECT * FROM USERS")
    fun getAllOfflineArticles():LiveData<List<UserEntity>>

    @Delete
    suspend fun delete(userEntity: UserEntity)
}