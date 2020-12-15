package com.example.bleapplication.domain.ble

import com.example.bleapplication.presentation.components.ble.BleManager

class StartScanInteractor (private val manager: BleManager) {
    fun invoke() = manager.scan()
}