package com.example.submissionaplikasistoryapp.view.auth.login

import androidx.lifecycle.ViewModel
import com.example.submissionaplikasistoryapp.data.StoryRepository

class LoginViewModel(private val storyRepository: StoryRepository) : ViewModel() {
    fun login(email: String, password: String) = storyRepository.postLogin(email, password)
}