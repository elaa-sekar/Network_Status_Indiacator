package com.demo.networkstatusindicator.utils

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

class NetworkReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
        context?.let {
            if (!isInternetAvailable(it)) {

            } else {

            }
        }
    }
}