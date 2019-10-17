package com.paduch.myapplication.view

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.paduch.myapplication.R
import com.paduch.myapplication.data.remote.model.Movie
import com.paduch.myapplication.inflate
import kotlinx.android.synthetic.main.fragment_detailed_movie.view.*;

class DetailedMovieFragment : Fragment() {
    companion object {
        val KEY_MOVIE = DetailedMovieFragment::class.java.name + ".keyMovie"
        const val POSTER_PATH_BASE = "https://image.tmdb.org/t/p/w342"
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = container?.inflate(R.layout.fragment_detailed_movie)
        val movie : Movie? = arguments?.getParcelable(KEY_MOVIE)
        view?.title?.text = movie?.title
        view?.overview?.text = movie?.overview
        view?.date?.text = movie?.releaseDate
        if(view?.poster != null){
            context?.let {
                Glide
                    .with(it)
                    .load(POSTER_PATH_BASE + movie?.posterPath)
                    .centerCrop()
                    .placeholder(ColorDrawable(Color.LTGRAY))
                    .into(view.poster)
            }
        }
        return view
    }
}