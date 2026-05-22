package com.swastik.haveneraowner.presentation.screens

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavController
import com.swastik.haveneraowner.data.models.response.Booking
import com.swastik.haveneraowner.presentation.viewModels.AuthViewModel
import org.koin.androidx.compose.koinViewModel


@Composable
fun BookingsScreen(
    navController: NavController,
    viewModel: AuthViewModel = koinViewModel()
) {

    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    val rooms by viewModel.ownerBookingsState.collectAsState()
    val results = rooms.success?.body()?.results
    var showFilters by remember { mutableStateOf(false) }
    var selectedBooking by remember { mutableStateOf<Booking?>(null) }

    Log.d("BookingRooms", "BookingsScreen: $results")

    LaunchedEffect(Unit) {
        viewModel.getOwnerBookings()
    }

    when {
        rooms.isLoading -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator(color = Color.Red)
            }
        }

        rooms.error != null -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text(
                    text = "Error: ${rooms.error}",
                    color = Color.Red,
                    fontSize = 16.sp
                )
            }
        }

        rooms.success != null -> {

            Column(modifier = Modifier
                .fillMaxSize()
                .background(Color.White)) {
                // Header
                Text(
                    text = "Bookings",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth()
                        .align(Alignment.CenterHorizontally)
                )

                // Filter Row
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Filters",
                        fontSize = 16.sp,
                        color = Color.Red,
                        fontWeight = FontWeight.Medium,
                        modifier = Modifier.clickable { showFilters = true }
                    )
                }

                // Booking List
                LazyColumn(modifier = Modifier
                    .fillMaxSize()
                    .padding(8.dp)) {
                    items(results ?: emptyList()) { booking ->
                        BookingCard(
                            booking = booking,
                            onClick = { selectedBooking = booking }
                        )
                    }
//            items(results ?: emptyList()) { booking ->
//                BookingCard(
//                    booking = booking,
//                    onClick = { selectedBooking = booking }
//                )
//            }
                }

                // Filters Dialog
                if (showFilters) {
                    FiltersDialog(onDismiss = { showFilters = false })
                }

                // Detailed Booking View Dialog
                if (selectedBooking != null) {
                    BookingDetailsDialog(
                        booking = selectedBooking!!,
                        onDismiss = { selectedBooking = null },
                        onApprove = { /* Handle Approve */ },
                        onCancel = { /* Handle Cancel */ },
                        onModify = { /* Handle Modify */ }
                    )
                }
            }
        }

    }
}

@Composable
fun BookingCard(booking: Booking, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp, vertical = 10.dp)
            .clickable(onClick = onClick),
        elevation = CardDefaults.cardElevation(6.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            // Booking ID & Status at the top
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Booking ID
                Text(
                    text = "Booking #${booking.id}",
                    color = Color.Black,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )

                // Status Badge
                StatusBadge(status = "Pending")
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Guest and Room Info
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Column {
                    InfoRows(title = "Guest:", value = booking.user.first_name)
                    InfoRows(title = "Room:", value = booking.room.room_name)
                }
            }

            Spacer(modifier = Modifier.height(6.dp))

            // Dates Section
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Column {
                    InfoRows(title = "Check-in:", value = booking.check_in_date)
                    InfoRows(title = "Check-out:", value = booking.check_out_date)
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Payment Info
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    Text(
                        text = "Total Amount",
                        color = Color.Gray,
                        fontSize = 12.sp
                    )
                    Text(
                        text = booking.total_amount,
                        color = Color.Black,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )
                }

                Column(horizontalAlignment = Alignment.End) {
                    Text(
                        text = "Payment Method",
                        color = Color.Gray,
                        fontSize = 12.sp
                    )
                    Text(
                        text = booking.user.first_name,
                        color = Color.Black,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            // View More Section
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "View Details",
                    color = Color(0xFFD32F2F), // Red accent
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium
                )

                Icon(
                    imageVector = Icons.Default.KeyboardArrowRight,
                    contentDescription = "View Details",
                    tint = Color(0xFFD32F2F),
                    modifier = Modifier.size(22.dp)
                )
            }
        }
    }
}

@Composable
private fun StatusChip(status: String) {
    Box(
        modifier = Modifier
            .background(
                color = when (status) {
                    "Confirmed" -> Color(0xFFE6FFFA)
                    "Pending" -> Color(0xFFFEFCBF)
                    "Canceled" -> Color(0xFFFED7D7)
                    else -> Color.LightGray
                },
                shape = RoundedCornerShape(16.dp)
            )
            .padding(horizontal = 12.dp, vertical = 6.dp)
    ) {
        Text(
            text = status,
            color = when (status) {
                "Confirmed" -> Color(0xFF319795)
                "Pending" -> Color(0xFFB7791F)
                "Canceled" -> Color(0xFFC53030)
                else -> Color.DarkGray
            },
            fontSize = 12.sp,
            fontWeight = FontWeight.Medium
        )
    }
}

@Composable
private fun InfoRows(title: String, value: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 2.dp),
        verticalAlignment = Alignment.Top
    ) {
        Text(
            text = title,
            color = Color(0xFF718096),
            fontSize = 12.sp,
            modifier = Modifier.width(80.dp)
        )
        Text(
            text = value,
            color = Color(0xFF2D3748),
            fontSize = 13.sp,
            fontWeight = FontWeight.Medium
        )
    }
}



