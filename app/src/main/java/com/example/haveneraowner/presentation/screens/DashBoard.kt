package com.example.haveneraowner.presentation.screens



import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil3.compose.AsyncImage
import com.example.haveneraowner.R
import com.example.haveneraowner.data.DataStoreManager
import com.example.haveneraowner.presentation.viewModels.AuthViewModel
import org.koin.androidx.compose.koinViewModel


@Composable
fun Dashboard(
    navController: NavController,
    viewModel: AuthViewModel = koinViewModel()
) {

    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    val profileStates by viewModel.profileState.collectAsState()
    val profile = profileStates.success?.body()
    val dashboardOverview by viewModel.dashboardOverviewState.collectAsState()
    val overview = dashboardOverview.success?.body()
    val RecentBookinfState by viewModel.recentBookingState.collectAsState()
    val recentBooking = RecentBookinfState.success?.body()

    LaunchedEffect(true) {
        viewModel.getProfile()
        viewModel.getDashboardOverview()
        viewModel.getRecentBookings()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF5F7FA))
            .padding(16.dp)
    ) {
        // Profile Section
        Spacer(Modifier.height(30.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (profile?.profile_image != null) {
                AsyncImage(
                    model = profile.profile_image,
                    contentDescription = "Profile Picture",
                    modifier = Modifier.size(50.dp),
                    contentScale = ContentScale.Crop
                )
            } else {
                Image(
                    painter = painterResource(id = R.drawable.havenera), // Replace with your profile image
                    contentDescription = "Profile Image",
                    modifier = Modifier.size(50.dp)
                )
            }

            Spacer(modifier = Modifier.width(10.dp))
            Column {

                Text(profile?.first_name ?: "", fontWeight = FontWeight.Bold, fontSize = 20.sp)
                Text("Welcome to Havenera", color = Color.Gray, fontSize = 14.sp)
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        // Statistics Cards
        Column(modifier = Modifier.fillMaxWidth()) {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
                StatsCard("Total Bookings", overview?.total_bookings.toString(), Color(0xFF4CAF50))
                StatsCard("Total Revenue", overview?.total_revenue.toString(), Color(0xFF2196F3))
            }
            Spacer(modifier = Modifier.height(12.dp))
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
                StatsCard("Occupy Rate", overview?.occupancy_rate.toString(), Color(0xFFFBC02D))
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        // Quick Actions
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
            ActionButton("Manage Booking", R.drawable.add)
            ActionButton("Manage Rooms", R.drawable.add)
            ActionButton("Manage Services", R.drawable.add)
        }

        Spacer(modifier = Modifier.height(20.dp))

        // Recent Bookings
        Text("Recent Bookings", fontWeight = FontWeight.Bold, fontSize = 18.sp)
        Spacer(modifier = Modifier.height(10.dp))
        LazyColumn(modifier = Modifier.fillMaxWidth()) {
            items(listOf(
                Pair("John Doe", "Deluxe Room"),
                Pair("Jane Smith", "Suite Room"),
                Pair("Michael Brown", "Standard Room"),
                Pair("Emily White", "Executive Room")
            )) { booking ->
                BookingItem(booking.first, booking.second, Color(0xFF4CAF50))
            }


//            item (recentBooking){
//                BookingItem(recentBooking?.room?.room_name ?: "" , recentBooking?.id.toString(), color = Color.Red )
//            }`
        }
    }
}

@Composable
fun StatsCard(title: String, amount: String, color: Color) {
    Card(
        shape = RoundedCornerShape(12.dp),
        backgroundColor = color,
        modifier = Modifier
            //.weight(1f)
            .padding(8.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(title, color = Color.White, fontSize = 14.sp)
            Text(amount, color = Color.White, fontSize = 18.sp, fontWeight = FontWeight.Bold)
        }
    }
}

@Composable
fun ActionButton(label: String, icon: Int) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Button(onClick = {}, shape = RoundedCornerShape(50),
            modifier = Modifier.size(60.dp),
            colors = ButtonDefaults.buttonColors(Color.Red),
        ) {
            Icon(painter = painterResource(id = icon), contentDescription = label, tint = Color.White)
        }
        Spacer(modifier = Modifier.height(8.dp))
        Text(label, fontSize = 14.sp)
    }
}

@Composable
fun BookingItem(name: String, roomType: String, color: Color) {
    Card(
        shape = RoundedCornerShape(10.dp),
        backgroundColor = Color.White,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp)
    ) {
        Row(
            modifier = Modifier.padding(12.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Text(name, fontSize = 16.sp, fontWeight = FontWeight.Medium)
                Text(roomType, fontSize = 14.sp, color = Color.Gray)
            }
            Text("Confirmed", fontSize = 16.sp, fontWeight = FontWeight.Bold, color = color)
        }
    }
}













