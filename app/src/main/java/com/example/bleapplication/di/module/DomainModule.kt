package com.example.bleapplication.di.module

import android.app.Application
import com.example.bleapplication.domain.ble.ConnectInteractor
import com.example.bleapplication.domain.ble.StartScanInteractor
import com.example.bleapplication.domain.ble.StopScanInteractor
import com.example.bleapplication.model.BleDevice
import com.example.bleapplication.model.BleState
import com.example.bleapplication.model.Device
import com.example.bleapplication.presentation.ui.devices.AndroidBleManager
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DomainModule {

    @Singleton
    @Provides
    fun provideAndroidBleManager(application: Application, bleState: BleState): AndroidBleManager =
        AndroidBleManager(application.applicationContext, bleState)

    @Singleton
    @Provides
    fun provideConnectInteractor(androidBleManager: AndroidBleManager, bleState: BleState): ConnectInteractor =
        ConnectInteractor(androidBleManager, bleState)

    @Singleton
    @Provides
    fun provideStartScanInteractor(androidBleManager: AndroidBleManager): StartScanInteractor =
        StartScanInteractor(androidBleManager)

    @Singleton
    @Provides
    fun provideStopScanInteractor(androidBleManager: AndroidBleManager): StopScanInteractor =
        StopScanInteractor(androidBleManager)

    @Singleton
    @Provides
    fun provideDevice(): Device = Device()

    @Singleton
    @Provides
    fun provideBleState(): BleState = BleState()
}