package com.nextgen.beritaku.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.dynamicfeatures.fragment.ui.AbstractProgressFragment
import com.nextgen.beritaku.R

class ProgressFragment : AbstractProgressFragment() {
    override fun onCancelled() {

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_progress, container, false)
    }

    override fun onFailed(errorCode: Int) {
    }

    override fun onProgress(status: Int, bytesDownloaded: Long, bytesTotal: Long) {

    }
}