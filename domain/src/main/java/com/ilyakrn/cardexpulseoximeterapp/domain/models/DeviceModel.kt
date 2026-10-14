package com.ilyakrn.cardexpulseoximeterapp.domain.models

data class DeviceModel(
    val address: String,
    val name: String,
    var isSaved: Boolean = false,
    var isConnected: Boolean = false,
) {
    override fun toString(): String {
        return "DeviceModel(address='$address', name='$name', isSaved=$isSaved, isConnected=$isConnected)"
    }
}