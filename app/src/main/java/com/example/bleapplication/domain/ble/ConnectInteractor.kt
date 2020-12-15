package com.example.bleapplication.domain.ble

import com.example.bleapplication.model.ble.BleState
import com.example.bleapplication.presentation.components.ble.BleManager
import io.reactivex.Observable

class ConnectInteractor(private val manager: BleManager, private val bleState: BleState) {
    fun invoke(address: String): Observable<Boolean> = manager.connect(address).doOnNext { bleState.connectionStatus = it }
}