package com.example.youtube.extension

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

fun Date.formatted(): String{
    return SimpleDateFormat(
        "d MMM yyyy",
        Locale("pt", "BR")
    ).format(this)
}