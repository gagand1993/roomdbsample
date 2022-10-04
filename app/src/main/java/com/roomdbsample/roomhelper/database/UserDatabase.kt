package com.roomdbsample.roomhelper.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.roomdbsample.other.ConverterHelper
import com.roomdbsample.roomhelper.dao.UserDao
import com.roomdbsample.roomhelper.entity.User

@Database(entities = [User::class], version = 1)
@TypeConverters(ConverterHelper::class)
abstract class UserDatabase :RoomDatabase(){

    abstract fun userDao():UserDao

    companion object{

        @Volatile
        private var INSTANCE:UserDatabase?=null

        fun getDatabaseInstance(context:Context):UserDatabase{
            if (INSTANCE==null){
                synchronized(this){
                    INSTANCE= Room.databaseBuilder(context.applicationContext,
                        UserDatabase::class.java,"RoomDbSample")
                        .build()
                }
            }
            return INSTANCE!!
        }
    }
}