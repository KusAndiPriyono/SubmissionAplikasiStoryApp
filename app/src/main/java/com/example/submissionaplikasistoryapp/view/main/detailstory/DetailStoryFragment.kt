package com.example.submissionaplikasistoryapp.view.main.detailstory

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.Target
import com.example.submissionaplikasistoryapp.databinding.FragmentDetailStoryBinding
import com.example.submissionaplikasistoryapp.utils.ViewModelFactory
import com.example.submissionaplikasistoryapp.view.main.liststory.HomeStoryViewModel
import java.text.SimpleDateFormat
import java.util.Locale


class DetailStoryFragment : Fragment() {

    private var _binding: FragmentDetailStoryBinding? = null
    private val binding get() = _binding!!

    private val homeStoryViewModel: HomeStoryViewModel by viewModels {
        ViewModelFactory(requireActivity())
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailStoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val args: DetailStoryFragmentArgs by navArgs()
        val story = args.story

        val transitionNamesMap = homeStoryViewModel.transitionNames.value
        if (transitionNamesMap != null) {
            binding.ivDetailStory.transitionName = transitionNamesMap["image_${story.id}"]
            binding.tvDetailStoryName.transitionName = transitionNamesMap["name_${story.name}"]
            binding.tvDetailStoryDate.transitionName = transitionNamesMap["date_${story.createdAt}"]
            binding.tvDetailStoryDesc.transitionName =
                transitionNamesMap["desc_${story.description}"]
        }

        Glide.with(binding.ivDetailStory.context)
            .load(story.photoUrl)
            .apply(RequestOptions.centerCropTransform())
            .listener(object : RequestListener<Drawable> {
                override fun onLoadFailed(
                    e: GlideException?,
                    model: Any?,
                    target: Target<Drawable>?,
                    isFirstResource: Boolean
                ): Boolean {
                    startPostponedEnterTransition()
                    return false
                }

                override fun onResourceReady(
                    resource: Drawable?,
                    model: Any?,
                    target: Target<Drawable>?,
                    dataSource: DataSource?,
                    isFirstResource: Boolean
                ): Boolean {
                    startPostponedEnterTransition()
                    return false
                }

            })
            .into(binding.ivDetailStory)

        binding.tvDetailStoryName.text = story.name

        val originalDateFormat =
            SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
        val targetDateFormat = SimpleDateFormat("dd MMMM yyyy", Locale.getDefault())
        val originalDate = originalDateFormat.parse(story.createdAt)
        val formattedDate = targetDateFormat.format(originalDate!!)
        binding.tvDetailStoryDate.text = formattedDate
        binding.tvDetailStoryDesc.text = story.description

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}