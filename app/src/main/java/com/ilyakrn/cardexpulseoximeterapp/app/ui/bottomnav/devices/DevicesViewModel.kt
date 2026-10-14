package com.ilyakrn.cardexpulseoximeterapp.app.ui.bottomnav.devices

import android.Manifest
import android.bluetooth.BluetoothAdapter
import android.content.Intent
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ilyakrn.cardexpulseoximeterapp.data.repos.DeviceRepoImpl
import com.ilyakrn.cardexpulseoximeterapp.data.repos.MeasureRepoImpl
import com.ilyakrn.cardexpulseoximeterapp.domain.models.DeviceModel
import com.ilyakrn.cardexpulseoximeterapp.domain.usecases.CheckConnectionAvailableUseCase
import com.ilyakrn.cardexpulseoximeterapp.domain.usecases.ConnectDeviceUseCase
import com.ilyakrn.cardexpulseoximeterapp.domain.usecases.StartFindingAvailableDevicesUseCase
import com.ilyakrn.cardexpulseoximeterapp.domain.usecases.StopFindingAvailableDevicesUseCase


class DevicesViewModel : ViewModel() {

    val devices = MutableLiveData<ArrayList<DeviceModel>>().apply {
        value = ArrayList()
    }

    fun startDevicesScan(fragment: Fragment){
        StartFindingAvailableDevicesUseCase(DeviceRepoImpl(fragment.requireContext(), {
            fragment.requestPermissions(arrayOf(Manifest.permission.BLUETOOTH_CONNECT), 1)
        },{
            fragment.startActivityForResult(Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE), 1)
        }), {
            devices.value?.add(it)
        }).invoke()
    }

    fun stopDevicesScan(fragment: Fragment){
        StopFindingAvailableDevicesUseCase(DeviceRepoImpl(fragment.requireContext(), {}, {})).invoke()
    }

    fun connectToDevice(fragment: Fragment, address: String) {
        fragment.requestPermissions(arrayOf(
            Manifest.permission.BLUETOOTH_CONNECT,
            Manifest.permission.BLUETOOTH_SCAN,
            Manifest.permission.ACCESS_COARSE_LOCATION
        ), 3)

        ConnectDeviceUseCase(address, DeviceRepoImpl(fragment.requireContext(), {
            fragment.requestPermissions(arrayOf(Manifest.permission.BLUETOOTH_CONNECT), 2)
        },{
            fragment.startActivityForResult(Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE), 2)
        }), MeasureRepoImpl(fragment.requireContext())
        ).invoke()
    }

    fun enableBluetooth(fragment: Fragment){
        fragment.requestPermissions(arrayOf(
            Manifest.permission.BLUETOOTH_CONNECT
        ), 1)
        if (CheckConnectionAvailableUseCase(DeviceRepoImpl(fragment.requireContext(), {}, {})).invoke())
            fragment.startActivityForResult(Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE), 1)
    }


}