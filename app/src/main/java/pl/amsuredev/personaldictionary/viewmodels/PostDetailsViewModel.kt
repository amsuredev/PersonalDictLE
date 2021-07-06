package pl.amsuredev.personaldictionary.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import pl.amsuredev.personaldictionary.data.Post
import pl.amsuredev.personaldictionary.postrepo.PostRepo
import java.util.*

class PostDetailsViewModel : ViewModel() {
    private val postRepo = PostRepo.get()
    private val crimeIdLiveData = MutableLiveData<UUID>()

    fun loadCrime(crimeId: UUID) {
        crimeIdLiveData.value = crimeId
    }

    fun updatePost(post: Post) {
        postRepo.updatePost(post)
    }

    var postLiveData: LiveData<Post> =
        Transformations.switchMap(crimeIdLiveData) {
            postRepo.getPost(it)
        }

}