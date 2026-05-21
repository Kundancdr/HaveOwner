package com.example.haveneraowner.utils

import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.CloudUpload
import androidx.compose.material.ripple
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.example.haveneraowner.ui.theme.GraySurface
import com.example.haveneraowner.ui.theme.Purple40
import com.example.haveneraowner.ui.theme.TextPrimary
import com.example.haveneraowner.ui.theme.TextSecondary
import com.example.haveneraowner.ui.theme.White


//@Composable
//fun UploadMainAndAdditionalImagesAddRoom(
//    description: String,
//    title: String,
//    onUploadClick: () -> Unit,
//    imageUri: MutableState<Uri?>? = null, // for main image
//    imageUris: MutableState<List<Uri>>? = null
//    ) {
//    Card(
//        modifier = Modifier
//            .clip(RoundedCornerShape(16.dp))
//            .height(300.dp)
//            .shadow(elevation = 4.dp, shape = RoundedCornerShape(16.dp))
//            .clickable(
//                interactionSource = remember { MutableInteractionSource() },
//                indication = ripple(
//                    color = Purple40,
//                    bounded = true
//                )
//            ) {
//                onUploadClick.invoke()
//            },
//        colors = CardDefaults.cardColors(containerColor = GraySurface)
//    ) {
//        Box(
//            modifier = Modifier
//                .fillMaxWidth()
//                .height(300.dp)
//                .clip(RoundedCornerShape(16.dp))
//                .clickable { onUploadClick() }
//                .background(GraySurface)
//        ) {
//            when {
//                imageUri != null && imageUri.value != null -> {
//                    // Show single main image
//                    AsyncImage(
//                        model = imageUri.value,
//                        contentDescription = null,
//                        modifier = Modifier.fillMaxSize(),
//                        contentScale = ContentScale.Crop
//                    )
//                    IconButton(
//                        onClick = { imageUri.value = null },
//                        modifier = Modifier
//                            .align(Alignment.TopEnd)
//                            .padding(8.dp)
//                            .background(White.copy(alpha = 0.8f), RoundedCornerShape(50))
//                            .size(28.dp)
//                    ) {
//                        Icon(
//                            imageVector = Icons.Default.Close,
//                            contentDescription = "Remove",
//                            tint = Color.Red,
//                            modifier = Modifier.size(18.dp)
//                        )
//                    }
//                }
//
//                imageUris != null && imageUris.value.isNotEmpty() -> {
//                    // Show additional images
//                    LazyRow(
//                        modifier = Modifier.fillMaxSize().padding(8.dp),
//                        horizontalArrangement = Arrangement.spacedBy(8.dp)
//                    ) {
//                        items(imageUris.value.size) { index ->
//                            Box {
//                                AsyncImage(
//                                    model = imageUris.value[index],
//                                    contentDescription = null,
//                                    modifier = Modifier
//                                        .size(100.dp)
//                                        .clip(RoundedCornerShape(12.dp)),
//                                    contentScale = ContentScale.Crop
//                                )
//                            }
//                        }
//                    }
//                }
//
//                else -> {
//                    // Upload UI
//                    Column(
//                        modifier = Modifier
//                            .fillMaxSize()
//                            .padding(vertical = 24.dp, horizontal = 12.dp),
//                        verticalArrangement = Arrangement.Center,
//                        horizontalAlignment = Alignment.CenterHorizontally
//                    ) {
//                        Icon(
//                            imageVector = Icons.Default.CloudUpload,
//                            contentDescription = null,
//                            modifier = Modifier.size(72.dp),
//                            tint = Purple40
//                        )
//                        Spacer(Modifier.height(12.dp))
//                        Text(title, fontSize = 16.sp, fontWeight = FontWeight.SemiBold, color = TextPrimary)
//                        Spacer(Modifier.height(12.dp))
//                        Text(description, fontSize = 13.sp, color = TextSecondary, textAlign = TextAlign.Center)
//                        Spacer(Modifier.height(12.dp))
//                        Button(
//                            onClick = { onUploadClick() },
//                            colors = ButtonDefaults.buttonColors(containerColor = Purple40),
//                            shape = RoundedCornerShape(12.dp),
//                            modifier = Modifier.height(42.dp).width(140.dp)
//                        ) {
//                            Text("Upload", color = White, fontSize = 14.sp)
//                        }
//                    }
//                }
//            }
//        }
//
//
//    }
//}