//@OptIn(ExperimentalMaterial3Api::class)
//@Composable
//fun Dashboard() {
//    Scaffold(
//        topBar = {
//            TopAppBar(
//                title = { Text("Dashboard", fontWeight = FontWeight.Bold) },
//                colors = TopAppBarDefaults.topAppBarColors(
//                    containerColor = MaterialTheme.colorScheme.primaryContainer,
//                    titleContentColor = MaterialTheme.colorScheme.primary
//                )
//            )
//        },
//        content = { paddingValues ->
//            LazyColumn(
//                modifier = Modifier
//                    .padding(paddingValues)
//                    .fillMaxSize()
//                    .padding(16.dp),
//                verticalArrangement = Arrangement.spacedBy(16.dp)
//            ) {
//                // **Metric Section - Improved**
//                item {
//                    LazyVerticalGrid(
//                        columns = GridCells.Fixed(2),
//                        modifier = Modifier.fillMaxWidth(),
//                        horizontalArrangement = Arrangement.spacedBy(16.dp),
//                        verticalArrangement = Arrangement.spacedBy(16.dp)
//                    ) {
//                        item {
//                            MetricCard(
//                                title = "Total Bookings",
//                                value = "1,234",
//                                icon = Icons.Default.Event,
//                                backgroundColor = Color(0xFF4CAF50)
//                            )
//                        }
//                        item {
//                            MetricCard(
//                                title = "Total Revenue",
//                                value = "$12,345",
//                                icon = Icons.Default.AttachMoney,
//                                backgroundColor = Color(0xFF2196F3)
//                            )
//                        }
//                        item {
//                            MetricCard(
//                                title = "Occupancy Rate",
//                                value = "85%",
//                                icon = Icons.Default.BarChart,
//                                backgroundColor = Color(0xFFFF9800)
//                            )
//                        }
//                        item {
//                            MetricCard(
//                                title = "New Customers",
//                                value = "256",
//                                icon = Icons.Default.Person,
//                                backgroundColor = Color(0xFFFF5722)
//                            )
//                        }
//                    }
//                }
//
//                // **Booking Overview Section**
//                item {
//                    SectionCard("Booking Overview")
//                }
//
//                // **Revenue Overview Section**
//                item {
//                    SectionCard("Revenue Overview")
//                }
//
//                // **Recent Bookings Section**
//                item {
//                    Text(
//                        text = "Recent Bookings",
//                        fontSize = 18.sp,
//                        fontWeight = FontWeight.Bold,
//                        color = MaterialTheme.colorScheme.primary
//                    )
//                }
//                items(recentBookings) { booking ->
//                   // RecentBookingItem(booking)
//
//                }
//            }
//        }
//    )
//}
//
//// **New Improved Metric Card**
//@Composable
//fun MetricCard(title: String, value: String, icon: ImageVector, backgroundColor: Color) {
//    Card(
//        modifier = Modifier
//            .fillMaxWidth()
//            .height(140.dp),
//        elevation = CardDefaults.cardElevation(8.dp),
//        shape = RoundedCornerShape(16.dp),
//        colors = CardDefaults.cardColors(containerColor = backgroundColor)
//    ) {
//        Column(
//            modifier = Modifier
//                .fillMaxSize()
//                .padding(16.dp),
//            verticalArrangement = Arrangement.Center,
//            horizontalAlignment = Alignment.CenterHorizontally
//        ) {
//            Icon(
//                imageVector = icon,
//                contentDescription = null,
//                tint = Color.White,
//                modifier = Modifier.size(32.dp)
//            )
//            Spacer(modifier = Modifier.height(8.dp))
//            Text(
//                text = title,
//                fontSize = 14.sp,
//                fontWeight = FontWeight.SemiBold,
//                color = Color.White
//            )
//            Spacer(modifier = Modifier.height(4.dp))
//            Text(
//                text = value,
//                fontSize = 22.sp,
//                fontWeight = FontWeight.Bold,
//                color = Color.White
//            )
//        }
//    }
//}
//
//// **Reused Section Card for Overview Sections**
//@Composable
//fun SectionCard(title: String) {
//    Card(
//        modifier = Modifier.fillMaxWidth(),
//        elevation = CardDefaults.cardElevation(6.dp),
//        shape = RoundedCornerShape(12.dp),
//        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
//    ) {
//        Column(
//            modifier = Modifier.padding(16.dp)
//        ) {
//            Text(
//                text = title,
//                fontSize = 18.sp,
//                fontWeight = FontWeight.Bold,
//                color = MaterialTheme.colorScheme.primary
//            )
//            Spacer(modifier = Modifier.height(8.dp))
//            Box(
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .height(200.dp)
//                    .clip(RoundedCornerShape(8.dp))
//                    .background(Color.LightGray)
//            )
//        }
//    }
//}




