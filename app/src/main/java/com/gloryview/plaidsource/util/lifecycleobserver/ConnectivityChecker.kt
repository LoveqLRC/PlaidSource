package com.gloryview.plaidsource.util.lifecycleobserver

import android.net.*
import androidx.lifecycle.*

/**
 *
 * @ProjectName:    PlaidSource
 * @Package:        com.gloryview.plaidsource.util
 * @ClassName:      ConnectivityChecker
 * @Description:     java类作用描述
 * @Author:         Rc
 * @CreateDate:     2019-06-20 11:39
 * @UpdateUser:     更新者
 * @UpdateDate:     2019-06-20 11:39
 * @UpdateRemark:   更新说明
 * @Version:        1.0
 */
class ConnectivityChecker(
    private val connectivityManager: ConnectivityManager
) : LifecycleObserver {

    private var monitoringConnectivity = false

    private val _connectedStatus = MutableLiveData<Boolean>()

    val connectedStatus: LiveData<Boolean>
        get() = _connectedStatus


    private val connectivityCallback = object : ConnectivityManager.NetworkCallback() {
        override fun onAvailable(network: Network) {
            _connectedStatus.postValue(true)
            connectivityManager.unregisterNetworkCallback(this)
            monitoringConnectivity = false
        }

        override fun onLost(network: Network) {
            _connectedStatus.postValue(false)
        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    fun stopMonitoringConnectivity() {
        if (monitoringConnectivity) {
            connectivityManager.unregisterNetworkCallback(connectivityCallback)
            monitoringConnectivity = false
        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    fun startMonitoringConnectivity() {
        val activeNetworkInfo: NetworkInfo? = connectivityManager.activeNetworkInfo
        val connected = activeNetworkInfo != null && activeNetworkInfo.isConnected
        _connectedStatus.postValue(connected)
        if (!connected) {
            connectivityManager.registerNetworkCallback(
                NetworkRequest.Builder()
                    .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET).build(),
                connectivityCallback
            )
            monitoringConnectivity = true
        }
    }


}