package com.example.bleapplication.domain.ble

import com.example.bleapplication.model.BleState
import io.reactivex.Observable

class ConnectInteractor(private val manager: BleManager, private val bleState: BleState) {
    fun invoke(address: String): Observable<Boolean> = manager.connect(address).doOnNext { bleState.connectionStatus = it }
}