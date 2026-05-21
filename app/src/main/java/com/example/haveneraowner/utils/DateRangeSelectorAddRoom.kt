package com.example.haveneraowner.utils

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun DateRangeSelectorAddRoom(
    fromDate: String,
    toDate: String,
    fromDateLabel : String = "From Date",
    toDateLabel : String = "To Date",
    onFromDateClick: () -> Unit,
    onToDateClick: () -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        DateSelectorFieldAddRoom(
            label = fromDateLabel,
            date = fromDate,
            onClick = onFromDateClick,
            modifier = Modifier.weight(1f)
        )

        DateSelectorFieldAddRoom(
            label = toDateLabel,
            date = toDate,
            onClick = onToDateClick,
            modifier = Modifier.weight(1f)
        )
    }
}