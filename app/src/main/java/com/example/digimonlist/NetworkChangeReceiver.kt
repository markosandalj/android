package com.example.digimonlist

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkInfo

class NetworkChangeReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork: NetworkInfo? = cm.activeNetworkInfo
        val isConnected: Boolean = activeNetwork?.isConnectedOrConnecting == true

        val networkIntent = Intent("com.example.digimonlist.NETWORK_STATUS").apply {
            putExtra("isConnected", isConnected)
            addCategory(Intent.CATEGORY_DEFAULT)
        }
        context.sendBroadcast(networkIntent)
    }
}
