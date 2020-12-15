package com.example.bleapplication.di.module

import com.example.bleapplication.domain.ble.*
import com.example.bleapplication.model.BleState
import com.example.bleapplication.presentation.ui.RouterImpl
import com.example.bleapplication.presentation.ui.details.DeviceDetailsFragmentPresenter
import com.example.bleapplication.presentation.ui.devices.DevicesFragmentPresenter
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class PresenterModule {

    @Singleton
    @Provides
    fun provideDevicesFragmentPresenter(
        startScanInteractor: StartScanInteractor,
        stopScanInteractor: StopScanInteractor,
        connectInteractor: ConnectInteractor,
        disconnectInteractor: DisconnectInteractor,
        connectionStatus: ConnectionStatus,
        router: RouterImpl
    ): DevicesFragmentPresenter =
        DevicesFragmentPresenter(
            startScanInteractor,
            stopScanInteractor,
            connectInteractor,
            disconnectInteractor,
            connectionStatus,
            router
        )

    @Singleton
    @Provides
    fun provideDeviceDetailsFragmentPresenter(
        connectInteractor: ConnectInteractor,
        bleState: BleState,
        connectionStatus: ConnectionStatus,
        router: RouterImpl
    ): DeviceDetailsFragmentPresenter =
        DeviceDetailsFragmentPresenter(connectInteractor, bleState, connectionStatus, router)
}