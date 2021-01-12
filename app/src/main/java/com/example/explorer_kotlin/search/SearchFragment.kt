package com.example.explorer_kotlin.search

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
import com.example.explorer_kotlin.R
import com.example.explorer_kotlin.databinding.FragmentSearchBinding


class SearchFragment : Fragment() {

     lateinit var  binding: FragmentSearchBinding

    private val searchViewModel: SearchViewModel by lazy {
        ViewModelProvider(this).get(SearchViewModel::class.java)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentSearchBinding.inflate(inflater)
        binding.viewModel = searchViewModel
        binding.lifecycleOwner = this

        searchViewModel.displaySearchResults.observe(viewLifecycleOwner, Observer {
            if(it)
            {
                Log.d("SearchFragment", "searchClicked received")
                Log.d("SearchFragment", "query = "+ binding.etQueryInput.text.toString())
                Log.d("SearchFragment", "startYear = "+ binding.etStartYearInput.text.toString())
                Log.d("SearchFragment", "endYear = "+ binding.etEndYearInput.text.toString())

                this.findNavController().navigate(SearchFragmentDirections.actionSearchFragment2ToOverviewFragment(
                        binding.etQueryInput.text.toString(),
                        binding.etStartYearInput.text.toString(),
                        binding.etEndYearInput.text.toString()))
                searchViewModel.searchResultDisplayComplete()

            }
        })




        return binding.root;
    }


}