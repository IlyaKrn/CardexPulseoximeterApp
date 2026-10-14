package com.ilyakrn.cardexpulseoximeterapp.domain.usecases

import com.ilyakrn.cardexpulseoximeterapp.domain.repos.DeviceRepo

class StopFindingAvailableDevicesUseCase(private val deviceRepo: DeviceRepo) {

    fun invoke(){
        deviceRepo.stopFindingDevices()
    }

}