package com.ilyakrn.cardexpulseoximeterapp.app.ui.bottomnav.history

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.ilyakrn.cardexpulseoximeterapp.app.databinding.FragmentNavHistoryBinding
import com.ilyakrn.cardexpulseoximeterapp.app.ui.adapters.MeasureAdapter

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

    override fun onPause() {
        super.onPause()
        viewModel.stopMeasuresObserve(requireContext())
    }
}