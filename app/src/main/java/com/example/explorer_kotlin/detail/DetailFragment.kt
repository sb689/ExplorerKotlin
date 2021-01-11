package com.example.explorer_kotlin.detail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.example.explorer_kotlin.R
import com.example.explorer_kotlin.databinding.FragmentDetailBinding


class DetailFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?,
    ): View? {


        val binding = FragmentDetailBinding.inflate(inflater)
        val application = requireNotNull(activity).application
        binding.lifecycleOwner = this
        val result = DetailFragmentArgs.fromBundle(requireArguments()).selectedResult
        val viewModelFactory = DetailViewModelFactory(result, application)
        val viewModel = ViewModelProvider(this, viewModelFactory).get(DetailViewModel::class.java)
        binding.viewModel = viewModel

        return binding.root
    }

}