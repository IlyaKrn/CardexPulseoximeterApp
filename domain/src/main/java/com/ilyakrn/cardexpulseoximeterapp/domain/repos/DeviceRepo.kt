package com.ilyakrn.cardexpulseoximeterapp.domain.repos

import com.ilyakrn.cardexpulseoximeterapp.domain.models.DeviceModel
import com.ilyakrn.cardexpulseoximeterapp.domain.models.MeasureModel

interface DeviceRepo {
    fun findDevices(onDeviceFound: (DeviceModel) -> Unit)
    fun getCurrentDevice(): DeviceModel?
    fun connect(mac: String)
    fun disconnect()
    fun listen(onReadMeasure: (MeasureModel) -> Unit)
}