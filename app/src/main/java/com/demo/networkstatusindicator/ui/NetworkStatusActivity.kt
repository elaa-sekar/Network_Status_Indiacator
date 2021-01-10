package com.demo.networkstatusindicator.ui

import android.bluetooth.BluetoothAdapter
import android.content.Context
import android.content.Intent
import android.net.wifi.WifiManager
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.demo.networkstatusindicator.R
import com.demo.networkstatusindicator.databinding.ActivityNetworkStatusBinding
import com.demo.networkstatusindicator.utils.toast


class NetworkStatusActivity : AppCompatActivity(), NetworkStatusListener {

    lateinit var binding: ActivityNetworkStatusBinding
    lateinit var viewModel: NetworkStatusViewModel

    companion object{
        const val NETWORK_REQUEST_CODE = 120
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_network_status)
        viewModel = ViewModelProvider(this).get(NetworkStatusViewModel::class.java)
        binding.viewModel = viewModel
        viewModel.listener = this
    }

    override fun toggleWifi(isChecked: Boolean) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            val panelIntent = Intent(Settings.Panel.ACTION_INTERNET_CONNECTIVITY)
            startActivityForResult(panelIntent, NETWORK_REQUEST_CODE)
        } else {
            val wifi = applicationContext.getSystemService(Context.WIFI_SERVICE) as WifiManager
            wifi.isWifiEnabled = isChecked
            viewModel.updateWifiStatusMessage(isChecked)
        }
    }

    override fun toggleBlueTooth(isChecked: Boolean) {
        val bluetoothAdapter = BluetoothAdapter.getDefaultAdapter()
        if (isChecked) {
            if (!bluetoothAdapter.isEnabled) bluetoothAdapter.enable()
        } else if (bluetoothAdapter.isEnabled) bluetoothAdapter.disable()
        viewModel.updateBlueToothMessage(isChecked)
    }

    override fun showMessage(message: String) {
        toast(message)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == NETWORK_REQUEST_CODE){

        }
    }
}