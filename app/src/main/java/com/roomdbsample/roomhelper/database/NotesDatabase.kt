package com.roomdbsample.roomhelper.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.roomdbsample.other.ConverterHelper
import com.roomdbsample.roomhelper.dao.NotesDao
import com.roomdbsample.roomhelper.entity.Notes

@Database(entities = [Notes::class], version = 1)
@TypeConverters(ConverterHelper::class)
abstract class NotesDatabase :RoomDatabase(){

    abstract fun userDao():NotesDao

    companion object{

        @Volatile
        private var INSTANCE:NotesDatabase?=null

        fun getDatabaseInstance(context:Context):NotesDatabase{
            if (INSTANCE==null){
                synchronized(this){
                    INSTANCE= Room.databaseBuilder(context.applicationContext,
                        NotesDatabase::class.java,"NotesDB")
                        .build()
                }
            }
            return INSTANCE!!
        }
    }
}