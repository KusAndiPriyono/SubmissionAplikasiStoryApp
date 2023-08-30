package com.example.submissionaplikasistoryapp.view.auth.signup

import androidx.lifecycle.ViewModel
import com.example.submissionaplikasistoryapp.data.StoryRepository

class SignupViewModel(private val storyRepository: StoryRepository) : ViewModel() {
    fun signup(name: String, email: String, password: String) =
        storyRepository.postSignup(name, email, password)
}