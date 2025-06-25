package com.ilyakrn.cardexpulseoximeterapp.ui.bottomnav.history

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.ilyakrn.cardexpulseoximeterapp.databinding.FragmentNavDevicesBinding
import com.ilyakrn.cardexpulseoximeterapp.databinding.FragmentNavHistoryBinding

class HistoryFragment : Fragment() {

    private lateinit var binding: FragmentNavHistoryBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val viewModel = ViewModelProvider(this)[HistoryViewModel::class.java]
        binding = FragmentNavHistoryBinding.inflate(inflater, container, false)
        val root: View = binding.root

        viewModel.text.observe(viewLifecycleOwner) {

        }
        return root
    }
}