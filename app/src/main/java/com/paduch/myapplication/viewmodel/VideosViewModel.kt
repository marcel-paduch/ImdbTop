package com.paduch.myapplication.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.paduch.myapplication.data.remote.model.Video
import com.paduch.myapplication.data.repository.VideosRepository
import javax.inject.Inject

class VideosViewModel @Inject constructor(private val repository: VideosRepository) : ViewModel() {
    lateinit var videoData: MutableLiveData<Video>

    fun init(movieId: Int) {
        videoData = repository.getVideoData(movieId)
    }


}