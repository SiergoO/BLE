package com.example.bleapplication.presentation.dialog

import android.bluetooth.BluetoothGattCharacteristic
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.DialogFragment
import com.example.bleapplication.R
import com.example.bleapplication.model.BleCharacteristic

class WriteCharacteristicDialog(
    private val callback: Callback,
    private var bleCharacteristic: BleCharacteristic
) :
    DialogFragment() {

    interface Callback {
        fun writeButtonClicked(char: BluetoothGattCharacteristic)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.dialog_write_characteristic, container, false)
        .also { dialog?.requestWindowFeature(Window.FEATURE_NO_TITLE) }

override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    val edit = (view.findViewById(R.id.edit_char) as EditText)
    (view.findViewById(R.id.btn_cancel) as Button).setOnClickListener { dialog?.dismiss() }
    (view.findViewById(R.id.btn_write) as Button).setOnClickListener {
        callback.writeButtonClicked(
            bleCharacteristic.bluetoothGattCharacteristic.apply {
                value = edit.text.toString().toByteArray()
            }
        )
        dismiss()
    }
    super.onViewCreated(view, savedInstanceState)
}

companion object {
    var TAG = "Write Characteristic Dialog"
}
}