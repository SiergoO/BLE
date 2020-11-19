package com.example.bleapplication.presentation.ui.devices

import android.bluetooth.*
import android.bluetooth.le.ScanCallback
import android.content.Context
import android.os.Build
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import com.example.bleapplication.domain.ble.BleManager
import com.example.bleapplication.model.BleDevice
import com.example.bleapplication.model.BleState
import com.example.bleapplication.model.Device
import com.example.bleapplication.presentation.utils.toBleDevice
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.ObservableEmitter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject
import java.util.*
import java.util.concurrent.TimeUnit

class AndroidBleManager (private val context: Context, private val bleState: BleState): BleManager {

    private val bluetoothLeAdapter = BluetoothAdapter.getDefaultAdapter()
    private val bluetoothLeScanner = bluetoothLeAdapter.bluetoothLeScanner
    private var bluetoothGatt: BluetoothGatt? = null
    protected val mDeviceList: ArrayList<BluetoothDevice> = arrayListOf()
    private var mLeScanCallback: LeScanCallback? = null
    private val mCompositeDisposable = CompositeDisposable()


    override fun scan(): Observable<BleDevice> {
        val subject = PublishSubject.create<BleDevice>()
        mDeviceList.clear()
        scanDevices(onFind = { device ->
            if (!subject.hasComplete()) {
                subject.onNext(device.toBleDevice())
                mDeviceList.add(device)
            }
        }, onFinish = { subject.onComplete() })
        return subject
    }

    override fun stopScan() {
        bluetoothLeScanner.stopScan(mLeScanCallback)
    }

    override fun connect(address: String): Observable<Boolean> =
        Observable.create<Boolean> { emitter ->
            val device = BluetoothAdapter.getDefaultAdapter().getRemoteDevice(address)
            bluetoothGatt = device.connectGatt(context, false, BleGattCallback(emitter))
        }
            .subscribeOn(AndroidSchedulers.mainThread())
            .observeOn(Schedulers.io())

    override fun disconnect() {
        bluetoothGatt?.disconnect()
    }

    private fun scanDevices(
        seconds: Long = 20,
        onFind: (BluetoothDevice) -> Unit,
        onFinish: () -> Unit
    ) {
            mLeScanCallback = LeScanCallback(onFind)
            bluetoothLeScanner?.startScan(mLeScanCallback)
            mCompositeDisposable.add(delayedCompletable(seconds).subscribe {
                bluetoothLeScanner?.stopScan(mLeScanCallback)
                onFinish()
            })
    }

    private fun delayedCompletable(seconds: Long): Completable = Completable
        .complete()
        .delay(seconds, TimeUnit.SECONDS)
        .subscribeOn(Schedulers.io())

    inner class BleGattCallback(private val emitter: ObservableEmitter<Boolean>) :
        BluetoothGattCallback() {
        override fun onConnectionStateChange(gatt: BluetoothGatt?, status: Int, newState: Int) {
            super.onConnectionStateChange(gatt, status, newState)
            when (newState) {
                BluetoothProfile.STATE_CONNECTED -> {
                    bleState.bleDevice = mDeviceList.first { it.address == gatt?.device?.address }.toBleDevice()
                    gatt?.discoverServices()
                }
                BluetoothProfile.STATE_DISCONNECTED -> {
                    emitter.onNext(false)
                    bleState.bleDevice = null
                }
            }
        }

        override fun onServicesDiscovered(gatt: BluetoothGatt?, status: Int) {
            super.onServicesDiscovered(gatt, status)
            val service = UUID.fromString("0000180f-0000-1000-8000-00805f9b34fb")
            val char = UUID.fromString("00002a19-0000-1000-8000-00805f9b34fb")
            val batteryChar =
                gatt?.services?.first { it.uuid == service }?.characteristics?.first { it.uuid == char }
            this@AndroidBleManager.bluetoothGatt = gatt
            gatt?.readCharacteristic(batteryChar)
            gatt?.setCharacteristicNotification(batteryChar, true)
            bleState.gatt = gatt
            emitter.onNext(true)
        }

        override fun onCharacteristicChanged(
            gatt: BluetoothGatt?,
            characteristic: BluetoothGattCharacteristic?
        ) {
            super.onCharacteristicChanged(gatt, characteristic)
            Handler(Looper.getMainLooper()).post {
                Toast.makeText(
                    context,
                    characteristic?.value?.contentToString(),
                    Toast.LENGTH_SHORT
                ).show() // decode
            }
        }

        override fun onCharacteristicRead(
            gatt: BluetoothGatt?,
            characteristic: BluetoothGattCharacteristic?,
            status: Int
        ) {
            super.onCharacteristicRead(gatt, characteristic, status)
            Handler(Looper.getMainLooper()).post {
                Toast.makeText(
                    context,
                    characteristic?.value?.contentToString(),
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }
}