package com.example.bleapplication.domain.ble

import com.example.bleapplication.presentation.ui.devices.AndroidBleManager

class StopScanInteractor (private val manager: BleManager) {
    fun invoke() = manager.stopScan()
}