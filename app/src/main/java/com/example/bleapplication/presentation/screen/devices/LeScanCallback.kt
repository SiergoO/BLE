package com.example.bleapplication.presentation.screen.devices

import android.bluetooth.BluetoothDevice
import android.bluetooth.le.ScanCallback
import android.bluetooth.le.ScanResult

class LeScanCallback(private val onFind: ((BluetoothDevice) -> Unit) = {}) : ScanCallback() {

    override fun onScanResult(callbackType: Int, result: ScanResult?) {
        super.onScanResult(callbackType, result)
        result?.device?.let { onFind(it) }
    }

    override fun onBatchScanResults(results: MutableList<ScanResult>?) {
        super.onBatchScanResults(results)
        results?.mapNotNull { it.device }?.forEach { onFind(it) }
    }
}