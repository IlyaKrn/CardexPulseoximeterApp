package com.ilyakrn.cardexpulseoximeterapp.bluetooth

import android.Manifest
import android.R.attr.text
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothSocket
import android.util.Log
import androidx.annotation.RequiresPermission
import com.ilyakrn.cardexpulseoximeterapp.models.MeasureModel
import java.io.DataInputStream
import java.io.DataOutputStream
import java.io.IOException
import java.io.InputStream
import java.io.OutputStream
import java.util.Arrays
import java.util.UUID


class BluetoothDeviceThread(private val adapter: BluetoothAdapter, private val deviceAddress: String, private val onReadMeasure: (MeasureModel) -> Unit) : Thread() {

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
            socket.inputStream.apply {
                while (true){
                    try {
                        //read DATA1,
                        var buffer = ""
                        while (!buffer.endsWith("DATA1,")){
                            buffer += read().toChar()
                        }
                        //read size
                        buffer = ""
                        while (!buffer.endsWith(",")){
                            buffer += read().toChar()
                        }
                        val dataSize = buffer.substring(0, buffer.length - 1).toInt() + 5
                        val dataBytesBuffer = ByteArray(dataSize)
                        read(dataBytesBuffer, 0, dataSize)
                        buffer = ""
                        dataBytesBuffer.forEach { buffer += it.toChar() }

                        val args = buffer.split(",")
                        if(args.size != 6)
                            continue
                        onReadMeasure.invoke(MeasureModel(0, device.address, device.name, args[2], args[4].toInt(), args[5].toInt()))
                    } catch(e : Exception){}
                }

            }
        }
    }

    fun cancel() {
        try {
            socket.close()
        } catch (e: IOException) {}
    }
}