package com.example.bleapplication.domain.ble

import com.example.bleapplication.model.BleDevice
import io.reactivex.Observable

interface BleManager {
    fun scan(): Observable<BleDevice>
    fun stopScan()
    fun connect(address: String): Observable<Boolean>
    fun disconnect()
}