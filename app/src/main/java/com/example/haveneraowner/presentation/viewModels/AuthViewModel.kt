package com.example.haveneraowner.presentation.viewModels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.haveneraowner.common.Results
import com.example.haveneraowner.data.DataStoreManager
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
import com.example.haveneraowner.data.models.response.FacilityResponse
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
import com.example.haveneraowner.data.models.response.SigninResponseModel
import com.example.haveneraowner.data.models.response.SignupResponseModel
import com.example.haveneraowner.data.models.response.UpdateOwnerServiceResponse
import com.example.haveneraowner.data.models.response.UpdateProfileResponse
import com.example.haveneraowner.data.models.response.WalletResponse
import com.example.haveneraowner.data.models.response.WithdrawResponse
import com.example.haveneraowner.data.models.response.WithdrawalHistoryResponse
import com.example.haveneraowner.domain.useCase.ChangePasswordUseCase
import com.example.haveneraowner.domain.useCase.CreateOwnerRoomUseCase
import com.example.haveneraowner.domain.useCase.CreateOwnerServiceUseCase
import com.example.haveneraowner.domain.useCase.DeleteOwnerRoomUseCase
import com.example.haveneraowner.domain.useCase.DeleteOwnerServiceUseCase
import com.example.haveneraowner.domain.useCase.ForgetPasswordOtpVerifyUseCase
import com.example.haveneraowner.domain.useCase.ForgetPasswordUseCase
import com.example.haveneraowner.domain.useCase.GetCategoryListUseCase
import com.example.haveneraowner.domain.useCase.GetFacilityListUseCase
import com.example.haveneraowner.domain.useCase.GetOwnerBookingsUseCase
import com.example.haveneraowner.domain.useCase.GetOwnerRoomByIdUseCase
import com.example.haveneraowner.domain.useCase.GetOwnerRoomsUseCase
import com.example.haveneraowner.domain.useCase.GetOwnerServiceByIdUseCase
import com.example.haveneraowner.domain.useCase.GetOwnerServiceUseCase
import com.example.haveneraowner.domain.useCase.GetProfileUseCase
import com.example.haveneraowner.domain.useCase.GetWalletUseCase
import com.example.haveneraowner.domain.useCase.GetWithdrawalHistoryUseCase
import com.example.haveneraowner.domain.useCase.LogOutUseCase
import com.example.haveneraowner.domain.useCase.LoginOtpVerifyUseCase
import com.example.haveneraowner.domain.useCase.PostWithdrawAmountUseCase
import com.example.haveneraowner.domain.useCase.RefreshTokenUseCase
import com.example.haveneraowner.domain.useCase.SigninUseCase
import com.example.haveneraowner.domain.useCase.SignupUseCase
import com.example.haveneraowner.domain.useCase.UpdateOwnerRoomUseCase
import com.example.haveneraowner.domain.useCase.UpdateOwnerServiceUseCase
import com.example.haveneraowner.domain.useCase.UpdateProfileUseCase
import com.example.haveneraowner.domain.useCase.VerifyOtpUseCase
import com.example.haveneraowner.domain.useCase.deleteReviewUseCase
import com.example.haveneraowner.domain.useCase.getBookingTrendsUseCase
import com.example.haveneraowner.domain.useCase.getDashboardOverviewUseCase
import com.example.haveneraowner.domain.useCase.getOwnerReviewByIdUseCase
import com.example.haveneraowner.domain.useCase.getOwnerReviewUseCase
import com.example.haveneraowner.domain.useCase.getRecentBookingsUseCase
import com.example.haveneraowner.domain.useCase.getRevenueTrendsUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import retrofit2.Response

