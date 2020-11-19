package com.example.bleapplication.presentation.ui

import android.Manifest
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.navigation.Navigation
import com.example.bleapplication.R
import dagger.android.DaggerActivity
import dagger.android.support.DaggerAppCompatActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        checkPermissions()
        Navigation.findNavController(findViewById(R.id.navhost)).navigate(R.id.devicesFragment)
    }

    private fun checkPermissions() {
        ActivityCompat.requestPermissions(
            this,
            arrayOf(
                Manifest.permission.BLUETOOTH_ADMIN,
                Manifest.permission.BLUETOOTH,
                Manifest.permission.ACCESS_FINE_LOCATION
            ),
            1
        )
    }
}