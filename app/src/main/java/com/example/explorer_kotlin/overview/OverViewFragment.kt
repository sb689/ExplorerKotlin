package com.example.explorer_kotlin.overview

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.explorer_kotlin.R
import com.example.explorer_kotlin.databinding.FragmentOverviewBinding



class OverViewFragment : Fragment() {


     lateinit var viewModel: OverViewViewModel

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


        val args: OverViewFragmentArgs by navArgs()
        var query = if(args.query.isNullOrEmpty() || args.query == "@") getString(R.string.default_query) else args.query
        val startYear= if( args.startYear.isNullOrEmpty()|| args.startYear == "@") null else args.startYear
        val endYear= if( args.endYear.isNullOrEmpty()|| args.endYear == "@") null else args.endYear


        Log.d("OverviewFragment", "query = $query, startYear = $startYear, endYear = $endYear")

        val activity = requireNotNull(this.activity)
        val viewModelFactory = OverviewViewModelFactory(
                query,
                startYear,
                endYear,
                activity.application)

        viewModel = viewModelFactory.let { ViewModelProvider(this, it).get(OverViewViewModel::class.java) }

        binding.viewModel = viewModel
        binding.rvSearchResult.layoutManager = LinearLayoutManager(context)

        binding.rvSearchResult.adapter = SearchResultAdapter(onClickListener = SearchResultAdapter.OnClickListener {
            viewModel.displayResultDetails(it)
        })

        viewModel.navigateToSelectedResult.observe(viewLifecycleOwner, Observer {
            if (it != null) {
                this.findNavController().navigate(OverViewFragmentDirections.actionOverviewFragmentToDetailFragment(it))
                viewModel.displayResultDetailsComplete()
            }
        })

        viewModel.navigateToSearchPage.observe(viewLifecycleOwner, Observer {
            if (it) {
                this.findNavController().navigate(OverViewFragmentDirections.actionOverviewFragmentToSearchFragment2())
                viewModel.displaySearchPageComplete()
            }
        })

        viewModel.eventNetworkError.observe(viewLifecycleOwner, Observer<Boolean> { isNetworkError ->
            if (isNetworkError) onError(getString(R.string.no_network_wrror_msg),  ErrorType.NETWORK)
        })

        viewModel.noDataFound.observe(viewLifecycleOwner, Observer<Boolean> {
            if (it) onError(getString(R.string.no_data_found_error_msg),  ErrorType.NO_DATA)
        })

        return binding.root
    }

    private fun onError(msg:String, type: ErrorType) {

            Toast.makeText(activity, msg, Toast.LENGTH_LONG).show()
            when(type){
                ErrorType.NO_DATA -> viewModel.noDataFoundErrorShown()
                ErrorType.NETWORK -> viewModel.onNetworkErrorShown()
            }

    }


}