package com.paduch.myapplication.view

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.paduch.myapplication.R
import com.paduch.myapplication.data.remote.model.Movie
import com.paduch.myapplication.di.DaggerMoviesComponent
import com.paduch.myapplication.inflate
import com.paduch.myapplication.ui.TopMoviesAdapter
import com.paduch.myapplication.viewmodel.TopMoviesViewModel
import kotlinx.android.synthetic.main.fragment_top_movies.view.*
import javax.inject.Inject

class TopMoviesFragment : Fragment() {
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = container?.inflate(R.layout.fragment_top_movies)
        val recyclerView = view?.my_recycler_view
        val layoutManager = LinearLayoutManager(context)
        recyclerView?.layoutManager = layoutManager
        recyclerView?.addItemDecoration(DividerItemDecoration(context, layoutManager.orientation))
        val adapter = context?.let { TopMoviesAdapter(it, ::showDetailsFragment) }
        recyclerView?.adapter = adapter
        val vm = ViewModelProviders.of(this, viewModelFactory)[TopMoviesViewModel::class.java]
        vm.topMoviesLiveData.observe(viewLifecycleOwner, Observer { adapter?.submitList(it.results) })
        vm.requestPage(1)
        return view
    }

    private fun showDetailsFragment(movie: Movie) {
        val bundle = Bundle()
        bundle.putParcelable(DetailedMovieFragment.KEY_MOVIE, movie)
        val itemDetailsFragment = DetailedMovieFragment()
        itemDetailsFragment.arguments = bundle
        fragmentManager?.beginTransaction()
            ?.replace(
                (view?.parent as ViewGroup).id,
                itemDetailsFragment, DetailedMovieFragment::class.java.name
            )?.addToBackStack(null)
            ?.commit()
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        DaggerMoviesComponent.builder().build().inject(this)
    }
}
