package com.example.bleapplication.presentation.screen.devices

import android.bluetooth.BluetoothAdapter
import com.example.bleapplication.R
import com.example.bleapplication.domain.ble.*
import com.example.bleapplication.model.BleDevice
import com.example.bleapplication.presentation.screen.BasePresenter
import com.example.bleapplication.presentation.screen.Router
import dagger.android.support.DaggerFragment
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class DevicesFragmentPresenter(
    private val startScanInteractor: StartScanInteractor,
    private val stopScanInteractor: StopScanInteractor,
    private val connectInteractor: ConnectInteractor,
    private val disconnectInteractor: DisconnectInteractor,
    private val connectionStatus: ConnectionStatus,
    private val router: Router
) :
    BasePresenter(), DevicesFragmentContract.Presenter {

    private lateinit var ui: DevicesFragment
    private var isScanning: Boolean = false
    private var isConnecting: Boolean = false
    private var isDeviceListEmpty: Boolean = true
    private val bluetoothLeAdapter = BluetoothAdapter.getDefaultAdapter()

    override fun start(ui: DaggerFragment) {
        this.ui = ui as DevicesFragment
    }

    override fun scan() {
        if (bluetoothLeAdapter.isEnabled) {
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
        } else ui.showBluetoothConnectionError()
    }

    override fun stopScan() {
        stopScanInteractor.invoke()
        onScanEnd()
    }

    override fun disconnect() {
        disconnectInteractor.invoke()
        isConnecting = false
        updateUi()
        ui.setToolbarTitle(ui.context?.getString(R.string.status_connection_cancelled))
    }

    override fun connect(device: BleDevice) {
        if (!isConnecting) {
            isConnecting = true
            mCompositeDisposable.add(
                connectInteractor.invoke(device.address).observeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread()).subscribe {
                        isConnecting = false
                        if (it) {
                            router.showDeviceDetailsFragment()
                        } else {
                            updateUi()
                            ui.setToolbarTitle(ui.context?.getString(R.string.status_cant_connect, device.name?: R.string.unknown_device))
                            connectionStatus.changeStatus(it)
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

    private fun onScanEnd() {
        mCompositeDisposable.clear()
        isScanning = false
        updateUi()
    }
}
