package com.example.bleapplication.model

import java.util.*

data class BleCharacteristic(val uuid: UUID, val name: String = "") {
    override fun toString(): String = if (name.isBlank()) uuid.toString() else name
}