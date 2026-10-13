package com.ilyakrn.cardexpulseoximeterapp.domain.useceses

import com.ilyakrn.cardexpulseoximeterapp.domain.models.DeviceModel
import com.ilyakrn.cardexpulseoximeterapp.domain.repos.DeviceRepo

class GetAvailableDevices(private val onDeviceFound: (DeviceModel) -> Unit, private val deviceRepo: DeviceRepo) {

    fun invoke(){
        deviceRepo.findDevices(onDeviceFound)
    }

}