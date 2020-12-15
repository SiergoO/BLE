package com.example.bleapplication.presentation.ui.details

import com.example.bleapplication.domain.ble.ConnectInteractor
import com.example.bleapplication.domain.ble.ConnectionStatus
import com.example.bleapplication.model.BleState
import com.example.bleapplication.presentation.components.ble.toBleService
import com.example.bleapplication.presentation.ui.BasePresenter
import com.example.bleapplication.presentation.ui.Router
import dagger.android.support.DaggerFragment
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class DeviceDetailsFragmentPresenter(
    private val connectInteractor: ConnectInteractor,
    private var bleState: BleState,
    private val connectionStatus: ConnectionStatus,
    private val router: Router
) : BasePresenter(), DeviceDetailsFragmentContract.Presenter {

    companion object {
        private const val FLAG_SET_LIST = 0x0001
        private const val FLAG_SET_STATUS = 0x0002
        private const val FLAG_SET_TOOLBAR = 0x0004
        private const val FLAG_SET_CONTENT = FLAG_SET_STATUS or FLAG_SET_TOOLBAR
    }

    private lateinit var ui: DeviceDetailsFragment
    private var isReconnecting: Boolean = false

    override fun start(ui: DaggerFragment) {
        this.ui = ui as DeviceDetailsFragment
        updateUi(FLAG_SET_CONTENT)
    }

    override fun scanServices() {
        updateUi(FLAG_SET_LIST)
    }

    fun setContent() {
        connectionStatus.observeStatus { isConnected ->
            bleState.connectionStatus = isConnected
            isReconnecting = false
            updateUi(FLAG_SET_CONTENT)
        }
    }

    override fun reconnect() {
        isReconnecting = true
        updateUi(FLAG_SET_TOOLBAR)
        mCompositeDisposable.add(
            connectInteractor.invoke(bleState.bleDevice?.address ?: "").observeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe {
                    connectionStatus.changeStatus(it)
                })
    }

    override fun goBack() {
        router.goBack()
    }

    private fun updateUi(flags: Int) {
        if (0 != (flags and FLAG_SET_LIST)) {
            ui.addServices(bleState.gatt?.services?.map { it.toBleService() } ?: listOf())
        }
        if (0 != (flags and FLAG_SET_TOOLBAR)) {
            ui.setToolbar(isReconnecting)
        }
        if (0 != (flags and FLAG_SET_CONTENT)) {
            ui.apply {
                setContent(bleState)
                setToolbar(isReconnecting)
            }
        }
    }
}