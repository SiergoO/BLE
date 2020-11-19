package com.example.bleapplication.presentation

import android.annotation.SuppressLint
import android.content.Context
import android.widget.Toast
import com.example.bleapplication.di.DaggerAppComponent
import dagger.android.AndroidInjector
import dagger.android.support.DaggerApplication
import io.reactivex.Completable
import io.reactivex.android.schedulers.AndroidSchedulers

class App : DaggerApplication() {

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        return DaggerAppComponent.builder().application(this).build()
    }
}

@SuppressLint("CheckResult")
fun Context.toast(text: String) {
    Completable.complete().observeOn(AndroidSchedulers.mainThread()).subscribe {
        Toast.makeText(this, text , Toast.LENGTH_SHORT).show()
    }
}