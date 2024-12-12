package com.example.capstoneproject.ui.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.capstoneproject.R

class BookAdapter(
    private val bookList: List<DataItem>,
    private val onAddToLibraryClick: (DataItem) -> Unit
) :
    ListAdapter<DataItem, BookAdapter.BookViewHolder>(DiffCallback()) {

        // menampilkan title, author, rating, gambar belum ada di response API
    inner class BookViewHolder(view: View) : RecyclerView.ViewHolder(view) {
            val image: ImageView = view.findViewById(R.id.ivBookCover)
            val title: TextView = view.findViewById(R.id.tvBookTitle)
            val author: TextView = view.findViewById(R.id.tvBookAuthor)
            val rating: RatingBar = view.findViewById(R.id.rbBookRating)
            val addToLibrary: ImageView = view.findViewById(R.id.ivAddToLibrary)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_book, parent, false)
        return BookViewHolder(view)
    }

    override fun onBindViewHolder(holder: BookViewHolder, position: Int) {
        val book = getItem(position)

        val randomImages = listOf(
            R.drawable.img,
            R.drawable.img1,
            R.drawable.img2,
            R.drawable.img3,
            R.drawable.img4,
            R.drawable.img5
        )

        Glide.with(holder.itemView.context)
            .load(book.image)
            .placeholder(R.drawable.baseline_image_24)
            .error(randomImages.random())
            .into(holder.image)

        holder.title.text = book.title
        holder.author.text = "By ${book.authors}"
        holder.rating.rating = book.reviewScore.toFloat()

        holder.addToLibrary.setOnClickListener {
            onAddToLibraryClick(book)
        }
    }

    class DiffCallback : DiffUtil.ItemCallback<DataItem>() {
        override fun areItemsTheSame(oldItem: DataItem, newItem: DataItem): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: DataItem, newItem: DataItem): Boolean {
            return oldItem == newItem
        }
    }
}