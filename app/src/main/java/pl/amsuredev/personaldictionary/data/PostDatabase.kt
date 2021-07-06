package pl.amsuredev.personaldictionary.data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [Post::class], version=1)
@TypeConverters(PostTypeConverters::class)
abstract class PostDatabase : RoomDatabase() {
    abstract fun postDao(): PostDao
}