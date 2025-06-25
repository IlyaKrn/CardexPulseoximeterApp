package com.ilyakrn.cardexpulseoximeterapp.ui.bottomnav.history

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class HistoryViewModel : ViewModel() {

    val text = MutableLiveData<ArrayList<String>>().apply {
        value = ArrayList()
    }
}