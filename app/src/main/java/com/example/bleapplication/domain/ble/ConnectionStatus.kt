package com.example.bleapplication.domain.ble

import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject

class ConnectionStatus {

    private val subject: PublishSubject<Boolean> = PublishSubject.create()

    fun changeStatus(isConnected: Boolean) {
        subject.onNext(isConnected)
    }

    fun observeStatus(observer: (Boolean) -> Unit): Disposable =
        subject.subscribeOn(Schedulers.io())
            .subscribe { observer.invoke(it) }
}