@Composable
fun UploadMainAndAdditionalImagesAddRoom(
    description: String,
    title: String,
    onUploadClick: () -> Unit,
    imageUri: MutableState<Uri?>? = null, // For main image
    imageUris: MutableState<List<Uri>>? = null // For additional images
) {
    Card(
        modifier = Modifier
            .clip(RoundedCornerShape(16.dp))
            .height(300.dp)
            .shadow(elevation = 4.dp, shape = RoundedCornerShape(16.dp))
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = ripple(color = Purple40, bounded = true)
            ) { onUploadClick.invoke() },
        colors = CardDefaults.cardColors(containerColor = GraySurface)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp)
                .clip(RoundedCornerShape(16.dp))
                .background(GraySurface)
        ) {
            when {
                imageUri != null && imageUri.value != null -> {
                    // Main Image Preview
                    AsyncImage(
                        model = imageUri.value,
                        contentDescription = null,
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Crop
                    )
                    IconButton(
                        onClick = { imageUri.value = null },
                        modifier = Modifier
                            .align(Alignment.TopEnd)
                            .padding(8.dp)
                            .background(White.copy(alpha = 0.8f), RoundedCornerShape(50))
                            .size(28.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = "Remove",
                            tint = Color.Red,
                            modifier = Modifier.size(18.dp)
                        )
                    }
                }

                imageUris != null && imageUris.value.isNotEmpty() -> {
                    // Additional Images Preview
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(8.dp),
//                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                        verticalArrangement = Arrangement.spacedBy(8.dp),
                        horizontalAlignment = Alignment.CenterHorizontally

                    ) {
                        items(imageUris.value.size) { index ->
                            Box(
                                modifier = Modifier
                                    .size(140.dp)
                                    .clip(RoundedCornerShape(12.dp))
                            ) {
                                AsyncImage(
                                    model = imageUris.value[index],
                                    contentDescription = null,
                                    contentScale = ContentScale.Crop,
                                    modifier = Modifier.fillMaxSize()
                                )
                                IconButton(
                                    onClick = {
                                        val updatedList = imageUris.value.toMutableList()
                                        updatedList.removeAt(index)
                                        imageUris.value = updatedList
                                    },
                                    modifier = Modifier
                                        .align(Alignment.TopEnd)
                                        .padding(4.dp)
                                        .background(White.copy(alpha = 0.8f), RoundedCornerShape(50))
                                        .size(24.dp)
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Close,
                                        contentDescription = "Remove Image",
                                        tint = Color.Red,
                                        modifier = Modifier.size(16.dp)
                                    )
                                }
                            }
                        }
                    }
                }

                else -> {
                    // Upload Placeholder
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(vertical = 24.dp, horizontal = 12.dp),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Icon(
                            imageVector = Icons.Default.CloudUpload,
                            contentDescription = null,
                            modifier = Modifier.size(72.dp),
                            tint = Purple40
                        )
                        Spacer(Modifier.height(12.dp))
                        Text(title, fontSize = 16.sp, fontWeight = FontWeight.SemiBold, color = TextPrimary)
                        Spacer(Modifier.height(12.dp))
                        Text(description, fontSize = 13.sp, color = TextSecondary, textAlign = TextAlign.Center)
                        Spacer(Modifier.height(12.dp))
                        Button(
                            onClick = { onUploadClick() },
                            colors = ButtonDefaults.buttonColors(containerColor = Purple40),
                            shape = RoundedCornerShape(12.dp),
                            modifier = Modifier
                                .height(42.dp)
                                .width(140.dp)
                        ) {
                            Text("Upload", color = White, fontSize = 14.sp)
                        }
                    }
                }
            }
        }
    }
}
