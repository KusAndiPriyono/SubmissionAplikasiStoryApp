package com.example.submissionaplikasistoryapp.utils

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.submissionaplikasistoryapp.di.Injection
import com.example.submissionaplikasistoryapp.view.auth.login.LoginViewModel
import com.example.submissionaplikasistoryapp.view.auth.signup.SignupViewModel
import com.example.submissionaplikasistoryapp.view.main.createstory.CreateStoryViewModel
import com.example.submissionaplikasistoryapp.view.main.liststory.HomeStoryViewModel

class ViewModelFactory(private val context: Context) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(SignupViewModel::class.java) -> {
                SignupViewModel(Injection.provideRepository(context)) as T
            }

            modelClass.isAssignableFrom(LoginViewModel::class.java) -> {
                LoginViewModel(Injection.provideRepository(context)) as T
            }

            modelClass.isAssignableFrom(HomeStoryViewModel::class.java) -> {
                HomeStoryViewModel(Injection.provideRepository(context)) as T
            }

            modelClass.isAssignableFrom(CreateStoryViewModel::class.java) -> {
                CreateStoryViewModel(Injection.provideRepository(context)) as T
            }

            else -> throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
        }
    }
}