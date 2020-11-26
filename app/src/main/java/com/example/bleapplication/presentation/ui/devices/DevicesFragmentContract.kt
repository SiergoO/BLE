package com.example.bleapplication.presentation.ui.devices

import com.example.bleapplication.model.BleDevice

interface DevicesFragmentContract {
    interface Ui {
        fun addDevice(bleDevice: BleDevice)
        fun showDeviceDetails()
        fun showConnectionError()
        fun showButtons(isScanning: Boolean)
        fun setToolbarTitle(title: String?)
        fun showLoading(showLoading: Boolean, showCancelIcon: Boolean)
    }

    interface Presenter {
    }
}