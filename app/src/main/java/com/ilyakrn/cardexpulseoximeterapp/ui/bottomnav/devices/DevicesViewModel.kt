package com.ilyakrn.cardexpulseoximeterapp.ui.bottomnav.devices

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothManager
import android.bluetooth.BluetoothSocket
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.location.LocationManager
import android.os.Build
import android.provider.Settings
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresPermission
import androidx.core.app.ActivityCompat
import androidx.core.app.ActivityCompat.requestPermissions
import androidx.core.app.ActivityCompat.startActivityForResult
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.getSystemService
import androidx.core.os.BuildCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.findViewTreeViewModelStoreOwner
import androidx.lifecycle.viewModelScope
import com.ilyakrn.cardexpulseoximeterapp.R
import com.ilyakrn.cardexpulseoximeterapp.models.DeviceModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.io.IOException
import java.util.UUID


class DevicesViewModel : ViewModel() {

    val devices = MutableLiveData<ArrayList<DeviceModel>>().apply {
        value = ArrayList()
    }

    private lateinit var adapter: BluetoothAdapter

    private lateinit var receiver: BroadcastReceiver

    private var isReceiverRegistered = false

    fun startDevicesScan(fragment: Fragment){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.S && ContextCompat.checkSelfPermission(fragment.requireContext(), Manifest.permission.BLUETOOTH_SCAN) != PackageManager.PERMISSION_GRANTED) {
            fragment.requestPermissions(arrayOf(Manifest.permission.BLUETOOTH_SCAN), 1)
            return
        }
        if(ContextCompat.checkSelfPermission(fragment.requireContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED ||
            ContextCompat.checkSelfPermission(fragment.requireContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED
        ) {
            fragment.requestPermissions(arrayOf(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION), 1)
            return
        }

        adapter = getSystemService(fragment.requireContext(), BluetoothManager::class.java)!!.adapter!!
        if(!isReceiverRegistered) {
            receiver = object : BroadcastReceiver() {
                override fun onReceive(context: Context, intent: Intent) {
                    if (intent.action == BluetoothDevice.ACTION_FOUND) {
                        intent.getParcelableExtra<BluetoothDevice>(BluetoothDevice.EXTRA_DEVICE)?.apply {
                            val newDevices = ArrayList(devices.value!!)
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S && ActivityCompat.checkSelfPermission(fragment.requireContext(), Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED) {
                                fragment.requestPermissions(arrayOf(Manifest.permission.BLUETOOTH_CONNECT), 1)
                                return
                            }
                            if(name != null) {
                                newDevices.forEach { existingDevice ->
                                    if (existingDevice.address == address)
                                        return
                                }
                                newDevices.add(DeviceModel(address, name, bondState == BluetoothDevice.BOND_BONDED))
                                devices.value = newDevices
                            }
                        }
                    }
                }
            }
            fragment.requireActivity().registerReceiver(receiver, IntentFilter(BluetoothDevice.ACTION_FOUND))
            isReceiverRegistered = true
        }
        adapter.cancelDiscovery()
        adapter.startDiscovery()
        devices.value = ArrayList()
        Toast.makeText(fragment.requireContext(), "Поиск устройств начался", Toast.LENGTH_SHORT).show()

    }

    fun stopDevicesScan(fragment: Fragment){
        if(isReceiverRegistered)
            fragment.requireContext().unregisterReceiver(receiver)
    }

    fun connectToDevice(fragment: Fragment, address: String) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S && ActivityCompat.checkSelfPermission(fragment.requireContext(), Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED) {
            fragment.requestPermissions(arrayOf(Manifest.permission.BLUETOOTH_CONNECT), 2)
            return
        }

        adapter = getSystemService(fragment.requireContext(), BluetoothManager::class.java)!!.adapter!!
        val device = adapter.getRemoteDevice(address)
        if(device.bondState == BluetoothDevice.BOND_NONE){
            device.createBond()
            return
        }
    }


}