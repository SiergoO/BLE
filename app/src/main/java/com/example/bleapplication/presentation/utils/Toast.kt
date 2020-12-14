package com.example.bleapplication.presentation.utils

import android.annotation.SuppressLint
import android.content.Context
import android.widget.Toast
import io.reactivex.Completable
import io.reactivex.android.schedulers.AndroidSchedulers

@SuppressLint("CheckResult")
fun Context.toast(text: String) {
    Completable.complete().observeOn(AndroidSchedulers.mainThread()).subscribe {
        Toast.makeText(this, text , Toast.LENGTH_SHORT).show()
    }
}