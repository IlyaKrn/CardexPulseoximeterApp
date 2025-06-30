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

        }
        binding.btReload.setOnClickListener {

        }

        return binding.root
    }
}