package com.example.youtube.base

interface RequestCallback<T> {
    fun onSuccess(data: T)
    fun onFailure(message: String)
    fun onComplete()
}