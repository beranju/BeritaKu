package com.nextgen.beritaku.profile

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.nextgen.beritaku.R
import com.nextgen.beritaku.databinding.FragmentProfileBinding
import com.nextgen.beritaku.utils.OtherMenu


class ProfileFragment : Fragment() {
    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!
    private lateinit var auth: FirebaseAuth
    private val listMenu: Array<String> = OtherMenu.values().map { it.string }.toTypedArray()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        auth = FirebaseAuth.getInstance()

        setupView(auth.currentUser!!)
        setOtherMenu()

        binding.btnEdit.setOnClickListener {
            val action = ProfileFragmentDirections.actionAccountNavigationToFormProfileFragment()
            findNavController().navigate(action)
        }
    }

    private fun setOtherMenu() {
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1, listMenu)
        binding.lvMenu.adapter = adapter
        binding.lvMenu.setOnItemClickListener { _, _, position, _ ->
            when(listMenu[position]){
                OtherMenu.FavoriteNews.string -> {
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse("beritaku://favorite"))
                    startActivity(intent)
                }
                OtherMenu.Logout.string -> {
                    auth.signOut()
                    val action = ProfileFragmentDirections.actionAccountNavigationToLoginFragment()
                    findNavController().navigate(action)
                }

            }
        }
    }

    private fun setupView(currentUser: FirebaseUser) {
        binding.tvUsername.text = currentUser.displayName
        Glide.with(requireContext())
            .load(currentUser.photoUrl)
            .apply(RequestOptions().placeholder(R.drawable.ic_load_data).error(R.drawable.ic_empty_data) )
            .into(binding.sivProfile)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    companion object
}