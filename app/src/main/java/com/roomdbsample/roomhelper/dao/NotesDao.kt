package com.roomdbsample.roomhelper.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.roomdbsample.roomhelper.entity.Notes
import java.util.Date

@Dao
interface NotesDao {

    @Insert
    suspend fun insert(user:Notes)

    @Query("UPDATE notes SET title=:nameValue,updatedAt=:updatedAtValue WHERE id=:id")
    suspend fun updateInfo(nameValue:String,updatedAtValue:Date,id:Long)

    @Query("DELETE FROM notes WHERE id = :id")
    suspend fun deleteUser(id:Long)

    @Query("SELECT * FROM notes")
    suspend fun getAllUser():List<Notes>

    @Query("SELECT * FROM notes WHERE id = :id")
    suspend fun getSingleUser(id:Long): Notes


}