//@OptIn(ExperimentalMaterial3Api::class)
//@Composable
//fun Dashboard() {
//    Scaffold(
//        topBar = {
//            TopAppBar(
//                title = { Text("Dashboard", fontWeight = FontWeight.Bold) },
//                colors = TopAppBarDefaults.topAppBarColors(
//                    containerColor = MaterialTheme.colorScheme.primaryContainer,
//                    titleContentColor = MaterialTheme.colorScheme.primary
//                )
//            )
//        },
//        content = { paddingValues ->
//            LazyColumn(
//                modifier = Modifier
//                    .padding(paddingValues)
//                    .fillMaxSize()
//                    .padding(16.dp),
//                verticalArrangement = Arrangement.spacedBy(16.dp)
//            ) {
//                // **Metric Section - Improved**
//                item {
//                    LazyVerticalGrid(
//                        columns = GridCells.Fixed(2),
//                        modifier = Modifier.fillMaxWidth(),
//                        horizontalArrangement = Arrangement.spacedBy(16.dp),
//                        verticalArrangement = Arrangement.spacedBy(16.dp)
//                    ) {
//                        item {
//                            MetricCard(
//                                title = "Total Bookings",
//                                value = "1,234",
//                                icon = Icons.Default.Event,
//                                backgroundColor = Color(0xFF4CAF50)
//                            )
//                        }
//                        item {
//                            MetricCard(
//                                title = "Total Revenue",
//                                value = "$12,345",
//                                icon = Icons.Default.AttachMoney,
//                                backgroundColor = Color(0xFF2196F3)
//                            )
//                        }
//                        item {
//                            MetricCard(
//                                title = "Occupancy Rate",
//                                value = "85%",
//                                icon = Icons.Default.BarChart,
//                                backgroundColor = Color(0xFFFF9800)
//                            )
//                        }
//                        item {
//                            MetricCard(
//                                title = "New Customers",
//                                value = "256",
//                                icon = Icons.Default.Person,
//                                backgroundColor = Color(0xFFFF5722)
//                            )
//                        }
//                    }
//                }
//
//                // **Booking Overview Section**
//                item {
//                    SectionCard("Booking Overview")
//                }
//
//                // **Revenue Overview Section**
//                item {
//                    SectionCard("Revenue Overview")
//                }
//
//                // **Recent Bookings Section**
//                item {
//                    Text(
//                        text = "Recent Bookings",
//                        fontSize = 18.sp,
//                        fontWeight = FontWeight.Bold,
//                        color = MaterialTheme.colorScheme.primary
//                    )
//                }
//                items(recentBookings) { booking ->
//                    RecentBookingItem(booking)
//                }
//            }
//        }
//    )
//}
//
//// **New Improved Metric Card**
//@Composable
//fun MetricCard(title: String, value: String, icon: ImageVector, backgroundColor: Color) {
//    Card(
//        modifier = Modifier
//            .fillMaxWidth()
//            .height(140.dp),
//        elevation = CardDefaults.cardElevation(8.dp),
//        shape = RoundedCornerShape(16.dp),
//        colors = CardDefaults.cardColors(containerColor = backgroundColor)
//    ) {
//        Column(
//            modifier = Modifier
//                .fillMaxSize()
//                .padding(16.dp),
//            verticalArrangement = Arrangement.Center,
//            horizontalAlignment = Alignment.CenterHorizontally
//        ) {
//            Icon(
//                imageVector = icon,
//                contentDescription = null,
//                tint = Color.White,
//                modifier = Modifier.size(32.dp)
//            )
//            Spacer(modifier = Modifier.height(8.dp))
//            Text(
//                text = title,
//                fontSize = 14.sp,
//                fontWeight = FontWeight.SemiBold,
//                color = Color.White
//            )
//            Spacer(modifier = Modifier.height(4.dp))
//            Text(
//                text = value,
//                fontSize = 22.sp,
//                fontWeight = FontWeight.Bold,
//                color = Color.White
//            )
//        }
//    }
//}
//
//// **Reused Section Card for Overview Sections**
//@Composable
//fun SectionCard(title: String) {
//    Card(
//        modifier = Modifier.fillMaxWidth(),
//        elevation = CardDefaults.cardElevation(6.dp),
//        shape = RoundedCornerShape(12.dp),
//        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
//    ) {
//        Column(
//            modifier = Modifier.padding(16.dp)
//        ) {
//            Text(
//                text = title,
//                fontSize = 18.sp,
//                fontWeight = FontWeight.Bold,
//                color = MaterialTheme.colorScheme.primary
//            )
//            Spacer(modifier = Modifier.height(8.dp))
//            Box(
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .height(200.dp)
//                    .clip(RoundedCornerShape(8.dp))
//                    .background(Color.LightGray)
//            )
//        }
//    }
//}
//
//
//@Composable
//fun RecentBookingItem(booking: Booking) {
//    Card(
//        modifier = Modifier
//            .fillMaxWidth()
//            .padding(vertical = 4.dp),
//        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
//    ) {
//        Row(
//            modifier = Modifier.padding(16.dp),
//            verticalAlignment = Alignment.CenterVertically,
//            horizontalArrangement = Arrangement.SpaceBetween
//        ) {
//            Column {
//                Text(
//                    text = booking.guestName,
//                    fontSize = 16.sp,
//                    fontWeight = FontWeight.Bold
//                )
//                Text(
//                    text = booking.roomNumber,
//                    fontSize = 14.sp,
//                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
//                )
//            }
//            Icon(
//                imageVector = Icons.Default.ArrowForward,
//                contentDescription = "View Details"
//            )
//        }
//    }
//}

