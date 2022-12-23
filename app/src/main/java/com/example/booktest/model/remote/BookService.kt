package com.example.booktest.model.remote

import android.icu.text.AlphabeticIndex
import android.icu.text.CaseMap.Title
import com.example.booktest.model.BookResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query
import java.util.logging.Filter

interface BookService {


    /**
     * Implementing the Retrofit
    1.- Add the library dependency
    2.- Create the service interface
    3.- Define the HTTP Verbs in the the interface
    4.- Create the Retrofit objects
    5.- Invoke either enqueue or execute
     */

    // https://www.googleapis.com/books/v1/volumes?q=bible&printType=books&filter=ebooks

    @GET(ENDPOINT)
    fun getNextBookPage(
        @Query(PARAM_Q) bookTitle: String,
        @Query(PARAM_PRINT_TYPE) bookType: String,
        @Query(PARAM_FILTER) bookFilter: String,
        @Query(PARAM_START_INDEX) pageIndex: Int

    ): Call<BookResponse>

}