package com.example.bleapplication.domain.ble

import io.reactivex.Observable

class ConnectInteractor(private val manager: BleManager) {
    fun invoke(address: String): Observable<Boolean> = manager.connect(address)
}