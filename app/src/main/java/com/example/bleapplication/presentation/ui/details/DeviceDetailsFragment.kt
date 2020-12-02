package com.example.bleapplication.presentation.ui.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.bleapplication.R
import com.example.bleapplication.databinding.FragmentDeviceDetailsBinding
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
    private var serviceListAdapter: ServiceListAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = FragmentDeviceDetailsBinding.inflate(inflater, container, false)
        .also { _viewBinding = it }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        presenter.start(this)
        serviceListAdapter = context?.let { ServiceListAdapter(it, presenter.getBleState() ) }
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
                deviceStatus.text =
                    getString(R.string.details_device_status, bleState.connectionStatus.toString())
            }
        }
    }
}