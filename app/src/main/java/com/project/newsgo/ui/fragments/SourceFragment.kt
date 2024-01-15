package com.project.newsgo.ui.fragments

import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.project.newsgo.R
import com.project.newsgo.databinding.FragmentHomeBinding
import com.project.newsgo.databinding.FragmentSourceBinding
import com.project.newsgo.ui.viewmodels.HomeFragmentViewModel

class SourceFragment : Fragment() {
    private var _binding: FragmentSourceBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View{
        _binding = FragmentSourceBinding.inflate(inflater, container, false)
        val view = binding.root
        val bundle: SourceFragmentArgs by navArgs()
        val source = bundle.source
        binding.webView.loadUrl(source)
        return view
    }


}