package com.nextgen.beritaku.profile

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.content.res.AppCompatResources
import androidx.fragment.app.Fragment
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.nextgen.beritaku.R
import com.nextgen.beritaku.databinding.FragmentFormProfileBinding
import com.nextgen.beritaku.utils.UiState
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.koin.androidx.viewmodel.ext.android.viewModel


class FormProfileFragment : Fragment(), View.OnClickListener {
    private var _binding: FragmentFormProfileBinding? = null
    private val binding get() = _binding!!
    private var uri: Uri? = null
    private val viewModel: ProfileViewModel by viewModel()

    private val intentGallery =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()){ result->
            if (result.resultCode == Activity.RESULT_OK){
                uri = result.data?.data
                Glide.with(requireActivity()).load(uri).into(binding.photoProfile)
            }

        }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val emptyImage = AppCompatResources.getDrawable(requireActivity(), R.drawable.undraw_male_avatar_g98d)
        viewModel.getUser()?.let { user->
            uri = user.photoUrl
            Glide.with(requireContext())
                .load(uri ?: emptyImage)
                .into(binding.photoProfile)
            binding.edtName.setText(user.displayName)
        }
        binding.ivSelectPhoto.setOnClickListener(this)
        binding.btnUpdate.setOnClickListener(this)

        viewModel.uiState.observe(viewLifecycleOwner){state ->
            when(state){
                is UiState.Loading -> {
                    binding.btnUpdate.isEnabled = false
                }
                is UiState.Success -> {
                    val go = FormProfileFragmentDirections.actionFormProfileFragmentToAccountNavigation()
                    findNavController().navigate(go)
                }
                is UiState.Error -> {
                    Toast.makeText(requireContext(), "Something went wrong!", Toast.LENGTH_SHORT).show()
                    Log.e(TAG, "onError : ${state.message}")
                }
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFormProfileBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        const val TAG = "FormProfileFragment"
    }

    override fun onClick(v: View?) {
        when(v){
            binding.ivSelectPhoto -> {
                Intent(Intent.ACTION_PICK).apply {
                    type = "image/*"
                }.also { intentGallery.launch(Intent.createChooser(it, null)) }
            }
            binding.btnUpdate -> {
                val name = binding.edtName.text.toString().trim()
                updateUserProfile(name, uri!!)
            }
        }
    }

    private fun updateUserProfile(name: String, uri: Uri) {
        viewModel.updateDataUser(name, uri)
    }
}