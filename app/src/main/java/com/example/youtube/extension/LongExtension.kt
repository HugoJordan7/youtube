package com.example.youtube.extension

fun Long.formatTime(): String{
    val minutes = this / 1000 / 60
    val seconds = (this / 1000) % 60
    return String.format("%2d:%2d", minutes, seconds)
}