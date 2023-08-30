package com.example.submissionaplikasistoryapp.view.main.liststory

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.submissionaplikasistoryapp.databinding.FragmentHomeStoryBinding
import com.example.submissionaplikasistoryapp.utils.ViewModelFactory

class HomeStoryFragment : Fragment() {

    private var _binding: FragmentHomeStoryBinding? = null
    private val binding get() = _binding!!

    private lateinit var adapter: StoryAdapter

    private val homeStoryViewModel: HomeStoryViewModel by viewModels {
        ViewModelFactory(requireActivity())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeStoryBinding.inflate(inflater, container, false)
        val layoutManager = LinearLayoutManager(context)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        binding.rvStory.layoutManager = layoutManager
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        homeStoryViewModel.stories.observe(viewLifecycleOwner) { data ->
            if (data != null) {
                adapter.submitData(viewLifecycleOwner.lifecycle, data)
            }
        }

        setupAdapter()
    }

    private fun setupAdapter() {
        adapter = StoryAdapter { story ->
            val action =
                HomeStoryFragmentDirections.actionHomeStoryFragmentToDetailStoryFragment(
                    story
                )

            val transitionNamesMap = mapOf(
                "image_${story.id}" to story.photoUrl,
                "name_${story.name}" to story.name,
                "date_${story.createdAt}" to story.createdAt,
                "desc_${story.description}" to story.description
            )
            homeStoryViewModel.transitionNames.value = transitionNamesMap

            findNavController().navigate(action)
        }
        binding.rvStory.adapter = adapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}