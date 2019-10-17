package com.paduch.myapplication.ui

import android.content.Context
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.paduch.myapplication.R
import com.paduch.myapplication.data.remote.model.Movie
import com.paduch.myapplication.inflate
import kotlinx.android.synthetic.main.recycler_row.view.*

val DiffCallback = object : DiffUtil.ItemCallback<Movie>() {
    override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean = oldItem.id == newItem.id
    override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean = oldItem == newItem
}

class TopMoviesAdapter(private val context: Context, val itemClick: (movie: Movie) -> Unit,
                       val loadNext: ()-> Unit) :
    ListAdapter<Movie, TopMoviesAdapter.ViewHolder>(DiffCallback) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(parent.inflate(R.layout.recycler_row))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if (position == itemCount - 1) {
            loadNext()
        }
        val movie: Movie = getItem(position)
        holder.itemView.setOnClickListener { itemClick(movie)  }
        holder.itemView.title_text_view.text =
            context.getString(R.string.movie_title, movie.title)
        holder.itemView.release_date_text_View.text =
            context.getString(R.string.release_date, movie.releaseDate)

    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}


