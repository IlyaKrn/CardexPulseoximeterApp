package com.ilyakrn.cardexpulseoximeterapp.domain.repos

import com.ilyakrn.cardexpulseoximeterapp.domain.models.DeviceModel
import com.ilyakrn.cardexpulseoximeterapp.domain.models.MeasureModel

interface DeviceRepo {
    fun findDevices(onDeviceFound: (DeviceModel) -> Unit)
    fun getConnectionAvailableStatus(): Boolean
    fun stopFindingDevices()
    fun connect(deviceMacAddress: String, onReadMeasure: (MeasureModel) -> Unit)
    fun disconnect()
}