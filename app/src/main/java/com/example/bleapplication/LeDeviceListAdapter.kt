package com.example.bleapplication

import android.annotation.SuppressLint
import android.bluetooth.BluetoothDevice
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.bleapplication.databinding.ItemDeviceBinding
import com.example.bleapplication.model.BleDevice

class LeDeviceListAdapter(private val callback: Callback) : RecyclerView.Adapter<LeDeviceListAdapter.ViewHolder>() {

    private var _viewBinding: ItemDeviceBinding? = null
    private val viewBinding: ItemDeviceBinding
        get() = _viewBinding!!
    private var devices: MutableSet<BleDevice> = mutableSetOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(ItemDeviceBinding.inflate(LayoutInflater.from(parent.context), parent, false).also { _viewBinding = it })

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
        callback.onDeviceMacClicked(devices.elementAt(position))
    }

    inner class ViewHolder(item: ItemDeviceBinding): RecyclerView.ViewHolder(item.root){
        private val address: TextView = viewBinding.address

        init {
            item.root.setOnClickListener {
                handleItemClicked(adapterPosition)
            }
        }

        @SuppressLint("SetTextI18n")
        fun bind(device: BleDevice) {
            address.text =  "${device.name} ${device.address}"
        }
    }

    interface Callback {
        fun onDeviceMacClicked(device: BleDevice)
    }
}