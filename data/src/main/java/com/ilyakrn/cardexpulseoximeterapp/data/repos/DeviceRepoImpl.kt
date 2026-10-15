package com.ilyakrn.cardexpulseoximeterapp.data.repos

import android.Manifest
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import androidx.core.content.ContextCompat.getSystemService
import android.util.Log
import androidx.annotation.RequiresPermission
import androidx.core.app.ActivityCompat
import com.ilyakrn.cardexpulseoximeterapp.data.bluetooth.BluetoothDeviceThread
import com.ilyakrn.cardexpulseoximeterapp.domain.models.DeviceModel
import com.ilyakrn.cardexpulseoximeterapp.domain.models.MeasureModel
import com.ilyakrn.cardexpulseoximeterapp.domain.repos.DeviceRepo

class DeviceRepoImpl(private val context: Context, private val onPermissionNotGranted: (String) -> Unit, private val onBluetoothNotEnabled: () -> Unit) : DeviceRepo {

    companion object {
        var bluetoothDeviceThread: BluetoothDeviceThread? = null
        private lateinit var adapter: BluetoothAdapter
        private lateinit var receiver: BroadcastReceiver
        private var isReceiverRegistered = false
    }

    override fun findDevices(onDeviceFound: (DeviceModel) -> Unit) {
        adapter = getSystemService(context, BluetoothManager::class.java)!!.adapter!!
        if(!adapter.isEnabled){
            Log.w(this.javaClass.name, "finding bluetooth devices failed: bluetooth not enabled")
            onBluetoothNotEnabled()
            return
        }
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            onPermissionNotGranted(Manifest.permission.ACCESS_FINE_LOCATION)
            return
        }
        if(isReceiverRegistered) {
            context.unregisterReceiver(receiver)
            Log.i(this.javaClass.name, "receiver unregistered")
        }
        receiver = object : BroadcastReceiver() {
            override fun onReceive(context: Context, intent: Intent) {
                if (intent.action == BluetoothDevice.ACTION_FOUND) {
                    intent.getParcelableExtra<BluetoothDevice>(BluetoothDevice.EXTRA_DEVICE)?.apply {
                        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED) {
                            onPermissionNotGranted(Manifest.permission.BLUETOOTH_CONNECT)
                            return
                        }
                        Log.i(this.javaClass.name, "bluetooth device found, deviceMacAddress: $address, name: $name, bondState: $bondState")
                        onDeviceFound(DeviceModel(address, name, bondState == BluetoothDevice.BOND_BONDED, false))
                    }
                }
            }
        }
        context.registerReceiver(receiver, IntentFilter(BluetoothDevice.ACTION_FOUND))
        Log.i(this.javaClass.name, "receiver registered")
        isReceiverRegistered = true
        adapter.cancelDiscovery()
        Log.i(this.javaClass.name, "bluetooth devices discovery cancelled")
        adapter.startDiscovery()
        Log.i(this.javaClass.name, "bluetooth devices discovery started")
    }

    override fun getConnectionAvailableStatus(): Boolean {
        return getSystemService(context, BluetoothManager::class.java)!!.adapter!!.isEnabled
    }

    override fun stopFindingDevices() {
        if(isReceiverRegistered) {
            context.unregisterReceiver(receiver)
            Log.i(this.javaClass.name, "receiver unregistered")
        }
    }

    override fun connect(deviceMacAddress: String, onReadMeasure: (MeasureModel) -> Unit) {

        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED) {
            onPermissionNotGranted(Manifest.permission.BLUETOOTH_CONNECT)
            return
        }


        adapter = getSystemService(context, BluetoothManager::class.java)!!.adapter!!
        if(!adapter.isEnabled){
            Log.w(this.javaClass.name, "finding bluetooth devices failed: bluetooth not enabled")
            onBluetoothNotEnabled()
            return
        }

        val device = adapter.getRemoteDevice(deviceMacAddress)
        if(device.bondState == BluetoothDevice.BOND_NONE){
            Log.i(this.javaClass.name, "trying to create bound with device, deviceMacAddress: ${device.address}")
            device.createBond()
        }
        if(device.bondState == BluetoothDevice.BOND_BONDED){
            Log.i(this.javaClass.name, "created bound with device, deviceMacAddress: ${device.address}")
            bluetoothDeviceThread?.isRunning = false
            bluetoothDeviceThread = BluetoothDeviceThread(adapter, deviceMacAddress, onReadMeasure)
            bluetoothDeviceThread!!.start()
        }
    }

    override fun disconnect() {
        Log.i(this.javaClass.name, "closing bluetooth connection if exists")
        bluetoothDeviceThread?.cancel()
    }

}