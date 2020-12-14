package com.example.bleapplication.presentation.screen.details

import com.example.bleapplication.model.BleService
import com.example.bleapplication.model.BleState

interface DeviceDetailsFragmentContract {
    interface Ui {
        fun addServices(services: List<BleService>)
        fun setContent(bleState: BleState)
        fun setToolbar(isReconnecting: Boolean)
    }

    interface Presenter {
        fun scanServices()
        fun reconnect()
        fun goBack()
    }
}