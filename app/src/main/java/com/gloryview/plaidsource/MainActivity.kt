package com.gloryview.plaidsource

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun cutout(view: View) {
        val intent = Intent(this, CutoutActivity::class.java)
        startActivity(intent)
    }

    fun materialButtons(view: View) {
        val intent = Intent(this, MaterialButtonActivity::class.java)
        startActivity(intent)
    }

    fun lifecycleObserver(view: View) {
        val intent = Intent(this, LifecycleObserverActivity::class.java)
        startActivity(intent)
    }
}
