package com.example.bleapplication.presentation.ui.details

import com.example.bleapplication.model.BleDevice
import com.example.bleapplication.model.BleService
import com.example.bleapplication.model.BleState


interface DeviceDetailsFragmentContract {
        interface Ui {
            fun addServices(services: List<BleService>)
            fun showContent(bleState: BleState)
            fun showLoading(show: Boolean)
            fun showReconnectButton(show: Boolean)
        }

        interface Presenter {
            fun getBleState(): BleState
            fun scanServices()
            fun reconnect()
        }
}