package com.example.bleapplication.presentation.screen

import android.os.Bundle

interface PresenterStateHolder<S : Presenter.State> {
    fun create(): S?
    fun restore(bundle: Bundle?): S?
    fun save(state: S, bundle: Bundle)
}