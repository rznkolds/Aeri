package com.rk.aeri.activity

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.rk.aeri.R
import com.rk.aeri.databinding.ActivityMainBinding
import com.rk.aeri.serviceoperations.AeriCounter

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if ( this.packageManager.hasSystemFeature ( PackageManager.FEATURE_SENSOR_STEP_COUNTER ) ) {

            if ( ContextCompat.checkSelfPermission(this , Manifest.permission.ACTIVITY_RECOGNITION) != PackageManager.PERMISSION_GRANTED) {

                if ( Build.VERSION.SDK_INT >= 29 ) {

                    this.requestPermissions ( arrayOf ( Manifest.permission.ACTIVITY_RECOGNITION ) , 1 )

                }
            }
        }

        val serviceIntent = Intent ( this, AeriCounter::class.java )

        if ( Build.VERSION.SDK_INT >= 26 ) {

            startForegroundService ( serviceIntent )

        } else {

            startService ( serviceIntent )
        }

        val host = this.supportFragmentManager.findFragmentById(R.id.main_container)

        val controller = host?.findNavController()

        if (controller != null) {

            binding.mainBottomNavigation.setupWithNavController(controller)
        }
    }
}