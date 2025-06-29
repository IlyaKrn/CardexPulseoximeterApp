package com.ilyakrn.cardexpulseoximeterapp.models

data class MeasureModel(
    val deviceAddress: String,
    val deviceName: String,
    val timestamp: Long,
    var PR: Int,
    var SpO2: Int
)