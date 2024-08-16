package com.example.youtube.feature.main.data

import com.example.youtube.base.RequestCallback
import com.example.youtube.model.ListVideo

interface MainDataSource {
    suspend fun findVideoList(callback: RequestCallback<ListVideo>)
}
