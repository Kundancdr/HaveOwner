package com.example.haveneraowner.presentation.screens

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.ArrowForwardIos
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Help
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material.icons.filled.Map
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Reviews
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavController
import coil3.compose.AsyncImage
import com.example.haveneraowner.data.DataStoreManager
import com.example.haveneraowner.presentation.navigation.Screen
import com.example.haveneraowner.presentation.viewModels.AuthViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel


@Composable
fun ProfileScreen(
    viewModel: AuthViewModel = koinViewModel(),
    navController: NavController,
    onFeatureClick: (String) -> Unit = {navController.navigate(Screen.WalletHistory.route)},
    onMapClick: () -> Unit = {},
    onReviewClick: () -> Unit = {navController.navigate(Screen.Reviews.route)}
) {

    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    var showLogoutDialog by remember { mutableStateOf(false) }
    val dataStore = remember { DataStoreManager(context) }
    val profileStates by viewModel.profileState.collectAsState()
    val profile = profileStates.success?.body()
    val walletState by viewModel.walletState.collectAsState()
    val wallet = walletState.success?.body()
    val walletHiss by viewModel.withdrawalHistoryState.collectAsState()
    //val history = walletHiss.success?.body()


    if (wallet != null) {
       // Log.d("profileData","profile ${wallet.balance}")
       // Log.d("profileData","profile ${wallet.total_earning}")

    }


    LaunchedEffect(true) {
        viewModel.getProfile()
        viewModel.getWallet()
        viewModel.getWithdrawalHistory()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color(0xFFF7F9FC))
            .padding(16.dp)
            .padding(top = 8.dp)
    ) {

        Card(
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier
                .fillMaxWidth()
                 .clickable { navController.navigate(Screen.ProfileUpdate.route) }
                .padding(bottom = 8.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(80.dp)
                        .clip(CircleShape)
                        .background(Color(0xFFE0E0E0))
                ) {
                    if (profile?.profile_image != null) {
                        AsyncImage(
                            model = profile.profile_image,
                            contentDescription = "Profile Picture",
                            modifier = Modifier.fillMaxSize(),
                            contentScale = ContentScale.Crop
                        )
                    } else {
                        Icon(
                            imageVector = Icons.Default.Person,
                            contentDescription = "Default Profile Icon",
                            tint = Color.White,
                            modifier = Modifier
                                .size(40.dp)
                                .align(Alignment.Center)
                        )
                    }
                }

                Spacer(modifier = Modifier.width(16.dp))

                Column {

                    Text(
                        text = profile?.first_name ?: "",
                        style = MaterialTheme.typography.headlineSmall.copy(
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF212121)
                        )
                    )

                    Text(
                        text = profile?.email ?: "",
                        style = MaterialTheme.typography.bodyMedium.copy(color = Color(0xFF757575))
                    )

                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End

                ) {
                    Icon(Icons.Default.ArrowForwardIos, contentDescription = "Back")

                }

            }
        }

        // Wallet Balance Card
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            shape = RoundedCornerShape(16.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        brush = Brush.linearGradient(
                            colors = listOf(Color(0xFF6A11CB), Color(0xFF2575FC))
                        )
                    )
                    .padding(15.dp)
            ) {
                Column {
                    Text(
                        text = "Wallet Balance",
                        fontSize = 18.sp,
                        color = Color.White.copy(alpha = 0.8f),
                        fontWeight = FontWeight.Normal
                    )
                    if (wallet != null) {
                        Text(
                            text = wallet.balance,
                            fontSize = 32.sp,
                            color = Color.White,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(top = 8.dp)
                        )
                    }
                }
            }
        }


        // Total Earnings Card
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            shape = RoundedCornerShape(16.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        brush = Brush.linearGradient(
                            colors = listOf(Color(0xFF11998E), Color(0xFF38EF7D))
                        )
                    )
                    .padding(15.dp)
            ) {
                Column {
                    Text(
                        text = "Total Earnings",
                        fontSize = 18.sp,
                        color = Color.White.copy(alpha = 0.8f),
                        fontWeight = FontWeight.Normal
                    )
                    if (wallet != null) {
                        Text(
                            text = wallet.total_earning,
                            fontSize = 32.sp,
                            color = Color.White,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(top = 8.dp)
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            // Account Settings Section
            items(
                listOf(
                    FeatureItem(
                        "Wallet History",
                        "View your wallet history",
                        Icons.Default.History
                    )
                )
            ) { item ->
                EnhancedFeatureItem(
                    title = item.title,
                    subtitle = item.subtitle,
                    icon = item.icon,
                    onClick = { onFeatureClick(item.title) }
                )
            }

            // More Section
            items(
                listOf(
                    FeatureItem(
                        "Map",
                        "Terms and conditions",
                        Icons.Default.Info
                    ),
                    FeatureItem(
                        "Reviews",
                        "Get support",
                        Icons.Default.Help
                    )
                )
            ) { item ->
                EnhancedFeatureItem(
                    title = item.title,
                    subtitle = item.subtitle,
                    icon = item.icon,
                    onClick = {
                        when (item.title) {
                            "Map" -> onMapClick()
                            "Reviews" -> onReviewClick()
                            else -> onFeatureClick(item.title)
                        }
                    }
                )
            }

            item {
                Spacer(modifier = Modifier.height(24.dp))
                Button(
                    onClick = { showLogoutDialog = true },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(45.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFE53935)),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Logout,
                        contentDescription = "Logout",
                        tint = Color.White
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Logout",
                        style = MaterialTheme.typography.bodyLarge.copy(
                            fontWeight = FontWeight.Medium
                        )
                    )
                }
            }
        }
        // Show the Logout Dialog if showLogoutDialog is true
        if (showLogoutDialog) {
            LogoutDialog(
                onDismiss = { showLogoutDialog = false },  // Dismiss the dialog
                navController = navController,
                onLogout = {
                    // Handle the logout action (e.g., navigate to login screen)
                    viewModel.logOut()
                    coroutineScope.launch(Dispatchers.Main) {
                        dataStore.clearAllData()

                        navController.navigate(Screen.Login.route) {
                            popUpTo(0) {
                                inclusive = true
                            }
                            launchSingleTop = true
                        }
                        // dataStore.saveLoginState(false)

                        //userPreferences.saveSignInResponse("", "")
                    }

                    showLogoutDialog = false
                }
            )
        }
    }

}

