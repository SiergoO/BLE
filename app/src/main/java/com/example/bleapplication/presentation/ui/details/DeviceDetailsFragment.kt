package com.example.bleapplication.presentation.ui.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.bleapplication.R
import com.example.bleapplication.databinding.FragmentDeviceDetailsBinding
import com.example.bleapplication.model.BleDevice
import com.example.bleapplication.model.BleState
import com.example.bleapplication.presentation.conponents.ble.toBCharacteristic
import com.example.bleapplication.presentation.conponents.ble.toBleService
import dagger.android.support.DaggerFragment
import javax.inject.Inject

class DeviceDetailsFragment: DaggerFragment(), DeviceDetailsFragmentContract.Ui {

    private var _viewBinding: FragmentDeviceDetailsBinding? = null
    private val viewBinding: FragmentDeviceDetailsBinding
        get() = _viewBinding!!
    @Inject
    lateinit var presenter: DeviceDetailsFragmentPresenter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = FragmentDeviceDetailsBinding.inflate(inflater, container, false).also { _viewBinding = it }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        presenter.start(this)
        super.onViewCreated(view, savedInstanceState)
        viewBinding.toolbar.apply {
            setNavigationIcon(R.drawable.ic_arrow_back)
            setNavigationOnClickListener { findNavController().navigateUp() }
        }
    }

    override fun showContent(bleState: BleState) {
        bleState.bleDevice?.run {
            viewBinding.apply {
                toolbar.title = name ?: "Unknown device"
                deviceAddress.text = getString(R.string.details_mac_address, address)
                deviceStatus.text = getString(R.string.details_device_status, bleState.connectionState.toString())
                deviceServices.text = getString(R.string.details_available_services, bleState.gatt?.services?.map { it.toBleService() }?.joinToString(", "))
                deviceCharAndValue.text = getString(R.string.details_char_and_value,
                    bleState.gatt?.services?.joinToString("\n ") { service ->
                        service.characteristics.map { it.toBCharacteristic() }.joinToString(", ")
                    })
            }
        }
    }
}