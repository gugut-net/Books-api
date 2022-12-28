package com.example.booktest.model.view

import android.content.ContentValues
import android.os.Bundle
import android.text.Editable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.booktest.databinding.DisplayFragmentBinding
import com.example.booktest.model.BookResponse
import com.example.booktest.model.remote.Network
import com.example.booktest.model.view.adapter.BooksAdapter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


private const val TAG = "DisplayFragment"

class DisplayFragment : Fragment() {

    companion object {
        const val EXTRA_BOOK = "EXTRA_BOOK"
        const val EXTRA_FILTER = "EXTRA_FILTER"
        const val EXTRA_TYPE = "EXTRA_TYPE"

        fun newInstance(
            bookTitle: String,
            bookFilter: String,
            bookType: String
        ) = DisplayFragment().apply{
            arguments = Bundle().apply {
                putString(EXTRA_BOOK, bookTitle)
                putString(EXTRA_FILTER, bookFilter)
                putString(EXTRA_TYPE, bookType)
            }

        }
    }
    private lateinit var binding: DisplayFragmentBinding
    private lateinit var bookTitle: String
    private lateinit var bookFilter: String
    private lateinit var bookType: String
    private val adapter: BooksAdapter by lazy {
        BooksAdapter(mutableListOf())
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        binding = DisplayFragmentBinding.inflate(
            inflater,
            container,
            false
        )
        arguments?.let {
             bookTitle = it.getString(EXTRA_BOOK) ?: " "
             bookFilter = it.getString(EXTRA_FILTER) ?: " "
             bookType = it.getString(EXTRA_TYPE) ?: " "

            getBookData(bookTitle, bookFilter, bookType)
        }
        initViews()
        return binding.root
    }

    private fun getBookData(bookTitle: String,
                            bookFilter: String,
                            bookPrintType: String,
                            startIndex: Int = 0) {
        Log.d(TAG, "getBookData: ")
        Network().api
            .getNextBookPage(bookTitle,
                bookPrintType,
                bookFilter,
                startIndex)
            .enqueue(
                object: Callback<BookResponse> {
                    override fun onResponse(call: Call<BookResponse>, response: Response<BookResponse>) {
                        if (response.isSuccessful) {
                            response.body()?.let {
                                updateAdapter(it)
                            }

                        }else {
                            Log.d(ContentValues.TAG, "onResponse: ${response.message()}")
                        }
                    }

                    override fun onFailure(call: Call<BookResponse>, t: Throwable) {
                        Log.d(ContentValues.TAG, "onFailure: ${t.message}")
                        t.printStackTrace()
                    }

                }
            )

    }
    private fun initViews() {
        binding.apply {
            bookList.layoutManager = GridLayoutManager(context, 3)
            bookList.adapter = adapter
            bookList.addOnScrollListener(scrollListener)
        }

    }

    private fun updateAdapter(dataSet: BookResponse) {
        adapter.updateDataSet(dataSet.items)

    }
    private val scrollListener = object : RecyclerView.OnScrollListener() {
        private var visibleItemCount = 0
        private var totalItemCount = 0
        private var pastItemVisible = 0

        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)

            val layoutManager = recyclerView.layoutManager as GridLayoutManager
            if (dy > 0) {
                visibleItemCount = layoutManager.childCount
                totalItemCount = layoutManager.itemCount
                pastItemVisible = layoutManager.findFirstVisibleItemPosition()

                if (visibleItemCount + pastItemVisible >= totalItemCount) {
                    fetchMoreData(totalItemCount)
                }
            }
        }
    }
    private fun fetchMoreData(nextStartIndex: Int) {
        getBookData(bookTitle, bookFilter, bookType, nextStartIndex)
    }
}
