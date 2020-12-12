package com.example.bleapplication.presentation.screen.details

import android.os.Bundle
import com.example.bleapplication.model.BleDevice
import com.example.bleapplication.presentation.screen.PresenterStateHolder
import java.io.Serializable

class DeviceDetailsFragmentPresenterStateHolder :
    PresenterStateHolder<DeviceDetailsFragmentContract.Presenter.State> {

    companion object {
        private const val ARG_IS_RECONNECTING = "isReconnecting"
    }

    override fun create(): DeviceDetailsFragmentContract.Presenter.State =
        State()

    override fun save(state: DeviceDetailsFragmentContract.Presenter.State?, bundle: Bundle) {
        bundle.putBoolean(ARG_IS_RECONNECTING, state?.isReconnecting?: false)
    }

    @Suppress("UNCHECKED_CAST")
    override fun restore(bundle: Bundle?): DeviceDetailsFragmentContract.Presenter.State? =
        bundle?.let {
            State().apply {
                isReconnecting = it.getBoolean(ARG_IS_RECONNECTING)
            }
        }

    private inner class State : DeviceDetailsFragmentContract.Presenter.State {
        override var isReconnecting: Boolean = false
    }
}