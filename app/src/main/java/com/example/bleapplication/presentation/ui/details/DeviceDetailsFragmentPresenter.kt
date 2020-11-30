package com.example.bleapplication.presentation.ui.details

import com.example.bleapplication.model.BleState
import com.example.bleapplication.presentation.conponents.ble.toBleService
import com.example.bleapplication.presentation.ui.BasePresenter
import dagger.android.support.DaggerFragment

class DeviceDetailsFragmentPresenter(private val bleState: BleState) : BasePresenter(),
    DeviceDetailsFragmentContract.Presenter {

    private lateinit var ui: DeviceDetailsFragment

    override fun start(ui: DaggerFragment) {
        this.ui = ui as DeviceDetailsFragment
    }

    override fun scanServices() {
        updateUi()
    }

    private fun updateUi() {
        ui.showContent(bleState)
        ui.addServices(bleState.gatt?.services?.map { it.toBleService() } ?: listOf())
    }
}