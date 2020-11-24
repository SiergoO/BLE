package com.example.bleapplication.presentation.ui.devices

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bleapplication.LeDeviceListAdapter
import com.example.bleapplication.R
import com.example.bleapplication.databinding.FragmentDevicesBinding
import com.example.bleapplication.model.BleDevice
import com.example.bleapplication.presentation.utils.toast
import dagger.android.support.DaggerFragment
import javax.inject.Inject

class DevicesFragment: DaggerFragment(), DevicesFragmentContract.Ui {

    private var _viewBinding: FragmentDevicesBinding? = null
    private val viewBinding: FragmentDevicesBinding
        get() = _viewBinding!!
    @Inject
    lateinit var presenter: DevicesFragmentPresenter
    private var leDeviceListAdapter: LeDeviceListAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = FragmentDevicesBinding.inflate(inflater, container, false).also { _viewBinding = it }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        presenter.start(this)
        leDeviceListAdapter = LeDeviceListAdapter(object : LeDeviceListAdapter.Callback {
            override fun onDeviceMacClicked(device: BleDevice) {
                presenter.connect(device)
            }
        })
        viewBinding.apply {
            btnScanStart.setOnClickListener {
                leDeviceListAdapter?.removeAllDevices()
                presenter.scan()
            }
            btnScanStop.setOnClickListener {
                presenter.stopScan()
            }
            list.apply {
                setHasFixedSize(true)
                layoutManager = LinearLayoutManager(context)
                adapter = leDeviceListAdapter
            }
        }
        super.onViewCreated(view, savedInstanceState)
    }

    override fun addDevice(bleDevice: BleDevice) {
        leDeviceListAdapter?.addDevice(bleDevice)
    }

    override fun showDeviceDetails() {
        findNavController().navigate(R.id.deviceDetailsFragment)
    }

    override fun showConnectionError() {
        context?.toast("Can't connect to chosen device")
    }

    override fun showButtons(isScanning: Boolean) {
        viewBinding.btnScanStart.isEnabled = !isScanning
        viewBinding.btnScanStop.isEnabled = isScanning
    }
}