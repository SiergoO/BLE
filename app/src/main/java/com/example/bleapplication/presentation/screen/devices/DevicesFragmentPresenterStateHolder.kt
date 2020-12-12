package com.example.bleapplication.presentation.screen.devices

import android.os.Bundle
import com.example.bleapplication.model.BleDevice
import com.example.bleapplication.presentation.screen.PresenterStateHolder
import java.io.Serializable

class DevicesFragmentPresenterStateHolder :
    PresenterStateHolder<DevicesFragmentContract.Presenter.State> {

    companion object {
        private const val ARG_STATUS = "status"
        private const val ARG_DEVICE_LIST = "deviceList"
    }

    override fun create(): DevicesFragmentContract.Presenter.State =
        State()

    override fun save(state: DevicesFragmentContract.Presenter.State?, bundle: Bundle) {
        bundle.putSerializable(ARG_STATUS, state?.status)
        bundle.putSerializable(ARG_DEVICE_LIST, state?.deviceList as Serializable?)
    }

    @Suppress("UNCHECKED_CAST")
    override fun restore(bundle: Bundle?): DevicesFragmentContract.Presenter.State? =
        bundle?.let {
            State().apply {
                status = it.getSerializable(ARG_STATUS) as DevicesFragmentContract.Status
                deviceList = it.getSerializable(ARG_DEVICE_LIST) as? MutableSet<BleDevice>?: mutableSetOf()
            }
        }

    private inner class State : DevicesFragmentContract.Presenter.State {
        override var status: DevicesFragmentContract.Status = DevicesFragmentContract.Status.FIRST_RUN
        override var deviceList: MutableSet<BleDevice> = mutableSetOf()
    }
}