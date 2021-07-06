package pl.amsuredev.personaldictionary.data

import android.net.Uri
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity
data class Post(
    @PrimaryKey val id: UUID = UUID.randomUUID(),
    var date: Date = Date(),
    var title: String = "",
    val image: Uri = Uri.EMPTY,
    var liked: Boolean = false
)
