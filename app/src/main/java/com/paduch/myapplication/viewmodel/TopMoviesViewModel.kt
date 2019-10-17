package com.paduch.myapplication.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.paduch.myapplication.data.remote.model.TopMoviesResponse
import com.paduch.myapplication.data.repository.MoviesRepository
import javax.inject.Inject

class TopMoviesViewModel @Inject constructor(
    private val repository: MoviesRepository): ViewModel() {
    private val pageFilter: MutableLiveData<Int> = MutableLiveData()
    val topMoviesLiveData: LiveData<TopMoviesResponse>
            = Transformations.switchMap(pageFilter, repository::getTopMovies)

    fun requestPage(page: Int) = pageFilter.postValue(page)
}