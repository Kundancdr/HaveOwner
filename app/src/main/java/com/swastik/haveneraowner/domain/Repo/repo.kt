package com.swastik.haveneraowner.domain.Repo

import com.swastik.haveneraowner.common.Results
import com.swastik.haveneraowner.data.models.CreateOwnerRoomRequest
import com.swastik.haveneraowner.data.models.CreateOwnerRoomResponse
import com.swastik.haveneraowner.data.models.DeleteOwnerRoomResponse
import com.swastik.haveneraowner.data.models.OwnerRoomByIdResponse
import com.swastik.haveneraowner.data.models.OwnerRoomResponse
import com.swastik.haveneraowner.data.models.UpdateOwnerRoomRequest
import com.swastik.haveneraowner.data.models.UpdateOwnerRoomResponse
import com.swastik.haveneraowner.data.models.request.ChangePasswordRequest
import com.swastik.haveneraowner.data.models.request.CreateOwnerServiceRequest
import com.swastik.haveneraowner.data.models.request.ForgetPassOtpVerifyRequest
import com.swastik.haveneraowner.data.models.request.ForgetPasswordRequest
import com.swastik.haveneraowner.data.models.request.LoginOtRequestModel
import com.swastik.haveneraowner.data.models.request.RefreshTokenRequest
import com.swastik.haveneraowner.data.models.request.SigninRequestModel
import com.swastik.haveneraowner.data.models.request.SingUpRequestModel
import com.swastik.haveneraowner.data.models.request.UpdateOwnerServiceRequest
import com.swastik.haveneraowner.data.models.request.UpdateProfileRequest
import com.swastik.haveneraowner.data.models.request.VerifyOtRequestModel
import com.swastik.haveneraowner.data.models.request.WithdrawRequest
import com.swastik.haveneraowner.data.models.response.BookingTrends
import com.swastik.haveneraowner.data.models.response.CategoryResponse
import com.swastik.haveneraowner.data.models.response.ChangePasswordResponse
import com.swastik.haveneraowner.data.models.response.CreateOwnerServiceResponse
import com.swastik.haveneraowner.data.models.response.DashboardOverview
import com.swastik.haveneraowner.data.models.response.DeleteOwnerServiceResponse
import com.swastik.haveneraowner.data.models.response.FacilityResponse
import com.swastik.haveneraowner.data.models.response.ForgetPassOtpVerifyResponse
import com.swastik.haveneraowner.data.models.response.ForgetPasswordResponse
import com.swastik.haveneraowner.data.models.response.GetProfileResponse
import com.swastik.haveneraowner.data.models.response.LogoutResponse
import com.swastik.haveneraowner.data.models.response.OwnerBookingsResponse
import com.swastik.haveneraowner.data.models.response.OwnerServiceByIdResponse
import com.swastik.haveneraowner.data.models.response.OwnerServiceResponse
import com.swastik.haveneraowner.data.models.response.RecentBooking
import com.swastik.haveneraowner.data.models.response.RefreshTokenResponse
import com.swastik.haveneraowner.data.models.response.Review
import com.swastik.haveneraowner.data.models.response.SigninResponseModel
import com.swastik.haveneraowner.data.models.response.SignupResponseModel
import com.swastik.haveneraowner.data.models.response.UpdateOwnerServiceResponse
import com.swastik.haveneraowner.data.models.response.UpdateProfileResponse
import com.swastik.haveneraowner.data.models.response.WalletResponse
import com.swastik.haveneraowner.data.models.response.WithdrawResponse
import com.swastik.haveneraowner.data.models.response.WithdrawalHistoryResponse
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
    suspend fun getWithdrawalHistory(): Flow<Results<Response<List<WithdrawalHistoryResponse>>>>
    suspend fun postWithdrawAmount(request: WithdrawRequest): Flow<Results<Response<WithdrawResponse>>>

    suspend fun getProfile(): Flow<Results<Response<GetProfileResponse>>>
    suspend fun updateProfile(request: UpdateProfileRequest): Flow<Results<Response<UpdateProfileResponse>>>
    suspend fun getCategoryList(): Flow<Results<Response<List<CategoryResponse>>>>
    suspend fun getFacilityList(): Flow<Results<Response<List<FacilityResponse>>>>





}