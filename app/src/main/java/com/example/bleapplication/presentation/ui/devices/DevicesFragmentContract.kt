package com.example.bleapplication.presentation.ui.devices

import com.example.bleapplication.model.BleDevice

interface DevicesFragmentContract {
    interface Ui {
        fun addDevice(bleDevice: BleDevice)
        fun openDeviceDetails()
    }

    interface Presenter {
        fun send(dleDevice: BleDevice)
    }
}