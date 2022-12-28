package com.example.booktest.model.view.adapter


import android.content.ClipData.Item
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.booktest.databinding.BookItemLayoutBinding
import com.example.booktest.model.Books
import com.example.booktest.model.BooksVolumeInfo
import com.squareup.picasso.Picasso

class BooksAdapter(private val dataSet: MutableList<Books>): RecyclerView.Adapter<BooksAdapter.BooksViewHolder>() {



    class BooksViewHolder(private val binding: BookItemLayoutBinding):
            RecyclerView.ViewHolder(binding.root) {

                fun onBind(bookItem: BooksVolumeInfo) {
                    binding.apply {
                        bookTitleItem.text = bookItem.title
                        Picasso.get()
                            .load(bookItem.imageLinks.smallThumbnail)
                            .into(bookCoverItem)
                    }
                }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        BooksViewHolder(
            BookItemLayoutBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: BooksViewHolder,
                                  position: Int) {
        holder.onBind(
            dataSet[position].volumeInfo
        )
    }

    override fun getItemCount() = dataSet.size

    fun updateDataSet(items: List<Books>) {
        val originalSize = dataSet.size - 1
        dataSet.addAll(items)
        notifyItemRangeInserted(originalSize, 10)
    }
}