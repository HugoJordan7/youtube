package com.example.youtube.model

data class Publisher(
    val id: String,
    val name: String,
    val pictureProfileUrl: String
)

class PublisherBuilder{
    var id: String = ""
    var name: String = ""
    var pictureProfileUrl: String = ""

    fun build(): Publisher = Publisher(id, name, pictureProfileUrl)
}
