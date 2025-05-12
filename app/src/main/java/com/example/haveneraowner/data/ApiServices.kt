package com.example.haveneraowner.data

import com.example.haveneraowner.data.models.CreateOwnerRoomRequest
import com.example.haveneraowner.data.models.CreateOwnerRoomResponse
import com.example.haveneraowner.data.models.DeleteOwnerRoomResponse
import com.example.haveneraowner.data.models.OwnerRoomByIdResponse
import com.example.haveneraowner.data.models.OwnerRoomResponse
import com.example.haveneraowner.data.models.UpdateOwnerRoomRequest
import com.example.haveneraowner.data.models.UpdateOwnerRoomResponse
import com.example.haveneraowner.data.models.request.AddNewServiceRequest
import com.example.haveneraowner.data.models.request.ChangePasswordRequest
import com.example.haveneraowner.data.models.request.CreateOwnerServiceRequest
import com.example.haveneraowner.data.models.request.ForgetPassOtpVerifyRequest
import com.example.haveneraowner.data.models.request.ForgetPasswordRequest
import com.example.haveneraowner.data.models.request.LoginOtRequestModel
import com.example.haveneraowner.data.models.response.OwnerBookingsResponse
import com.example.haveneraowner.data.models.request.RefreshTokenRequest
import com.example.haveneraowner.data.models.request.SigninRequestModel
import com.example.haveneraowner.data.models.request.SingUpRequestModel
import com.example.haveneraowner.data.models.request.UpdateOwnerServiceRequest
import com.example.haveneraowner.data.models.request.UpdateProfileRequest
import com.example.haveneraowner.data.models.request.VerifyOtRequestModel
import com.example.haveneraowner.data.models.request.WithdrawRequest
import com.example.haveneraowner.data.models.response.AddNewServicesResponse
import com.example.haveneraowner.data.models.response.BookingTrends
import com.example.haveneraowner.data.models.response.CategoryResponse
import com.example.haveneraowner.data.models.response.ChangePasswordResponse
import com.example.haveneraowner.data.models.response.CreateOwnerServiceResponse
import com.example.haveneraowner.data.models.response.DashboardOverview
import com.example.haveneraowner.data.models.response.DeleteOwnerServiceResponse
import com.example.haveneraowner.data.models.response.FacilityResponse
import com.example.haveneraowner.data.models.response.ForgetPassOtpVerifyResponse
import com.example.haveneraowner.data.models.response.ForgetPasswordResponse
import com.example.haveneraowner.data.models.response.GetAllServices
import com.example.haveneraowner.data.models.response.GetProfileResponse
import com.example.haveneraowner.data.models.response.GetServicesById
import com.example.haveneraowner.data.models.response.LogoutResponse
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
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface ApiServices {

    @POST("auth/owner/signup/")
    suspend fun createUser(
        @Body singUpRequestModel: SingUpRequestModel
    ): Response<SignupResponseModel>

    @POST("auth/verify-otp/")
    suspend fun verifyOtp(
        @Body verifyOtRequestModel: VerifyOtRequestModel
    ): Response<SignupResponseModel>

    @POST("auth/signin/")
    suspend fun login(
        @Body signinRequestModel: SigninRequestModel
    ): Response<SigninResponseModel>

    @POST("auth/signin/")
    suspend fun loginOtpVerify(
        @Body loginOtRequestModel: LoginOtRequestModel
    ): Response<SigninResponseModel>

    @GET("auth/sign-out/")
    suspend fun logOut(): Response<LogoutResponse>

    @POST("auth/forget-password/")
    suspend fun forgetPassword(
        @Body forgetPasswordRequest: ForgetPasswordRequest
    ): Response<ForgetPasswordResponse>

    @POST("auth/forget-password/")
    suspend fun forgetPasswordOtpVerify(
        @Body forgetPassOtpVerifyRequest: ForgetPassOtpVerifyRequest
    ): Response<ForgetPassOtpVerifyResponse>

    @POST("auth/change-password/")
    suspend fun changePassword(
        @Body changePasswordRequest: ChangePasswordRequest
    ): Response<ChangePasswordResponse>

    @POST("auth/token/refresh/")
    suspend fun refreshToken(
        @Body refreshTokenRequest: RefreshTokenRequest
    ): Response<RefreshTokenResponse>



    @GET("services")
    suspend fun getAllServices(): Response<List<GetAllServices>>

    @GET("services/{service_id}")
    suspend fun getServiceById(
        @Path("service_id") serviceId: String
    ): Response<GetServicesById>

    @POST("services")
    suspend fun addNewService(
        @Body addNewServiceRequest: AddNewServiceRequest
    ): Response<AddNewServicesResponse>

//    @PUT("services/{service_id}")
//    suspend fun updateService(
//        @Path("service_id") serviceId: String,
//        @Body updateServiceRequest: UpdateServiceRequest
//    ): Response<UpdateServiceResponse>
//
//    @DELETE("services/{service_id}")
//    suspend fun deleteService(
//        @Path("service_id") serviceId: String
//    ): Response<DeleteServiceResponse>



//    @GET("categories")
//    suspend fun getAllCategories(): Response<GetAllCategoriesResponse>
//
//    @POST("categories")
//    suspend fun addNewCategory(@Body request: AddCategoryRequest): Response<AddNewCategoryResponse>
//
//    @PUT("categories/{category_id}")
//    suspend fun updateCategory(@Path("category_id") categoryId: String, @Body request: UpdateCategoryRequest): Response<UpdateCategoryResponse>
//
//    @DELETE("categories/{category_id}")
//    suspend fun deleteCategory(@Path("category_id") categoryId: String): Response<DeleteCategoryResponse>
//
//    @GET("categories/{category_id}")
//    suspend fun getCategoryById(@Path("category_id") categoryId: String): Response<GetAllCategoriesResponseItem>
//
//    @GET("api/rooms")
//    suspend fun getAllRooms(): Response<GetAllRoomsResponse>
//
//    @POST("api/rooms")
//    suspend fun addNewRoom(@Body request: AddNewRoomRequest): Response<AddNewRoomResponse>
//
//    @PUT("api/rooms/{id}")
//    suspend fun updateRoom(@Path("id") id: String, @Body request: UpdateRoomRequest): Response<UpdateRoomResponse>
//
//    @DELETE("api/rooms/{id}")
//    suspend fun deleteRoom(@Path("id") id: String): Response<DeleteRoomResponse>
//
//    @GET("api/rooms/{id}")
//    suspend fun getRoomById(@Path("id") id: String): Response<GetRoomByIdResponse>


    @GET("owner/bookings/")
    suspend fun getOwnerBookings(): Response<OwnerBookingsResponse>

    @GET("owner/dashboard/booking-trends/")
    suspend fun getBookingTrends(): Response<BookingTrends>

    @GET("owner/dashboard/overview/")
    suspend fun getDashboardOverview(): Response<DashboardOverview>

    @GET("owner/dashboard/recent-bookings/")
    suspend fun getRecentBookings(): Response<RecentBooking>

    @GET("owner/dashboard/revenue-trends/")
    suspend fun getRevenueTrends(): Response<BookingTrends>

    @GET("owner/reviews/")
    suspend fun getOwnerReviews(): Response<Review>

    @GET("owner/reviews/{review_id}//")
    suspend fun getOwnerReviewById(@Path("review_id") reviewId: Int): Response<Review>

    @DELETE("owner/reviews/{reviewId}/")
    suspend fun deleteReview(@Path("reviewId") reviewId: Int): Response<Unit>



    //Services Apis
    @GET("owner/services/")
    suspend fun getOwnerService(): Response<OwnerServiceResponse>

    @POST("owner/services/")
    suspend fun createOwnerService(@Body request: CreateOwnerServiceRequest): Response<CreateOwnerServiceResponse>

    @PUT("owner/services/")
    suspend fun updateOwnerService(
        @Path("serviceId") serviceId: Int,
        @Body request: UpdateOwnerServiceRequest
    ): Response<UpdateOwnerServiceResponse>

    @DELETE("owner/services/")
    suspend fun deleteOwnerService(@Path("serviceId") serviceId: Int): Response<DeleteOwnerServiceResponse>

    @GET("owner/services/{service_id}/")
    suspend fun getOwnerServiceById(@Path("serviceId") serviceId: Int): Response<OwnerServiceByIdResponse>

    //Rooms

    @GET("owner/rooms/")
    suspend fun getOwnerRooms(): Response<OwnerRoomResponse>

    @POST("owner/rooms/")
    suspend fun createOwnerRoom(@Body request: CreateOwnerRoomRequest): Response<CreateOwnerRoomResponse>

    @PUT("owner/room/{id}")
    suspend fun updateOwnerRoom(@Path("id") roomId: Int, @Body request: UpdateOwnerRoomRequest): Response<UpdateOwnerRoomResponse>

    @DELETE("owner/room/{id}")
    suspend fun deleteOwnerRoom(@Path("id") roomId: Int): Response<DeleteOwnerRoomResponse>

    @GET("owner/room/{id}")
    suspend fun getOwnerRoomById(@Path("id") roomId: Int): Response<OwnerRoomByIdResponse>

    //wallet

    @GET("owner/wallet/")
    suspend fun getWallet(): Response<WalletResponse>

    // ✅ Get Withdrawal History
    @GET("owner/withdrawal/history/")
    suspend fun getWithdrawalHistory(): Response<WithdrawalHistoryResponse>

    // ✅ Post Withdraw Amount
    @POST("owner/withdrawal/history/")
    suspend fun postWithdrawAmount(@Body request: WithdrawRequest): Response<WithdrawResponse>




    @GET("auth/owner/me/")
    suspend fun getProfile(): Response<GetProfileResponse>

    @POST("auth/owner/me/")
    suspend fun updateProfile(@Body updateProfileRequest: UpdateProfileRequest): Response<UpdateProfileResponse>

    @GET("categories/list/")
    suspend fun getCategoryList(): Response<CategoryResponse>

    @GET("facilities/list/")
    suspend fun getFacilityList(): Response<FacilityResponse>
}