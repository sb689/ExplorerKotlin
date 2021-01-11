package com.example.explorer_kotlin.overview

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.explorer_kotlin.databinding.FragmentOverviewBinding


class OverviewFragment : Fragment() {

    private val viewModel:OverViewViewModel by lazy {
        ViewModelProvider(this).get(OverViewViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment

        Log.d("OverviewFragment", "onCreate called")
        val binding = FragmentOverviewBinding.inflate(inflater)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        binding.rvSearchResult.layoutManager = LinearLayoutManager(context)

        binding.rvSearchResult.adapter = SearchResultAdapter(onClickListener = SearchResultAdapter.OnClickListener{
            viewModel.displayResultDetails(it)
        })

        viewModel.navigateToSelectedResult.observe(viewLifecycleOwner, Observer {
            if(it != null)
            {
               this.findNavController().navigate(OverviewFragmentDirections.actionOverviewFragmentToDetailFragment())
                viewModel.displayResultDetailsComplete()
            }
        })
        return binding.root
    }


}