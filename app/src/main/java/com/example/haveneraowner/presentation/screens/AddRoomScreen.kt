package com.example.haveneraowner.presentation.screens

import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
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
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil3.compose.AsyncImage
import com.example.haveneraowner.data.models.CreateOwnerRoomRequest
import com.example.haveneraowner.presentation.viewModels.AuthViewModel
import com.example.haveneraowner.ui.theme.Purple40
import com.example.haveneraowner.ui.theme.TextPrimary
import com.example.haveneraowner.ui.theme.TextSecondary
import com.example.haveneraowner.ui.theme.deepBlue
import com.example.haveneraowner.utils.DateRangeSelectorAddRoom
import com.example.haveneraowner.utils.DesignerTextFieldAddRoom
import com.example.haveneraowner.utils.FormatToNormalDate
import com.example.haveneraowner.utils.ThemedDropdown
import com.example.haveneraowner.utils.UploadMainAndAdditionalImagesAddRoom
import com.example.haveneraowner.utils.UriToImage
import com.example.haveneraowner.utils.datePicker
import com.example.haveneraowner.utils.toRequestBody
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddRoom(navController: NavController, viewModel: AuthViewModel = koinViewModel()) {


    val addRoomState = viewModel.createOwnerRoomState.collectAsState()
    val context = LocalContext.current
    var showLocationDialog by remember { mutableStateOf(false) }
    val userLocation = "nhi aa rha hai"


    var roomName by remember { mutableStateOf("") }
    var price by remember { mutableStateOf("") }                 // discounted / selling price
    var maxPrice by remember { mutableStateOf("") }              // original max price (price not per night)
    var discountPercent by remember { mutableStateOf("") }       // auto-calculated discount (%)
    var taxPercent by remember { mutableStateOf("5") }           // tax percentage (default 5%)
    var priceFormat by remember { mutableStateOf("Night") }
    var categorySelected by remember { mutableStateOf("Select an option") }
    var statusSelected by remember { mutableStateOf("Single") }
//        var roomTypeSelected by remember { mutableStateOf("Available") }
    var fromDate by remember { mutableStateOf("Select Date") }
    var toDate by remember { mutableStateOf("Select Date") }
//        var rules by remember { mutableStateOf("") }
//        var capacity by remember { mutableStateOf("") }
    var nearBy by remember { mutableStateOf("") }


    var address1 by remember { mutableStateOf("") }
    var address2 by remember { mutableStateOf("") }
    var state by remember { mutableStateOf("") }
    var city by remember { mutableStateOf("") }
    var postalCode by remember { mutableStateOf("") }
    var roomNumber by remember { mutableStateOf("") }

    val available = remember { mutableStateOf("Available") }
    val contry = remember { mutableStateOf("India") }
//        var facilities by remember { mutableStateOf("") }
//        var services by remember { mutableStateOf("") }
    val additionalImageUris = remember { mutableStateOf<List<Uri>>(emptyList()) }


    var description by remember { mutableStateOf("") }

    val imageUri = remember { mutableStateOf<Uri?>(null) }

    var showPreviewDialog by remember { mutableStateOf(false) }

    var categoryOptions by remember { mutableStateOf<List<String>>(emptyList()) }
    var categoryMap by remember { mutableStateOf<Map<String, Int>>(emptyMap()) }


    val pickMultipleImagesLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickMultipleVisualMedia()
    ) { uris ->
        if (uris.isNotEmpty()) {
            val limitedUris = uris.take(2)
            additionalImageUris.value = limitedUris
        }
    }


    val pickImageLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia()
    ) { uri ->
        if (uri != null) {
            imageUri.value = uri

        }
    }


    val getAllRoomCategoryList = viewModel.categoryState.collectAsState()

    LaunchedEffect(key1 = Unit) {
        viewModel.getCategoryList()
        //viewModel.displayUserLocationFromPreferences()
    }

    when {
        getAllRoomCategoryList.value.isLoading -> {
            // Show a loading indicator, maybe a CircularProgressIndicator
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }

        getAllRoomCategoryList.value.success != null -> {
            getAllRoomCategoryList.value.success?.body()?.let { list ->
                val names = list.map { it.name }
                val map = list.associate { it.name to it.id }
                categoryOptions = names
                categoryMap = map
            }
        }

        getAllRoomCategoryList.value.error != null -> {
            Toast.makeText(
                context,
                "Error: ${getAllRoomCategoryList.value.error}",
                Toast.LENGTH_SHORT
            ).show()
        }
    }






    when {
        addRoomState.value.isLoading -> {
            // Show a loading indicator, maybe a CircularProgressIndicator
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }

        addRoomState.value.success != null -> {

            Toast.makeText(context, "Room added successfully!", Toast.LENGTH_SHORT).show()
            Toast.makeText(context, "Your Post Will Go Live And Successfully Upload With in Few Minutes", Toast.LENGTH_SHORT).show()
            roomName = ""
            price = ""
            maxPrice = ""
            discountPercent = ""
            taxPercent = "5"
            priceFormat = "Night"
            categorySelected = "Select an option"
            statusSelected = "Single"
//                roomTypeSelected = "Available"
            fromDate = "Select Date"
            toDate = "Select Date"
//                rules = ""
//                capacity = ""
            address1 = ""
            address2 = ""
            state = ""
            city = ""
            postalCode = ""
            roomNumber = ""
//                facilities = ""
//                services = ""
            description = ""
            imageUri.value = null
            additionalImageUris.value = emptyList()
            addRoomState.value.success = null

        }

        addRoomState.value.error != null -> {
            Toast.makeText(context, "Error: ${addRoomState.value.error}", Toast.LENGTH_SHORT).show()

            Text(
                text = "Error: ${addRoomState.value.error}",
                color = Color.Red,
                modifier = Modifier.padding(16.dp)
            )
        }
    }

    Scaffold(
        contentWindowInsets = WindowInsets.systemBars,
        modifier = Modifier
            .fillMaxSize()
            .background(White),
        containerColor = White,
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Add Room",
                        fontSize = 22.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = TextPrimary
                    )
                },
                navigationIcon = {
                    IconButton(
                        onClick = { navController.popBackStack() },
                        modifier = Modifier
                            .padding(start = 12.dp)
                            .size(40.dp)
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back",
                            tint = deepBlue
                        )
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = White,
                    titleContentColor = deepBlue
                ),
                modifier = Modifier.shadow(2.dp)
            )
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
        ) {

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 20.dp)
                    .imePadding()

                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.spacedBy(10.dp),
            ) {

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 16.dp),
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    // First Card
                    Box(
                        modifier = Modifier.weight(1f)
                    ) {



                        UploadMainAndAdditionalImagesAddRoom(
                            title = "Upload Main Image",
                            description = "Upload the main image of your room.",
                            onUploadClick = {
                                pickImageLauncher.launch(
                                    PickVisualMediaRequest(
                                        ActivityResultContracts.PickVisualMedia.ImageOnly
                                    )
                                )
                            },
                            imageUri = imageUri
                        )

                    }

                    // Second Card
                    Box(
                        modifier = Modifier.weight(1f)
                    ) {
                        UploadMainAndAdditionalImagesAddRoom(
                            title = "Add Additional Images",
                            description = "Upload more photos to showcase.",
                            onUploadClick = {
                                pickMultipleImagesLauncher.launch(
                                    PickVisualMediaRequest(
                                        ActivityResultContracts.PickVisualMedia.ImageOnly
                                    )
                                )
                            },
                            imageUris = additionalImageUris

                        )



                    }
                }

                Text(
                    "Room Information",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center,
                    color = TextPrimary
                )

                DesignerTextFieldAddRoom(
                    value = roomName,
                    label = "Title",
                    onValueChange = { roomName = it },
                    maxLength = 40 // ensures it won’t be too long

                )

                DesignerTextFieldAddRoom(
                    value = roomNumber,
                    label = "Room Number",
                    onValueChange = { roomNumber = it },
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Next,
                    maxLength = 10, // ensures it won’t be too long
                    numericOnly = true // only digits allowed


                )

                DesignerTextFieldAddRoom(
                    value = price,
                    label = "Room Price (in ₹)",
                    onValueChange = {
                        price = it
                        val max = maxPrice.toDoubleOrNull()
                        val sell = it.toDoubleOrNull()
                        discountPercent = if (max != null && max > 0 && sell != null && sell <= max) {
                            val discount = ((max - sell) / max) * 100.0
                            String.format("%.0f", discount)
                        } else {
                            ""
                        }
                    },
                    imeAction = ImeAction.Next,
                    keyboardType = KeyboardType.Number,
                    maxLength = 7,
                    numericOnly = true
                )

                DesignerTextFieldAddRoom(
                    value = maxPrice,
                    label = "Price Not Per Night (Max Price in ₹)",
                    onValueChange = {
                        maxPrice = it
                        val max = it.toDoubleOrNull()
                        val sell = price.toDoubleOrNull()
                        discountPercent = if (max != null && max > 0 && sell != null && sell <= max) {
                            val discount = ((max - sell) / max) * 100.0
                            String.format("%.0f", discount)
                        } else {
                            ""
                        }
                    },
                    imeAction = ImeAction.Next,
                    keyboardType = KeyboardType.Number,
                    maxLength = 7,
                    numericOnly = true
                )

                DesignerTextFieldAddRoom(
                    value = if (discountPercent.isNotBlank()) "$discountPercent% OFF" else "",
                    label = "Discount (%)",
                    onValueChange = { /* read-only visual field */ },
                    imeAction = ImeAction.Next,
                    keyboardType = KeyboardType.Number
                )

                DesignerTextFieldAddRoom(
                    value = taxPercent,
                    label = "Tax (%)",
                    onValueChange = { input ->
                        // allow only digits and decimal point
                        taxPercent = input.filter { it.isDigit() || it == '.' }
                    },
                    imeAction = ImeAction.Next,
                    keyboardType = KeyboardType.Number
                )

                val computedTaxAmount = run {
                    val priceVal = price.toDoubleOrNull()
                    val taxVal = taxPercent.toDoubleOrNull()
                    if (priceVal != null && taxVal != null) {
                        (priceVal * taxVal) / 100.0
                    } else {
                        null
                    }
                }

                computedTaxAmount?.let { amount ->
                    Text(
                        text = "Tax Amount: ₹${"%.2f".format(amount)}",
                        fontSize = 12.sp,
                        color = TextSecondary
                    )
                }

                ThemedDropdown(
                    label = "Category",
                    options = categoryOptions,
                    selectedOption = categorySelected,
                    onOptionSelected = { categorySelected = it }
                )


