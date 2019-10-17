package com.paduch.myapplication.view

import android.app.AlertDialog
import android.content.Context
import android.opengl.Visibility
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
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

    private lateinit var viewModel: TopMoviesViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = container?.inflate(R.layout.fragment_top_movies)
        val recyclerView = view?.my_recycler_view
        val layoutManager = LinearLayoutManager(context)
        val fab = view?.fab

        val progressDrawable = context?.let { CircularProgressDrawable(it) }
        progressDrawable?.centerRadius = 250f
        progressDrawable?.strokeWidth = 25f
        //progressDrawable?.setVisible(true, false)
        progressDrawable?.start()
        view?.progress_drawable?.setImageDrawable(progressDrawable)
        fab?.setOnClickListener { showFilterDialog() }
        recyclerView?.layoutManager = layoutManager
        recyclerView?.addItemDecoration(DividerItemDecoration(context, layoutManager.orientation))
        val adapter = context?.let {
            TopMoviesAdapter(it, ::showDetailsFragment) {
                viewModel.requestPage(viewModel.lastRequestedPage + 1)
            }
        }
        recyclerView?.adapter = adapter
        viewModel = ViewModelProviders.of(this, viewModelFactory)[TopMoviesViewModel::class.java]
        viewModel.topMoviesLiveData.observe(
            viewLifecycleOwner,
            Observer {
                progressDrawable?.stop()
                view?.progress_drawable?.visibility = View.GONE
                adapter?.submitList(ArrayList(it.results))
                viewModel.lastRequestedPage = it.page
            })
        viewModel.requestPage()
        return view
    }

    private fun showFilterDialog() {
        val alert = AlertDialog.Builder(context)

        val layout = LinearLayout(context)
        layout.orientation = LinearLayout.VERTICAL

        val minYearBox = EditText(context)
        minYearBox.hint = "min year"
        minYearBox.setText(viewModel.getMinYear().toString())
        layout.addView(minYearBox)

        val maxYearBox = EditText(context)
        maxYearBox.hint = "max year"
        maxYearBox.setText(viewModel.getMaxYear().toString())
        layout.addView(maxYearBox)

        alert.setView(layout)
        alert.setPositiveButton(android.R.string.ok) { _, _ ->
            val maxYear = maxYearBox.text.toString().toIntOrNull()
            val minYear = minYearBox.text.toString().toIntOrNull()
            viewModel.setYearFiltering(minYear ?: 0, maxYear ?: 9999)
        }
        alert.setNegativeButton(android.R.string.cancel) { dialog, _ ->
            run {
                dialog.cancel()
                viewModel.disableFiltering()
            }
        }
        alert.show()
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

