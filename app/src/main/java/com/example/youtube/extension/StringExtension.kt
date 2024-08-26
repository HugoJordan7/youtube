package com.example.youtube.extension

import java.lang.UnsupportedOperationException
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

fun String.toDate(): Date {
    return SimpleDateFormat("yyyy-mm-dd", Locale("pt", "BR")).parse(this)
        ?: throw UnsupportedOperationException("Invalid string format")
}