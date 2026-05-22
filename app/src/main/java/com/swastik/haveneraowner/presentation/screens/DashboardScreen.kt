package com.swastik.haveneraowner.presentation.screens

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.swastik.haveneraowner.presentation.viewModels.AuthViewModel
import org.koin.androidx.compose.koinViewModel

// Custom color scheme
object DashboardColors {
    val Background = Color(0xFFF5F5F5)
    val CardBackground = Color.White
    val PrimaryText = Color(0xFF333333)
    val SecondaryText = Color(0xFF666666)
    val AccentColor = Color(0xFFE57373)
    val ButtonColor = Color(0xFFD32F2F)
}


@Composable
fun DashboardScreen(
    navController: NavController,
    viewModel: AuthViewModel = koinViewModel()
) {

    val dashboardData by viewModel.ownerBookingsState.collectAsState()
    val Bookingtrenddata by viewModel.bookingTrendsState.collectAsState()
    LaunchedEffect(Unit) {
        viewModel.getOwnerBookings()
        viewModel.getBookingTrends()
    }
    //Log.d("ownerdata","Owner ${dashboardData.success?.body()?.data}")
    //Log.d("ownerdata","Owner ${dashboardData.success?.body()?.total}")
    Log.d("ownerdatas","Owner ${dashboardData.success?.body()}")
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(DashboardColors.Background)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .verticalScroll(rememberScrollState())
        ) {
            DashboardHeader()
            Spacer(modifier = Modifier.height(24.dp))
            TotalBookingsSection()
            Spacer(modifier = Modifier.height(24.dp))
            RevenueSummarySection()
            Spacer(modifier = Modifier.height(24.dp))
            PendingActionsSection()
            Spacer(modifier = Modifier.height(24.dp))
            BookingTrendsChart()
        }
    }
}

@Composable
fun DashboardHeader() {
    Text(
        text = "Dashboard",
        fontSize = 28.sp,
        fontWeight = FontWeight.Bold,
        color = DashboardColors.PrimaryText
    )
}


@Composable
fun TotalBookingsSection() {
    DashboardCard(title = "Total Bookings") {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            BookingStatItem("Daily", "42")
            BookingStatItem("Monthly", "1,256")
            BookingStatItem("Yearly", "15,230")
        }
    }
}

@Composable
fun RevenueSummarySection() {
    DashboardCard(title = "Revenue Summary") {
        Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
            RevenueSummaryItem("Daily", "$2,450")
            RevenueSummaryItem("Weekly", "$17,820")
            RevenueSummaryItem("Monthly", "$76,500")
        }
    }
}

@Composable
fun PendingActionsSection() {
    DashboardCard(title = "Pending Actions") {
        Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
            PendingActionItem("Unapproved Bookings", "12")
            PendingActionItem("Customer Inquiries", "8")
            Button(
                onClick = { /* Handle view all */ },
                colors = ButtonDefaults.buttonColors(containerColor = DashboardColors.ButtonColor),
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier.align(Alignment.End)
            ) {
                Text("View All")
            }
        }
    }
}

@Composable
fun BookingTrendsChart() {
    DashboardCard(title = "Booking Trends") {
        // Placeholder for the chart
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .background(Color.LightGray, RoundedCornerShape(8.dp)),
            contentAlignment = Alignment.Center
        ) {
            Text("Booking Trends Chart")
        }
    }
}

@Composable
fun DashboardCard(title: String, content: @Composable () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(DashboardColors.CardBackground, RoundedCornerShape(12.dp))
            .padding(16.dp)
    ) {
        Text(
            text = title,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = DashboardColors.PrimaryText,
            modifier = Modifier.padding(bottom = 16.dp)
        )
        content()
    }
}

@Composable
fun BookingStatItem(label: String, value: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(text = value, fontSize = 24.sp, fontWeight = FontWeight.Bold, color = DashboardColors.PrimaryText)
        Text(text = label, fontSize = 14.sp, color = DashboardColors.SecondaryText)
    }
}

@Composable
fun RevenueSummaryItem(period: String, amount: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = period, fontSize = 16.sp, color = DashboardColors.SecondaryText)
        Text(text = amount, fontSize = 18.sp, fontWeight = FontWeight.Bold, color = DashboardColors.PrimaryText)
    }
}

@Composable
fun PendingActionItem(action: String, count: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = action, fontSize = 16.sp, color = DashboardColors.SecondaryText)
        Text(
            text = count,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            color = DashboardColors.AccentColor,
            modifier = Modifier
                .background(
                    DashboardColors.AccentColor.copy(alpha = 0.1f),
                    RoundedCornerShape(4.dp)
                )
                .padding(horizontal = 8.dp, vertical = 4.dp)
        )
    }
}