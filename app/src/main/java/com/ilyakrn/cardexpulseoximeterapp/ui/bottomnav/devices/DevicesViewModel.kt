package com.ilyakrn.cardexpulseoximeterapp.ui.bottomnav.devices

import android.annotation.SuppressLint
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothManager
import android.content.Context
import android.content.Intent
import androidx.core.app.ActivityCompat.startActivityForResult
import androidx.core.content.ContextCompat.getSystemService
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ilyakrn.cardexpulseoximeterapp.models.DeviceModel

class DevicesViewModel : ViewModel() {

    val devices = MutableLiveData<ArrayList<DeviceModel>>().apply {
        value = ArrayList()
    }

    @SuppressLint("MissingPermission")
    fun getDevices(context: Context){
        getSystemService(context, BluetoothManager::class.java)?.adapter?.let {
            if (it.isEnabled)
                devices.value = it.bondedDevices.map { device -> DeviceModel(device.address.toString(), device.name, false) } as ArrayList<DeviceModel>
        }
    }

    fun updateDevices(){

    }

    fun enableBluetooth(fragment: androidx.fragment.app.Fragment){
        getSystemService(fragment.requireContext(), BluetoothManager::class.java)?.adapter?.let {
            if (!it.isEnabled) {
                fragment.startActivityForResult(Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE), 1)
            }
        }
    }
}