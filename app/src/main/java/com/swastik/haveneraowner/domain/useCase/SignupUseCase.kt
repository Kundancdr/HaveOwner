package com.swastik.haveneraowner.domain.useCase


import com.swastik.haveneraowner.common.Results
import com.swastik.haveneraowner.data.models.request.SingUpRequestModel
import com.swastik.haveneraowner.data.models.response.SignupResponseModel
import com.swastik.haveneraowner.domain.Repo.Repo
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

class SignupUseCase (private val repo: Repo){
    suspend fun signupUseCase( singUpRequestModel: SingUpRequestModel): Flow<Results<Response<SignupResponseModel>>> {
        return repo.signup(singUpRequestModel)
    }
}