class AuthViewModel(
    private val dataStoreManager: DataStoreManager,
    private val signupUseCase: SignupUseCase,
    private val verifyOtpUseCase: VerifyOtpUseCase,
    private val signinUseCase: SigninUseCase,
    private val loginOtpVerifyUseCase: LoginOtpVerifyUseCase,
    private val logOutUseCase: LogOutUseCase,
    private val forgetPasswordUseCase: ForgetPasswordUseCase,
    private val forgetPasswordOtpVerifyUseCase: ForgetPasswordOtpVerifyUseCase,
    private val changePasswordUseCase: ChangePasswordUseCase,
    private val refreshTokenUseCase: RefreshTokenUseCase,
    private val getOwnerBookingsUseCase: GetOwnerBookingsUseCase,
    private val getDashboardOverviewUseCase: getDashboardOverviewUseCase,
    private val getBookingTrendsUseCase: getBookingTrendsUseCase,
    private val getRecentBookingsUseCase: getRecentBookingsUseCase,
    private val getRevenueTrendsUseCase: getRevenueTrendsUseCase,
    private val getOwnerReviewUseCase: getOwnerReviewUseCase,
    private val getOwnerReviewByIdUseCase: getOwnerReviewByIdUseCase,
    private val deleteReviewUseCase: deleteReviewUseCase,
    private val getOwnerServiceUseCase: GetOwnerServiceUseCase,
    private val createOwnerServiceUseCase: CreateOwnerServiceUseCase,
    private val updateOwnerServiceUseCase: UpdateOwnerServiceUseCase,
    private val deleteOwnerServiceUseCase: DeleteOwnerServiceUseCase,
    private val getOwnerServiceByIdUseCase: GetOwnerServiceByIdUseCase,
    private val getOwnerRoomsUseCase: GetOwnerRoomsUseCase,
    private val createOwnerRoomUseCase: CreateOwnerRoomUseCase,
    private val updateOwnerRoomUseCase: UpdateOwnerRoomUseCase,
    private val deleteOwnerRoomUseCase: DeleteOwnerRoomUseCase,
    private val getOwnerRoomByIdUseCase: GetOwnerRoomByIdUseCase,
    private val getWalletUseCase: GetWalletUseCase,
    private val getWithdrawalHistoryUseCase: GetWithdrawalHistoryUseCase,
    private val postWithdrawAmountUseCase: PostWithdrawAmountUseCase,
    private val getProfileUseCase: GetProfileUseCase,
    private val updateProfileUseCase: UpdateProfileUseCase,
    private val getCategoryListUseCase: GetCategoryListUseCase,
    private val getFacilityListUseCase: GetFacilityListUseCase


) : ViewModel() {

    val isLoggedIn: Flow<Boolean> = dataStoreManager.isLoggedInFlow

    private val _refreshTokenState = MutableStateFlow(RefreshTokenState())
    val refreshTokenState = _refreshTokenState.asStateFlow()

    private val _forgetPasswordState = MutableStateFlow(ForgetPasswordState())
    val forgetPasswordState = _forgetPasswordState.asStateFlow()

    private val _otpVerificationState = MutableStateFlow(OtpVerificationState())
    val otpVerificationState = _otpVerificationState.asStateFlow()

    private val _changePasswordState = MutableStateFlow(ChangePasswordState())
    val changePasswordState = _changePasswordState.asStateFlow()

    private val _logOutState = MutableStateFlow(LogoutState())
    val logOutState = _logOutState.asStateFlow()

    private val _signInState = MutableStateFlow(SignInState())
    val signInState = _signInState.asStateFlow()

    private val _otpVerifyState = MutableStateFlow(OtpVerifyState())
    val otpVerifyState = _otpVerifyState.asStateFlow()

    private val _loginOtpVerifyState = MutableStateFlow(LoginOtpVerifyState())
    val loginOtpVerifyState = _loginOtpVerifyState.asStateFlow()

    private val _signUpState = MutableStateFlow(SignUpState())
    val signUpState = _signUpState.asStateFlow()

    private val _ownerBookingsState = MutableStateFlow(OwnerBookingsState())
    val ownerBookingsState = _ownerBookingsState.asStateFlow()

    private val _dashboardOverviewState = MutableStateFlow(DashboardOverviewState())
    val dashboardOverviewState = _dashboardOverviewState.asStateFlow()

    private val _bookingTrendsState = MutableStateFlow(BookingTrendsState())
    val bookingTrendsState = _bookingTrendsState.asStateFlow()

    private val _recentBookingState = MutableStateFlow(RecentBookingState())
    val recentBookingState = _recentBookingState.asStateFlow()

    private val _revenueTrendsState = MutableStateFlow(RevenueTrendsState())
    val revenueTrendsState = _revenueTrendsState.asStateFlow()

    private val _ownerReviewState = MutableStateFlow(OwnerReviewState())
    val ownerReviewState = _ownerReviewState.asStateFlow()

    private val _ownerReviewByIdState = MutableStateFlow(OwnerReviewByIdState())
    val ownerReviewByIdState = _ownerReviewByIdState.asStateFlow()

    private val _deleteReviewState = MutableStateFlow(DeleteReviewState())
    val deleteReviewState = _deleteReviewState.asStateFlow()

    private val _ownerServiceState = MutableStateFlow(OwnerServiceState())
    val ownerServiceState = _ownerServiceState.asStateFlow()

    private val _createOwnerServiceState = MutableStateFlow(CreateOwnerServiceState())
    val createOwnerServiceState = _createOwnerServiceState.asStateFlow()

    private val _updateOwnerServiceState = MutableStateFlow(UpdateOwnerServiceState())
    val updateOwnerServiceState = _updateOwnerServiceState.asStateFlow()

    private val _deleteOwnerServiceState = MutableStateFlow(DeleteOwnerServiceState())
    val deleteOwnerServiceState = _deleteOwnerServiceState.asStateFlow()

    private val _ownerServiceByIdState = MutableStateFlow(OwnerServiceByIdState())
    val ownerServiceByIdState = _ownerServiceByIdState.asStateFlow()

    // 🔹 State for getting all owner rooms
    private val _ownerRoomState = MutableStateFlow(OwnerRoomState())
    val ownerRoomState = _ownerRoomState.asStateFlow()

    // 🔹 State for creating a room
    private val _createOwnerRoomState = MutableStateFlow(CreateOwnerRoomState())
    val createOwnerRoomState = _createOwnerRoomState.asStateFlow()

    // 🔹 State for updating a room
    private val _updateOwnerRoomState = MutableStateFlow(UpdateOwnerRoomState())
    val updateOwnerRoomState = _updateOwnerRoomState.asStateFlow()

    // 🔹 State for deleting a room
    private val _deleteOwnerRoomState = MutableStateFlow(DeleteOwnerRoomState())
    val deleteOwnerRoomState = _deleteOwnerRoomState.asStateFlow()

    // 🔹 State for getting a room by ID
    private val _ownerRoomByIdState = MutableStateFlow(OwnerRoomByIdState())
    val ownerRoomByIdState = _ownerRoomByIdState.asStateFlow()
    // 🔹 State for Wallet Details
    private val _walletState = MutableStateFlow(WalletState())
    val walletState = _walletState.asStateFlow()

    // 🔹 State for Withdrawal History
    private val _withdrawalHistoryState = MutableStateFlow(WithdrawalHistoryState())
    val withdrawalHistoryState = _withdrawalHistoryState.asStateFlow()

    // 🔹 State for Withdraw Amount
    private val _withdrawAmountState = MutableStateFlow(WithdrawAmountState())
    val withdrawAmountState = _withdrawAmountState.asStateFlow()

    private val _profileState = MutableStateFlow(ProfileState())
    val profileState = _profileState.asStateFlow()

    private val _updateProfileState = MutableStateFlow(UpdateProfileState())
    val updateProfileState = _updateProfileState.asStateFlow()

    private val _categoryState = MutableStateFlow(CategoryState())
    val categoryState = _categoryState.asStateFlow()

    private val _facilityState = MutableStateFlow(FacilityState())
    val facilityState = _facilityState.asStateFlow()






    fun getProfile() {
        _profileState.value = ProfileState(isLoading = true)
        viewModelScope.launch(Dispatchers.IO) {
            getProfileUseCase.execute().collect {
                when (it) {
                    is Results.Success -> {
                        Log.d("PRofile","Sucess ${it.data.body()}")
                        _profileState.value = ProfileState(success = it.data)
                    }
                    is Results.Error -> {
                        _profileState.value = ProfileState(error = it.message)
                    }
                    is Results.Loading -> {
                        _profileState.value = ProfileState(isLoading = true)
                    }
                }
            }
        }
    }

    // Update profile data
    fun updateProfile(updateRequest: UpdateProfileRequest) {
        _updateProfileState.value = UpdateProfileState(isLoading = true)
        viewModelScope.launch(Dispatchers.IO) {
            updateProfileUseCase.execute(updateRequest).collect {
                when (it) {
                    is Results.Success -> {
                        _updateProfileState.value = UpdateProfileState(success = it.data)
                    }
                    is Results.Error -> {
                        _updateProfileState.value = UpdateProfileState(error = it.message)
                    }
                    is Results.Loading -> {
                        _updateProfileState.value = UpdateProfileState(isLoading = true)
                    }
                }
            }
        }
    }



    fun getFacilityList() {
        _facilityState.value = FacilityState(isLoading = true)
        viewModelScope.launch(Dispatchers.IO) {
            getFacilityListUseCase.execute().collect { result ->
                _facilityState.value = when (result) {
                    is Results.Success -> {
                        Log.d("getFacility", "Success: ${result.data.body()}")
                        FacilityState(success = result.data)
                    }
                    is Results.Error -> {
                        Log.d("getFacility", "Success: ${result.message}")
                        FacilityState(error = result.message)
                    }
                    is Results.Loading -> FacilityState(isLoading = true)
                }
            }
        }
    }

    fun getCategoryList() {
        _categoryState.value = CategoryState(isLoading = true)
        viewModelScope.launch(Dispatchers.IO) {
            getCategoryListUseCase.execute().collect { result ->
                _categoryState.value = when (result) {
                    is Results.Success -> {
                        Log.d("getcat", "Success: ${result.data.body()}")
                        CategoryState(success = result.data)
                    }
                    is Results.Error -> {
                        Log.d("getcat", "Success: ${result.message}")
                        CategoryState(error = result.message)
                    }
                    is Results.Loading -> CategoryState(isLoading = true)
                }
            }
        }
    }



    fun getWallet() {
        _walletState.value = WalletState(isLoading = true)

        viewModelScope.launch(Dispatchers.IO) {
            getWalletUseCase.execute().collect { result ->
               // Log.d("getWallet", "Received result: $result")

                _walletState.value = when (result) {
                    is Results.Success -> {
                      //  Log.d("getWallet", "Success: ${result.data}")
                        WalletState(success = result.data)
                    }
                    is Results.Error -> {
                      //  Log.e("getWallet", "Error: ${result.message}")
                        WalletState(error = result.message)
                    }
                    is Results.Loading -> {
                        WalletState(isLoading = true)
                    }
                }
            }
        }
    }

//init {
//    getWithdrawalHistory()
//}

    fun getWithdrawalHistory() {
        _withdrawalHistoryState.value = WithdrawalHistoryState(isLoading = true)
        viewModelScope.launch(Dispatchers.IO) {
            getWithdrawalHistoryUseCase.execute().collect { result ->
                _withdrawalHistoryState.value = when (result) {
                    is Results.Success -> {

                        Log.d("getWallet", "Received result: $result")
                        WithdrawalHistoryState(success = result.data.body())
                    }
                    is Results.Error ->{

                        Log.e("getWallet", "Error: ${result.message}")
                        WithdrawalHistoryState(error = result.message)
                    }

                    is Results.Loading -> WithdrawalHistoryState(isLoading = true)
                }
            }
        }
    }

    /**
     * 🔹 Post Withdraw Amount
     */
    fun postWithdrawAmount(amount: Int) {
        _withdrawAmountState.value = WithdrawAmountState(isLoading = true)
        viewModelScope.launch(Dispatchers.IO) {
            postWithdrawAmountUseCase.execute(WithdrawRequest(amount)).collect { result ->
                _withdrawAmountState.value = when (result) {
                    is Results.Success -> WithdrawAmountState(success = result.data)
                    is Results.Error -> WithdrawAmountState(error = result.message)
                    is Results.Loading -> WithdrawAmountState(isLoading = true)
                }
            }
        }
    }



fun getOwnerRooms() {
        _ownerRoomState.value = OwnerRoomState(isLoading = true)
        Log.d("getOwnerRooms", "Fetching owner rooms...") // Log before launching coroutine

        viewModelScope.launch(Dispatchers.IO) {
            getOwnerRoomsUseCase.execute().collect { result ->
                when (result) {
                    is Results.Success -> {
                        val responseBody = result.data.body()
                        Log.d("getOwnerRooms", "Success: Response = $responseBody")

                        _ownerRoomState.value = OwnerRoomState(success = result.data)
                    }
                    is Results.Error -> {
                        Log.e("getOwnerRooms", "Error: ${result.message}")

                        _ownerRoomState.value = OwnerRoomState(error = result.message)
                    }
                    is Results.Loading -> {
                        Log.d("getOwnerRooms", "Loading...")

                        _ownerRoomState.value = OwnerRoomState(isLoading = true)
                    }
                }
            }
        }
    }


    /**
     * 🔹 Create an owner room
     */
    fun createOwnerRoom(request: CreateOwnerRoomRequest) {
        _createOwnerRoomState.value = CreateOwnerRoomState(isLoading = true)
        viewModelScope.launch(Dispatchers.IO) {
            createOwnerRoomUseCase.execute(request).collect { result ->
                _createOwnerRoomState.value = when (result) {
                    is Results.Success -> CreateOwnerRoomState(success = result.data)
                    is Results.Error -> CreateOwnerRoomState(error = result.message)
                    is Results.Loading -> CreateOwnerRoomState(isLoading = true)
                }
            }
        }
    }

    /**
     * 🔹 Update an owner room
     */
    fun updateOwnerRoom(roomId: Int, request: UpdateOwnerRoomRequest) {
        _updateOwnerRoomState.value = UpdateOwnerRoomState(isLoading = true)
        viewModelScope.launch(Dispatchers.IO) {
            updateOwnerRoomUseCase.execute(roomId, request).collect {
                _updateOwnerRoomState.value = when (it) {
                    is Results.Success -> UpdateOwnerRoomState(success = it.data)
                    is Results.Error -> UpdateOwnerRoomState(error = it.message)
                    is Results.Loading -> UpdateOwnerRoomState(isLoading = true)
                }
            }
        }
    }

    /**
     * 🔹 Delete an owner room
     */
    fun deleteOwnerRoom(roomId: Int) {
        _deleteOwnerRoomState.value = DeleteOwnerRoomState(isLoading = true)
        viewModelScope.launch(Dispatchers.IO) {
            deleteOwnerRoomUseCase.execute(roomId).collect { result ->
                _deleteOwnerRoomState.value = when (result) {
                    is Results.Success -> DeleteOwnerRoomState(success = result.data)
                    is Results.Error -> DeleteOwnerRoomState(error = result.message)
                    is Results.Loading -> DeleteOwnerRoomState(isLoading = true)
                }
            }
        }
    }

    /**
     * 🔹 Get owner room by ID
     */
    fun getOwnerRoomById(roomId: Int) {
        _ownerRoomByIdState.value = OwnerRoomByIdState(isLoading = true)
        viewModelScope.launch(Dispatchers.IO) {
            getOwnerRoomByIdUseCase.execute(roomId).collect { result ->
                _ownerRoomByIdState.value = when (result) {
                    is Results.Success ->

                    {
                        val responseBody = result.data.body()
                        Log.d("getOwnerRoomsById", "Success: Response = $responseBody")

                        OwnerRoomByIdState(success = result.data)

                    }
                    is Results.Error -> OwnerRoomByIdState(error = result.message)
                    is Results.Loading -> OwnerRoomByIdState(isLoading = true)
                }
            }
        }
    }


    fun getOwnerService() {
        _ownerServiceState.value = OwnerServiceState(isLoading = true)
        Log.d("getOwnerService", "Fetching owner services...") // Log before launching coroutine

        viewModelScope.launch(Dispatchers.IO) {
            getOwnerServiceUseCase.execute().collect { result ->
                when (result) {
                    is Results.Success -> {
                        Log.d("getOwnerService", "Success: Response = ${result.data.body()}")

                        _ownerServiceState.value = OwnerServiceState(success = result.data)
                    }
                    is Results.Error -> {
                        Log.e("getOwnerService", "Error: ${result.message}")

                        _ownerServiceState.value = OwnerServiceState(error = result.message)
                    }
                    is Results.Loading -> {
                        Log.d("getOwnerService", "Loading...")

                        _ownerServiceState.value = OwnerServiceState(isLoading = true)
                    }
                }
            }
        }
    }


    fun createOwnerService(request: CreateOwnerServiceRequest) {
        _createOwnerServiceState.value = CreateOwnerServiceState(isLoading = true)
        viewModelScope.launch(Dispatchers.IO) {
            createOwnerServiceUseCase.execute(request).collect {
                when (it) {
                    is Results.Success -> _createOwnerServiceState.value = CreateOwnerServiceState(success = it.data)
                    is Results.Error -> _createOwnerServiceState.value = CreateOwnerServiceState(error = it.message)
                    is Results.Loading -> _createOwnerServiceState.value = CreateOwnerServiceState(isLoading = true)
                }
            }
        }
    }


    fun updateOwnerService(serviceId: Int, request: UpdateOwnerServiceRequest) {
        _updateOwnerServiceState.value = UpdateOwnerServiceState(isLoading = true)
        viewModelScope.launch(Dispatchers.IO) {
            updateOwnerServiceUseCase.execute(serviceId, request).collect {
                when (it) {
                    is Results.Success -> _updateOwnerServiceState.value = UpdateOwnerServiceState(success = it.data)
                    is Results.Error -> _updateOwnerServiceState.value = UpdateOwnerServiceState(error = it.message)
                    is Results.Loading -> _updateOwnerServiceState.value = UpdateOwnerServiceState(isLoading = true)
                }
            }
        }
    }


    fun deleteOwnerService(serviceId: Int) {
        _deleteOwnerServiceState.value = DeleteOwnerServiceState(isLoading = true)
        viewModelScope.launch(Dispatchers.IO) {
            deleteOwnerServiceUseCase.execute(serviceId).collect { result ->
                when (result) {
                    is Results.Success -> _deleteOwnerServiceState.value = DeleteOwnerServiceState(success = result.data)
                    is Results.Error -> _deleteOwnerServiceState.value = DeleteOwnerServiceState(error = result.message)
                    is Results.Loading -> _deleteOwnerServiceState.value = DeleteOwnerServiceState(isLoading = true)
                }
            }
        }
    }


    fun getOwnerServiceById(serviceId: Int) {
        _ownerServiceByIdState.value = OwnerServiceByIdState(isLoading = true)
        viewModelScope.launch(Dispatchers.IO) {
            getOwnerServiceByIdUseCase.execute(serviceId).collect { result ->
                when (result) {
                    is Results.Success -> _ownerServiceByIdState.value = OwnerServiceByIdState(success = result.data)
                    is Results.Error -> _ownerServiceByIdState.value = OwnerServiceByIdState(error = result.message)
                    is Results.Loading -> _ownerServiceByIdState.value = OwnerServiceByIdState(isLoading = true)
                }
            }
        }
    }



    fun deleteReview(reviewId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            deleteReviewUseCase.execute(reviewId).collect { result ->
                when (result) {
                    is Results.Success -> _deleteReviewState.value =
                        DeleteReviewState(success = result.data)

                    is Results.Error -> _deleteReviewState.value =
                        DeleteReviewState(error = result.message)

                    is Results.Loading -> _deleteReviewState.value =
                        DeleteReviewState(isLoading = true)
                }
            }
        }
    }


    fun getOwnerReviewById(reviewId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            getOwnerReviewByIdUseCase.execute(reviewId).collect { result ->
                when (result) {
                    is Results.Success -> {
                     //   Log.d("DEBUG", "Success: ${result.data}")
                        _ownerReviewByIdState.value = OwnerReviewByIdState(success = result.data)
                    }

                    is Results.Error -> _ownerReviewByIdState.value =
                        OwnerReviewByIdState(error = result.message)

                    is Results.Loading -> _ownerReviewByIdState.value =
                        OwnerReviewByIdState(isLoading = true)
                }
            }
        }
    }


    fun getOwnerReviews() {
        viewModelScope.launch(Dispatchers.IO) {
            getOwnerReviewUseCase.execute().collect { result ->
                when (result) {
                    is Results.Success -> {
                       Log.d("DEBUGS", "Success: ${result.data.body()?.user}")

                        _ownerReviewState.value =
                            OwnerReviewState(success = result.data)
                    }

                    is Results.Error -> _ownerReviewState.value =
                        OwnerReviewState(error = result.message)

                    is Results.Loading -> _ownerReviewState.value =
                        OwnerReviewState(isLoading = true)
                }
            }
        }
    }

    fun getRevenueTrends() {
        viewModelScope.launch(Dispatchers.IO) {
            getRevenueTrendsUseCase.execute().collect { result ->
                when (result) {
                    is Results.Success -> _revenueTrendsState.value =
                        RevenueTrendsState(success = result.data)

                    is Results.Error -> _revenueTrendsState.value =
                        RevenueTrendsState(error = result.message)

                    is Results.Loading -> _revenueTrendsState.value =
                        RevenueTrendsState(isLoading = true)
                }
            }
        }
    }


    fun getRecentBookings() {
        viewModelScope.launch(Dispatchers.IO) {
            getRecentBookingsUseCase.execute().collect { result ->
                when (result) {
                    is Results.Success -> _recentBookingState.value =
                        RecentBookingState(success = result.data)

                    is Results.Error -> _recentBookingState.value =
                        RecentBookingState(error = result.message)

                    is Results.Loading -> _recentBookingState.value =
                        RecentBookingState(isLoading = true)
                }
            }
        }
    }


    fun getBookingTrends() {
        viewModelScope.launch(Dispatchers.IO) {
            getBookingTrendsUseCase.execute().collect { result ->
                when (result) {
                    is Results.Success -> _bookingTrendsState.value =
                        BookingTrendsState(success = result.data)

                    is Results.Error -> _bookingTrendsState.value =
                        BookingTrendsState(error = result.message)

                    is Results.Loading -> _bookingTrendsState.value =
                        BookingTrendsState(isLoading = true)
                }
            }
        }
    }

    fun getDashboardOverview() {
        _dashboardOverviewState.value = DashboardOverviewState(isLoading = true)
        viewModelScope.launch(Dispatchers.IO) {
            getDashboardOverviewUseCase.execute().collect { result ->
                when (result) {
                    is Results.Success -> _dashboardOverviewState.value =
                        DashboardOverviewState(success = result.data)

                    is Results.Error -> _dashboardOverviewState.value =
                        DashboardOverviewState(error = result.message)

                    is Results.Loading -> _dashboardOverviewState.value =
                        DashboardOverviewState(isLoading = true)
                }
            }
        }
    }


    fun getOwnerBookings() {
        _ownerBookingsState.value = OwnerBookingsState(isLoading = true)
        viewModelScope.launch(Dispatchers.IO) {
            getOwnerBookingsUseCase.execute().collect { result ->
                when (result) {
                    is Results.Success -> {
                        Log.d("DEBUG", "Success: ${result.data.body()?.results}")

                        _ownerBookingsState.value =
                            OwnerBookingsState(success = result.data)
                    }

                    is Results.Error -> {
                        Log.e("DEBUG", "Error: ${result.message}")
                        _ownerBookingsState.value =
                            OwnerBookingsState(error = result.message)
                    }
                    is Results.Loading -> _ownerBookingsState.value =
                        OwnerBookingsState(isLoading = true)
                }
            }
        }
    }

    fun refreshToken(refreshTokenRequest: RefreshTokenRequest) {
        _refreshTokenState.value = RefreshTokenState(isLoading = true)
        viewModelScope.launch(Dispatchers.IO) {
            refreshTokenUseCase.execute(refreshTokenRequest).collect {
                when (it) {
                    is Results.Success -> {
                        _refreshTokenState.value = RefreshTokenState(success = it.data)
                    }

                    is Results.Error -> {
                        _refreshTokenState.value = RefreshTokenState(error = it.message)
                    }

                    is Results.Loading -> {
                        _refreshTokenState.value = RefreshTokenState(isLoading = true)
                    }
                }
            }
        }
    }

    fun forgetPassword(forgetPasswordRequest: ForgetPasswordRequest) {
        viewModelScope.launch(Dispatchers.IO) {
            forgetPasswordUseCase.execute(forgetPasswordRequest).collect {
                when (it) {
                    is Results.Success -> _forgetPasswordState.value =
                        ForgetPasswordState(success = it.data)

                    is Results.Error -> _forgetPasswordState.value =
                        ForgetPasswordState(error = it.message)

                    is Results.Loading -> _forgetPasswordState.value =
                        ForgetPasswordState(isLoading = true)
                }
            }
        }
    }

    fun verifyOtp(forgetPassOtpVerifyRequest: ForgetPassOtpVerifyRequest) {
        viewModelScope.launch(Dispatchers.IO) {
            forgetPasswordOtpVerifyUseCase.execute(forgetPassOtpVerifyRequest).collect {
                when (it) {
                    is Results.Success -> _otpVerificationState.value =
                        OtpVerificationState(success = it.data)

                    is Results.Error -> _otpVerificationState.value =
                        OtpVerificationState(error = it.message)

                    is Results.Loading -> _otpVerificationState.value =
                        OtpVerificationState(isLoading = true)
                }
            }
        }
    }

    fun changePassword(changePasswordRequest: ChangePasswordRequest) {
        viewModelScope.launch(Dispatchers.IO) {
            changePasswordUseCase.execute(changePasswordRequest).collect {
                when (it) {
                    is Results.Success -> _changePasswordState.value =
                        ChangePasswordState(success = it.data)

                    is Results.Error -> _changePasswordState.value =
                        ChangePasswordState(error = it.message)

                    is Results.Loading -> _changePasswordState.value =
                        ChangePasswordState(isLoading = true)
                }
            }
        }
    }

    fun clearOtpVerificationState() {
        _otpVerifyState.value = OtpVerifyState()
    }

    fun clearSignUpState() {
        _signUpState.value = SignUpState()
    }

    fun loginOtpVerify(loginOtRequestModel: LoginOtRequestModel) {
        _loginOtpVerifyState.value = LoginOtpVerifyState(isLoading = true)
        viewModelScope.launch(Dispatchers.IO) {
            loginOtpVerifyUseCase.loginOtpVerifyUseCase(loginOtRequestModel).collect {
                when (it) {
                    is Results.Success -> {
                        dataStoreManager.saveLoginState(true)
                        _loginOtpVerifyState.value =
                            LoginOtpVerifyState(success = it.data)
                    }

                    is Results.Error -> {
                        _loginOtpVerifyState.value =
                            LoginOtpVerifyState(error = it.message)
                    }

                    is Results.Loading -> {
                        _loginOtpVerifyState.value = LoginOtpVerifyState(isLoading = true)
                    }
                }
            }
        }


    }

    fun login(signinRequestModel: SigninRequestModel) {
        viewModelScope.launch(Dispatchers.IO) {
            signinUseCase.signinUseCase(signinRequestModel).collect {
                when (it) {
                    is Results.Success -> {
                        //  userPreferences.saveLoginStatus(true)
                        _signInState.value = SignInState(success = it.data)
                    }

                    is Results.Error -> {
                        _signInState.value = SignInState(error = it.message)
                    }

                    is Results.Loading -> {
                        _signInState.value = SignInState(isLoading = true)
                    }

                }
            }

        }
    }


    fun verifyOtp(verifyOtRequestModel: VerifyOtRequestModel) {
        _otpVerifyState.value = OtpVerifyState(isLoading = true)
        viewModelScope.launch(Dispatchers.IO) {
            verifyOtpUseCase.verifyOtpUseCase(verifyOtRequestModel).collect {
                when (it) {
                    is Results.Success -> {
                        _otpVerifyState.value =
                            OtpVerifyState(success = it.data)
                    }

                    is Results.Error -> {
                        _otpVerifyState.value =
                            OtpVerifyState(error = it.message)
                    }

                    is Results.Loading -> {
                        _otpVerifyState.value = OtpVerifyState(isLoading = true)
                    }
                }
            }
        }


    }


    fun signUp(signUpRequestModel: SingUpRequestModel) {
        _signUpState.value = SignUpState(isLoading = true)
        viewModelScope.launch(Dispatchers.IO) {
            signupUseCase.signupUseCase(signUpRequestModel).collect {
                when (it) {
                    is Results.Success -> {
                        _signUpState.value = SignUpState(success = it.data)
                    }

                    is Results.Error -> {
                        _signUpState.value = SignUpState(error = it.message)
                    }

                    is Results.Loading -> {
                        _signUpState.value = SignUpState(isLoading = true)
                    }


                }
            }
        }
    }

    fun logOut() {
        viewModelScope.launch(Dispatchers.IO) {
            logOutUseCase.logOutUseCase().collect {
                when (it) {
                    is Results.Error -> {
                        _logOutState.value = LogoutState(
                            errorMessage = it.message,
                            isLoading = false
                        )
                    }

                    is Results.Loading -> {
                        _logOutState.value = LogoutState(
                            isLoading = true
                        )
                    }

                    is Results.Success -> {
                        dataStoreManager.saveLoginState(false)
                        _logOutState.value = LogoutState(
                            success = it.data,
                            isLoading = false
                        )
                    }
                }
            }
        }
    }


}

