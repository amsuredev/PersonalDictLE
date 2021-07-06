package pl.amsuredev.personaldictionary.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import java.util.*

@Dao
interface PostDao {
    @Query("SELECT * FROM post")
    fun getPosts(): LiveData<List<Post>>

    @Query("SELECT * FROM post WHERE id=(:id)")
    fun getPost(id: UUID):LiveData<Post>

    @Update
    fun updatePost(post: Post)

    @Insert
    fun addPost(post: Post)
}