package com.example.bleapplication.di.module

import com.example.bleapplication.presentation.screen.devices.DevicesFragmentPresenterStateHolder
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class PresenterStateHolderModule {

    @Singleton
    @Provides
    fun provideDevicesFragmentPresenterStateHolder(): DevicesFragmentPresenterStateHolder = DevicesFragmentPresenterStateHolder()
}