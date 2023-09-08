package com.example.submissionaplikasistoryapp.utils

import com.example.submissionaplikasistoryapp.data.database.ListStoryItem
import com.example.submissionaplikasistoryapp.data.response.stories.AllStoriesResponse

object DataDummy {
    fun generateDummyStories(): AllStoriesResponse {
        val listStory = ArrayList<ListStoryItem>()
        for (i in 1..20) {
            val listStoryItem = ListStoryItem(
                createdAt = "2023-09-05T05:05:05Z",
                description = "Description $i",
                id = "id_$i",
                lat = i.toDouble() * 10,
                lon = i.toDouble() * 10,
                name = "Name $i",
                photoUrl = "https://ichef.bbci.co.uk/onesport/cps/976/cpsprodpb/AA43/production/_130978534_gettyimages-1644160385.jpg"
            )
            listStory.add(listStoryItem)
        }
        return AllStoriesResponse(
            error = false, message = "Stories fetched successfully", listStory = listStory
        )
    }
}