package com.example.bleapplication.presentation.ui.devices

import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice

class BleScanCallback(private val onFind: ((BluetoothDevice) -> Unit) = {}) : BluetoothAdapter.LeScanCallback {
    override fun onLeScan(device: BluetoothDevice?, rssi: Int, scanRecord: ByteArray?) {
        device?.let { onFind(it) }
    }
}