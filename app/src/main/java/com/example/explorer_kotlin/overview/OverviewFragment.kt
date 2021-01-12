package com.example.explorer_kotlin.overview

import android.app.Application
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.explorer_kotlin.R
import com.example.explorer_kotlin.databinding.FragmentOverviewBinding
import com.example.explorer_kotlin.databinding.FragmentSearchBinding
import com.example.explorer_kotlin.detail.DetailFragmentArgs
import com.example.explorer_kotlin.detail.DetailViewModel
import com.example.explorer_kotlin.detail.DetailViewModelFactory


class OverviewFragment : Fragment() {




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


       val args: OverviewFragmentArgs by navArgs()
        var query = if(args.query == "@") getString(R.string.default_query) else args.query
        val startYear= if(args.startYear == "@" || args.startYear.isNullOrEmpty()) null else args.startYear
        val endYear= if(args.endYear == "@" || args.endYear.isNullOrEmpty()) null else args.endYear


        Log.d("OverviewFragment", "query = "+ query + ", startYear = " + startYear + ", endYear = "+ endYear)
        val viewModelFactory = OverviewViewModelFactory(
                query,
                startYear,
                endYear,
                Application())

        val viewModel = ViewModelProvider(this, viewModelFactory).get(OverViewViewModel::class.java)

        binding.viewModel = viewModel
        binding.rvSearchResult.layoutManager = LinearLayoutManager(context)

        binding.rvSearchResult.adapter = SearchResultAdapter(onClickListener = SearchResultAdapter.OnClickListener {
            viewModel.displayResultDetails(it)
        })

        viewModel.navigateToSelectedResult.observe(viewLifecycleOwner, Observer {
            if (it != null) {
                this.findNavController().navigate(OverviewFragmentDirections.actionOverviewFragmentToDetailFragment(it))
                viewModel.displayResultDetailsComplete()
            }
        })

        viewModel.navigateToSearchPage.observe(viewLifecycleOwner, Observer {
            if (it) {
                this.findNavController().navigate(OverviewFragmentDirections.actionOverviewFragmentToSearchFragment2())
                viewModel.displaySearchPageComplete()
            }
        })

        return binding.root
    }


}