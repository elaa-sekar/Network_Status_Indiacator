package com.demo.networkstatusindicator.ui

import androidx.databinding.ObservableField
import androidx.lifecycle.ViewModel

class NetworkStatusViewModel: ViewModel() {

    // Data
    var wifiStatus = ObservableField<String>()
    var mobileDataStatus = ObservableField<String>()
    var bluetoothStatus = ObservableField<String>()
}