data class Booking(
    val guestName: String,
    val roomNumber: String
)

val recentBookings = listOf(
    Booking("John Doe", "Room 101"),
    Booking("Jane Smith", "Room 202"),
    Booking("Alice Johnson", "Room 303")
)





//import androidx.compose.foundation.background
//import androidx.compose.foundation.clickable
//import androidx.compose.foundation.layout.Arrangement
//import androidx.compose.foundation.layout.Column
//import androidx.compose.foundation.layout.*
//import androidx.compose.foundation.layout.FlowColumnScopeInstance.weight
//import androidx.compose.foundation.layout.Row
//import androidx.compose.foundation.layout.Spacer
//import androidx.compose.foundation.layout.fillMaxSize
//import androidx.compose.foundation.layout.fillMaxWidth
//import androidx.compose.foundation.layout.height
//import androidx.compose.foundation.layout.padding
//import androidx.compose.foundation.layout.size
//import androidx.compose.foundation.layout.width
//import androidx.compose.foundation.shape.RoundedCornerShape
//import androidx.compose.material.icons.Icons
//import androidx.compose.material.icons.filled.Add
//import androidx.compose.material.icons.filled.Assessment
//import androidx.compose.material.icons.filled.AttachMoney
//import androidx.compose.material.icons.filled.CalendarMonth
//import androidx.compose.material.icons.filled.Hotel
//import androidx.compose.material.icons.filled.KeyboardArrowRight
//import androidx.compose.material.icons.filled.MeetingRoom
//import androidx.compose.material.icons.filled.Person
//import androidx.compose.material3.Card
//import androidx.compose.material3.CardDefaults
//import androidx.compose.material3.Icon
//import androidx.compose.material3.MaterialTheme
//import androidx.compose.material3.Text
//import androidx.compose.runtime.Composable
//import androidx.compose.runtime.mutableStateOf
//import androidx.compose.runtime.remember
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.graphics.vector.ImageVector
//import androidx.compose.ui.text.font.FontWeight
//import androidx.compose.ui.unit.dp
//import androidx.compose.ui.unit.sp
//
//
//@Composable
//fun Dashboard() {
//    val totalBookings = remember { mutableStateOf(320) }
//    val totalRevenue = remember { mutableStateOf(1_200_000) }
//    val occupancyRate = remember { mutableStateOf(85) }
//    val activeUsers = remember { mutableStateOf(520) }
//
//    Column(
//        modifier = Modifier
//            .fillMaxSize()
//            .background(Color(0xFFF5F5F5)) // Light background
//            .padding(16.dp)
//    ) {
//        Text(
//            text = "📊 Dashboard Overview",
//            style = MaterialTheme.typography.headlineMedium,
//            fontWeight = FontWeight.Bold,
//            color = Color(0xFF37474F), // Dark text
//            modifier = Modifier.padding(bottom = 16.dp)
//        )
//
//        // **Top Statistics Row**
//        Row(
//            modifier = Modifier.fillMaxWidth(),
//            horizontalArrangement = Arrangement.spacedBy(16.dp)
//        ) {
//            DashboardCard("Total Bookings", totalBookings.value.toString(), Icons.Default.CalendarMonth, Color(0xFF1E88E5),modifier = Modifier.weight(1f))
//            DashboardCard("Total Revenue", "₹${totalRevenue.value}", Icons.Default.AttachMoney, Color(0xFF43A047))
//        }
//
//        Spacer(modifier = Modifier.height(16.dp))
//
//        Row(
//            modifier = Modifier.fillMaxWidth(),
//            horizontalArrangement = Arrangement.spacedBy(16.dp)
//        ) {
//            DashboardCard("Occupancy Rate", "${occupancyRate.value}%", Icons.Default.Assessment, Color(0xFFF57C00))
//            DashboardCard("Active Users", activeUsers.value.toString(), Icons.Default.Person, Color(0xFF6A1B9A))
//        }
//
//        Spacer(modifier = Modifier.height(24.dp))
//
//        // **Detailed Sections**
//        SectionCard(title = "Recent Bookings") { RecentBookingsList() }
//        SectionCard(title = "Revenue Breakdown") { RevenueSummary() }
//        SectionCard(title = "Quick Actions") { ActionButtons() }
//    }
//}
//
//// 📌 **Dashboard Statistic Card**
//@Composable
//fun DashboardCard(title: String, value: String, icon: ImageVector, bgColor: Color, modifier: Modifier = Modifier) {
//    Card(
//        modifier = Modifier
//            .height(120.dp),
//        shape = RoundedCornerShape(16.dp),
//        colors = CardDefaults.cardColors(containerColor = bgColor),
//        elevation = CardDefaults.cardElevation(8.dp)
//    ) {
//        Column(
//            modifier = Modifier.padding(16.dp),
//            verticalArrangement = Arrangement.SpaceBetween
//        ) {
//            Icon(icon, contentDescription = title, tint = Color.White, modifier = Modifier.size(32.dp))
//            Text(title, color = Color.White, fontSize = 14.sp, fontWeight = FontWeight.Medium)
//            Text(value, color = Color.White, fontSize = 20.sp, fontWeight = FontWeight.Bold)
//        }
//    }
//}
//
//// 📌 **Section Card Wrapper**
//@Composable
//fun SectionCard(title: String, content: @Composable () -> Unit) {
//    Card(
//        modifier = Modifier
//            .fillMaxWidth()
//            .padding(vertical = 8.dp),
//        shape = RoundedCornerShape(16.dp),
//        elevation = CardDefaults.cardElevation(4.dp)
//    ) {
//        Column(modifier = Modifier.padding(16.dp)) {
//            Text(title, fontSize = 18.sp, fontWeight = FontWeight.SemiBold, color = Color(0xFF37474F))
//            Spacer(modifier = Modifier.height(8.dp))
//            content()
//        }
//    }
//}
//
//// 📌 **Recent Bookings Section**
//@Composable
//fun RecentBookingsList() {
//    val bookings = listOf(
//        "Room 101 - John Doe",
//        "Room 202 - Alice Smith",
//        "Room 305 - Robert Brown",
//        "Room 412 - Emma Johnson"
//    )
//
//    Column {
//        bookings.forEach { booking ->
//            Row(
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .padding(vertical = 6.dp),
//                verticalAlignment = Alignment.CenterVertically
//            ) {
//                Icon(Icons.Default.Hotel, contentDescription = "Booking", tint = Color.Gray)
//                Spacer(modifier = Modifier.width(8.dp))
//                Text(booking, fontSize = 16.sp)
//                Spacer(modifier = Modifier.weight(1f))
//                Icon(Icons.Default.KeyboardArrowRight, contentDescription = "View", tint = Color.Gray)
//            }
//        }
//    }
//}
//
//// 📌 **Revenue Summary Section**
//@Composable
//fun RevenueSummary() {
//    Row(
//        modifier = Modifier.fillMaxWidth(),
//        horizontalArrangement = Arrangement.SpaceBetween
//    ) {
//        RevenueCard("Today", "₹25,000")
//        RevenueCard("This Week", "₹1,75,000")
//        RevenueCard("This Month", "₹6,80,000")
//    }
//}
//
//// 📌 **Revenue Card**
//@Composable
//fun RevenueCard(title: String, amount: String) {
//    Column(horizontalAlignment = Alignment.CenterHorizontally) {
//        Text(title, fontSize = 14.sp, fontWeight = FontWeight.Medium)
//        Text(amount, fontSize = 18.sp, fontWeight = FontWeight.Bold, color = Color(0xFF43A047))
//    }
//}
//
//// 📌 **Quick Actions Section**
//@Composable
//fun ActionButtons() {
//    Row(
//        modifier = Modifier.fillMaxWidth(),
//        horizontalArrangement = Arrangement.SpaceEvenly
//    ) {
//        ActionButton("Add Booking", Icons.Default.Add)
//        ActionButton("Manage Rooms", Icons.Default.MeetingRoom)
//        ActionButton("View Reports", Icons.Default.Assessment)
//    }
//}
//
//// 📌 **Individual Action Button**
//@Composable
//fun ActionButton(title: String, icon: ImageVector) {
//    Column(
//        horizontalAlignment = Alignment.CenterHorizontally,
//        modifier = Modifier
//            .padding(8.dp)
//            .clickable { /* Handle click action */ }
//    ) {
//        Icon(icon, contentDescription = title, tint = Color(0xFF1976D2), modifier = Modifier.size(36.dp))
//        Spacer(modifier = Modifier.height(4.dp))
//        Text(title, fontSize = 14.sp)
//    }
//}