//                    )

                Text(
                    "Room Available Date",
                    fontSize = 13.sp,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier.fillMaxWidth(),
                    color = TextPrimary
                )

                DateRangeSelectorAddRoom(
                    fromDate = fromDate,
                    toDate = toDate,
                    onFromDateClick = {
                        CoroutineScope(Dispatchers.Main).launch {
                            fromDate = datePicker(context)
                        }
                    },
                    onToDateClick = {
                        CoroutineScope(Dispatchers.Main).launch {
                            toDate = datePicker(context)
                        }
                    }
                )


                Spacer(modifier = Modifier.height(10.dp))

                Text(
                    "Room Location",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center,
                    color = TextPrimary
                )


                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    Column(modifier = Modifier.weight(1f)) {
                        DesignerTextFieldAddRoom(
                            value = address1,
                            label = "Address Line 1",
                            onValueChange = { address1 = it },
                            imeAction = ImeAction.Next,
                            maxLength = 100 // ensures it won’t be too long
                        )
                    }
                    OutlinedButton(
                        onClick = {
                            showLocationDialog = true
                            //viewModel.displayUserLocationFromPreferences()
                        },
                        modifier = Modifier.alignByBaseline()
                    ) {
                        Text("Get")
                    }
                }


                DesignerTextFieldAddRoom(
                    value = address2,
                    label = "Address Line 2",
                    onValueChange = { address2 = it },
                    imeAction = ImeAction.Next,
                    maxLength = 100 // ensures it won’t be too long
                )

                DesignerTextFieldAddRoom(
                    value = city,
                    label = "City",
                    onValueChange = { city = it },
                    imeAction = ImeAction.Next,
                    maxLength = 20 // ensures it won’t be too long
                )

                DesignerTextFieldAddRoom(
                    value = state,
                    label = "State",
                    onValueChange = { state = it },
                    imeAction = ImeAction.Next,
                    maxLength = 20 // ensures it won’t be too long
                )

                DesignerTextFieldAddRoom(
                    value = postalCode,
                    label = "Postal Code",
                    onValueChange = { postalCode = it },
                    imeAction = ImeAction.Next,
                    keyboardType = KeyboardType.Number,
                    maxLength = 6, // ensures it won’t be too long
                    numericOnly = true // only digits allowed
                )


                Spacer(modifier = Modifier.height(10.dp))

