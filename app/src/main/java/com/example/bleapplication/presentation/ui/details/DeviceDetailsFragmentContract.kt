package com.example.bleapplication.presentation.ui.details

import com.example.bleapplication.model.BleDevice
import com.example.bleapplication.model.BleState


interface DeviceDetailsFragmentContract {
        interface Ui {
            fun showContent(bleState: BleState)
        }

        interface Presenter {
        }
}