package com.ilyakrn.cardexpulseoximeterapp.app.models

data class DeviceModel(
    val address: String,
    val name: String,
    var isSaved: Boolean,
    var isConnected: Boolean,
)