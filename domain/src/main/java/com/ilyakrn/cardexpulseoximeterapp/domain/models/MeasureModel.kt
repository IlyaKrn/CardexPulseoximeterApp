package com.ilyakrn.cardexpulseoximeterapp.domain.models

data class MeasureModel(
    val measureId: Long?,
    val deviceMacAddress: String,
    val deviceName: String,
    val timestamp: Long,
    val PR: Int,
    val SpO2: Int,
)