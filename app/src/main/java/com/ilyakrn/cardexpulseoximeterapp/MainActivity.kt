package com.ilyakrn.cardexpulseoximeterapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.ilyakrn.cardexpulseoximeterapp.bluetooth.BluetoothDeviceThread
import com.ilyakrn.cardexpulseoximeterapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    var bluetoothDeviceThread: BluetoothDeviceThread? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.bottomNavView.setupWithNavController(findNavController(R.id.bottom_nav_host_fragment))

    }

}