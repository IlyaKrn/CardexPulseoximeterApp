package com.ilyakrn.cardexpulseoximeterapp.ui.bottomnav.devices

import android.Manifest
import android.annotation.SuppressLint
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothManager
import android.content.Context
import android.content.Intent
import androidx.annotation.RequiresPermission
import androidx.core.app.ActivityCompat.startActivityForResult
import androidx.core.content.ContextCompat.getSystemService
import androidx.core.os.BuildCompat
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ilyakrn.cardexpulseoximeterapp.models.DeviceModel

class DevicesViewModel : ViewModel() {

    val devices = MutableLiveData<ArrayList<DeviceModel>>().apply {
        value = ArrayList()
    }

    @RequiresPermission(Manifest.permission.BLUETOOTH_CONNECT)
    fun getDevices(context: Context){
        val newDevices = ArrayList<DeviceModel>()
        getSystemService(context, BluetoothManager::class.java)!!.adapter!!.bondedDevices.forEach {
            newDevices.add(DeviceModel(it.address, it.name, false))
        }
        devices.value = newDevices
    }
}