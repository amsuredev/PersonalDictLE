package pl.amsuredev.personaldictionary

import android.app.Application
import pl.amsuredev.personaldictionary.postrepo.PostRepo

class PersonalDictionaryApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        PostRepo.initialize(context = applicationContext)
    }
}