package com.example.bleapplication.presentation.ui.details

import com.example.bleapplication.model.ble.BleService
import com.example.bleapplication.model.ble.BleState

interface DeviceDetailsFragmentContract {
    interface Ui {
        fun addServices(services: List<BleService>)
        fun setToolbar(isReconnecting: Boolean)
        fun setContent(bleState: BleState)
    }

    interface Presenter {
        fun scanServices()
        fun reconnect()
        fun goBack()
    }
}