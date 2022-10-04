package com.roomdbsample.roomhelper.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "notes")
data class Notes(
    @PrimaryKey(autoGenerate = true)
    var id:Long,
    var title:String,
    var description:String,
    var createdAt:Date,
    var updatedAt:Date

)
