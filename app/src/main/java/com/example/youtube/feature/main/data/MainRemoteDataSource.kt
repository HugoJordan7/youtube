package com.example.youtube.feature.main.data

import com.example.youtube.base.RequestCallback
import com.example.youtube.model.ListVideo
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.Request

class MainRemoteDataSource: MainDataSource {

    override suspend fun findVideoList(callback: RequestCallback<ListVideo>) {
        try {
            val client = OkHttpClient.Builder().build()

            val request = Request.Builder()
                .get()
                .url("https://tiagoaguiar.co/api/youtube-videos")
                .build()

            val response = client.newCall(request).execute()
            if (response.isSuccessful){
                val list = GsonBuilder().create()
                    .fromJson(response.body()?.string(), ListVideo::class.java)
                callback.onSuccess(list)
            } else {
                throw RuntimeException()
            }
        } catch (e: Exception){
            callback.onFailure(e.message ?: "Erro ao buscar posts")
        } finally {
            callback.onComplete()
        }
    }

}