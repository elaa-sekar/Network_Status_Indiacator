package com.demo.networkstatusindicator.ui

import android.widget.CompoundButton
import androidx.databinding.ObservableField
import androidx.lifecycle.ViewModel

class NetworkStatusViewModel : ViewModel() {

    // Listener
    var listener: NetworkStatusListener? = null

    // Data
    var wifiStatus = ObservableField<String>()
    var mobileDataStatus = ObservableField<String>()
    var bluetoothStatus = ObservableField<String>()

    // On Click Methods
    fun onWifiSwitchToggled(compoundButton: CompoundButton, isChecked: Boolean) {
        listener?.toggleWifi(isChecked)
    }

    fun onBlueToothSwitchToggled(compoundButton: CompoundButton, isChecked: Boolean) {
        listener?.toggleBlueTooth(isChecked)
    }

    fun updateWifiStatusMessage(isTurnedOn: Boolean) {
        val wifiStatusMessage = "Wi-Fi is turned ${
            if (isTurnedOn) "ON" else "OFF"
        }"
        wifiStatus.set(wifiStatusMessage)
        listener?.showMessage(wifiStatusMessage)
    }

    fun updateBlueToothMessage(isTurnedOn: Boolean){
        val blueToothStatusMessage = "Bluetooth is turned ${
            if (isTurnedOn) "ON" else "OFF"
        }"
        bluetoothStatus.set(blueToothStatusMessage)
        listener?.showMessage(blueToothStatusMessage)
    }
}