data class SignUpState(
    val isLoading: Boolean = false,
    val success: Response<SignupResponseModel>? = null,
    var error: String? = null
)

data class OtpVerifyState(
    val isLoading: Boolean = false,
    val success: Response<SignupResponseModel>? = null,
    val error: String? = null
)

data class LoginOtpVerifyState(
    val isLoading: Boolean = false,
    val success: Response<SigninResponseModel>? = null,
    val error: String? = null
)

data class SignInState(
    val isLoading: Boolean = false,
    val success: Response<SigninResponseModel>? = null,
    val error: String? = null

)

data class LogoutState(
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val success: Response<LogoutResponse>? = null
)

data class ForgetPasswordState(
    val isLoading: Boolean = false,
    val success: Response<ForgetPasswordResponse>? = null,
    val error: String? = null
)

data class OtpVerificationState(
    val isLoading: Boolean = false,
    val success: Response<ForgetPassOtpVerifyResponse>? = null,
    val error: String? = null
)

data class ChangePasswordState(
    val isLoading: Boolean = false,
    val success: Response<ChangePasswordResponse>? = null,
    val error: String? = null
)

data class RefreshTokenState(
    val isLoading: Boolean = false,
    val success: Response<RefreshTokenResponse>? = null,
    val error: String? = null
)

