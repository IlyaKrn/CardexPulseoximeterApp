package com.ilyakrn.cardexpulseoximeterapp.ui.bottomnav.devices

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Adapter
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.ilyakrn.cardexpulseoximeterapp.databinding.FragmentNavDevicesBinding
import com.ilyakrn.cardexpulseoximeterapp.models.DeviceModel
import com.ilyakrn.cardexpulseoximeterapp.ui.adapters.DeviceAdapter

class DevicesFragment : Fragment() {

    private lateinit var binding: FragmentNavDevicesBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val viewModel = ViewModelProvider(this)[DevicesViewModel::class.java]
        binding = FragmentNavDevicesBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val adapter = DeviceAdapter(arrayListOf(DeviceModel("mac:mac", "name1", 5), DeviceModel("mac:mac", "name1", 5), DeviceModel("mac:mac", "name1", 5), DeviceModel("mac:mac", "name1", 5), DeviceModel("mac:mac", "name1", 5), DeviceModel("mac:mac", "name1", 5), DeviceModel("mac:mac", "name1", 5), DeviceModel("mac:mac", "name1", 5), DeviceModel("mac:mac", "name1", 5), DeviceModel("mac:mac", "name1", 5), DeviceModel("mac:mac", "name1", 5), DeviceModel("mac:mac", "name1", 5), DeviceModel("mac:mac", "name1", 5), DeviceModel("mac:mac", "name1", 5), DeviceModel("mac:mac", "name1", 5), DeviceModel("mac:mac", "name1", 5), DeviceModel("mac:mac", "name1", 5), DeviceModel("mac:mac", "name1", 5), DeviceModel("mac:mac", "name1", 5), DeviceModel("mac:mac", "name1", 5), DeviceModel("mac:mac", "name1", 5), DeviceModel("mac:mac", "name1", 5), DeviceModel("mac:mac", "name1", 5), DeviceModel("mac:mac", "name1", 5), DeviceModel("mac:mac", "name1", 5), DeviceModel("mac:mac", "name1", 5), DeviceModel("mac:mac", "name1", 5), DeviceModel("mac:mac", "name1", 5), DeviceModel("mac:mac", "name1", 5), DeviceModel("mac:mac", "name1", 5), DeviceModel("mac:mac", "name1", 5), DeviceModel("mac:mac", "name1", 5), DeviceModel("mac:mac", "name1", 5), ))
        binding.rvDevices.layoutManager = LinearLayoutManager(activity)
        binding.rvDevices.adapter = adapter



        viewModel.text.observe(viewLifecycleOwner) {

        }


        return root
    }
}