package com.demo.networkstatusindicator.utils

import android.bluetooth.BluetoothAdapter
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.wifi.WifiManager
import timber.log.Timber

class NetworkReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
        Timber.d("On Receive Called")
        context?.let {
            intent?.action?.let { action ->
                when (action) {
                    WifiManager.WIFI_STATE_CHANGED_ACTION -> {
                        it.toast(if (isWifiTurnedOn(it)) WIFI_TURNED_ON else WIFI_TURNED_OFF)
                    }
                    ConnectivityManager.CONNECTIVITY_ACTION -> {
                        it.toast(if (isMobileDataTurnedOn(it)) MOBILE_DATA_TURNED_ON else MOBILE_DATA_TURNED_OFF)
                    }
                    BluetoothAdapter.ACTION_STATE_CHANGED -> {
                        val state: Int = intent.getIntExtra(
                            BluetoothAdapter.EXTRA_STATE,
                            BluetoothAdapter.ERROR
                        )
                        if (state == BluetoothAdapter.STATE_ON) {
                            it.toast(BLUETOOTH_TURNED_ON)
                        } else if (state == BluetoothAdapter.STATE_OFF) {
                            it.toast(BLUETOOTH_TURNED_OFF)
                        }
                    }
                    else -> {
                        // Does nothing
                    }
                }
            }
        }
    }
}