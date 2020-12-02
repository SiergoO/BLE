package com.example.bleapplication.di.module

import android.app.Application
import com.example.bleapplication.domain.ble.*
import com.example.bleapplication.model.BleState
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
    fun provideDisconnectInteractor(androidBleManager: AndroidBleManager): DisconnectInteractor =
        DisconnectInteractor(androidBleManager)

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
    fun provideBleState(): BleState = BleState()

    @Singleton
    @Provides
    fun provideConnectionState(): ConnectionStatus = ConnectionStatus()
    }