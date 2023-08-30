package com.example.submissionaplikasistoryapp.view.main

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import com.example.submissionaplikasistoryapp.R
import com.example.submissionaplikasistoryapp.databinding.ActivityMainBinding
import com.example.submissionaplikasistoryapp.utils.Preference
import com.example.submissionaplikasistoryapp.view.auth.AuthActivity

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.bottomAppBar.setNavigationOnClickListener {
            // Handle navigation icon press

        }

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_activity_main_fragment) as NavHostFragment
        val navController = navHostFragment.navController

        binding.bottomAppBar.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.app_bar_home -> {
                    navController.navigate(R.id.homeStoryFragment)
                    true
                }

                R.id.app_bar_profile -> {
                    navController.navigate(R.id.profileFragment)
                    true
                }

                R.id.action_logout -> {
                    Preference.logOut(this)
                    val intent = Intent(
                        this,
                        AuthActivity::class.java
                    ).addFlags(
                        Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
                    )
                    startActivity(intent)
                    finish()
                    true
                }

                else -> false
            }
        }
    }
}
