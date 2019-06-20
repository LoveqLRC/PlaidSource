package com.gloryview.plaidsource.util.lifecycleobserver

import android.util.Log
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent

/**
 *
 * @ProjectName:    PlaidSource
 * @Package:        com.gloryview.plaidsource.util.lifecycleobserver
 * @ClassName:      LifecycleObserverDemo
 * @Description:     java类作用描述
 * @Author:         Rc
 * @CreateDate:     2019-06-20 11:39
 * @UpdateUser:     更新者
 * @UpdateDate:     2019-06-20 11:39
 * @UpdateRemark:   更新说明
 * @Version:        1.0
 */
class LifecycleObserverDemo : LifecycleObserver {


    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    fun onLifecycleObserverCreate() {
        Log.d("PlaidSource","onLifecycleObserverCreate")
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    fun onLifecycleObserverStart() {
        Log.d("PlaidSource","onLifecycleObserverStart")
    }


    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    fun onLifecycleObserverResume() {
        Log.d("PlaidSource","onLifecycleObserverResume")
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    fun onLifecycleObserverPause() {
        Log.d("PlaidSource","onLifecycleObserverPause")
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    fun onLifecycleObserverStop() {
        Log.d("PlaidSource","onLifecycleObserverStop")
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun onLifecycleObserverDestroy() {
        Log.d("PlaidSource","onLifecycleObserverDestroy")
    }
}