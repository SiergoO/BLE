package com.example.bleapplication.model

import android.bluetooth.BluetoothGatt

data class BleState (
    var bleDevice: BleDevice? = null,
    var bleServices: List<BleService> = listOf(),
    var connectionStatus: Boolean = false,
    var gatt: BluetoothGatt? = null
)