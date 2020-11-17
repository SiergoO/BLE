package com.example.bleapplication.presentation.utils

import android.bluetooth.BluetoothDevice
import com.example.bleapplication.model.BleDevice

fun BluetoothDevice.toBleDevice() = BleDevice(address, name)