package com.nextgen.beritaku.auth.signup

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.nextgen.beritaku.R
import com.nextgen.beritaku.databinding.FragmentSignupBinding
import com.nextgen.beritaku.utils.UiState
import com.nextgen.beritaku.utils.isEmailValid
import org.koin.androidx.viewmodel.ext.android.viewModel

class SignupFragment : Fragment(), View.OnClickListener {

    private var _binding : FragmentSignupBinding? = null
    private val binding get() = _binding
    private val viewModel: SignUpViewModel by viewModel()



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding?.btnSignup?.setOnClickListener(this)
        binding?.tvLogin?.setOnClickListener(this)
        viewModel.uiState.observe(viewLifecycleOwner){state->
            when(state){
                is UiState.Loading -> {
                    Log.d(TAG, "Loading...")
                    binding?.apply {
                        btnSignup.isEnabled = false
                        tvLogin.isEnabled = false
                    }
                }
                is UiState.Error -> {
                    Toast.makeText(requireContext(), state.message, Toast.LENGTH_SHORT).show()
                }
                is UiState.Success -> {
                    val action = SignupFragmentDirections.actionSignupFragmentToLoginFragment()
                    findNavController().navigate(action)
                }
            }
        }


    }


    override fun onClick(view: View?) {
        when(view){
            binding?.btnSignup -> {
                val name = binding?.edtName?.text.toString().trim()
                val email = binding?.etEmail?.text.toString().trim()
                val password = binding?.etPassword?.text.toString().trim()
                val rePassword = binding?.etReTypePass?.text.toString().trim()
                if (validate(name, email, password, rePassword)){
                    viewModel.registerWithEmail(email, password, name)
                }
            }
            binding?.tvLogin -> {
                val action = SignupFragmentDirections.actionSignupFragmentToLoginFragment()
                findNavController().navigate(action)
            }
        }
    }

    private fun validate(
        name: String,
        email: String,
        password: String,
        rePassword: String
    ): Boolean {
        resetAllError()
        if(name.isEmpty()){
            binding?.edtName?.error = getString(R.string.error_et_name)
            return false
        }

        if (email.isEmpty()){
            binding?.etEmail?.error = getString(R.string.error_et_email)
            return false
        }

        if (password.isEmpty() || rePassword.isEmpty()){
            binding?.etPassword?.error = getString(R.string.error_et_password)
            binding?.etReTypePass?.error = getString(R.string.error_et_password)
            return false
        }

        if (password != rePassword){
            binding?.etPassword?.error = getString(R.string.error_pass_not_match)
            return false
        }

        if (!email.isEmailValid()){
            binding?.etEmail?.error = getString(R.string.error_email_not_valid)
            return false
        }
        return true
    }

    private fun resetAllError() {
        binding?.apply {
            edtName.error = null
            etEmail.error = null
            etPassword.error = null
            etReTypePass.error = null
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSignupBinding.inflate(layoutInflater)
        return binding?.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        const val TAG = "SignUpFragment"
    }
}