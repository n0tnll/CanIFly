package com.shv.canifly

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.multidex.MultiDex
import dagger.hilt.android.HiltAndroidApp
import dji.v5.common.error.IDJIError
import dji.v5.common.register.DJISDKInitEvent
import dji.v5.manager.SDKManager
import dji.v5.manager.interfaces.SDKManagerCallback

@HiltAndroidApp
class CanIFlyApplication : Application() {

    private val TAG = this::class.simpleName

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        MultiDex.install(this)
        com.secneo.sdk.Helper.install(this)
    }

    override fun onCreate() {
        super.onCreate()
        // Initialize MSDK

        SDKManager.getInstance().init(this, object : SDKManagerCallback {
            override fun onInitProcess(event: DJISDKInitEvent?, totalProcess: Int) {
                Log.i(TAG, "onInitProcess: ")
                if (event == DJISDKInitEvent.INITIALIZE_COMPLETE)
                    SDKManager.getInstance().registerApp()
            }

            override fun onRegisterSuccess() {
                Log.i(TAG, "onRegisterSuccess: ")
            }

            override fun onRegisterFailure(error: IDJIError?) {
                Log.i(TAG, "onRegisterFailure: ")
            }

            override fun onProductDisconnect(productId: Int) {
                Log.i(TAG, "onProductDisconnect: ")
            }

            override fun onProductConnect(productId: Int) {
                Log.i(TAG, "onProductConnect: ")
            }

            override fun onProductChanged(productId: Int) {
                Log.i(TAG, "onProductChanged: ")
            }

            override fun onDatabaseDownloadProgress(current: Long, total: Long) {
                Log.i(TAG, "onDatabaseDownloadProgress: ${current/total}")
            }

        })
    }
}