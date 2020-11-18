package com.example.bleapplication.presentation.ui.details

import com.example.bleapplication.presentation.ui.BasePresenter
import dagger.android.support.DaggerFragment

class DeviceDetailsFragmentPresenter : BasePresenter() {

    private lateinit var ui: DeviceDetailsFragment

    override fun start(ui: DaggerFragment) {
        this.ui = ui as DeviceDetailsFragment
    }
}