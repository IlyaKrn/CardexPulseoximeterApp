package com.ilyakrn.cardexpulseoximeterapp.ui.bottomnav.devices

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.ilyakrn.cardexpulseoximeterapp.databinding.FragmentNavDevicesBinding
import com.ilyakrn.cardexpulseoximeterapp.ui.adapters.DeviceAdapter

class DevicesFragment : Fragment() {

    private lateinit var binding: FragmentNavDevicesBinding
    private lateinit var viewModel: DevicesViewModel
    private lateinit var adapter: DeviceAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        viewModel = ViewModelProvider(this)[DevicesViewModel::class.java]
        binding = FragmentNavDevicesBinding.inflate(inflater, container, false)
        adapter = DeviceAdapter()
        binding.rvDevices.layoutManager = LinearLayoutManager(activity)
        binding.rvDevices.adapter = adapter
        viewModel.devices.observe(viewLifecycleOwner) { devices ->
            adapter.devices = devices
        }
        adapter.onClickListener = { device ->
            viewModel.connectToDevice(this, device.address)
        }
        binding.btReload.setOnClickListener {
            viewModel.startDevicesScan(this)
        }
        viewModel.enableBluetooth(this)
        viewModel.startDevicesScan(this)

        return binding.root
    }

    override fun onDestroy() {
        viewModel.stopDevicesScan(this)
        super.onDestroy()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when(requestCode){
            1 -> {
                if(resultCode == RESULT_OK)
                    viewModel.startDevicesScan(this)
            }
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when(requestCode){
            1 -> {
                grantResults.forEach {
                    if(it != 0)
                        return
                }
                viewModel.startDevicesScan(this)
            }
        }
    }
}