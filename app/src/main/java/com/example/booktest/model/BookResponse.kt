package com.example.booktest.model

data class BookResponse(

    val totalItems: Int,
    val items: List<Books>

)

data class Books (
    val volumeInfo: BooksVolumeInfo

        )

data class BooksVolumeInfo(
    val title: String,
    val printType: String,
    val imageLinks: BooksImageLink,
    val previewLink: String,
    val subtitle: String,
    val authors: List<String>
)

data class BooksImageLink(
    val smallThumbnail: String,
    val thumbnail: String
)