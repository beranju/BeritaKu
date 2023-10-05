package com.nextgen.beritaku.detail

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.nextgen.beritaku.R
import com.nextgen.beritaku.databinding.FragmentWebBinding


class WebFragment : Fragment() {
    private var _binding: FragmentWebBinding? = null
    private val binding get() = _binding

    @SuppressLint("SetJavaScriptEnabled")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.toolbar?.apply {
            setupWithNavController(findNavController(), null)
            title = context.getString(R.string.web_view_title)
        }
        val url = arguments?.getString(URL)

        val webView = binding?.webView
        webView?.settings?.javaScriptEnabled = true
        webView?.webViewClient = object : WebViewClient(){
            override fun onPageFinished(view: WebView?, url: String?) {
                view?.loadUrl("javascript:alert('berita berhasil dimuat')")
            }
        }
        webView?.loadUrl(url.toString())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentWebBinding.inflate(inflater, container, false)
        return  binding?.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        const val URL = "url"
    }
}