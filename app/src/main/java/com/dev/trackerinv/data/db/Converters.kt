package com.dev.trackerinv.data.db
import androidx.room.TypeConverter
import com.dev.trackerinv.data.model.GST
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class Converters {
    @TypeConverter
    fun fromGst(gst: GST): String = Gson().toJson(gst)

    @TypeConverter
    fun toGst(gstString: String): GST = Gson().fromJson(gstString, object : TypeToken<GST>() {}.type)
}
