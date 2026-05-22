package com.swastik.haveneraowner.utils

import android.app.DatePickerDialog
import android.content.Context
import kotlinx.coroutines.suspendCancellableCoroutine
import java.util.Calendar
import kotlin.coroutines.resume

suspend fun datePicker(context: Context): String = suspendCancellableCoroutine { continuation ->
    val calendar = Calendar.getInstance()
    val year = calendar.get(Calendar.YEAR)
    val month = calendar.get(Calendar.MONTH)
    val day = calendar.get(Calendar.DAY_OF_MONTH)

    val datePickerDialog = DatePickerDialog(
        context,
        { _, selectedYear, selectedMonth, selectedDayOfMonth ->
            val formattedDate = "${selectedDayOfMonth.toString().padStart(2, '0')}-${
                (selectedMonth + 1).toString().padStart(2, '0')
            }-$selectedYear"
            continuation.resume(formattedDate)
        },
        year,
        month,
        day
    )

    datePickerDialog.setOnCancelListener {
        continuation.resume("")
    }

    datePickerDialog.show()
}
