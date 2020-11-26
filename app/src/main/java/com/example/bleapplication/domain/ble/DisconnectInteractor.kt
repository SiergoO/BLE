package com.example.bleapplication.domain.ble

class DisconnectInteractor(private val manager: BleManager) {
    fun invoke() = manager.disconnect()
}