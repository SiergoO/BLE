package com.example.bleapplication.presentation.ui.devices

import android.bluetooth.*
import android.content.Context
import com.example.bleapplication.presentation.components.ble.BleManager
import com.example.bleapplication.model.BleDevice
import com.example.bleapplication.model.BleState
import com.example.bleapplication.presentation.utils.ble.toBleDevice
import com.example.bleapplication.presentation.utils.convertToString
import com.example.bleapplication.presentation.utils.filterBrackets
import com.example.bleapplication.presentation.utils.toast
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.ObservableEmitter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject
import java.util.*
import java.util.concurrent.TimeUnit

class AndroidBleManager(private val context: Context, private var bleState: BleState) : BleManager {

    companion object {
        private const val SCAN_DURATION = 20L
    }

    private val bluetoothLeAdapter = BluetoothAdapter.getDefaultAdapter()
    private val bluetoothLeScanner = bluetoothLeAdapter.bluetoothLeScanner
    private var bluetoothGatt: BluetoothGatt? = null
    private val mDeviceList: ArrayList<BluetoothDevice> = arrayListOf()
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
        }.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())

    override fun disconnect() {
        bluetoothGatt?.disconnect()
    }

    private fun scanDevices(
        seconds: Long = SCAN_DURATION,
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
        .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())

    inner class BleGattCallback(private val emitter: ObservableEmitter<Boolean>) :
        BluetoothGattCallback() {
        override fun onConnectionStateChange(gatt: BluetoothGatt?, status: Int, newState: Int) {
            super.onConnectionStateChange(gatt, status, newState)
            when (newState) {
                BluetoothProfile.STATE_CONNECTED -> {
                    bleState.bleDevice =
                        mDeviceList.first { it.address == gatt?.device?.address }.toBleDevice()
                    gatt?.discoverServices()
                }
                BluetoothProfile.STATE_DISCONNECTED -> {
                    emitter.onNext(false)
                    gatt?.disconnect()
                }
            }
        }

        override fun onServicesDiscovered(gatt: BluetoothGatt?, status: Int) {
            super.onServicesDiscovered(gatt, status)
            bleState.gatt = gatt
            this@AndroidBleManager.bluetoothGatt = gatt
            emitter.onNext(true)
        }

        override fun onCharacteristicChanged(
            gatt: BluetoothGatt?,
            characteristic: BluetoothGattCharacteristic?
        ) {
            super.onCharacteristicChanged(gatt, characteristic)
            context.toast("Notifying: ${characteristic?.value.convertToString()}".filterBrackets())
        }

        override fun onCharacteristicRead(
            gatt: BluetoothGatt?,
            characteristic: BluetoothGattCharacteristic?,
            status: Int
        ) {
            super.onCharacteristicRead(gatt, characteristic, status)
            context.toast("Reading: ${characteristic?.value.convertToString()}".filterBrackets())
        }

        override fun onCharacteristicWrite(
            gatt: BluetoothGatt?,
            characteristic: BluetoothGattCharacteristic?,
            status: Int
        ) {
            super.onCharacteristicWrite(gatt, characteristic, status)
            context.toast("Writing: ${characteristic?.value.convertToString()}".filterBrackets())
        }
    }
}