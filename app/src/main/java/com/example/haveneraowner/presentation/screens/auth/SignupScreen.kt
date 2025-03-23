package com.example.haveneraowner.presentation.screens.auth

import android.util.Patterns
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.clickable
import androidx.compose.ui.Alignment
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.VisualTransformation
import androidx.navigation.NavController
import com.example.haveneraowner.data.models.request.SingUpRequestModel
import com.example.haveneraowner.presentation.navigation.Screen
import com.example.haveneraowner.presentation.viewModels.AuthViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

@Composable
fun SignupScreen(
    navController: NavController,
    viewModel: AuthViewModel = koinViewModel()
) {

    val signupState by viewModel.signUpState.collectAsState()
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    fun isValidEmail(email: String): Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    var firstName by remember { mutableStateOf("") }
    var lastName by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }
    var dateOfBirth by remember { mutableStateOf("") }
    var profileImage by remember { mutableStateOf("") }
    var addressLine1 by remember { mutableStateOf("") }
    var addressLine2 by remember { mutableStateOf("") }
    var city by remember { mutableStateOf("") }
    var state by remember { mutableStateOf("") }
    var postalCode by remember { mutableStateOf("") }
    var country by remember { mutableStateOf("") }
    var bankName by remember { mutableStateOf("") }
    var accountHolderName by remember { mutableStateOf("") }
    var accountNumber by remember { mutableStateOf("") }
    var ifscCode by remember { mutableStateOf("") }
    var branchName by remember { mutableStateOf("") }
    var idProofType by remember { mutableStateOf("") }
    var idProofNumber by remember { mutableStateOf("") }
    var idProofImage by remember { mutableStateOf("") }
    var emergencyContact by remember { mutableStateOf("") }
    var notes by remember { mutableStateOf("") }


    when {
        signupState.isLoading -> CircularProgressIndicator()

        signupState.success != null -> {

            LaunchedEffect(Unit) {
                navController.navigate(Screen.SignupOtpVerification.createRoute(email)){
                    popUpTo(Screen.Login.route) { inclusive = true }
                }
            }
            Toast.makeText(context, "Otp Send Successfully", Toast.LENGTH_SHORT).show()
            // Clear the success state after navigation
            viewModel.clearSignUpState()
        }

        signupState.error != null -> {
            Toast.makeText(context, signupState.error.toString(), Toast.LENGTH_SHORT).show()
            // Log.d("SignupResponse", signupState.error.toString())
        }

    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Welcome to",
            fontWeight = FontWeight.Bold,
            fontSize = 28.sp,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        Text(
            text = "HAVENERA",
            fontSize = 28.sp,
            fontFamily = FontFamily.Serif,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        InputField("First Name", firstName) { firstName = it }
        InputField("Last Name", lastName) { lastName = it }
        InputField("Email", email) { email = it }
        InputField("Password", password, isPassword = true) { password = it }
        InputField("Phone", phone) { phone = it }
        InputField("Date of Birth", dateOfBirth) { dateOfBirth = it }

        Button(
            onClick = {

              //  navController.navigate(Screen.DashBoard.route)
                if (email.isBlank() || password.isBlank()
                ) {
                    Toast.makeText(
                        context,
                        "Please fill all fields.",
                        Toast.LENGTH_SHORT
                    )
                        .show()
                    return@Button
                }
                if (!isValidEmail(email)) {
                    Toast.makeText(
                        context,
                        "Please enter a valid email.",
                        Toast.LENGTH_SHORT
                    ).show()
                    return@Button
                }
                if (password.length < 5) {
                    Toast.makeText(
                        context,
                        "Password must be at least 6 characters.",
                        Toast.LENGTH_SHORT
                    ).show()
                    return@Button
                }

                val SignUptModel = SingUpRequestModel(
                    first_name = firstName,
                    last_name = lastName,
                    email = email,
                    password = password,
                    phone = phone,
                    date_of_birth = dateOfBirth,
                    profile_image = "",
                    address_line1 = "",
                    address_line2 ="",
                    city ="",
                    state = "",
                    postal_code = "",
                    country = "",
                    bank_name = "",
                    account_holder_name = ":",
                    account_number ="",
                    ifsc_code ="",
                    branch_name ="",
                    id_proof_type ="",
                    id_proof_number ="",
                    id_proof_image ="",
                    emergency_contact ="",
                    notes=""

                )

                coroutineScope.launch(Dispatchers.IO) {
                    viewModel.signUp(SignUptModel)
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color.Red),
            enabled = !signupState.isLoading // Disable button while loading
        ) {
            if (signupState.isLoading) {
                CircularProgressIndicator(
                    color = Color.White,
                    modifier = Modifier.size(24.dp)
                )
            } else {
                Text("Sign Up", color = Color.White, fontSize = 16.sp)
            }         }

        Spacer(modifier = Modifier.height(32.dp))

        // Footer Text
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            Text("Already have an account? ", color = Color.Gray, fontSize = 14.sp)
            Text(
                text = "Login",
                color = Color.Black,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.clickable {
                    navController.navigate(Screen.Login.route)
                }
            )
        }
    }
}

@Composable
fun InputField(label: String, value: String, isPassword: Boolean = false, onValueChange: (String) -> Unit) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(label) },
        visualTransformation = if (isPassword) PasswordVisualTransformation() else VisualTransformation.None,
        keyboardOptions = KeyboardOptions.Default.copy(
            keyboardType = if (isPassword) KeyboardType.Password else KeyboardType.Text
        ),
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 8.dp)
    )
}
