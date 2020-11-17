package com.example.bleapplication.model

data class BleDevice(val address: String, val name: String? = null) {

    companion object {
        const val DEFAULT_NAME = "unknown"
    }

    override fun toString(): String = if (name.isNullOrBlank()) DEFAULT_NAME else name
}