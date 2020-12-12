package com.example.bleapplication.presentation.screen;

import android.view.View;
import dagger.android.support.DaggerFragment

import io.reactivex.disposables.CompositeDisposable;

open class BasePresenter<S: Presenter.State> : Presenter<S> {

    protected val mCompositeDisposable = CompositeDisposable()
    private var view: View? = null

    override fun start(ui: DaggerFragment) {}

    override fun attach(view: View?) {
        this.view = view
    }

    override fun destroy() {
        view = null
        mCompositeDisposable.clear()
    }

    override fun saveState(savedState: S?) {}

    override fun restoreState(savedState: S?) {}

}