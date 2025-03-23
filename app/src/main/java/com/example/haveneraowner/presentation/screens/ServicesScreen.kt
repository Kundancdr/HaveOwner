package com.example.haveneraowner.presentation.screens

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Spa
import androidx.compose.material.icons.filled.Tune
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.haveneraowner.data.models.request.CreateOwnerServiceRequest
import com.example.haveneraowner.data.models.request.UpdateOwnerServiceRequest
import com.example.haveneraowner.data.models.response.Owner
import com.example.haveneraowner.data.models.response.ResultItem
import com.example.haveneraowner.presentation.viewModels.AuthViewModel
import org.koin.androidx.compose.koinViewModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


@Composable
fun ServicesScreen(
    navController: NavController,
    viewModel: AuthViewModel = koinViewModel()
) {

    val serviceData by viewModel.ownerServiceState.collectAsState()
    val services = serviceData.success?.body()?.results ?: emptyList()
    Log.d("getData", "data $services")
    var showAddDialog by remember { mutableStateOf(false) }
     var editingService by remember { mutableStateOf<ResultItem?>(null) }

    LaunchedEffect(Unit) {
        viewModel.getOwnerService()
    }

    when {
        serviceData.isLoading -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator(color = Color.Red)
            }
        }

        serviceData.error != null -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text(
                    text = "Error: ${serviceData.error}",
                    color = Color.Red,
                    fontSize = 16.sp
                )
            }
        }

        serviceData.success != null -> {


            Scaffold(
                floatingActionButton = {
                    FloatingActionButton(
                        onClick = { showAddDialog = true },
                        containerColor = Color(0xFFE53E3E),
                        contentColor = Color.White
                    ) {
                        Icon(Icons.Default.Add, contentDescription = "Add Service")
                    }
                }
            ) { padding ->
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color(0xFFF8FAFD))
                        .padding(padding)
                        .padding(16.dp)
                ) {
                    // Header
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Services",
                            style = MaterialTheme.typography.headlineMedium,
                            color = Color(0xFF2D3748),
                            modifier = Modifier.weight(1f)
                        )

                        IconButton(onClick = { /* Implement sorting/filtering */ }) {
                            Icon(
                                imageVector = Icons.Default.Tune,
                                contentDescription = "Filter",
                                tint = Color(0xFF718096)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // Services List
                    if (services.isEmpty()) {
                        EmptyState()
                    } else {
                        LazyColumn(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                            items(services) { service ->
                                ServiceCard(
                                    service = service,
                                    onEdit = {
                                        viewModel.updateOwnerService(
                                            service.id,
                                            UpdateOwnerServiceRequest(
                                                name = service.name,
                                                description = service.description,
                                                price = service.price.length
                                            )
                                        )
                                    },
                                    onDelete = { viewModel.deleteOwnerService(service.id) }
                                )
                            }
                        }
                    }
                }

                // Add Service Dialog
                if (showAddDialog) {
                    ServiceDialog(
                        title = "Add New Service",
                        onDismiss = {
                            showAddDialog = false
                        },
                        onConfirm = { result ->

                            viewModel.createOwnerService(
                                CreateOwnerServiceRequest(
                                    name = result.name,
                                    description = result.description,
                                    price = result.price
                                )
                            )
                            showAddDialog = false
                            //  viewModel.createOwnerService(newService)
                            //  showAddDialog = false

//                    val resultItem = ResultItem(
//                        id = services.size + 1, // Generate a temporary ID
//                        name = newService.name,
//                        description = newService.description,
//                        price = newService.price.toString() // Assuming price in ResultItem is a String
//                    )
//                    services = services + resultItem
//                    showAddDialog = false
                        }
                    )
                }

                // Edit Service Dialog
//        editingService?.let { service ->
//            ServiceDialog(
//                title = "Edit Service",
//                service = service,
//                onDismiss = { editingService = null },
//                onConfirm = { updatedService ->
////                    services = services.map { if (it.id == updatedService.id) updatedService else it }
////                    editingService = null
//                    viewModel.updateOwnerService(updatedService)
//                    editingService = null
//                }
//            )
//        }
            }
        }
    }
}

