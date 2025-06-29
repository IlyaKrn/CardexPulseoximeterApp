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

class HistoryFragment : Fragment() {

    private lateinit var binding: FragmentNavHistoryBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val viewModel = ViewModelProvider(this)[HistoryViewModel::class.java]
        binding = FragmentNavHistoryBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val adapter = MeasureAdapter()
        adapter.measures = arrayListOf(MeasureModel("mac:mac", "name1", System.currentTimeMillis(), 80, 98), MeasureModel("mac:mac", "name1", System.currentTimeMillis(), 80, 98), MeasureModel("mac:mac", "name1", System.currentTimeMillis(), 80, 98), MeasureModel("mac:mac", "name1", System.currentTimeMillis(), 80, 98), MeasureModel("mac:mac", "name1", System.currentTimeMillis(), 80, 98), )
        adapter.onClickListener = { measure ->
            Toast.makeText(context, measure.timestamp.toString(), Toast.LENGTH_SHORT).show()
        }
        binding.rvMeasures.layoutManager = LinearLayoutManager(activity)
        binding.rvMeasures.adapter = adapter



        viewModel.text.observe(viewLifecycleOwner) {

        }


        return root
    }
}