package com.ilyakrn.cardexpulseoximeterapp.bluetooth

import android.Manifest
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothSocket
import android.util.Log
import androidx.annotation.RequiresPermission
import com.ilyakrn.cardexpulseoximeterapp.models.MeasureModel
import java.io.IOException
import java.util.UUID

class BluetoothDeviceThread(private val adapter: BluetoothAdapter, private val deviceAddress: String, private val onReadMeasure: (MeasureModel) -> Unit) : Thread() {

    private lateinit var socket: BluetoothSocket
    var isRunning = true
    var isConnected: Boolean? = null

    @RequiresPermission(allOf = [Manifest.permission.BLUETOOTH_SCAN, Manifest.permission.BLUETOOTH_CONNECT])
    override fun run() {
        try {
            Log.i(this.javaClass.name, "socket thread started (trying mac '$deviceAddress')")
            if (adapter.cancelDiscovery())
                Log.i(this.javaClass.name, "discovery cancelled successful")
            else
                Log.w(this.javaClass.name, "discovery not canceled")

            val device = adapter.getRemoteDevice(deviceAddress)
            Log.i(this.javaClass.name, "trying set connection with device '${device.name}', mac: '${device.address}', state: '${device.bondState}'")

            device.uuids?.let { uuids ->
                for (i in 0..uuids.size){
                    try {
                        Log.i(this.javaClass.name, "${device.address}: trying device uuid '${uuids[i]}'")
                        socket = device.createRfcommSocketToServiceRecord(UUID.fromString(uuids[i].toString()))
                        socket.connect()
                        Log.i(this.javaClass.name, "${device.address}: connection with uuid '${uuids[i]}' successful")
                        break
                    } catch (e: Exception){
                        Log.w(this.javaClass.name, "${device.address}: failed to connect with uuid '${uuids[i]}'")
                    }
                }
            }
            if(socket.isConnected){
                isConnected = true
                socket.inputStream.apply {
                    Log.i(this.javaClass.name, "${device.address}: start listening socket")
                    while (isRunning){
                        try {
                            //read DATA1,
                            var buffer = ""
                            while (!buffer.endsWith("DATA1,") && isRunning){
                                buffer += read().toChar()
                            }
                            Log.i(this.javaClass.name, "${device.address}: 'DATA1,' readed, trying to read data size")
                            //read size
                            buffer = ""
                            while (!buffer.endsWith(",") && isRunning){
                                buffer += read().toChar()
                            }
                            if(!isRunning)
                                break
                            val dataSize = buffer.substring(0, buffer.length - 1).toInt() + 5
                            Log.i(this.javaClass.name, "${device.address}: data size read ($dataSize), trying to read args")
                            val dataBytesBuffer = ByteArray(dataSize)
                            read(dataBytesBuffer, 0, dataSize)
                            buffer = ""
                            dataBytesBuffer.forEach { buffer += it.toChar() }

                            val args = buffer.split(",")
                            if(args.size != 6) {
                                Log.w(this.javaClass.name, "${device.address}: invalid args read, listening continued")
                                continue
                            }
                            Log.i(this.javaClass.name, "${device.address}: start measure callback")
                            onReadMeasure.invoke(MeasureModel(0, device.address, device.name, args[2], args[4].toInt(), args[5].toInt()))
                        } catch(e : Exception){
                            Log.w(this.javaClass.name, "${device.address}: error parsing socket input data, listening continued")
                        }
                    }

                }
            }
            else{
                Log.e(this.javaClass.name, "failed to connect to device '${device.name}', mac: '${device.address}'")
            }
        } catch (e: IllegalStateException){
            Log.e(this.javaClass.name, "failed to connect to device: invalid mac '$deviceAddress'")
        }catch (e: Exception){
            e.printStackTrace()
        }
        isConnected = false
        isRunning = false
    }

    fun cancel() {
        try {
            Log.w(this.javaClass.name, "$deviceAddress: bluetooth socket closed")
            isConnected = false
            isRunning = false
            socket.close()
        } catch (e: IOException) {
            Log.w(this.javaClass.name, "$deviceAddress: failed to close socket: ${e.message}")
        }
    }
}