package com.example.submissionaplikasistoryapp.view.main.createstory

import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.net.toFile
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.example.submissionaplikasistoryapp.R
import com.example.submissionaplikasistoryapp.data.Result
import com.example.submissionaplikasistoryapp.databinding.FragmentCreateStoryBinding
import com.example.submissionaplikasistoryapp.utils.ViewModelFactory
import com.example.submissionaplikasistoryapp.utils.reduceFileImage
import com.example.submissionaplikasistoryapp.utils.rotateFile
import com.example.submissionaplikasistoryapp.utils.uriToFile
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File


class CreateStoryFragment : Fragment() {
    private var getFile: File? = null
    private var _binding: FragmentCreateStoryBinding? = null
    private val binding get() = _binding!!
    private val createStoryViewModel: CreateStoryViewModel by viewModels {
        ViewModelFactory(requireActivity())
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCreateStoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    @Suppress("DEPRECATION")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnCamera.setOnClickListener(Navigation.createNavigateOnClickListener(R.id.cameraFragment))
        binding.btnGallery.setOnClickListener { startGallery() }
        binding.btnAdd.setOnClickListener { uploadImage() }

        val fileUri = arguments?.get(ARG_SELECTED_IMAGE)
        if (fileUri != null) {
            val uri: Uri = fileUri as Uri
            val isBackCamera = arguments?.get(ARG_IS_BACK_CAMERA) as Boolean
            val result = rotateFile(BitmapFactory.decodeFile(uri.path), isBackCamera)
            getFile = uri.toFile()
            binding.ivPreviewImage.setImageBitmap(result)
        }
    }

    private fun startGallery() {
        val intent = Intent()
        intent.action = Intent.ACTION_GET_CONTENT
        intent.type = "image/*"
        val chooser = Intent.createChooser(intent, "Choose a Picture")
        launcherIntentGallery.launch(chooser)
    }

    private val launcherIntentGallery =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == AppCompatActivity.RESULT_OK) {
                val selectedImg: Uri = result.data?.data as Uri
                selectedImg.let { uri ->
                    val myFile = uriToFile(uri, requireContext())
                    getFile = myFile
                    binding.ivPreviewImage.setImageURI(uri)
                }
            }
        }

    private fun uploadImage() {
        if (getFile != null) {
            showLoading(true)
            val file = reduceFileImage(getFile as File)
            val descriptionText = binding.edAddDescription.text
            if (!descriptionText.isNullOrEmpty()) {
                val description =
                    descriptionText.toString().toRequestBody("text/plain".toMediaType())
                val requestImage = file.asRequestBody("image/*".toMediaTypeOrNull())
                val imageMultiPart: MultipartBody.Part =
                    MultipartBody.Part.createFormData("photo", file.name, requestImage)
                createStoryViewModel.postStory(imageMultiPart, description)
                    .observe(viewLifecycleOwner) {
                        if (it != null) {
                            when (it) {
                                is Result.Success -> {
                                    showLoading(false)
                                    Toast.makeText(context, it.data.message, Toast.LENGTH_SHORT)
                                        .show()
                                    findNavController().navigate(CreateStoryFragmentDirections.actionCreateStoryFragmentToHomeStoryFragment())
                                }

                                is Result.Loading -> {
                                    showLoading(true)
                                }

                                is Result.Error -> {
                                    showLoading(false)
                                    Toast.makeText(context, it.error, Toast.LENGTH_SHORT).show()
                                }
                            }
                        }
                    }
            } else {
                Toast.makeText(context, "Deskripsi tidak boleh kosong", Toast.LENGTH_SHORT).show()
            }

        } else {
            Toast.makeText(context, "Gambar tidak boleh kosong", Toast.LENGTH_SHORT).show()
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBarCreateStory.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        const val ARG_SELECTED_IMAGE = "selected_image"
        const val ARG_IS_BACK_CAMERA = "isBackCamera"
    }

}