package com.example.youtube.feature.main.controller

import com.example.youtube.base.RequestCallback
import com.example.youtube.feature.main.data.MainRepository
import com.example.youtube.feature.main.view.MainActivity
import com.example.youtube.model.ListVideo
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainController(
    private var view: MainActivity?,
    private val repository: MainRepository
) {

    private val controllerScope = CoroutineScope(Dispatchers.IO)

    fun findVideoList() {
        controllerScope.launch {
            view?.showProgress(true)
            repository.findVideoList(object : RequestCallback<ListVideo> {
                override fun onSuccess(data: ListVideo) {
                    mainScope {
                        if (data.data.isEmpty()) view?.displayFailure("Nenhum post encontrado")
                        else view?.displayVideoList(data)
                    }
                }
                override fun onFailure(message: String) {
                    mainScope{
                        view?.displayFailure(message)
                    }
                }
                override fun onComplete() {
                    mainScope{
                        view?.showProgress(false)
                    }
                }
            })
        }
    }

    private fun mainScope(response: () -> Unit){
        controllerScope.launch {
            withContext(Dispatchers.Main){
                response.invoke()
            }
        }
    }

    fun onDestroy() {
        controllerScope.cancel()
        view = null
    }

}