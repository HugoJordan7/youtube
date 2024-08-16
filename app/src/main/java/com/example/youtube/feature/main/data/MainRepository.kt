package com.example.youtube.feature.main.data

import com.example.youtube.base.RequestCallback
import com.example.youtube.model.ListVideo

class MainRepository(private val dataSource: MainDataSource) {

    suspend fun findVideoList(callback: RequestCallback<ListVideo>){
        dataSource.findVideoList(object : RequestCallback<ListVideo>{
            override fun onSuccess(data: ListVideo) {
                callback.onSuccess(data)
            }
            override fun onFailure(message: String) {
                callback.onFailure(message)
            }
            override fun onComplete() {
                callback.onComplete()
            }
        })
    }

}