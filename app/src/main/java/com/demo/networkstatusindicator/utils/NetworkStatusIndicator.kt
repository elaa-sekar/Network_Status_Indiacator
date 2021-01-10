package com.demo.networkstatusindicator.utils

import android.app.Activity
import android.app.Application
import android.bluetooth.BluetoothAdapter
import android.content.Context
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.net.wifi.WifiManager
import android.os.Bundle

class NetworkStatusIndicator : Application() {

    lateinit var appContext: Context
    lateinit var networkReceiver: NetworkReceiver
    lateinit var intentFilterNetwork: IntentFilter

    companion object {
        var networkAction: String = ConnectivityManager.CONNECTIVITY_ACTION
        var blueToothAction: String = BluetoothAdapter.ACTION_STATE_CHANGED
        var wifiAction: String = WifiManager.WIFI_STATE_CHANGED_ACTION

        val filterActions = arrayOf(networkAction, blueToothAction, wifiAction)
        lateinit var applicationInstance: NetworkStatusIndicator
    }

    override fun onCreate() {
        super.onCreate()
        applicationInstance = this
        setContext(applicationContext)
        intentFilterNetwork = IntentFilter()
        for (item in filterActions) intentFilterNetwork.addAction(item)

        registerActivityLifecycleCallbacks(object : ActivityLifecycleCallbacks {
            override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
                networkReceiver = NetworkReceiver()
            }

            override fun onActivityStarted(activity: Activity) {

            }

            override fun onActivityResumed(activity: Activity) {
                activity.registerReceiver(networkReceiver, intentFilterNetwork)
            }

            override fun onActivityPaused(activity: Activity) {
                activity.unregisterReceiver(networkReceiver)
            }

            override fun onActivityStopped(activity: Activity) {

            }

            override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {

            }

            override fun onActivityDestroyed(activity: Activity) {

            }

        })
    }

    private fun setContext(context: Context) {
        appContext = context
    }
}