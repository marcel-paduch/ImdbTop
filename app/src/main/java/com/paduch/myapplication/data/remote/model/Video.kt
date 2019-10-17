package com.paduch.myapplication.data.remote.model

import com.google.gson.annotations.SerializedName

class VideoWrapper(@SerializedName("results") val videos: List<Video>)
class Video(val site: String,val key: String, val type: String)