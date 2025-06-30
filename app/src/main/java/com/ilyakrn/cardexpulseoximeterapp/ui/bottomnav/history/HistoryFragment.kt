package com.ilyakrn.cardexpulseoximeterapp.ui.bottomnav.history

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.ilyakrn.cardexpulseoximeterapp.databinding.FragmentNavDevicesBinding
import com.ilyakrn.cardexpulseoximeterapp.databinding.FragmentNavHistoryBinding
import com.ilyakrn.cardexpulseoximeterapp.models.DeviceModel
import com.ilyakrn.cardexpulseoximeterapp.models.MeasureModel
import com.ilyakrn.cardexpulseoximeterapp.ui.adapters.DeviceAdapter
import com.ilyakrn.cardexpulseoximeterapp.ui.adapters.MeasureAdapter
import com.ilyakrn.cardexpulseoximeterapp.ui.bottomnav.devices.DevicesViewModel

class HistoryFragment : Fragment() {

    private lateinit var binding: FragmentNavHistoryBinding
    private lateinit var viewModel: HistoryViewModel
    private lateinit var adapter: MeasureAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        viewModel = ViewModelProvider(this)[HistoryViewModel::class.java]
        binding = FragmentNavHistoryBinding.inflate(inflater, container, false)
        adapter = MeasureAdapter()
        binding.rvMeasures.layoutManager = LinearLayoutManager(activity)
        binding.rvMeasures.adapter = adapter
        viewModel.measures.observe(viewLifecycleOwner) { measures ->
            adapter.measures = measures
        }
        binding.btDelete.setOnClickListener {
            viewModel.deleteAllMeasures(requireContext())
        }
        viewModel.startMeasuresObserve(requireContext())

        return binding.root
    }
}