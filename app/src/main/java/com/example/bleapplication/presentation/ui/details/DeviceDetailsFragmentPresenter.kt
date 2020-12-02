package com.example.bleapplication.presentation.ui.details

import android.annotation.SuppressLint
import com.example.bleapplication.domain.ble.ConnectionStatus
import com.example.bleapplication.model.BleState
import com.example.bleapplication.presentation.components.ble.toBleService
import com.example.bleapplication.presentation.ui.BasePresenter
import dagger.android.support.DaggerFragment

class DeviceDetailsFragmentPresenter(private var bleState: BleState, private val connectionStatus: ConnectionStatus) : BasePresenter(),
    DeviceDetailsFragmentContract.Presenter {

    private lateinit var ui: DeviceDetailsFragment

    override fun start(ui: DaggerFragment) {
        this.ui = ui as DeviceDetailsFragment
    }

    override fun getBleState(): BleState = bleState

    override fun scanServices() {
        updateUi()
    }

    fun setContent() {
        connectionStatus.observeStatus {
            bleState.connectionStatus = it
            ui.showContent(bleState)
        }
    }

    @SuppressLint("CheckResult")
    private fun updateUi() {
        ui.showContent(bleState)
        ui.addServices(bleState.gatt?.services?.map { it.toBleService() } ?: listOf())
    }
}