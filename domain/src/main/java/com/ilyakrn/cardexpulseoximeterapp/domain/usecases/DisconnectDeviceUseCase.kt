package com.ilyakrn.cardexpulseoximeterapp.domain.usecases

import com.ilyakrn.cardexpulseoximeterapp.domain.repos.DeviceRepo

class DisconnectDeviceUseCase(private val deviceRepo: DeviceRepo) {

    fun invoke(){
        deviceRepo.disconnect()
    }

}