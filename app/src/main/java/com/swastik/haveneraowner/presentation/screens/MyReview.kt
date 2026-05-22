package com.swastik.haveneraowner.presentation.screens

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.swastik.haveneraowner.presentation.viewModels.AuthViewModel
import org.koin.androidx.compose.koinViewModel


@Composable
fun ReviewScreen(
    viewModel: AuthViewModel = koinViewModel()

) {
    val reviewsState by viewModel.ownerReviewState.collectAsState()
    val reviews = reviewsState.success?.body()


    val dummyReviews = listOf(
        Review(1, Room(101, "Deluxe Room"), 4, "Great experience! Very clean and spacious.", "2025-02-15T14:30:00"),
        Review(2, Room(102, "Luxury Suite"), 5, "Absolutely amazing! Highly recommended.", "2025-02-16T09:45:00"),
        Review(3, Room(103, "Standard Room"), 2, "Not worth the price. Poor service.", "2025-02-14T18:20:00"),
        Review(4, Room(104, "Executive Suite"), 3, "Decent stay but could be better.", "2025-02-13T08:30:00"),
    )

    Log.d("revires", "MyReviewScreen: $reviews ")
    LaunchedEffect(Unit) {
        viewModel.getOwnerReviews()
    }

    Spacer(Modifier.height(25.dp))
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("My Reviews", fontWeight = FontWeight.Bold) },
                backgroundColor = Color.White,
                elevation = 4.dp
            )
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .background(Color(0xFFF8F9FA))
        ) {
            items(dummyReviews, key = { it.id }) { review ->
                ReviewItem(review, onDelete = { /* Handle Delete Action */ })
            }
        }
    }
}

@Composable
fun ReviewItem(review: Review, onDelete: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)
            .clip(RoundedCornerShape(12.dp)),
        elevation = 4.dp,
        backgroundColor = Color.White
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            // Review ID & Room Name
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text("Review ID: ${review.id}", fontSize = 14.sp, fontWeight = FontWeight.SemiBold, color = Color.Gray)
                Text(
                    text = review.room.room_name,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Rating & Date
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                RatingBadge(rating = review.rating)
               // Text("Created At: ${formatDate(review.createdAt)}", fontSize = 12.sp, color = Color.Gray)
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Comment
            Text(review.comment, fontSize = 14.sp, color = Color.Black)

            Spacer(modifier = Modifier.height(12.dp))

            // Delete Button
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                IconButton(onClick = onDelete) {
                    Icon(Icons.Default.Delete, contentDescription = "Delete Review", tint = Color.Red)
                }
            }
        }
    }
}

@Composable
fun RatingBadge(rating: Int) {
    val ratingColor = when {
        rating >= 4 -> Color(0xFF4CAF50) // Green for high rating
        rating == 3 -> Color(0xFFFFA726) // Orange for average rating
        else -> Color(0xFFD32F2F) // Red for low rating
    }

    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(8.dp))
            .background(ratingColor.copy(alpha = 0.2f))
            .padding(horizontal = 12.dp, vertical = 6.dp),
    ) {
        Text(text = "⭐ $rating", color = ratingColor, fontWeight = FontWeight.Medium, fontSize = 14.sp)
    }
}

// Helper function to format dates
//fun formatDate(date: String): String {
//    return try {
//        val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault())
//        val outputFormat = SimpleDateFormat("dd MMM yyyy, hh:mm a", Locale.getDefault())
//        outputFormat.format(inputFormat.parse(date)!!)
//    } catch (e: Exception) {
//        date
//    }
//}

// Dummy Data Model
data class Review(
    val id: Int,
    val room: Room,
    val rating: Int,
    val comment: String,
    val createdAt: String
)

data class Room(
    val id: Int,
    val room_name: String
)




