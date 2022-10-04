package com.roomdbsample.roomhelper.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "user")
data class User(
    @PrimaryKey(autoGenerate = true)
    var id:Long,
    var name:String,
    var phone:String,
    var createdAt:Date,
    var updatedAt:Date

)
