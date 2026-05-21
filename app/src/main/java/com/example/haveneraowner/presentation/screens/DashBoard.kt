package com.example.haveneraowner.presentation.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil3.compose.AsyncImage
import com.example.haveneraowner.R
import com.example.haveneraowner.presentation.navigation.Screen
import com.example.haveneraowner.presentation.viewModels.AuthViewModel
import org.koin.androidx.compose.koinViewModel
import java.util.Calendar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Dashboard(
    navController: NavController,
    viewModel: AuthViewModel = koinViewModel()
) {
    val profileStates by viewModel.profileState.collectAsState()
    val profile = profileStates.success?.body()
    val dashboardOverview by viewModel.dashboardOverviewState.collectAsState()
    val overview = dashboardOverview.success?.body()
    val RecentBookinfState by viewModel.recentBookingState.collectAsState()
    val recentBooking = RecentBookinfState.success?.body()

    LaunchedEffect(Unit) {
        viewModel.getProfile()
        viewModel.getDashboardOverview()
        viewModel.getRecentBookings()
    }

    // Determine time-of-day greeting
    val calendar = Calendar.getInstance()
    val greeting = when (calendar.get(Calendar.HOUR_OF_DAY)) {
        in 0..11 -> "Good Morning"
        in 12..16 -> "Good Afternoon"
        else -> "Good Evening"
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        "Havenera Partner",
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp,
                        color = Color(0xFF2D3748)
                    )
                },
                actions = {
                    IconButton(onClick = { /* Handle notification click */ }) {
                        Icon(
                            imageVector = Icons.Default.Notifications,
                            contentDescription = "Notifications",
                            tint = Color(0xFF718096)
                        )
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = Color(0xFFF8F9FA)
                )
            )
        },
        containerColor = Color(0xFFF8F9FA)
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            // Profile & Greeting Section
            item {
                Spacer(modifier = Modifier.height(8.dp))
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.White, RoundedCornerShape(16.dp))
                        .padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    val avatarModifier = Modifier
                        .size(60.dp)
                        .clip(CircleShape)
                        .background(Color(0xFFEDF2F7))

                    if (profile?.profile_image != null) {
                        AsyncImage(
                            model = profile.profile_image,
                            contentDescription = "Profile Picture",
                            modifier = avatarModifier,
                            contentScale = ContentScale.Crop
                        )
                    } else {
                        Image(
                            painter = painterResource(id = R.drawable.havenera),
                            contentDescription = "Profile Image",
                            modifier = avatarModifier,
                            contentScale = ContentScale.Crop
                        )
                    }

                    Spacer(modifier = Modifier.width(16.dp))

                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = greeting,
                            fontSize = 14.sp,
                            color = Color(0xFF718096),
                            fontWeight = FontWeight.Medium
                        )
                        Text(
                            text = profile?.first_name ?: "Partner",
                            fontSize = 22.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF1A202C),
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                }
            }

            // Statistics Section
            item {
                Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    Text(
                        text = "Overview",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF2D3748)
                    )

                    // Stats Grid Row 1
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        MetricCard(
                            title = "Total Bookings",
                            value = overview?.total_bookings?.toString() ?: "0",
                            icon = Icons.Default.DateRange,
                            gradient = Brush.linearGradient(
                                colors = listOf(Color(0xFFE53935), Color(0xFFB71C1C))
                            ),
                            modifier = Modifier.weight(1f)
                        )
                        MetricCard(
                            title = "Total Revenue",
                            value = "₹${overview?.total_revenue?.toInt() ?: 0}",
                            icon = Icons.Default.AttachMoney,
                            gradient = Brush.linearGradient(
                                colors = listOf(Color(0xFF1E88E5), Color(0xFF0D47A1))
                            ),
                            modifier = Modifier.weight(1f)
                        )
                    }

                    // Stats Grid Row 2
                    MetricCard(
                        title = "Occupancy Rate",
                        value = "${overview?.occupancy_rate ?: 0.0}%",
                        icon = Icons.Default.TrendingUp,
                        gradient = Brush.linearGradient(
                            colors = listOf(Color(0xFF7171D0), Color(0xFF390B67))
                        ),
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }

            // Quick Actions Section
            item {
                Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    Text(
                        text = "Quick Actions",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF2D3748)
                    )

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        ActionCard(
                            label = "Manage Booking",
                            icon = Icons.Default.DataExploration,
                            color = Color(0xFFFFEBEE),
                            iconColor = Color(0xFFC62828),
                            modifier = Modifier.weight(1f),
                            onClick = { navController.navigate(Screen.Booking.route) }
                        )
                        ActionCard(
                            label = "Manage Rooms",
                            icon = Icons.Default.RoomPreferences,
                            color = Color(0xFFE3F2FD),
                            iconColor = Color(0xFF1565C0),
                            modifier = Modifier.weight(1f),
                            onClick = { navController.navigate(Screen.Rooms.route) }
                        )
                        ActionCard(
                            label = "Manage Services",
                            icon = Icons.Default.Sensors,
                            color = Color(0xFFEDE7F6),
                            iconColor = Color(0xFF651FFF),
                            modifier = Modifier.weight(1f),
                            onClick = { navController.navigate(Screen.Services.route) }
                        )
                    }
                }
            }

            // Recent Bookings Section
            item {
                Text(
                    text = "Recent Bookings",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF2D3748)
                )
            }

            // Check if API booking data exists, else fallback to mock list
            if (recentBooking != null) {
                item {
                    RecentBookingItem(
                        guestName = "${recentBooking.user.first_name} ${recentBooking.user.last_name}",
                        roomType = recentBooking.room.room_name,
                        bookingId = "HN-${recentBooking.id}",
                        status = "Confirmed",
                        statusColor = Color(0xFF4CAF50)
                    )
                }
            } else {
                // Mock Bookings
                val mockBookings = listOf(
                    Triple("John Doe", "Deluxe Room", "HN-1049"),
                    Triple("Jane Smith", "Suite Room", "HN-1048"),
                    Triple("Michael Brown", "Standard Room", "HN-1047"),
                    Triple("Emily White", "Executive Room", "HN-1046")
                )
                items(mockBookings) { booking ->
                    RecentBookingItem(
                        guestName = booking.first,
                        roomType = booking.second,
                        bookingId = booking.third,
                        status = "Confirmed",
                        statusColor = Color(0xFF4CAF50)
                    )
                }
            }

            item {
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}

