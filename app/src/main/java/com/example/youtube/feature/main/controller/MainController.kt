package com.example.youtube.feature.main.controller

import com.example.youtube.feature.main.view.MainActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch

class MainController(private var view: MainActivity?) {

    private val controllerScope = CoroutineScope(Dispatchers.IO)

    fun findVideoList(){
        controllerScope.launch {

        }
    }

    fun onDestroy(){
        controllerScope.cancel()
        view = null
    }

}