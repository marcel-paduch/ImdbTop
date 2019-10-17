package com.paduch.myapplication.data.remote.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize


class TopMoviesResponse(val page: Int, val results: List<Movie>, val totalPages: Int)
@Parcelize
data class Movie(
    val id: Int,
    val overview: String,
    val title: String, @SerializedName("poster_path") val posterPath: String,
    @SerializedName("release_date") val releaseDate: String,
    val trailer: String
) : Parcelable

