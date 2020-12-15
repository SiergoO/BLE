package com.example.bleapplication.domain.ble

import com.example.bleapplication.presentation.components.ble.BleManager

class DisconnectInteractor(private val manager: BleManager) {
    fun invoke() = manager.disconnect()
}