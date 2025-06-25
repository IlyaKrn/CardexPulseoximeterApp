package com.ilyakrn.cardexpulseoximeterapp.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.ilyakrn.cardexpulseoximeterapp.databinding.ItemDeviceBinding
import com.ilyakrn.cardexpulseoximeterapp.models.DeviceModel
import com.ilyakrn.cardexpulseoximeterapp.ui.bottomnav.devices.DevicesViewModel

class DeviceAdapter(devices: ArrayList<DeviceModel>) : RecyclerView.Adapter<DeviceAdapter.Holder>() {

    var devices = devices
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        return Holder(ItemDeviceBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(devices[position])
    }

    override fun getItemCount(): Int {
        return devices.size
    }

    class Holder(private val binding: ItemDeviceBinding) : ViewHolder(binding.root) {

        fun bind(device: DeviceModel) {
            binding.deviceName.text = device.name
            binding.root.setOnClickListener {
                val toast: Toast = Toast.makeText(it.context, "Device '" + device.name + "' selected", Toast.LENGTH_LONG)
                toast.show()
            }
        }
    }

}