package com.ilyakrn.cardexpulseoximeterapp.models

data class DeviceModel(
    val address: String,
    val name: String,
    var isSaved: Boolean,
    var isConnected: Boolean,
)