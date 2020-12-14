package com.example.bleapplication.di.module

import com.example.bleapplication.presentation.screen.MainActivity
import com.example.bleapplication.presentation.screen.details.DeviceDetailsFragment
import com.example.bleapplication.presentation.screen.devices.DevicesFragment
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