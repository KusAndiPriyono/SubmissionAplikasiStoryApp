package com.example.submissionaplikasistoryapp.view.main.liststory

import android.os.Bundle
import android.transition.TransitionInflater
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.submissionaplikasistoryapp.R
import com.example.submissionaplikasistoryapp.databinding.FragmentHomeStoryBinding
import com.example.submissionaplikasistoryapp.utils.ViewModelFactory

class HomeStoryFragment : Fragment(R.layout.item_story) {

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
        sharedElementReturnTransition =
            TransitionInflater.from(context).inflateTransition(android.R.transition.move)

        homeStoryViewModel.stories.observe(viewLifecycleOwner) { data ->
            if (data != null) {
                adapter.submitData(viewLifecycleOwner.lifecycle, data)
            }
        }

        setupAdapter()

    }

    private fun setupAdapter() {
        adapter = StoryAdapter { story, imageView, nameView, dateView, descView ->
            val action =
                HomeStoryFragmentDirections.actionHomeStoryFragmentToDetailStoryFragment(
                    id = story.id,
                    photoUrl = story.photoUrl,
                    name = story.name,
                    createdAt = story.createdAt,
                    description = story.description,
                )
            val extras = FragmentNavigatorExtras(
                imageView to imageView.transitionName,
                nameView to nameView.transitionName,
                dateView to dateView.transitionName,
                descView to descView.transitionName
            )
            findNavController().navigate(action, extras)
        }
        binding.rvStory.adapter = adapter
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}