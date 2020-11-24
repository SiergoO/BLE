package com.example.bleapplication.presentation.ui.devices

import com.example.bleapplication.domain.ble.ConnectInteractor
import com.example.bleapplication.domain.ble.StartScanInteractor
import com.example.bleapplication.domain.ble.StopScanInteractor
import com.example.bleapplication.model.BleDevice
import com.example.bleapplication.presentation.ui.BasePresenter
import dagger.android.support.DaggerFragment

class DevicesFragmentPresenter(
    private val startScanInteractor: StartScanInteractor,
    private val connectInteractor: ConnectInteractor,
    private val stopScanInteractor: StopScanInteractor,
) :
    BasePresenter(), DevicesFragmentContract.Presenter {

    private lateinit var ui: DevicesFragment
    private var isScanning: Boolean = false

    override fun start(ui: DaggerFragment) {
        this.ui = ui as DevicesFragment
    }

    fun scan() {
        isScanning = true
        updateUi()
        mCompositeDisposable.add(
            startScanInteractor
                .invoke()
                .subscribe({
                    ui.addDevice(it)
                }, {}, {
                    onScanEnd()
                })
        )
        updateUi()
    }

    fun stopScan() {
        stopScanInteractor.invoke()
        onScanEnd()
    }

    private fun onScanEnd() {
        mCompositeDisposable.clear()
        isScanning = false
        updateUi()
    }

    fun connect(device: BleDevice) {
        mCompositeDisposable.add(connectInteractor.invoke(device.address).subscribe {
            if (it) {
                ui.showDeviceDetails()
            } else
                ui.showConnectionError()
        })
    }

    private fun updateUi() {
        ui.showButtons(isScanning)
    }
}