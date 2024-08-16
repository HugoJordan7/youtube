package com.example.youtube.di

import com.example.youtube.feature.main.data.MainRemoteDataSource
import com.example.youtube.feature.main.data.MainRepository

object DependencyInjector {


    private val mainRepository = MainRepository(MainRemoteDataSource())

    fun provideMainRepository() = mainRepository

}