package com.ilyakrn.cardexpulseoximeterapp.domain.useceses

import com.ilyakrn.cardexpulseoximeterapp.domain.models.DeviceModel
import com.ilyakrn.cardexpulseoximeterapp.domain.repos.DeviceRepo

class ConnectToDevice(private val deviceMacAddress: String, private val deviceRepo: DeviceRepo) {

    fun invoke(){
        deviceRepo.connect(deviceMacAddress)
    }

}