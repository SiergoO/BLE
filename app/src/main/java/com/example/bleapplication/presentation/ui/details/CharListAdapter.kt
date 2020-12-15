package com.example.bleapplication.presentation.ui.details

import android.bluetooth.BluetoothGattCharacteristic
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.bleapplication.databinding.ItemCharBinding
import com.example.bleapplication.domain.ble.ConnectionStatus
import com.example.bleapplication.model.BleCharacteristic
import com.example.bleapplication.model.BleState
import com.example.bleapplication.presentation.dialog.WriteCharacteristicDialog
import com.example.bleapplication.presentation.utils.shorten
import dagger.android.support.DaggerAppCompatActivity

class CharListAdapter(private val bleState: BleState, private val connectionStatus: ConnectionStatus) :
    RecyclerView.Adapter<CharListAdapter.ViewHolder>() {

    private lateinit var _viewBinding: ItemCharBinding
    private val viewBinding: ItemCharBinding
        get() = _viewBinding
    private var chars: MutableList<BleCharacteristic> = mutableListOf()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(
            ItemCharBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                .also { _viewBinding = it })

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(chars[position])
    }

    override fun getItemCount(): Int = chars.size

    fun addChars(bleChars: List<BleCharacteristic>?) {
        if (!bleChars.isNullOrEmpty()) {
            chars.addAll(bleChars)
        }
        notifyDataSetChanged()
    }

    inner class ViewHolder(item: ItemCharBinding) : RecyclerView.ViewHolder(item.root) {

        fun bind(char: BleCharacteristic) {
            viewBinding.apply {
                charName.text = char.name
                charUuid.text = char.uuid?.shorten()
                frameNotification.apply {
                    if (char.properties.any { it == "Notifiable" }) {
                        visibility = View.VISIBLE
                        btnEnableNotifications.apply {
                            visibility = View.VISIBLE
                            setOnClickListener {
                                it.visibility = View.GONE
                                btnDisableNotifications.visibility = View.VISIBLE
                                bleState.gatt?.setCharacteristicNotification(
                                    char.bluetoothGattCharacteristic,
                                    true
                                )
                            }
                        }
                        btnDisableNotifications.apply {
                            setOnClickListener {
                                it.visibility = View.GONE
                                btnEnableNotifications.visibility = View.VISIBLE
                                bleState.gatt?.setCharacteristicNotification(
                                    char.bluetoothGattCharacteristic,
                                    false
                                )
                            }
                        }
                    } else View.GONE
                }
                btnRead.apply {
                    if (char.properties.any { it == "Readable" }) {
                        visibility = View.VISIBLE
                        setOnClickListener {
                            bleState.gatt?.readCharacteristic(
                                char.bluetoothGattCharacteristic
                            )
                        }
                    } else View.GONE
                }
                btnWrite.apply {
                    if (char.properties.any { it == "Writable" }) {
                        visibility = View.VISIBLE
                        setOnClickListener {
                            WriteCharacteristicDialog(object : WriteCharacteristicDialog.Callback {
                                override fun writeButtonClicked(char: BluetoothGattCharacteristic) {
                                    bleState.gatt?.writeCharacteristic(char)
                                }
                            }, char).show(
                                (context as DaggerAppCompatActivity).supportFragmentManager,
                                WriteCharacteristicDialog.TAG
                            )
                        }
                    } else View.GONE
                }
                connectionStatus.observeStatus {
                    root.alpha = if (it) 1.0f else 0.3f
                }
            }
        }
    }
}