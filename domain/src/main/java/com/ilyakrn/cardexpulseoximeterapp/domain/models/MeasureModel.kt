package com.ilyakrn.cardexpulseoximeterapp.domain.models

data class MeasureModel(
    val measureId: Long?,
    val deviceMacAddress: String,
    val deviceName: String,
    val timestamp: String,
    var PR: Int,
    var SpO2: Int,
)