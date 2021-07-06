package pl.amsuredev.personaldictionary.data

import android.net.Uri
import androidx.room.TypeConverter
import java.util.*

class PostTypeConverters {
    @TypeConverter
    fun fromDate(date: Date?): Long? {
        return date?.time
    }

    @TypeConverter
    fun toDate(millisSinceEpoch: Long?): Date? {
        return millisSinceEpoch?.let {
            Date(it)
        }
    }

    @TypeConverter
    fun toURI(uri: Uri): String?{
        return uri.toString()
    }

    @TypeConverter
    fun fromURI(uri: String?): Uri?{
        return Uri.parse(uri)
    }

    @TypeConverter
    fun toUUID(uuid: String?): UUID?{
        return UUID.fromString(uuid)
    }

    @TypeConverter
    fun fromUUID(uuid: UUID?): String?{
        return uuid?.toString()
    }
}