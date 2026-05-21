package com.example.haveneraowner.utils

import java.text.SimpleDateFormat
import java.util.Locale



fun FormatToNormalDate(dateString: String): String {
    val inputFormat = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()) // or whatever you use
    val outputFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
    val date = inputFormat.parse(dateString)
    return outputFormat.format(date!!)
}
