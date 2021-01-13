package com.demo.networkstatusindicator.utils

import android.app.Activity
import android.app.Application
import android.bluetooth.BluetoothAdapter
import android.content.Context
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.net.LinkProperties
import android.net.Network
import android.net.NetworkCapabilities
import android.net.wifi.WifiManager
import android.os.Build
import android.os.Bundle
import timber.log.Timber

class NetworkStatusIndicator : Application() {

    lateinit var appContext: Context
    var networkReceiver: NetworkReceiver? = null
    lateinit var intentFilterNetwork: IntentFilter

    companion object {
        var networkAction: String = ConnectivityManager.CONNECTIVITY_ACTION
        var blueToothAction: String = BluetoothAdapter.ACTION_STATE_CHANGED
//        var wifiAction: String = WifiManager.WIFI_STATE_CHANGED_ACTION

        val filterActions = arrayOf(networkAction, blueToothAction)
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
                registerNetworkListener()
            }

            override fun onActivityStarted(activity: Activity) {

            }

            override fun onActivityResumed(activity: Activity) {
                networkReceiver?.let {
                    activity.registerReceiver(it, intentFilterNetwork)
                }
            }

            override fun onActivityPaused(activity: Activity) {
                networkReceiver?.let {
                    activity.unregisterReceiver(it)
                }

            }

            override fun onActivityStopped(activity: Activity) {

            }

            override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {

            }

            override fun onActivityDestroyed(activity: Activity) {

            }

        })
    }

    private fun registerNetworkListener() {
        if(Build.VERSION.SDK_INT <= Build.VERSION_CODES.P) {
            networkReceiver = NetworkReceiver()
        } else {
            val connectivityManager =
                getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager?
            connectivityManager?.registerDefaultNetworkCallback(object :
                ConnectivityManager.NetworkCallback() {
                override fun onAvailable(network: Network) {
                    super.onAvailable(network)
                    Timber.d("Network - On Available Called")
                }

                override fun onLosing(network: Network, maxMsToLive: Int) {
                    super.onLosing(network, maxMsToLive)
                    Timber.d("Network - On Losing Called")
                }

                override fun onUnavailable() {
                    super.onUnavailable()
                }

                override fun onCapabilitiesChanged(
                    network: Network,
                    networkCapabilities: NetworkCapabilities
                ) {
                    super.onCapabilitiesChanged(network, networkCapabilities)
                }

                override fun onLinkPropertiesChanged(
                    network: Network,
                    linkProperties: LinkProperties
                ) {
                    super.onLinkPropertiesChanged(network, linkProperties)
                }

                override fun onBlockedStatusChanged(network: Network, blocked: Boolean) {
                    super.onBlockedStatusChanged(network, blocked)
                }
            })
        }
    }

    private fun setContext(context: Context) {
        appContext = context
    }
}