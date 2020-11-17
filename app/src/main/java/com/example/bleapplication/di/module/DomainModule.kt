package com.example.bleapplication.di.module

import android.app.Application
import com.example.bleapplication.domain.ble.ConnectInteractor
import com.example.bleapplication.domain.ble.StartScanInteractor
import com.example.bleapplication.domain.ble.StopScanInteractor
import com.example.bleapplication.presentation.ui.devices.AndroidBleManager
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DomainModule {

    @Singleton
    @Provides
    fun provideAndroidBleManager(application: Application): AndroidBleManager =
        AndroidBleManager(application.applicationContext)

    @Singleton
    @Provides
    fun provideConnectInteractor(androidBleManager: AndroidBleManager): ConnectInteractor =
        ConnectInteractor(androidBleManager)

    @Singleton
    @Provides
    fun provideStartScanInteractor(androidBleManager: AndroidBleManager): StartScanInteractor =
        StartScanInteractor(androidBleManager)

    @Singleton
    @Provides
    fun provideStopScanInteractor(androidBleManager: AndroidBleManager): StopScanInteractor =
        StopScanInteractor(androidBleManager)
}