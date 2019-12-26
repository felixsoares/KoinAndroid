package com.felix.nasa.architecture

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.felix.nasa.R
import com.felix.nasa.architecture.mvp.view.MainActivity
import com.felix.nasa.architecture.mvvm.SecondaryActivity
import kotlinx.android.synthetic.main.activity_menu.*

class MenuActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)

        btnMvp.setOnClickListener {
            start(Intent(this, MainActivity::class.java))
        }
        btnMvvm.setOnClickListener {
            start(Intent(this, SecondaryActivity::class.java))
        }
    }

    private fun start(intent: Intent) {
        startActivity(intent)
    }
}
