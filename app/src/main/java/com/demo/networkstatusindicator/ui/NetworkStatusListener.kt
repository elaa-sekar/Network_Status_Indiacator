package com.demo.networkstatusindicator.ui

interface NetworkStatusListener {
    fun toggleWifi(isChecked: Boolean)
    fun toggleBlueTooth(isChecked: Boolean)
    fun showMessage(message: String)
}