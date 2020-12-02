package com.example.bleapplication.presentation.ui.details

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.bleapplication.databinding.ItemCharBinding
import com.example.bleapplication.model.BleCharacteristic
import com.example.bleapplication.model.BleState

class CharListAdapter(private val bleState: BleState) :
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
                charUuid.text = char.uuid?.toString()?.take(6)
                btnNotify.apply {
                    visibility =
                        if (char.properties.any { it == "Notifiable" }) View.VISIBLE else View.GONE
                    setOnClickListener {
                        bleState.gatt?.setCharacteristicNotification(
                            char.bluetoothGattCharacteristic,
                            true
                        )
                    }
                }
                btnRead.apply {
                    visibility =
                        if (char.properties.any { it == "Readable" }) View.VISIBLE else View.GONE
                    setOnClickListener {
                        bleState.gatt?.readCharacteristic(
                            char.bluetoothGattCharacteristic
                        )
                    }
                }
                btnWrite.apply {
                    visibility =
                        if (char.properties.any { it == "Writable" }) View.VISIBLE else View.GONE
                    setOnClickListener {
                        bleState.gatt?.writeCharacteristic(
                            char.bluetoothGattCharacteristic
                        )
                    }
                }
            }
        }
    }

    interface Callback {
        fun onReadCharClicked(char: BleCharacteristic)
    }
}