package com.example.haveneraowner.presentation.screens

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.haveneraowner.presentation.viewModels.AuthViewModel
import org.koin.androidx.compose.koinViewModel
import java.text.SimpleDateFormat
import java.util.Locale


@Composable
fun WalletHistoryScreen(
    viewModel: AuthViewModel = koinViewModel()
) {
    val walletHistory by viewModel.withdrawalHistoryState.collectAsState()
    val history = walletHistory.success?.body()

    Log.d("bhhgvn", "WalletHistoryScreen: $history ")

    LaunchedEffect(Unit) {
        viewModel.getWithdrawalHistory()
    }

    val dummyTransactions = listOf(
        WalletTransaction(1, 5000.0, "Completed", "2025-02-15T14:30:00", "2025-02-16T10:15:00"),
        WalletTransaction(2, 2000.0, "Pending", "2025-02-16T09:45:00", ""),
        WalletTransaction(3, 1500.0, "Failed", "2025-02-14T18:20:00", "2025-02-14T19:00:00"),
        WalletTransaction(4, 7500.0, "Completed", "2025-02-13T08:30:00", "2025-02-13T12:45:00"),
    )

    Spacer(Modifier.height(25.dp))
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Wallet History", fontWeight = FontWeight.Bold) },
                backgroundColor = Color.White,
                elevation = 4.dp
            )
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .background(Color(0xFFF8F9FA))
        ) {
            items(dummyTransactions, key = { it.id }) { transaction ->
                WalletTransactionItem(transaction)
            }
        }
    }
}

@Composable
fun WalletTransactionItem(transaction: WalletTransaction) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)
            .clip(RoundedCornerShape(12.dp)),
        elevation = 4.dp,
        backgroundColor = Color.White
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            // Transaction ID & Amount
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text("Transaction ID: ${transaction.id}", fontSize = 14.sp, fontWeight = FontWeight.SemiBold, color = Color.Gray)
                Text(
                    text = "₹${transaction.amount}",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Status with color-coded badge
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                StatusBadge(status = transaction.status)

                Column(horizontalAlignment = Alignment.End) {
                    Text("Requested: ${formatDate(transaction.requestedAt)}", fontSize = 12.sp, color = Color.Gray)
                    if (transaction.proceededAt.isNotEmpty()) {
                        Text("Processed: ${formatDate(transaction.proceededAt)}", fontSize = 12.sp, color = Color.Gray)
                    }
                }
            }
        }
    }
}

@Composable
fun StatusBadge(status: String) {
    val statusColor = when (status) {
        "Completed" -> Color(0xFF4CAF50) // Green
        "Pending" -> Color(0xFFFFA726) // Orange
        "Failed" -> Color(0xFFD32F2F) // Red
        else -> Color.Gray
    }

    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(8.dp))
            .background(statusColor.copy(alpha = 0.2f))
            .padding(horizontal = 12.dp, vertical = 6.dp),
    ) {
        Text(text = status, color = statusColor, fontWeight = FontWeight.Medium, fontSize = 14.sp)
    }
}

// Helper function to format dates
fun formatDate(date: String): String {
    return try {
        val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault())
        val outputFormat = SimpleDateFormat("dd MMM yyyy, hh:mm a", Locale.getDefault())
        outputFormat.format(inputFormat.parse(date)!!)
    } catch (e: Exception) {
        date
    }
}

// Dummy Data Model
data class WalletTransaction(
    val id: Int,
    val amount: Double,
    val status: String,
    val requestedAt: String,
    val proceededAt: String
)
