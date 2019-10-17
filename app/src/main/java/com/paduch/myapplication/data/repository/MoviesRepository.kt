package com.paduch.myapplication.data.repository

import androidx.lifecycle.MutableLiveData
import com.paduch.myapplication.data.remote.model.Movie
import com.paduch.myapplication.data.remote.model.TopMoviesResponse
import com.paduch.myapplication.data.remote.service.ImdbService
import org.joda.time.LocalDate
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
    private var lastPage = 0
    var enableFiltering = false
        set(x) {
            field = x
            emitFromCache()
        }
    var maxYear = 9999
    var minYear = 0

    fun getTopMovies(page: Int = 1): MutableLiveData<TopMoviesResponse> {
        if (page > lastPage) {
            refreshTopMovies(page)
        }
        return topMoviesData
    }

    private fun refreshTopMovies(page: Int = 1) {
        executor.execute {
            val response = service.top(page).execute().body()
            lastPage = response?.page ?: 1
            response?.results?.let {
                cache.addAll(response.results)
                lastPage = response.page
            }
            emitFromCache()
        }
    }

    private fun emitFromCache() {
        if (enableFiltering) {
            val filteredCache = cache.filter {
                LocalDate.parse(it.releaseDate).minusYears(minYear).year >= 0 &&
                        LocalDate.parse(it.releaseDate).minusYears(maxYear).year <= 0
            }
            topMoviesData.postValue(TopMoviesResponse(lastPage, filteredCache, -1))

        } else {
            topMoviesData.postValue(TopMoviesResponse(lastPage, cache, -1))
        }
    }
}