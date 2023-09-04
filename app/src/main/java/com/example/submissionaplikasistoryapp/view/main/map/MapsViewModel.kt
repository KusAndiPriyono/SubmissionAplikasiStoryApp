package com.example.submissionaplikasistoryapp.view.main.map

import androidx.lifecycle.ViewModel
import com.example.submissionaplikasistoryapp.data.StoryRepository

class MapsViewModel(private val storyRepository: StoryRepository) : ViewModel() {
    fun getStoriesWithLocation() = storyRepository.getStoryWithLocation()
}