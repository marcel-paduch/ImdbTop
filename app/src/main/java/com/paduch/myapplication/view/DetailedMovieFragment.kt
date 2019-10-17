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
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.paduch.myapplication.di.DaggerMoviesComponent
import com.paduch.myapplication.viewmodel.TopMoviesViewModel
import com.paduch.myapplication.viewmodel.VideosViewModel
import kotlinx.android.synthetic.main.fragment_detailed_movie.*
import javax.inject.Inject


class DetailedMovieFragment : Fragment() {
    companion object {
        val KEY_MOVIE = DetailedMovieFragment::class.java.name + ".keyMovie"
        const val POSTER_PATH_BASE = "https://image.tmdb.org/t/p/w342"
    }
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

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
        val viewModel = ViewModelProviders.of(this, viewModelFactory)[VideosViewModel::class.java]
        viewModel.init(movie?.id ?: 0)
        viewModel.videoData.observe(viewLifecycleOwner, Observer{
            view?.video_button?.isEnabled = true
            view?.video_button?.setOnClickListener { _ -> playYtVideo(it.key)}
        })
        return view
    }

    private fun playYtVideo(videoId: String) {
        val appIntent = Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube://$videoId"))
        val webIntent = Intent(
            Intent.ACTION_VIEW,
            Uri.parse("http://www.youtube.com/watch?v=$id")
        )
        try {
            context?.startActivity(appIntent)
        } catch (ex: ActivityNotFoundException) {
            context?.startActivity(webIntent)
        }

    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        DaggerMoviesComponent.builder().build().injectDetailed(this)

    }
}