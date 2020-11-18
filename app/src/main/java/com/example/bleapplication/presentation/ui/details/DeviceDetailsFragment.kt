package com.example.bleapplication.presentation.ui.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.bleapplication.databinding.FragmentDeviceDetailsBinding
import com.example.bleapplication.databinding.FragmentDevicesBinding
import com.example.bleapplication.presentation.ui.devices.DevicesFragmentPresenter
import dagger.android.support.DaggerFragment
import javax.inject.Inject

class DeviceDetailsFragment: DaggerFragment() {

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
    }
}