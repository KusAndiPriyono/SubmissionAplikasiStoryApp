package com.example.submissionaplikasistoryapp.view.main.detailstory

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.transition.TransitionInflater
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.Target
import com.example.submissionaplikasistoryapp.data.database.ListStoryItem
import com.example.submissionaplikasistoryapp.databinding.FragmentDetailStoryBinding
import com.example.submissionaplikasistoryapp.utils.DateFormatter
import java.util.TimeZone


class DetailStoryFragment : Fragment() {

    private var _binding: FragmentDetailStoryBinding? = null
    private val binding get() = _binding!!


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

        sharedElementEnterTransition = TransitionInflater.from(requireContext())
            .inflateTransition(android.R.transition.move)

        binding.listStoryItem = ListStoryItem(
            id = arguments?.getString(ARG_ID) ?: "",
            photoUrl = arguments?.getString(ARG_PHOTO_URL) ?: "",
            name = arguments?.getString(ARG_NAME) ?: "",
            createdAt = arguments?.getString(ARG_CREATED_AT) ?: "",
            description = arguments?.getString(ARG_DESCRIPTION) ?: "",
            lat = arguments?.getDouble(ARG_LAT) ?: 0.0,
            lon = arguments?.getDouble(ARG_LON) ?: 0.0
        )

        val progressBar = binding.progressBarDetail

        binding.tvDetailStoryDate.text = DateFormatter.formatDate(
            arguments?.getString("createdAt") ?: "",
            TimeZone.getDefault().id
        )

        Glide.with(this)
            .load(arguments?.getString("photo_url"))
            .apply(
                RequestOptions()
                    .override(350, 550)
                    .placeholder(android.R.color.darker_gray)
                    .error(android.R.color.holo_red_light)
            )
            .listener(object : RequestListener<Drawable> {
                override fun onLoadFailed(
                    p0: GlideException?,
                    p1: Any?,
                    p2: Target<Drawable>?,
                    p3: Boolean
                ): Boolean {
                    startPostponedEnterTransition()
                    progressBar.visibility = View.GONE
                    return false
                }

                override fun onResourceReady(
                    p0: Drawable?,
                    p1: Any?,
                    p2: Target<Drawable>?,
                    p3: DataSource?,
                    p4: Boolean
                ): Boolean {
                    startPostponedEnterTransition()
                    progressBar.visibility = View.GONE
                    return false
                }

            })
            .into(binding.ivDetailStory)


        binding.executePendingBindings()

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        const val ARG_ID = "id"
        const val ARG_PHOTO_URL = "photo_url"
        const val ARG_NAME = "name"
        const val ARG_CREATED_AT = "createdAt"
        const val ARG_DESCRIPTION = "description"
        const val ARG_LAT = "lat"
        const val ARG_LON = "lon"
    }
}