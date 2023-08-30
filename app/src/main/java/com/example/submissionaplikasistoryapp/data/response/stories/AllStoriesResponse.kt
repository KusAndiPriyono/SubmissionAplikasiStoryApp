package com.example.submissionaplikasistoryapp.data.response.stories

import com.example.submissionaplikasistoryapp.data.database.ListStoryItem
import com.google.gson.annotations.SerializedName

data class AllStoriesResponse(

    @field:SerializedName("listStory")
    val listStory: List<ListStoryItem>,

    @field:SerializedName("error")
    val error: Boolean,

    @field:SerializedName("message")
    val message: String
)

