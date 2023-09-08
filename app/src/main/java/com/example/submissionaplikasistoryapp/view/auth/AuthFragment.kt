package com.example.submissionaplikasistoryapp.view.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.example.submissionaplikasistoryapp.R
import com.example.submissionaplikasistoryapp.databinding.FragmentAuthBinding
import com.example.submissionaplikasistoryapp.utils.Preference


class AuthFragment : Fragment() {

    private var _binding: FragmentAuthBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAuthBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.loginButton.setOnClickListener(
            Navigation.createNavigateOnClickListener(R.id.action_authFragment_to_loginFragment)
        )
        binding.signupButton.setOnClickListener(
            Navigation.createNavigateOnClickListener(R.id.action_authFragment_to_signupFragment)
        )

        val sharedPref = Preference.initPref(requireContext(), SHARED_PREF_KEY)
        val token = sharedPref.getString(TOKEN_KEY, "")

//        if (token != "") {
//            val action = AuthFragmentDirections.actionAuthFragmentToMainActivity()
//            Navigation.findNavController(view).navigate(action)
//        }
        if (token?.isNotEmpty() == true) {
            val action = AuthFragmentDirections.actionAuthFragmentToMainActivity()
            Navigation.findNavController(view).navigate(action)
        }
    }

    companion object {
        const val SHARED_PREF_KEY = "onSignIn"
        const val TOKEN_KEY = "token"
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}