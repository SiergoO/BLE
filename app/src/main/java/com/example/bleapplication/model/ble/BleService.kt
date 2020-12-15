package com.example.bleapplication.model.ble

import java.util.*

data class BleService(
    val uuid: UUID?,
    val name: String?,
    val characteristics: ArrayList<BleCharacteristic> = arrayListOf()
)