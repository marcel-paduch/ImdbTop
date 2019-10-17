package com.paduch.myapplication.data.repository

import androidx.lifecycle.MutableLiveData
import com.paduch.myapplication.data.remote.model.Movie
import com.paduch.myapplication.data.remote.model.TopMoviesResponse
import com.paduch.myapplication.data.remote.service.ImdbService
import java.util.concurrent.ExecutorService
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MoviesRepository @Inject constructor(
    private val service: ImdbService,
    private val executor: ExecutorService
) {
    private val topMoviesData: MutableLiveData<TopMoviesResponse> = MutableLiveData()
    private val cache: ArrayList<Movie> = ArrayList()
    private var lastPage = 1

    fun getTopMovies(page: Int = 1): MutableLiveData<TopMoviesResponse> {
       // emitFromCache()
        refreshTopMovies(page)
        return topMoviesData
    }

    private fun refreshTopMovies(page: Int = 1) {
        executor.execute {
            val response = service.top(page).execute().body()
            lastPage = response?.page ?: 1
            response?.results?.let { cache.addAll(it) }
            emitFromCache()
        }
    }

    private fun emitFromCache() {
        topMoviesData.postValue(TopMoviesResponse(lastPage, cache, -1))
    }
}