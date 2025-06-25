package com.ilyakrn.cardexpulseoximeterapp.ui.bottomnav.devices

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.ilyakrn.cardexpulseoximeterapp.databinding.FragmentNavDevicesBinding

class DevicesFragment : Fragment() {

    private lateinit var binding: FragmentNavDevicesBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val viewModel = ViewModelProvider(this)[DevicesViewModel::class.java]
        binding = FragmentNavDevicesBinding.inflate(inflater, container, false)
        val root: View = binding.root

        viewModel.text.observe(viewLifecycleOwner) {

        }


        return root
    }
}