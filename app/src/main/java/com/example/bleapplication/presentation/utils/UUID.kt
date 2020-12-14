package com.example.bleapplication.presentation.utils

import java.util.*

fun UUID.shorten(): String {
    val s = this.toString()
    return if (s.startsWith("0000")) {
        s.take(8).replace("0000", "0x")
    } else s
}