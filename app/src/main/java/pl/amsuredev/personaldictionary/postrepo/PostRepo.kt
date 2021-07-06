package pl.amsuredev.personaldictionary.postrepo

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.Room
import pl.amsuredev.personaldictionary.data.Post
import pl.amsuredev.personaldictionary.data.PostDatabase
import java.util.*
import java.util.concurrent.Executors

private const val DB_NAME = "postdb"
class PostRepo private constructor (context: Context) {
    private val executor = Executors.newSingleThreadExecutor()

    private val database: PostDatabase = Room.databaseBuilder(
        context.applicationContext,
        PostDatabase::class.java,
        DB_NAME
    ).build()

    val postDao = database.postDao()

    fun getPosts(): LiveData<List<Post>> {
        return postDao.getPosts()
    }

    fun getPost(id: UUID): LiveData<Post> {
        return postDao.getPost(id)
    }

    fun addPost(post: Post){
        executor.execute {
            postDao.addPost(post)
        }
    }

    fun updatePost(post: Post){
        executor.execute {
            postDao.updatePost(post)
        }
    }

    companion object{
        private var INSTANCE: PostRepo? = null

        fun initialize(context: Context){
            if (INSTANCE == null){
                INSTANCE = PostRepo(context)
            }
        }

        fun get(): PostRepo{
            return INSTANCE ?:
            throw IllegalStateException("Post repo nust be initialized")
        }
    }
}