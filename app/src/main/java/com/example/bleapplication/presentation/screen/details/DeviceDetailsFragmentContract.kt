package com.example.bleapplication.presentation.screen.details

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
        fun scanServices()
        fun reconnect()
        fun goBack()

        interface State : com.example.bleapplication.presentation.screen.Presenter.State
    }
}