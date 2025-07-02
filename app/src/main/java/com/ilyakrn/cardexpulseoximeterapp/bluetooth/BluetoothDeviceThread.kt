package com.ilyakrn.cardexpulseoximeterapp.bluetooth

import android.Manifest
import android.R.attr.text
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothSocket
import android.util.Log
import androidx.annotation.RequiresPermission
import java.io.DataInputStream
import java.io.DataOutputStream
import java.io.IOException
import java.io.InputStream
import java.io.OutputStream
import java.util.Arrays
import java.util.UUID


class BluetoothDeviceThread(private val adapter: BluetoothAdapter, private val deviceAddress: String) : Thread() {

    private lateinit var socket: BluetoothSocket

    @RequiresPermission(allOf = [Manifest.permission.BLUETOOTH_SCAN, Manifest.permission.BLUETOOTH_CONNECT])
    override fun run() {
        adapter.cancelDiscovery()
        val device = adapter.getRemoteDevice(deviceAddress)

        device.uuids?.let { uuids ->
            for (i in 0..uuids.size){
                try {
                    socket = device.createRfcommSocketToServiceRecord(UUID.fromString(uuids[i].toString()))
                    socket.connect()
                    break
                } catch (e: Exception){}
            }
        }
        if(socket.isConnected){
            socket.outputStream.write(byteArrayOf(12))
        }
    }

    fun cancel() {
        try {
            socket.close()
        } catch (e: IOException) {}
    }
}