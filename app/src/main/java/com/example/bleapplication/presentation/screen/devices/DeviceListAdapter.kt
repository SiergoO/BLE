package com.example.bleapplication.presentation.screen.devices

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.bleapplication.R
import com.example.bleapplication.databinding.ItemDeviceBinding
import com.example.bleapplication.model.BleDevice

class DeviceListAdapter(private val context: Context, private val callback: Callback) :
    RecyclerView.Adapter<DeviceListAdapter.ViewHolder>() {

    private var _viewBinding: ItemDeviceBinding? = null
    private val viewBinding: ItemDeviceBinding
        get() = _viewBinding!!
    private var devices: MutableSet<BleDevice> = mutableSetOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(ItemDeviceBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                .also { _viewBinding = it })

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(devices.elementAt(position))
    }

    override fun getItemCount(): Int = devices.size

    fun addDevice(bluetoothDevice: BleDevice) {
        devices.add(bluetoothDevice)
        notifyDataSetChanged()
    }

    fun removeAllDevices() {
        devices.removeAll(devices)
        notifyDataSetChanged()
    }

    private fun handleItemClicked(position: Int) {
        callback.onDeviceClicked(devices.elementAt(position))
    }

    inner class ViewHolder(item: ItemDeviceBinding) : RecyclerView.ViewHolder(item.root) {
        private val address: TextView = viewBinding.address
        private val name: TextView = viewBinding.name

        init {
            item.root.setOnClickListener {
                handleItemClicked(adapterPosition)
            }
        }

        fun bind(device: BleDevice) {
            name.text = device.name ?: context.getString(R.string.unknown_device)
            address.text = device.address
        }
    }

    interface Callback {
        fun onDeviceClicked(device: BleDevice)
    }
}