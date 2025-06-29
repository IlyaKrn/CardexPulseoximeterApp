package com.ilyakrn.cardexpulseoximeterapp.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.GONE
import androidx.recyclerview.widget.RecyclerView.VISIBLE
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.ilyakrn.cardexpulseoximeterapp.databinding.ItemDeviceBinding
import com.ilyakrn.cardexpulseoximeterapp.models.DeviceModel

class DeviceAdapter() : RecyclerView.Adapter<DeviceAdapter.Holder>() {

    var devices = ArrayList<DeviceModel>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    var onClickListener: (device: DeviceModel) -> Unit = {}
        set(value) {
            field = value
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        return Holder(ItemDeviceBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(devices[position], onClickListener)
    }

    override fun getItemCount(): Int {
        return devices.size
    }

    class Holder(private val binding: ItemDeviceBinding) : ViewHolder(binding.root) {
        fun bind(device: DeviceModel, onClickListener: (device: DeviceModel) -> Unit) {
            binding.deviceName.text = device.name
            binding.isConnected.visibility = if (device.isConnected) VISIBLE else GONE
            binding.root.setOnClickListener {
                onClickListener.invoke(device)
            }
        }
    }

}