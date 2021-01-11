package com.blood.jetpackpaging3.paging.db

import androidx.room.TypeConverter
import com.blood.jetpackpaging3.paging.bean.Tag
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import java.util.*

class Converters {

    @TypeConverter
    fun fromTimestamp(value: Long?): Date? {
        return if (value == null) null else Date(value)
    }

    @TypeConverter
    fun dateToTimestamp(date: Date?): Long? {
        return date?.time
    }

    @TypeConverter
    fun json2STagList(str: String): List<Tag> {
        return GsonBuilder().create().fromJson(str, object : TypeToken<List<Tag>>() {}.type)
    }

    @TypeConverter
    fun tagList2Json(data: List<Tag>): String {
        return GsonBuilder().create().toJson(data)
    }

}