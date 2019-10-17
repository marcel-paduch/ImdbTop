package com.paduch.myapplication.data.remote.service;

import com.paduch.myapplication.data.remote.model.TopMoviesResponse
import com.paduch.myapplication.data.remote.model.Video
import com.paduch.myapplication.data.remote.model.VideoWrapper
import retrofit2.Call
import retrofit2.http.GET;
import retrofit2.http.Path
import retrofit2.http.Query;

interface ImdbService {

    @GET("movie/top_rated")
    fun top(@Query("page") page: Int) : Call<TopMoviesResponse>

    @GET("movie/{movie_id}/videos")
    fun videos(@Path("movie_id") movieId: Int) : Call<VideoWrapper>
}
