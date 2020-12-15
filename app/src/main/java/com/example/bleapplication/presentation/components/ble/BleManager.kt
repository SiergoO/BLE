package com.example.bleapplication.presentation.components.ble

import com.example.bleapplication.model.ble.BleDevice
import io.reactivex.Observable

interface BleManager {
    fun scan(): Observable<BleDevice>
    fun stopScan()
    fun connect(address: String): Observable<Boolean>
    fun disconnect()
}