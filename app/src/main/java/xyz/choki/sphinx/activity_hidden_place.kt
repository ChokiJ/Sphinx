package xyz.choki.sphinx

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_hidden_place.*
import org.jetbrains.anko.find
import org.jetbrains.anko.toast
import java.nio.file.Files.find

class activity_hidden_place : AppCompatActivity() {
    private var latitude: Double = 0.0
    private var longitude: Double = 0.0
        override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hidden_place)
            }

}

