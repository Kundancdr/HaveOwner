package com.swastik.haveneraowner.presentation.screens.auth

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
//import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.swastik.haveneraowner.data.models.request.VerifyOtRequestModel
import com.swastik.haveneraowner.presentation.navigation.Screen
import com.swastik.haveneraowner.presentation.viewModels.AuthViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignupOtpVerificationScreen(
    viewModel: AuthViewModel = koinViewModel(),
    email: String,
    navController: NavController,
    onBackClick: () -> Unit={}
) {

    val otpState by viewModel.otpVerifyState.collectAsState()
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()

    var otpValues by remember { mutableStateOf(List(6) { "" }) }
    var remainingSeconds by remember { mutableStateOf(30) }
    val focusRequesters = remember { List(6) { FocusRequester() } }
    val scope = rememberCoroutineScope()
    val otp = otpValues.joinToString("")

    LaunchedEffect(Unit) {
        while (remainingSeconds > 0) {
            delay(1000)
            remainingSeconds--
        }
    }


    when {
        otpState.isLoading -> CircularProgressIndicator()

        otpState.error != null -> {
            Toast.makeText(context, "Error: ${otpState.error}", Toast.LENGTH_SHORT).show()
        }
        otpState.success != null -> {
            Toast.makeText(context, "OTP Verified Successfully!", Toast.LENGTH_SHORT).show()
            LaunchedEffect(Unit) {
                navController.navigate(Screen.DashBoard.route)
            }
            // Clear the success state after navigation
            viewModel.clearOtpVerificationState()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp)
    ) {
        // Top Bar
        IconButton(
            onClick = onBackClick,
            modifier = Modifier.padding(bottom = 16.dp)
        ) {
            Icon(Icons.Default.ArrowBack, contentDescription = "Go back")
        }

        // Header
        Text(
            text = "Verification",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Please enter the 6-digit verification code we just sent to",
            style = MaterialTheme.typography.bodyLarge,
            color = Color.Gray
        )

        Text(
            text = email,
            style = MaterialTheme.typography.bodyLarge,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(32.dp))

        // OTP Input Fields
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            otpValues.forEachIndexed { index, value ->
                OutlinedTextField(
                    value = value,
                    onValueChange = { newValue ->
                        if (newValue.length <= 1 && newValue.all { it.isDigit() }) {
                            val newOtpValues = otpValues.toMutableList()
                            newOtpValues[index] = newValue
                            otpValues = newOtpValues

                            if (newValue.isNotEmpty() && index < 5) {
                                focusRequesters[index + 1].requestFocus()
                            }
                        }
                    },
                    modifier = Modifier
                        .weight(1f)
                        .padding(horizontal = 4.dp)
                        .focusRequester(focusRequesters[index]),
                    textStyle = LocalTextStyle.current.copy(textAlign = TextAlign.Center),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    singleLine = true,
                    shape = MaterialTheme.shapes.medium
                )
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Verify Button
        Button(
            onClick = {
                Log.d("OtpVerificationScreen", "Received username: $email")
                if (otp.isBlank()) {
                    Toast.makeText(context, "Please enter your OTP.", Toast.LENGTH_SHORT)
                        .show()
                    return@Button
                }

                coroutineScope.launch {
                    viewModel.verifyOtp(
                        VerifyOtRequestModel(
                            email = email,
                            otp = otp
                        )
                    )
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color.Red),
            shape = RoundedCornerShape(12.dp),
            enabled = otpValues.all { it.isNotEmpty() },

            ) {
            Text("Verify")
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Resend Code
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Didn't receive the code? ",
                style = MaterialTheme.typography.bodyMedium,
                color = Color.Gray
            )

            TextButton(
                onClick = {
                    if (remainingSeconds == 0) {
                        scope.launch {
                            remainingSeconds = 30
                            // Implement resend logic here
                        }
                    }
                },
                enabled = remainingSeconds == 0
            ) {
                Text(
                    if (remainingSeconds > 0) "Resend in ${remainingSeconds}s"
                    else "Resend Code"
                )
            }
        }
    }
}