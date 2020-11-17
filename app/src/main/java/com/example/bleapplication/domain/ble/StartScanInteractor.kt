package com.example.bleapplication.domain.ble

class StartScanInteractor (private val manager: BleManager) {
    fun invoke() = manager.scan()
}