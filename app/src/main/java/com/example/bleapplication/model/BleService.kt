// Developed for %CLIENT% by Softeq Development Corporation
// http://www.softeq.com
package com.example.bleapplication.model

import java.util.*

class BleService(
        val uuid: UUID,
        val name: String = "",
        val characteristics: ArrayList<BleCharacteristic> = arrayListOf()
) {
    override fun toString(): String = if (name.isBlank()) uuid.toString() else name
}