package com.example.haveneraowner.utils
import java.text.SimpleDateFormat
import java.util.Locale

fun formatToISO8601(date: String): String {
    val inputFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
    val outputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.getDefault())
    val parsedDate = inputFormat.parse(date)
    return outputFormat.format(parsedDate!!)
}
