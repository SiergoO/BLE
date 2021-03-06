package com.example.bleapplication.presentation.utils.ble

import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothGattCharacteristic
import android.bluetooth.BluetoothGattService
import com.example.bleapplication.model.BleCharacteristic
import com.example.bleapplication.model.BleDevice
import com.example.bleapplication.model.BleService
import java.util.*

object BleUtilsData {

    val HEX_ARRAY = "0123456789ABCDEF".toCharArray()
    const val RADIX = 16
    const val WHOLE = 0xFF
    const val SECOND = 0x0F

    val all_services = mapOf<UUID, String>(
        UUID.fromString("00001809-0000-1000-8000-00805f9b34fb") to "Health Thermometer",
        UUID.fromString("00001800-0000-1000-8000-00805f9b34fb") to "Generic Access",
        UUID.fromString("00001801-0000-1000-8000-00805f9b34fb") to "Generic Attribute",
        UUID.fromString("00001802-0000-1000-8000-00805f9b34fb") to "Immediate Alert",
        UUID.fromString("00001803-0000-1000-8000-00805f9b34fb") to "Link Loss",
        UUID.fromString("00001804-0000-1000-8000-00805f9b34fb") to "Tx Power",
        UUID.fromString("00001805-0000-1000-8000-00805f9b34fb") to "Current Time Service",
        UUID.fromString("00001806-0000-1000-8000-00805f9b34fb") to "Reference Time Update Service",
        UUID.fromString("00001807-0000-1000-8000-00805f9b34fb") to "Next DST Change Service",
        UUID.fromString("00001808-0000-1000-8000-00805f9b34fb") to "Glucose",
        UUID.fromString("00001809-0000-1000-8000-00805f9b34fb") to "Health Thermometer",
        UUID.fromString("0000180a-0000-1000-8000-00805f9b34fb") to "Device Information",
        UUID.fromString("0000180d-0000-1000-8000-00805f9b34fb") to "Heart Rate",
        UUID.fromString("0000180e-0000-1000-8000-00805f9b34fb") to "Phone Alert Status Service",
        UUID.fromString("0000180f-0000-1000-8000-00805f9b34fb") to "Battery Service",
        UUID.fromString("00001810-0000-1000-8000-00805f9b34fb") to "Blood Pressure",
        UUID.fromString("00001811-0000-1000-8000-00805f9b34fb") to "Alert Notification Service",
        UUID.fromString("00001812-0000-1000-8000-00805f9b34fb") to "Human Interface Device",
        UUID.fromString("00001813-0000-1000-8000-00805f9b34fb") to "Scan Parameters",
        UUID.fromString("00001814-0000-1000-8000-00805f9b34fb") to "Running Speed and Cadence",
        UUID.fromString("00001815-0000-1000-8000-00805f9b34fb") to "Automation IO",
        UUID.fromString("00001816-0000-1000-8000-00805f9b34fb") to "Cycling Speed and Cadence",
        UUID.fromString("00001818-0000-1000-8000-00805f9b34fb") to "Cycling Power",
        UUID.fromString("00001819-0000-1000-8000-00805f9b34fb") to "Location and Navigation",
        UUID.fromString("0000181a-0000-1000-8000-00805f9b34fb") to "Environmental Sensing",
        UUID.fromString("0000181b-0000-1000-8000-00805f9b34fb") to "Body Composition",
        UUID.fromString("0000181c-0000-1000-8000-00805f9b34fb") to "User Data",
        UUID.fromString("0000181d-0000-1000-8000-00805f9b34fb") to "Weight Scale",
        UUID.fromString("0000181e-0000-1000-8000-00805f9b34fb") to "Bond Management Service",
        UUID.fromString("0000181f-0000-1000-8000-00805f9b34fb") to "Continuous Glucose Monitoring",
        UUID.fromString("00001820-0000-1000-8000-00805f9b34fb") to "Internet Protocol Support Service",
        UUID.fromString("00001821-0000-1000-8000-00805f9b34fb") to "Indoor Positioning",
        UUID.fromString("00001822-0000-1000-8000-00805f9b34fb") to "Pulse Oximeter Service",
        UUID.fromString("00001823-0000-1000-8000-00805f9b34fb") to "HTTP Proxy",
        UUID.fromString("00001824-0000-1000-8000-00805f9b34fb") to "Transport Discovery",
        UUID.fromString("00001825-0000-1000-8000-00805f9b34fb") to "Object Transfer Service",
        UUID.fromString("00001826-0000-1000-8000-00805f9b34fb") to "Fitness Machine",
        UUID.fromString("00001827-0000-1000-8000-00805f9b34fb") to "Mesh Provisioning Service",
        UUID.fromString("00001828-0000-1000-8000-00805f9b34fb") to "Mesh Proxy Service",
        UUID.fromString("00001829-0000-1000-8000-00805f9b34fb") to "Reconnection Configuration"
    )
    val all_characteristics = mapOf<UUID, String>(
        UUID.fromString("00002a00-0000-1000-8000-00805f9b34fb") to "Device Name",
        UUID.fromString("00002a01-0000-1000-8000-00805f9b34fb") to "Appearance",
        UUID.fromString("00002a02-0000-1000-8000-00805f9b34fb") to "Peripheral Privacy Flag",
        UUID.fromString("00002a03-0000-1000-8000-00805f9b34fb") to "Reconnection Address",
        UUID.fromString("00002a04-0000-1000-8000-00805f9b34fb") to "Peripheral Preferred Connection Parameters",
        UUID.fromString("00002a05-0000-1000-8000-00805f9b34fb") to "Service Changed",
        UUID.fromString("00002a06-0000-1000-8000-00805f9b34fb") to "Alert Level",
        UUID.fromString("00002a07-0000-1000-8000-00805f9b34fb") to "Tx Power Level",
        UUID.fromString("00002a08-0000-1000-8000-00805f9b34fb") to "Date Time",
        UUID.fromString("00002a09-0000-1000-8000-00805f9b34fb") to "Day of Week",
        UUID.fromString("00002a0a-0000-1000-8000-00805f9b34fb") to "Day Date Time",
        UUID.fromString("00002a0b-0000-1000-8000-00805f9b34fb") to "Exact Time 100",
        UUID.fromString("00002a0c-0000-1000-8000-00805f9b34fb") to "Exact Time 256",
        UUID.fromString("00002a0d-0000-1000-8000-00805f9b34fb") to "DST Offset",
        UUID.fromString("00002a0e-0000-1000-8000-00805f9b34fb") to "Time Zone",
        UUID.fromString("00002a0f-0000-1000-8000-00805f9b34fb") to "Local Tim Information",
        UUID.fromString("00002a10-0000-1000-8000-00805f9b34fb") to "Secondary Time Zone",
        UUID.fromString("00002a11-0000-1000-8000-00805f9b34fb") to "Time with DST",
        UUID.fromString("00002a12-0000-1000-8000-00805f9b34fb") to "Time Accuracy",
        UUID.fromString("00002a13-0000-1000-8000-00805f9b34fb") to "Time Source",
        UUID.fromString("00002a14-0000-1000-8000-00805f9b34fb") to "Reference Tme Information",
        UUID.fromString("00002a15-0000-1000-8000-00805f9b34fb") to "Time Broadcast",
        UUID.fromString("00002a16-0000-1000-8000-00805f9b34fb") to "Time Update Control Point",
        UUID.fromString("00002a17-0000-1000-8000-00805f9b34fb") to "Time Update State",
        UUID.fromString("00002a18-0000-1000-8000-00805f9b34fb") to "Glucose Measurement",
        UUID.fromString("00002a19-0000-1000-8000-00805f9b34fb") to "Battery Level",
        UUID.fromString("00002a1a-0000-1000-8000-00805f9b34fb") to "Battery Power State",
        UUID.fromString("00002a1b-0000-1000-8000-00805f9b34fb") to "Battery Level State",
        UUID.fromString("00002a1c-0000-1000-8000-00805f9b34fb") to "Temperature Measurement",
        UUID.fromString("00002a1d-0000-1000-8000-00805f9b34fb") to "Temperature Type",
        UUID.fromString("00002a1e-0000-1000-8000-00805f9b34fb") to "Intermediate Temperature",
        UUID.fromString("00002a1f-0000-1000-8000-00805f9b34fb") to "Temperature Celsius",
        UUID.fromString("00002a20-0000-1000-8000-00805f9b34fb") to "Temperature Fahrenheit",
        UUID.fromString("00002a21-0000-1000-8000-00805f9b34fb") to "Measurement Interval",
        UUID.fromString("00002a22-0000-1000-8000-00805f9b34fb") to "Boot Keyboard Input Report",
        UUID.fromString("00002a23-0000-1000-8000-00805f9b34fb") to "System ID",
        UUID.fromString("00002a24-0000-1000-8000-00805f9b34fb") to "Model Number String",
        UUID.fromString("00002a25-0000-1000-8000-00805f9b34fb") to "Serial Number String",
        UUID.fromString("00002a26-0000-1000-8000-00805f9b34fb") to "Firmware Revision String",
        UUID.fromString("00002a27-0000-1000-8000-00805f9b34fb") to "Hardware Revision String",
        UUID.fromString("00002a28-0000-1000-8000-00805f9b34fb") to "Software Revision String",
        UUID.fromString("00002a29-0000-1000-8000-00805f9b34fb") to "Manufacturer Name String",
        UUID.fromString("00002a2a-0000-1000-8000-00805f9b34fb") to "IEEE 11073-20601 Regulatory Certification Data List",
        UUID.fromString("00002a2b-0000-1000-8000-00805f9b34fb") to "Current Time",
        UUID.fromString("00002a2c-0000-1000-8000-00805f9b34fb") to "Magnetic Declination",
        UUID.fromString("00002a2f-0000-1000-8000-00805f9b34fb") to "Position 2D",
        UUID.fromString("00002a30-0000-1000-8000-00805f9b34fb") to "Position 3D",
        UUID.fromString("00002a31-0000-1000-8000-00805f9b34fb") to "Scan Refresh",
        UUID.fromString("00002a32-0000-1000-8000-00805f9b34fb") to "Boot Keyboard Output Report",
        UUID.fromString("00002a33-0000-1000-8000-00805f9b34fb") to "Boot Mouse Input Report",
        UUID.fromString("00002a34-0000-1000-8000-00805f9b34fb") to "Glucose Measurement Context",
        UUID.fromString("00002a35-0000-1000-8000-00805f9b34fb") to "Blood Pressure Measurement",
        UUID.fromString("00002a36-0000-1000-8000-00805f9b34fb") to "Intermediate Cuff Pressure",
        UUID.fromString("00002a37-0000-1000-8000-00805f9b34fb") to "Heart Rate Measurement",
        UUID.fromString("00002a38-0000-1000-8000-00805f9b34fb") to "Body Sensor Location",
        UUID.fromString("00002a39-0000-1000-8000-00805f9b34fb") to "Heart Rate Control Point",
        UUID.fromString("00002a3a-0000-1000-8000-00805f9b34fb") to "Removable",
        UUID.fromString("00002a3b-0000-1000-8000-00805f9b34fb") to "Service Required",
        UUID.fromString("00002a3c-0000-1000-8000-00805f9b34fb") to "Scientific Temperature Celsius",
        UUID.fromString("00002a3d-0000-1000-8000-00805f9b34fb") to "String",
        UUID.fromString("00002a3e-0000-1000-8000-00805f9b34fb") to "Network Availability",
        UUID.fromString("00002a3f-0000-1000-8000-00805f9b34fb") to "Alert Status",
        UUID.fromString("00002a40-0000-1000-8000-00805f9b34fb") to "Ringer Control point",
        UUID.fromString("00002a41-0000-1000-8000-00805f9b34fb") to "Ringer Setting",
        UUID.fromString("00002a42-0000-1000-8000-00805f9b34fb") to "Alert Category ID Bit Mask",
        UUID.fromString("00002a43-0000-1000-8000-00805f9b34fb") to "Alert Category ID",
        UUID.fromString("00002a44-0000-1000-8000-00805f9b34fb") to "Alert Notification Control Point",
        UUID.fromString("00002a45-0000-1000-8000-00805f9b34fb") to "Unread Alert Status",
        UUID.fromString("00002a46-0000-1000-8000-00805f9b34fb") to "New Alert",
        UUID.fromString("00002a47-0000-1000-8000-00805f9b34fb") to "Supported New Alert Category",
        UUID.fromString("00002a48-0000-1000-8000-00805f9b34fb") to "Supported Unread Alert Category",
        UUID.fromString("00002a49-0000-1000-8000-00805f9b34fb") to "Blood Pressure Feature",
        UUID.fromString("00002a4a-0000-1000-8000-00805f9b34fb") to "HID Information",
        UUID.fromString("00002a4b-0000-1000-8000-00805f9b34fb") to "Report Map",
        UUID.fromString("00002a4c-0000-1000-8000-00805f9b34fb") to "HID Control Point",
        UUID.fromString("00002a4d-0000-1000-8000-00805f9b34fb") to "Report",
        UUID.fromString("00002a4e-0000-1000-8000-00805f9b34fb") to "Protocol Mode",
        UUID.fromString("00002a4f-0000-1000-8000-00805f9b34fb") to "Scan Interval Window",
        UUID.fromString("00002a50-0000-1000-8000-00805f9b34fb") to "PnP ID",
        UUID.fromString("00002a51-0000-1000-8000-00805f9b34fb") to "Glucose Feature",
        UUID.fromString("00002a52-0000-1000-8000-00805f9b34fb") to "Record Access Control Point",
        UUID.fromString("00002a53-0000-1000-8000-00805f9b34fb") to "RSC Measurement",
        UUID.fromString("00002a54-0000-1000-8000-00805f9b34fb") to "RSC Feature",
        UUID.fromString("00002a55-0000-1000-8000-00805f9b34fb") to "SC Control Point",
        UUID.fromString("00002a56-0000-1000-8000-00805f9b34fb") to "Digital",
        UUID.fromString("00002a57-0000-1000-8000-00805f9b34fb") to "Digital Output",
        UUID.fromString("00002a58-0000-1000-8000-00805f9b34fb") to "Analog",
        UUID.fromString("00002a59-0000-1000-8000-00805f9b34fb") to "Analog Output",
        UUID.fromString("00002a5a-0000-1000-8000-00805f9b34fb") to "Aggregate",
        UUID.fromString("00002a5b-0000-1000-8000-00805f9b34fb") to "CSC Measurement",
        UUID.fromString("00002a5c-0000-1000-8000-00805f9b34fb") to "CSC Feature",
        UUID.fromString("00002a5d-0000-1000-8000-00805f9b34fb") to "Sensor Location",
        UUID.fromString("00002a5e-0000-1000-8000-00805f9b34fb") to "PLX Spot-Check Measurement",
        UUID.fromString("00002a5f-0000-1000-8000-00805f9b34fb") to "PLX Continuous Measurement Characteristic",
        UUID.fromString("00002a60-0000-1000-8000-00805f9b34fb") to "PLX Features",
        UUID.fromString("00002a62-0000-1000-8000-00805f9b34fb") to "Pulse Oximetry Control Point",
        UUID.fromString("00002a63-0000-1000-8000-00805f9b34fb") to "Cycling Power Measurement",
        UUID.fromString("00002a64-0000-1000-8000-00805f9b34fb") to "Cycling Power Vector",
        UUID.fromString("00002a65-0000-1000-8000-00805f9b34fb") to "Cycling Power Feature",
        UUID.fromString("00002a66-0000-1000-8000-00805f9b34fb") to "Cycling Power Control Point",
        UUID.fromString("00002a67-0000-1000-8000-00805f9b34fb") to "Location and Speed Characteristic",
        UUID.fromString("00002a68-0000-1000-8000-00805f9b34fb") to "Navigation",
        UUID.fromString("00002a69-0000-1000-8000-00805f9b34fb") to "Position Quality",
        UUID.fromString("00002a6a-0000-1000-8000-00805f9b34fb") to "LN Feature",
        UUID.fromString("00002a6b-0000-1000-8000-00805f9b34fb") to "LN Control Point",
        UUID.fromString("00002a6c-0000-1000-8000-00805f9b34fb") to "Elevation",
        UUID.fromString("00002a6d-0000-1000-8000-00805f9b34fb") to "Pressure",
        UUID.fromString("00002a6e-0000-1000-8000-00805f9b34fb") to "Temperature",
        UUID.fromString("00002a6f-0000-1000-8000-00805f9b34fb") to "Humidity",
        UUID.fromString("00002a70-0000-1000-8000-00805f9b34fb") to "True Wind Speed",
        UUID.fromString("00002a71-0000-1000-8000-00805f9b34fb") to "True Wind Direction",
        UUID.fromString("00002a72-0000-1000-8000-00805f9b34fb") to "Apparent Wind Speed",
        UUID.fromString("00002a73-0000-1000-8000-00805f9b34fb") to "Apparent Wind Direction",
        UUID.fromString("00002a74-0000-1000-8000-00805f9b34fb") to "Gust Factor",
        UUID.fromString("00002a75-0000-1000-8000-00805f9b34fb") to "Pollen Concentration",
        UUID.fromString("00002a76-0000-1000-8000-00805f9b34fb") to "UV Index",
        UUID.fromString("00002a77-0000-1000-8000-00805f9b34fb") to "Irradiance",
        UUID.fromString("00002a78-0000-1000-8000-00805f9b34fb") to "Rainfall",
        UUID.fromString("00002a79-0000-1000-8000-00805f9b34fb") to "Wind Chill",
        UUID.fromString("00002a7a-0000-1000-8000-00805f9b34fb") to "Heat Index",
        UUID.fromString("00002a7b-0000-1000-8000-00805f9b34fb") to "Dew Point",
        UUID.fromString("00002a7d-0000-1000-8000-00805f9b34fb") to "Descriptor Value Changed",
        UUID.fromString("00002a7e-0000-1000-8000-00805f9b34fb") to "Aerobic Heart Rate Lower Limit",
        UUID.fromString("00002a7f-0000-1000-8000-00805f9b34fb") to "Aerobic Threshold",
        UUID.fromString("00002a80-0000-1000-8000-00805f9b34fb") to "Age",
        UUID.fromString("00002a81-0000-1000-8000-00805f9b34fb") to "Anaerobic Heart Rate Lower Limit",
        UUID.fromString("00002a82-0000-1000-8000-00805f9b34fb") to "Anaerobic Heart Rate Upper Limit",
        UUID.fromString("00002a83-0000-1000-8000-00805f9b34fb") to "Anaerobic Threshold",
        UUID.fromString("00002a84-0000-1000-8000-00805f9b34fb") to "Aerobic Heart Rate Upper Limit",
        UUID.fromString("00002a85-0000-1000-8000-00805f9b34fb") to "Date of Birth",
        UUID.fromString("00002a86-0000-1000-8000-00805f9b34fb") to "Date of Threshold Assessment",
        UUID.fromString("00002a87-0000-1000-8000-00805f9b34fb") to "Email Address",
        UUID.fromString("00002a88-0000-1000-8000-00805f9b34fb") to "Fat Burn Heart Rate Lower Limit",
        UUID.fromString("00002a89-0000-1000-8000-00805f9b34fb") to "Fat Burn Heart Rate Upper Limit",
        UUID.fromString("00002a8a-0000-1000-8000-00805f9b34fb") to "First Name",
        UUID.fromString("00002a8b-0000-1000-8000-00805f9b34fb") to "Five Zone Heart Rate Limits",
        UUID.fromString("00002a8c-0000-1000-8000-00805f9b34fb") to "Gender",
        UUID.fromString("00002a8d-0000-1000-8000-00805f9b34fb") to "Heart Rate Max",
        UUID.fromString("00002a8e-0000-1000-8000-00805f9b34fb") to "Height",
        UUID.fromString("00002a8f-0000-1000-8000-00805f9b34fb") to "Hip Circumference",
        UUID.fromString("00002a90-0000-1000-8000-00805f9b34fb") to "Last Name",
        UUID.fromString("00002a91-0000-1000-8000-00805f9b34fb") to "Maximum Recommended Heart Rate",
        UUID.fromString("00002a92-0000-1000-8000-00805f9b34fb") to "Resting Heart Rate",
        UUID.fromString("00002a93-0000-1000-8000-00805f9b34fb") to "Sport Type for Aerobic and Anaerobic Thresholds",
        UUID.fromString("00002a94-0000-1000-8000-00805f9b34fb") to "Three Zone Heart Rate Limits",
        UUID.fromString("00002a95-0000-1000-8000-00805f9b34fb") to "Two Zone Heart Rate Limit",
        UUID.fromString("00002a96-0000-1000-8000-00805f9b34fb") to "VO2 Max",
        UUID.fromString("00002a97-0000-1000-8000-00805f9b34fb") to "Waist Circumference",
        UUID.fromString("00002a98-0000-1000-8000-00805f9b34fb") to "Weight",
        UUID.fromString("00002a99-0000-1000-8000-00805f9b34fb") to "Database Change Increment",
        UUID.fromString("00002a9a-0000-1000-8000-00805f9b34fb") to "User Index",
        UUID.fromString("00002a9b-0000-1000-8000-00805f9b34fb") to "Body Composition Feature",
        UUID.fromString("00002a9c-0000-1000-8000-00805f9b34fb") to "Body Composition Measurement",
        UUID.fromString("00002a9d-0000-1000-8000-00805f9b34fb") to "Weight Measurement",
        UUID.fromString("00002a9e-0000-1000-8000-00805f9b34fb") to "Weight Scale Feature",
        UUID.fromString("00002a9f-0000-1000-8000-00805f9b34fb") to "User Control Point",
        UUID.fromString("00002aa0-0000-1000-8000-00805f9b34fb") to "Magnetic Flux Density - 2D",
        UUID.fromString("00002aa1-0000-1000-8000-00805f9b34fb") to "Magnetic Flux Density - 3D",
        UUID.fromString("00002aa2-0000-1000-8000-00805f9b34fb") to "Language",
        UUID.fromString("00002aa3-0000-1000-8000-00805f9b34fb") to "Barometric Pressure Trend",
        UUID.fromString("00002aa4-0000-1000-8000-00805f9b34fb") to "Bond Management Control Point",
        UUID.fromString("00002aa5-0000-1000-8000-00805f9b34fb") to "Bond Management Features",
        UUID.fromString("00002aa6-0000-1000-8000-00805f9b34fb") to "Central Address Resolution",
        UUID.fromString("00002aa7-0000-1000-8000-00805f9b34fb") to "CGM Measurement",
        UUID.fromString("00002aa8-0000-1000-8000-00805f9b34fb") to "CGM Feature",
        UUID.fromString("00002aa9-0000-1000-8000-00805f9b34fb") to "CGM Status",
        UUID.fromString("00002aaa-0000-1000-8000-00805f9b34fb") to "CGM Session Start Time",
        UUID.fromString("00002aab-0000-1000-8000-00805f9b34fb") to "CGM Session Run Time",
        UUID.fromString("00002aac-0000-1000-8000-00805f9b34fb") to "CGM Specific Ops Control Point",
        UUID.fromString("00002aad-0000-1000-8000-00805f9b34fb") to "Indoor Positioning Configuration",
        UUID.fromString("00002aae-0000-1000-8000-00805f9b34fb") to "Latitude",
        UUID.fromString("00002aaf-0000-1000-8000-00805f9b34fb") to "Longitude",
        UUID.fromString("00002ab0-0000-1000-8000-00805f9b34fb") to "Local North Coordinate",
        UUID.fromString("00002ab1-0000-1000-8000-00805f9b34fb") to "Local East Coordinate",
        UUID.fromString("00002ab2-0000-1000-8000-00805f9b34fb") to "Floor Number",
        UUID.fromString("00002ab3-0000-1000-8000-00805f9b34fb") to "Altitude",
        UUID.fromString("00002ab4-0000-1000-8000-00805f9b34fb") to "Uncertainty",
        UUID.fromString("00002ab5-0000-1000-8000-00805f9b34fb") to "Location Name",
        UUID.fromString("00002ab6-0000-1000-8000-00805f9b34fb") to "URI",
        UUID.fromString("00002ab7-0000-1000-8000-00805f9b34fb") to "HTTP Headers",
        UUID.fromString("00002ab8-0000-1000-8000-00805f9b34fb") to "HTTP Status Code",
        UUID.fromString("00002ab9-0000-1000-8000-00805f9b34fb") to "HTTP Entity Body",
        UUID.fromString("00002aba-0000-1000-8000-00805f9b34fb") to "HTTP Control Point",
        UUID.fromString("00002abb-0000-1000-8000-00805f9b34fb") to "HTTPS Security",
        UUID.fromString("00002abc-0000-1000-8000-00805f9b34fb") to "TDS Control Point",
        UUID.fromString("00002abd-0000-1000-8000-00805f9b34fb") to "OTS Feature",
        UUID.fromString("00002abe-0000-1000-8000-00805f9b34fb") to "Object Name",
        UUID.fromString("00002abf-0000-1000-8000-00805f9b34fb") to "Object Type",
        UUID.fromString("00002ac0-0000-1000-8000-00805f9b34fb") to "Object Size",
        UUID.fromString("00002ac1-0000-1000-8000-00805f9b34fb") to "Object First-Created",
        UUID.fromString("00002ac2-0000-1000-8000-00805f9b34fb") to "Object Last-Modified",
        UUID.fromString("00002ac3-0000-1000-8000-00805f9b34fb") to "Object ID",
        UUID.fromString("00002ac4-0000-1000-8000-00805f9b34fb") to "Object Properties",
        UUID.fromString("00002ac5-0000-1000-8000-00805f9b34fb") to "Object Action Control Point",
        UUID.fromString("00002ac6-0000-1000-8000-00805f9b34fb") to "Object List Control Point",
        UUID.fromString("00002ac7-0000-1000-8000-00805f9b34fb") to "Object List Filter",
        UUID.fromString("00002ac8-0000-1000-8000-00805f9b34fb") to "Object Changed",
        UUID.fromString("00002ac9-0000-1000-8000-00805f9b34fb") to "Resolvable Private Address Only",
        UUID.fromString("00002acc-0000-1000-8000-00805f9b34fb") to "Fitness Machine Feature",
        UUID.fromString("00002acd-0000-1000-8000-00805f9b34fb") to "Treadmill Data",
        UUID.fromString("00002ace-0000-1000-8000-00805f9b34fb") to "Cross Trainer Data",
        UUID.fromString("00002acf-0000-1000-8000-00805f9b34fb") to "Step Climber Data",
        UUID.fromString("00002ad0-0000-1000-8000-00805f9b34fb") to "Stair Climber Data",
        UUID.fromString("00002ad1-0000-1000-8000-00805f9b34fb") to "Rower Data",
        UUID.fromString("00002ad2-0000-1000-8000-00805f9b34fb") to "Indoor Bike Data",
        UUID.fromString("00002ad3-0000-1000-8000-00805f9b34fb") to "Training Status",
        UUID.fromString("00002ad4-0000-1000-8000-00805f9b34fb") to "Supported Speed Range",
        UUID.fromString("00002ad5-0000-1000-8000-00805f9b34fb") to "Supported Inclination Range",
        UUID.fromString("00002ad6-0000-1000-8000-00805f9b34fb") to "Supported Resistance Level Range",
        UUID.fromString("00002ad7-0000-1000-8000-00805f9b34fb") to "Supported Heart Rate Range",
        UUID.fromString("00002ad8-0000-1000-8000-00805f9b34fb") to "Supported Power Range",
        UUID.fromString("00002ad9-0000-1000-8000-00805f9b34fb") to "Fitness Machine Control Point",
        UUID.fromString("00002ada-0000-1000-8000-00805f9b34fb") to "Fitness Machine Status",
        UUID.fromString("00002aed-0000-1000-8000-00805f9b34fb") to "Date UTC",
        UUID.fromString("00002b1d-0000-1000-8000-00805f9b34fb") to "RC Feature",
        UUID.fromString("00002b1e-0000-1000-8000-00805f9b34fb") to "RC Settings",
        UUID.fromString("00002b1f-0000-1000-8000-00805f9b34fb") to "Reconnection Configuration Control Point",
        UUID.fromString("00002a1c-0000-1000-8000-00805f9b34fb") to "Temperature Measurement",
        UUID.fromString("00002a1d-0000-1000-8000-00805f9b34fb") to "Temperature Type",
        UUID.fromString("00002a1e-0000-1000-8000-00805f9b34fb") to "Intermediate Temperature",
        UUID.fromString("00002a21-0000-1000-8000-00805f9b34fb") to "Measurement Interval"
    )
}

