package com.example.bleapplication.di.module

import com.example.bleapplication.domain.ble.ConnectInteractor
import com.example.bleapplication.domain.ble.StartScanInteractor
import com.example.bleapplication.domain.ble.StopScanInteractor
import com.example.bleapplication.model.BleState
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
        connectInteractor: ConnectInteractor,
        stopScanInteractor: StopScanInteractor
    ): DevicesFragmentPresenter =
        DevicesFragmentPresenter(startScanInteractor, connectInteractor, stopScanInteractor)

    @Singleton
    @Provides
    fun provideDeviceDetailsFragmentPresenter(bleState: BleState):
            DeviceDetailsFragmentPresenter =
        DeviceDetailsFragmentPresenter(bleState)
}