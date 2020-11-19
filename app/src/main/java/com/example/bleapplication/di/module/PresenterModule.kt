package com.example.bleapplication.di.module

import com.example.bleapplication.domain.ble.ConnectInteractor
import com.example.bleapplication.domain.ble.StartScanInteractor
import com.example.bleapplication.domain.ble.StopScanInteractor
import com.example.bleapplication.model.BleState
import com.example.bleapplication.model.Device
import com.example.bleapplication.presentation.ui.details.DeviceDetailsFragmentPresenter
import com.example.bleapplication.presentation.ui.devices.AndroidBleManager
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
        stopScanInteractor: StopScanInteractor,
        device: Device
    ): DevicesFragmentPresenter =
        DevicesFragmentPresenter(startScanInteractor, connectInteractor, stopScanInteractor, device)

    @Singleton
    @Provides
    fun provideDeviceDetailsFragmentPresenter(bleState: BleState):
            DeviceDetailsFragmentPresenter =
        DeviceDetailsFragmentPresenter(bleState)
}