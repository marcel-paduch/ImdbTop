package com.paduch.myapplication.data.repository

import androidx.lifecycle.MutableLiveData
import com.paduch.myapplication.data.remote.model.Video
import com.paduch.myapplication.data.remote.service.ImdbService
import java.util.concurrent.ExecutorService
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class VideosRepository @Inject constructor(
    private val service: ImdbService,
    private val executor: ExecutorService
) {
    private val videoData: MutableLiveData<Video> = MutableLiveData()

    fun getVideoData(id: Int): MutableLiveData<Video> {
        fetchVideo(id)
        return videoData
    }

    private fun fetchVideo(id: Int) {
        executor.execute {
            val videos = service.videos(id).execute().body()
            videoData.postValue(videos?.videos?.find {
                it.site == "YouTube" && it.type == "Trailer"
            })
        }
    }


}
