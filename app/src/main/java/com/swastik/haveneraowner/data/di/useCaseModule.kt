package com.swastik.haveneraowner.data.di

import com.swastik.haveneraowner.domain.useCase.ChangePasswordUseCase
import com.swastik.haveneraowner.domain.useCase.CreateOwnerRoomUseCase
import com.swastik.haveneraowner.domain.useCase.CreateOwnerServiceUseCase
import com.swastik.haveneraowner.domain.useCase.DeleteOwnerRoomUseCase
import com.swastik.haveneraowner.domain.useCase.DeleteOwnerServiceUseCase
import com.swastik.haveneraowner.domain.useCase.ForgetPasswordOtpVerifyUseCase
import com.swastik.haveneraowner.domain.useCase.ForgetPasswordUseCase
import com.swastik.haveneraowner.domain.useCase.GetCategoryListUseCase
import com.swastik.haveneraowner.domain.useCase.GetFacilityListUseCase
import com.swastik.haveneraowner.domain.useCase.GetOwnerBookingsUseCase
import com.swastik.haveneraowner.domain.useCase.GetOwnerRoomByIdUseCase
import com.swastik.haveneraowner.domain.useCase.GetOwnerRoomsUseCase
import com.swastik.haveneraowner.domain.useCase.GetOwnerServiceByIdUseCase
import com.swastik.haveneraowner.domain.useCase.GetOwnerServiceUseCase
import com.swastik.haveneraowner.domain.useCase.GetProfileUseCase
import com.swastik.haveneraowner.domain.useCase.GetWalletUseCase
import com.swastik.haveneraowner.domain.useCase.GetWithdrawalHistoryUseCase
import com.swastik.haveneraowner.domain.useCase.LogOutUseCase
import com.swastik.haveneraowner.domain.useCase.LoginOtpVerifyUseCase
import com.swastik.haveneraowner.domain.useCase.PostWithdrawAmountUseCase
import com.swastik.haveneraowner.domain.useCase.RefreshTokenUseCase
import com.swastik.haveneraowner.domain.useCase.SigninUseCase
import com.swastik.haveneraowner.domain.useCase.SignupUseCase
import com.swastik.haveneraowner.domain.useCase.UpdateOwnerRoomUseCase
import com.swastik.haveneraowner.domain.useCase.UpdateOwnerServiceUseCase
import com.swastik.haveneraowner.domain.useCase.UpdateProfileUseCase
import com.swastik.haveneraowner.domain.useCase.VerifyOtpUseCase
import com.swastik.haveneraowner.domain.useCase.deleteReviewUseCase
import com.swastik.haveneraowner.domain.useCase.getBookingTrendsUseCase
import com.swastik.haveneraowner.domain.useCase.getDashboardOverviewUseCase
import com.swastik.haveneraowner.domain.useCase.getOwnerReviewByIdUseCase
import com.swastik.haveneraowner.domain.useCase.getOwnerReviewUseCase
import com.swastik.haveneraowner.domain.useCase.getRecentBookingsUseCase
import com.swastik.haveneraowner.domain.useCase.getRevenueTrendsUseCase
import org.koin.dsl.module

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