package com.shv.canifly.domain.usecases

import com.shv.canifly.domain.entity.Airport
import com.shv.canifly.domain.repository.Repository
import com.shv.canifly.domain.util.Resource
import javax.inject.Inject

class GetNfzDataUseCase @Inject constructor(private val repository: Repository) {

    suspend operator fun invoke(): Resource<List<Airport>> {
        return repository.getNfzData()
    }
}