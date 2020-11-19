package com.example.bleapplication.model

import java.io.Serializable

data class BleDevice(val address: String, val name: String? = null) : Serializable