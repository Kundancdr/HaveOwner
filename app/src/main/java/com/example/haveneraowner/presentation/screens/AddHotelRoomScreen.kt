package com.example.haveneraowner.presentation.screens

import android.app.DatePickerDialog
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Checkbox
import androidx.compose.material.Divider
import androidx.compose.material.IconButton
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.RadioButton
import androidx.compose.material.Surface
import androidx.compose.material.TextButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Spa
import androidx.compose.material.icons.filled.Tune
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import coil.compose.rememberImagePainter
import android.net.Uri
import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.heightIn
import androidx.compose.material.OutlinedButton
import androidx.compose.material.icons.filled.Collections
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Image
import com.example.haveneraowner.data.models.Categorys
import com.example.haveneraowner.data.models.CreateOwnerRoomRequest
import com.example.haveneraowner.data.models.Rooms
import com.example.haveneraowner.data.models.request.CreateOwnerServiceRequest
import com.example.haveneraowner.presentation.viewModels.AuthViewModel
import org.koin.androidx.compose.koinViewModel
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

@Composable
fun AddHotelRoomScreen(
    viewModel: AuthViewModel = koinViewModel()
) {

    var context = LocalContext.current
    var showRoomDialog by remember { mutableStateOf(false) }
    var showAddDialog by remember { mutableStateOf(false) }
    //var selectedRoom: Rooms? by remember { mutableStateOf(null) }
    val ownerRoomState by viewModel.ownerRoomState.collectAsState()
    val roomsData = ownerRoomState.success?.body()?.results ?: emptyList()
    //val createOwnerRoomState by viewModel.createOwnerRoomState.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.getOwnerRooms()
    }

    // Handle loading, error, and success states for fetching rooms
    when {
        ownerRoomState.isLoading -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator(color = Color.Red)
            }
        }

        ownerRoomState.error != null -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text(
                    text = "Error: ${ownerRoomState.error}",
                    color = Color.Red,
                    fontSize = 16.sp
                )
            }
        }

        ownerRoomState.success != null -> {

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
                ) {
                    // Header
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Rooms",
                            style = MaterialTheme.typography.headlineMedium,
                            color = Color(0xFF2D3748),
                            modifier = Modifier.weight(1f)
                        )

                        androidx.compose.material3.IconButton(onClick = { /* Implement sorting/filtering */ }) {
                            Icon(
                                imageVector = Icons.Default.Tune,
                                contentDescription = "Filter",
                                tint = Color(0xFF718096)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // Services List
                    if (roomsData.isEmpty()) {
                        EmptyState()
                    } else {

                        LazyColumn(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                            items(roomsData) { data ->
                                RoomCard(
                                    room = data
                                )
                            }
                        }
                    }
                }

                // Add Service Dialog
                if (showAddDialog) {
                    RoomDialog(
                        //room = Rooms,
                        onDismiss = {
                            showAddDialog = false
                        },
                        onSave = {result ->
                            viewModel.createOwnerRoom(
                                CreateOwnerRoomRequest(
                                    category = Categorys(id = 1, name = ""),
                                    room_name = result.room_name,
                                    description = result.description,
                                    rules = result.rules,
                                    price_per_night = result.price_per_night,
                                    price_not_per_night = result.price_not_per_night,
                                    tax = result.tax,
                                    room_type = result.room_type,
                                    capacity = result.capacity,
                                    no_of_room = result.no_of_room,
                                    latitude = result.latitude,
                                    longitude = result.longitude,
                                    near_by = result.near_by,
                                    address = result.address,
                                    address_line2 = result.address_line2,
                                    city = result.city,
                                    state = result.state,
                                    postal_code = result.postal_code,
                                    country = result.country,
                                    available_from = result.available_from,
                                    available_to = result.available_to,
                                    facilities = result.facilities,
                                    services = result.services,
                                    main_image = result.main_image,
                                    additional_images = result.additional_images,
                                    status = result.status,
                                    created_at = result.created_at,
                                )
                            )
                        }
                    )

//                    ServiceDialog(
//                        title = "Add New Service",
//                        onDismiss = {
//                            showAddDialog = false
//                        },
//                        onConfirm = { result ->
//
//                            viewModel.createOwnerService(
//                                CreateOwnerServiceRequest(
//                                    name = result.name,
//                                    description = result.description,
//                                    price = result.price
//                                )
//                            )
//                            showAddDialog = false
//                        }
//                    )
                }
            }
        }
    }
}


