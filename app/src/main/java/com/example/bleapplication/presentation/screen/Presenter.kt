package com.example.bleapplication.presentation.screen;

import android.view.View;
import dagger.android.support.DaggerFragment

interface Presenter<S : Presenter.State> {
    fun start(ui: DaggerFragment)
    fun attach(view: View?)
    fun destroy()
    fun saveState(savedState: S)
    fun restoreState(savedState: S?)
    interface State
}