package com.example.bleapplication.di.module

import com.example.bleapplication.presentation.ui.MainActivity
import com.example.bleapplication.presentation.ui.details.DeviceDetailsFragment
import com.example.bleapplication.presentation.ui.devices.DevicesFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class BuildersModule  {

    @ContributesAndroidInjector(modules = [])
    abstract fun contributeMainActivity(): MainActivity

    @ContributesAndroidInjector(modules = [])
    abstract fun contributeFavoriteComicsFragment(): DevicesFragment

    @ContributesAndroidInjector(modules = [])
    abstract fun contributeScalesFragment(): DeviceDetailsFragment
}