@Composable
fun RoomDialog(
    //room: Rooms?,
    onDismiss: () -> Unit ={},
    onSave: (CreateOwnerRoomRequest) -> Unit ={},
    viewModel: AuthViewModel = koinViewModel()
) {

    val Categorys by viewModel.categoryState.collectAsState()
    val Services by viewModel.ownerServiceState.collectAsState()
    val Facility by viewModel.facilityState.collectAsState()
    val catlist = Categorys.success?.body()?.name
    val serviceList = Services.success?.body()
    val facilityList = Facility.success?.body()
    Log.d("serviceList", "RoomDialog:$facilityList")

    var category by remember { mutableStateOf("") }
    var roomName by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var rules by remember { mutableStateOf(listOf<String>()) }
    var pricePerNight by remember { mutableStateOf(0.0) }
    var priceNotPerNight by remember { mutableStateOf(0.0) }
    var taxPercentage by remember { mutableStateOf(0.0) }
    var capacity by remember { mutableStateOf(0) }
    var numberOfRooms by remember { mutableStateOf(0) }
    var fullAddress by remember { mutableStateOf("") }
    var nearByAddress by remember { mutableStateOf("") }
    var city by remember { mutableStateOf("") }
    var state by remember { mutableStateOf("") }
    var country by remember { mutableStateOf("") }
    var pinCode by remember { mutableStateOf("") }
    var availableFrom by remember { mutableStateOf(System.currentTimeMillis()) }
    var mainImage by remember { mutableStateOf<Uri?>(null) }
    var additionalImages by remember { mutableStateOf<List<Uri>>(emptyList()) }
    var facilities by remember { mutableStateOf<List<String>>(emptyList()) }
    var services by remember { mutableStateOf<List<String>>(emptyList()) }
    var status by remember { mutableStateOf("Available") }
    var searchLocation by remember { mutableStateOf("") }

    val context = LocalContext.current

    Dialog(onDismissRequest = onDismiss) {
        Surface(
            shape = RoundedCornerShape(8.dp),
            color = Color(0xFFF8FAFD),
        ) {
            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .verticalScroll(rememberScrollState())
            ) {
                Text("Add Room", style = MaterialTheme.typography.labelMedium)
                Spacer(modifier = Modifier.height(16.dp))

                // Category
                OutlinedTextField(
                    value = category,
                    onValueChange = { category = it },
                    label = { Text("Category") },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(6.dp))

                // Room Name
                OutlinedTextField(
                    value = roomName,
                    onValueChange = { roomName = it },
                    label = { Text("Room Name") },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(6.dp))
                // Description
                OutlinedTextField(
                    value = description,
                    onValueChange = { description = it },
                    label = { Text("Description") },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(6.dp))
                // Rules
                OutlinedTextField(
                    value = rules.joinToString("\n-"),
                    onValueChange = { rules = it.split("\n-") },
                    label = { Text("Rules") },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(6.dp))
                // Price/Night
                OutlinedTextField(
                    value = pricePerNight.toString(),
                    onValueChange = { pricePerNight = it.toDoubleOrNull() ?: 0.0 },
                    label = { Text("Price/Night") },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(6.dp))
                // Price Not/Night
                OutlinedTextField(
                    value = priceNotPerNight.toString(),
                    onValueChange = { priceNotPerNight = it.toDoubleOrNull() ?: 0.0 },
                    label = { Text("Price Not/Night") },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(6.dp))
                // Tax
                OutlinedTextField(
                    value = taxPercentage.toString(),
                    onValueChange = { taxPercentage = it.toDoubleOrNull() ?: 0.0 },
                    label = { Text("Tax (%)") },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(6.dp))
                // Capacity
                OutlinedTextField(
                    value = capacity.toString(),
                    onValueChange = { capacity = it.toIntOrNull() ?: 0 },
                    label = { Text("Capacity") },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(6.dp))
                // Number of Rooms
                OutlinedTextField(
                    value = numberOfRooms.toString(),
                    onValueChange = { numberOfRooms = it.toIntOrNull() ?: 0 },
                    label = { Text("Number of Rooms") },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(6.dp))
                // Full Address
                OutlinedTextField(
                    value = fullAddress,
                    onValueChange = { fullAddress = it },
                    label = { Text("Full Address") },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(6.dp))
                // Nearby Address
                OutlinedTextField(
                    value = nearByAddress,
                    onValueChange = { nearByAddress = it },
                    label = { Text("Nearby Address") },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(6.dp))
                // City
                OutlinedTextField(
                    value = city,
                    onValueChange = { city = it },
                    label = { Text("City") },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(6.dp))
                // State
                OutlinedTextField(
                    value = state,
                    onValueChange = { state = it },
                    label = { Text("State") },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(6.dp))
                // Country
                OutlinedTextField(
                    value = country,
                    onValueChange = { country = it },
                    label = { Text("Country") },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(6.dp))
                // Pin Code
                OutlinedTextField(
                    value = pinCode,
                    onValueChange = { pinCode = it },
                    label = { Text("Pin Code") },
                    modifier = Modifier.fillMaxWidth()
                )



                Spacer(modifier = Modifier.height(6.dp))
                // Available From

                val calendar = Calendar.getInstance().apply {
                    timeInMillis = availableFrom
                }

                val formattedDate = remember(availableFrom) {
                    val date = Date(availableFrom)
                    val formatter = SimpleDateFormat("dd MMM yyyy", Locale.getDefault())
                    formatter.format(date)
                }

                val datePickerDialog = DatePickerDialog(
                    context,
                    { _, year, month, day ->
                        val calendar = Calendar.getInstance()
                        calendar.set(year, month, day)
                        availableFrom = calendar.timeInMillis
                    },
                    Calendar.getInstance().get(Calendar.YEAR),
                    Calendar.getInstance().get(Calendar.MONTH),
                    Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
                )

                OutlinedButton(
                    onClick = { datePickerDialog.show() },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp),
                    shape = RoundedCornerShape(8.dp),
                    border = BorderStroke(1.dp, Color.Gray),
                    colors = ButtonDefaults.outlinedButtonColors(contentColor = Color.Black)
                ) {
                    Icon(
                        imageVector = Icons.Default.DateRange,
                        contentDescription = "Select Date",
                        modifier = Modifier.padding(end = 8.dp)
                    )
                    Text(text = formattedDate)
                }

                Spacer(Modifier.height(10.dp))

                val launcherSingle = rememberLauncherForActivityResult(
                    contract = ActivityResultContracts.GetContent()
                ) { uri: Uri? ->
                    uri?.let { mainImage = it }
                }

                val launcherMultiple = rememberLauncherForActivityResult(
                    contract = ActivityResultContracts.GetMultipleContents()
                ) { uris: List<Uri> ->
                    additionalImages = uris
                }

                Column(modifier = Modifier.fillMaxWidth()) {
                    OutlinedButton(
                        onClick = { launcherSingle.launch("image/*") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(56.dp),
                        shape = RoundedCornerShape(8.dp),
                        border = BorderStroke(1.dp, Color.Gray),
                        colors = ButtonDefaults.outlinedButtonColors(contentColor = Color.Black)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Image,
                            contentDescription = "Pick Image",
                            modifier = Modifier.padding(end = 8.dp)
                        )
                        Text(text = "Select Main Image")
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    mainImage?.let { imageUri ->
                        Image(
                            painter = rememberImagePainter(imageUri),
                            contentDescription = "Main Image",
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(200.dp)
                                .clip(RoundedCornerShape(12.dp))
                                .border(2.dp, Color.Gray, RoundedCornerShape(12.dp))
                                .padding(4.dp),
                            contentScale = ContentScale.Crop
                        )
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                Column(modifier = Modifier.fillMaxWidth()) {
                    OutlinedButton(
                        onClick = { launcherMultiple.launch("image/*") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(56.dp),
                        shape = RoundedCornerShape(8.dp),
                        border = BorderStroke(1.dp, Color.Gray),
                        colors = ButtonDefaults.outlinedButtonColors(contentColor = Color.Black)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Collections, // Better suited for multiple images
                            contentDescription = "Pick Images",
                            modifier = Modifier.padding(end = 8.dp)
                        )
                        Text("Select Additional Images")
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    if (additionalImages.isNotEmpty()) {
                        LazyVerticalGrid(
                            columns = GridCells.Fixed(3),
                            modifier = Modifier
                                .fillMaxWidth()
                                .heightIn(max = 300.dp), // Fixes crash from infinite height
                            contentPadding = PaddingValues(8.dp),
                            verticalArrangement = Arrangement.spacedBy(8.dp),
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            items(additionalImages) { imageUri ->
                                Image(
                                    painter = rememberImagePainter(imageUri),
                                    contentDescription = "Additional Image",
                                    modifier = Modifier
                                        .aspectRatio(1f)
                                        .clip(RoundedCornerShape(8.dp))
                                        .border(2.dp, Color.Gray, RoundedCornerShape(8.dp)),
                                    contentScale = ContentScale.Crop
                                )
                            }
                        }
                    }
                }

                Spacer(Modifier.height(10.dp))


                Text("facility")
                // Facilities
                // Assuming you have a list of facilities to choose from
                val facilitiesList = listOf("Wi-Fi", "Pool", "Gym", "Parking")
                facilitiesList.forEach { facility ->
                    Row (
                        verticalAlignment = Alignment.CenterVertically
                    ){
                        Checkbox(
                            checked = facilities.contains(facility),
                            onCheckedChange = { checked ->
                                facilities = if (checked) facilities + facility else facilities - facility
                            }
                        )
                        Text(facility)
                    }
                }

                Spacer(modifier = Modifier.height(6.dp))

                Text("Services")
                // Services
                // Assuming you have a list of services to choose from
                val servicesList = listOf("Room Service", "Laundry", "Breakfast")
                servicesList.forEach { service ->
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Checkbox(
                            checked = services.contains(service),
                            onCheckedChange = { checked ->
                                services = if (checked) services + service else services - service
                            }
                        )
                        Text(service)
                    }
                }

                Spacer(modifier = Modifier.height(6.dp))

                Text("Select Availibility")
                // Status
                val statusOptions = listOf("Available", "Booked")
                statusOptions.forEach { option ->
                    Row (
                        verticalAlignment = Alignment.CenterVertically
                    ){
                        RadioButton(
                            selected = status == option,
                            onClick = { status = option }
                        )
                        Text(option)
                    }
                }

                Spacer(modifier = Modifier.height(6.dp))
                // Search Location
                OutlinedTextField(
                    value = searchLocation,
                    onValueChange = { searchLocation = it },
                    label = { Text("Search Location") },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(16.dp))
                // Save and Cancel Buttons
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    Button(onClick = onDismiss) {
                        Text("Cancel")
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Button(onClick = {
                        val roomss = CreateOwnerRoomRequest(
                            category = Categorys(id = 1, name = ""),
                            room_name = roomName,
                            description = description,
                            rules = rules.toString(),
                            price_per_night = pricePerNight,
                            price_not_per_night = priceNotPerNight,
                            tax = taxPercentage,
                            room_type = TODO(),
                            capacity = capacity,
                            no_of_room = numberOfRooms,
                            latitude = TODO(),
                            longitude = TODO(),
                            near_by = nearByAddress,
                            address = nearByAddress,
                            address_line2 = fullAddress,
                            city = city,
                            state = state,
                            postal_code = TODO(),
                            country =country,
                            available_from = availableFrom.toString(),
                            available_to = TODO(),
                            facilities = TODO(),
                            services = TODO(),
                            main_image = TODO(),
                            additional_images = TODO(),
                            status = status,
                            created_at = TODO(),
                        )
                        onSave(roomss)
                    }) {
                        Text("Save")
                    }
                }
            }
        }
    }
}





@Composable
fun RoomCard(
    room: Rooms,
    onView: () -> Unit = {},
    onEdit: () -> Unit = {},
    onDelete: () -> Unit = {}
) {
    Card(
        elevation = CardDefaults.cardElevation(6.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .shadow(6.dp, RoundedCornerShape(16.dp))
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = "ID: ${room.id}",
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Spacer(Modifier.weight(1f))
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(50))
                        .background(
                            when (room.status) {
                                "Available" -> Color(0xFF4CAF50) // Green
                                else -> Color(0xFFF44336) // Red
                            }
                        )
                        .padding(horizontal = 12.dp, vertical = 4.dp)
                ) {
                    Text(
                        text = room.status,
                        color = Color.White,
                        style = MaterialTheme.typography.labelSmall
                    )
                }
            }

            Spacer(Modifier.height(8.dp))
            Text(room.room_name, style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
            Text(
                room.category.name,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Spacer(Modifier.height(8.dp))
            Text(
                room.description,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
            )

            Divider(Modifier.padding(vertical = 8.dp))

            Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth()) {
                InfoChip("Capacity: ${room.capacity}")
                InfoChip("Rooms: ${room.no_of_room}")
                InfoChip("₹${room.price_per_night}/night")
            }

            Spacer(Modifier.height(12.dp))
            Row(horizontalArrangement = Arrangement.SpaceEvenly, modifier = Modifier.fillMaxWidth()) {
                ActionButton(icon = Icons.Default.Visibility, text = "View", onClick = onView, color = Color(0xFF1976D2))
                ActionButton(icon = Icons.Default.Edit, text = "Edit", onClick = onEdit, color = Color(0xFFFFA000))
                ActionButton(icon = Icons.Default.Delete, text = "Delete", onClick = onDelete, color = Color(0xFFD32F2F))
            }
        }
    }
}