//


                Spacer(modifier = Modifier.height(10.dp))

                Text(
                    "Room Description",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center,
                    color = TextPrimary
                )

                DesignerTextFieldAddRoom(
                    description,
                    label = "Enter Here The Description",
                    onValueChange = { description = it },
                    minLines = 3,
                    imeAction = ImeAction.Done,
                    maxLength = 250,
                    maxWords = 50
                )


                if (showLocationDialog && userLocation.isNotBlank()) {
                    AlertDialog(
                        onDismissRequest = { showLocationDialog = false },
                        title = { Text("Use Current Location?") },
                        text = {
                            Column(
                                modifier = Modifier
                                    .heightIn(max = 200.dp) // Limit height to make scrollable when needed
                                    .verticalScroll(rememberScrollState())
                            ) {
                                Text(userLocation)
                            }
                        },
                        confirmButton = {
                            Button(onClick = {
                                address1 = userLocation
                                city = extractCity(userLocation)
                                state = extractState(userLocation)
                                showLocationDialog = false
                            }) {
                                Text("Yes, Use it")
                            }
                        },
                        dismissButton = {
                            TextButton(onClick = {
                                showLocationDialog = false
                            }) {
                                Text("Cancel")
                            }
                        }
                    )
                }

                // Preview Dialog

                if (showPreviewDialog) {
                    AlertDialog(
                        onDismissRequest = { showPreviewDialog = false },
                        title = { Text("Confirm Room Details") },
                        text = {
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .heightIn(min = 100.dp, max = 500.dp) // adjust as needed
                                    .verticalScroll(rememberScrollState()),
                                verticalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                // Main Image
                                imageUri.value?.let {
                                    AsyncImage(
                                        model = it,
                                        contentDescription = "Main Image",
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .height(150.dp)
                                            .clip(RoundedCornerShape(12.dp)),
                                        contentScale = ContentScale.Crop
                                    )
                                }

                                // Additional Images
                                if (additionalImageUris.value.isNotEmpty()) {
                                    Text("Additional Images:", fontWeight = FontWeight.Bold)
                                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                                        additionalImageUris.value.forEach { uri ->
                                            AsyncImage(
                                                model = uri,
                                                contentDescription = "Additional Image",
                                                modifier = Modifier
                                                    .size(60.dp)
                                                    .clip(RoundedCornerShape(8.dp)),
                                                contentScale = ContentScale.Crop
                                            )
                                        }
                                    }
                                }

                                Text("Room Name: $roomName")
                                Text("Room Number: $roomNumber")
                                Text("Price: ₹$price")
                                Text("Category: $categorySelected")
                                Text("Available From: $fromDate")
                                Text("Available To: $toDate")
                                Text("Address Line 1: $address1")
                                Text("Address Line 2: $address2")
                                Text("City: $city")
                                Text("State: $state")
                                Text("Postal Code: $postalCode")
                                Text("Description: $description")
                                Text("Status: ${available.value}")
                                Text("Note: Your Post Will Go Live And Successfully Upload Within Few Minutes")
                            }
                        },
                        confirmButton = {
                            Button(onClick = {
                                // Images are currently validated before opening this dialog.
                                // You can later wire these into your backend if needed.
                                val mainImage = imageUri.value?.let { UriToImage(context, it, "main_image") }
                                val additionalImages = additionalImageUris.value.mapNotNull { uri ->
                                    UriToImage(context, uri, "additional_images")
                                }

                                val categoryId = categoryMap[categorySelected] ?: 0
                                val priceValue = price.toDoubleOrNull() ?: 0.0
                                val maxPriceValue = maxPrice.toDoubleOrNull()
                                val taxValue = taxPercent.toDoubleOrNull()

                                if (priceValue <= 0.0) {
                                    Toast.makeText(
                                        context,
                                        "Please enter a valid Room Price",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                    return@Button
                                }

                                if (maxPriceValue == null || maxPriceValue <= 0.0) {
                                    Toast.makeText(
                                        context,
                                        "Please enter a valid Price Not Per Night (Max Price)",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                    return@Button
                                }

                                if (priceValue > maxPriceValue) {
                                    Toast.makeText(
                                        context,
                                        "Room Price cannot be greater than Max Price",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                    return@Button
                                }

                                val finalTax = taxValue ?: 5.0
                                val roomFor = if (statusSelected.equals("Single", ignoreCase = true)) 1 else 2

                                val capacityValue = 1
                                val roomCount = 1
                                val latitudeValue = 0.0
                                val longitudeValue = 0.0
                                val addressValue = address1
                                val availableFromValue = FormatToNormalDate(fromDate)

                                viewModel.createOwnerRoom(
                                    CreateOwnerRoomRequest(
                                        category = categoryId,
                                        room_for = roomFor,
                                        room_name = roomName,
                                        description = description,
                                        price_per_night = priceValue,
                                        price_not_per_night = maxPriceValue,
                                        tax = finalTax,
                                        capacity = capacityValue,
                                        no_of_room = roomCount,
                                        latitude = latitudeValue,
                                        longitude = longitudeValue,
                                        address = addressValue,
                                        city = city.ifBlank { null },
                                        state = state.ifBlank { null },
                                        country = contry.value.ifBlank { null },
                                        postal_code = postalCode.ifBlank { null },
                                        available_from = availableFromValue,
                                        facilities = emptyList(),
                                        services = emptyList(),
                                        status = available.value,
                                        main_image = null,
                                        additional_images = emptyList(),
                                        pricePerNight = price.toRequestBody(),
                                        roomName = roomName.toRequestBody(),
                                        addressLine2 = address2.toRequestBody(),
                                        roomNo = roomNumber.toRequestBody(),
                                        nearBy = nearBy.toRequestBody()
                                    )
                                )
//                                viewModel.createOwnerRoom(
//                                    CreateOwnerRoomRequest(
//                                        category = categoryMap[categorySelected].toString()
//                                            .toRequestBody(),
//                                        roomName = roomName.toRequestBody(),
//                                        description = description.toRequestBody(),
//                                        pricePerNight = price.toRequestBody(),
//                                        roomNo = roomNumber.toRequestBody(),
//                                        nearBy = nearBy.toRequestBody(),
//                                        address = address1.toRequestBody(),
//                                        addressLine2 = address2.toRequestBody(),
//                                        city = city.toRequestBody(),
//                                        state = state.toRequestBody(),
//                                        postalCode = postalCode.toRequestBody(),
//                                        country = contry.value.toRequestBody(),
//                                        availableFrom = FormatToNormalDate(fromDate).toRequestBody(),
//                                        availableTo = FormatToNormalDate(toDate).toRequestBody(),
//                                        status = available.value.toRequestBody(),
//                                        mainImage = mainImage!!,
//                                        additionalImages = additionalImages
//                                    )
//                                )
                                showPreviewDialog = false
                            }) {
                                Text("Confirm")
                            }
                        },
                        dismissButton = {
                            TextButton(onClick = { showPreviewDialog = false }) {
                                Text("Cancel")
                            }
                        }
                    )
                }


                OutlinedButton(
                    onClick = {

                        if (fromDate == "Select Date" || toDate == "Select Date") {
                            Toast.makeText(
                                context,
                                "Please select both From and To dates",
                                Toast.LENGTH_SHORT
                            ).show()
                            return@OutlinedButton
                        }

                        val formattedFromDate = FormatToNormalDate(fromDate)
                        val formattedToDate = FormatToNormalDate(toDate)

                        val mainImage =
                            imageUri.value?.let { UriToImage(context, it, "main_image") }
                        val additionalImages = additionalImageUris.value.mapNotNull { uri ->
                            UriToImage(context, uri, "additional_images")
                        }

                        if (mainImage == null) {
                            Toast.makeText(
                                context,
                                "Please select a main image",
                                Toast.LENGTH_SHORT
                            ).show()
                            return@OutlinedButton
                        } else if (additionalImages.isEmpty()) {
                            Toast.makeText(
                                context,
                                "Please select at least one additional image",
                                Toast.LENGTH_SHORT
                            ).show()
                            return@OutlinedButton
                        }
                        Log.d("AddRoom", "check: ${categoryMap[categorySelected]}")

                        if (categoryMap[categorySelected] == null) {
                            Toast.makeText(
                                context,
                                "Please select a valid category",
                                Toast.LENGTH_SHORT
                            ).show()
                            return@OutlinedButton
                        }
                        showPreviewDialog = true


//                        viewModel.addRoom(
////
//                            category = categoryMap[categorySelected].toString().toRequestBody(),
//                            roomName = roomName.toRequestBody(),
//                            description = description.toRequestBody(),
//                            pricePerNight = price.toRequestBody(),
//                            roomNo = roomNumber.toRequestBody(),
//                            nearBy = nearBy.toRequestBody(),
//                            address = address1.toRequestBody(),
//                            addressLine2 = address2.toRequestBody(),
//                            city = city.toRequestBody(),
//                            state = state.toRequestBody(),
//                            postalCode = postalCode.toRequestBody(),
//                            country = contry.value.toRequestBody(),
//                            availableFrom = FormatToNormalDate(fromDate).toRequestBody(),
//                            availableTo = FormatToNormalDate(toDate).toRequestBody(),
//                            status = available.value.toRequestBody(),
//                            mainImage = mainImage,
//                            additionalImages = additionalImages
//
//
//                        )
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 10.dp, bottom = 8.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Purple40,
                        contentColor = White
                    )
                ) {
                    Text("Upload")
                }
            }

        }


    }

}


fun extractCity(location: String): String {
    // You can use simple split logic or regex here
    return location.split(",").getOrNull(1)?.trim() ?: ""
}

fun extractState(location: String): String {
    return location.split(",").getOrNull(2)?.trim() ?: ""
}