@Composable
private fun EnhancedFeatureItem(
    title: String,
    subtitle: String,
    icon: ImageVector,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .background(Color(0xFFFFEBEE), CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = Color(0xFFE53935),
                    modifier = Modifier.size(24.dp)
                )
            }
            Spacer(modifier = Modifier.width(16.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.bodyLarge.copy(
                        fontWeight = FontWeight.Medium,
                        color = Color(0xFF212121)
                    )
                )
                Text(
                    text = subtitle,
                    style = MaterialTheme.typography.bodySmall.copy(
                        color = Color(0xFF757575)
                    )
                )
            }
            Icon(
                imageVector = Icons.Default.ChevronRight,
                contentDescription = null,
                tint = Color(0xFFBDBDBD)
            )
        }
    }
}

private data class FeatureItem(
    val title: String,
    val subtitle: String,
    val icon: ImageVector
)








@Composable
fun WalletScreen(
    viewModel: AuthViewModel = koinViewModel(),
    navController: NavController,
) {
    var showWithdrawalDialog by remember { mutableStateOf(false) }
    var withdrawalAmount by remember { mutableStateOf("") }
    val withdrawalHistory = remember {
        listOf(
            Withdrawal("2023-10-01", "100.00"),
            Withdrawal("2023-09-25", "50.00"),
            Withdrawal("2023-09-20", "200.00")
        )
    }
    val profileStates by viewModel.profileState.collectAsState()
    val profile = profileStates.success?.body()

    Log.d("profileData","profile $profile")

    LaunchedEffect(true) {
        viewModel.getProfile()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF5F5F5))
            .padding(16.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Spacer(Modifier.height(20.dp))
            Card(
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
                    //.clickable { navController.navigate(Screen.ProfileUpdate.route) }
                    .padding(bottom = 8.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(80.dp)
                            .clip(CircleShape)
                            .background(Color(0xFFE0E0E0))
                    ) {
                        if (profile?.profile_image != null) {
                            AsyncImage(
                                model = profile.profile_image,
                                contentDescription = "Profile Picture",
                                modifier = Modifier.fillMaxSize(),
                                contentScale = ContentScale.Crop
                            )
                        } else {
                            Icon(
                                imageVector = Icons.Default.Person,
                                contentDescription = "Default Profile Icon",
                                tint = Color.White,
                                modifier = Modifier
                                    .size(40.dp)
                                    .align(Alignment.Center)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.width(16.dp))

                    Column {

                        Text(
                            text = profile?.first_name ?: "",
                            style = MaterialTheme.typography.headlineSmall.copy(
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF212121)
                            )
                        )

                        Text(
                            text = profile?.email ?: "",
                            style = MaterialTheme.typography.bodyMedium.copy(
                                color = Color(
                                    0xFF757575
                                )
                            )
                        )

                    }

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.End

                    ) {
                        Icon(Icons.Default.ArrowForwardIos, contentDescription = "Back")

                    }

                }
            }

        // Wallet Balance Card
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            shape = RoundedCornerShape(16.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        brush = Brush.linearGradient(
                            colors = listOf(Color(0xFF6A11CB), Color(0xFF2575FC))
                        )
                    )
                    .padding(24.dp)
            ) {
                Column {
                    Text(
                        text = "Wallet Balance",
                        fontSize = 18.sp,
                        color = Color.White.copy(alpha = 0.8f),
                        fontWeight = FontWeight.Normal
                    )
                    Text(
                        text = "$500.00",
                        fontSize = 32.sp,
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(top = 8.dp)
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Total Earnings Card
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            shape = RoundedCornerShape(16.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        brush = Brush.linearGradient(
                            colors = listOf(Color(0xFF11998E), Color(0xFF38EF7D))
                        )
                    )
                    .padding(24.dp)
            ) {
                Column {
                    Text(
                        text = "Total Earnings",
                        fontSize = 18.sp,
                        color = Color.White.copy(alpha = 0.8f),
                        fontWeight = FontWeight.Normal
                    )
                    Text(
                        text = "$1200.00",
                        fontSize = 32.sp,
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(top = 8.dp)
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Request Withdrawal Button
        Button(
            onClick = { showWithdrawalDialog = true },
            modifier = Modifier
                .width(150.dp)
                .height(40.dp),
            shape = RoundedCornerShape(20.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF2575FC))
        ) {
            Text(
                text = "Withdraw",
                fontSize = 16.sp,
                color = Color.White
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Withdrawal History Title
        Text(
            text = "Withdrawal History",
            fontSize = 20.sp,
            color = Color.Black,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.align(Alignment.Start)
        )

        // Withdrawal History List
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp)
        ) {
            items(withdrawalHistory) { withdrawal ->
                WithdrawalHistoryItem(withdrawal)
            }
        }
    }

    // Withdrawal Dialog
    if (showWithdrawalDialog) {
        Dialog(onDismissRequest = { showWithdrawalDialog = false }) {
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                shape = RoundedCornerShape(16.dp),
                color = Color.White
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Request Withdrawal",
                        fontSize = 20.sp,
                        color = Color.Black,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    OutlinedTextField(
                        value = withdrawalAmount,
                        onValueChange = { withdrawalAmount = it },
                        label = { Text("Enter Amount") },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true,
                        keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number)
                    )

                    Spacer(modifier = Modifier.height(24.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Button(
                            onClick = { showWithdrawalDialog = false },
                            modifier = Modifier.weight(1f),
                            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFE0E0E0))
                        ) {
                            Text("Cancel", color = Color.Black)
                        }

                        Spacer(modifier = Modifier.width(16.dp))

                        Button(
                            onClick = {
                                // Handle withdrawal request logic here
                                showWithdrawalDialog = false
                            },
                            modifier = Modifier.weight(1f),
                            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF2575FC))
                        ) {
                            Text("Request", color = Color.White)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun WithdrawalHistoryItem(withdrawal: Withdrawal) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Text(
                    text = "Date: ${withdrawal.date}",
                    fontSize = 16.sp,
                    color = Color.Black
                )
                Text(
                    text = "Amount: $${withdrawal.amount}",
                    fontSize = 16.sp,
                    color = Color.Black,
                    modifier = Modifier.padding(top = 4.dp)
                )
            }
            Icon(
                imageVector = Icons.Default.ArrowForward,
                contentDescription = "Withdrawal",
                tint = Color.Gray
            )
        }
    }
}

data class Withdrawal(val date: String, val amount: String)