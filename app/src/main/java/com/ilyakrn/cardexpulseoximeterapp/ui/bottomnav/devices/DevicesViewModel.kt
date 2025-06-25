package com.ilyakrn.cardexpulseoximeterapp.ui.bottomnav.devices

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class DevicesViewModel : ViewModel() {

    val text = MutableLiveData<ArrayList<String>>().apply {
        value = ArrayList()
    }
}