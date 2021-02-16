package com.example.explorer_kotlin.detail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
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

        binding.lifecycleOwner = this
        val result = DetailFragmentArgs.fromBundle(requireArguments()).selectedResult
        val viewModel by viewModels<DetailViewModel> {
            DetailViewModelFactory(result)
        }
        binding.viewModel = viewModel

        return binding.root
    }

}