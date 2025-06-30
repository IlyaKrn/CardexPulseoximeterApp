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

    lateinit var adapter: BluetoothAdapter

    private val receiver = object : BroadcastReceiver() {
        @SuppressLint("MissingPermission")
        override fun onReceive(context: Context, intent: Intent) {
            if (intent.action == BluetoothDevice.ACTION_FOUND) {
                intent.getParcelableExtra<BluetoothDevice>(BluetoothDevice.EXTRA_DEVICE)?.apply {
                    if(name != null) {
                        val newDevices = ArrayList(devices.value!!)
                        newDevices.add(DeviceModel(address, name, false))
                        devices.value = newDevices
                    }
                }
            }
        }
    }

    private var isReceiverRegistered = false
    private var isScan = false

    fun startDevicesScan(activity: Activity){
        adapter = getSystemService(activity, BluetoothManager::class.java)!!.adapter!!
        adapter.apply {
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.S &&
                ContextCompat.checkSelfPermission(activity, Manifest.permission.BLUETOOTH_SCAN) != PackageManager.PERMISSION_GRANTED
            ) {
                requestPermissions(
                    activity, arrayOf(
                        Manifest.permission.BLUETOOTH_SCAN,
                    ), 1
                )
                return
            }
            if(
                ContextCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED
            ) {
                requestPermissions(
                    activity, arrayOf(
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACCESS_COARSE_LOCATION
                    ), 1
                )
                return
            }

            if(!isReceiverRegistered) {
                activity.registerReceiver(receiver, IntentFilter(BluetoothDevice.ACTION_FOUND))
                isReceiverRegistered = true
            }
            devices.value = ArrayList()
            if(!isScan) {
                startDiscovery()
                Toast.makeText(activity, "Поиск устройств начался", Toast.LENGTH_SHORT).show()
                isScan = true
                viewModelScope.launch {
                    delay(15000)
                    isScan = false
                }
            } else {
                Toast.makeText(activity, "Поиск уже ведется", Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun stopDevicesScan(activity: Activity){
        if(isReceiverRegistered)
            activity.registerReceiver(receiver, IntentFilter(BluetoothDevice.ACTION_FOUND))
    }

    fun connectToDevice(activity: Activity, macAddress: String){
        ConnectThread(activity, macAddress).start()
    }


    private inner class ConnectThread(private val activity: Activity, private val address: String) : Thread() {

        private lateinit var socket: BluetoothSocket

        override fun run() {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S && ActivityCompat.checkSelfPermission(activity, Manifest.permission.BLUETOOTH_SCAN) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(
                    activity, arrayOf(
                        Manifest.permission.BLUETOOTH_SCAN
                    ), 1
                )
                return
            }

            socket = adapter.getRemoteDevice(address).createRfcommSocketToServiceRecord(UUID.randomUUID())
            adapter.cancelDiscovery()
            socket.connect()
//            manageMyConnectedSocket(socket)
        }

        fun cancel() {
            try {
                socket.close()
            } catch (e: IOException) {
                Log.e("BLUETOOTH", e.message!!)
            }
        }
    }


}