package com.example.bleapplication.presentation.utils

import java.nio.ByteBuffer
import java.util.*

fun UUID.getBytesFromUUID(): ByteArray {
    val bb: ByteBuffer = ByteBuffer.wrap(ByteArray(16))
    bb.putLong(this.mostSignificantBits)
    bb.putLong(this.leastSignificantBits)
    return bb.array()
}

fun ByteArray.getUUIDFromBytes(): UUID {
    val byteBuffer: ByteBuffer = ByteBuffer.wrap(this)
    val high: Long = byteBuffer.long
    val low: Long = byteBuffer.long
    return UUID(high, low)
}

fun UUID.shorten(): String {
    val s = this.toString()
    return if (s.startsWith("0000")) {
        s.take(8).replace("0000", "0x")
    } else s
}