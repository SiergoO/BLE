package com.example.bleapplication.presentation.screen.devices

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import androidx.core.os.bundleOf
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bleapplication.R
import com.example.bleapplication.databinding.FragmentDevicesBinding
import com.example.bleapplication.model.BleDevice
import com.example.bleapplication.presentation.screen.details.DeviceDetailsFragment
import com.example.bleapplication.presentation.utils.toast
import dagger.android.support.DaggerFragment
import javax.inject.Inject

class DevicesFragment : DaggerFragment(), DevicesFragmentContract.Ui {

    companion object {
        fun newInstance(): DevicesFragment =
            DevicesFragment().apply {
                arguments = Bundle().apply {

                }
            }
    }

    private var _viewBinding: FragmentDevicesBinding? = null
    private val viewBinding: FragmentDevicesBinding
        get() = _viewBinding!!

    @Inject
    lateinit var presenter: DevicesFragmentPresenter
    @Inject
    lateinit var presenterStateHolder: DevicesFragmentPresenterStateHolder
    private var deviceListAdapter: DeviceListAdapter? = null
    private var state: DevicesFragmentContract.Presenter.State? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? =
        FragmentDevicesBinding.inflate(inflater, container, false).also { _viewBinding = it }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        presenter.start(this)
        deviceListAdapter = context?.let {
            DeviceListAdapter(it, object : DeviceListAdapter.Callback {
                override fun onDeviceClicked(device: BleDevice) {
                    presenter.stopScan()
                    presenter.disconnect()
                    presenter.connect(device)
                }
            })
        }
        state = presenterStateHolder.restore(savedInstanceState) ?: presenterStateHolder.create()
        presenter.restoreState(state)
        presenter.updateDeviceList()
        viewBinding.apply {
            btnScanStart.setOnClickListener {
                deviceListAdapter?.removeAllDevices()
                presenter.disconnect()
                presenter.scan()
            }
            btnScanStop.setOnClickListener {
                presenter.stopScan()
            }
            list.apply {
                setHasFixedSize(true)
                layoutManager = LinearLayoutManager(context)
                adapter = deviceListAdapter
            }
            toolbar.findViewById<ImageView>(R.id.cancel_action).setOnClickListener {
                presenter.disconnect()
            }
        }
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        presenter.saveState(state)
        presenterStateHolder.save(state, outState)
    }

    override fun addDevice(bleDevice: BleDevice) {
        deviceListAdapter?.addDevice(bleDevice)
    }

    override fun addDevices(deviceList: MutableSet<BleDevice>) {
        deviceListAdapter?.addDevices(deviceList)
    }

    override fun showConnectionError() {
        context?.toast("Can't connect to chosen device")
    }

    override fun showBluetoothConnectionError() {
        context?.toast("Please, enable bluetooth!")
    }

    override fun showButtons(isScanning: Boolean) {
        viewBinding.btnScanStart.isEnabled = !isScanning
        viewBinding.btnScanStop.isEnabled = isScanning
    }

    override fun setToolbarTitle(title: String?) {
        viewBinding.toolbar.title = title ?: context?.getString(R.string.app_name)
    }

    override fun showLoading(showLoading: Boolean, showCancelIcon: Boolean) {
        viewBinding.toolbar.findViewById<ProgressBar>(R.id.progress_loading).visibility =
            if (showLoading) View.VISIBLE else View.GONE
        viewBinding.toolbar.findViewById<ImageView>(R.id.cancel_action).visibility =
            if (showCancelIcon) View.VISIBLE else View.GONE
    }
}