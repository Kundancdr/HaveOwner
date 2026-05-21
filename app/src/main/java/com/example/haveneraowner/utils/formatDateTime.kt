package com.example.haveneraowner.utils
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone

fun formatDateTime(isoTime: String): String {
    // Input formatter (matches API format)
    val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
    inputFormat.timeZone = TimeZone.getTimeZone("UTC")

    // Output formatter (user-friendly format)
    val outputFormat = SimpleDateFormat("dd MMM yyyy, hh:mm a", Locale.getDefault())

    val date: Date = inputFormat.parse(isoTime)!!
    return outputFormat.format(date)
}
