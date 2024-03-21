package com.shv.canifly.domain.usecases

import com.shv.canifly.domain.entity.WatchingDate
import com.shv.canifly.domain.repository.Repository
import javax.inject.Inject

class AddWatchingDateUseCase @Inject constructor(private val repository: Repository) {
    suspend operator fun invoke(watchingDate: WatchingDate) {
        repository.addWatchingDate(watchingDate)
    }
}