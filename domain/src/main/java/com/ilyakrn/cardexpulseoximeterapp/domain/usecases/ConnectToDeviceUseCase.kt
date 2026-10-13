package com.ilyakrn.cardexpulseoximeterapp.domain.usecases

import com.ilyakrn.cardexpulseoximeterapp.domain.models.MeasureModel
import com.ilyakrn.cardexpulseoximeterapp.domain.repos.DeviceRepo
import com.ilyakrn.cardexpulseoximeterapp.domain.repos.MeasureRepo

class ConnectToDeviceUseCase(private val deviceMacAddress: String, private val deviceRepo: DeviceRepo, private val measureRepo: MeasureRepo) {

    fun invoke(){
        deviceRepo.connect(deviceMacAddress, {
            measureRepo.createMeasure(it)
        })
    }

}