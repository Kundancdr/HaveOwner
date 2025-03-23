package com.example.haveneraowner.data.repo

import android.util.Log
import com.example.haveneraowner.common.Results
import com.example.haveneraowner.data.ApiServices
import com.example.haveneraowner.data.TokenProvider
import com.example.haveneraowner.data.models.CreateOwnerRoomRequest
import com.example.haveneraowner.data.models.CreateOwnerRoomResponse
import com.example.haveneraowner.data.models.DeleteOwnerRoomResponse
import com.example.haveneraowner.data.models.OwnerRoomByIdResponse
import com.example.haveneraowner.data.models.OwnerRoomResponse
import com.example.haveneraowner.data.models.UpdateOwnerRoomRequest
import com.example.haveneraowner.data.models.UpdateOwnerRoomResponse
import com.example.haveneraowner.data.models.request.ChangePasswordRequest
import com.example.haveneraowner.data.models.request.CreateOwnerServiceRequest
import com.example.haveneraowner.data.models.request.ForgetPassOtpVerifyRequest
import com.example.haveneraowner.data.models.request.ForgetPasswordRequest
import com.example.haveneraowner.data.models.request.LoginOtRequestModel
import com.example.haveneraowner.data.models.request.RefreshTokenRequest
import com.example.haveneraowner.data.models.request.SigninRequestModel
import com.example.haveneraowner.data.models.request.SingUpRequestModel
import com.example.haveneraowner.data.models.request.UpdateOwnerServiceRequest
import com.example.haveneraowner.data.models.request.UpdateProfileRequest
import com.example.haveneraowner.data.models.request.VerifyOtRequestModel
import com.example.haveneraowner.data.models.request.WithdrawRequest
import com.example.haveneraowner.data.models.response.BookingTrends
import com.example.haveneraowner.data.models.response.CategoryResponse
import com.example.haveneraowner.data.models.response.ChangePasswordResponse
import com.example.haveneraowner.data.models.response.CreateOwnerServiceResponse
import com.example.haveneraowner.data.models.response.DashboardOverview
import com.example.haveneraowner.data.models.response.DeleteOwnerServiceResponse
import com.example.haveneraowner.data.models.response.ForgetPassOtpVerifyResponse
import com.example.haveneraowner.data.models.response.ForgetPasswordResponse
import com.example.haveneraowner.data.models.response.GetProfileResponse
import com.example.haveneraowner.data.models.response.LogoutResponse
import com.example.haveneraowner.data.models.response.OwnerBookingsResponse
import com.example.haveneraowner.data.models.response.OwnerServiceByIdResponse
import com.example.haveneraowner.data.models.response.OwnerServiceResponse
import com.example.haveneraowner.data.models.response.RecentBooking
import com.example.haveneraowner.data.models.response.RefreshTokenResponse
import com.example.haveneraowner.data.models.response.Review
import com.example.haveneraowner.data.models.response.ReviewResponse
import com.example.haveneraowner.data.models.response.SigninResponseModel
import com.example.haveneraowner.data.models.response.SignupResponseModel
import com.example.haveneraowner.data.models.response.UpdateOwnerServiceResponse
import com.example.haveneraowner.data.models.response.UpdateProfileResponse
import com.example.haveneraowner.data.models.response.WalletResponse
import com.example.haveneraowner.data.models.response.WithdrawResponse
import com.example.haveneraowner.data.models.response.WithdrawalHistoryResponse
import com.example.haveneraowner.domain.Repo.Repo
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException

