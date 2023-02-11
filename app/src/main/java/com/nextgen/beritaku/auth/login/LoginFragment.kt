package com.nextgen.beritaku.auth.login

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.nextgen.beritaku.R
import com.nextgen.beritaku.databinding.FragmentLoginBinding
import com.nextgen.beritaku.utils.isEmailValid
import org.koin.androidx.viewmodel.ext.android.viewModel

class LoginFragment : Fragment(), View.OnClickListener {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!
    private val viewModel: LoginViewModel by viewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnLogin.setOnClickListener(this)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
       const val TAG = "LoginFragment"
    }

    override fun onClick(p0: View?) {
        when(p0){
            binding.btnLogin -> {
                val email = binding.etEmail.text.toString().trim()
                val password = binding.etPassword.text.toString().trim()
                if (validate(email, password)){
                    val action = LoginFragmentDirections.actionLoginFragmentToHomeNavigation()
                    findNavController().navigate(action)
                }
            }
        }

    }

    private fun validate(email: String, password: String): Boolean {
        if (!email.isEmailValid()){
            binding.etEmail.error = getString(R.string.error_email_not_valid)
            return false
        }
        if (email.isEmpty()){
            binding.etEmail.error = getString(R.string.error_et_email)
            return false
        }
        if (password.isEmpty()){
            binding.etPassword.error = getString(R.string.error_et_password)
            return false
        }
        return true
    }
}