@Composable
private fun ServiceCard(
    service: ResultItem,
    onEdit: () -> Unit = {},
    onDelete: () -> Unit = {}
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onEdit() },
        elevation = CardDefaults.cardElevation(4.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "#${service.id}",
                    color = Color(0xFF718096),
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Medium
                )
                Text(
                    text = "$${service.price}",
                    color = Color(0xFFE53E3E), // Red color for price
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = service.name,
                color = Color(0xFF2D3748),
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = service.description,
                color = Color(0xFF718096),
                fontSize = 14.sp,
                lineHeight = 20.sp
            )


            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "",
                    color = Color(0xFFA0AEC0),
                    fontSize = 12.sp
                )

                Row {
                    IconButton(onClick = onEdit) {
                        Icon(
                            imageVector = Icons.Default.Edit,
                            contentDescription = "Edit",
                            tint = Color(0xFF4299E1),
                        )
                    }
                    IconButton(onClick = onDelete) {
                        Icon(
                            imageVector = Icons.Default.Delete,
                            contentDescription = "Delete",
                            tint = Color(0xFF4299E1)
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun ServiceDialog(
    title: String,
    service: ResultItem? = null,
    onDismiss: () -> Unit,
    onConfirm: (CreateOwnerServiceRequest) -> Unit,
    viewModel: AuthViewModel = koinViewModel()
) {
    var name by remember { mutableStateOf(service?.name ?: "") }
    var description by remember { mutableStateOf(service?.description ?: "") }
    var price by remember { mutableStateOf(service?.price?.toString() ?: "") }
    var priceError by remember { mutableStateOf(false) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(text = title, fontWeight = FontWeight.Bold) },
        text = {
            Column(modifier = Modifier.padding(vertical = 8.dp)) {
                OutlinedTextField(
                    value = name,
                    onValueChange = { name = it },
                    label = { Text("Service Name") },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(16.dp))

                OutlinedTextField(
                    value = description,
                    onValueChange = { description = it },
                    label = { Text("Description") },
                    modifier = Modifier.fillMaxWidth(),
                    maxLines = 3
                )

                Spacer(modifier = Modifier.height(16.dp))

                OutlinedTextField(
                    value = price,
                    onValueChange = {
                        if (it.isEmpty() || it.matches(Regex("^\\d+(\\.\\d{0,2})?$"))) {
                            price = it
                            priceError = false
                        }
                    },
                    label = { Text("Price") },
                    isError = priceError,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    trailingIcon = { Text("USD", color = Color(0xFF718096)) }
                )

                if (priceError) {
                    Text(
                        text = "Invalid price format",
                        color = Color.Red,
                        fontSize = 12.sp
                    )
                }
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    val parsedPrice = price.toIntOrNull()
                    if (parsedPrice != null) {

                        onConfirm(CreateOwnerServiceRequest(name, description, parsedPrice))

//                        viewModel.createOwnerService(
//                            CreateOwnerServiceRequest(
//                                name = name,
//                                description = description,
//                                price = parsedPrice
//                            )
//                        )
                        onDismiss()
                    } else {
                        priceError = true
                    }
                },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF4299E1))
            ) {
                Text("Save")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel", color = Color(0xFF718096))
            }
        },
        shape = RoundedCornerShape(16.dp)
    )
}

@Composable
private fun EmptyState() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(
            imageVector = Icons.Default.Spa,
            contentDescription = null,
            tint = Color(0xFFE53E3E),
            modifier = Modifier.size(64.dp)
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "No Services Found",
            color = Color(0xFF718096),
            fontWeight = FontWeight.Medium
        )
        Text(
            text = "Tap the + button to add a new service",
            color = Color(0xFFA0AEC0),
            fontSize = 14.sp
        )
    }
}