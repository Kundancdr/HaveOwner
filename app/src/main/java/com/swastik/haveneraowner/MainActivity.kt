package com.swastik.haveneraowner

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.swastik.haveneraowner.presentation.navigation.BottomNavigationBar
import com.swastik.haveneraowner.presentation.navigation.NavGraph
import com.swastik.haveneraowner.ui.theme.HaveneraOwnerTheme
import com.google.android.libraries.places.api.Places

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val apiKey = getString(R.string.maps_api_key)
        if (!Places.isInitialized() && apiKey != null) {
            Places.initialize(applicationContext, apiKey)
        }
        enableEdgeToEdge()
        setContent {
            HaveneraOwnerTheme {
              //  Dashboard()
                MainScreen()
            }
        }
    }
}

@Composable
fun MainScreen() {
    val navController = rememberNavController()

    Column(modifier = Modifier.fillMaxSize()) {
        // Navigation graph should take up all available vertical space except for the BottomNavigationBar
        Box(modifier = Modifier.weight(1f)) {
            NavGraph(navController = navController)
        }

        // BottomNavigationBar stays fixed at the bottom
        BottomNavigationBar(
            navController = navController,
            modifier = Modifier.fillMaxWidth() // Ensure it spans the screen width
        )
    }
}