@Composable
fun MetricCard(
    title: String,
    value: String,
    icon: ImageVector,
    gradient: Brush,
    modifier: Modifier = Modifier
) {
    Card(
        shape = RoundedCornerShape(16.dp),
        modifier = modifier.height(120.dp),
        colors = CardDefaults.cardColors(containerColor = Color.Transparent),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(gradient)
                .padding(16.dp)
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = title,
                        color = Color.White.copy(alpha = 0.8f),
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Medium
                    )
                    Icon(
                        imageVector = icon,
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier.size(24.dp)
                    )
                }
                Text(
                    text = value,
                    color = Color.White,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

@Composable
fun ActionCard(
    label: String,
    icon: ImageVector,
    color: Color,
    iconColor: Color,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        modifier = modifier
            .height(110.dp)
            .clickable { onClick() }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Box(
                modifier = Modifier
                    .size(44.dp)
                    .clip(CircleShape)
                    .background(color),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = label,
                    tint = iconColor,
                    modifier = Modifier.size(20.dp)
                )
            }
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                text = label,
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF4A5568),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}

@Composable
fun RecentBookingItem(
    guestName: String,
    roomType: String,
    bookingId: String,
    status: String,
    statusColor: Color
) {
    Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Guest Avatar Placeholder
            Box(
                modifier = Modifier
                    .size(46.dp)
                    .clip(CircleShape)
                    .background(Color(0xFFF7FAFC)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.Person,
                    contentDescription = null,
                    tint = Color(0xFFA0AEC0),
                    modifier = Modifier.size(24.dp)
                )
            }

            Spacer(modifier = Modifier.width(14.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = guestName,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF2D3748)
                )
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    Text(
                        text = roomType,
                        fontSize = 13.sp,
                        color = Color(0xFF718096)
                    )
                    Text(
                        text = "•",
                        fontSize = 13.sp,
                        color = Color(0xFFA0AEC0)
                    )
                    Text(
                        text = bookingId,
                        fontSize = 13.sp,
                        color = Color(0xFF718096),
                        fontWeight = FontWeight.Medium
                    )
                }
            }

            // Status Badge
            Box(
                modifier = Modifier
                    .background(statusColor.copy(alpha = 0.1f), RoundedCornerShape(8.dp))
                    .padding(horizontal = 10.dp, vertical = 6.dp)
            ) {
                Text(
                    text = status,
                    color = statusColor,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}
