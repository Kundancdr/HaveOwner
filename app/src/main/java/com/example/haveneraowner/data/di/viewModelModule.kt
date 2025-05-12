package com.example.haveneraowner.data.di

import com.example.haveneraowner.domain.useCase.GetOwnerRoomsUseCase
import com.example.haveneraowner.domain.useCase.getBookingTrendsUseCase
import com.example.haveneraowner.domain.useCase.getDashboardOverviewUseCase
import com.example.haveneraowner.presentation.viewModels.AuthViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {

    viewModel {
        AuthViewModel(
            dataStoreManager = get(),
            signupUseCase = get(),
            verifyOtpUseCase = get(),
            signinUseCase = get(),
            loginOtpVerifyUseCase = get(),
            logOutUseCase = get(),
            forgetPasswordUseCase = get(),
            forgetPasswordOtpVerifyUseCase = get(),
            changePasswordUseCase = get(),
            refreshTokenUseCase = get(),
            getOwnerBookingsUseCase = get(),
            getDashboardOverviewUseCase = get(),
            getBookingTrendsUseCase= get(),
            getRecentBookingsUseCase = get(),
            getRevenueTrendsUseCase = get(),
            getOwnerReviewUseCase = get(),
            getOwnerReviewByIdUseCase = get(),
            deleteReviewUseCase = get(),
            getOwnerServiceUseCase = get(),
            createOwnerServiceUseCase = get(),
            updateOwnerServiceUseCase = get(),
            deleteOwnerServiceUseCase = get(),
            getOwnerServiceByIdUseCase = get(),
            getOwnerRoomsUseCase = get(),
            createOwnerRoomUseCase = get(),
            updateOwnerRoomUseCase = get(),
            deleteOwnerRoomUseCase = get(),
            getOwnerRoomByIdUseCase = get(),
            getWalletUseCase = get(),
            getWithdrawalHistoryUseCase = get(),
            postWithdrawAmountUseCase = get(),
            getProfileUseCase = get(),
            updateProfileUseCase = get(),
            getCategoryListUseCase = get(),
            getFacilityListUseCase = get()





        )
    }
}