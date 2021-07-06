package pl.amsuredev.personaldictionary

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import pl.amsuredev.personaldictionary.databinding.ActivitySplashScreenBinding


class SplashScreenActivity : AppCompatActivity() {
    var _bind: ActivitySplashScreenBinding? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _bind = ActivitySplashScreenBinding.inflate(layoutInflater)
        setContentView(_bind?.root)
        _bind?.dict?.alpha = 0F
        _bind?.dict?.animate()?.setDuration(1500)?.alpha(1F)?.withEndAction {
            startActivity(Intent(baseContext, MainActivity::class.java))
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
            finish()
        }

    }

    override fun onDestroy() {
        _bind = null
        super.onDestroy()
    }
}