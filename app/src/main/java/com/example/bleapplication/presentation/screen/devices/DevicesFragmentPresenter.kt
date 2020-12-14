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
    private var status: DevicesFragmentContract.Status = DevicesFragmentContract.Status.FIRST_RUN
    private var deviceList: MutableSet<BleDevice> = mutableSetOf()
    private val bluetoothLeAdapter = BluetoothAdapter.getDefaultAdapter()

    override fun start(ui: DaggerFragment) {
        this.ui = ui as DevicesFragment
        updateUi()
    }

    override fun scan() {
        if (bluetoothLeAdapter.isEnabled) {
            deviceList.clear()
            status = DevicesFragmentContract.Status.SCANNING
            updateUi()
            mCompositeDisposable.add(
                startScanInteractor
                    .invoke()
                    .subscribe({
                        ui.addDevice(it)
                        deviceList.add(it)
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
        status = DevicesFragmentContract.Status.CONNECTION_CANCELLED
        updateUi()
    }

    override fun connect(device: BleDevice) {
        if (status != DevicesFragmentContract.Status.CONNECTING) {
            status = DevicesFragmentContract.Status.CONNECTING
            mCompositeDisposable.add(
                connectInteractor.invoke(device.address).observeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread()).subscribe {
                        if (it) {
                            status = DevicesFragmentContract.Status.CONNECTED
                            router.showDeviceDetailsFragment()
                        } else {
                            connectionStatus.changeStatus(it)
                            status = DevicesFragmentContract.Status.CANT_CONNECT
                            updateUi()
                            ui.showConnectionError()
                        }
                    })
            updateUi()
        }
    }

    override fun updateDeviceList() {
        ui.addDevices(deviceList)
    }

    private fun updateUi() {
        ui.showButtons(status == DevicesFragmentContract.Status.SCANNING)
        ui.setToolbarTitle(
            when (status) {
                DevicesFragmentContract.Status.FIRST_RUN -> ui.context?.getString(R.string.app_name)
                DevicesFragmentContract.Status.SCANNING -> ui.context?.getString(R.string.status_scanning)
                DevicesFragmentContract.Status.FOUND_DEVICES -> ui.context?.getString(R.string.status_found_devices)
                DevicesFragmentContract.Status.NO_DEVICES_FOUND -> ui.context?.getString(R.string.status_no_devices_found)
                DevicesFragmentContract.Status.CONNECTING -> ui.context?.getString(R.string.status_connecting)
                DevicesFragmentContract.Status.CONNECTION_CANCELLED -> ui.context?.getString(R.string.status_connection_cancelled)
                DevicesFragmentContract.Status.CANT_CONNECT -> ui.context?.getString(R.string.status_cant_connect)
                DevicesFragmentContract.Status.CONNECTED -> ui.context?.getString(R.string.status_found_devices)
            }
        )
        ui.showLoading(
            status == DevicesFragmentContract.Status.CONNECTING || status == DevicesFragmentContract.Status.SCANNING,
            status == DevicesFragmentContract.Status.CONNECTING
        )
    }

    private fun onScanEnd() {
        mCompositeDisposable.clear()
        status = if (deviceList.isEmpty()) {
            DevicesFragmentContract.Status.NO_DEVICES_FOUND
        } else DevicesFragmentContract.Status.FOUND_DEVICES
        updateUi()
    }
}
