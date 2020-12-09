package com.example.bleapplication.presentation.screen.devices

import com.example.bleapplication.model.BleDevice

interface DevicesFragmentContract {
    interface Ui {
        fun addDevice(bleDevice: BleDevice)
        fun addDevices(deviceList: MutableSet<BleDevice>)
        fun showButtons(isScanning: Boolean)
        fun setToolbarTitle(title: String?)
        fun showLoading(showLoading: Boolean, showCancelIcon: Boolean)
        fun showConnectionError()
        fun showBluetoothConnectionError()
    }

    interface Presenter: com.example.bleapplication.presentation.screen.Presenter<Presenter.State> {
        fun scan()
        fun stopScan()
        fun connect(device: BleDevice)
        fun disconnect()
        fun updateDeviceList()

        interface State : com.example.bleapplication.presentation.screen.Presenter.State {
            var status: Status
            var deviceList: MutableSet<BleDevice>
        }
    }

    enum class Status {
        FIRST_RUN,
        SCANNING,
        FOUND_DEVICES,
        NO_DEVICES_FOUND,
        CONNECTING,
        CONNECTION_CANCELLED,
        CONNECTED,
        CANT_CONNECT
    }
}