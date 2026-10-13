package com.ilyakrn.cardexpulseoximeterapp.domain.usecases

import com.ilyakrn.cardexpulseoximeterapp.domain.models.DeviceModel
import com.ilyakrn.cardexpulseoximeterapp.domain.repos.DeviceRepo

class GetAvailableDevicesUseCase(private val onDeviceFound: (DeviceModel) -> Unit, private val deviceRepo: DeviceRepo) {

    fun invoke(){
        deviceRepo.findDevices(onDeviceFound)
    }

}