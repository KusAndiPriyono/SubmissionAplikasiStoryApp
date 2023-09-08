package com.example.submissionaplikasistoryapp.view.main.liststory

import android.annotation.SuppressLint
import android.util.Log
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.AsyncPagingDataDiffer
import androidx.paging.PagingData
import androidx.paging.PagingSource
import androidx.paging.PagingState
import androidx.recyclerview.widget.ListUpdateCallback
import com.example.submissionaplikasistoryapp.data.StoryRepository
import com.example.submissionaplikasistoryapp.data.database.ListStoryItem
import com.example.submissionaplikasistoryapp.utils.DataDummy
import com.example.submissionaplikasistoryapp.utils.MainDispatcherRule
import com.example.submissionaplikasistoryapp.utils.getOrAwaitValue
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class HomeStoryViewModelTest {
    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val mainDispatcherRules = MainDispatcherRule()

    @Mock
    private lateinit var storyRepository: StoryRepository

    private lateinit var homeStoryViewModel: HomeStoryViewModel

    @SuppressLint("CheckResult")
    @Test
    fun `when Get Stories Should Not Null and Return Success`() = runTest {
        val dummyAllStoriesResponse = DataDummy.generateDummyStories()
        val data: PagingData<ListStoryItem> =
            StoryPagingStore.snapshot(dummyAllStoriesResponse.listStory)
        val expectedStories = MutableLiveData<PagingData<ListStoryItem>>()
        expectedStories.value = data


        Mockito.mockStatic(Log::class.java)
        Mockito.`when`(storyRepository.getStories()).thenReturn(expectedStories)

        homeStoryViewModel = HomeStoryViewModel(storyRepository)
        val actualStories: PagingData<ListStoryItem> = homeStoryViewModel.stories.getOrAwaitValue()

        val differ = AsyncPagingDataDiffer(
            diffCallback = StoryAdapter.DIFF_CALLBACK,
            updateCallback = noopListUpdateCallback,
            workerDispatcher = Dispatchers.Main,
        )
        differ.submitData(actualStories)

        Assert.assertNotNull(differ.snapshot())
        Assert.assertEquals(dummyAllStoriesResponse.listStory, differ.snapshot())
        Assert.assertEquals(dummyAllStoriesResponse.listStory.size, differ.snapshot().size)
        Assert.assertEquals(dummyAllStoriesResponse.listStory[0].id, differ.snapshot()[0]?.id)

    }

    @Test
    fun `when Get Stories Empty Should Return No Data`() = runTest {
        val data: PagingData<ListStoryItem> = PagingData.from(emptyList())
        val expectedStories = MutableLiveData<PagingData<ListStoryItem>>()
        expectedStories.value = data

        Mockito.`when`(storyRepository.getStories()).thenReturn(expectedStories)
        homeStoryViewModel = HomeStoryViewModel(storyRepository)
        val actualStories: PagingData<ListStoryItem> = homeStoryViewModel.stories.getOrAwaitValue()

        val differ = AsyncPagingDataDiffer(
            diffCallback = StoryAdapter.DIFF_CALLBACK,
            updateCallback = noopListUpdateCallback,
            workerDispatcher = Dispatchers.Main,
        )
        differ.submitData(actualStories)

        Assert.assertTrue(differ.snapshot().isEmpty())
    }

}

class StoryPagingStore : PagingSource<Int, LiveData<List<ListStoryItem>>>() {

    companion object {
        fun snapshot(listStory: List<ListStoryItem>): PagingData<ListStoryItem> {
            return PagingData.from(listStory)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, LiveData<List<ListStoryItem>>>): Int {
        return 0
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, LiveData<List<ListStoryItem>>> {
        return LoadResult.Page(emptyList(), 0, 1)
    }

}

val noopListUpdateCallback = object : ListUpdateCallback {
    override fun onInserted(position: Int, count: Int) {}
    override fun onRemoved(position: Int, count: Int) {}
    override fun onMoved(fromPosition: Int, toPosition: Int) {}
    override fun onChanged(position: Int, count: Int, payload: Any?) {}
}


