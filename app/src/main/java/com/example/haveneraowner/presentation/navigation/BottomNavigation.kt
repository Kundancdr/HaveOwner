package com.example.haveneraowner.presentation.navigation

import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBalanceWallet
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Dashboard
import androidx.compose.material.icons.filled.DataExploration
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Help
import androidx.compose.material.icons.filled.RoomPreferences
import androidx.compose.material.icons.filled.RoomService
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Sensors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState

data class BottomNavItem(
    val name: String,
    val route: String,
    val icon: ImageVector
)

@Composable
fun BottomNavigationBar(navController: NavController,modifier: Modifier) {
    val items = listOf(
        BottomNavItem(
            name = "Home",
            route = Screen.DashBoard.route,
            icon = Icons.Filled.Dashboard
        ),
        BottomNavItem(
            name = "Booking",
            route = Screen.Booking.route,
            icon = Icons.Filled.DataExploration
        ),
        BottomNavItem(
            name = "Rooms",
            route = Screen.Rooms.route,
            icon = Icons.Filled.RoomPreferences
        ),
        BottomNavItem(
            name = "Service",
            route = Screen.Services.route,
            icon = Icons.Filled.Sensors
        ),
        BottomNavItem(
            name = "Profile",
            route = Screen.Profile.route,
            icon = Icons.Filled.AccountBalanceWallet
        )
    )

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    val bottomNavRoutes = items.map { it.route }
    if (currentRoute in bottomNavRoutes) {
        BottomNavigation(
            backgroundColor = Color.White,
            contentColor = Color.Gray
        ) {
            items.forEach { item ->
                BottomNavigationItem(
                    icon = { Icon(item.icon, contentDescription = item.name) },
                    label = { Text(text = item.name) },
                    selected = currentRoute == item.route,
                    onClick = {
                        if (currentRoute != item.route) {
                            navController.navigate(item.route) {
                                popUpTo(navController.graph.startDestinationId)
                                popUpTo(Screen.DashBoard.route){
                                    inclusive = false
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        }
                    },
                    selectedContentColor = Color.Red,
                    unselectedContentColor = Color.Gray,
                    alwaysShowLabel = true
                )
            }
        }
    }
}
