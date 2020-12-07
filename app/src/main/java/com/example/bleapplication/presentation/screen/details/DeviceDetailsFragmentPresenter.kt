package com.example.bleapplication.presentation.screen.details

import com.example.bleapplication.domain.ble.ConnectInteractor
import com.example.bleapplication.domain.ble.ConnectionStatus
import com.example.bleapplication.model.BleState
import com.example.bleapplication.presentation.components.ble.toBleService
import com.example.bleapplication.presentation.screen.BasePresenter
import com.example.bleapplication.presentation.screen.Router
import dagger.android.support.DaggerFragment
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class DeviceDetailsFragmentPresenter(
    private val connectInteractor: ConnectInteractor,
    private var bleState: BleState,
    private val connectionStatus: ConnectionStatus,
    private val router: Router
) : BasePresenter(),
    DeviceDetailsFragmentContract.Presenter {

    private lateinit var ui: DeviceDetailsFragment

    override fun start(ui: DaggerFragment) {
        this.ui = ui as DeviceDetailsFragment
    }

    override fun scanServices() {
        updateUi()
    }

    fun setContent() {
        connectionStatus.observeStatus {
            bleState.connectionStatus = it
            ui.showReconnectButton(true)
            ui.showLoading(false)
            ui.showContent(bleState)
        }
    }

    override fun reconnect() {
        ui.showReconnectButton(false)
        ui.showLoading(true)
        mCompositeDisposable.add(
            connectInteractor.invoke(bleState.bleDevice?.address ?: "").observeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe {
                        connectionStatus.changeStatus(it)
                })
    }

    override fun goBack() {
        router.goBack()
    }

    private fun updateUi() {
        ui.showContent(bleState)
        ui.addServices(bleState.gatt?.services?.map { it.toBleService() } ?: listOf())
    }
}