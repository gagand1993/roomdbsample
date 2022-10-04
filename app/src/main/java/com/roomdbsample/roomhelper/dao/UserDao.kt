package com.roomdbsample.roomhelper.dao

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.roomdbsample.roomhelper.entity.User
import java.util.Date

@Dao
interface UserDao {

    @Insert
    suspend fun insert(user:User)

    @Query("UPDATE user SET name=:nameValue,updatedAt=:updatedAtValue WHERE id=:id")
    suspend fun updateInfo(nameValue:String,updatedAtValue:Date,id:Long)

    @Query("DELETE FROM user WHERE id = :id")
    suspend fun deleteUser(id:Long)

    @Query("SELECT * FROM user")
    suspend fun getAllUser():List<User>

    @Query("SELECT * FROM user WHERE id = :id")
    suspend fun getSingleUser(id:Long): User


}