//import androidx.compose.foundation.background
//import androidx.compose.foundation.clickable
//import androidx.compose.foundation.layout.*
//import androidx.compose.foundation.rememberScrollState
//import androidx.compose.foundation.shape.RoundedCornerShape
//import androidx.compose.foundation.verticalScroll
//import androidx.compose.material.*
//import androidx.compose.material.DrawerDefaults.backgroundColor
//import androidx.compose.material.icons.Icons
//import androidx.compose.material.icons.filled.Add
//import androidx.compose.material.icons.filled.ArrowForward
//import androidx.compose.material.icons.filled.Assessment
//import androidx.compose.material.icons.filled.BarChart
//import androidx.compose.material.icons.filled.DateRange
//import androidx.compose.material.icons.filled.Hotel
//import androidx.compose.material.icons.filled.MeetingRoom
//import androidx.compose.material.icons.filled.Money
//import androidx.compose.material.icons.filled.Person
//import androidx.compose.material3.CardDefaults
//import androidx.compose.runtime.Composable
//import androidx.compose.runtime.mutableStateOf
//import androidx.compose.runtime.remember
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.graphics.vector.ImageVector
//import androidx.compose.ui.text.font.FontWeight
//import androidx.compose.ui.unit.dp
//import androidx.compose.ui.unit.sp
//
//@Composable
//fun Dashboard() {
//    val totalBookings = remember { mutableStateOf(320) }
//    val totalRevenue = remember { mutableStateOf(1_200_000) }
//    val occupancyRate = remember { mutableStateOf(85) }
//
//    Column(
//        modifier = Modifier
//            .fillMaxSize()
//            .verticalScroll(rememberScrollState())
//            .padding(16.dp)
//    ) {
//        Text(
//            text = "Dashboard",
//            style = MaterialTheme.typography.h5,
//            fontWeight = FontWeight.Bold,
//            modifier = Modifier.padding(bottom = 16.dp)
//        )
//
//        // Statistics Row
//        Row(
//            modifier = Modifier.fillMaxWidth(),
//            horizontalArrangement = Arrangement.SpaceBetween
//        ) {
//            DashboardCard("Total Bookings", totalBookings.value.toString(), Icons.Default.DateRange, Color(0xFF1E88E5))
//            DashboardCard("Total Revenue", "₹${totalRevenue.value}", Icons.Default.Money, Color(0xFF43A047))
//        }
//
//        Spacer(modifier = Modifier.height(16.dp))
//
//        Row(
//            modifier = Modifier.fillMaxWidth(),
//            horizontalArrangement = Arrangement.SpaceBetween
//        ) {
//            DashboardCard("Occupancy Rate", "${occupancyRate.value}%", Icons.Default.BarChart, Color(0xFFF57C00))
//            DashboardCard("Active Users", "520", Icons.Default.Person, Color(0xFF6A1B9A))
//        }
//
//        Spacer(modifier = Modifier.height(24.dp))
//
//        // Booking Details Section
//        SectionTitle("Recent Bookings")
//        RecentBookingsList()
//
//        Spacer(modifier = Modifier.height(24.dp))
//
//        // Revenue Summary Section
//        SectionTitle("Revenue Summary")
//        RevenueSummary()
//
//        Spacer(modifier = Modifier.height(24.dp))
//
//        // Action Buttons Section
//        SectionTitle("Quick Actions")
//        ActionButtons()
//    }
//}
//
//// 📌 Dashboard Statistic Card
//@Composable
//fun DashboardCard(title: String, value: String, icon: ImageVector, color: Color) {
//    Card(
//        modifier = Modifier
//           // .weight(1f)
//            .padding(8.dp)
//            .height(100.dp),
//        shape = RoundedCornerShape(12.dp),
//        backgroundColor = MaterialTheme.colors. surface,
//        elevation = 1.dp,
//      //  elevation = CardDefaults.elevatedCardElevation(defaultElevation = 6.dp)
//    ) {
//        Column(
//            modifier = Modifier
//                .fillMaxSize()
//                .padding(12.dp),
//            verticalArrangement = Arrangement.SpaceBetween
//        ) {
//            Icon(icon, contentDescription = title, tint = Color.White, modifier = Modifier.size(24.dp))
//            Text(title, color = Color.White, fontSize = 14.sp, fontWeight = FontWeight.Medium)
//            Text(value, color = Color.White, fontSize = 18.sp, fontWeight = FontWeight.Bold)
//        }
//    }
//}
//
//// 📌 Section Title
//@Composable
//fun SectionTitle(title: String) {
//    Text(
//        text = title,
//        style = MaterialTheme.typography.h4,
//        fontWeight = FontWeight.SemiBold,
//        modifier = Modifier.padding(bottom = 8.dp)
//    )
//}
//
//// 📌 Recent Bookings List (Dummy Data)
//@Composable
//fun RecentBookingsList() {
//    val bookings = listOf(
//        "Room 101 - John Doe",
//        "Room 202 - Alice Smith",
//        "Room 305 - Robert Brown",
//        "Room 412 - Emma Johnson"
//    )
//
//    Column(
//        modifier = Modifier
//            .fillMaxWidth()
//            .background(Color.White, shape = RoundedCornerShape(12.dp))
//            .padding(16.dp)
//    ) {
//        bookings.forEach { booking ->
//            Row(
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .padding(vertical = 6.dp),
//                verticalAlignment = Alignment.CenterVertically
//            ) {
//                Icon(Icons.Default.Hotel, contentDescription = "Booking", tint = Color.Gray)
//                Spacer(modifier = Modifier.width(8.dp))
//                Text(booking, fontSize = 16.sp)
//                Spacer(modifier = Modifier.weight(1f))
//                Icon(Icons.Default.ArrowForward, contentDescription = "View", tint = Color.Gray)
//            }
//        }
//    }
//}
//
//// 📌 Revenue Summary Section
//@Composable
//fun RevenueSummary() {
//    Column(
//        modifier = Modifier
//            .fillMaxWidth()
//            .background(Color.White, shape = RoundedCornerShape(12.dp))
//            .padding(16.dp)
//    ) {
//        Row(
//            modifier = Modifier.fillMaxWidth(),
//            horizontalArrangement = Arrangement.SpaceBetween
//        ) {
//            RevenueCard("Today", "₹25,000")
//            RevenueCard("This Week", "₹1,75,000")
//            RevenueCard("This Month", "₹6,80,000")
//        }
//    }
//}
//
//// 📌 Revenue Card
//@Composable
//fun RevenueCard(title: String, amount: String) {
//    Column(horizontalAlignment = Alignment.CenterHorizontally) {
//        Text(title, fontSize = 14.sp, fontWeight = FontWeight.Medium)
//        Text(amount, fontSize = 18.sp, fontWeight = FontWeight.Bold, color = Color(0xFF43A047))
//    }
//}
//
//// 📌 Quick Actions Section
//@Composable
//fun ActionButtons() {
//    Row(
//        modifier = Modifier.fillMaxWidth(),
//        horizontalArrangement = Arrangement.SpaceEvenly
//    ) {
//        ActionButton("Add Booking", Icons.Default.Add)
//        ActionButton("Manage Rooms", Icons.Default.MeetingRoom)
//        ActionButton("View Reports", Icons.Default.Assessment)
//    }
//}
//
//// 📌 Individual Action Button
//@Composable
//fun ActionButton(title: String, icon: ImageVector) {
//    Column(
//        horizontalAlignment = Alignment.CenterHorizontally,
//        modifier = Modifier
//            .padding(8.dp)
//            .clickable { /* Handle click action */ }
//    ) {
//        Icon(icon, contentDescription = title, tint = Color(0xFF1976D2), modifier = Modifier.size(36.dp))
//        Spacer(modifier = Modifier.height(4.dp))
//        Text(title, fontSize = 14.sp)
//    }
//}




