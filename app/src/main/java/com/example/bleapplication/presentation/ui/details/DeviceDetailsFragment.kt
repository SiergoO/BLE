package com.example.bleapplication.presentation.ui.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.bleapplication.R
import com.example.bleapplication.databinding.FragmentDeviceDetailsBinding
import com.example.bleapplication.domain.ble.ConnectionStatus
import com.example.bleapplication.model.BleService
import com.example.bleapplication.model.BleState
import dagger.android.support.DaggerFragment
import javax.inject.Inject

class DeviceDetailsFragment : DaggerFragment(), DeviceDetailsFragmentContract.Ui {

    private var _viewBinding: FragmentDeviceDetailsBinding? = null
    private val viewBinding: FragmentDeviceDetailsBinding
        get() = _viewBinding!!

    @Inject
    lateinit var presenter: DeviceDetailsFragmentPresenter
    @Inject
    lateinit var bleState: BleState
    @Inject
    lateinit var connectionStatus: ConnectionStatus
    private var serviceListAdapter: ServiceListAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = FragmentDeviceDetailsBinding.inflate(inflater, container, false)
        .also { _viewBinding = it }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        presenter.start(this)
        serviceListAdapter = context?.let { ServiceListAdapter(it, bleState, connectionStatus) }
        viewBinding.apply {
            servicesList.apply {
                setHasFixedSize(true)
                layoutManager = LinearLayoutManager(context)
                overScrollMode = RecyclerView.OVER_SCROLL_NEVER
                adapter = serviceListAdapter
                presenter.scanServices()
            }
            toolbar.apply {
                setNavigationIcon(R.drawable.ic_arrow_back)
                setNavigationOnClickListener { findNavController().navigateUp() }
            }
            btnReconnect.setOnClickListener { presenter.reconnect() }
        }
        presenter.setContent()
        super.onViewCreated(view, savedInstanceState)
    }

    override fun addServices(services: List<BleService>) {
        serviceListAdapter?.addServices(services)
    }

    override fun showContent(bleState: BleState) {
        bleState.bleDevice?.run {
            viewBinding.apply {
                toolbar.title = name ?: context?.getString(R.string.unknown_device)
                deviceAddress.text = context?.getString(R.string.details_mac_address, address)
                deviceStatus.apply {
                    when (bleState.connectionStatus) {
                        true -> {
                            text = context?.getString(R.string.state_connected)
                            setTextColor(ContextCompat.getColor(context, R.color.light_green))
                        }
                        false -> {
                            text = context?.getString(R.string.state_disconnected)
                            setTextColor(ContextCompat.getColor(context, R.color.light_red))
                        }
                    }
                }
                btnReconnect.isEnabled = !bleState.connectionStatus
            }
        }
    }

    override fun showReconnectButton(show: Boolean) {
        viewBinding.toolbar.findViewById<ImageView>(R.id.btn_reconnect).visibility =
            if (show) View.VISIBLE else View.GONE
    }

    override fun showLoading(show: Boolean) {
        viewBinding.toolbar.findViewById<ProgressBar>(R.id.progress_reconnecting).visibility =
            if (show) View.VISIBLE else View.GONE
    }
}