//@Composable
//fun MyReviewScreen(viewModel: AuthViewModel = koinViewModel()) {
//    val reviews = listOf(
//        Review(1, Room(101, "Deluxe Room"), 4, "Very comfortable and clean!", "2025-02-15T14:30:00"),
//        Review(2, Room(202, "Suite Room"), 5, "Amazing service and hospitality.", "2025-02-10T10:15:00"),
//        Review(3, Room(303, "Standard Room"), 3, "Decent, but could be better.", "2025-01-05T19:45:00"),
//        Review(4, Room(404, "Economy Room"), 2, "Not satisfied with the cleanliness.", "2025-01-01T08:30:00"),
//    )
//
//    Scaffold(
//        topBar = {
//            TopAppBar(
//                title = { Text("My Reviews", fontWeight = FontWeight.Bold) },
//                backgroundColor = Color.White,
//                elevation = 4.dp
//            )
//        }
//    ) { padding ->
//        LazyColumn(
//            modifier = Modifier
//                .fillMaxSize()
//                .padding(padding)
//                .background(Color(0xFFF8F9FA))
//        ) {
//            items(reviews, key = { it.id }) { review ->
//                SwipeToDeleteItem(
//                    item = review,
//                    onDelete = { viewModel.deleteReview(review.id) }
//                ) {
//                    ReviewItem(review)
//                }
//            }
//        }
//    }
//}
//
//@Composable
//fun ReviewItem(review: Review) {
//    Card(
//        modifier = Modifier
//            .fillMaxWidth()
//            .padding(10.dp)
//            .clip(RoundedCornerShape(12.dp)),
//        elevation = 4.dp,
//        backgroundColor = Color.White
//    ) {
//        Column(
//            modifier = Modifier
//                .fillMaxWidth()
//                .padding(16.dp)
//        ) {
//            // Review ID & Room Name
//            Row(
//                modifier = Modifier.fillMaxWidth(),
//                horizontalArrangement = Arrangement.SpaceBetween
//            ) {
//                Text("Review ID: ${review.id}", fontSize = 14.sp, fontWeight = FontWeight.SemiBold, color = Color.Gray)
//                Text(
//                    text = review.room.room_name,
//                    fontSize = 16.sp,
//                    fontWeight = FontWeight.Bold,
//                    color = Color.Black
//                )
//            }
//
//            Spacer(modifier = Modifier.height(8.dp))
//
//            // Rating & Date
//            Row(
//                modifier = Modifier.fillMaxWidth(),
//                horizontalArrangement = Arrangement.SpaceBetween,
//                verticalAlignment = Alignment.CenterVertically
//            ) {
//                RatingBadge(rating = review.rating)
//
//                Column(horizontalAlignment = Alignment.End) {
//                    Text("Created At: ${formatDate(review.created_at)}", fontSize = 12.sp, color = Color.Gray)
//                }
//            }
//
//            Spacer(modifier = Modifier.height(8.dp))
//
//            // Review Comment
//            Text(
//                text = review.comment,
//                fontSize = 14.sp,
//                fontWeight = FontWeight.Normal,
//                color = Color.Black
//            )
//
//            Spacer(modifier = Modifier.height(10.dp))
//
//            // Delete Action Button
//            Row(
//                modifier = Modifier.fillMaxWidth(),
//                horizontalArrangement = Arrangement.End
//            ) {
//                TextButton(onClick = { /* Delete Action */ }) {
//                    Icon(imageVector = Icons.Default.Delete, contentDescription = "Delete", tint = Color.Red)
//                    Text("Delete", color = Color.Red, fontWeight = FontWeight.Bold)
//                }
//            }
//        }
//    }
//}
//
//@Composable
//fun RatingBadge(rating: Int) {
//    val ratingColor = when {
//        rating >= 4 -> Color(0xFF4CAF50) // Green
//        rating == 3 -> Color(0xFFFFA726) // Orange
//        else -> Color(0xFFD32F2F) // Red
//    }
//
//    Box(
//        modifier = Modifier
//            .clip(RoundedCornerShape(8.dp))
//            .background(ratingColor.copy(alpha = 0.2f))
//            .padding(horizontal = 12.dp, vertical = 6.dp),
//    ) {
//        Text(text = "⭐ $rating", color = ratingColor, fontWeight = FontWeight.Medium, fontSize = 14.sp)
//    }
//}
//
//// Helper function to format dates
//fun formatDate(date: String): String {
//    return try {
//        val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault())
//        val outputFormat = SimpleDateFormat("dd MMM yyyy, hh:mm a", Locale.getDefault())
//        outputFormat.format(inputFormat.parse(date)!!)
//    } catch (e: Exception) {
//        date
//    }
//}
//
//// Dummy Data Model
//data class Review(
//    val id: Int,
//    val room: Room,
//    val rating: Int,
//    val comment: String,
//    val created_at: String
//)
//
//data class Room(
//    val id: Int,
//    val room_name: String
//)
