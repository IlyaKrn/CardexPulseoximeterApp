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

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        viewModel = ViewModelProvider(this)[DevicesViewModel::class.java]
        binding = FragmentNavDevicesBinding.inflate(inflater, container, false)

        val adapter = DeviceAdapter()
        binding.rvDevices.layoutManager = LinearLayoutManager(activity)
        binding.rvDevices.adapter = adapter
        binding.btReload.setOnClickListener {
            viewModel.updateDevices()
        }

        viewModel.devices.observe(viewLifecycleOwner) { devices ->
            adapter.devices = devices
        }
        viewModel.enableBluetooth(this)
        viewModel.getDevices(requireContext())

        return binding.root
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if(resultCode == RESULT_OK) {
            when (requestCode) {
                1 -> viewModel.getDevices(requireContext())
            }
        }
        super.onActivityResult(requestCode, resultCode, data)
    }
}