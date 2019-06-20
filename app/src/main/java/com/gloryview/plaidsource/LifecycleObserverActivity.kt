package com.gloryview.plaidsource

import android.net.ConnectivityManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.content.getSystemService
import androidx.lifecycle.Observer
import com.gloryview.plaidsource.util.lifecycleobserver.ConnectivityChecker
import com.gloryview.plaidsource.util.lifecycleobserver.LifecycleObserverDemo

class LifecycleObserverActivity : AppCompatActivity() {
    val lifecycleObserverDemo by lazy {
        LifecycleObserverDemo()
    }
    val connectivityManager by lazy {
        return@lazy getSystemService<ConnectivityManager>()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lifecycle_observer)
        lifecycleObserverDemo.apply {
            lifecycle.addObserver(this)
        }
        connectivityManager?.also {
            lifecycle.addObserver(ConnectivityChecker(it).apply {
                connectedStatus.observe(this@LifecycleObserverActivity,
                    Observer<Boolean> { isConnect ->
                        run {
                            if (isConnect) {
                                Toast.makeText(this@LifecycleObserverActivity, "网络已连接", Toast.LENGTH_SHORT).show()
                            } else {
                                Toast.makeText(this@LifecycleObserverActivity, "网络已断开", Toast.LENGTH_SHORT).show()
                            }
                        }
                    })
            })
        }
    }
}
