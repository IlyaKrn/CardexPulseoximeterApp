package com.ilyakrn.cardexpulseoximeterapp.domain.useceses

import com.ilyakrn.cardexpulseoximeterapp.domain.repos.DeviceRepo

class DisconnectDevice(private val deviceRepo: DeviceRepo) {

    fun invoke(){
        deviceRepo.disconnect()
    }

}