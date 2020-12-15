package com.example.bleapplication.model.ble

import android.bluetooth.BluetoothGattCharacteristic
import java.util.*

data class BleCharacteristic(
    val uuid: UUID?,
    val name: String?,
    val value: String?,
    val properties: List<String>,
    val bluetoothGattCharacteristic: BluetoothGattCharacteristic
)