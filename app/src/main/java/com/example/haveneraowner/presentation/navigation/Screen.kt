package com.example.haveneraowner.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.haveneraowner.presentation.screens.AddHotelRoomScreen
import com.example.haveneraowner.presentation.screens.AddRoom
import com.example.haveneraowner.presentation.screens.BookingsScreen
import com.example.haveneraowner.presentation.screens.Dashboard
import com.example.haveneraowner.presentation.screens.DashboardScreen
import com.example.haveneraowner.presentation.screens.ProfileScreen
import com.example.haveneraowner.presentation.screens.ProfileUpdateScreen
import com.example.haveneraowner.presentation.screens.ReviewScreen
import com.example.haveneraowner.presentation.screens.auth.ForgetPasswordOtpVerificationScreen
import com.example.haveneraowner.presentation.screens.auth.ForgetPasswordScreen
import com.example.haveneraowner.presentation.screens.auth.LoginOtpVerificationScreen
import com.example.haveneraowner.presentation.screens.auth.LoginScreen
import com.example.haveneraowner.presentation.screens.ServicesScreen
import com.example.haveneraowner.presentation.screens.WalletHistoryScreen
import com.example.haveneraowner.presentation.screens.auth.SignupOtpVerificationScreen
import com.example.haveneraowner.presentation.screens.auth.SignupScreen
import com.example.haveneraowner.presentation.screens.auth.SplashScreen
import com.example.haveneraowner.presentation.screens.WalletScreen

sealed class Screen(val route: String) {

    object Splash : Screen("splash")
    object Signup : Screen("signup")
    object SignupOtpVerification : Screen("signup_otp_verification/{email}") {
        fun createRoute(email: String) = "signup_otp_verification/$email"
    }

    object Login : Screen("login")
    object LoginOtpVerification : Screen("login_otp_verification/{email}/{password}") {
        fun createRoute(email: String, password: String) = "login_otp_verification/$email/$password"
    }

    object ForgetPassword : Screen("forget_password")
    object ForgetPasswordOtpVerification : Screen("forget_password_otp_verification/{email}") {
        fun createRoute(email: String) = "forget_password_otp_verification/$email"
    }

    object DashBoard : Screen("dashboard")

    object Booking : Screen("booking")

    object Rooms : Screen("rooms")

    object AddRooms : Screen("add_Room")

    object Services : Screen("services")

    object Profile : Screen("profile")

    object WalletHistory : Screen("wallet_History")

    object Reviews : Screen("reviews")

    object ProfileUpdate : Screen("profileUpdate")
}


@Composable
fun NavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Screen.Splash.route
    ) {
        composable(Screen.Splash.route) {
            SplashScreen(navController = navController)
        }
        composable(Screen.Login.route) {
            LoginScreen(navController = navController)
        }
        composable(Screen.Signup.route) {
            SignupScreen(navController)
        }
        composable(
            Screen.SignupOtpVerification.route,
            arguments = listOf(navArgument("email") { type = NavType.StringType })
        ) { backStackEntry ->
            val email = backStackEntry.arguments?.getString("email")
            if (email != null) {
                SignupOtpVerificationScreen(email = email, navController = navController)
            }
        }

        composable(
            Screen.LoginOtpVerification.route,
            arguments = listOf(
                navArgument("email") { type = NavType.StringType },
                navArgument("password") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val email = backStackEntry.arguments?.getString("email")
            val password = backStackEntry.arguments?.getString("password")
            if (email != null && password != null) {
                LoginOtpVerificationScreen(
                    email = email,
                    password = password,
                    navController = navController
                )
            }
        }
        composable(Screen.ForgetPassword.route) {
            ForgetPasswordScreen(navController = navController)
        }

        composable(
            Screen.ForgetPasswordOtpVerification.route,
            arguments = listOf(navArgument("email") { type = NavType.StringType })
        ) { backStackEntry ->
            val email = backStackEntry.arguments?.getString("email")
            if (email != null) {
                ForgetPasswordOtpVerificationScreen(email = email, navController = navController)
            }
        }

        composable(Screen.DashBoard.route){
            //DashboardScreen(navController = navController)
            Dashboard(navController)
        }

        composable(Screen.Booking.route){
            BookingsScreen(navController)
        }

        composable(Screen.Rooms.route){
            AddHotelRoomScreen(navController = navController)
        }

        composable(Screen.AddRooms.route){
            AddRoom(navController = navController)
        }

        composable(Screen.Services.route){
            ServicesScreen(navController)
        }

        composable(Screen.Profile.route){
            ProfileScreen(navController = navController)
        }

        composable(Screen.WalletHistory.route){
            WalletHistoryScreen()
        }

        composable(Screen.Reviews.route){
            ReviewScreen()
        }

        composable(Screen.ProfileUpdate.route){
            ProfileUpdateScreen()
        }
    }
}