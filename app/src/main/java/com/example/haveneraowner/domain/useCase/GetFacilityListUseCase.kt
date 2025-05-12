package com.example.haveneraowner.domain.useCase

import com.example.haveneraowner.common.Results
import com.example.haveneraowner.data.models.response.CategoryResponse
import com.example.haveneraowner.data.models.response.FacilityResponse
import com.example.haveneraowner.domain.Repo.Repo
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

class GetFacilityListUseCase(private val repo: Repo) {
    suspend fun execute(): Flow<Results<Response<FacilityResponse>>> {
        return repo.getFacilityList()
    }
}