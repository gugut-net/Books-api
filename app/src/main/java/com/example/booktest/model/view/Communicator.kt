package com.example.booktest.model.view

import android.text.Editable

interface Communicator {

    fun sendDataToSearch(
        bookTitle: String,
        bookFilter:String,
        bookType: String)
}