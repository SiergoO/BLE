package com.example.bleapplication.presentation.ui;

import android.view.View;
import dagger.android.support.DaggerFragment

import io.reactivex.disposables.CompositeDisposable;

open class BasePresenter : Presenter {
    protected val mCompositeDisposable = CompositeDisposable()
    private var view: View? = null

    override fun start(ui: DaggerFragment) {

    }

    override fun attach(view: View?) {
        this.view = view
    }

    override fun destroy() {
        view = null
        mCompositeDisposable.clear()
    }
}