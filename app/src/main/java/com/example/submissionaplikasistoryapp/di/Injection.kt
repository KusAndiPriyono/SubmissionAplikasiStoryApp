package com.example.submissionaplikasistoryapp.di

import android.content.Context
import com.example.submissionaplikasistoryapp.data.StoryRepository
import com.example.submissionaplikasistoryapp.data.retrofit.ApiConfig
import com.example.submissionaplikasistoryapp.utils.Preference
import kotlinx.coroutines.runBlocking

object Injection {

    fun provideRepository(context: Context): StoryRepository {
        val pref = Preference.initPref(context, "onSignIn")
        val user = runBlocking { pref.getString("token", null).toString() }
        val apiService = ApiConfig.getApiService(user)
        return StoryRepository(apiService)
    }

}