@Composable
fun InfoChip(label: String) {
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(50))
            .background(Color(0xFFF5F5F5))
            .padding(horizontal = 12.dp, vertical = 6.dp)
    ) {
        Text(label, style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
    }
}

@Composable
fun ActionButton(icon: ImageVector, text: String, onClick: () -> Unit, color: Color) {
    TextButton(
        onClick = onClick,
        colors = ButtonDefaults.textButtonColors(contentColor = color),
        modifier = Modifier.padding(horizontal = 4.dp)
    ) {
        Icon(icon, contentDescription = text, tint = color, modifier = Modifier.size(20.dp))
        Spacer(Modifier.width(4.dp))
        Text(text, style = MaterialTheme.typography.labelMedium)
    }
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
            text = "No Rooms Found",
            color = Color(0xFF718096),
            fontWeight = FontWeight.Medium
        )
        Text(
            text = "Tap the + button to add a new rooms",
            color = Color(0xFFA0AEC0),
            fontSize = 14.sp
        )
    }
}




//
//import android.app.DatePickerDialog
//import android.widget.Toast
//import androidx.activity.compose.rememberLauncherForActivityResult
//import androidx.activity.result.PickVisualMediaRequest
//import androidx.activity.result.contract.ActivityResultContracts
//import androidx.compose.foundation.Image
//import androidx.compose.foundation.background
//import androidx.compose.foundation.layout.Arrangement
//import androidx.compose.foundation.layout.Box
//import androidx.compose.foundation.layout.Column
//import androidx.compose.foundation.layout.Row
//import androidx.compose.foundation.layout.Spacer
//import androidx.compose.foundation.layout.fillMaxSize
//import androidx.compose.foundation.layout.fillMaxWidth
//import androidx.compose.foundation.layout.height
//import androidx.compose.foundation.layout.padding
//import androidx.compose.foundation.layout.size
//import androidx.compose.foundation.layout.width
//import androidx.compose.foundation.lazy.LazyColumn
//import androidx.compose.foundation.lazy.items
//import androidx.compose.foundation.rememberScrollState
//import androidx.compose.foundation.shape.RoundedCornerShape
//import androidx.compose.foundation.text.KeyboardOptions
//import androidx.compose.foundation.verticalScroll
//import androidx.compose.material.Checkbox
//import androidx.compose.material.CircularProgressIndicator
//import androidx.compose.material.Divider
//import androidx.compose.material.ExperimentalMaterialApi
//import androidx.compose.material.ExposedDropdownMenuBox
//import androidx.compose.material.RadioButton
//import androidx.compose.material.icons.Icons
//import androidx.compose.material.icons.filled.Delete
//import androidx.compose.material.icons.filled.Edit
//import androidx.compose.material.icons.filled.Visibility
//import androidx.compose.material3.Button
//import androidx.compose.material3.ButtonDefaults
//import androidx.compose.material3.Card
//import androidx.compose.material3.CardDefaults
//import androidx.compose.material3.ExperimentalMaterial3Api
//import androidx.compose.material3.ExposedDropdownMenuDefaults
//import androidx.compose.material3.Icon
//import androidx.compose.material3.IconButton
//import androidx.compose.material3.MaterialTheme
//import androidx.compose.material3.OutlinedButton
//import androidx.compose.material3.OutlinedTextField
//import androidx.compose.material3.Surface
//import androidx.compose.material3.Text
//import androidx.compose.material3.TextButton
//import androidx.compose.material3.TextFieldDefaults
//import androidx.compose.runtime.Composable
//import androidx.compose.runtime.LaunchedEffect
//import androidx.compose.runtime.collectAsState
//import androidx.compose.runtime.getValue
//import androidx.compose.runtime.mutableStateOf
//import androidx.compose.runtime.remember
//import androidx.compose.runtime.setValue
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.draw.clip
//import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.platform.LocalContext
//import androidx.compose.ui.text.TextStyle
//import androidx.compose.ui.text.font.FontWeight
//import androidx.compose.ui.text.input.KeyboardType
//import androidx.compose.ui.text.style.TextAlign
//import androidx.compose.ui.unit.dp
//import androidx.compose.ui.unit.sp
//import androidx.compose.ui.window.Dialog
//import androidx.navigation.NavController
//import coil.compose.rememberImagePainter
//import com.example.haveneraowner.data.models.Category
//import com.example.haveneraowner.data.models.CreateOwnerRoomRequest
//import com.example.haveneraowner.data.models.Rooms
//import com.example.haveneraowner.data.models.response.Room
//import com.example.haveneraowner.presentation.viewModels.AuthViewModel
//import org.koin.androidx.compose.koinViewModel
//import java.util.Calendar
//
//
//@Composable
//fun AddHotelRoomScreen(
//    navController: NavController,
//    viewModel: AuthViewModel = koinViewModel()
//) {
//    var context = LocalContext.current
//    var showRoomDialog by remember { mutableStateOf(false) }
//    var selectedRoom: Rooms? by remember { mutableStateOf(null) }
//    val ownerRoomState by viewModel.ownerRoomState.collectAsState()
//    val createOwnerRoomState by viewModel.createOwnerRoomState.collectAsState()
//
//    LaunchedEffect(Unit) {
//        viewModel.getOwnerRooms()
//    }
//
//    // Handle loading, error, and success states for fetching rooms
//    when {
//        ownerRoomState.isLoading -> {
//            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
//                CircularProgressIndicator(color = Color.Red)
//            }
//        }
//
//        ownerRoomState.error != null -> {
//            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
//                Text(
//                    text = "Error: ${ownerRoomState.error}",
//                    color = Color.Red,
//                    fontSize = 16.sp
//                )
//            }
//        }
//
//        ownerRoomState.success != null -> {
//            val rooms = ownerRoomState.success!!.body()?.results ?: emptyList()
//            RoomListContent(
//                rooms = rooms,
//                showRoomDialog = showRoomDialog,
//                selectedRoom = selectedRoom,
//                onShowDialog = { showRoomDialog = it },
//                onSelectRoom = { selectedRoom = it },
//                onCreateRoom = { request -> viewModel.createOwnerRoom(request) }
//            )
//        }
//    }
//
//    // Handle loading, error, and success states for creating rooms
//    when {
//        createOwnerRoomState.isLoading -> {
//            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
//                CircularProgressIndicator(color = Color.Red)
//            }
//        }
//
//        createOwnerRoomState.error != null -> {
//            LaunchedEffect(createOwnerRoomState.error) {
//                Toast.makeText(context, "Error: ${createOwnerRoomState.error}", Toast.LENGTH_SHORT)
//                    .show()
//            }
//        }
//
//        createOwnerRoomState.success != null -> {
//            LaunchedEffect(createOwnerRoomState.success) {
//                Toast.makeText(context, "Room created successfully!", Toast.LENGTH_SHORT).show()
//                viewModel.getOwnerRooms() // Refresh the list
//            }
//        }
//    }
//}
//
//    @Composable
//    fun RoomListContent(
//        rooms: List<Rooms>,
//        showRoomDialog: Boolean,
//        selectedRoom: Rooms?,
//        onShowDialog: (Boolean) -> Unit,
//        onSelectRoom: (Rooms?) -> Unit,
//        onCreateRoom: (CreateOwnerRoomRequest) -> Unit
//    ) {
//        Column(
//            modifier = Modifier
//                .fillMaxSize()
//                .padding(16.dp)
//        ) {
//            Text(
//                text = "Rooms",
//                style = MaterialTheme.typography.headlineMedium,
//                modifier = Modifier.fillMaxWidth(),
//                textAlign = TextAlign.Center
//            )
//
//            Spacer(Modifier.height(16.dp))
//
//            LazyColumn(
//                verticalArrangement = Arrangement.spacedBy(12.dp),
//                modifier = Modifier.weight(1f)
//            ) {
//                items(rooms) { room ->
//                    RoomCard(
//                        room = room,
//                        onView = { onSelectRoom(room) },
//                        onEdit = { onSelectRoom(room); onShowDialog(true) },
//                        onDelete = { /* Handle delete */ }
//                    )
//                }
//            }
//
//            Button(
//                onClick = {
//                    onSelectRoom(null)
//                    onShowDialog(true)
//                },
//                modifier = Modifier
//                    .width(150.dp)
//                    .height(45.dp),
//                colors = ButtonDefaults.buttonColors(containerColor = Color.Red),
//                shape = RoundedCornerShape(8.dp)
//            ) {
//                Text(
//                    text = "Add Room",
//                    color = Color.White,
//                    fontSize = 14.sp,
//                    fontWeight = FontWeight.Bold
//                )
//            }
//        }
//
//        // Show Room Dialog
//        if (showRoomDialog) {
//            RoomDialog(
//                room = selectedRoom,
//                onDismiss = { onShowDialog(false) },
//                onSave = { room ->
//                    val request = CreateOwnerRoomRequest(
//                        room_name = room.room_name,
//                        description = room.description,
//                        price_per_night = room.price_per_night.toDouble().toInt(),
//                        tax = room.tax.toDouble().toInt(),
//                        capacity = room.capacity,
//                        address = room.address,
//                        city = room.city,
//                        state = room.state,
//                        country = room.country,
//                        postal_code = room.postal_code,
//                    )
//                    onCreateRoom(request)
//                    onShowDialog(false)
//                }
//            )
//        }
//    }
//
//
//@Composable
//fun RoomCard(
//    room: Rooms,
//    onView: () -> Unit,
//    onEdit: () -> Unit,
//    onDelete: () -> Unit
//) {
//    Card(
//        elevation = CardDefaults.cardElevation(4.dp),
//        colors = CardDefaults.cardColors(Color.White),
//        modifier = Modifier.fillMaxWidth()
//    ) {
//        Column(modifier = Modifier.padding(16.dp)) {
//            Row(verticalAlignment = Alignment.CenterVertically) {
//                Text(
//                    text = "ID: ${room.id}",
//                    style = MaterialTheme.typography.labelSmall,
//                    color = MaterialTheme.colorScheme.onSurfaceVariant
//                )
//                Spacer(Modifier.weight(1f))
//                Text(
//                    text = room.status,
//                    color = when (room.status) {
//                        "Available" -> Color.Green
//                        else -> Color.Red
//                    },
//                    style = MaterialTheme.typography.labelSmall
//                )
//            }
//
//            Spacer(Modifier.height(8.dp))
//            Text(room.room_name, style = MaterialTheme.typography.titleLarge)
//            Text(room.category.name, style = MaterialTheme.typography.bodySmall)
//
//            Spacer(Modifier.height(8.dp))
//            Text(room.description, style = MaterialTheme.typography.bodyMedium)
//
//            Divider(Modifier.padding(vertical = 8.dp))
//
//            Row {
//                InfoChip("Capacity: ${room.capacity}")
//                Spacer(Modifier.width(4.dp))
//                InfoChip("Rooms: ${room.no_of_room}")
//                Spacer(Modifier.width(4.dp))
//                InfoChip("₹${room.price_per_night}/night")
//            }
//
//            Spacer(Modifier.height(8.dp))
//            Row(horizontalArrangement = Arrangement.End, modifier = Modifier.fillMaxWidth()) {
//                IconButton(onClick = onView) {
//                    Icon(Icons.Default.Visibility, "View")
//                }
//                IconButton(onClick = onEdit) {
//                    Icon(Icons.Default.Edit, "Edit")
//                }
//                IconButton(onClick = onDelete) {
//                    Icon(Icons.Default.Delete, "Delete")
//                }
//            }
//        }
//    }
//}
//
//
//@Composable
//fun RoomDialog(
//    room: Rooms?,
//    onDismiss: () -> Unit,
//    onSave: (Rooms) -> Unit
//) {
//    var category by remember { mutableStateOf("") }
//    var roomName by remember { mutableStateOf("") }
//    var description by remember { mutableStateOf("") }
//    var rules by remember { mutableStateOf(listOf<String>()) }
//    var pricePerNight by remember { mutableStateOf(0.0) }
//    var priceNotPerNight by remember { mutableStateOf(0.0) }
//    var taxPercentage by remember { mutableStateOf(0.0) }
//    var capacity by remember { mutableStateOf(0) }
//    var numberOfRooms by remember { mutableStateOf(0) }
//    var fullAddress by remember { mutableStateOf("") }
//    var nearByAddress by remember { mutableStateOf("") }
//    var city by remember { mutableStateOf("") }
//    var state by remember { mutableStateOf("") }
//    var country by remember { mutableStateOf("") }
//    var pinCode by remember { mutableStateOf("") }
//    var availableFrom by remember { mutableStateOf(System.currentTimeMillis()) }
//    var mainImage by remember { mutableStateOf<String?>(null) }
//    var additionalImages by remember { mutableStateOf<List<String>>(emptyList()) }
//    var facilities by remember { mutableStateOf<List<String>>(emptyList()) }
//    var services by remember { mutableStateOf<List<String>>(emptyList()) }
//    var status by remember { mutableStateOf("Available") }
//    var searchLocation by remember { mutableStateOf("") }
//
//    val context = LocalContext.current
//
//    Dialog(onDismissRequest = onDismiss) {
//        Surface(
//            shape = RoundedCornerShape(8.dp),
//            color = MaterialTheme.colorScheme.onSurfaceVariant,
//        ) {
//            Column(
//                modifier = Modifier
//                    .padding(16.dp)
//                    .verticalScroll(rememberScrollState())
//            ) {
//                Text("Add Room", style = MaterialTheme.typography.headlineMedium)
//                Spacer(modifier = Modifier.height(16.dp))
//
//                // Category
//                OutlinedTextField(
//                    value = category,
//                    onValueChange = { category = it },
//                    label = { Text("Category") },
//                    modifier = Modifier.fillMaxWidth()
//                )
//
//                // Room Name
//                OutlinedTextField(
//                    value = roomName,
//                    onValueChange = { roomName = it },
//                    label = { Text("Room Name") },
//                    modifier = Modifier.fillMaxWidth()
//                )
//
//                // Description
//                OutlinedTextField(
//                    value = description,
//                    onValueChange = { description = it },
//                    label = { Text("Description") },
//                    modifier = Modifier.fillMaxWidth()
//                )
//
//                // Rules
//                OutlinedTextField(
//                    value = rules.joinToString("\n-"),
//                    onValueChange = { rules = it.split("\n-") },
//                    label = { Text("Rules (start each line with '-')") },
//                    modifier = Modifier.fillMaxWidth()
//                )
//
//                // Price/Night
//                OutlinedTextField(
//                    value = pricePerNight.toString(),
//                    onValueChange = { pricePerNight = it.toDoubleOrNull() ?: 0.0 },
//                    label = { Text("Price/Night") },
//                    modifier = Modifier.fillMaxWidth()
//                )
//
//                // Price Not/Night
//                OutlinedTextField(
//                    value = priceNotPerNight.toString(),
//                    onValueChange = { priceNotPerNight = it.toDoubleOrNull() ?: 0.0 },
//                    label = { Text("Price Not/Night") },
//                    modifier = Modifier.fillMaxWidth()
//                )
//
//                // Tax
//                OutlinedTextField(
//                    value = taxPercentage.toString(),
//                    onValueChange = { taxPercentage = it.toDoubleOrNull() ?: 0.0 },
//                    label = { Text("Tax (%)") },
//                    modifier = Modifier.fillMaxWidth()
//                )
//
//                // Capacity
//                OutlinedTextField(
//                    value = capacity.toString(),
//                    onValueChange = { capacity = it.toIntOrNull() ?: 0 },
//                    label = { Text("Capacity") },
//                    modifier = Modifier.fillMaxWidth()
//                )
//
//                // Number of Rooms
//                OutlinedTextField(
//                    value = numberOfRooms.toString(),
//                    onValueChange = { numberOfRooms = it.toIntOrNull() ?: 0 },
//                    label = { Text("Number of Rooms") },
//                    modifier = Modifier.fillMaxWidth()
//                )
//
//                // Full Address
//                OutlinedTextField(
//                    value = fullAddress,
//                    onValueChange = { fullAddress = it },
//                    label = { Text("Full Address") },
//                    modifier = Modifier.fillMaxWidth()
//                )
//
//                // Nearby Address
//                OutlinedTextField(
//                    value = nearByAddress,
//                    onValueChange = { nearByAddress = it },
//                    label = { Text("Nearby Address") },
//                    modifier = Modifier.fillMaxWidth()
//                )
//
//                // City
//                OutlinedTextField(
//                    value = city,
//                    onValueChange = { city = it },
//                    label = { Text("City") },
//                    modifier = Modifier.fillMaxWidth()
//                )
//
//                // State
//                OutlinedTextField(
//                    value = state,
//                    onValueChange = { state = it },
//                    label = { Text("State") },
//                    modifier = Modifier.fillMaxWidth()
//                )
//
//                // Country
//                OutlinedTextField(
//                    value = country,
//                    onValueChange = { country = it },
//                    label = { Text("Country") },
//                    modifier = Modifier.fillMaxWidth()
//                )
//
//                // Pin Code
//                OutlinedTextField(
//                    value = pinCode,
//                    onValueChange = { pinCode = it },
//                    label = { Text("Pin Code") },
//                    modifier = Modifier.fillMaxWidth()
//                )
//
//                // Available From
//                val datePickerDialog = DatePickerDialog(
//                    context,
//                    { _, year, month, day ->
//                        val calendar = Calendar.getInstance()
//                        calendar.set(year, month, day)
//                        availableFrom = calendar.timeInMillis
//                    },
//                    Calendar.getInstance().get(Calendar.YEAR),
//                    Calendar.getInstance().get(Calendar.MONTH),
//                    Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
//                )
//
//                Button(onClick = { datePickerDialog.show() }) {
//                    Text("Select Available From Date")
//                }
//
//                // Main Image
//                Button(onClick = { /* Open image picker */ }) {
//                    Text("Select Main Image")
//                }
//                mainImage?.let { imageUri ->
//                    Image(
//                        painter = rememberImagePainter(imageUri),
//                        contentDescription = null,
//                        modifier = Modifier.size(100.dp)
//                    )
//                }
//
//                // Additional Images
//                Button(onClick = { /* Open multiple image picker */ }) {
//                    Text("Select Additional Images")
//                }
//                additionalImages.forEach { imageUri ->
//                    Image(
//                        painter = rememberImagePainter(imageUri),
//                        contentDescription = null,
//                        modifier = Modifier.size(100.dp)
//                    )
//                }
//
//                // Facilities
//                // Assuming you have a list of facilities to choose from
//                val facilitiesList = listOf("Wi-Fi", "Pool", "Gym", "Parking")
//                facilitiesList.forEach { facility ->
//                    Row {
//                        Checkbox(
//                            checked = facilities.contains(facility),
//                            onCheckedChange = { checked ->
//                                facilities = if (checked) facilities + facility else facilities - facility
//                            }
//                        )
//                        Text(facility)
//                    }
//                }
//
//                // Services
//                // Assuming you have a list of services to choose from
//                val servicesList = listOf("Room Service", "Laundry", "Breakfast")
//                servicesList.forEach { service ->
//                    Row {
//                        Checkbox(
//                            checked = services.contains(service),
//                            onCheckedChange = { checked ->
//                                services = if (checked) services + service else services - service
//                            }
//                        )
//                        Text(service)
//                    }
//                }
//
//                // Status
//                val statusOptions = listOf("Available", "Booked")
//                statusOptions.forEach { option ->
//                    Row {
//                        RadioButton(
//                            selected = status == option,
//                            onClick = { status = option }
//                        )
//                        Text(option)
//                    }
//                }
//
//                // Search Location
//                OutlinedTextField(
//                    value = searchLocation,
//                    onValueChange = { searchLocation = it },
//                    label = { Text("Search Location") },
//                    modifier = Modifier.fillMaxWidth()
//                )
//
//                // Save and Cancel Buttons
//                Row(
//                    modifier = Modifier.fillMaxWidth(),
//                    horizontalArrangement = Arrangement.End
//                ) {
//                    Button(onClick = onDismiss) {
//                        Text("Cancel")
//                    }
//                    Spacer(modifier = Modifier.width(8.dp))
//                    Button(onClick = {
//                        val room = CreateOwnerRoomRequest(
//                            category,
//                            roomName,
//                            description,
//                            rules,
//                            pricePerNight,
//                            priceNotPerNight,
//                            taxPercentage,
//                            capacity,
//                            numberOfRooms,
//                            fullAddress,
//                            nearByAddress,
//                            city,
//                            state,
//                            country,
//                            pinCode,
//                            availableFrom,
//                            mainImage ?: "",
//                            additionalImages,
//                            facilities,
//                            services,
//                            status,
//                            searchLocation
//                        )
//                        onSave(room)
//                    }) {
//                        Text("Save")
//                    }
//                }
//            }
//        }
//    }
//}
//
////@Composable
////fun RoomDialogs(
////    room: Rooms?,
////    onDismiss: () -> Unit,
////    onSave: (Rooms) -> Unit
////) {
////    // State variables for all fields
////    var category by remember { mutableStateOf(room?.category?.name ?: "") }
////    var name by remember { mutableStateOf(room?.room_name ?: "") }
////    var description by remember { mutableStateOf(room?.description ?: "") }
////    var price by remember { mutableStateOf(room?.price_per_night ?: "") }
////    var tax by remember { mutableStateOf(room?.tax ?: "") }
////    var capacity by remember { mutableStateOf(room?.capacity?.toString() ?: "") }
////    var roomCount by remember { mutableStateOf(room?.no_of_room?.toString() ?: "") }
////    var address by remember { mutableStateOf(room?.address ?: "") }
////    var city by remember { mutableStateOf(room?.city ?: "") }
////    var state by remember { mutableStateOf(room?.state ?: "") }
////    var country by remember { mutableStateOf(room?.country ?: "") }
////    var pinCode by remember { mutableStateOf(room?.postal_code ?: "") }
////    var status by remember { mutableStateOf(room?.status ?: "Available") }
////    var availableFrom by remember { mutableStateOf(room?.available_from ?: "") }
////    var mainImage by remember { mutableStateOf(room?.main_image ?: "") }
////    var additionalImages by remember { mutableStateOf(room?.additional_images ?: emptyList()) }
////    var facilities by remember { mutableStateOf(room?.facilities ?: emptyList()) }
////    var services by remember { mutableStateOf(room?.services ?: emptyList()) }
////
////    Dialog(onDismissRequest = onDismiss) {
////        Surface(
////            shape = RoundedCornerShape(16.dp),
////            modifier = Modifier
////                .fillMaxWidth()
////                .height(600.dp)
////        ) {
////            Column(
////                modifier = Modifier
////                    .padding(16.dp)
////                    .verticalScroll(rememberScrollState())
////            ) {
////                // Add all input fields here (same as before)
////
////                // Save Button
////                Button(
////                    onClick = {
////                        val updatedRoom = Rooms(
////                            id = room?.id ?: 0,
////                            category = Category(0, category, ""),
////                            room_name = name,
////                            description = description,
////                            price_per_night = price,
////                            tax = tax,
////                            capacity = capacity.toInt(),
////                            no_of_room = roomCount.toInt(),
////                            address = address,
////                            city = city,
////                            state = state,
////                            country = country,
////                            postal_code = pinCode,
////                            status = status,
////                            facilities = facilities,
////                            services = services,
////                            status = status,
////
////                        )
////                        onSave(updatedRoom)
////                    },
////                    colors = ButtonDefaults.buttonColors(containerColor = Color.Red),
////                    modifier = Modifier.fillMaxWidth()
////                ) {
////                    Text(if (room == null) "Add Room" else "Save", color = Color.White)
////                }
////            }
////        }
////    }
////}
//
//@Composable
//fun InfoChip(text: String) {
//    Box(
//        modifier = Modifier
//            .clip(RoundedCornerShape(8.dp))
//            .background(MaterialTheme.colorScheme.secondaryContainer)
//            .padding(horizontal = 8.dp, vertical = 4.dp)
//    ) {
//        Text(text, style = MaterialTheme.typography.labelSmall)
//    }
//}
//
//
//
//
//
//
//
////    Column(
////        modifier = Modifier
////            .fillMaxSize()
////            .padding(16.dp)
////    ) {
////        Text(
////            text =  "Rooms",
////            style = MaterialTheme.typography.headlineMedium,
////            modifier = Modifier.fillMaxWidth(),
////            textAlign = TextAlign.Center
////        )
////
////        Spacer(Modifier.height(16.dp))
////
////        LazyColumn(
////            verticalArrangement = Arrangement.spacedBy(12.dp),
////            modifier = Modifier.weight(1f)
////        ) {
////            items(rooms) { room ->
////                RoomCard(
////                    room = room,
////                    onView = { selectedRoom = room },
////                    onEdit = { selectedRoom = room; showRoomDialog = true },
////                    onDelete = { rooms = rooms - room }
////                )
////            }
////        }
////
////        Button(
////            onClick = {
////                selectedRoom = null
////                showRoomDialog = true
////            },
////            modifier = Modifier
////                .width(150.dp)
////                .height(45.dp),
////            colors = ButtonDefaults.buttonColors(containerColor = Color.Red),
////            shape = RoundedCornerShape(8.dp) // Adds rounded corners for a better look
////        ) {
////            Text(
////                text = "Add Room",
////                color = Color.White, // Ensures text is readable on red background
////                fontSize = 14.sp,
////                fontWeight = FontWeight.Bold
////            )
////        }
////
////    }
////
////    if (showRoomDialog) {
////        RoomDialog(
////            room = selectedRoom,
////            onDismiss = { showRoomDialog = false },
////            onSave = { room ->
////                if (selectedRoom == null) {
////                    rooms = rooms + room
////                } else {
////                    rooms = rooms.map { if (it.id == room.id) room else it }
////                }
////                showRoomDialog = false
////            }
////        )
////    }
////}
////
////// Enhanced RoomCard
////@Composable
////fun RoomCard(
////    room: Room,
////    onView: () -> Unit,
////    onEdit: () -> Unit,
////    onDelete: () -> Unit
////) {
////    Card(
////        elevation = CardDefaults.cardElevation(4.dp),
////        colors = CardDefaults.cardColors(Color.White),
////        modifier = Modifier.fillMaxWidth()
////    ) {
////        Column(modifier = Modifier.padding(16.dp)) {
////            Row(verticalAlignment = Alignment.CenterVertically) {
////                Text(
////                    text = "ID: ${room.id}",
////                    style = MaterialTheme.typography.labelSmall,
////                    color = MaterialTheme.colorScheme.onSurfaceVariant
////                )
////                Spacer(Modifier.weight(1f))
////                Text(
////                    text = room.status,
////                    color = when (room.status) {
////                        "Available" -> Color.Green
////                        else -> Color.Red
////                    },
////                    style = MaterialTheme.typography.labelSmall
////                )
////            }
////
////            Spacer(Modifier.height(8.dp))
////            Text(room.name, style = MaterialTheme.typography.titleLarge)
////            Text(room.category, style = MaterialTheme.typography.bodySmall)
////
////            Spacer(Modifier.height(8.dp))
////            Text(room.description, style = MaterialTheme.typography.bodyMedium)
////
////            Divider(Modifier.padding(vertical = 8.dp))
////
////            Row {
////                InfoChip("Capacity: ${room.capacity}")
////                Spacer(Modifier.width(4.dp))
////                InfoChip("Rooms: ${room.numberOfRooms}")
////                Spacer(Modifier.width(4.dp))
////                InfoChip("₹${room.pricePerNight}/night")
////            }
////
////            Spacer(Modifier.height(8.dp))
////            Row(horizontalArrangement = Arrangement.End, modifier = Modifier.fillMaxWidth()) {
////                IconButton(onClick = onView) {
////                    Icon(Icons.Default.Visibility, "View")
////                }
////                IconButton(onClick = onEdit) {
////                    Icon(Icons.Default.Edit, "Edit")
////                }
////                IconButton(onClick = onDelete) {
////                    Icon(Icons.Default.Delete, "Delete")
////                }
////            }
////        }
////    }
////}
//////
////@Composable
////fun InfoChip(text: String) {
////    Box(
////        modifier = Modifier
////            .clip(RoundedCornerShape(8.dp))
////            .background(MaterialTheme.colorScheme.secondaryContainer)
////            .padding(horizontal = 8.dp, vertical = 4.dp)
////    ) {
////        Text(text, style = MaterialTheme.typography.labelSmall)
////    }
////}
//
////// Enhanced RoomDialog
////@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterialApi::class)
////@Composable
////fun RoomDialog(
////    room: Room?,
////    onDismiss: () -> Unit,
////    onSave: (Room) -> Unit
////) {
////    val scrollState = rememberScrollState()
////    val context = LocalContext.current
////    val categories = listOf("Standard", "Deluxe", "Suite", "Executive", "Presidential")
////
////    // State variables
////    var category by remember { mutableStateOf(room?.category ?: "") }
////    var name by remember { mutableStateOf(room?.name ?: "") }
////    var description by remember { mutableStateOf(room?.description ?: "") }
////    var rules by remember { mutableStateOf(room?.rules?.joinToString("\n") ?: "") }
////    var price by remember { mutableStateOf(room?.pricePerNight?.toString() ?: "") }
////    var tax by remember { mutableStateOf(room?.taxPercentage?.toString() ?: "") }
////    var capacity by remember { mutableStateOf(room?.capacity?.toString() ?: "") }
////    var roomCount by remember { mutableStateOf(room?.numberOfRooms?.toString() ?: "") }
////    var location by remember { mutableStateOf(room?.location ?: "") }
////    var address by remember { mutableStateOf(room?.fullAddress ?: "") }
////    var nearAddress by remember { mutableStateOf(room?.nearByAddress ?: "") }
////    var city by remember { mutableStateOf(room?.city ?: "") }
////    var state by remember { mutableStateOf(room?.state ?: "") }
////    var country by remember { mutableStateOf(room?.country ?: "") }
////    var pinCode by remember { mutableStateOf(room?.pinCode ?: "") }
////    var availableFrom by remember { mutableStateOf(room?.availableFrom ?: "") }
////    var status by remember { mutableStateOf(room?.status ?: "Available") }
////    var mainImage by remember { mutableStateOf(room?.mainImage ?: "") }
////    var additionalImages by remember { mutableStateOf(room?.additionalImages ?: emptyList()) }
////    var facilities by remember { mutableStateOf(room?.facilities ?: emptyList()) }
////    var services by remember { mutableStateOf(room?.services ?: emptyList()) }
////
////    // Image Picker
////    val multipleImagePicker = rememberLauncherForActivityResult(
////        contract = ActivityResultContracts.PickMultipleVisualMedia()
////    ) { uris ->
////        additionalImages = uris.map { it.toString() }
////    }
////
////    // Date Picker
////    val datePicker = rememberLauncherForActivityResult(
////        contract = ActivityResultContracts.PickVisualMedia()
////    ) { /* Handle date selection here */ }
////
////    Dialog(onDismissRequest = onDismiss) {
////        Surface(
////            shape = RoundedCornerShape(16.dp),
////            modifier = Modifier
////                .fillMaxWidth()
////                .height(600.dp)
////        ) {
////            Column(
////                modifier = Modifier
////                    .padding(16.dp)
////                    .verticalScroll(scrollState)
////            ) {
////                // Category Dropdown
////                ExposedDropdownMenuBox(
////                    expanded = false,
////                    onExpandedChange = {}
////                ) {
////                    OutlinedTextField(
////                        value = category,
////                        onValueChange = { category = it },
////                        label = { Text("Room Category") },
////                        modifier = Modifier.fillMaxWidth(),
////                        trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = false) },
////                        colors = TextFieldDefaults.outlinedTextFieldColors(
////                            //Color = Color.Black,
////                            focusedBorderColor = Color.Gray,
////                            unfocusedBorderColor = Color.LightGray
////                        )
////                    )
////                }
////
////                Spacer(Modifier.height(8.dp))
////
////                // Room Name
////                OutlinedTextField(
////                    value = name,
////                    onValueChange = { name = it },
////                    label = { Text("Room Name") },
////                    modifier = Modifier.fillMaxWidth()
////                )
////
////                Spacer(Modifier.height(8.dp))
////
////                // Description
////                OutlinedTextField(
////                    value = description,
////                    onValueChange = { description = it },
////                    label = { Text("Description") },
////                    modifier = Modifier
////                        .fillMaxWidth()
////                        .height(100.dp),
////                    maxLines = 3
////                )
////
////                Spacer(Modifier.height(8.dp))
////
////                // Rules
////                OutlinedTextField(
////                    value = rules,
////                    onValueChange = { rules = it },
////                    label = { Text("Rules (start each line with '-')") },
////                    modifier = Modifier
////                        .fillMaxWidth()
////                        .height(100.dp),
////                    maxLines = 3
////                )
////
////                Spacer(Modifier.height(8.dp))
////
////                // Pricing Section
////                Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
////                    OutlinedTextField(
////                        value = price,
////                        onValueChange = { price = it },
////                        label = { Text("Price/Night (₹)") },
////                        modifier = Modifier.weight(0.48f),
////                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
////                    )
////                    OutlinedTextField(
////                        value = tax,
////                        onValueChange = { tax = it },
////                        label = { Text("Tax (%)") },
////                        modifier = Modifier.weight(0.48f),
////                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
////                    )
////                }
////
////                Spacer(Modifier.height(8.dp))
////
////                // Capacity Section
////                Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
////                    OutlinedTextField(
////                        value = capacity,
////                        onValueChange = { capacity = it },
////                        label = { Text("Capacity") },
////                        modifier = Modifier.weight(0.48f),
////                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
////                    )
////                    OutlinedTextField(
////                        value = roomCount,
////                        onValueChange = { roomCount = it },
////                        label = { Text("Number of Rooms") },
////                        modifier = Modifier.weight(0.48f),
////                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
////                    )
////                }
////
////                Spacer(Modifier.height(8.dp))
////
////                // Location Section
////                Column(Modifier.fillMaxWidth()) {
////                    OutlinedTextField(
////                        value = location,
////                        onValueChange = { location = it },
////                        label = { Text("Location Name") },
////                        modifier = Modifier.fillMaxWidth()
////                    )
////                    Spacer(Modifier.height(4.dp))
////                    OutlinedTextField(
////                        value = address,
////                        onValueChange = { address = it },
////                        label = { Text("Full Address") },
////                        modifier = Modifier.fillMaxWidth()
////                    )
////                    Spacer(Modifier.height(4.dp))
////                    OutlinedTextField(
////                        value = nearAddress,
////                        onValueChange = { nearAddress = it },
////                        label = { Text("Nearby Address") },
////                        modifier = Modifier.fillMaxWidth()
////                    )
////                }
////
////                Spacer(Modifier.height(8.dp))
////
////                // City/State Section
////                Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
////                    OutlinedTextField(
////                        value = city,
////                        onValueChange = { city = it },
////                        label = { Text("City") },
////                        modifier = Modifier.weight(0.48f)
////                    )
////                    OutlinedTextField(
////                        value = state,
////                        onValueChange = { state = it },
////                        label = { Text("State") },
////                        modifier = Modifier.weight(0.48f)
////                    )
////                }
////
////                Spacer(Modifier.height(8.dp))
////
////                // Country/Pincode Section
////                Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
////                    OutlinedTextField(
////                        value = country,
////                        onValueChange = { country = it },
////                        label = { Text("Country") },
////                        modifier = Modifier.weight(0.48f)
////                    )
////                    OutlinedTextField(
////                        value = pinCode,
////                        onValueChange = { pinCode = it },
////                        label = { Text("Pin Code") },
////                        modifier = Modifier.weight(0.48f),
////                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
////                    )
////                }
////
////                Spacer(Modifier.height(8.dp))
////
////                // Image Selection
////                Column {
////                    Text("Main Image", style = TextStyle(fontSize = 14.sp, color = Color.Gray))
////                    Button(
////                        onClick = { /* Implement image selection */ },
////                        colors = ButtonDefaults.buttonColors(containerColor = Color.LightGray),
////                        modifier = Modifier.fillMaxWidth()
////                    ) {
////                        Text("Select Main Image", color = Color.Black)
////                    }
////
////                    Spacer(Modifier.height(8.dp))
////
////                    Text("Additional Images", style = TextStyle(fontSize = 14.sp, color = Color.Gray))
////                    Button(
////                        onClick = { multipleImagePicker.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)) },
////                        colors = ButtonDefaults.buttonColors(containerColor = Color.LightGray),
////                        modifier = Modifier.fillMaxWidth()
////                    ) {
////                        Text("Select Additional Images", color = Color.Black)
////                    }
////                }
////
////                Spacer(Modifier.height(8.dp))
////
////                // Status Dropdown
////                ExposedDropdownMenuBox(
////                    expanded = false,
////                    onExpandedChange = {}
////                ) {
////                    OutlinedTextField(
////                        value = status,
////                        onValueChange = {},
////                        label = { Text("Status") },
////                        readOnly = true,
////                        trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = false) },
////                        modifier = Modifier.fillMaxWidth()
////                    )
////                }
////
////                Spacer(Modifier.height(16.dp))
////
////                // Buttons
////                Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
////                    Button(
////                        onClick = onDismiss,
////                        colors = ButtonDefaults.buttonColors(containerColor = Color.Red),
////                        modifier = Modifier.weight(0.48f)
////                    ) {
////                        Text("Cancel", color = Color.White)
////                    }
////
////                    Button(
////                        onClick = {
////                            onSave(
////                                Room(
////                                    id = room?.id ?: System.currentTimeMillis(),
////                                    category = category,
////                                    name = name,
////                                    description = description,
////                                    rules = rules.split("\n"),
////                                    pricePerNight = price.toIntOrNull() ?: 0,
////                                    taxPercentage = tax.toIntOrNull() ?: 0,
////                                    capacity = capacity.toIntOrNull() ?: 0,
////                                    numberOfRooms = roomCount.toIntOrNull() ?: 0,
////                                    location = location,
////                                    fullAddress = address,
////                                    nearByAddress = nearAddress,
////                                    city = city,
////                                    state = state,
////                                    country = country,
////                                    pinCode = pinCode,
////                                    availableFrom = availableFrom,
////                                    mainImage = mainImage,
////                                    additionalImages = additionalImages,
////                                    facilities = facilities,
////                                    services = services,
////                                    status = status
////                                )
////                            )
////                        },
////                        colors = ButtonDefaults.buttonColors(containerColor = Color.Red),
////                        modifier = Modifier.weight(0.48f)
////                    ) {
////                        Text(if (room == null) "Add Room" else "Save", color = Color.White)
////                    }
////                }
////            }
////        }
////    }
////}
////
//@Composable
//fun SectionTitle(text: String) {
//    Text(
//        text = text,
//        style = MaterialTheme.typography.titleMedium,
//        modifier = Modifier.padding(vertical = 8.dp)
//    )
//}
//
//// Sample data
////val sampleRooms = listOf(
////    Room(
////        id = 1,
////        category = "Deluxe Suite",
////        name = "Premium City View Suite",
////        description = "Spacious suite with panoramic city views",
////        rules = listOf("- No smoking", "- No pets allowed"),
////        pricePerNight = 25000,
////        taxPercentage = 18,
////        capacity = 4,
////        numberOfRooms = 10,
////        location = "City Center",
////        fullAddress = "123 Luxury Street, Downtown",
////        nearByAddress = "Near Central Business District",
////        city = "Mumbai",
////        state = "Maharashtra",
////        country = "India",
////        pinCode = "400001",
////        availableFrom = "2024-03-01",
////        mainImage = "",
////        additionalImages = emptyList(),
////        facilities = listOf("Wi-Fi", "Mini Bar"),
////        services = listOf("24/7 Room Service"),
////        status = "Available"
////    )
////)

