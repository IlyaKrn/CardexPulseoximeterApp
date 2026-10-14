package com.ilyakrn.cardexpulseoximeterapp.domain.usecases

import com.ilyakrn.cardexpulseoximeterapp.domain.repos.DeviceRepo

class CheckConnectionAvailableUseCase(private val deviceRepo: DeviceRepo) {

    fun invoke(): Boolean {
        return deviceRepo.getConnectionAvailableStatus()
    }

}