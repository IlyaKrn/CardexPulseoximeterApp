package com.ilyakrn.cardexpulseoximeterapp.domain.usecases

import com.ilyakrn.cardexpulseoximeterapp.domain.models.DeviceModel
import com.ilyakrn.cardexpulseoximeterapp.domain.repos.DeviceRepo

class StartFindingAvailableDevicesUseCase(private val deviceRepo: DeviceRepo, private val onDeviceFound: (DeviceModel) -> Unit) {

    fun invoke(){
        deviceRepo.findDevices(onDeviceFound)
    }

}