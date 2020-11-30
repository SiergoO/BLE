package com.example.bleapplication.presentation.ui.devices

import com.example.bleapplication.R
import com.example.bleapplication.domain.ble.ConnectInteractor
import com.example.bleapplication.domain.ble.DisconnectInteractor
import com.example.bleapplication.domain.ble.StartScanInteractor
import com.example.bleapplication.domain.ble.StopScanInteractor
import com.example.bleapplication.model.BleDevice
import com.example.bleapplication.presentation.ui.BasePresenter
import dagger.android.support.DaggerFragment
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class DevicesFragmentPresenter(
    private val startScanInteractor: StartScanInteractor,
    private val stopScanInteractor: StopScanInteractor,
    private val connectInteractor: ConnectInteractor,
    private val disconnectInteractor: DisconnectInteractor,
) :
    BasePresenter(), DevicesFragmentContract.Presenter {

    private lateinit var ui: DevicesFragment
    private var isScanning: Boolean = false
    private var isConnecting: Boolean = false
    private var isDeviceListEmpty: Boolean = true

    override fun start(ui: DaggerFragment) {
        this.ui = ui as DevicesFragment
    }

    fun scan() {
        isConnecting = false
        isScanning = true
        updateUi()
        mCompositeDisposable.add(
            startScanInteractor
                .invoke()
                .subscribe({
                    if (isDeviceListEmpty) isDeviceListEmpty = false
                    ui.addDevice(it)
                }, {}, {
                    onScanEnd()
                })
        )
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

    fun disconnect() {
        disconnectInteractor.invoke()
        isConnecting = false
        updateUi()
        ui.setToolbarTitle(ui.context?.getString(R.string.status_connection_cancelled))
    }

    fun connect(device: BleDevice) {
        if (!isConnecting) {
            isConnecting = true
            mCompositeDisposable.add(
                connectInteractor.invoke(device.address).observeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread()).subscribe {
                        isConnecting = false
                        if (it) {
                            ui.showDeviceDetails()
                        } else {
                            updateUi()
                            ui.setToolbarTitle(ui.context?.getString(R.string.status_cant_connect, device.name?: R.string.unknown_device))
                            ui.showConnectionError()
                        }
                    })
            updateUi()
        }
    }

    private fun updateUi() {
        ui.showButtons(isScanning)
        ui.setToolbarTitle(
            if (isConnecting) ui.context?.getString(R.string.status_connecting) else {
                when (isScanning) {
                    true -> ui.context?.getString(R.string.status_scanning)
                    false -> if (!isDeviceListEmpty) {
                        isDeviceListEmpty = true
                        ui.context?.getString(R.string.status_found_devices)
                    } else ui.context?.getString(R.string.status_no_devices_found)
                }
            }
        )
        ui.showLoading(isConnecting || isScanning, isConnecting)
    }
}