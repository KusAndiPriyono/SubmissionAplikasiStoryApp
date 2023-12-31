package com.example.submissionaplikasistoryapp.view.auth.signup

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
import com.example.submissionaplikasistoryapp.data.Result
import com.example.submissionaplikasistoryapp.databinding.FragmentSignupBinding
import com.example.submissionaplikasistoryapp.utils.ViewModelFactory
import com.example.submissionaplikasistoryapp.view.customview.CustomButton
import com.example.submissionaplikasistoryapp.view.customview.CustomEditText


class SignupFragment : Fragment() {

    private lateinit var customNameEditText: CustomEditText
    private lateinit var customEmailEditText: CustomEditText
    private lateinit var customPasswordEditText: CustomEditText
    private lateinit var customButton: CustomButton


    private var _binding: FragmentSignupBinding? = null
    private val binding get() = _binding!!

    private val signupViewModel: SignupViewModel by viewModels {
        ViewModelFactory(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSignupBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        customNameEditText = binding.edRegisterName
        customEmailEditText = binding.edRegisterEmail
        customPasswordEditText = binding.edRegisterPassword
        customButton = binding.signupButton

        setCustomButtonEnable()

        customNameEditText.addTextChangedListener(onTextChanged = { _, _, _, _ ->
            setCustomButtonEnable()
        })
        customEmailEditText.addTextChangedListener(onTextChanged = { _, _, _, _ ->
            setCustomButtonEnable()
        })
        customPasswordEditText.addTextChangedListener(onTextChanged = { _, _, _, _ ->
            setCustomButtonEnable()
        })

        binding.signupButton.setOnClickListener { it ->
            val name = binding.edRegisterName.text.toString()
            val email = binding.edRegisterEmail.text.toString()
            val password = binding.edRegisterPassword.text.toString()

            val imm = activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(it.windowToken, 0)

            signupViewModel.signup(name, email, password).observe(requireActivity()) {
                if (it != null) {
                    when (it) {
                        is Result.Loading -> {
                            showLoading(true)
                        }

                        is Result.Success -> {
                            showLoading(false)
                            Toast.makeText(context, "Berhasil Sign Up", Toast.LENGTH_LONG).show()
                            findNavController().navigate(
                                SignupFragmentDirections.actionSignupFragmentToLoginFragment()
                            )
                        }

                        is Result.Error -> {
                            showLoading(false)
                            Toast.makeText(context, it.error, Toast.LENGTH_LONG).show()
                        }
                    }
                }
            }
        }
    }


    private fun showLoading(isLoading: Boolean) {
        binding.progressBarSignup.visibility = if (isLoading) View.VISIBLE else View.GONE

    }

    private fun setCustomButtonEnable() {
        binding.signupButton.isEnabled = customNameEditText.text!!.isNotEmpty() &&
                customEmailEditText.text!!.isNotEmpty() &&
                customPasswordEditText.text!!.isNotEmpty()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}