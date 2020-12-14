package com.example.bleapplication.domain.ble

class StopScanInteractor (private val manager: BleManager) {
    fun invoke() = manager.stopScan()
}