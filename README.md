# Android-wear-os-sensor-bluetoothSocket

안드로이드 OS 기반의 wearable에서 필요한 센서 데이터를 다른 기기에 전달하는 방법 - bluetooth socket 사용

#### Android 공식에서 권장하는 방법은 아님.
#### MessageClient나 DataClient Api를 사용을 권장.

#### 앱 실행 필수 사항
- 센서 데이터를 받을 수 있는 서버가 필요함(ex - android 휴대폰)
- 앱 실행 전 서버는 블루투스 소켓을 먼저 열어줘야 한다.


<details>
<summary>SDK</summary>

```
android {
    compileSdk 33

    defaultConfig {
        applicationId "com.example.wear_os_sensor"
        minSdk 28
        targetSdk 33
        versionCode 1
        versionName "1.0"

    }
}
```
</details>
<details>
<summary>dependencies</summary>

```
dependencies {
    implementation "androidx.annotation:annotation:1.5.0"

    implementation 'androidx.core:core-ktx:1.7.0'
    implementation 'com.google.android.gms:play-services-wearable:18.0.0'
    implementation 'androidx.percentlayout:percentlayout:1.0.0'
    implementation 'androidx.legacy:legacy-support-v4:1.0.0'
    implementation 'androidx.recyclerview:recyclerview:1.2.1'
    implementation 'androidx.wear:wear:1.1.0'
    implementation 'androidx.appcompat:appcompat:1.6.0'
    implementation 'com.google.android.material:material:1.4.0'
    implementation 'androidx.constraintlayout:constraintlayout:2.1.4'
}
```
</details>
<details>
<summary>Manifest</summary>

```
<uses-permission android:name="android.permission.WAKE_LOCK" />
<uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />
<uses-permission android:name="android.permission.BLUETOOTH" android:maxSdkVersion="30"/>
<uses-permission android:name="android.permission.BLUETOOTH_ADMIN" android:maxSdkVersion="30"/>
<uses-permission android:name="android.permission.BLUETOOTH_SCAN" android:usesPermissionFlags="neverForLocation" />
<uses-permission android:name="android.permission.BLUETOOTH_ADVERTISE" />
<uses-permission android:name="android.permission.BLUETOOTH_CONNECT" />
<uses-permission android:name="android.permission.BODY_SENSORS" />
<uses-permission android:name="android.permission.ACTIVITY_RECOGNITION" />
<uses-permission android:name="android.permission.FOREGROUND_SERVICE" />
```
</details>
<details>
<summary>Server Code</summary>

```
package com.example.bluetoothtest

import android.annotation.SuppressLint
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothServerSocket
import android.bluetooth.BluetoothSocket
import android.util.Log
import java.io.IOException
import java.util.*

// 블루투스에서 서버의 역할을 수행하는 스레드
@SuppressLint("MissingPermission")
class AcceptThread(private val bluetoothAdapter: BluetoothAdapter, private var adapter: RecyclerViewAdapter,
    var list: ItemList): Thread() {
    private lateinit var serverSocket: BluetoothServerSocket

    companion object {
        private const val TAG = "ACCEPT_THREAD"
        private const val SOCKET_NAME = "server"
        private val MY_UUID = UUID.fromString("00001101-0000-1000-8000-00805f9b34fb")
    }

    init {
        try {
            // 서버 소켓
            serverSocket = bluetoothAdapter.listenUsingRfcommWithServiceRecord(SOCKET_NAME, MY_UUID)
        } catch(e: Exception) {
            Log.d(TAG, e.message.toString())
        }
    }

    override fun run() {
        var socket: BluetoothSocket? = null
        Log.d("success", socket.toString())
        while(true) {
            try {
                // 클라이언트 소켓
                socket = serverSocket.accept()
                Log.d("success", socket.toString())
                Thread.sleep(300)
            } catch (e: IOException) {
                Log.d(TAG, e.message.toString())
            }

            socket?.let {
                val mInputputStream = socket.inputStream
                val buffer = ByteArray(1024)
                var bytes: Int
                while(true){
                    //get value from wearable
                }
                serverSocket.close()
            }
            break
        }
    }

    fun cancel() {
        try {
            serverSocket.close()
        } catch (e: IOException) {
            Log.d(TAG, e.message.toString())
        }
    }
}
```
</details>

![Frame 1](https://user-images.githubusercontent.com/97783148/218670996-dbb65fd2-c6a6-4219-ab4b-0ca3c7721d94.png)
