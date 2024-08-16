package com.example.youtube.feature.main.data

import com.example.youtube.base.RequestCallback
import com.example.youtube.model.ListVideo

class MainRepository(private val dataSource: MainDataSource) {

    suspend fun findVideoList(callback: RequestCallback<ListVideo>){
        dataSource.findVideoList(callback)
    }

}