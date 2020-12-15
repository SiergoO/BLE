package com.example.bleapplication.domain.ble

import com.example.bleapplication.presentation.components.ble.BleManager

class StopScanInteractor (private val manager: BleManager) {
    fun invoke() = manager.stopScan()
}