package com.example.haveneraowner.domain.Repo

import com.example.haveneraowner.common.Results
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
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

interface Repo {

    suspend fun signup(singUpRequestModel: SingUpRequestModel): Flow<Results<Response<SignupResponseModel>>>
    suspend fun verifyOtp(verifyOtRequestModel: VerifyOtRequestModel): Flow<Results<Response<SignupResponseModel>>>
    suspend fun login(singinRequestModel: SigninRequestModel): Flow<Results<Response<SigninResponseModel>>>
    suspend fun loginOtpVerify(loginOtRequestModel: LoginOtRequestModel): Flow<Results<Response<SigninResponseModel>>>
    suspend fun logOut(): Flow<Results<Response<LogoutResponse>>>
    suspend fun forgetPassword(forgetPasswordRequest: ForgetPasswordRequest): Flow<Results<Response<ForgetPasswordResponse>>>
    suspend fun forgetPasswordOtpVerify(forgetPassOtpVerifyRequest: ForgetPassOtpVerifyRequest): Flow<Results<Response<ForgetPassOtpVerifyResponse>>>
    suspend fun changePassword(changePasswordRequest: ChangePasswordRequest): Flow<Results<Response<ChangePasswordResponse>>>
    suspend fun refreshToken(refreshTokenRequest: RefreshTokenRequest): Flow<Results<Response<RefreshTokenResponse>>>

    suspend fun getOwnerBookings(): Flow<Results<Response<OwnerBookingsResponse>>>
    suspend fun getDashboardOverview(): Flow<Results<Response<DashboardOverview>>>
    suspend fun getBookingTrends(): Flow<Results<Response<BookingTrends>>>
    suspend fun getRecentBookings(): Flow<Results<Response<RecentBooking>>>
    suspend fun getRevenueTrends(): Flow<Results<Response<BookingTrends>>>
    suspend fun getOwnerReviews(): Flow<Results<Response<Review>>>
    suspend fun getOwnerReviewById(reviewId: Int): Flow<Results<Response<Review>>>
    suspend fun deleteReview(reviewId: Int): Flow<Results<Response<Unit>>>


    suspend fun getOwnerService(): Flow<Results<Response<OwnerServiceResponse>>>
    suspend fun createOwnerService(request: CreateOwnerServiceRequest): Flow<Results<Response<CreateOwnerServiceResponse>>>
    suspend fun updateOwnerService(serviceId: Int, request: UpdateOwnerServiceRequest): Flow<Results<Response<UpdateOwnerServiceResponse>>>
    suspend fun deleteOwnerService(serviceId: Int): Flow<Results<Response<DeleteOwnerServiceResponse>>>
    suspend fun getOwnerServiceById(serviceId: Int): Flow<Results<Response<OwnerServiceByIdResponse>>>

    suspend fun getOwnerRooms(): Flow<Results<Response<OwnerRoomResponse>>>
    suspend fun createOwnerRoom(request: CreateOwnerRoomRequest): Flow<Results<Response<CreateOwnerRoomResponse>>>
    suspend fun updateOwnerRoom(roomId: Int, request: UpdateOwnerRoomRequest): Flow<Results<Response<UpdateOwnerRoomResponse>>>
    suspend fun deleteOwnerRoom(roomId: Int): Flow<Results<Response<DeleteOwnerRoomResponse>>>
    suspend fun getOwnerRoomById(roomId: Int): Flow<Results<Response<OwnerRoomByIdResponse>>>

    suspend fun getWallet(): Flow<Results<Response<WalletResponse>>>
    suspend fun getWithdrawalHistory(): Flow<Results<Response<WithdrawalHistoryResponse>>>
    suspend fun postWithdrawAmount(request: WithdrawRequest): Flow<Results<Response<WithdrawResponse>>>

    suspend fun getProfile(): Flow<Results<Response<GetProfileResponse>>>
    suspend fun updateProfile(request: UpdateProfileRequest): Flow<Results<Response<UpdateProfileResponse>>>
    suspend fun getCategoryList(): Flow<Results<Response<CategoryResponse>>>




}