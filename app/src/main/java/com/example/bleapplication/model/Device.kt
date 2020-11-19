package com.example.bleapplication.model

import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject

class Device {

    private val subject: PublishSubject<BleDevice> = PublishSubject.create()

    fun send(bleDevice: BleDevice) {
        subject.onNext(bleDevice)
    }

    fun observe(observer: (BleDevice) -> Unit): Disposable =
        subject.subscribeOn(Schedulers.io())
            .subscribe { observer.invoke(it) }
}