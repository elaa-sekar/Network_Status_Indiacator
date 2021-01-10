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
import com.demo.networkstatusindicator.utils.enableOrDisableBlueTooth
import com.demo.networkstatusindicator.utils.isBlueToothTurnedOn
import com.demo.networkstatusindicator.utils.toast


class NetworkStatusActivity : AppCompatActivity(), NetworkStatusListener {

    lateinit var binding: ActivityNetworkStatusBinding
    lateinit var viewModel: NetworkStatusViewModel

    companion object {
        const val NETWORK_REQUEST_CODE = 120
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_network_status)
        viewModel = ViewModelProvider(this).get(NetworkStatusViewModel::class.java)
        binding.viewModel = viewModel
        viewModel.listener = this
        viewModel.updateNetWorkStatus()
    }

    override fun toggleWifi(isChecked: Boolean) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            val wifiPanelIntent = Intent(Settings.Panel.ACTION_WIFI)
            startActivityForResult(wifiPanelIntent, NETWORK_REQUEST_CODE)
        } else {
            val wifi = applicationContext.getSystemService(Context.WIFI_SERVICE) as WifiManager
            wifi.isWifiEnabled = isChecked
            viewModel.updateWifiStatusMessage(isChecked)
        }
    }

    override fun toggleBlueTooth(isChecked: Boolean) {
        enableOrDisableBlueTooth(isChecked)
        viewModel.updateBlueToothMessage(isChecked)
    }

    override fun showMessage(message: String) {
        toast(message)
    }

//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        Timber.d("Request Code $requestCode Result Code $resultCode Data $data")
//        if (requestCode == NETWORK_REQUEST_CODE) {
//            if (resultCode == Activity.RESULT_OK) {
//                viewModel.updateWifiStatusMessage(isWifiTurnedOn(this))
//            }
//        }
//        super.onActivityResult(requestCode, resultCode, data)
//    }
}