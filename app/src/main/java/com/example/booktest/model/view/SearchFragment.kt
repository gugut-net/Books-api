package com.example.booktest.model.view

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import com.example.booktest.R
import com.example.booktest.databinding.SearchFragmentBinding

class SearchFragment: Fragment() {

    private lateinit var binding: SearchFragmentBinding
    private lateinit var adapterFilter: ArrayAdapter<String>
    private lateinit var adapterBookType: ArrayAdapter<String>

    private lateinit var communicator: Communicator

    override fun onAttach(context: Context) {
        super.onAttach(context)
        when(context) {
            is Communicator -> communicator = context
            else -> throw Exception("Incorrect Host Activity")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        binding = SearchFragmentBinding.inflate(
            inflater,
            container,
            false
        )
        initViews()

        return binding.root
    }

    private fun initViews() {
        adapterFilter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_list_item_1,
            requireContext().resources
                .getStringArray(R.array.book_filter)
        )
        adapterBookType = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_list_item_1,
            requireContext().resources
                .getStringArray(R.array.bool_print_type)
        )
        binding.apply {
            bookFilter.adapter = adapterFilter
            bookPrint.adapter = adapterBookType
            bookSearch.setOnClickListener{
                sendSearchParams()
            }
        }
    }

    private fun sendSearchParams() {
            binding.bookTitle.editText?.text?.let {
            val bookTitle = it.toString()
            val bookFilter = binding.bookFilter.selectedItem.toString()
            val bookPrintType = binding.bookPrint.selectedItem.toString()

            communicator.sendDataToSearch(bookTitle, bookFilter, bookPrintType)

            }
    }
}