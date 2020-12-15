package com.example.bleapplication.model

import io.reactivex.subjects.PublishSubject
import java.io.Serializable

data class BleDevice(
    val address: String,
    val name: String? = null,
    var connectionState: PublishSubject<Boolean>? = null
) : Serializable