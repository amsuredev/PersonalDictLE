package pl.amsuredev.personaldictionary

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.fragment.app.FragmentActivity
import androidx.navigation.NavHostController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import pl.amsuredev.personaldictionary.databinding.ActivityMainBinding
import pl.amsuredev.personaldictionary.databinding.ActivitySplashScreenBinding
import pl.amsuredev.personaldictionary.ui.ListPostsFragment
import java.util.*
private const val UUID_TAG = "UUID"
class MainActivity : AppCompatActivity(), ListPostsFragment.CallBacks{
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setUpBottomNav()
    }

    private fun setUpBottomNav(){
        val bottomNav = findViewById<BottomNavigationView>(R.id.bottomNavigationView)
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.controller) as NavHostFragment
        val navController = navHostFragment.navController
        bottomNav.setupWithNavController(navController)
    }

    override fun onItemSelected(postId: UUID) {
        val intent = Intent(baseContext, PostActivity::class.java)
        intent.putExtra(UUID_TAG, postId)
        startActivity(intent)
    }
}