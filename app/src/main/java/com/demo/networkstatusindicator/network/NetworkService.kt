package com.demo.networkstatusindicator.network

import android.app.Service
import android.content.Intent
import android.os.IBinder

class NetworkService : Service() {
    override fun onBind(intent: Intent?): IBinder? {
       return null
    }
}