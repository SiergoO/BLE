// Developed for %CLIENT% by Softeq Development Corporation
// http://www.softeq.com
package com.example.bleapplication.model

import java.util.*

data class BleService(
    val uuid: UUID?,
    val name: String?,
    val characteristics: ArrayList<BleCharacteristic> = arrayListOf()
)