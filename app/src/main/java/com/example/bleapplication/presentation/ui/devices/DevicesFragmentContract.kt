package com.example.bleapplication.presentation.ui.devices

import com.example.bleapplication.model.BleDevice

interface DevicesFragmentContract {
    interface Ui {
        fun addDevice(bleDevice: BleDevice)
        fun showDeviceDetails()
        fun showConnectionError()
    }

    interface Presenter {
    }
}