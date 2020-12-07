package com.example.bleapplication.presentation.screen.devices

import com.example.bleapplication.model.BleDevice

interface DevicesFragmentContract {
    interface Ui {
        fun addDevice(bleDevice: BleDevice)
        fun showButtons(isScanning: Boolean)
        fun setToolbarTitle(title: String?)
        fun showLoading(showLoading: Boolean, showCancelIcon: Boolean)
        fun showConnectionError()
        fun showBluetoothConnectionError()
    }

    interface Presenter {
        fun scan()
        fun stopScan()
        fun connect(device: BleDevice)
        fun disconnect()
    }
}