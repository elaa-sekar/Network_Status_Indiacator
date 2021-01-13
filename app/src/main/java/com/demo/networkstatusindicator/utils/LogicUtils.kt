package com.demo.networkstatusindicator.utils

import android.bluetooth.BluetoothAdapter
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build


fun isInternetAvailable(context: Context): Boolean {
    return isWifiTurnedOn(context) || isMobileDataTurnedOn(context)
}

fun isWifiTurnedOn(context: Context): Boolean {

    val connectivityManager =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager?

    connectivityManager?.let {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            it.getNetworkCapabilities(it.activeNetwork)?.apply {
                return when {
                    hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                    else -> false
                }
            }
        } else {
            it.activeNetworkInfo?.let { networkInfo ->
                return when (networkInfo.type) {
                    ConnectivityManager.TYPE_WIFI -> true
                    else -> false
                }
            }
        }
    }
    return false
}

fun isMobileDataTurnedOn(context: Context): Boolean {

    val connectivityManager =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager?

    connectivityManager?.let {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            it.getNetworkCapabilities(it.activeNetwork)?.apply {
                return when {
                    hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                    else -> false
                }
            }
        } else {
            it.activeNetworkInfo?.let { networkInfo ->
                return when (networkInfo.type) {
                    ConnectivityManager.TYPE_MOBILE -> true
                    else -> false
                }
            }
        }
    }
    return false
}

fun isBlueToothTurnedOn(): Boolean {
    return BluetoothAdapter.getDefaultAdapter().isEnabled
}

fun enableOrDisableBlueTooth(isChecked: Boolean) {
    val bluetoothAdapter = BluetoothAdapter.getDefaultAdapter()
    if (isChecked) {
        if (!isBlueToothTurnedOn()) bluetoothAdapter.enable()
    } else if (isBlueToothTurnedOn()) bluetoothAdapter.disable()
}






