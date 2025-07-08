package com.ilyakrn.cardexpulseoximeterapp.models

data class MeasureModel(
    val databaseId: Long,
    val deviceAddress: String,
    val deviceName: String,
    val timestamp: String,
    var PR: Int,
    var SpO2: Int
)