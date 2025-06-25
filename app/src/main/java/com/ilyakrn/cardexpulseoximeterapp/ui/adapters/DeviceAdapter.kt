package com.ilyakrn.cardexpulseoximeterapp.ui.adapters

import android.annotation.SuppressLint
import android.bluetooth.BluetoothClass.Device
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.ilyakrn.cardexpulseoximeterapp.databinding.ItemDeviceBinding
import com.ilyakrn.cardexpulseoximeterapp.models.DeviceModel
import com.ilyakrn.cardexpulseoximeterapp.ui.bottomnav.devices.DevicesViewModel

class DeviceAdapter : RecyclerView.Adapter<DeviceAdapter.Holder>() {

    private var devices = ArrayList<DevicesViewModel>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        return Holder(ItemDeviceBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
    }

    override fun getItemCount(): Int {
        return devices.size
    }

    class Holder(private val binding: ItemDeviceBinding) : ViewHolder(binding.root) {

    }

}