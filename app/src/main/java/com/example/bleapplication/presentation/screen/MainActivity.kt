package com.example.bleapplication.presentation.screen

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.core.app.ActivityCompat
import com.example.bleapplication.R
import com.example.bleapplication.presentation.screen.devices.DevicesFragmentPresenterStateHolder
import dagger.android.support.DaggerAppCompatActivity
import javax.inject.Inject

class MainActivity : DaggerAppCompatActivity() {

    companion object {
        private val PERMISSIONS = arrayOf(
            Manifest.permission.BLUETOOTH,
            Manifest.permission.BLUETOOTH_ADMIN,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION,
        )
        private const val PERMISSIONS_REQUEST_CODE = 1
    }

    @Inject
    lateinit var router: RouterImpl

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        router.activity = this
        setContentView(R.layout.activity_main)
        if (!hasPermissions())
            checkPermissions()
        if (savedInstanceState == null) {
            router.showDevicesFragment()
        }
    }

    private fun checkPermissions() {
        ActivityCompat.requestPermissions(
            this,
            PERMISSIONS,
            PERMISSIONS_REQUEST_CODE
        )
    }

    private fun hasPermissions(): Boolean {
        for (permission in PERMISSIONS) {
            if (ActivityCompat.checkSelfPermission(
                    this,
                    permission
                ) != PackageManager.PERMISSION_GRANTED
            )
                return false
        }
        return true
    }
}