fun String.hexStringToByteArray(): ByteArray {
    val data = ByteArray(length / 2)
    var i = 0
    while (i < length) {
        data[i / 2] = ((Character.digit(get(i), BleUtilsData.RADIX) shl 4)
                + Character.digit(get(i + 1), BleUtilsData.RADIX)).toByte()
        i += 2
    }
    return data
}

fun BluetoothDevice.toBleDevice() = BleDevice(address, name)

fun BluetoothGattService.toBleService() = BleService(
    uuid,
    BleUtilsData.all_services[uuid] ?: "",
    characteristics.map { it.toBCharacteristic() } as ArrayList<BleCharacteristic>
)

fun BluetoothGattCharacteristic.toBCharacteristic() =
    BleCharacteristic(
        uuid, BleUtilsData.all_characteristics[uuid] ?: "", value.contentToString(),
        properties.toStringProperties(),
        this
    )

private fun Int.toStringProperties(): List<String> {
    val prop = mutableListOf<String>()
    val isWritable =
        (this and (BluetoothGattCharacteristic.PROPERTY_WRITE or BluetoothGattCharacteristic.PROPERTY_WRITE_NO_RESPONSE)) != 0
    val isReadable = (this and BluetoothGattCharacteristic.PROPERTY_READ) != 0
    val isNotifiable = (this and BluetoothGattCharacteristic.PROPERTY_NOTIFY) != 0
    if (isReadable) prop.add("Readable")
    if (isWritable) prop.add("Writable")
    if (isNotifiable) prop.add("Notifiable")
    return prop.toList()
}