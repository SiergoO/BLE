package com.example.bleapplication.presentation.ui.details

import com.example.bleapplication.model.BleDevice
import com.example.bleapplication.model.BleState
import com.example.bleapplication.model.Device
import com.example.bleapplication.presentation.ui.BasePresenter
import dagger.android.support.DaggerFragment

class DeviceDetailsFragmentPresenter(private val bleState: BleState) : BasePresenter(), DeviceDetailsFragmentContract.Presenter {

    private lateinit var ui: DeviceDetailsFragment
    private var bleDevice: BleDevice? = null

    override fun start(ui: DaggerFragment) {
        this.ui = ui as DeviceDetailsFragment
        updateUi()
    }

    private fun updateUi(){
        ui.showContent(bleState)
    }
}