package com.example.explorer_kotlin.search

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.explorer_kotlin.R
import com.example.explorer_kotlin.databinding.FragmentSearchBinding
import com.example.explorer_kotlin.showToast



class SearchFragment : Fragment() {

    lateinit var binding: FragmentSearchBinding

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

        searchViewModel.toastText.observe(viewLifecycleOwner, Observer {
            it.getContentIfNotHandled()?.let {
                when(it){
                    R.string.no_data_found_error_msg ->  view?.showToast(
                            getString(R.string.no_data_found_error_msg), Toast.LENGTH_LONG)
                    R.string.no_network_error_msg -> view?.showToast(
                            getString(R.string.no_network_error_msg), Toast.LENGTH_LONG)
                }

            }})

        searchViewModel.displaySearchResults.observe(viewLifecycleOwner, Observer {
            it.getContentIfNotHandled()?.let {

                Log.d("SearchFragment", "searchClicked received")
                Log.d("SearchFragment", "query = " + binding.etQueryInput.text.toString())
                Log.d("SearchFragment", "startYear = " + binding.etStartYearInput.text.toString())
                Log.d("SearchFragment", "endYear = " + binding.etEndYearInput.text.toString())

                this.findNavController().navigate(SearchFragmentDirections.actionSearchFragment2ToOverviewFragment(
                        binding.etQueryInput.text.toString(),
                        binding.etStartYearInput.text.toString(),
                        binding.etEndYearInput.text.toString()))
            }
        })

        searchViewModel.hideKeyBoard.observe(viewLifecycleOwner, Observer {
            it.getContentIfNotHandled()?.let {
                if (it) {
                    hideKeyboard()
                }
            }

        })
        return binding.root;
    }

    fun Context.hideKeyboard(view: View) {
        val inputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
    }

    fun Fragment.hideKeyboard() {
        view?.let { activity?.hideKeyboard(it) }
    }



}