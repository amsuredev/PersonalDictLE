package pl.amsuredev.personaldictionary.viewmodels

import androidx.lifecycle.ViewModel
import pl.amsuredev.personaldictionary.data.Post
import pl.amsuredev.personaldictionary.postrepo.PostRepo

class PostListViewModel : ViewModel() {
    private val postRepo = PostRepo.get()
    val postsLiveData = postRepo.getPosts()
    fun addPost(post: Post) {
        postRepo.addPost(post)
    }
}