class RepoImpl(
    private val apiServices: ApiServices,
    private val tokenProvider: TokenProvider
): Repo {
    override suspend fun signup(singUpRequestModel: SingUpRequestModel): Flow<Results<Response<SignupResponseModel>>> =
        callbackFlow {
            trySend(Results.Loading) // Emit loading state
            try {
                val response = apiServices.createUser(singUpRequestModel)
                if (response.isSuccessful) {

                    // Success case: Return the response
                    trySend(Results.Success(response))
                } else {
                    val errorMessage = response.errorBody()?.string() ?: "Unknown error"
                    val statusCode = response.code()
                    trySend(
                        Results.Error(
                            "Signup failed (Code: $statusCode): $errorMessage"
                        )
                    )
                }
            } catch (e: HttpException) {
                // Handle HTTP exceptions explicitly
                trySend(Results.Error("HTTP Exception: ${e.message} (Code: ${e.code()})"))
            } catch (e: IOException) {
                // Handle network-related exceptions
                trySend(Results.Error("Network Error: Please check your connection"))
            } catch (e: Exception) {
                // General exceptions
                trySend(Results.Error("Unexpected Error: ${e.localizedMessage}"))
            }
            awaitClose { close() }

        }

    override suspend fun verifyOtp(verifyOtRequestModel: VerifyOtRequestModel): Flow<Results<Response<SignupResponseModel>>> =
        callbackFlow {
            trySend(Results.Loading)
            try {
                val response = apiServices.verifyOtp(verifyOtRequestModel)
                if (response.isSuccessful) {
                    trySend(Results.Success(response))
                } else {
                    trySend(
                        Results.Error(
                            "Signup failed: ${
                                response.errorBody()?.string() ?: "Unknown error"
                            }"
                        )
                    )
                }
            } catch (e: Exception) {
                trySend(Results.Error("Exception occurred: ${e.message}"))
            }
            awaitClose { close() }
        }

    override suspend fun login(singinRequestModel: SigninRequestModel): Flow<Results<Response<SigninResponseModel>>> =
        callbackFlow {
            trySend(Results.Loading)
            try {
                val response = apiServices.login(singinRequestModel)
                if (response.isSuccessful) {

                    trySend(Results.Success(response))
                } else {
                    trySend(
                        Results.Error(
                            "Login failed: ${
                                response.errorBody()?.string() ?: "Unknown error"
                            }"
                        )
                    )
                }
            } catch (e: Exception) {
                trySend(Results.Error("Exception occurred: ${e.message}"))
            }
            awaitClose { close() }
        }

    override suspend fun loginOtpVerify(loginOtRequestModel: LoginOtRequestModel): Flow<Results<Response<SigninResponseModel>>> =
        callbackFlow {
            trySend(Results.Loading)
            try {
                val response = apiServices.loginOtpVerify(loginOtRequestModel)
                if (response.isSuccessful) {
                    Log.d("loginOtpVerify", "Response: ${response.body()}")
                    val accessToken = response.body()?.accessToken
                    val refreshToken = response.body()?.refreshToken
                    if (accessToken != null && refreshToken != null) {
                        tokenProvider.updateAccessToken(accessToken)
                        tokenProvider.updateRefreshToken(refreshToken)
                        Log.d("loginOtpVerify", "AccessToken updated successfully")
                        Log.d("loginOtpVerify", "AccessToken: $accessToken, RefreshToken: $refreshToken")
                    }
                    trySend(Results.Success(response))
                } else {
                    Log.e("loginOtpVerify", "Error Response: ${response.errorBody()?.string()}")
                    trySend(
                        Results.Error(
                            "Login failed: ${
                                response.errorBody()?.string() ?: "Unknown error"
                            }"
                        )
                    )
                }
            } catch (e: Exception) {
                trySend(Results.Error("Exception occurred: ${e.message}"))
            }
            awaitClose { close() }
        }

    override suspend fun refreshToken(refreshTokenRequest: RefreshTokenRequest): Flow<Results<Response<RefreshTokenResponse>>> =
        callbackFlow {
            trySend(Results.Loading)
            try {
                Log.d("AuthRepository", "refreshToken: Refresh token process started")
                // Get the refresh token from TokenProvider
                val refreshToken = tokenProvider.getRefreshToken()
                Log.d("AuthRepository", "Retrieved RefreshToken: $refreshToken")

                // If the refresh token is not available, we cannot proceed
                if (refreshToken.isNullOrEmpty()) {
                    Log.e("AuthRepository", "refreshToken: No refresh token available")
                    trySend(Results.Error("No refresh token available. Please log in again."))
                    close()
                    return@callbackFlow
                }

                // Create a new request with the refresh token in the body
                val updatedRequest = refreshTokenRequest.copy(accessToken = refreshToken)
                Log.d("AuthRepository", "refreshToken: Request sent -> $updatedRequest")

                // Call the refresh token API with the refresh token in the body and access token in the header
                val response = apiServices.refreshToken(updatedRequest)

                if (response.isSuccessful) {
                    val body = response.body()
                    Log.d("AuthRepository", "refreshToken: Successful response -> $body")

                    val newAccessToken = body?.accessToken
                    val newRefreshToken = body?.refreshToken
                    // Update tokens if available
                    if (!newAccessToken.isNullOrEmpty()) {
                        tokenProvider.updateAccessToken(newAccessToken)
                        Log.d("AuthRepository", "refreshToken: AccessToken updated -> $newAccessToken")
                    }
                    if (!newRefreshToken.isNullOrEmpty()) {
                        tokenProvider.updateRefreshToken(newRefreshToken)
                        Log.d("AuthRepository", "refreshToken: RefreshToken updated -> $newRefreshToken")
                    }

                    trySend(Results.Success(response))
                } else {
                    val errorMsg = response.errorBody()?.string() ?: "Unknown error"
                    Log.e("AuthRepository", "refreshToken: API error -> $errorMsg")
                    trySend(Results.Error("Failed to refresh token: $errorMsg"))
                }
            } catch (e: Exception) {
                Log.e("AuthRepository", "refreshToken: Exception occurred -> ${e.message}")
                trySend(Results.Error("Exception occurred: ${e.message}"))
            }
            awaitClose { close() }
        }


    override suspend fun logOut(): Flow<Results<Response<LogoutResponse>>> = callbackFlow {
        trySend(Results.Loading)
        try {
            val response = apiServices.logOut()
            if (response.isSuccessful) {
                trySend(Results.Success(response))
            } else {
                trySend(Results.Error("Logout failed: ${response.errorBody()?.string()}"))
            }

        } catch (exception: Exception) {
            trySend(Results.Error("Exception occurred: ${exception.message}"))
        }

        awaitClose { close() }
    }

    override suspend fun forgetPassword(forgetPasswordRequest: ForgetPasswordRequest): Flow<Results<Response<ForgetPasswordResponse>>> =
        callbackFlow {
            trySend(Results.Loading)
            try {
                val response = apiServices.forgetPassword(forgetPasswordRequest)
                if (response.isSuccessful) {
                    trySend(Results.Success(response))
                } else {
                    trySend(
                        Results.Error(
                            "Forget Password failed: ${
                                response.errorBody()?.string() ?: "Unknown error"
                            }"
                        )
                    )
                }
            } catch (e: Exception) {
                trySend(Results.Error("Exception occurred: ${e.message}"))
            }
            awaitClose { close() }
        }

    override suspend fun forgetPasswordOtpVerify(forgetPassOtpVerifyRequest: ForgetPassOtpVerifyRequest): Flow<Results<Response<ForgetPassOtpVerifyResponse>>> =
        callbackFlow {
            trySend(Results.Loading)
            try {
                val response = apiServices.forgetPasswordOtpVerify(forgetPassOtpVerifyRequest)
                if (response.isSuccessful) {
                    trySend(Results.Success(response))
                } else {
                    trySend(
                        Results.Error(
                            "OTP Verification failed: ${
                                response.errorBody()?.string() ?: "Unknown error"
                            }"
                        )
                    )
                }
            } catch (e: Exception) {
                trySend(Results.Error("Exception occurred: ${e.message}"))
            }
            awaitClose { close() }
        }

    override suspend fun changePassword(changePasswordRequest: ChangePasswordRequest): Flow<Results<Response<ChangePasswordResponse>>> =
        callbackFlow {
            trySend(Results.Loading)
            try {
                val response = apiServices.changePassword(changePasswordRequest)
                if (response.isSuccessful) {
                    trySend(Results.Success(response))
                } else {
                    trySend(
                        Results.Error(
                            "Change Password failed: ${
                                response.errorBody()?.string() ?: "Unknown error"
                            }"
                        )
                    )
                }
            } catch (e: Exception) {
                trySend(Results.Error("Exception occurred: ${e.message}"))
            }
            awaitClose { close() }
        }


    override suspend fun getOwnerBookings(): Flow<Results<Response<OwnerBookingsResponse>>> = callbackFlow {
        trySend(Results.Loading)
        try {
            val response = apiServices.getOwnerBookings()
            if (response.isSuccessful) {
                trySend(Results.Success(response))
            } else {
                trySend(
                    Results.Error(
                        "Failed to fetch bookings: ${response.errorBody()?.string() ?: "Unknown error"}"
                    )
                )
            }
        } catch (e: Exception) {
            trySend(Results.Error("Exception occurred: ${e.message}"))
        }
        awaitClose { close() }
    }

    override suspend fun getDashboardOverview(): Flow<Results<Response<DashboardOverview>>> = callbackFlow {
        trySend(Results.Loading)
        try {
            val response = apiServices.getDashboardOverview()
            if (response.isSuccessful) {
                trySend(Results.Success(response))
            } else {
                trySend(
                    Results.Error(
                        "Failed to fetch dashboard: ${response.errorBody()?.string() ?: "Unknown error"}"
                    )
                )
            }
        } catch (e: Exception) {
            trySend(Results.Error("Exception occurred: ${e.message}"))
        }
        awaitClose { close() }
    }


    override suspend fun getBookingTrends(): Flow<Results<Response<BookingTrends>>> = callbackFlow {
        trySend(Results.Loading)
        try {
            val response = apiServices.getBookingTrends()
            if (response.isSuccessful) {
                trySend(Results.Success(response))
            } else {
                trySend(
                    Results.Error(
                        "Failed to fetch booking trends: ${response.errorBody()?.string() ?: "Unknown error"}"
                    )
                )
            }
        } catch (e: Exception) {
            trySend(Results.Error("Exception occurred: ${e.message}"))
        }
        awaitClose { close() }
    }


    override suspend fun getRecentBookings(): Flow<Results<Response<RecentBooking>>> = callbackFlow {
        trySend(Results.Loading)
        try {
            val response = apiServices.getRecentBookings()
            if (response.isSuccessful) {
                trySend(Results.Success(response))
            } else {
                trySend(
                    Results.Error(
                        "Failed to fetch recent booking: ${response.errorBody()?.string() ?: "Unknown error"}"
                    )
                )
            }
        } catch (e: Exception) {
            trySend(Results.Error("Exception occurred: ${e.message}"))
        }
        awaitClose { close() }
    }

    override suspend fun getRevenueTrends(): Flow<Results<Response<BookingTrends>>> = callbackFlow {
        trySend(Results.Loading)
        try {
            val response = apiServices.getRevenueTrends()
            if (response.isSuccessful) {
                trySend(Results.Success(response))
            } else {
                trySend(
                    Results.Error(
                        "Failed to fetch revenue Trends: ${response.errorBody()?.string() ?: "Unknown error"}"
                    )
                )
            }
        } catch (e: Exception) {
            trySend(Results.Error("Exception occurred: ${e.message}"))
        }
        awaitClose { close() }
    }


    override suspend fun getOwnerReviews(): Flow<Results<Response<Review>>> = callbackFlow {
        trySend(Results.Loading)
        try {
            val response = apiServices.getOwnerReviews()
            if (response.isSuccessful) {
                trySend(Results.Success(response))
            } else {
                trySend(
                    Results.Error(
                        "Failed to get owner review: ${response.errorBody()?.string() ?: "Unknown error"}"
                    )
                )
            }
        } catch (e: Exception) {
            trySend(Results.Error("Exception occurred: ${e.message}"))
        }
        awaitClose { close() }
    }



    override suspend fun getOwnerReviewById(reviewId : Int): Flow<Results<Response<Review>>> = callbackFlow {
        trySend(Results.Loading)
        try {
            val response = apiServices.getOwnerReviewById(reviewId)
            if (response.isSuccessful) {
                trySend(Results.Success(response))
            } else {
                trySend(
                    Results.Error(
                        "Failed to get owner review : ${response.errorBody()?.string() ?: "Unknown error"}"
                    )
                )
            }
        } catch (e: Exception) {
            trySend(Results.Error("Exception occurred: ${e.message}"))
        }
        awaitClose { close() }
    }


    override suspend fun deleteReview(reviewId : Int): Flow<Results<Response<Unit>>> = callbackFlow {
        trySend(Results.Loading)
        try {
            val response = apiServices.deleteReview(reviewId)
            if (response.isSuccessful) {
                trySend(Results.Success(response))
            } else {
                trySend(
                    Results.Error(
                        "Failed to delete review : ${response.errorBody()?.string() ?: "Unknown error"}"
                    )
                )
            }
        } catch (e: Exception) {
            trySend(Results.Error("Exception occurred: ${e.message}"))
        }
        awaitClose { close() }
    }


    override suspend fun getOwnerService(): Flow<Results<Response<OwnerServiceResponse>>> = callbackFlow {
        trySend(Results.Loading)
        try {
            val response = apiServices.getOwnerService()
            if (response.isSuccessful) {
                trySend(Results.Success(response))
            } else {
                trySend(Results.Error("Failed: ${response.errorBody()?.string() ?: "Unknown error"}"))
            }
        } catch (e: Exception) {
            trySend(Results.Error("Exception: ${e.message}"))
        }
        awaitClose { close() }
    }

    override suspend fun createOwnerService(request: CreateOwnerServiceRequest): Flow<Results<Response<CreateOwnerServiceResponse>>> =
        callbackFlow {
            trySend(Results.Loading)
            try {
                val response = apiServices.createOwnerService(request)
                if (response.isSuccessful) {
                    trySend(Results.Success(response))
                } else {
                    trySend(Results.Error("Failed: ${response.errorBody()?.string() ?: "Unknown error"}"))
                }
            } catch (e: Exception) {
                trySend(Results.Error("Exception: ${e.message}"))
            }
            awaitClose { close() }
        }

    override suspend fun updateOwnerService(serviceId: Int, request: UpdateOwnerServiceRequest): Flow<Results<Response<UpdateOwnerServiceResponse>>> =
        callbackFlow {
            trySend(Results.Loading)
            try {
                val response = apiServices.updateOwnerService(serviceId, request)
                if (response.isSuccessful) {
                    trySend(Results.Success(response))
                } else {
                    trySend(Results.Error("Failed: ${response.errorBody()?.string() ?: "Unknown error"}"))
                }
            } catch (e: Exception) {
                trySend(Results.Error("Exception: ${e.message}"))
            }
            awaitClose { close() }
        }

    override suspend fun deleteOwnerService(serviceId: Int): Flow<Results<Response<DeleteOwnerServiceResponse>>> =
        callbackFlow {
            trySend(Results.Loading)
            try {
                val response = apiServices.deleteOwnerService(serviceId)
                if (response.isSuccessful) {
                    trySend(Results.Success(response))
                } else {
                    trySend(Results.Error("Failed: ${response.errorBody()?.string() ?: "Unknown error"}"))
                }
            } catch (e: Exception) {
                trySend(Results.Error("Exception: ${e.message}"))
            }
            awaitClose { close() }
        }

    override suspend fun getOwnerServiceById(serviceId: Int): Flow<Results<Response<OwnerServiceByIdResponse>>> =
        callbackFlow {
            trySend(Results.Loading)
            try {
                val response = apiServices.getOwnerServiceById(serviceId)
                if (response.isSuccessful) {
                    trySend(Results.Success(response))
                } else {
                    trySend(Results.Error("Failed: ${response.errorBody()?.string() ?: "Unknown error"}"))
                }
            } catch (e: Exception) {
                trySend(Results.Error("Exception: ${e.message}"))
            }
            awaitClose { close() }
        }

    override suspend fun getOwnerRooms(): Flow<Results<Response<OwnerRoomResponse>>> =
        callbackFlow {
            trySend(Results.Loading)
            try {
                val response = apiServices.getOwnerRooms()
                if (response.isSuccessful) {
                    trySend(Results.Success(response))
                } else {
                    trySend(Results.Error("Error: ${response.errorBody()?.string()}"))
                }
            } catch (e: Exception) {
                trySend(Results.Error("Exception: ${e.message}"))
            }
            awaitClose { close() }
        }

    override suspend fun createOwnerRoom(request: CreateOwnerRoomRequest): Flow<Results<Response<CreateOwnerRoomResponse>>> =
        callbackFlow {
            trySend(Results.Loading)
            try {
                val response = apiServices.createOwnerRoom(request)
                if (response.isSuccessful) {
                    trySend(Results.Success(response))
                } else {
                    trySend(Results.Error("Error: ${response.errorBody()?.string()}"))
                }
            } catch (e: Exception) {
                trySend(Results.Error("Exception: ${e.message}"))
            }
            awaitClose { close() }
        }

    override suspend fun updateOwnerRoom(roomId: Int, request: UpdateOwnerRoomRequest): Flow<Results<Response<UpdateOwnerRoomResponse>>> =
        callbackFlow {
            trySend(Results.Loading)
            try {
                val response = apiServices.updateOwnerRoom(roomId, request)
                if (response.isSuccessful) {
                    trySend(Results.Success(response))
                } else {
                    trySend(Results.Error("Error: ${response.errorBody()?.string()}"))
                }
            } catch (e: Exception) {
                trySend(Results.Error("Exception: ${e.message}"))
            }
            awaitClose { close() }
        }

    override suspend fun deleteOwnerRoom(roomId: Int): Flow<Results<Response<DeleteOwnerRoomResponse>>> =
        callbackFlow {
            trySend(Results.Loading)
            try {
                val response = apiServices.deleteOwnerRoom(roomId)
                if (response.isSuccessful) {
                    trySend(Results.Success(response))
                } else {
                    trySend(Results.Error("Error: ${response.errorBody()?.string()}"))
                }
            } catch (e: Exception) {
                trySend(Results.Error("Exception: ${e.message}"))
            }
            awaitClose { close() }
        }

    override suspend fun getOwnerRoomById(roomId: Int): Flow<Results<Response<OwnerRoomByIdResponse>>> =
        callbackFlow {
            trySend(Results.Loading)
            try {
                val response = apiServices.getOwnerRoomById(roomId)
                if (response.isSuccessful) {
                    trySend(Results.Success(response))
                } else {
                    trySend(Results.Error("Error: ${response.errorBody()?.string()}"))
                }
            } catch (e: Exception) {
                trySend(Results.Error("Exception: ${e.message}"))
            }
            awaitClose { close() }
        }

    override suspend fun getWallet(): Flow<Results<Response<WalletResponse>>> = flow {
        emit(Results.Loading)
        try {
            val response = apiServices.getWallet()
            if (response.isSuccessful) {
                emit(Results.Success(response))
            } else {
                emit(Results.Error(response.message()))
            }
        } catch (e: Exception) {
            emit(Results.Error(e.localizedMessage ?: "Unexpected Error"))
        }
    }

    override suspend fun getWithdrawalHistory(): Flow<Results<Response<WithdrawalHistoryResponse>>> = flow {
        emit(Results.Loading)
        try {
            val response = apiServices.getWithdrawalHistory()
            if (response.isSuccessful) {
                emit(Results.Success(response))
            } else {
                emit(Results.Error(response.message()))
            }
        } catch (e: Exception) {
            emit(Results.Error(e.localizedMessage ?: "Unexpected Error"))
        }
    }

    override suspend fun postWithdrawAmount(request: WithdrawRequest): Flow<Results<Response<WithdrawResponse>>> = flow {
        emit(Results.Loading)
        try {
            val response = apiServices.postWithdrawAmount(request)
            if (response.isSuccessful) {
                emit(Results.Success(response))
            } else {
                emit(Results.Error(response.message()))
            }
        } catch (e: Exception) {
            emit(Results.Error(e.localizedMessage ?: "Unexpected Error"))
        }
    }

    override suspend fun getProfile(): Flow<Results<Response<GetProfileResponse>>> = callbackFlow {
        trySend(Results.Loading)
        try {
            val response = apiServices.getProfile()
            if (response.isSuccessful) {
                trySend(Results.Success(response))
            } else {
                trySend(Results.Error("Failed to fetch profile: ${response.errorBody()?.string() ?: "Unknown error"}"))
            }
        } catch (e: Exception) {
            trySend(Results.Error("Exception: ${e.message}"))
        }
        awaitClose { close() }
    }

    override suspend fun updateProfile(request: UpdateProfileRequest): Flow<Results<Response<UpdateProfileResponse>>> = callbackFlow {
        trySend(Results.Loading)
        try {
            val response = apiServices.updateProfile(request)
            if (response.isSuccessful) {
                trySend(Results.Success(response))
            } else {
                trySend(Results.Error("Failed to update profile: ${response.errorBody()?.string() ?: "Unknown error"}"))
            }
        } catch (e: Exception) {
            trySend(Results.Error("Exception: ${e.message}"))
        }
        awaitClose { close() }
    }


    override suspend fun getCategoryList(): Flow<Results<Response<CategoryResponse>>> = flow {
        emit(Results.Loading)
        try {
            val response = apiServices.getCategoryList()
            if (response.isSuccessful) {
                emit(Results.Success(response))
            } else {
                emit(Results.Error(response.message()))
            }
        } catch (e: Exception) {
            emit(Results.Error(e.localizedMessage ?: "Unexpected Error"))
        }
    }

}