data class OwnerBookingsState(
    val isLoading: Boolean = false,
    val success: Response<OwnerBookingsResponse>? = null,
    val error: String? = null
)

data class DashboardOverviewState(
    val isLoading: Boolean = false,
    val success: Response<DashboardOverview>? = null,
    val error: String? = null
)

data class BookingTrendsState(
    val isLoading: Boolean = false,
    val success: Response<BookingTrends>? = null,
    val error: String? = null
)

data class RecentBookingState(
    val isLoading: Boolean = false,
    val success: Response<RecentBooking>? = null,
    val error: String? = null
)

data class RevenueTrendsState(
    val isLoading: Boolean = false,
    val success: Response<BookingTrends>? = null,
    val error: String? = null
)

data class OwnerReviewState(
    val isLoading: Boolean = false,
    val success: Response<Review>? = null,
    val error: String? = null
)

data class OwnerReviewByIdState(
    val isLoading: Boolean = false,
    val success: Response<Review>? = null,
    val error: String? = null
)

data class DeleteReviewState(
    val isLoading: Boolean = false,
    val success: Response<Unit>? = null,
    val error: String? = null
)

data class OwnerServiceState(
    val isLoading: Boolean = false,
    val success: Response<OwnerServiceResponse>? = null,
    val error: String? = null
)

