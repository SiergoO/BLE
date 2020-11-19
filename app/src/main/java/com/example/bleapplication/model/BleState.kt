package com.example.bleapplication.model

import android.bluetooth.BluetoothGatt

data class BleState (
    var bleDevice: BleDevice? = null,
    var connectionState: Boolean = false,
    var gatt: BluetoothGatt? = null
)