//import androidx.compose.foundation.background
//import androidx.compose.foundation.layout.*
//import androidx.compose.foundation.shape.RoundedCornerShape
//import androidx.compose.material.*
//import androidx.compose.runtime.Composable
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.text.font.FontWeight
//import androidx.compose.ui.unit.dp
//import androidx.compose.ui.unit.sp
//import androidx.compose.foundation.layout.weight
//import io.github.bytebeats.compose.charts.bar.BarChart
//import io.github.bytebeats.compose.charts.bar.model.BarChartEntry
//import io.github.bytebeats.compose.charts.bar.model.BarChartData
//import io.github.bytebeats.compose.charts.bar.renderer.axis.rememberAxisRenderer
//import io.github.bytebeats.compose.charts.bar.renderer.label.rememberLabelRenderer
//
//@Composable
//fun Dashboard() {
//    Scaffold(
//        topBar = {
//            TopAppBar(
//                title = { Text("Dashboard", fontWeight = FontWeight.Bold) },
//                backgroundColor = Color.White,
//                elevation = 4.dp
//            )
//        }
//    ) { padding ->
//        Column(
//            modifier = Modifier
//                .fillMaxSize()
//                .padding(padding)
//                .background(Color(0xFFF8F9FA))
//                .padding(16.dp)
//        ) {
//            // Summary Cards
//            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
//                SummaryCard(title = "Total Bookings", value = "234")
//                SummaryCard(title = "Total Revenue", value = "$12,340")
//            }
//
//            Spacer(modifier = Modifier.height(16.dp))
//
//            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
//                SummaryCard(title = "Occupancy Rate", value = "78%")
//            }
//
//            Spacer(modifier = Modifier.height(24.dp))
//
//            // Graphs Section
//            Text(text = "Bookings Overview", fontSize = 18.sp, fontWeight = FontWeight.Bold)
//            Spacer(modifier = Modifier.height(8.dp))
//            BookingChart()
//
//            Spacer(modifier = Modifier.height(24.dp))
//
//            Text(text = "Revenue Overview", fontSize = 18.sp, fontWeight = FontWeight.Bold)
//            Spacer(modifier = Modifier.height(8.dp))
//            RevenueChart()
//        }
//    }
//}
//
//// Summary Card Composable
//@Composable
//fun SummaryCard(title: String, value: String) {
//    Card(
//        modifier = Modifier
//           // .weight(1f)
//            .padding(8.dp),
//        elevation = 4.dp,
//        shape = RoundedCornerShape(12.dp),
//        backgroundColor = Color.White
//    ) {
//        Column(
//            modifier = Modifier.padding(16.dp),
//            horizontalAlignment = Alignment.CenterHorizontally
//        ) {
//            Text(title, fontSize = 14.sp, color = Color.Gray, fontWeight = FontWeight.Medium)
//            Spacer(modifier = Modifier.height(8.dp))
//            Text(value, fontSize = 20.sp, fontWeight = FontWeight.Bold, color = Color.Black)
//        }
//    }
//}
//
//// Dummy Bookings Chart
//@Composable
//fun BookingChart() {
//    val data = listOf(
//        BarChartEntry(x = 1f, y = 50f),
//        BarChartEntry(x = 2f, y = 70f),
//        BarChartEntry(x = 3f, y = 100f),
//        BarChartEntry(x = 4f, y = 120f),
//        BarChartEntry(x = 5f, y = 90f)
//    )
//
//    BarChart(
//        barChartData = BarChartData(
//            series = listOf(BarChartSeries(data, Color(0xFF1976D2)))
//        ),
//        modifier = Modifier
//            .fillMaxWidth()
//            .height(200.dp)
//    )
//}
//
//// Dummy Revenue Chart
//@Composable
//fun RevenueChart() {
//    val data = listOf(
//        BarChartEntry(x = 1f, y = 500f),
//        BarChartEntry(x = 2f, y = 700f),
//        BarChartEntry(x = 3f, y = 900f),
//        BarChartEntry(x = 4f, y = 1200f),
//        BarChartEntry(x = 5f, y = 1000f)
//    )
//
//    BarChart(
//        barChartData = BarChartData(
//            series = listOf(BarChartSeries(data, Color(0xFFFFA726)))
//        ),
//        modifier = Modifier
//            .fillMaxWidth()
//            .height(200.dp)
//    )
//}
