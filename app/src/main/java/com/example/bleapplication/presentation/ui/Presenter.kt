package com.example.bleapplication.presentation.ui;

import android.view.View;
import dagger.android.support.DaggerFragment

interface Presenter {
    fun start(ui: DaggerFragment)
    fun attach(view: View?)
    fun destroy()
}