data class CreateOwnerServiceState(
    val isLoading: Boolean = false,
    val success: Response<CreateOwnerServiceResponse>? = null,
    val error: String? = null
)

data class UpdateOwnerServiceState(
    val isLoading: Boolean = false,
    val success: Response<UpdateOwnerServiceResponse>? = null,
    val error: String? = null
)

data class DeleteOwnerServiceState(
    val isLoading: Boolean = false,
    val success: Response<DeleteOwnerServiceResponse>? = null,
    val error: String? = null
)

data class OwnerServiceByIdState(
    val isLoading: Boolean = false,
    val success: Response<OwnerServiceByIdResponse>? = null,
    val error: String? = null
)

data class OwnerRoomState(
    val isLoading: Boolean = false,
    val success: Response<OwnerRoomResponse>? = null,
    val error: String? = null
)

data class CreateOwnerRoomState(
    val isLoading: Boolean = false,
    val success: Response<CreateOwnerRoomResponse>? = null,
    val error: String? = null
)

data class UpdateOwnerRoomState(
    val isLoading: Boolean = false,
    val success: Response<UpdateOwnerRoomResponse>? = null,
    val error: String? = null
)

data class DeleteOwnerRoomState(
    val isLoading: Boolean = false,
    val success: Response<DeleteOwnerRoomResponse>? = null,
    val error: String? = null
)

data class OwnerRoomByIdState(
    val isLoading: Boolean = false,
    val success: Response<OwnerRoomByIdResponse>? = null,
    val error: String? = null
)
data class WalletState(
    val isLoading: Boolean = false,
    val success: Response<WalletResponse>? = null,
    val error: String? = null
)

data class WithdrawalHistoryState(
    val isLoading: Boolean = false,
    val success: List<WithdrawalHistoryResponse>? = null,
    val error: String? = null
)

data class WithdrawAmountState(
    val isLoading: Boolean = false,
    val success: Response<WithdrawResponse>? = null,
    val error: String? = null
)
data class ProfileState(
    val isLoading: Boolean = false,
    val success: Response<GetProfileResponse>? = null,
    val error: String? = null
)

data class UpdateProfileState(
    val isLoading: Boolean = false,
    val success: Response<UpdateProfileResponse>? = null,
    val error: String? = null
)

data class CategoryState(
    val isLoading: Boolean = false,
    val success: Response<List<CategoryResponse>>? = null,
    val error: String? = null
)

data class FacilityState(
    val isLoading: Boolean = false,
    val success: Response<List<FacilityResponse>>? = null,
    val error: String? = null
)