// Updated Booking data class
//data class Booking(
//    val id: String,
//    val userName: String,
//    val contactInfo: String,
//    val roomName: String,
//    val checkInDate: String,
//    val checkOutDate: String,
//    val totalAmount: Double,
//    val paymentMethod: String,
//    val status: String,
//    val bookingTime: String
//)

// Updated dummy data in BookingsScreen
//val bookings = listOf(
//    Booking(
//        id = "235689",
//        userName = "John Doe",
//        contactInfo = "john@example.com",
//        roomName = "Luxury Suite",
//        checkInDate = "2023-09-15",
//        checkOutDate = "2023-09-20",
//        totalAmount = 1200.0,
//        paymentMethod = "Credit Card",
//        status = "Confirmed",
//        bookingTime = "15 Aug 2023, 14:30"
//    ),
//    Booking(
//        id = "235690",
//        userName = "Jane Smith",
//        contactInfo = "jane@example.com",
//        roomName = "Deluxe Room",
//        checkInDate = "2023-09-18",
//        checkOutDate = "2023-09-22",
//        totalAmount = 850.0,
//        paymentMethod = "PayPal",
//        status = "Pending",
//        bookingTime = "16 Aug 2023, 10:15"
//    )
//)


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FiltersDialog(onDismiss: () -> Unit) {
    Dialog(onDismissRequest = onDismiss) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White, shape = RoundedCornerShape(16.dp))
                .padding(16.dp)
        ) {
            Text(
                text = "Filters",
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                color = Color.Black,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Date Range Filter
            Text(
                text = "Date Range",
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium,
                color = Color.Black
            )
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                OutlinedTextField(
                    value = "",
                    onValueChange = {},
                    label = { Text("Start Date") },
                    modifier = Modifier.weight(1f),
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = Color.Gray,
                        unfocusedBorderColor = Color.LightGray,
                        // textColor = Color.Black
                    )
                )
                Spacer(modifier = Modifier.width(8.dp))
                OutlinedTextField(
                    value = "",
                    onValueChange = {},
                    label = { Text("End Date") },
                    modifier = Modifier.weight(1f),
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = Color.Gray,
                        unfocusedBorderColor = Color.LightGray,
                        //  textColor = Color.Black
                    )
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Room Type Filter
            Text(
                text = "Room Type",
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium,
                color = Color.Black
            )
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(
                value = "",
                onValueChange = {},
                label = { Text("Select Room Type") },
                modifier = Modifier.fillMaxWidth(),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = Color.Gray,
                    unfocusedBorderColor = Color.LightGray,
                    //  textColor = Color.Black
                )
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Buttons for Actions
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Button(
                    onClick = onDismiss,
                    colors = ButtonDefaults.buttonColors(containerColor = Color.LightGray),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text(text = "Cancel", color = Color.Black, fontWeight = FontWeight.Bold)
                }
                Button(
                    onClick = onDismiss,
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Red),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text(text = "Apply", color = Color.White, fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}

@Composable
fun BookingDetailsDialog(
    booking: Booking,
    onDismiss: () -> Unit,
    onApprove: () -> Unit,
    onCancel: () -> Unit,
    onModify: () -> Unit
) {
    Dialog(onDismissRequest = onDismiss) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White, shape = RoundedCornerShape(16.dp))
                .padding(16.dp)
        ) {
            Column(
                modifier = Modifier.fillMaxWidth()
            ) {
                // Dialog Title
                Text(
                    text = "Booking Details",
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    color = Color.Black,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Guest Details Section
                Text(
                    text = "Guest Details",
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    color = Color.Black
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = "Name: ${booking.user_name}", fontSize = 14.sp, color = Color.Black)
                Text(text = "Contact: ${booking.user_phone}", fontSize = 14.sp, color = Color.DarkGray)
                Text(text = "ID: ${booking.user.id}", fontSize = 14.sp, color = Color.Black)

                Spacer(modifier = Modifier.height(16.dp))

                // Room Details Section
                Text(
                    text = "Room Details",
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    color = Color.Black
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = "Room Type: ${booking.id}", fontSize = 14.sp, color = Color.Black)
                Text(text = "Price: ${booking.total_amount}", fontSize = 14.sp, color = Color.Black)

                Spacer(modifier = Modifier.height(16.dp))

                // Action Buttons
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    // Approve Button
                    Button(
                        onClick = onApprove,
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF4CAF50)),
                        shape = RoundedCornerShape(8.dp),
                        modifier = Modifier.weight(1f)
                    ) {
                        Text(text = "Approve", color = Color.White, fontWeight = FontWeight.SemiBold,  maxLines = 1)
                    }
                    Spacer(modifier = Modifier.width(8.dp))

                    // Cancel Button
                    Button(
                        onClick = onCancel,
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFF44336)),
                        shape = RoundedCornerShape(8.dp),
                        modifier = Modifier.weight(1f)
                    ) {
                        Text(text = "Cancel", color = Color.White, fontWeight = FontWeight.SemiBold,  maxLines = 1)
                    }
                    Spacer(modifier = Modifier.width(8.dp))

                    // Modify Button
                    Button(
                        onClick = onModify,
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFFC107)),
                        shape = RoundedCornerShape(8.dp),
                        modifier = Modifier.weight(1f)
                    ) {
                        Text(text = "Modify", color = Color.Black, fontWeight = FontWeight.SemiBold,  maxLines = 1)
                    }
                }
            }
        }
    }
}


