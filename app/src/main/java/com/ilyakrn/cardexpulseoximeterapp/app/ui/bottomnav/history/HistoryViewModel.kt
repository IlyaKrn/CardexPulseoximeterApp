package com.ilyakrn.cardexpulseoximeterapp.app.ui.bottomnav.history

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ilyakrn.cardexpulseoximeterapp.data.repos.MeasureRepoImpl
import com.ilyakrn.cardexpulseoximeterapp.domain.models.MeasureModel
import com.ilyakrn.cardexpulseoximeterapp.domain.usecases.DeleteAllMeasuresUseCase
import com.ilyakrn.cardexpulseoximeterapp.domain.usecases.GetRecentMeasuresByFilterUseCase
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class HistoryViewModel : ViewModel() {

    val measures = MutableLiveData<ArrayList<MeasureModel>>().apply {
        value = ArrayList()
    }

    var updateMeasuresCoroutine: Job? = null

    fun deleteAllMeasures(context: Context){
        DeleteAllMeasuresUseCase(MeasureRepoImpl(context))
    }

    fun startMeasuresObserve(context: Context){
        updateMeasuresCoroutine?.cancel()
        updateMeasuresCoroutine = viewModelScope.launch {
            while (true){
                measures.value = GetRecentMeasuresByFilterUseCase(null, null, null, MeasureRepoImpl(context)).invoke()
                delay(2000)
            }
        }
    }

    fun stopMeasuresObserve(context: Context){
        updateMeasuresCoroutine?.cancel()
    }
}