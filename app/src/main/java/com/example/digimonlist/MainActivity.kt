package com.example.digimonlist

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentTransaction

class MainActivity : AppCompatActivity(), ListFragment.OnDigimonSelectedListener {

    private lateinit var networkStatusTextView: TextView
    private lateinit var networkStatusReceiver: BroadcastReceiver
    private var isLandscape: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        networkStatusTextView = findViewById(R.id.networkStatusTextView)
        isLandscape = findViewById<View>(R.id.fragment_container_detail) != null

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, ListFragment())
                .commit()
        }

        networkStatusReceiver = object : BroadcastReceiver() {
            override fun onReceive(context: Context?, intent: Intent?) {
                val isConnected = intent?.getBooleanExtra("isConnected", true) ?: true
                if (isConnected) {
                    networkStatusTextView.text = ""
                } else {
                    networkStatusTextView.text = getString(R.string.network_offline)
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        val filter = IntentFilter("com.example.digimonlist.NETWORK_STATUS").apply {
            addCategory(Intent.CATEGORY_DEFAULT)
        }
        registerReceiver(networkStatusReceiver, filter, RECEIVER_NOT_EXPORTED)
    }

    override fun onPause() {
        super.onPause()
        unregisterReceiver(networkStatusReceiver)
    }

    override fun onDigimonSelected(digimon: Digimon) {
        if (isLandscape) {
            val detailFragment = DetailFragment.newInstance(digimon)
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container_detail, detailFragment)
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .commit()
        } else {
            val intent = Intent(this, DetailActivity::class.java).apply {
                putExtra("digimon", digimon)
            }
            startActivity(intent)
        }
    }
}
