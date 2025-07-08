package com.ilyakrn.cardexpulseoximeterapp.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.ilyakrn.cardexpulseoximeterapp.databinding.ItemDeviceBinding
import com.ilyakrn.cardexpulseoximeterapp.databinding.ItemMeasureBinding
import com.ilyakrn.cardexpulseoximeterapp.models.DeviceModel
import com.ilyakrn.cardexpulseoximeterapp.models.MeasureModel

class MeasureAdapter() : RecyclerView.Adapter<MeasureAdapter.Holder>() {

    var measures = ArrayList<MeasureModel>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    var onClickListener: (measureModel: MeasureModel) -> Unit = {}
        set(value) {
            field = value
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        return Holder(ItemMeasureBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(measures[position], onClickListener)
    }

    override fun getItemCount(): Int {
        return measures.size
    }

    class Holder(private val binding: ItemMeasureBinding) : ViewHolder(binding.root) {
        fun bind(measure: MeasureModel, onClickListener: (measures: MeasureModel) -> Unit) {
            binding.deviceName.text = measure.deviceName
            binding.timestamp.text = measure.timestamp
            binding.valueSPO2.text = measure.SpO2.toString()
            binding.valuePR.text = measure.PR.toString()

            binding.root.setOnClickListener {
                onClickListener.invoke(measure)
            }
        }
    }

}