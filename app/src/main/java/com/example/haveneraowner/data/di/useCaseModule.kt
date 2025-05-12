package com.example.haveneraowner.data.di

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
import org.koin.dsl.module
import kotlin.math.sin

val useCaseModule = module {


    // Provide ChangePasswordUseCase
    single { SignupUseCase(get()) }

    single { VerifyOtpUseCase(get()) }

    single { SigninUseCase(get()) }

    single { LoginOtpVerifyUseCase(get()) }

    single { RefreshTokenUseCase(get()) }

    single { LogOutUseCase(get()) }

    single { ChangePasswordUseCase(get()) }

    single { ForgetPasswordOtpVerifyUseCase(get()) }

    single { ForgetPasswordUseCase(get()) }

    single { GetOwnerBookingsUseCase(get()) }

    single { getDashboardOverviewUseCase(get()) }

    single { getBookingTrendsUseCase(get()) }

    single { getRecentBookingsUseCase(get()) }

    single { getRevenueTrendsUseCase(get()) }

    single { getOwnerReviewUseCase(get()) }

    single { getOwnerReviewByIdUseCase(get()) }

    single { deleteReviewUseCase(get()) }

    single { GetOwnerServiceUseCase(get()) }
    single { CreateOwnerServiceUseCase(get()) }
    single { UpdateOwnerServiceUseCase(get()) }
    single { DeleteOwnerServiceUseCase(get()) }
    single { GetOwnerServiceByIdUseCase(get()) }


    single { GetOwnerRoomsUseCase(get()) }
    single { CreateOwnerRoomUseCase(get()) }
    single { UpdateOwnerRoomUseCase(get()) }
    single { DeleteOwnerRoomUseCase(get()) }
    single { GetOwnerRoomByIdUseCase(get()) }

    single { GetWalletUseCase(get()) }
    single { GetWithdrawalHistoryUseCase(get()) }
    single { PostWithdrawAmountUseCase(get()) }


    single { GetProfileUseCase(get()) }
    single { UpdateProfileUseCase(get()) }
    single { GetCategoryListUseCase(get()) }
    single { GetFacilityListUseCase(get()) }












}