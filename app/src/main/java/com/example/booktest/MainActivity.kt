package com.example.booktest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import com.example.booktest.databinding.SearchFragmentBinding
import com.example.booktest.model.view.Communicator
import com.example.booktest.model.view.DisplayFragment
import com.example.booktest.model.view.SearchFragment


private const val TAG = "MainActivity"

class MainActivity : AppCompatActivity(), Communicator {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_search, SearchFragment())
            .commit()

    }

    override fun sendDataToSearch(
        bookTitle: String,
        bookFilter: String,
        bookType: String) {
        // TODO("Not yet implemented")
        supportFragmentManager.beginTransaction()
            .add(R.id.fragment_display, DisplayFragment
                .newInstance(bookTitle, bookFilter, bookType))
            .addToBackStack("")
            .commit()
    }
}