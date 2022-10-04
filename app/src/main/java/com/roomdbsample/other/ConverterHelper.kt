package com.roomdbsample.other

import java.util.Date
import androidx.room.TypeConverter

open class ConverterHelper {

    @TypeConverter
    fun fromDateToLong(value:Date):Long{
        return value.time
    }

    @TypeConverter
    fun fromLongToDate(value:Long):Date{
        return Date(value)
    }
}