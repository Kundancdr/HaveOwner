package com.example.haveneraowner.domain.useCase


import com.example.haveneraowner.common.Results
import com.example.haveneraowner.data.models.request.SingUpRequestModel
import com.example.haveneraowner.data.models.response.SignupResponseModel
import com.example.haveneraowner.domain.Repo.Repo
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

class SignupUseCase (private val repo: Repo){
    suspend fun signupUseCase( singUpRequestModel: SingUpRequestModel): Flow<Results<Response<SignupResponseModel>>> {
        return repo.signup(singUpRequestModel)
    }
}