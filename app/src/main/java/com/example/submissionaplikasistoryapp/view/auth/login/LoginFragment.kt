package com.example.submissionaplikasistoryapp.view.auth.login

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.submissionaplikasistoryapp.R
import com.example.submissionaplikasistoryapp.data.Result
import com.example.submissionaplikasistoryapp.data.response.login.LoginResponse
import com.example.submissionaplikasistoryapp.databinding.FragmentLoginBinding
import com.example.submissionaplikasistoryapp.utils.Preference
import com.example.submissionaplikasistoryapp.utils.ViewModelFactory
import com.example.submissionaplikasistoryapp.view.customview.CustomButton
import com.example.submissionaplikasistoryapp.view.customview.CustomEditText


class LoginFragment : Fragment() {

    private lateinit var customEmailEditText: CustomEditText
    private lateinit var customPasswordEditText: CustomEditText
    private lateinit var customButton: CustomButton

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    private val loginViewModel: LoginViewModel by viewModels {
        ViewModelFactory(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        customPasswordEditText = binding.edLoginPassword
        customButton = binding.loginButton
        customEmailEditText = binding.edLoginEmail

        setCustomButtonEnable()

        customEmailEditText.addTextChangedListener(onTextChanged = { _, _, _, _ ->
            setCustomButtonEnable()
        })
        customPasswordEditText.addTextChangedListener(onTextChanged = { _, _, _, _ ->
            setCustomButtonEnable()
        })

        binding.loginButton.setOnClickListener {
            val email = binding.edLoginEmail.text.toString()
            val password = binding.edLoginPassword.text.toString()

            val imm = activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(it.windowToken, 0)

            loginViewModel.login(email, password).observe(requireActivity()) { result ->
                if (result != null) {
                    when (result) {
                        is Result.Loading -> {
                            showLoading(true)
                        }

                        is Result.Success -> {
                            loginProcess(result.data)
                            showLoading(false)
                        }

                        is Result.Error -> {
                            showLoading(false)
                            Toast.makeText(requireContext(), result.error, Toast.LENGTH_LONG).show()
                        }
                    }
                }
            }
        }
    }

    private fun loginProcess(data: LoginResponse) {
        if (data.error) {
            Toast.makeText(requireContext(), data.message, Toast.LENGTH_LONG).show()
        } else {
            Preference.saveToken(data.loginResult.token, requireContext())
            findNavController().navigate(R.id.action_loginFragment_to_mainActivity)
            requireActivity().finish()
        }

    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBarLogin.visibility = if (isLoading) View.VISIBLE else View.GONE

    }

    private fun setCustomButtonEnable() {
        binding.loginButton.isEnabled = customPasswordEditText.text.toString()
            .isNotEmpty() && customEmailEditText.text.toString().isNotEmpty()
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}