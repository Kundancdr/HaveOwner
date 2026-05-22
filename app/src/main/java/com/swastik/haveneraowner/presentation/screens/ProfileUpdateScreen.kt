package com.swastik.haveneraowner.presentation.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.swastik.haveneraowner.presentation.viewModels.AuthViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun ProfileUpdateScreen(
    profileViewModel: AuthViewModel = koinViewModel(),
    onProfileUpdated: () -> Unit={}
) {
    val profile by profileViewModel.profileState.collectAsState()
    val profileState = profile.success?.body()

    var firstName by remember { mutableStateOf(profileState?.first_name ?: "") }
    var lastName by remember { mutableStateOf(profileState?.last_name ?: "") }
    var email by remember { mutableStateOf(profileState?.email ?: "") }
    var phone by remember { mutableStateOf(profileState?.phone ?: "") }
    var dateOfBirth by remember { mutableStateOf(profileState?.date_of_birth ?: "") }
    var address1 by remember { mutableStateOf(profileState?.address_line1 ?: "") }
    var address2 by remember { mutableStateOf(profileState?.address_line2 ?: "") }
    var city by remember { mutableStateOf(profileState?.city ?: "") }
    var state by remember { mutableStateOf(profileState?.state ?: "") }
    var postalCode by remember { mutableStateOf(profileState?.postal_code ?: "") }
    var country by remember { mutableStateOf(profileState?.country ?: "") }
    var bankName by remember { mutableStateOf(profileState?.bank_name ?: "") }
    var accountHolderName by remember { mutableStateOf(profileState?.account_holder_name ?: "") }
    var accountNumber by remember { mutableStateOf(profileState?.account_number ?: "") }
    var ifscCode by remember { mutableStateOf(profileState?.ifsc_code ?: "") }
    var branchName by remember { mutableStateOf(profileState?.branch_name ?: "") }
    var idProofType by remember { mutableStateOf(profileState?.id_proof_type ?: "") }
    var idProofNumber by remember { mutableStateOf(profileState?.id_proof_number ?: "") }
    var emergencyContact by remember { mutableStateOf(profileState?.emergency_contact ?: "") }
    var notes by remember { mutableStateOf(profileState?.notes ?: "") }

    var profileImage by remember { mutableStateOf(profileState?.profile_image ?: "") }
    var idProofImage by remember { mutableStateOf(profileState?.id_proof_image ?: "") }

    val context = LocalContext.current

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Update Profile", fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = { /* Handle back press */ }) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
                backgroundColor = Color.White,
                elevation = 8.dp
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Profile Image Section
            Box(contentAlignment = Alignment.BottomEnd) {
                Image(
                    painter = rememberAsyncImagePainter(profileImage),
                    contentDescription = "Profile Image",
                    modifier = Modifier
                        .size(100.dp)
                        .clip(CircleShape)
                        .border(2.dp, Color.Gray, CircleShape)
                )
                IconButton(
                    onClick = { /* Handle Image Upload */ },
                    modifier = Modifier
                        .size(32.dp)
                        .background(Color.Red, CircleShape)
                        .border(1.dp, Color.White, CircleShape)
                ) {
                    Icon(Icons.Filled.CameraAlt, contentDescription = "Upload", tint = Color.White)
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Form Fields
            ProfileTextField("First Name", firstName) { firstName = it }
            ProfileTextField("Last Name", lastName) { lastName = it }
            ProfileTextField("Email", email) { email = it }
            ProfileTextField("Phone", phone) { phone = it }
            ProfileTextField("Date of Birth", dateOfBirth) { dateOfBirth = it }
            ProfileTextField("Address Line 1", address1) { address1 = it }
            ProfileTextField("Address Line 2", address2) { address2 = it }
            ProfileTextField("City", city) { city = it }
            ProfileTextField("State", state) { state = it }
            ProfileTextField("Postal Code", postalCode) { postalCode = it }
            ProfileTextField("Country", country) { country = it }

            // Bank Details
            Text("Bank Details", fontWeight = FontWeight.Bold, fontSize = 18.sp)
            Divider()
            ProfileTextField("Bank Name", bankName) { bankName = it }
            ProfileTextField("Account Holder Name", accountHolderName) { accountHolderName = it }
            ProfileTextField("Account Number", accountNumber) { accountNumber = it }
            ProfileTextField("IFSC Code", ifscCode) { ifscCode = it }
            ProfileTextField("Branch Name", branchName) { branchName = it }

            // ID Proof
            Text("ID Proof", fontWeight = FontWeight.Bold, fontSize = 18.sp)
            Divider()
            ProfileTextField("ID Proof Type", idProofType) { idProofType = it }
            ProfileTextField("ID Proof Number", idProofNumber) { idProofNumber = it }

            ProfileTextField("Emergency Contact", emergencyContact) { emergencyContact = it }
            ProfileTextField("Notes", notes) { notes = it }

            // Update Button
            Button(
                onClick = {
//                    profileViewModel.updateProfile(
//                        firstName.ifEmpty { "" },
//                        lastName.ifEmpty { "" },
//                        email.ifEmpty { "" },
//                        phone.ifEmpty { "" },
//                        dateOfBirth.ifEmpty { "" },
//                        address1.ifEmpty { "" },
//                        address2.ifEmpty { "" },
//                        city.ifEmpty { "" },
//                        state.ifEmpty { "" },
//                        postalCode.ifEmpty { "" },
//                        country.ifEmpty { "" },
//                        bankName.ifEmpty { "" },
//                        accountHolderName.ifEmpty { "" },
//                        accountNumber.ifEmpty { "" },
//                        ifscCode.ifEmpty { "" },
//                        branchName.ifEmpty { "" },
//                        idProofType.ifEmpty { "" },
//                        idProofNumber.ifEmpty { "" },
//                        emergencyContact.ifEmpty { "" },
//                        notes.ifEmpty { "" },
//                        profileImage.ifEmpty { "" },
//                        idProofImage.ifEmpty { "" }
//                    )
                    onProfileUpdated()
                },
                colors = ButtonDefaults.buttonColors(backgroundColor = Color.Red),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp)
                    .height(50.dp),
                shape = RoundedCornerShape(8.dp)
            ) {
                Text("Update Profile", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = Color.White)
            }
        }
    }
}

@Composable
fun ProfileTextField(label: String, value: String, onValueChange: (String) -> Unit) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(label) },
        modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),
        shape = RoundedCornerShape(8.dp)
    )
}
