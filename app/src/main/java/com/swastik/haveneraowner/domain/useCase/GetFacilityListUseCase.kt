package com.swastik.haveneraowner.domain.useCase

import com.swastik.haveneraowner.common.Results
import com.swastik.haveneraowner.data.models.response.FacilityResponse
import com.swastik.haveneraowner.domain.Repo.Repo
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

class GetFacilityListUseCase(private val repo: Repo) {
    suspend fun execute(): Flow<Results<Response<List<FacilityResponse>>>> {
        return repo.getFacilityList()
    }
}