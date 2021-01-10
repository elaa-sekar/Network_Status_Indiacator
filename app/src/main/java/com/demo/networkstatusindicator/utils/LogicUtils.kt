package com.demo.networkstatusindicator.utils

import android.bluetooth.BluetoothAdapter
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.net.wifi.WifiManager
import android.os.Build
import androidx.core.content.ContextCompat.getSystemService


fun isInternetAvailable(context: Context): Boolean {

    var result = false
    val connectivityManager =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager?

    connectivityManager?.let {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            it.getNetworkCapabilities(connectivityManager.activeNetwork)?.apply {
                result = when {
                    hasTransport(NetworkCapabilities.TRANSPORT_WIFI) || hasTransport(
                        NetworkCapabilities.TRANSPORT_CELLULAR
                    ) -> true
                    else -> false
                }
            }
        } else {
            it.activeNetworkInfo?.let { networkInfo ->
                result = when (networkInfo.type) {
                    ConnectivityManager.TYPE_WIFI, ConnectivityManager.TYPE_MOBILE -> true
                    else -> false
                }
            }
        }
    }
    return result
}

fun isWifiTurnedOn(context: Context): Boolean {
    val wifiManager =
        context.applicationContext.getSystemService(Context.WIFI_SERVICE) as WifiManager?
    wifiManager?.let {
        return it.isWifiEnabled
    }
    return false
}

fun isMobileDataTurnedOn(context: Context): Boolean {
    var result = false
    val connectivityManager =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager?

    connectivityManager?.let {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            it.getNetworkCapabilities(it.activeNetwork)?.apply {
                result = when {
                    hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                    else -> false
                }
            }
        } else {
            it.activeNetworkInfo?.let { networkInfo ->
                result = when (networkInfo.type) {
                    ConnectivityManager.TYPE_MOBILE -> true
                    else -> false
                }
            }
        }
    }
    return result
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





