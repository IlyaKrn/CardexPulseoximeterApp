package com.ilyakrn.cardexpulseoximeterapp.ui.bottomnav.history

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ilyakrn.cardexpulseoximeterapp.models.MeasureModel
import com.ilyakrn.cardexpulseoximeterapp.sqlite.SQLiteManager
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class HistoryViewModel : ViewModel() {

    val measures = MutableLiveData<ArrayList<MeasureModel>>().apply {
        value = ArrayList()
    }

    fun deleteAllMeasures(context: Context){
        SQLiteManager(context).deleteAllMeasures()
    }

    fun startMeasuresObserve(context: Context){
        viewModelScope.launch {
            while (true){
                SQLiteManager(context).apply {
                    measures.value = getMeasures()
                }
                delay(2000)
            }
        }
    }

}