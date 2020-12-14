package com.example.bleapplication.presentation.utils

fun String.filterBrackets() = filter { it !in setOf('[', ']') }

fun ByteArray?.convertToString() = String(this ?: byteArrayOf())